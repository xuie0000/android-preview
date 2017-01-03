package com.xuie.android.ui.weather;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.xuie.android.R;
import com.xuie.android.bean.weather.Weather;
import com.xuie.android.databinding.ActivityWeatherBinding;
import com.xuie.android.ui.Injection;

import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity implements WeatherContracts.View {
    private WeatherContracts.Presenter mPresenter;
    private ActivityWeatherBinding weatherBinding;
    private List<Weather> weathers = new ArrayList<>();
    private WeatherAdapter weatherAdapter = new WeatherAdapter(weathers);

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weatherBinding = DataBindingUtil.setContentView(this, R.layout.activity_weather);
        new WeatherPresenter(Injection.provideWeatherRepository(), this);

        final GridLayoutManager manager = new GridLayoutManager(this, 2);
        weatherBinding.recyclerView.setLayoutManager(manager);
        weatherBinding.recyclerView.setAdapter(weatherAdapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                return weatherAdapter.isHeader(position) ? manager.getSpanCount() : 1;
            }
        });
    }

    @Override protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override public void setCity(String city) {
        weatherBinding.setCity(city);
        mPresenter.loadWeathers(city);
    }

    @Override public void setWeather(List<Weather> weathers) {
        Weather w = weathers.get(0);
        weatherBinding.setWeather(w);

        this.weathers.clear();
        this.weathers.addAll(weathers);
        weatherAdapter.notifyDataSetChanged();

        mPresenter.loadCity();
//        new Handler(getMainLooper()).postDelayed(() -> mPresenter.loadCity(), 5000 * 10);
    }

    @BindingAdapter("android:src") public static void setImageResource(ImageView imageView, Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    @Override public void getWeatherFail() {
        new Handler(getMainLooper()).postDelayed(() -> mPresenter.loadCity(), 5000);
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
