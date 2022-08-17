package com.mc2022.template;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class bonusOne extends AppCompatActivity implements SensorEventListener {
    TextView linearAccerelationSensorStatus, linearAccerelationSensorValue;
    private SensorManager sensorManager;
    private Sensor linearAccerelationSensor, proximitySensor;
    ToggleButton linearAccerelationSensorToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_one);

        linearAccerelationSensorStatus =findViewById(R.id.textView24);
        linearAccerelationSensorValue =findViewById(R.id.textView25);
        linearAccerelationSensorToggleButton = findViewById(R.id.toggleButtonThirty);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(bonusOne.this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        linearAccerelationSensorToggleButton.setVisibility(View.INVISIBLE);
        linearAccerelationSensorToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    linearAccerelationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                    if (linearAccerelationSensor != null) {
                        sensorManager.registerListener(bonusOne.this, linearAccerelationSensor, SensorManager.SENSOR_DELAY_NORMAL);
                        //Toast.makeText(bonusOne.this, "Light Sensor sensor started", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //Toast.makeText(getApplicationContext(),"Light Sensor Not Available",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(bonusOne.this, "Light Sensor sensor stopped", Toast.LENGTH_SHORT).show();
                    sensorManager.unregisterListener(bonusOne.this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
                    linearAccerelationSensorValue.setText("Light Sensor Value Unavailable");
                    linearAccerelationSensorStatus.setText("Light Sensor Status: OFF");
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if(sensor.getType() == Sensor.TYPE_LIGHT ){
            float val = sensorEvent.values[0];
            linearAccerelationSensorValue.setText("Light Sensor Value: "+String.valueOf(val));
            linearAccerelationSensorStatus.setText("Light Sensor Status: On");
        }else if(sensor.getType() == Sensor.TYPE_PROXIMITY){
            float val = sensorEvent.values[0];
            if(val < 1 ){
                if(linearAccerelationSensorToggleButton.isChecked())
                    linearAccerelationSensorToggleButton.toggle();
                else{
                    if(!linearAccerelationSensorToggleButton.isChecked()){
                        linearAccerelationSensorToggleButton.toggle();
                    }
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}