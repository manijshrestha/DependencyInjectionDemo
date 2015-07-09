package com.manijshrestha.dependencyinjectiondemo;

import com.manijshrestha.dependencyinjectiondemo.ui.WeatherLookupActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                AndroidModule.class,
                AppModule.class
        }
)
public interface AppComponent {

    void inject(WeatherLookupActivity activity);
}
