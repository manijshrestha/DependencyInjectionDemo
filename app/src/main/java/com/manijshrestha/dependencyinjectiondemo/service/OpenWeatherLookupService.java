package com.manijshrestha.dependencyinjectiondemo.service;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.manijshrestha.dependencyinjectiondemo.api.OpenWeatherService;
import com.manijshrestha.dependencyinjectiondemo.model.WeatherData;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OpenWeatherLookupService implements WeatherLookupService {

    ConnectivityManager mConnectivityManager;

    public OpenWeatherLookupService(ConnectivityManager connectivityManager) {
        mConnectivityManager = connectivityManager;
    }

    @Override
    public void getWeather(final String cityName, final WeatherLookupListener listener) {
        if (!isConnected()) {
            listener.onNoConnectivity();
            return;
        }

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://api.openweathermap.org").build();
        OpenWeatherService openWeatherService = adapter.create(OpenWeatherService.class);

        openWeatherService.getWeatherData(cityName, "imperial", new Callback<WeatherData>() {
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
