package com.xuie.androiddemo.model.service;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by xuie on 16-6-8.
 */
public interface SinaAPI {
    String API = "http://int.dpool.sina.com.cn/iplookup/iplookup.php";

    @GET() Observable<String> getSinaDpool();
}
