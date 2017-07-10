package com.ashwingairola.attendfree;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class SubjectSelectorFragment extends Fragment
{
    private int classNo;
    private Spinner spinner;

    public SubjectSelectorFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_subject_selector, container, false);

        ArrayList<String> subjectsList = new DBHelper(getContext()).getAllSubjects();
        subjectsList.add(0,"<None>");

        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,subjectsList);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner)view.findViewById(R.id.class_name);
        spinner.setAdapter(subjectAdapter);

        TextView textView = (TextView)view.findViewById(R.id.class_number);
        textView.setText(new StringBuilder(textView.getText().toString()).append(classNo));

        return view;
    }

    public void setClassNo(int classNo)
    {
        this.classNo = classNo;
    }
}
