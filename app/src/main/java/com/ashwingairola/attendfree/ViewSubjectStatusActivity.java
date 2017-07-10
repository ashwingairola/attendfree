package com.ashwingairola.attendfree;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewSubjectStatusActivity extends AppCompatActivity implements SubjectStatusFragment.OnStatusFragmentClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subject_status);

        DBHelper dbHelper = new DBHelper(this);
        double minPercentage = dbHelper.getMinPercentage();
        ArrayList<Subject> subjects = dbHelper.getAllSubjectDetails();
        System.out.println(subjects);
        ArrayList<SubjectStatusFragment> fragments = new ArrayList<>();

        for(int i=0; i<subjects.size(); ++i)
        {
            SubjectStatusFragment fragment = new SubjectStatusFragment();
            Subject subject = subjects.get(i);

            fragment.setSubjectName(subject.getSubjectName());
            double percentage = subject.getPercentage();
            fragment.setPercentage(String.format("%.1f", percentage)+"%");
            fragment.setStatus((percentage>minPercentage)?"Safe":(percentage<minPercentage)?"Danger":"Unsafe");
            fragments.add(fragment);

            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.display_status, fragment);
            transaction.commit();
        }
    }

    @Override
    public void onStatusFragmentClick(View root)
    {
        System.out.println("Listener activated");
        String subjectName = ((TextView)root.findViewById(R.id.subject_name)).getText().toString();

        Intent intent = new Intent(this, SubjectDetailsActivity.class);
        intent.putExtra(SubjectDetailsActivity.SUBJECT_NAME, subjectName);
        startActivity(intent);
    }

    public void onClickBackButton(View view)
    {
        finish();
    }
}
