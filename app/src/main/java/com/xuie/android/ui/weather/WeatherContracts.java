package com.xuie.android.ui.weather;

import com.xuie.android.bean.weather.Weather;
import com.xuie.android.ui.BasePresenter;
import com.xuie.android.ui.BaseView;

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
