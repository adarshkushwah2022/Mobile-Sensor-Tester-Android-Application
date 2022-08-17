package com.mc2022.template;

import android.widget.Button;
import android.widget.TextView;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class gpsLocation {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    public gpsLocation(String userName,double latitude,double longitude,String Address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.userName = userName;
        this.Address=Address;
    }

    @ColumnInfo(name = "latitude")
    public double latitude;

    @ColumnInfo(name = "longitude")
    public double longitude;

    @ColumnInfo(name = "userName")
    public String userName;

    @ColumnInfo(name = "Address")
    public String Address;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
