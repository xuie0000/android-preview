package com.xuie.androiddemo.ui.weather;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.xuie.androiddemo.R;
import com.xuie.androiddemo.bean.weather.Weather;
import com.xuie.androiddemo.databinding.ActivityWeatherDataBindingBinding;
import com.xuie.androiddemo.ui.Injection;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataBindingActivity extends AppCompatActivity implements WeatherContracts.View {
    private WeatherContracts.Presenter mPresenter;
    private ActivityWeatherDataBindingBinding weatherDataBindingBinding;
    private List<Weather> weathers = new ArrayList<>();
    private WeatherAdapter weatherAdapter = new WeatherAdapter(weathers);

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_data_binding);

        weatherDataBindingBinding = DataBindingUtil.setContentView(this, R.layout.activity_weather_data_binding);
        new WeatherPresenter(Injection.provideWeatherRepository(), this);

        weatherDataBindingBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        weatherDataBindingBinding.recyclerView.setAdapter(weatherAdapter);
    }

    @Override protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override public void setCity(String city) {
        weatherDataBindingBinding.setCity(city);
        mPresenter.loadWeathers(city);
    }

    @Override public void setWeather(List<Weather> weathers) {
        Weather w = weathers.get(0);
        weatherDataBindingBinding.setWeather(w);

        this.weathers.clear();
        this.weathers.addAll(weathers);
        weatherAdapter.notifyDataSetChanged();
    }

    @Override public void getWeatherFail() {

    }

    @Override protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                ;
        decorView.setSystemUiVisibility(uiOptions);
        mPresenter.subscribe();
    }

    @Override protected void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override public void setPresenter(WeatherContracts.Presenter presenter) {
        mPresenter = presenter;
    }
}
