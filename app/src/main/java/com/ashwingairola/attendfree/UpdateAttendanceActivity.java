package com.ashwingairola.attendfree;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class UpdateAttendanceActivity extends AppCompatActivity implements AddClassDialogFragment.AddClassDialogListener, UpdateAttendanceDialogFragment.UpdateAttendanceDialogListener
{
    private static final String MY_PREFERENCES = "my_preferences";
    private static final String IS_ATTENDANCE_UPDATED = "is_attendance_updated";
    public static final String DAY = "day";

    private ArrayList<AttendanceFragment> fragmentArrayList;
    private ArrayList<String> schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_attendance);

        populate();
    }

    private void populate()
    {
        fragmentArrayList = new ArrayList<>();
        Intent intent = getIntent();
        String day = intent.getStringExtra(DAY);
        DBHelper dbHelper = new DBHelper(this);
        ArrayList<String> periods = dbHelper.getSchedule(day);
        schedule = new ArrayList<>();

        for(int i=0; i<periods.size(); ++i)
        {
            String period = periods.get(i);
            if(!(period==null || period.equalsIgnoreCase("<None>")))
            {
                schedule.add(period);
            }
        }

        for(int i=0; i<schedule.size(); ++i)
        {
            String subjectName = schedule.get(i);
            AttendanceFragment fragment = new AttendanceFragment();
            fragment.setSubject(subjectName);
            fragmentArrayList.add(fragment);
            addFragment(fragment);
        }
    }

    private void addFragment(AttendanceFragment fragment)
    {
        int containerViewId = R.id.schedule;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    public void onClickBackButton(View view)
    {
        finish();
    }

    public void onClickAddButton(View view)
    {
        AddClassDialogFragment dialogFragment = new AddClassDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "add_class");
    }

    @Override
    public void onDialogPositiveClick(String addedClass)
    {

        AttendanceFragment fragment = new AttendanceFragment();
        fragment.setSubject(addedClass);
        fragmentArrayList.add(fragment);
        schedule.add(addedClass);
        addFragment(fragment);
    }

    public void onClickDoneButton(View view)
    {
        for(AttendanceFragment fragment:fragmentArrayList)
        {
            View root = fragment.getView();
            RadioButton rbAttended = (RadioButton)root.findViewById(R.id.attended);
            RadioButton rbBunked = (RadioButton)root.findViewById(R.id.bunked);
            RadioButton rbCancelled = (RadioButton)root.findViewById(R.id.cancelled);

            if(!rbAttended.isChecked() && !rbBunked.isChecked() && !rbCancelled.isChecked())
            {
                UpdateErrorDialogFragment dialogFragment = new UpdateErrorDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "error");
                return;
            }
            else
            {
                UpdateAttendanceDialogFragment dialogFragment = new UpdateAttendanceDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "update_attendance");
                return;
            }
        }
    }

    @Override
    public void onClickUpdateButton()
    {
        ArrayList<String> statusList = new ArrayList<>();
        for(int i=0; i<fragmentArrayList.size(); ++i)
        {
            AttendanceFragment fragment = fragmentArrayList.get(i);
            View root = fragment.getView();

            RadioButton rbAttended = (RadioButton)root.findViewById(R.id.attended);
            RadioButton rbBunked = (RadioButton)root.findViewById(R.id.bunked);
            RadioButton rbCancelled = (RadioButton)root.findViewById(R.id.cancelled);

            String status = (rbAttended.isChecked())?rbAttended.getText().toString()
                    :(rbBunked.isChecked())?rbBunked.getText().toString()
                    :(rbCancelled.isChecked())?rbCancelled.getText().toString()
                    :"";
            statusList.add(status);
        }

        DBHelper dbHelper = new DBHelper(this);
        boolean flag = dbHelper.updateAttendance(schedule, statusList);
        Toast toast;
        if(flag)
        {
            SharedPreferences preferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(IS_ATTENDANCE_UPDATED, true);
            editor.commit();

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 50);

            System.out.println(System.currentTimeMillis());
            System.out.println(calendar.getTimeInMillis());

            Intent intent = new Intent(this, UpdateAttendanceReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 7121999, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager = (AlarmManager)this.getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

            toast = Toast.makeText(this, "Attendance Updated for Today!", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
        else
        {
            toast = Toast.makeText(this, "Sorry! Attendance could not be updated.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
