package com.xuie.androiddemo.model;

import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.bean.weather.Weather;
import com.xuie.androiddemo.model.IModel.IWeatherCallback;
import com.xuie.androiddemo.model.IModel.WeatherModel;
import com.xuie.androiddemo.model.service.ServiceGenerator;
import com.xuie.androiddemo.model.service.WthrcdnAPI;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import rx.Observable;

/**
 * Created by xuie on 16-6-7.
 */
public class WeatherModelImpl implements WeatherModel {

    private static WeatherModelImpl instance;
    private WthrcdnAPI wthrcdnAPI;

    private WeatherModelImpl() {
        wthrcdnAPI = ServiceGenerator.createService(WthrcdnAPI.class);
    }

    public static WeatherModelImpl getInstance() {
        if (instance == null) {
            synchronized (WeatherModelImpl.class) {
                instance = new WeatherModelImpl();
            }
        }
        return instance;
    }

    @Override public void getCity(IWeatherCallback callback) {
        OkHttpUtils.get().url("http://int.dpool.sina.com.cn/iplookup/iplookup.php").build().execute(new StringCallback() {
            @Override public void onError(Call call, Exception e, int id) {
                Logger.e(e.getMessage());
            }

            @Override public void onResponse(String response, int id) {
                if (callback == null) {
                    Logger.e("callback is null");
                    return;
                }

                Logger.d("s:" + response + ", s.length:" + response.length());
                if (response.equals("")) {
                    callback.setCity("深圳");
                }
                String newData = response.substring(0, response.length() - 4);
                int startIndex = newData.lastIndexOf('\t') + 1;
                callback.setCity(newData.substring(startIndex, newData.length()));
            }
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
