package com.manijshrestha.dependencyinjectiondemo.service;

public interface WeatherLookupService {

    void getWeather(String city, WeatherLookupListener listener);
}
