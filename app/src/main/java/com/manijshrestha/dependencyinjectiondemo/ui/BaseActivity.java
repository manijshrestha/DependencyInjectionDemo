package com.manijshrestha.dependencyinjectiondemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.manijshrestha.dependencyinjectiondemo.DemoApplication;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DemoApplication application = (DemoApplication) getApplication();
        application.inject(this);
    }
}
