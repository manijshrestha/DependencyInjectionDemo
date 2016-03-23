package com.manijshrestha.dependencyinjectiondemo.ui;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.manijshrestha.dependencyinjectiondemo.R;
import com.manijshrestha.dependencyinjectiondemo.model.WeatherData;
import com.manijshrestha.dependencyinjectiondemo.service.WeatherLookupListener;
import com.manijshrestha.dependencyinjectiondemo.service.WeatherLookupService;

import javax.inject.Inject;

public class WeatherLookupActivity extends BaseActivity implements WeatherLookupListener {

    EditText mCityNameET;
    TextView mTemperatureTV;
    Button mSearchBtn;

    @Inject
    WeatherLookupService mWeatherLookupService;

    @Inject
    ConnectivityManager mConnMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_lookup);

        mCityNameET = (EditText) findViewById(R.id.city_name);
        mTemperatureTV = (TextView) findViewById(R.id.temperature);
        mSearchBtn = (Button) findViewById(R.id.lookup);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeatherLookupService.getWeather(mCityNameET.getText().toString(), WeatherLookupActivity.this);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        NetworkInfo activeNetworkInfo = mConnMgr.getActiveNetworkInfo();
        mSearchBtn.setEnabled(activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting());
    }

    @Override
    public void onWeatherData(WeatherData weatherData) {
        WeatherData.MainData mainData = weatherData.getMain();
        if (mainData != null) {
            mTemperatureTV.setText(getString(R.string.temperature_string, mainData.getTemp()));
        } else {
            Toast.makeText(this, "Check city name", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNoWeatherData() {
        Toast.makeText(this, "Could not get data :(", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNoConnectivity() {
        Toast.makeText(this, "No Data Connectivity!", Toast.LENGTH_LONG).show();
    }
}
