package com.manijshrestha.dependencyinjectiondemo.api;

import com.manijshrestha.dependencyinjectiondemo.model.WeatherData;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface OpenWeatherService {

    @GET("/data/2.5/weather")
    void getWeatherData(@Query("q") String cityName, @Query("units") String unit, Callback<WeatherData> onWeatherData);
}
