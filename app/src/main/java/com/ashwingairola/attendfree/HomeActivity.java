package com.ashwingairola.attendfree;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements UpdateConfirmDialogFragment.OnUpdateConfirmListener
{

    private static final String MY_PREFERENCES = "my_preferences";
    private static final String IS_ATTENDANCE_UPDATED = "is_attendance_updated";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        populate();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        populate();
    }

    private void populate()
    {
        DBHelper dbHelper = new DBHelper(this);
        TextView totalAttendance = (TextView)findViewById(R.id.overall_percentage);
        TextView overallStatus = (TextView)findViewById(R.id.overall_status);
        Subject subject = dbHelper.getLowestAttendanceSubject();
        double minPercentage = dbHelper.getMinPercentage();
        String subjectName = subject.getSubjectName();
        double percentage = subject.getPercentage();

        totalAttendance.setText(String.format("%.1f", percentage)+"%");
        overallStatus.setText(subjectName);

        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.status_view);
        if(percentage<minPercentage)
            ;
    }

    public void onClickViewTimeTableButton(View view)
    {
        Intent intent = new Intent(this, ViewTimeTableActivity.class);
        startActivity(intent);
    }

    public void onClickViewSubjectStatusButton(View view)
    {
        Intent intent = new Intent(this, ViewSubjectStatusActivity.class);
        startActivity(intent);
    }

    public void onClickUpdateButton(View view)
    {
        SharedPreferences preferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        if(!preferences.contains(IS_ATTENDANCE_UPDATED))
        {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(IS_ATTENDANCE_UPDATED, false);
            editor.commit();
        }

        boolean isAttendanceUpdated = preferences.getBoolean(IS_ATTENDANCE_UPDATED, false);
        if(isAttendanceUpdated)
        {
            UpdateConfirmDialogFragment dialogFragment = new UpdateConfirmDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "confirm_update");
        }
        else
        {
            openUpdateScreen();
        }
    }

    public void onClickSetReminderButton(View view)
    {
        Intent intent = new Intent(this, AlarmActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClickYesButton()
    {
        openUpdateScreen();
    }

    private void openUpdateScreen()
    {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());

        Intent intent = new Intent(this, UpdateAttendanceActivity.class);
        intent.putExtra(UpdateAttendanceActivity.DAY, day);
        startActivity(intent);
    }
}
