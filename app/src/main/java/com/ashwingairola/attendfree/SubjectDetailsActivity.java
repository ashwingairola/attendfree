package com.ashwingairola.attendfree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

public class SubjectDetailsActivity extends AppCompatActivity
{
    public static final String SUBJECT_NAME = "subjectName";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_details);

        Intent intent = this.getIntent();
        String subjectName = intent.getStringExtra(SUBJECT_NAME);
        DBHelper dbHelper = new DBHelper(this);
        Subject subject = dbHelper.getSubject(subjectName);

        TextView subjectNameView = (TextView)this.findViewById(R.id.subject_name);
        TextView attendedView = (TextView)this.findViewById(R.id.attended_view);
        TextView bunkedView = (TextView)this.findViewById(R.id.bunked_view);
        TextView cancelledView = (TextView)this.findViewById(R.id.cancelled_view);
        TextView totalView = (TextView)this.findViewById(R.id.total_view);
        TextView percentageView = (TextView)this.findViewById(R.id.percent_attendance_view);

        subjectNameView.setText(subjectName);
        attendedView.setText(""+subject.getAttended());
        bunkedView.setText(""+subject.getBunked());
        cancelledView.setText(""+subject.getCancelled());
        totalView.setText(""+subject.getTotal());
        percentageView.setText(String.format("%.1f",subject.getPercentage())+"%");
    }

    public void onClickBackButton(View view)
    {
        finish();
    }
}
