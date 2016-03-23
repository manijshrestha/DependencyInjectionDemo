package com.manijshrestha.dependencyinjectiondemo;

import android.net.ConnectivityManager;

import com.manijshrestha.dependencyinjectiondemo.api.OpenWeatherService;
import com.manijshrestha.dependencyinjectiondemo.service.OpenWeatherLookupService;
import com.manijshrestha.dependencyinjectiondemo.service.WeatherLookupService;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Provides
    @Singleton
    WeatherLookupService providesWeatherLookupService(ConnectivityManager connectivityManager, OpenWeatherService openWeatherService) {
        return new OpenWeatherLookupService(connectivityManager, openWeatherService);
    }

    @Provides
    @Singleton
    @Named("LoggingInterceptor")
    Interceptor providesLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    @Named("OpenAPIKeyInterceptor")
    Interceptor providesInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                HttpUrl url = originalRequest.url();
                HttpUrl updatedUrl = url.newBuilder()
                        .addQueryParameter("APPID", BuildConfig.API_KEY)
                        .build();

                Request newRequest = originalRequest.newBuilder()
                        .url(updatedUrl).build();

                return chain.proceed(newRequest);
            }
        };
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(@Named("OpenAPIKeyInterceptor") Interceptor interceptor, @Named("LoggingInterceptor") Interceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    OpenWeatherService providesOpenWeatherService(OkHttpClient okHttpClient) {
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return adapter.create(OpenWeatherService.class);
    }
}
