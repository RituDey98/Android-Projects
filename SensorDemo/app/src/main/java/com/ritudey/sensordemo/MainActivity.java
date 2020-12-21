package com.ritudey.sensordemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    Sensor accelerometer,gyroscope,magnetometer,light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
       accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

       sensorManager.registerListener(MainActivity.this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
       Log.i("registered sensor","accelerometer");


    }

    @Override
    public void onSensorChanged(SensorEvent event) {



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}