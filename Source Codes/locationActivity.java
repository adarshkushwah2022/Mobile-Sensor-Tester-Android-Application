package com.mc2022.template;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class locationActivity extends AppCompatActivity {
    private LocationManager locationManagerOne;
    LocationListener mlocListenerOne;
    AppDatabase myCustomDatabaseObject;

    TextView userNameTextField;
    ToggleButton saveLocationToggleButton;
    String userNameValue;

    private LocationManager locationManagerTwo;
    LocationListener mlocListenerTwo;
    Location locationTwo;
    double locationLongitude, locationLatitude;
    ArrayList<Double> distances = new ArrayList<>();
    ArrayList<gpsLocation> nearestLocs =new ArrayList<>();
    TextView nearestLocationOne, nearestLocationTwo, nearestLocationThree;
    Button show3NearestButton;

    boolean gatewayOne = false;
    boolean gatewayTwo = false;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        myCustomDatabaseObject = AppDatabase.getDatabaseInstance(this);
        userNameTextField =findViewById(R.id.editTextTextPersonName);
        saveLocationToggleButton = findViewById(R.id.toggleButtonPlaceOne);
        nearestLocationOne = findViewById(R.id.textView10);
        nearestLocationTwo = findViewById(R.id.textView11);
        nearestLocationThree = findViewById(R.id.textView12);
        show3NearestButton = findViewById(R.id.button33);

        saveLocationToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    if(userNameTextField.getText().toString().matches("")){
                        Toast.makeText(locationActivity.this, "Please Enter Username", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(locationActivity.this, "Location Saving On", Toast.LENGTH_SHORT).show();
                        userNameValue = userNameTextField.getText().toString();
                        if(ContextCompat.checkSelfPermission(locationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(locationActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(locationActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},1);
                        }
                        gatewayOne=true;
                        locationManagerOne = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        mlocListenerOne = new MyLocationListenerThree();
                        locationManagerOne.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, mlocListenerOne);
                    }
                }else{
                    if(userNameTextField.getText().toString().matches("")){
                        Toast.makeText(locationActivity.this, "Please Enter Username", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(locationActivity.this, "Location Saving Off", Toast.LENGTH_SHORT).show();
                    }
                    gatewayOne=false;
                }
            }
        });


        show3NearestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gatewayTwo=true;
                locationManagerTwo = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                mlocListenerTwo = new MyLocationListenerTwo();
                if (ContextCompat.checkSelfPermission(locationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(locationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(locationActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
                locationManagerTwo.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, mlocListenerTwo);
                if(locationManagerTwo != null){
                    locationTwo = locationManagerTwo.getLastKnownLocation(locationManagerTwo.GPS_PROVIDER);
                    if (locationTwo != null){
                        locationLatitude = locationTwo.getLatitude();
                        locationLongitude = locationTwo.getLongitude();
                    }
                }
                List<gpsLocation> databaseLocationsList = myCustomDatabaseObject.userDao().getLocationList();
                if(databaseLocationsList.size() == 0){
                    Toast.makeText(getApplicationContext(),"Location Database Empty",Toast.LENGTH_SHORT).show();
                }else{
                    if(databaseLocationsList.size() <= 3) {
                        while (!databaseLocationsList.isEmpty()) {
                            double maxDist = Integer.MAX_VALUE;
                            int minIndex = -1;
                            for (int i = 0; i < databaseLocationsList.size(); i++) {
                                gpsLocation obj = databaseLocationsList.get(i);
                                double curDist = distanceCalculator(locationLatitude, obj.getLatitude(), locationLongitude, obj.getLongitude());
                                if (curDist < maxDist) {
                                    minIndex = i;
                                    maxDist = curDist;
                                }
                            }
                            distances.add(maxDist);
                            nearestLocs.add(databaseLocationsList.get(minIndex));
                            databaseLocationsList.remove(minIndex);
                        }
                        if(distances.size()==1){
                            nearestLocationOne.setText(nearestLocs.get(0).getAddress());
                            nearestLocationTwo.setText("");
                            nearestLocationThree.setText("");
                        }else if(distances.size()==2){
                            nearestLocationOne.setText(nearestLocs.get(0).getAddress());
                            nearestLocationTwo.setText(nearestLocs.get(1).getAddress());
                            nearestLocationThree.setText("");
                        }else{
                            nearestLocationOne.setText(nearestLocs.get(0).getAddress());
                            nearestLocationTwo.setText(nearestLocs.get(1).getAddress());
                            nearestLocationThree.setText(nearestLocs.get(2).getAddress());
                        }
                        while(!distances.isEmpty()){
                            distances.remove(0);
                        }
                    } else {
                        int count = 0;
                        while(count < 3){
                            double maxDist = Integer.MAX_VALUE;
                            int minIndex = -1;
                            for (int i = 0; i < databaseLocationsList.size(); i++) {
                                gpsLocation obj = databaseLocationsList.get(i);
                                double curDist = distanceCalculator(locationLatitude, obj.getLatitude(), locationLongitude, obj.getLongitude());
                                if (curDist < maxDist) {
                                    minIndex = i;
                                    maxDist = curDist;
                                }
                            }
                            distances.add(maxDist);
                            nearestLocs.add(databaseLocationsList.get(minIndex));
                            databaseLocationsList.remove(minIndex);
                            count++;
                        }
                        nearestLocationOne.setText(nearestLocs.get(0).getAddress());
                        nearestLocationTwo.setText(nearestLocs.get(1).getAddress());
                        nearestLocationThree.setText(nearestLocs.get(2).getAddress());
                        while(!distances.isEmpty()){
                            distances.remove(0);
                        }
                    }
                }
            }
        });

        }

    public static double distanceCalculator(double firstLatitude, double secondLatitude, double firstLongitude, double secondLongitude) {
        firstLongitude = Math.toRadians(firstLongitude);
        secondLongitude = Math.toRadians(secondLongitude);
        firstLatitude = Math.toRadians(firstLatitude);
        secondLatitude = Math.toRadians(secondLatitude);

        double distLongitude = secondLongitude-firstLongitude;
        double distLatitude = secondLatitude-firstLatitude;
        double valueOne = Math.pow(Math.sin(distLatitude/2),2) + Math.cos(firstLatitude) * Math.cos(secondLatitude) * Math.pow(Math.sin(distLongitude / 2),2);
        double valueTwo = 2 * Math.asin(Math.sqrt(valueOne));
        double thresholdValue = 6371;
        return(valueTwo * thresholdValue);
    }

    class MyLocationListenerThree implements LocationListener{
        @Override
        public void onProviderDisabled(@NonNull String provider) {
            LocationListener.super.onProviderDisabled(provider);
        }

        @Override
        public void onLocationChanged(@NonNull Location location) {
            if(userNameTextField.getText().toString().matches("") || !saveLocationToggleButton.isChecked()){

            }else{
                String myCurrentLocationAddress="";
                List<Address> addressList=null;
                Geocoder geocoder = new Geocoder(locationActivity.this, Locale.getDefault());
                try {
                    addressList= geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    myCurrentLocationAddress = addressList.get(0).getAddressLine(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myCustomDatabaseObject.userDao().insertLocation(new gpsLocation(userNameValue,(location.getLatitude()), location.getLongitude(), myCurrentLocationAddress));
            }
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }


    class MyLocationListenerTwo implements LocationListener{
        @Override
        public void onProviderDisabled(@NonNull String provider) {
            LocationListener.super.onProviderDisabled(provider);
        }

        @Override
        public void onLocationChanged(@NonNull Location location) {

        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }}

    @Override
    protected void onPause(){
        super.onPause();
        if(gatewayOne){
            locationManagerOne.removeUpdates(mlocListenerOne);
        }
        if(gatewayTwo){
            locationManagerTwo.removeUpdates(mlocListenerTwo);
        }
    }
}