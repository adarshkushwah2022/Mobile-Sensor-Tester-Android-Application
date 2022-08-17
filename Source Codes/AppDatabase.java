package com.mc2022.template;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {lightSensorEntity.class, proximitySensorEntity.class,linearAccerelationSensorEntity.class,gyroscopeSensorEntity.class,magneticFieldSensorEntity.class,ambientTemperatureSensorEntity.class,gpsLocation.class}, version = 35)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public static AppDatabase myDatabaseObj;

    public static AppDatabase getDatabaseInstance(Context context){
        if(myDatabaseObj == null){
            myDatabaseObj = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,"myCustomDatabaseFinal").
                            fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return myDatabaseObj;
    }
}
