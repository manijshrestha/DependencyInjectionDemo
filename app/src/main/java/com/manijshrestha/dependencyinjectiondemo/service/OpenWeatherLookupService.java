package com.manijshrestha.dependencyinjectiondemo.service;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.manijshrestha.dependencyinjectiondemo.api.OpenWeatherService;
import com.manijshrestha.dependencyinjectiondemo.model.WeatherData;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenWeatherLookupService implements WeatherLookupService {

    private final ConnectivityManager mConnectivityManager;
    private final OpenWeatherService mOpenWeatherService;

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

        Call<WeatherData> call = mOpenWeatherService.getWeatherData(cityName, "imperial");
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                listener.onWeatherData(response.body());
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                listener.onNoWeatherData();
            }
        });
    }

    private boolean isConnected() {
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
