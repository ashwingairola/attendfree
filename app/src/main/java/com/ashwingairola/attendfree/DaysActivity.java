package com.ashwingairola.attendfree;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DaysActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days);
    }

    public void onClickDayButton(View view)
    {
        Button clickedButton = (Button)view;
        Intent intent = new Intent(this, CreateTimeTableActivity.class);
        intent.putExtra(CreateTimeTableActivity.DAY, clickedButton.getText().toString());
        startActivity(intent);
    }

    public void onClickNextButton(View view)
    {
        SharedPreferences preferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("is_timetable_empty", false);
        editor.commit();

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
