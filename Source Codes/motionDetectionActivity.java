package com.mc2022.template;

import androidx.appcompat.app.AppCompatActivity;

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

public class motionDetectionActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor linearAccerelationSensor;
    ToggleButton linearAccerelationToggleButton;
    private TextView linearAccerelationXvalue, linearAccerelationYvalue, linearAccerelationZvalue;
    private TextView motionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_detection);
        linearAccerelationToggleButton = findViewById(R.id.toggleButton20);
        motionStatus=findViewById(R.id.textView21);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        linearAccerelationToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    linearAccerelationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                    if (linearAccerelationSensor != null) {

                        sensorManager.registerListener(motionDetectionActivity.this, linearAccerelationSensor, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(motionDetectionActivity.this, "Linear Acceleration Sensor sensor started", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Linear Acceleration Sensor Not Available",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(motionDetectionActivity.this, "Linear Acceleration Sensor sensor stopped", Toast.LENGTH_SHORT).show();
                    sensorManager.unregisterListener(motionDetectionActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION));
                }
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
       float xDirectionValue = sensorEvent.values[0];
       float yDirectionValue = sensorEvent.values[1];
       float zDirectionValue = sensorEvent.values[2];

       double motionResultantValue = Math.sqrt(xDirectionValue*xDirectionValue +
                                                yDirectionValue*yDirectionValue +
                                                    zDirectionValue*zDirectionValue);
       if(motionResultantValue > 0.7){
           motionStatus.setText("Motion Detected");
       }else{
           motionStatus.setText("Not in Motion");
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