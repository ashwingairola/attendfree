package com.ashwingairola.attendfree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateTimeTableActivity extends AppCompatActivity
{
    public static final String DAY = "day";
    private ArrayList<SubjectSelectorFragment> fragmentList;
    private int classesPerDay;
    private String day;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_time_table);

        Intent intent = getIntent();
        day = intent.getStringExtra(DAY);
        DBHelper dbHelper = new DBHelper(this);

        TextView textView = (TextView)findViewById(R.id.please_enter_schedule);
        String message = textView.getText().toString();
        message += " "+day;
        textView.setText(message);

        classesPerDay = dbHelper.getClassesPerDay();

        fragmentList = new ArrayList<>();
        for(int i=0; i<classesPerDay; ++i)
        {
            SubjectSelectorFragment fragment = new SubjectSelectorFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.select_subjects_layout, fragment);
            transaction.commit();

            fragment.setClassNo(i+1);
            fragmentList.add(fragment);
        }
    }

    public void onClickNextButton(View view)
    {
        ArrayList<String> subjects = new ArrayList<>();
        for(int i=0; i<classesPerDay; ++i)
        {
            SubjectSelectorFragment fragment = fragmentList.get(i);
            Spinner spinner = (Spinner)fragment.getView().findViewById(R.id.class_name);
            String selected = spinner.getSelectedItem().toString();
            subjects.add(selected);
        }

        DBHelper dbHelper = new DBHelper(this);
        boolean flag = dbHelper.insertTimeTableDetails(subjects, day);

        if(flag)
        {
            Toast successToast = Toast.makeText(this, "Success!", Toast.LENGTH_LONG);
            successToast.show();
            finish();
        }
        else
        {
            Toast errorToast = Toast.makeText(this, "Sorry! Couldn't add subjects to the time-table.", Toast.LENGTH_LONG);
            errorToast.show();
        }
    }
}
