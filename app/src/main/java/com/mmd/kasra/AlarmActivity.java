package com.mmd.kasra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AlarmActivity extends AppCompatActivity{

    private int intensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
    }

    @Override
    protected void onResume() {
        super.onResume();
        intensity = this.getIntent().getIntExtra("intensity", 0);
        Intent intent = new Intent(this, AlarmService.class);
        intent.putExtra("intensity", intensity);
        startService(intent);
    }


}
