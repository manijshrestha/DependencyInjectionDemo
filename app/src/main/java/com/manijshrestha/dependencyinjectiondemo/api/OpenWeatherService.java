package com.manijshrestha.dependencyinjectiondemo.api;

import com.manijshrestha.dependencyinjectiondemo.model.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {

    @GET("/data/2.5/weather")
    Call<WeatherData> getWeatherData(@Query("q") String cityName, @Query("units") String unit);
}
