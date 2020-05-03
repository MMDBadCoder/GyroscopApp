package com.mmd.kasra;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Vibrator;

public class AlarmService2 extends Service {
    private final IBinder binder = new LocalBinder();
    private Vibrator vibrator;
    public class LocalBinder extends Binder {
        AlarmService2 getService() {
            // Return this instance of LocalService so clients can call public methods
            return AlarmService2.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void startVibration(){
        if(vibrator == null)
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        final long[] pattern = {1000, 200};
        vibrator.vibrate(pattern, 0);
    }

    public void stopVibration(){
        vibrator.cancel();
    }
}
