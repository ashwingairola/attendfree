package com.ashwingairola.attendfree;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class TimeTableActivity extends AppCompatActivity
{
    public static final String DAY = "day";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        String day = getIntent().getStringExtra(DAY);
        TextView dayView = (TextView)findViewById(R.id.day_view);
        dayView.setText(day);

        DBHelper dbHelper = new DBHelper(this);
        ArrayList<String> schedule = dbHelper.getSchedule(day);

        for(int i=0; i<schedule.size(); ++i)
        {
            ShowClassFragment fragment = new ShowClassFragment();
            fragment.setClassNo(i+1);
            fragment.setClassName(schedule.get(i));
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.schedule, fragment);
            transaction.commit();
        }
    }

    public void onClickBackButton(View view)
    {
        finish();
    }
}
