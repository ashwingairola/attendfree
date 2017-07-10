package com.ashwingairola.attendfree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class ViewTimeTableActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_time_table);
    }

    public void onClickDayButton(View view)
    {
        String day = ((Button)view).getText().toString();
        Intent intent = new Intent(this, TimeTableActivity.class);
        intent.putExtra(TimeTableActivity.DAY, day);
        startActivity(intent);
    }

    public void onClickBackButton(View view)
    {
        finish();
    }
}
