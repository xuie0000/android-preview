package com.xuie.androiddemo.view.activity.IView;

import android.graphics.drawable.Drawable;

import com.xuie.androiddemo.bean.WeatherBean;

import java.util.List;

public interface IWeatherActivity {

    void showProgress();

    void hideProgress();

    void setCity(String city);

    void setToday(String data);

    void setTemperature(String temperature);

    void setWind(String wind);

    void setWeather(String weather);

    void setWeatherImage(Drawable drawable);

    void setWeatherData(List<WeatherBean> lists);

    void showErrorToast(String msg);
}
