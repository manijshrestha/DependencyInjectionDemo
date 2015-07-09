package com.manijshrestha.dependencyinjectiondemo;

import android.net.ConnectivityManager;

import com.manijshrestha.dependencyinjectiondemo.api.OpenWeatherService;
import com.manijshrestha.dependencyinjectiondemo.service.OpenWeatherLookupService;
import com.manijshrestha.dependencyinjectiondemo.service.WeatherLookupService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module
public class AppModule {

    @Provides
    @Singleton
    WeatherLookupService providesWeatherLookupService(ConnectivityManager connectivityManager, OpenWeatherService openWeatherService) {
        return new OpenWeatherLookupService(connectivityManager, openWeatherService);
    }

    @Provides
    @Singleton
    OpenWeatherService providesOpenWeatherService() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://api.openweathermap.org").build();
        return adapter.create(OpenWeatherService.class);
    }
}
