package com.xuie.androiddemo.model.IModel;

public interface IWeatherCallback {
	void setCity(String city);

	void requestCityFail(String message);
}
