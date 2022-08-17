package com.mc2022.template;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class lightSensorNightModeActivity extends AppCompatActivity implements SensorEventListener {
    private Sensor lightSensor;
    private ToggleButton lightSensorToggleButton;
    private SensorManager sensorManager;
    TextView lightValue, nightModeStatus;
    Boolean gateway = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor_night_mode);
        lightSensorToggleButton= findViewById(R.id.toggleButtonTen);
        lightValue=findViewById(R.id.textView19);
        nightModeStatus = findViewById(R.id.textView18);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensorToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                    if (lightSensor != null) {
                        gateway=true;
                        sensorManager.registerListener(lightSensorNightModeActivity.this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Light Sensor Not Available",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(lightSensorNightModeActivity.this, "Light Sensor sensor stopped", Toast.LENGTH_SHORT).show();
                    sensorManager.unregisterListener(lightSensorNightModeActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
                    gateway=false;
                }
            }
        });
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if(!lightSensorToggleButton.isChecked()){

        }else{
            float lightCapacity = (float) sensorEvent.values[0];
            lightValue.setText(String.valueOf(lightCapacity));
            if(lightCapacity<=70){
                nightModeStatus.setText("Night Mode On ");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else {
                nightModeStatus.setText("Night Mode OFF ");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
        gateway=true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(gateway){
            sensorManager.unregisterListener(lightSensorNightModeActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(gateway && !lightSensorToggleButton.isChecked()){
            sensorManager.unregisterListener(lightSensorNightModeActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
        }
    }
}