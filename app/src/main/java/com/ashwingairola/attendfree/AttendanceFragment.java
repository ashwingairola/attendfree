package com.ashwingairola.attendfree;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AttendanceFragment extends Fragment
{
    private String subjectName;

    public AttendanceFragment()
    {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        View root = getView();
        TextView subject = (TextView)root.findViewById(R.id.subject_name);
        subject.setText(subjectName);
    }

    public void setSubject(String subjectName)
    {
        this.subjectName = subjectName;
    }
}
