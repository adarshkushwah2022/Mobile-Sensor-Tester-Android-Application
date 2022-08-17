package com.mc2022.template;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertlightSensorValue(lightSensorEntity lightSensorEntities);

    @Insert
    void insertProximitySensorValue(proximitySensorEntity proximitySensorEntities);

    @Insert
    void insertLinearAccerelationSensorValue(linearAccerelationSensorEntity linearAccerelationSensorEntities);

    @Insert
    void insertgyroscopeSensorValue(gyroscopeSensorEntity gyroscopeSensorEntities);

    @Insert
    void insertmagneticFieldSensorValue(magneticFieldSensorEntity magneticFieldSensorEntities);

    @Insert
    void insertambientTemperatureSensorValue(ambientTemperatureSensorEntity ambientTemperatureSensorEntities);

    @Insert
    void insertLocation(gpsLocation gpsLocations);

    @Query("SELECT * FROM proximitySensorEntity ORDER BY uid DESC LIMIT 10 ")
    List<proximitySensorEntity> getProximityList();

    @Query("SELECT * FROM linearAccerelationSensorEntity ORDER BY uid DESC LIMIT 10 ")
    List<linearAccerelationSensorEntity> getLinearAccelerationObjectsList();

    @Query("SELECT * FROM gpsLocation")
    List<gpsLocation> getLocationList();


}