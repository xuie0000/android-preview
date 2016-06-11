package com.xuie.androiddemo.model.service;

import com.xuie.androiddemo.bean.weather.Wthrcdn;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xuie on 16-6-7.
 */
public interface WthrcdnAPI {
    String API = "http://wthrcdn.etouch.cn/";

    @GET("weather_mini") Observable<Wthrcdn> getWthrcdnData(@Query("city") String city);

}
