package com.xuie.androiddemo.model.IModel;

import com.xuie.androiddemo.bean.WeatherBean;

import java.util.List;

import rx.Observable;

/**
 * Created by xuie on 16-6-7.
 */
public interface WeatherModel {
    Observable<String> loadLocation(double location, double longitude);

    Observable<List<WeatherBean>> loadWeather(String city);
}
