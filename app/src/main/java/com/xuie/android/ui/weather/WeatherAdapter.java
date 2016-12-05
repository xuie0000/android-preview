package com.xuie.android.ui.weather;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuie.android.R;
import com.xuie.android.bean.weather.Weather;
import com.xuie.android.databinding.ItemWeatherDataBindingBinding;

import java.util.List;

/**
 * Created by xuie on 16-9-26.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> {
    private List<Weather> weathers;

    public WeatherAdapter(List<Weather> weathers) {
        this.weathers = weathers;
    }

    @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_data_binding, parent, false);
        return new MyViewHolder(view);
    }

    @Override public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(weathers.get(position));
    }

    @Override public int getItemCount() {
        return weathers != null ? weathers.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ItemWeatherDataBindingBinding itemWeatherDataBindingBinding;

        MyViewHolder(View itemView) {
            super(itemView);
            itemWeatherDataBindingBinding = DataBindingUtil.bind(itemView);
        }

        public void bind(Weather weather) {
            itemWeatherDataBindingBinding.setWeather(weather);
        }
    }
}
