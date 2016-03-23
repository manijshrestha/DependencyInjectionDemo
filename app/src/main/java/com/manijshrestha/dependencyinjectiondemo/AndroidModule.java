package com.manijshrestha.dependencyinjectiondemo;

import android.content.Context;
import android.net.ConnectivityManager;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule {

    private final DemoApplication mApplication;

    public AndroidModule(DemoApplication application) {
        mApplication = application;
    }

    @Provides
    Context providesApplicationContext() {
        return mApplication.getApplicationContext();
    }

    @Provides
    ConnectivityManager providesConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

}
