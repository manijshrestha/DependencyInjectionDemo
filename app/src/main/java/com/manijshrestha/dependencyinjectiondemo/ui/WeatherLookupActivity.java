package com.manijshrestha.dependencyinjectiondemo.ui;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.manijshrestha.dependencyinjectiondemo.R;
import com.manijshrestha.dependencyinjectiondemo.model.WeatherData;
import com.manijshrestha.dependencyinjectiondemo.service.WeatherLookupService;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherLookupActivity extends BaseActivity {

    private static final String TAG = "WeatherLookupActivity";

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
                Observable<WeatherData> observable = mWeatherLookupService.getWeather(mCityNameET.getText().toString());

                observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<WeatherData>() {
                            @Override
                            public void onCompleted() {
                                Log.d(TAG, "Completed");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(getApplicationContext(), "No Data Connectivity!", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onNext(WeatherData weatherData) {
                                WeatherData.MainData mainData = weatherData.getMain();
                                if (mainData != null) {
                                    mTemperatureTV.setText(getString(R.string.temperature_string, mainData.getTemp()));
                                } else {
                                    Toast.makeText(getApplicationContext(), "Check city name", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        NetworkInfo activeNetworkInfo = mConnMgr.getActiveNetworkInfo();
        mSearchBtn.setEnabled(activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting());
    }
}
