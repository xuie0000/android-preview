package com.xuie.android.data;

import com.orhanobut.logger.Logger;
import com.xuie.android.bean.weather.Weather;
import com.xuie.android.data.api.ServiceGenerator;
import com.xuie.android.data.api.WthrcdnAPI;
import com.xuie.android.data.source.IWeatherCallback;
import com.xuie.android.data.source.WeatherSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;

/**
 * Created by xuie on 16-6-7.
 */
public class WeatherRepository implements WeatherSource {

    private static WeatherRepository instance;
    private WthrcdnAPI wthrcdnAPI;
    private OkHttpClient okHttpClient;

    private WeatherRepository() {
        wthrcdnAPI = ServiceGenerator.createService(WthrcdnAPI.class);
        okHttpClient = new OkHttpClient();
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
        Request request = new Request.Builder()
                .url("http://int.dpool.sina.com.cn/iplookup/iplookup.php")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                Logger.e("failure " + e.getMessage());
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                if (callback == null) {
                    Logger.e("callback is null");
                    return;
                }
                String body = response.body().string();

                Logger.d("s:" + body + ", s.length:" + body.length());
                String[] results = body.split("\t");
                for (String s : results) {
                    System.out.println("" + s);
                }
                String city = "深圳";
                if (results.length >= 6)
                    city = results[5];
                Logger.d("city : " + city);
                String finalCity = city;
                callback.setCity(finalCity);
//                new Handler(Looper.getMainLooper()).post(() -> callback.setCity(finalCity));
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
