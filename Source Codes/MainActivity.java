package com.mc2022.template;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensorManager;
    private Sensor gyroScopeSensor, lightSensor, magneticFieldSensor, linearAccerelationSensor, proximimtySensor, ambientTemperatureSensor;
    private ToggleButton gyroScopeToggleButton, lightSensorToggleButton, magneticFieldToggleButton, linearAccerelationToggleButton, proximitySensorToggleButton , ambientTemperatureSensorToggleButton;
    private TextView gyroXvalue, gyroYvalue, gyroZvalue, lightSensorValue;
    private TextView magneticFieldXvalue, magneticFieldYvalue, magneticFieldZvalue;
    private TextView linearAccerelationXvalue, linearAccerelationYvalue, linearAccerelationZvalue;
    private TextView proximitySensorValue, ambientTemperatureValue;
    AppDatabase databaseObj;

    Button linearAccerelationChartButton, proximityChartButton, addPlaceButton;
    Button nightNodeButton,motionDetectionButton;
    Button bonusTwo;
    Button bonusOne;

    boolean linearAccerelationSensorStatus = false;
    boolean gyroscopeSensorStatus = false;
    boolean magneticFieldSensorStatus = false;
    boolean proximitySensorStatus = false;
    boolean lightSensorStatus= false;
    boolean ambientTemperatureSensorStatus = false;

