package com.mmd.kasra;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GyroLabActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private boolean color = false;


    private List<String> items_TYPE_GYROSCOPE;
    private ListView listview_TYPE_GYROSCOPE;
    private ArrayAdapter<String> adapter_TYPE_GYROSCOPE;

    private List<String> items_TYPE_GYROSCOPE_UNCALIBRATED;
    private ListView listview_TYPE_GYROSCOPE_UNCALIBRATED;
    private ArrayAdapter<String> adapter_TYPE_GYROSCOPE_UNCALIBRATED;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyro_lab);

        listview_TYPE_GYROSCOPE = findViewById(R.id.listview_TYPE_GYROSCOPE);
        items_TYPE_GYROSCOPE = new ArrayList<>();

        listview_TYPE_GYROSCOPE_UNCALIBRATED = findViewById(R.id.listview_TYPE_GYROSCOPE_UNCALIBRATED);
        items_TYPE_GYROSCOPE_UNCALIBRATED = new ArrayList<>();

        refreshDisplay();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float[] values = event.values;

            float x = values[0];
            float y = values[1];
            float z = values[2];

            items_TYPE_GYROSCOPE = new ArrayList<>();
            items_TYPE_GYROSCOPE.add("TYPE_GYROSCOPE");
            items_TYPE_GYROSCOPE.add("Rate of rotation around the x axis: " + String.valueOf(x));
            items_TYPE_GYROSCOPE.add("Rate of rotation around the y axis: " + String.valueOf(y));
            items_TYPE_GYROSCOPE.add("Rate of rotation around the z axis: " + String.valueOf(z));

            adapter_TYPE_GYROSCOPE.notifyDataSetChanged();
            refreshDisplay();
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE_UNCALIBRATED) {
            float[] values = event.values;

            float x = values[0];
            float y = values[1];
            float z = values[2];

            items_TYPE_GYROSCOPE_UNCALIBRATED = new ArrayList<>();
            items_TYPE_GYROSCOPE_UNCALIBRATED.add("GYROSCOPE_UNCALIBRATED");
            items_TYPE_GYROSCOPE_UNCALIBRATED.add("Rate of rotation (without drift compensation) around the x axis: " + String.valueOf(x));
            items_TYPE_GYROSCOPE_UNCALIBRATED.add("Rate of rotation (without drift compensation) around the x axis: " + String.valueOf(y));
            items_TYPE_GYROSCOPE_UNCALIBRATED.add("Rate of rotation (without drift compensation) around the x axis: " + String.valueOf(z));
            items_TYPE_GYROSCOPE_UNCALIBRATED.add("Estimated drift around the x axis: " + String.valueOf(values[3]));
            items_TYPE_GYROSCOPE_UNCALIBRATED.add("Estimated drift around the y axis: " + String.valueOf(values[4]));
            items_TYPE_GYROSCOPE_UNCALIBRATED.add("Estimated drift around the z axis: " + String.valueOf(values[5]));

            adapter_TYPE_GYROSCOPE_UNCALIBRATED.notifyDataSetChanged();
            refreshDisplay();
        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshDisplay();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    private void refreshDisplay() {
        adapter_TYPE_GYROSCOPE = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items_TYPE_GYROSCOPE);
        listview_TYPE_GYROSCOPE.setAdapter(adapter_TYPE_GYROSCOPE);


        adapter_TYPE_GYROSCOPE_UNCALIBRATED = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items_TYPE_GYROSCOPE_UNCALIBRATED);
        listview_TYPE_GYROSCOPE_UNCALIBRATED.setAdapter(adapter_TYPE_GYROSCOPE_UNCALIBRATED);
    }

}




