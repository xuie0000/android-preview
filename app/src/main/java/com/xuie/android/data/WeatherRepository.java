package com.xuie.android.data;

import com.orhanobut.logger.Logger;
import com.xuie.android.bean.weather.Weather;
import com.xuie.android.data.source.IWeatherCallback;
import com.xuie.android.data.source.WeatherSource;
import com.xuie.android.data.api.ServiceGenerator;
import com.xuie.android.data.api.WthrcdnAPI;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import rx.Observable;

/**
 * Created by xuie on 16-6-7.
 */
public class WeatherRepository implements WeatherSource {

    private static WeatherRepository instance;
    private WthrcdnAPI wthrcdnAPI;

    private WeatherRepository() {
        wthrcdnAPI = ServiceGenerator.createService(WthrcdnAPI.class);
    }

    public static WeatherRepository getInstance() {
        if (instance == null) {
            synchronized (WeatherRepository.class) {
                instance = new WeatherRepository();
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

//                Logger.d("s:" + response + ", s.length:" + response.length());
                String[] results = response.split("\t");
                for (String s : results) {
                    System.out.println("" + s);
                }
                String city = "深圳";
                if (results.length >= 6)
                    city = results[5];
                Logger.d("city:" + city);
                callback.setCity(city);
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
