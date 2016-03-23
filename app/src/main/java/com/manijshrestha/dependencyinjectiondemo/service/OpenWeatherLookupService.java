package com.manijshrestha.dependencyinjectiondemo.service;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.manijshrestha.dependencyinjectiondemo.api.OpenWeatherService;
import com.manijshrestha.dependencyinjectiondemo.model.WeatherData;

import javax.inject.Inject;

import rx.Observable;

public class OpenWeatherLookupService implements WeatherLookupService {

    private final ConnectivityManager mConnectivityManager;
    private final OpenWeatherService mOpenWeatherService;

    @Inject
    public OpenWeatherLookupService(ConnectivityManager connectivityManager, OpenWeatherService openWeatherService) {
        mConnectivityManager = connectivityManager;
        mOpenWeatherService = openWeatherService;
    }

    @Override
    public Observable<WeatherData> getWeather(final String cityName) {
        if (!isConnected()) {
            return Observable.error(new Exception("No Network"));
        }

        return mOpenWeatherService.getWeatherData(cityName, "imperial");
    }

    private boolean isConnected() {
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
