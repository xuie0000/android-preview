package com.xuie.androiddemo.model.service;

import com.xuie.androiddemo.bean.Wthrcdn;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by xuie on 16-6-7.
 */
public interface WthrcdnAPI {
    String API = "http://wthrcdn.etouch.cn/weather_mini?city=";

    @GET("{city}") Observable<Wthrcdn> getWthrcdnData(@Path("city") String city);

}
