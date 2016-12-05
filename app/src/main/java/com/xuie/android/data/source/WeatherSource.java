package com.xuie.android.data.source;

import com.xuie.android.bean.weather.Weather;

import java.util.List;

import rx.Observable;

/**
 * Created by xuie on 16-6-7.
 */
public interface WeatherSource {
    void getCity(IWeatherCallback weatherCallback);

    Observable<List<Weather>> getWeathers(String city);
}
