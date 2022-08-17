package com.mc2022.template;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class proximitySensorEntity {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    public proximitySensorEntity(String time,float xValue) {
        this.xValue = xValue;

        this.time=time;
    }

    @ColumnInfo(name = "xValue")
    public float xValue;

    @ColumnInfo(name = "time")
    public String time;

    public float getxValue() {
        return xValue;
    }

    public void setxValue(float xValue) {
        this.xValue = xValue;
    }
}
