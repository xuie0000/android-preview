package com.xuie.androiddemo.presenter;

import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.model.IModel.IWeatherCallback;
import com.xuie.androiddemo.model.IModel.WeatherModel;
import com.xuie.androiddemo.model.WeatherModelImpl;
import com.xuie.androiddemo.view.activity.WeatherActivity;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherPresenter extends BasePresenter<WeatherActivity> {

    private WeatherModel weatherModel;
    private WeatherCallback weatherCallback = new WeatherCallback();

    public WeatherPresenter() {
        this.weatherModel = WeatherModelImpl.getInstance();
    }

    public void loadWeathers(String city) {
        weatherModel.getWeathers(city)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weathers -> getView().setWeather(weathers),
                        throwable -> Logger.e(throwable.getMessage()));
    }

    public void loadCity() {
        weatherModel.getCity(weatherCallback);
    }

    class WeatherCallback implements IWeatherCallback {
        @Override public void setCity(String city) {
            getView().setCity(city);
        }

        @Override public void requestCityFail(String message) {
            Logger.e(message);
        }
    }
}
