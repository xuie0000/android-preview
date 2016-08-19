package com.xuie.androiddemo.data.IModel;

import com.xuie.androiddemo.bean.weather.Weather;

import java.util.List;

import rx.Observable;

/**
 * Created by xuie on 16-6-7.
 */
public interface WeatherModel {
    void getCity(IWeatherCallback weatherCallback);

    Observable<List<Weather>> getWeathers(String city);
}
