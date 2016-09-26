package com.xuie.androiddemo.ui.weather;

import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.data.source.IWeatherCallback;
import com.xuie.androiddemo.data.WeatherRepository;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherPresenter implements WeatherContracts.Presenter {
    private WeatherRepository mWeatherRepository;
    private WeatherContracts.View mWeatherView;
    private WeatherCallback weatherCallback = new WeatherCallback();

    public WeatherPresenter(WeatherRepository weatherRepository, WeatherContracts.View weatherView) {
        mWeatherRepository = weatherRepository;
        mWeatherView = weatherView;
        mWeatherView.setPresenter(this);
    }

    public void loadWeathers(String city) {
        mWeatherRepository.getWeathers(city)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weathers -> mWeatherView.setWeather(weathers),
                        throwable -> Logger.e(throwable.getMessage()));
    }

    public void loadCity() {
        mWeatherRepository.getCity(weatherCallback);
    }

    @Override public void subscribe() {
        loadCity();
    }

    @Override public void unsubscribe() {

    }

    private class WeatherCallback implements IWeatherCallback {
        @Override public void setCity(String city) {
            mWeatherView.setCity(city);
        }

        @Override public void requestCityFail(String message) {
            Logger.e(message);
        }
    }
}
