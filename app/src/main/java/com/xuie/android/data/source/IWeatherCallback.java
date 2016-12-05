package com.xuie.android.data.source;

public interface IWeatherCallback {
	void setCity(String city);

	void requestCityFail(String message);
}
