package com.mc2022.template;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class chartActivity extends AppCompatActivity {
    private LineChart proximitySensorChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        proximitySensorChart = findViewById(R.id.proximityChartOne);
        proximitySensorChart.setPinchZoom(true);
        proximitySensorChart.setTouchEnabled(true);
        MyMarkerView myCustomMarker = new MyMarkerView(getApplicationContext(), R.layout.custom_marker_view);
        myCustomMarker.setChartView(proximitySensorChart);
        proximitySensorChart.setMarker(myCustomMarker);
        createChartAxis();
    }
    public void createChartAxis() {

        XAxis chartXAxis = proximitySensorChart.getXAxis();
        chartXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chartXAxis.setDrawGridLines(false);
        chartXAxis.setAxisMaximum(10f);
        chartXAxis.setAxisMinimum(0f);
        chartXAxis.setAxisLineWidth(1f);
        chartXAxis.setAxisLineColor(R.color.black);
        chartXAxis.setTextColor(R.color.black);

        YAxis chartLeftAxis = proximitySensorChart.getAxisLeft();
        chartLeftAxis.setDrawGridLines(false);
        chartLeftAxis.setDrawZeroLine(false);
        chartLeftAxis.removeAllLimitLines();
        chartLeftAxis.setAxisMaximum(12f);
        chartLeftAxis.setAxisMinimum(0f);
        chartLeftAxis.setAxisLineWidth(1f);
        chartLeftAxis.setAxisLineColor(R.color.black);
        chartLeftAxis.setTextColor(R.color.black);
        proximitySensorChart.getAxisRight().setEnabled(false);
        insertDataValuesInChart();
    }

    private void insertDataValuesInChart() {
        ArrayList<Entry> coordinatePoints = new ArrayList<>();
        AppDatabase database =  AppDatabase.getDatabaseInstance(this);
        List<proximitySensorEntity> lastTenProximityObjects = database.userDao().getProximityList();
        Collections.reverse(lastTenProximityObjects);
        int i=1;
        for(proximitySensorEntity pse:lastTenProximityObjects){
            coordinatePoints.add(new Entry(i,pse.getxValue()));
            i++;
        }

        LineDataSet coordinateLine;
        if (proximitySensorChart.getData() != null && proximitySensorChart.getData().getDataSetCount() > 0) {
            coordinateLine = (LineDataSet) proximitySensorChart.getData().getDataSetByIndex(0);
            coordinateLine.setValues(coordinatePoints);
            proximitySensorChart.getData().notifyDataChanged();
            proximitySensorChart.notifyDataSetChanged();
        } else {
            coordinateLine = new LineDataSet(coordinatePoints, "Proximity Values Line");
            coordinateLine.setColor(Color.BLACK);
            coordinateLine.setCircleColor(Color.RED);
            coordinateLine.setLineWidth(7f);
            coordinateLine.setCircleRadius(5f);
            coordinateLine.setDrawFilled(false);
            coordinateLine.setValueTextSize(12f);
            coordinateLine.setDrawCircleHole(false);
            ArrayList<ILineDataSet> finalCoordinateDataset = new ArrayList<>();
            finalCoordinateDataset.add(coordinateLine);
            LineData data = new LineData(finalCoordinateDataset);
            proximitySensorChart.setData(data);
        }
    }
}
