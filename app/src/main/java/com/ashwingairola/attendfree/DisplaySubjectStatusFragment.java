package com.ashwingairola.attendfree;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DisplaySubjectStatusFragment extends Fragment
{
    private int subjectId;
    private String subjectName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_display_subject_status, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }
}