//    private LocationManager locationManager;
//    LocationListener mlocListener;
//    double locationLongitude,locationLatitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gyroScopeToggleButton = findViewById(R.id.toggleButtonOne);
        lightSensorToggleButton = findViewById(R.id.toggleButtonTwo);
        magneticFieldToggleButton = findViewById(R.id.toggleButtonThree);
        linearAccerelationToggleButton  = findViewById(R.id.toggleButtonFour);
        proximitySensorToggleButton = findViewById(R.id.toggleButtonFive);
        ambientTemperatureSensorToggleButton = findViewById(R.id.toggleButtonSix);
        addPlaceButton =findViewById(R.id.buttonPlace);
        nightNodeButton = findViewById(R.id.buttonPlaceFour);
        motionDetectionButton = findViewById(R.id.buttonPlaceFourTwo);
        bonusTwo=findViewById(R.id.buttonPlaceFour3);
        bonusOne=findViewById(R.id.buttonPlaceFour4);
        proximityChartButton = findViewById(R.id.buttonOne);
        linearAccerelationChartButton = findViewById(R.id.buttonTwo);
        gyroXvalue = findViewById(R.id.gyroXvalueTextField);
        gyroYvalue = findViewById(R.id.gyroYvalueTextField);
        gyroZvalue = findViewById(R.id.gyroZvalueTextField);
        lightSensorValue = findViewById(R.id.lightSensorValueTextField);
        magneticFieldXvalue = findViewById(R.id.magneticFieldXvalueTextField);
        magneticFieldYvalue = findViewById(R.id.magneticFieldYvalueTextField);
        magneticFieldZvalue = findViewById(R.id.magneticFieldZvalueTextField);
        linearAccerelationXvalue = findViewById(R.id.linearAccerelationXvalueTextField);
        linearAccerelationYvalue = findViewById(R.id.linearAccerelationYvalueTextField);
        linearAccerelationZvalue = findViewById(R.id.linearAccerelationZvalueTextField);
        proximitySensorValue = findViewById(R.id.proximitySensorValueTextField);
        ambientTemperatureValue = findViewById(R.id.ambientTemperatureSensorValueTextField);


        gyroXvalue.setText("0");
        gyroYvalue.setText("0");
        gyroZvalue.setText("0");
        lightSensorValue.setText("0");
        magneticFieldXvalue.setText("0");
        magneticFieldYvalue.setText("0");
        magneticFieldZvalue.setText("0");
        linearAccerelationXvalue.setText("0");
        linearAccerelationYvalue.setText("0");
        linearAccerelationZvalue.setText("0");
        proximitySensorValue.setText("0");
        ambientTemperatureValue.setText("0");

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        databaseObj = AppDatabase.getDatabaseInstance(this);

        gyroScopeToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    gyroScopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                    if (gyroScopeSensor != null) {
                        gyroscopeSensorStatus =true;
                        sensorManager.registerListener(MainActivity.this, gyroScopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, "Gyroscope Sensor sensor started", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Gyroscope Sensor Not Available",Toast.LENGTH_SHORT).show();
                    }
                }else{

                    Stop(1);
                    Toast.makeText(MainActivity.this, "Gyroscope Sensor sensor stopped", Toast.LENGTH_SHORT).show();
                    gyroscopeSensorStatus =false;
                    gyroXvalue.setText("0");
                    gyroYvalue.setText("0");
                    gyroZvalue.setText("0");
                }
            }
        });

        lightSensorToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                    if (lightSensor != null) {
                        lightSensorStatus=true;
                        sensorManager.registerListener(MainActivity.this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, "Light Sensor sensor started", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Light Sensor Not Available",Toast.LENGTH_SHORT).show();
                    }
                }else{
                        Toast.makeText(MainActivity.this, "Light Sensor sensor stopped", Toast.LENGTH_SHORT).show();
                        Stop(2);
                        lightSensorStatus=false;
                        lightSensorValue.setText("0");
                }
            }
        });

        magneticFieldToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    magneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                    if (magneticFieldSensor != null) {
                        magneticFieldSensorStatus=true;
                        sensorManager.registerListener(MainActivity.this, magneticFieldSensor, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, "Magnetic Field Sensor sensor started", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Magnetic Field Sensor Not Available",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Magnetic Field Sensor sensor stopped", Toast.LENGTH_SHORT).show();
                    Stop(3);
                    magneticFieldSensorStatus=false;
                    magneticFieldXvalue.setText("0");
                    magneticFieldYvalue.setText("0");
                    magneticFieldZvalue.setText("0");
                }
            }
        });

        linearAccerelationToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    linearAccerelationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                    if (linearAccerelationSensor != null) {
                        linearAccerelationSensorStatus =true;
                        sensorManager.registerListener(MainActivity.this, linearAccerelationSensor, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, "Linear Acceleration Sensor sensor started", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Linear Acceleration Sensor Not Available",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Linear Acceleration Sensor sensor stopped", Toast.LENGTH_SHORT).show();
                    Stop(4);
                    linearAccerelationSensorStatus =false;
                    linearAccerelationXvalue.setText("0");
                    linearAccerelationYvalue.setText("0");
                    linearAccerelationZvalue.setText("0");
                }
            }
        });

        proximitySensorToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    proximimtySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                    if (proximimtySensor != null) {
                        proximitySensorStatus=true;
                        sensorManager.registerListener(MainActivity.this, proximimtySensor, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, "Proximity Sensor sensor started", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Proximity Sensor Not Available",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Proximity Sensor sensor stopped", Toast.LENGTH_SHORT).show();
                    Stop(5);
                    proximitySensorStatus=false;
                    proximitySensorValue.setText("0");
                }
            }
        });

        ambientTemperatureSensorToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    ambientTemperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                    if (ambientTemperatureSensor != null) {
                        ambientTemperatureSensorStatus = true;
                        sensorManager.registerListener(MainActivity.this, ambientTemperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, "Ambient Temperature Sensor sensor started", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Ambient Temperature Sensor Not Available",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Ambient Temperature Sensor sensor stopped", Toast.LENGTH_SHORT).show();
                    Stop(6);
                    ambientTemperatureSensorStatus = false;
                    ambientTemperatureValue.setText("0");
                }
            }
        });

        proximityChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<proximitySensorEntity> lastTenProximityObjects = databaseObj.userDao().getProximityList();
                if(lastTenProximityObjects.size()<10){
                    Toast.makeText(getApplicationContext(),"Not Enough Proximity Sensor Measurements",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(MainActivity.this, chartActivity.class);
                    startActivity(intent);
                }
            }
        });

        linearAccerelationChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<linearAccerelationSensorEntity> lastTenLinearAccelerationObjects = databaseObj.userDao().getLinearAccelerationObjectsList();
                if(lastTenLinearAccelerationObjects.size()<10){
                    Toast.makeText(getApplicationContext(),"Not Enough Linear Accerelation Measurements",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(MainActivity.this, chartActivityTwo.class);
                    startActivity(intent);
                }
            }
        });

        addPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    Intent intent = new Intent(MainActivity.this, locationActivity.class);
                    startActivity(intent);
                }
            }
        });

        nightNodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, lightSensorNightModeActivity.class);
                startActivity(intent);
            }
        });

        motionDetectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, motionDetectionActivity.class);
                startActivity(intent);
            }
        });

        bonusTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, bonusTwo.class);
                startActivity(intent);
            }
        });

        bonusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, bonusOne.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if(sensor.getType() == Sensor.TYPE_GYROSCOPE ){
            String currentTime = getOriginalCurrentTime();
            gyroXvalue.setText(String.valueOf((float) sensorEvent.values[0]));
            gyroYvalue.setText(String.valueOf((float) sensorEvent.values[1]));
            gyroZvalue.setText(String.valueOf((float) sensorEvent.values[2]));
            new gyroscopeThread(currentTime,(float) sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]).start();
        }else if (sensor.getType() == Sensor.TYPE_LIGHT){
            String currentTime = getOriginalCurrentTime();
            lightSensorValue.setText(String.valueOf((float) sensorEvent.values[0]));
            new lightSensorThread(currentTime,(float) sensorEvent.values[0]).start();
        }else if(sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            String currentTime = getOriginalCurrentTime();
            magneticFieldXvalue.setText(String.valueOf((float) sensorEvent.values[0]));
            magneticFieldYvalue.setText(String.valueOf((float) sensorEvent.values[1]));
            magneticFieldZvalue.setText(String.valueOf((float) sensorEvent.values[2]));
            new magneticFieldSensorThread(currentTime,(float) sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]).start();
        }else if(sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            String currentTime = getOriginalCurrentTime();
            linearAccerelationXvalue.setText(String.valueOf((float) sensorEvent.values[0]));
            linearAccerelationYvalue.setText(String.valueOf((float) sensorEvent.values[1]));
            linearAccerelationZvalue.setText(String.valueOf((float) sensorEvent.values[2]));
            new linearAccerelationThread(currentTime,(float) sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]).start();
        }else if(sensor.getType() == Sensor.TYPE_PROXIMITY){
            String currentTime = getOriginalCurrentTime();
            databaseObj.userDao().insertProximitySensorValue(new proximitySensorEntity(currentTime,(float) sensorEvent.values[0]));
            proximitySensorValue.setText(String.valueOf((float) sensorEvent.values[0]));
            new proximitySensorThread(currentTime,(float) sensorEvent.values[0]).start();
        }else if(sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
            String currentTime = getOriginalCurrentTime();
            ambientTemperatureValue.setText(String.valueOf((float) sensorEvent.values[0]));
            new ambientTemperatureSensorThread(currentTime,(float) sensorEvent.values[0]).start();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor,int i){

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    //   @Override
    protected void Stop(int i) {
        if(i == 2) {
            sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
        }else if(i == 1){
            sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE));
        }else if(i == 3){
            sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
        }else if(i == 4){
            sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION));
        }else if(i == 5){
            sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY));
        }else if(i == 6){
            sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE));
        }
    }

    class linearAccerelationThread extends Thread{
        String time;
        float xp,yp,zp;
        linearAccerelationThread(String time, float xp, float yp, float zp){
            this.xp=xp;
            this.yp=yp;
            this.zp=zp;
            this.time=time;
        }
        @Override
        public void run(){
            if(!linearAccerelationSensorStatus){
                return;
            }
            super.run();
            databaseObj.userDao().insertLinearAccerelationSensorValue(new linearAccerelationSensorEntity(time,xp,yp,zp));
        }
    }

    class gyroscopeThread extends Thread{
        String time;
        float xp,yp,zp;
        gyroscopeThread(String time, float xp, float yp, float zp){
            this.xp=xp;
            this.yp=yp;
            this.zp=zp;
            this.time=time;
        }
        @Override
        public void run(){
            if(!gyroscopeSensorStatus){
                return;
            }
            super.run();
            databaseObj.userDao().insertgyroscopeSensorValue(new gyroscopeSensorEntity(time,xp,yp,zp));
        }
    }

    class lightSensorThread extends Thread{
        String time;
        float lightValue;
        lightSensorThread(String time, float lightValue){
            this.lightValue=lightValue;
            this.time=time;
        }
        @Override
        public void run(){
            if(!lightSensorStatus){
                return;
            }
            super.run();
            databaseObj.userDao().insertlightSensorValue(new lightSensorEntity(time,lightValue));
        }
    }

    class magneticFieldSensorThread extends Thread{
        String time;
        float xp,yp,zp;
        magneticFieldSensorThread(String time, float xp, float yp, float zp){
            this.xp=xp;
            this.yp=yp;
            this.zp=zp;
            this.time=time;
        }
        @Override
        public void run(){
            if(!magneticFieldSensorStatus){
                return;
            }
            super.run();
            databaseObj.userDao().insertmagneticFieldSensorValue(new magneticFieldSensorEntity(time,xp,yp,zp));
        }
    }

    class proximitySensorThread extends Thread{
        String time;
        float proximityValue;
        proximitySensorThread(String time, float proximityValue){
            this.proximityValue=proximityValue;
            this.time=time;
        }
        @Override
        public void run(){
            if(!proximitySensorStatus){
                return;
            }
            super.run();
            databaseObj.userDao().insertProximitySensorValue(new proximitySensorEntity(time,proximityValue));
        }
    }

    class ambientTemperatureSensorThread extends Thread{
        String time;
        float temperatureValue;
        ambientTemperatureSensorThread(String time, float temperatureValue){
            this.temperatureValue=temperatureValue;
            this.time=time;
        }
        @Override
        public void run(){
            if(!ambientTemperatureSensorStatus){
                return;
            }
            super.run();
            databaseObj.userDao().insertambientTemperatureSensorValue(new ambientTemperatureSensorEntity(time,temperatureValue));
        }
    }

    public static String getOriginalCurrentTime(){
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
    }

}