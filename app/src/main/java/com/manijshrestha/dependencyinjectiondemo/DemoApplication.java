package com.manijshrestha.dependencyinjectiondemo;

import android.app.Application;

import com.manijshrestha.dependencyinjectiondemo.ui.WeatherLookupActivity;

public class DemoApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .androidModule(new AndroidModule(this))
                .build();
    }

    public void inject(Object activity) {
        if (activity instanceof WeatherLookupActivity) {
            mAppComponent.inject((WeatherLookupActivity) activity);
        }
    }
}
