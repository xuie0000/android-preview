package com.xuie.androiddemo.view.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.R;
import com.xuie.androiddemo.bean.Weather;
import com.xuie.androiddemo.presenter.Weather2Presenter;
import com.xuie.androiddemo.view.activity.IView.IWeatherActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Weather_2_Activity extends  BaseActivity<Weather_2_Activity, Weather2Presenter> implements IWeatherActivity {

    @BindView(R.id.city) TextView city;
    @BindView(R.id.today) TextView today;
    @BindView(R.id.icon) ImageView icon;
    @BindView(R.id.temp) TextView temp;
    @BindView(R.id.wind) TextView wind;
    @BindView(R.id.weather) TextView weather;
    @BindView(R.id.weather_content) LinearLayout weatherContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Logger.d("onCreate............");
        super.onCreate(savedInstanceState);
        Logger.d("onCreate............");
        setContentView(R.layout.activity_weather);
        Logger.d("onCreate............");
        ButterKnife.bind(this);
        Logger.d("onCreate............");
    }

    @Override protected Weather2Presenter createPresenter() {
        return new Weather2Presenter();
    }

    @Override public void setCity(String city) {

    }

    @Override public void setWeather(List<Weather> weathers) {

    }

    @Override public void showProgress() {

    }

    @Override public void hideProgress() {

    }
}
