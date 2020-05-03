package com.mmd.kasra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.widget.Button;

public class VibrationRecognitionActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonStart;
    private Button buttonStop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibration_recognition);
        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);

        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == buttonStart) {
            startService(new Intent(this, MediaService.class));
        } else if (view == buttonStop) {
            stopService(new Intent(this, MediaService.class));
        }
    }

}
