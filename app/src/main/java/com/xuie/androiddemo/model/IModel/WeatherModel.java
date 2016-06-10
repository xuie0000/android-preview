package com.xuie.androiddemo.model.IModel;

import com.xuie.androiddemo.bean.Weather;

import java.util.List;

import rx.Observable;

/**
 * Created by xuie on 16-6-7.
 */
public interface WeatherModel {
    void getCity(IWeatherCallback weatherCallback);

    Observable<List<Weather>> getWeathers(String city);
}
