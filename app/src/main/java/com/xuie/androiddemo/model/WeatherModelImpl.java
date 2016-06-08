package com.xuie.androiddemo.model;

import com.xuie.androiddemo.bean.Weather;
import com.xuie.androiddemo.model.IModel.WeatherModel;
import com.xuie.androiddemo.model.service.ServiceGenerator;
import com.xuie.androiddemo.model.service.SinaAPI;
import com.xuie.androiddemo.model.service.WthrcdnAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by xuie on 16-6-7.
 */
public class WeatherModelImpl implements WeatherModel {

    static WeatherModelImpl instance;
    private static Retrofit.Builder wthrcdnBuilder =
            new Retrofit.Builder()
                    .baseUrl(WthrcdnAPI.API)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit.Builder sinaBuilder =
            new Retrofit.Builder()
                    .baseUrl(SinaAPI.API)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());
    private static SinaAPI sinaApi;
    private static WthrcdnAPI wthrcdnAPI;

    public WeatherModelImpl() {
        sinaApi = ServiceGenerator.createService(SinaAPI.class, sinaBuilder, null);
        wthrcdnAPI = ServiceGenerator.createService(WthrcdnAPI.class, wthrcdnBuilder, null);
    }

    public static WeatherModelImpl getInstance() {
        if (instance == null) {
            synchronized (WeatherModelImpl.class) {
                instance = new WeatherModelImpl();
            }
        }
        return instance;
    }

    @Override public Observable<String> getCity() {
        return sinaApi.getSinaDpool().map(s -> {
            String newData = s.substring(0, s.length() - 4);
            int startIndex = newData.lastIndexOf('\t') + 1;
            return newData.substring(startIndex, newData.length());
        });
    }

    @Override public Observable<List<Weather>> getWeathers(String city) {
        return wthrcdnAPI.getWthrcdnData(city).map(wthrcdn -> {
            List<Weather> weathers = new ArrayList<>();
            weathers.addAll(wthrcdn.getData().getWeather());
            return weathers;
        });
    }
}
