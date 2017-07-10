package com.ashwingairola.attendfree;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ShowClassFragment extends Fragment
{

    private int classNo;
    private String className;

    public ShowClassFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_show_class, container, false);
        TextView subjectNo = (TextView)root.findViewById(R.id.class_number);
        TextView subjectName = (TextView)root.findViewById(R.id.class_name);
        subjectName.setText(className);
        subjectNo.setText(subjectNo.getText().toString() + classNo);
        return root;
    }

    public void setClassNo(int classNo)
    {
        this.classNo = classNo;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }
}
