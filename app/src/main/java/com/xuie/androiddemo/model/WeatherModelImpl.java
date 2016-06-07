package com.xuie.androiddemo.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xuie.androiddemo.bean.WeatherBean;
import com.xuie.androiddemo.model.IModel.WeatherModel;
import com.xuie.androiddemo.model.service.BaiduAPI;
import com.xuie.androiddemo.model.service.DribbbleAPI;
import com.xuie.androiddemo.model.service.ServiceGenerator;
import com.xuie.androiddemo.model.service.WthrcdnAPI;

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
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(DribbbleAPI.API)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());
    private static BaiduAPI baiduAPI;
    private static WthrcdnAPI wthrcdnAPI;

    public WeatherModelImpl() {
        baiduAPI = ServiceGenerator.createService(BaiduAPI.class, builder, null);
        wthrcdnAPI = ServiceGenerator.createService(WthrcdnAPI.class, builder, null);
    }

    public static WeatherModelImpl getInstance() {
        if (instance == null) {
            synchronized (WeatherModelImpl.class) {
                instance = new WeatherModelImpl();
            }
        }
        return instance;
    }

    @Override public Observable<String> loadLocation(double location, double longitude) {
        return baiduAPI.loadLocation(location, longitude).map(this::getCity);
    }

    @Override public Observable<List<WeatherBean>> loadWeather(String city) {
        return null;
    }


    public String getCity(String json) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(json).getAsJsonObject();
        JsonElement status = jsonObj.get("status");
        if (status != null && "OK".equals(status.getAsString())) {
            JsonObject result = jsonObj.getAsJsonObject("result");
            if (result != null) {
                JsonObject addressComponent = result.getAsJsonObject("addressComponent");
                if (addressComponent != null) {
                    JsonElement cityElement = addressComponent.get("city");
                    if (cityElement != null) {
                        return cityElement.getAsString().replace("市", "");
                    }
                }
            }
        }
        return null;
    }
}
