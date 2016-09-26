package com.xuie.androiddemo.ui.weather;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuie.androiddemo.R;
import com.xuie.androiddemo.bean.weather.Weather;
import com.xuie.androiddemo.databinding.WeatherDataBindingItemBinding;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_data_binding_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(weathers.get(position));
    }

    @Override public int getItemCount() {
        return weathers != null ? weathers.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        WeatherDataBindingItemBinding weatherDataBindingItemBinding;

        MyViewHolder(View itemView) {
            super(itemView);
            weatherDataBindingItemBinding = DataBindingUtil.bind(itemView);
        }

        public void bind(Weather weather) {
            weatherDataBindingItemBinding.setWeather(weather);
        }
    }
}
