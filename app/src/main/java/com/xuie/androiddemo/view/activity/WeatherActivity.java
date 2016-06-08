package com.xuie.androiddemo.view.activity;

import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.mikepenz.iconics.view.IconicsImageView;
import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.R;
import com.xuie.androiddemo.bean.Weather;
import com.xuie.androiddemo.presenter.WeatherPresenter;
import com.xuie.androiddemo.util.WeatherUtils;
import com.xuie.androiddemo.view.activity.IView.IWeatherActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherActivity extends BaseActivity<WeatherActivity, WeatherPresenter> implements IWeatherActivity {

    @BindView(R.id.city) TextView city;
    @BindView(R.id.today) TextView today;
    @BindView(R.id.icon) IconicsImageView icon;
    @BindView(R.id.temp) TextView temp;
    @BindView(R.id.wind) TextView wind;
    @BindView(R.id.weather) TextView weather;
    @BindView(R.id.weather_content) LinearLayout weatherContent;

    @Override public void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        Logger.d("onCreate");

//        mPresenter.loadCity();
    }

    @Override protected WeatherPresenter createPresenter() {
        return new WeatherPresenter();
    }

    @Override public void setCity(String city) {
        this.city.setText(city);
        mPresenter.loadWeathers(city);
    }

    @Override public void setWeather(List<Weather> weathers) {
        Weather w = weathers.get(0);
        this.today.setText(w.getDate());
        this.icon.setImageDrawable(WeatherUtils.getDrawable(this, w.getType()));
        this.wind.setText(w.getFengxiang());
        this.temp.setText(w.getHigh() + w.getLow());
        this.weather.setText(w.getType());

        weatherContent.removeAllViews();
        for (Weather weather : weathers) {
            View view = LayoutInflater.from(this).inflate(R.layout.weather_item, null, false);
            TextView tv_date = ButterKnife.findById(view, R.id.date);
            ImageView iv_icon = ButterKnife.findById(view, R.id.icon);
            TextView tv_temp = ButterKnife.findById(view, R.id.temp);
            TextView tv_wind = ButterKnife.findById(view, R.id.wind);
            TextView tv_weather = ButterKnife.findById(view, R.id.weather);

            tv_date.setText(weather.getDate());
            iv_icon.setImageDrawable(WeatherUtils.getDrawable(this, weather.getType()));
            tv_temp.setText(weather.getHigh() + weather.getLow());
            tv_wind.setText(weather.getFengxiang());
            tv_weather.setText(weather.getType());
            weatherContent.addView(view);
        }
    }

    @Override public void showProgress() {

    }

    @Override public void hideProgress() {

    }
}
