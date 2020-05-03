package com.mmd.kasra.ui.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.mmd.kasra.AlarmActivity;
import com.mmd.kasra.R;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    //    private HomeViewModel homeViewModel;
//    private SensorManager sensorManager;
//    private Sensor sensor;
    private TextView textView;
    private TimePickerDialog picker;
    private Switch alarmSwitch;
    private SeekBar seekBar;

    private int hour = 12, min = 30;
    private int intensity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        alarmSwitch = root.findViewById(R.id.alarm_switch);
        textView = root.findViewById(R.id.time_textView);
        seekBar = root.findViewById(R.id.intensity_seekbar);
        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setAlarm();
                } else {
                    unsetAlarm();
                }
            }
        });
        textView.setText(String.format("%2d:%2d", hour, min));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TextView CL: ", "hello");
                picker = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                textView.setText(String.format("%d:%d", hourOfDay, minute));
                                hour = hourOfDay;
                                min = minute;
                            }
                        }, hour, min, true);
                picker.show();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(!alarmSwitch.isChecked())
                    return;
                unsetAlarm();
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(!alarmSwitch.isChecked())
                    return;
                setAlarm();
            }
        });
//        textView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Intent intent = new Intent(getContext(), AlarmActivity.class);
//                startActivity(intent);
//                return false;
//            }
//        });
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        textView.setText(sensorList.get(0).toString());
        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
//        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
//        sensorManager.unregisterListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setAlarm() {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmActivity.class);
        intent.putExtra("intensity", seekBar.getProgress());
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext().getApplicationContext(), 11, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        Log.i("In Set Alarm: ", "" + hour + " : " + min);
//                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 2, pendingIntent);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void unsetAlarm() {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext().getApplicationContext(), 11, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
//
//    @Override
//    public void onSensorChanged(SensorEvent event) {
////        float axisX = event.values[0];
////        float axisY = event.values[1];
////        float axisZ = event.values[2];
////        String string = String.format("axis X rotational speed: %f\naxis Y rotational speed: %f\naxis Z rotational speed: %f\n", axisX, axisY, axisZ);
////        textView.setText(string);
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//    }
}
