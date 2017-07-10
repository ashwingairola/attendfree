package com.ashwingairola.attendfree;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

public class UpdateAttendanceDialogFragment extends DialogFragment
{
    public UpdateAttendanceDialogFragment()
    {
        // Required empty public constructor
    }

    interface UpdateAttendanceDialogListener
    {
        void onClickUpdateButton();
    }

    UpdateAttendanceDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_update_attendance_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.setMessage("NOTE!");

        builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                listener = (UpdateAttendanceDialogListener)getContext();
                listener.onClickUpdateButton();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {}
        });

        return builder.create();
    }
}
