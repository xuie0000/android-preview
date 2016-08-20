package com.xuie.androiddemo.data.source;

public interface IWeatherCallback {
	void setCity(String city);

	void requestCityFail(String message);
}
