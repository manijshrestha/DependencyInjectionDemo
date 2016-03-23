package com.manijshrestha.dependencyinjectiondemo.api;

import com.manijshrestha.dependencyinjectiondemo.model.WeatherData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface OpenWeatherService {

    @GET("/data/2.5/weather")
    Observable<WeatherData> getWeatherData(@Query("q") String cityName, @Query("units") String unit);
}
