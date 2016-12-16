package com.xuie.android.ui.weather;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xuie.android.R;
import com.xuie.android.bean.weather.Weather;
import com.xuie.android.databinding.ItemWeatherContentBinding;
import com.xuie.android.databinding.ItemWeatherHeadBinding;

import java.util.List;

/**
 * Created by xuie on 16-9-26.
 */

public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_FOOTER = 1;
    public static final int TYPE_NORMAL = 2;

    private List<Weather> weathers;

    public WeatherAdapter(List<Weather> weathers) {
        this.weathers = weathers;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_HEADER:
            case TYPE_FOOTER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_head, parent, false);
                return new HeadViewHolder(view);
            case TYPE_NORMAL:
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_content, parent, false);
                return new ContentViewHolder(view);
        }
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
            case TYPE_FOOTER:
                ((HeadViewHolder) holder).bind(weathers.get(position));
                break;
            case TYPE_NORMAL:
                ((ContentViewHolder) holder).bind(weathers.get(position));
                break;
        }
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override public int getItemCount() {
        return weathers != null ? weathers.size() : 0;
    }

    @Override public int getItemViewType(int position) {
//        if (position == getItemCount()-1){
//            return TYPE_FOOTER;
//        }
        return isHeader(position) ? TYPE_HEADER : TYPE_NORMAL;
    }

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    private class ContentViewHolder extends RecyclerView.ViewHolder {
        ItemWeatherContentBinding itemWeatherContentBinding;

        ContentViewHolder(View itemView) {
            super(itemView);
            itemWeatherContentBinding = DataBindingUtil.bind(itemView);
        }

        public void bind(Weather weather) {
            itemWeatherContentBinding.setWeather(weather);
        }
    }

    private class HeadViewHolder extends RecyclerView.ViewHolder {
        ItemWeatherHeadBinding itemWeatherHeadBinding;

        HeadViewHolder(View itemView) {
            super(itemView);
            itemWeatherHeadBinding = DataBindingUtil.bind(itemView);
        }

        public void bind(Weather weather) {
            itemWeatherHeadBinding.setWeather(weather);
        }
    }
}
