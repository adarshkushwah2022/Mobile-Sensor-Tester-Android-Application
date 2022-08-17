package com.mc2022.template;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class linearAccerelationSensorEntity {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    public linearAccerelationSensorEntity(String time,float xValue,float yValue,float zValue) {
        this.xValue = xValue;
        this.yValue = yValue;
        this.zValue = zValue;
        this.time=time;
    }

    @ColumnInfo(name = "xValue")
    public float xValue;

    @ColumnInfo(name = "yValue")
    public float yValue;

    @ColumnInfo(name = "zValue")
    public float zValue;

    @ColumnInfo(name = "time")
    public String time;
    public float getxValue() {
        return xValue;
    }

    public void setxValue(float xValue) {
        this.xValue = xValue;
    }

    public float getyValue() {
        return yValue;
    }

    public void setyValue(float yValue) {
        this.yValue = yValue;
    }

    public float getzValue() {
        return zValue;
    }

    public void setzValue(float zValue) {
        this.zValue = zValue;
    }
}
