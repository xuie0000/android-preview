package com.xuie.androiddemo.model.service;

import com.xuie.androiddemo.bean.WeatherBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by xuie on 16-6-7.
 */
public interface WthrcdnAPI {
    String API = "http://wthrcdn.etouch.cn/weather_mini?city=";

    Observable<String> loadLocation();

    @GET("{city}") Observable<List<WeatherBean>> loadWeather(@Path("city") String city);

}
