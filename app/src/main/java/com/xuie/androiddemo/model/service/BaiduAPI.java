package com.xuie.androiddemo.model.service;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by xuie on 16-6-7.
 */
public interface BaiduAPI {
    String API = "http://api.map.baidu.com/geocoder?output=json&referer=32D45CBEEC107315C553AD1131915D366EEF79B4&location=";
    String API2 = "http://int.dpool.sina.com.cn/iplookup/iplookup.php";
    @GET("{location},{longitude}") Observable<String> loadLocation(@Path("location") double location, @Path("longitude") double longitude);
}
