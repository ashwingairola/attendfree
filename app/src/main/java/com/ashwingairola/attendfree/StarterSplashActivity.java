package com.ashwingairola.attendfree;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;

public class StarterSplashActivity extends AppCompatActivity
{
    private static final int SPLASH_TIME_OUT = 3000;
    private static final String MY_PREFERENCES = "my_preferences";
    private static final String IS_USER_EMPTY = "is_user_empty";
    private static final String IS_TIMETABLE_EMPTY = "is_timetable_empty";
    private static final String IS_SUBJECTS_EMPTY = "is_subjects_empty";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                SharedPreferences preferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                Intent intent;
                boolean isUserEmpty, isTimeTableEmpty, isSubjectsEmpty;
                if(!(preferences.contains(IS_USER_EMPTY) && preferences.contains(IS_TIMETABLE_EMPTY) && preferences.contains(IS_SUBJECTS_EMPTY)))
                {
                    editor.putBoolean(IS_USER_EMPTY, true);
                    editor.putBoolean(IS_TIMETABLE_EMPTY, true);
                    editor.putBoolean(IS_SUBJECTS_EMPTY, true);
                    editor.commit();
                    intent = new Intent(StarterSplashActivity.this, StarterActivity.class);
                    startActivity(intent);
                }
                else
                {
                    isUserEmpty = preferences.getBoolean(IS_USER_EMPTY, false);
                    isTimeTableEmpty = preferences.getBoolean(IS_TIMETABLE_EMPTY, false);
                    isSubjectsEmpty = preferences.getBoolean(IS_SUBJECTS_EMPTY, false);

                    if(isUserEmpty)
                    {
                        intent = new Intent(StarterSplashActivity.this, StarterActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(isSubjectsEmpty)
                    {
                        intent = new Intent(StarterSplashActivity.this, EnterSubjectsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(isTimeTableEmpty)
                    {
                        intent = new Intent(StarterSplashActivity.this, DaysActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        intent = new Intent(StarterSplashActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
