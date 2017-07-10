package com.ashwingairola.attendfree;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class EnterSubjectsActivity extends AppCompatActivity
{
    private DBHelper dbHelper;
    private int numOfSubjects;
    private TableRow[] rows;
    private EditText[] subjects;
    private EditText[] attended;
    private EditText[] bunked;
    private EditText[] cancelled;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_subjects);

        dbHelper = new DBHelper(this);
        numOfSubjects = dbHelper.getNumOfSubjects();
        rows = new TableRow[numOfSubjects];
        subjects = new EditText[numOfSubjects];
        attended = new EditText[numOfSubjects];
        bunked = new EditText[numOfSubjects];
        cancelled = new EditText[numOfSubjects];

        TableLayout tableLayout = (TableLayout)findViewById(R.id.subjects_table);

        for(int i=0; i<numOfSubjects; ++i)
        {
            rows[i] = new TableRow(this);
            rows[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            subjects[i] = new EditText(this);
            subjects[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            subjects[i].setHint("Subject "+(i+1));

            attended[i] = new EditText(this);
            attended[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            attended[i].setInputType(InputType.TYPE_CLASS_NUMBER);

            bunked[i] = new EditText(this);
            bunked[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            bunked[i].setInputType(InputType.TYPE_CLASS_NUMBER);

            cancelled[i] = new EditText(this);
            cancelled[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            cancelled[i].setInputType(InputType.TYPE_CLASS_NUMBER);

            rows[i].addView(subjects[i]);
            rows[i].addView(attended[i]);
            rows[i].addView(bunked[i]);
            rows[i].addView(cancelled[i]);
            tableLayout.addView(rows[i], new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    public void onClickNextButton(View view)
    {
        int i;
        Toast errorToast;

        for(i=0; i<numOfSubjects; ++i)
        {
            String subject = subjects[i].getText().toString();
            String attend = attended[i].getText().toString();
            String bunk = bunked[i].getText().toString();
            String cancel = cancelled[i].getText().toString();

            if(subject.equals("")||attend.equals("")||bunk.equals("")||cancel.equals(""))
            {
                errorToast = Toast.makeText(this, "Please fill in all the subjects.", Toast.LENGTH_LONG);
                errorToast.show();
                return;
            }
        }

        boolean flag = false;

        for(i=0; i<numOfSubjects; ++i)
        {
            String subject = subjects[i].getText().toString();
            int attend = Integer.parseInt(attended[i].getText().toString());
            int bunk = Integer.parseInt(bunked[i].getText().toString());
            int cancel = Integer.parseInt(cancelled[i].getText().toString());

            flag = dbHelper.addSubject(subject, attend, bunk, cancel);

            if(!flag)
            {
                errorToast = Toast.makeText(this, "Sorry! Subjects could not be updated.", Toast.LENGTH_LONG);
                errorToast.show();
            }
        }

        if(flag)
        {
            SharedPreferences preferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("is_subjects_empty", false);
            editor.commit();

            Intent intent = new Intent(this, DaysActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
