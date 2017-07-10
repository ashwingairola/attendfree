package com.ashwingairola.attendfree;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddClassDialogFragment extends DialogFragment
{
    ArrayList<String> subjects;
    ArrayAdapter<String> adapter;

    public AddClassDialogFragment()
    {
        // Required empty public constructor
    }

    interface AddClassDialogListener
    {
        void onDialogPositiveClick(String addedClass);
    }

    AddClassDialogListener listener;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        listener = (AddClassDialogListener)context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_add_class_dialog, null);
        builder.setView(view);
        subjects = new DBHelper(getContext()).getAllSubjects();

        adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, subjects);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        final Spinner spinner = (Spinner)view.findViewById(R.id.add_class);
        spinner.setAdapter(adapter);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                listener.onDialogPositiveClick(spinner.getSelectedItem().toString());
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {}
        });
        return builder.create();
    }
}
