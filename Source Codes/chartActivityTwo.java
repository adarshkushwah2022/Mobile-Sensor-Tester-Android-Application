package com.mc2022.template;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

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

public class chartActivityTwo extends AppCompatActivity {

    private LineChart linearAccerelationSensorChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_two);

        AppDatabase database =  AppDatabase.getDatabaseInstance(this);
        List<linearAccerelationSensorEntity> lastTenLinearAccelerationObjects = database.userDao().getLinearAccelerationObjectsList();
        if(lastTenLinearAccelerationObjects.size() <= 9){
            Toast.makeText(this,"Not enough entries",Toast.LENGTH_SHORT).show();
        }else {
            linearAccerelationSensorChart = findViewById(R.id.linearAccerelationChartOne);
            linearAccerelationSensorChart.setPinchZoom(true);
            linearAccerelationSensorChart.setTouchEnabled(true);

            MyMarkerView myCustomMarker = new MyMarkerView(getApplicationContext(), R.layout.custom_marker_view);
            myCustomMarker.setChartView(linearAccerelationSensorChart);
            linearAccerelationSensorChart.setMarker(myCustomMarker);
            createChartAxis();
        }
    }
    public void createChartAxis() {
        XAxis chartXAxis = linearAccerelationSensorChart.getXAxis();
        chartXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chartXAxis.setDrawGridLines(false);
//        chartXAxis.setAxisMaximum(10f);
//        chartXAxis.setAxisMinimum(0f);
        chartXAxis.setAxisLineWidth(1f);
        chartXAxis.setAxisLineColor(R.color.black);
        chartXAxis.setTextColor(R.color.black);

        YAxis chartLeftAxis = linearAccerelationSensorChart.getAxisLeft();
        chartLeftAxis.setDrawGridLines(false);
        chartLeftAxis.setDrawZeroLine(false);
        chartLeftAxis.removeAllLimitLines();
//        chartLeftAxis.setAxisMinimum(0f);
//        chartLeftAxis.setAxisLineWidth(1f);
        chartLeftAxis.setAxisLineColor(R.color.black);
        chartLeftAxis.setTextColor(R.color.black);

        linearAccerelationSensorChart.getAxisRight().setEnabled(false);
        insertDataValuesInChart();
    }

    private void insertDataValuesInChart() {
        ArrayList<Entry> coordinatePoints = new ArrayList<>();
        AppDatabase database =  AppDatabase.getDatabaseInstance(this);
        List<linearAccerelationSensorEntity> lastTenLinearAccelerationObjects = database.userDao().getLinearAccelerationObjectsList();
        Collections.reverse(lastTenLinearAccelerationObjects);
        int i=1;
        for(linearAccerelationSensorEntity pse:lastTenLinearAccelerationObjects){
            float xCoordinateValue = pse.getxValue();
            float yCoordinateValue = pse.getyValue();
            float zCoordinateValue = pse.getzValue();

            float coordinateAverageValue = (xCoordinateValue+yCoordinateValue+zCoordinateValue)/3;
            coordinatePoints.add(new Entry(i,coordinateAverageValue));
            i++;
        }

        LineDataSet coordinateLine;
        if (linearAccerelationSensorChart.getData() != null && linearAccerelationSensorChart.getData().getDataSetCount() > 0) {
            coordinateLine = (LineDataSet) linearAccerelationSensorChart.getData().getDataSetByIndex(0);
            coordinateLine.setValues(coordinatePoints);
            linearAccerelationSensorChart.getData().notifyDataChanged();
            linearAccerelationSensorChart.notifyDataSetChanged();
        } else {
            coordinateLine = new LineDataSet(coordinatePoints, "Average Values Line");
            coordinateLine.setColor(Color.BLACK);
            coordinateLine.setCircleColor(Color.YELLOW);
            coordinateLine.setLineWidth(7f);
            coordinateLine.setCircleRadius(5f);
            coordinateLine.setDrawFilled(false);
            coordinateLine.setValueTextSize(6f);
            coordinateLine.setDrawCircleHole(false);
            ArrayList<ILineDataSet> finalCoordinateDataset = new ArrayList<>();
            finalCoordinateDataset.add(coordinateLine);
            LineData data = new LineData(finalCoordinateDataset);
            linearAccerelationSensorChart.setData(data);
        }
    }
}
