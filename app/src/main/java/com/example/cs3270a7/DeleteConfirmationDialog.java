package com.example.cs3270a7;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs3270a7.db.AppDatabase;
import com.example.cs3270a7.db.Course;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteConfirmationDialog extends DialogFragment {
    //VARS
    MyDialogCloseListener mCallBack;
    private Course course;

    //PROPERTIES
    public void setCourse(Course course) {
        this.course = course;
    }

    //INTERFACE
    public interface MyDialogCloseListener
    {
        public void handleDialogClose();
    }

    //LIFECYCLE
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            Fragment frag = getActivity().getSupportFragmentManager().findFragmentByTag("courseViewFrag");
            CourseViewFragment parentFrag = ((CourseViewFragment)frag);
            mCallBack = (MyDialogCloseListener) parentFrag;

        } catch (ClassCastException e)
        {
            throw new ClassCastException(getActivity().toString()
                    + " must implement onActionListener interface");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.DeleteDialogTitle))
                .setMessage(getString(R.string.DeleteDialogMessage))
                .setPositiveButton(getString(R.string.deleteCourse), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        delete();
                    }
                })
                .setNegativeButton(getString(R.string.DeleteDialogNeg), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    //METHODS
    public void delete()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase.getInstance(getContext())
                        .courseDAO()
                        .delete(course);
            }
        }).start();

        mCallBack.handleDialogClose();
        dismiss();
    }
}
