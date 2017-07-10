package com.ashwingairola.attendfree;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SubjectStatusFragment extends Fragment implements View.OnClickListener
{
    private String subjectName, percentage, status;
    private View root;

    public SubjectStatusFragment()
    {
        // Required empty public constructor
    }

    public interface OnStatusFragmentClickListener
    {
        void onStatusFragmentClick(View root);
    }

    OnStatusFragmentClickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.fragment_subject_status, container, false);
        TextView subjectView = (TextView)root.findViewById(R.id.subject_name);
        TextView percentView = (TextView)root.findViewById(R.id.percent_attendance);
        TextView statusView = (TextView)root.findViewById(R.id.status);
        subjectView.setText(subjectName);
        percentView.setText(percentage);
        statusView.setText(status);
        root.setOnClickListener(this);
        return root;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
    }

    public void setPercentage(String percentage)
    {
        this.percentage = percentage;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public void onClick(View view)
    {
        listener = (OnStatusFragmentClickListener) getActivity();
        listener.onStatusFragmentClick(root);
    }
}
