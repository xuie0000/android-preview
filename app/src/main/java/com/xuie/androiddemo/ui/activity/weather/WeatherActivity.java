package com.xuie.androiddemo.ui.activity.weather;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.R;
import com.xuie.androiddemo.bean.weather.Weather;
import com.xuie.androiddemo.ui.Injection;
import com.xuie.androiddemo.util.WeatherUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherActivity extends AppCompatActivity implements WeatherContracts.View {

    @BindView(R.id.city) TextView city;
    @BindView(R.id.today) TextView today;
    @BindView(R.id.icon) ImageView icon;
    @BindView(R.id.temp) TextView temp;
    @BindView(R.id.wind) TextView wind;
    @BindView(R.id.weather) TextView weather;
    @BindView(R.id.weather_content) LinearLayout weatherContent;

    private WeatherContracts.Presenter mPresenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        Logger.d("onCreate");

        mPresenter = new WeatherPresenter(Injection.provideWeatherRepository(), this);

        mPresenter.loadCity();
    }

    @Override protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
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

    @Override public void setPresenter(WeatherContracts.Presenter presenter) {

    }
}
