package com.xuie.androiddemo.util;

import android.content.Context;
import android.graphics.Color;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.weather_icons_typeface_library.WeatherIcons;

/**
 * Created by xuie on 16-6-8.
 */
public class WeatherUtils {
    public static IconicsDrawable getDrawable(Context context, String weather) {
        switch (weather) {
            case "多云":
            case "多云转阴":
            case "多云转晴":
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_day_cloudy).color(Color.BLACK).sizeDp(24);
            case "中雨":
            case "中到大雨":
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_rain).color(Color.BLACK).sizeDp(24);
            case "雷阵雨":
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_thunderstorm).color(Color.BLACK).sizeDp(24);
            case "阵雨":
            case "阵雨转多云":
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_rain_mix).color(Color.BLACK).sizeDp(24);
            case "暴雪":
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_snow).color(Color.BLACK).sizeDp(24);
            case "暴雨":
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_rain).color(Color.BLACK).sizeDp(24);
            case "大暴雨":
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_rain).color(Color.BLACK).sizeDp(24);
            case "大雪":
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_snow).color(Color.BLACK).sizeDp(24);
            case "大雨":
            case "大雨转中雨":
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_rain).color(Color.BLACK).sizeDp(24);
            case "雷阵雨冰雹":
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_hail).color(Color.BLACK).sizeDp(24);
            case "晴":
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_day_sunny).color(Color.BLACK).sizeDp(24);
            case "沙尘暴":
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_sandstorm).color(Color.BLACK).sizeDp(24);
            case "特大暴雨":
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_rain_wind).color(Color.BLACK).sizeDp(24);
            case "雾":
            case "雾霾":
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_smog).color(Color.BLACK).sizeDp(24);
            case "小雪":
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_snow).color(Color.BLACK).sizeDp(24);
            case "小雨":
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_raindrops).color(Color.BLACK).sizeDp(24);
            case "阴":
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_cloud).color(Color.BLACK).sizeDp(24);
            case "雨夹雪":
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_sleet).color(Color.BLACK).sizeDp(24);
            case "阵雪":
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_rain_mix).color(Color.BLACK).sizeDp(24);
            case "中雪":
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_snow).color(Color.BLACK).sizeDp(24);
            default:
                return new IconicsDrawable(context, WeatherIcons.Icon.wic_day_sunny).color(Color.BLACK).sizeDp(24);
        }
    }
}
