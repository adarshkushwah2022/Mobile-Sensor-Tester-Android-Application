package com.mc2022.template;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class bonusTwo extends AppCompatActivity implements SensorEventListener {
    TextView lightSensorStatus, lightSensorValue;
    private SensorManager sensorManager;
    private Sensor lightSensor, proximitySensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_two);

        lightSensorStatus=findViewById(R.id.textView24);
        lightSensorValue=findViewById(R.id.textView25);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (proximitySensor != null) {
            sensorManager.registerListener(bonusTwo.this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
            Toast.makeText(bonusTwo.this, "Proximity Sensor sensor started", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Proximity Sensor Not Available",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if(sensor.getType() == Sensor.TYPE_LIGHT ){
            lightSensorValue.setText("Light Value: "+String.valueOf(sensorEvent.values[0]));
        }else if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float proximityValue = sensorEvent.values[2];
            if(proximityValue > 0){
                //Toast.makeText(bonusTwo.this,"Light Sensor Off",Toast.LENGTH_SHORT).show();
                sensorManager.registerListener(bonusTwo.this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
                lightSensorStatus.setText("Light Sensor On");
            }else{
                //Toast.makeText(bonusTwo.this,"Light Sensor On",Toast.LENGTH_SHORT).show();
                sensorManager.registerListener(bonusTwo.this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
                lightSensorStatus.setText("Light Sensor On");

                sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
                lightSensorStatus.setText("Light Sensor OFF");
                lightSensorValue.setText("Light Value: 0.0");
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