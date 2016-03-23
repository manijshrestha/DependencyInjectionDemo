package com.manijshrestha.dependencyinjectiondemo.service;

import com.manijshrestha.dependencyinjectiondemo.model.WeatherData;

import rx.Observable;

public interface WeatherLookupService {

    Observable<WeatherData> getWeather(String city);
}
