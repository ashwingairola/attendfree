package com.ashwingairola.attendfree;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

public class UpdateConfirmDialogFragment extends DialogFragment
{

    public UpdateConfirmDialogFragment()
    {
        // Required empty public constructor
    }

    interface OnUpdateConfirmListener
    {
        void onClickYesButton();
    }

    OnUpdateConfirmListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_update_confirm_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("WARNING!");
        builder.setView(view);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                listener = (OnUpdateConfirmListener)getContext();
                listener.onClickYesButton();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {}
        });

        return builder.create();
    }
}
