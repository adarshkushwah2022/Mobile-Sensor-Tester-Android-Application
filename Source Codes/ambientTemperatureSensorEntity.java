package com.mc2022.template;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ambientTemperatureSensorEntity {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    public ambientTemperatureSensorEntity(String time,float tempValue) {
        this.tempValue = tempValue;
        this.time=time;
    }

    @ColumnInfo(name = "tempValue")
    public float tempValue;

    @ColumnInfo(name = "time")
    public String time;
}
