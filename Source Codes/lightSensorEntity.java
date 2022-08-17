package com.mc2022.template;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class lightSensorEntity {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    public lightSensorEntity(String time,float lightValue) {
        this.lightValue = lightValue;
        this.time=time;
    }

    @ColumnInfo(name = "lightValue")
    public float lightValue;

    @ColumnInfo(name = "time")
    public String time;
}
