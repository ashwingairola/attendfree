package com.ashwingairola.attendfree;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity
{
    private PendingIntent alarmIntent;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Button addAlarmButton = (Button)this.findViewById(R.id.add_alarm_button);
        Button deleteAlarmButton = (Button)this.findViewById(R.id.delete_alarm_button);

        Intent intent = new Intent(this, AlarmBroadcastReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 24081996, intent, 0);

        preferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if(!preferences.contains("is_alarm_set"))
        {
            editor.putBoolean("is_alarm_set", false);
            editor.commit();
        }
        else if(preferences.getBoolean("is_alarm_set", false))
        {
            addAlarmButton.setEnabled(false);
            deleteAlarmButton.setEnabled(true);
        }
        else
        {
            addAlarmButton.setEnabled(true);
            deleteAlarmButton.setEnabled(false);
        }
    }

    public void onClickBackButton(View view)
    {
        finish();
    }

    public void onClickAddAlarmButton(View view)
    {
        TimePicker timePicker = (TimePicker)this.findViewById(R.id.timePicker);
        int hours = timePicker.getCurrentHour();
        int minutes = timePicker.getCurrentMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);

        preferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("is_alarm_set", true);
        editor.commit();

        Toast.makeText(this, "Alarm set!", Toast.LENGTH_SHORT).show();
        finish();
    }


    public void onClickDeleteAlarmButton(View view)
    {
        alarmIntent.cancel();
        view.setEnabled(false);
        preferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("is_alarm_set", false);
        editor.commit();
        Toast.makeText(this, "Alarm cancelled!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
