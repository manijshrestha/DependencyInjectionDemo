package com.manijshrestha.dependencyinjectiondemo.service;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.manijshrestha.dependencyinjectiondemo.api.OpenWeatherService;
import com.manijshrestha.dependencyinjectiondemo.model.WeatherData;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OpenWeatherLookupService implements WeatherLookupService {

    ConnectivityManager mConnectivityManager;
    OpenWeatherService mOpenWeatherService;

    @Inject
    public OpenWeatherLookupService(ConnectivityManager connectivityManager, OpenWeatherService openWeatherService) {
        mConnectivityManager = connectivityManager;
        mOpenWeatherService = openWeatherService;
    }

    @Override
    public void getWeather(final String cityName, final WeatherLookupListener listener) {
        if (!isConnected()) {
            listener.onNoConnectivity();
            return;
        }

        mOpenWeatherService.getWeatherData(cityName, "imperial", new Callback<WeatherData>() {
            @Override
            public void success(WeatherData weatherData, Response response) {
                listener.onWeatherData(weatherData);
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onNoWeatherData();
            }
        });
    }

    private boolean isConnected() {
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
