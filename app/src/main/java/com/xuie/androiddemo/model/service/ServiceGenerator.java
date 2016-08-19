package com.xuie.androiddemo.model.service;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xuie.androiddemo.model.Injection;

import java.io.IOException;

import io.realm.RealmObject;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
        @Override public boolean shouldSkipField(FieldAttributes f) {
            return f.getDeclaringClass().equals(RealmObject.class);
        }

        @Override public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    }).create();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(WthrcdnAPI.API)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson));

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken) {
        return createService(serviceClass, builder, authToken);
    }

    public static <S> S createService(Class<S> serviceClass, Retrofit.Builder builder, final String authToken) {
        if (authToken != null) {
            httpClient.interceptors().add(chain -> {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", authToken)
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            });
        }

        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client=new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new DrTokenInterceptor())
                .build();

        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    private static class DrTokenInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request=chain.request();
            request=request.newBuilder().addHeader("Authorization",new StringBuilder()
                    .append("Bearer ")
                    .append(Injection.provideTokenValue())
                    .toString()).build();
            return chain.proceed(request);
        }
    }

}
