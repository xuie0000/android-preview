package com.xuie.androiddemo.data.IModel;

public interface IWeatherCallback {
	void setCity(String city);

	void requestCityFail(String message);
}
