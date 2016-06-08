package com.xuie.androiddemo.view.activity.IView;

import com.xuie.androiddemo.bean.Weather;

import java.util.List;

public interface IWeatherActivity {

    void setCity(String city);

    void setWeather(List<Weather> weathers);

    void showProgress();

    void hideProgress();
}
