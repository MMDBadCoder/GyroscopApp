package com.mmd.kasra;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.util.Log;

public class AlarmService extends IntentService implements SensorEventListener {

    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor sensor;
    private boolean dismissed = false;
    private int dismissNumber, targetDismissNumber;
    private int intensity;
    private String TAG = "AlarmService";

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent: started");
        targetDismissNumber = R.integer.target_dismiss_number;
        if (intent != null) {
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            intensity = intent.getIntExtra("intensity", 0);
            Log.i(TAG, "onHandleIntent: " + intensity);
            startVibration();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float xSpeed = event.values[0];
        float ySpeed = event.values[1];
        float zSpeed = Math.abs(event.values[2]);
        Log.v("Sensor: ", String.format("x: %f\ny: %f\nz: %f", xSpeed, ySpeed, zSpeed));
        if(zSpeed > intensity + 5){
            Log.i("Sensor Exceeds: ", "" + dismissNumber);
            if(!dismissed) {
                if(dismissNumber > 10) {
                    Log.i(TAG, "onSensorChanged: Let's dismiss it");
                    stopVibration();
                    sensorManager.unregisterListener(this);
                    dismissed = true;
                    dismissNumber = 0;
                    stopSelf();
                }
                dismissNumber++;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void startVibration(){
        Log.i(TAG, "startVibration: ");
        if(vibrator == null)
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        final long[] pattern = {1000, 200};
        vibrator.vibrate(pattern, 0);
    }

    public void stopVibration(){
        vibrator.cancel();
    }
}
