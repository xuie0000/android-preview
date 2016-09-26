package com.xuie.androiddemo.ui.weather;

import com.xuie.androiddemo.bean.weather.Weather;
import com.xuie.androiddemo.ui.BasePresenter;
import com.xuie.androiddemo.ui.BaseView;

import java.util.List;

/**
 * Created by xuie on 16-8-20.
 */

public interface WeatherContracts {

    interface View extends BaseView<Presenter> {
        void setCity(String city);

        void setWeather(List<Weather> weathers);

        void getWeatherFail();
    }

    interface Presenter extends BasePresenter {
        void loadWeathers(String city);


        void loadCity();
    }
}
