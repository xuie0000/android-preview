package com.xuie.androiddemo.model.service;

import com.xuie.androiddemo.bean.dribbble.AccessToken;
import com.xuie.androiddemo.bean.dribbble.AuthBody;
import com.xuie.androiddemo.bean.dribbble.Shot;
import com.xuie.androiddemo.bean.dribbble.User;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface DribbbleAPI {
    String API = "https://api.dribbble.com/v1/";

    @GET("shots") Observable<Shot[]> getShots(@Query("list") String list,
                                              @Query("timeframe") String timeframe,
                                              @Query("date") String date,
                                              @Query("sort") String sort,
                                              @Query("page") int page,
                                              @Query("per_page") int per_page);

    @GET("shots") Observable<Shot[]> getShots(@Query("page") int page,
                                              @Query("per_page") int per_page);

    @POST("https://dribbble.com/oauth/token") Observable<AccessToken> getAccessToken(@Body AuthBody body);

    @FormUrlEncoded
    @POST("https://dribbble.com/oauth/token")
    Observable<String> getDrToken(@Field("client_id")String client_id,
                                         @Field("client_secret")String client_secret,
                                         @Field("code")String code,
                                         @Field("redirect_uri")String redirect_uri);


    @GET("user") Observable<User> getUserWithAccessToken(@Query("access_token") String accessToken);

    @GET("users/{username}") Observable<User> getUserInfo(@Path("username") String username);

    @GET("shots/{shot_id}/likes") Observable<User> getShotLikes(@Path("shot_id") String shot_id);

    @GET("shots/{shot_id}/likes") Observable cheakIsLikeShot(@Path("shot_id") String shot_id);

    @POST("shots/{shot_id}/likes") Observable likeShot(@Path("shot_id") String shot_id);


}
