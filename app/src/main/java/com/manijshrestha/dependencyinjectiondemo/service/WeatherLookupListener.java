package com.manijshrestha.dependencyinjectiondemo.service;

import com.manijshrestha.dependencyinjectiondemo.model.WeatherData;

public interface WeatherLookupListener {

    void onWeatherData(WeatherData weatherData);

    void onNoWeatherData();

    void onNoConnectivity();
}
