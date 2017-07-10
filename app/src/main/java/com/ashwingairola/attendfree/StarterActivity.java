package com.ashwingairola.attendfree;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StarterActivity extends AppCompatActivity implements TextWatcher
{

    private static final String MY_PREFERENCES = "my_preferences";
    private EditText txtName;
    private EditText txtInstitution;
    private EditText txtDesignation;
    private EditText txtNumOfSubjects;
    private EditText txtClassesPerDay;
    private EditText txtMinPercentage;
    private Button btnNext;
    private Toast errorToast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);

        txtName = (EditText)findViewById(R.id.username);
        txtInstitution = (EditText)findViewById(R.id.institution);
        txtDesignation = (EditText)findViewById(R.id.designation);
        txtNumOfSubjects = (EditText)findViewById(R.id.num_of_subjects);
        txtClassesPerDay = (EditText)findViewById(R.id.classes_per_day);
        txtMinPercentage = (EditText)findViewById(R.id.min_percentage);
        btnNext = (Button)findViewById(R.id.next_button);

        txtName.addTextChangedListener(this);
        txtInstitution.addTextChangedListener(this);
        txtDesignation.addTextChangedListener(this);
        txtNumOfSubjects.addTextChangedListener(this);
        txtClassesPerDay.addTextChangedListener(this);
        txtMinPercentage.addTextChangedListener(this);
    }

    public void onTextChanged(CharSequence s, int start, int before, int counter)
    {
        check();
    }

    public void beforeTextChanged(CharSequence s, int start, int before, int counter)
    {

    }

    public void afterTextChanged(Editable s)
    {

    }

    private void check()
    {
        boolean textFieldsEmpty = (txtName.getText().toString()=="")||(txtClassesPerDay.getText().toString()=="")||(txtNumOfSubjects.getText().toString()=="");
        if(!textFieldsEmpty)
            btnNext.setEnabled(true);
        else
            btnNext.setEnabled(false);
    }

    public void onClickResetButton(View view)
    {
        txtName.setText(null);
        txtClassesPerDay.setText(null);
        txtNumOfSubjects.setText(null);
        txtInstitution.setText(null);
        txtDesignation.setText(null);
        txtMinPercentage.setText(null);
        btnNext.setEnabled(false);
    }

    public void onClickNextButton(View view)
    {
        String username = txtName.getText().toString();
        String institution = txtInstitution.getText().toString();
        String designation = txtDesignation.getText().toString();
        int numOfSubjects = Integer.parseInt(txtNumOfSubjects.getText().toString());
        int classesPerDay = Integer.parseInt(txtClassesPerDay.getText().toString());
        double minPercentage = Double.parseDouble(txtMinPercentage.getText().toString());

        DBHelper dbHelper = new DBHelper(this);
        if(dbHelper.getNumOfRows()<1)
        {
            boolean flag = dbHelper.insertUserDetails(username, institution, designation, numOfSubjects, classesPerDay, minPercentage);

            if(flag)
            {
                SharedPreferences preferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("is_user_empty", false);
                editor.commit();

                Intent intent = new Intent(this, EnterSubjectsActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                errorToast = Toast.makeText(this, "Sorry! Your details could not be entered.", Toast.LENGTH_LONG);
                errorToast.show();
            }
        }
        else
        {
            errorToast = Toast.makeText(this, "Sorry! There is already a user for this app.", Toast.LENGTH_LONG);
            errorToast.show();
        }
    }
}
