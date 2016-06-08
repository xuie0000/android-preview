package com.xuie.androiddemo.presenter;

import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.model.IModel.WeatherModel;
import com.xuie.androiddemo.model.WeatherModelImpl;
import com.xuie.androiddemo.view.activity.WeatherActivity;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherPresenter extends BasePresenter<WeatherActivity> {

    WeatherModel weatherModel;

    public WeatherPresenter() {
        this.weatherModel = new WeatherModelImpl();
    }

    public void loadWeathers(String city) {
        weatherModel.getWeathers(city)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weathers -> getView().setWeather(weathers), throwable -> Logger.e(throwable.getMessage()));
    }

    public void loadCity() {
        weatherModel.getCity()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> getView().setCity(s), throwable -> Logger.e(throwable.getMessage()));
    }
}
