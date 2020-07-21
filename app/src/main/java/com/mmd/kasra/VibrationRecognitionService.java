package com.mmd.kasra;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;

import androidx.annotation.Nullable;


class MediaService extends Service implements SensorEventListener {
    private MediaPlayer player;
    private float sensitivity;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Salam");
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        sensitivity = Float.parseFloat(intent.getExtras().getString("sensitivity"));

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }


    private void turnOnScreen() {
        PowerManager TempPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock TempWakeLock = TempPowerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "myapp:VibrationRecognition");
        TempWakeLock.acquire();
        TempWakeLock.release();
    }

    private float calculateVibrationRate(SensorEvent event) {
        float sumOfSquares = 0;
        for (float value : event.values) {
            sumOfSquares += value * value;
        }
        return (float) Math.sqrt(sumOfSquares);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float vibrationRate = calculateVibrationRate(event);
        if (vibrationRate > sensitivity) {
            turnOnScreen();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

}