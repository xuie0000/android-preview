package com.xuie.androiddemo.model.IModel;

import com.xuie.androiddemo.bean.Weather;

import java.util.List;

import rx.Observable;

/**
 * Created by xuie on 16-6-7.
 */
public interface WeatherModel {
    Observable<String> getCity();

    Observable<List<Weather>> getWeathers(String city);
}
