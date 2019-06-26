package com.example.cs3270a8;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.example.cs3270a8.db.AppDatabase;
import com.example.cs3270a8.db.Course;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseViewFragment extends DialogFragment implements DeleteConfirmationDialog.MyDialogCloseListener {
    //VARS
    private TextInputEditText edtID, edtName, edtCourseCode, edtStartAt, edtEndAt;
    private Button btnSaveCourse;
    private View root;
    private Course course;


    //PROPERTIES
    public void setCourse(Course course) {
        this.course = course;
    }


    //LIFECYCLE
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_course_view, container, false);

        //Tool Bar
        Toolbar toolbar = root.findViewById(R.id.toolbar);
        toolbar.setTitle(course.getName());
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        }

        setHasOptionsMenu(true);

        edtID = root.findViewById(R.id.edt_id);
        edtName = root.findViewById(R.id.edt_name);
        edtCourseCode = root.findViewById(R.id.edt_course_code);
        edtStartAt = root.findViewById(R.id.edt_start_at);
        edtEndAt = root.findViewById(R.id.edt_end_at);
        btnSaveCourse = root.findViewById(R.id.btn_save);
        btnSaveCourse.setVisibility(View.INVISIBLE);

        //Populate Form
        edtID.setText(course.getId());
        edtName.setText(course.getName());
        edtCourseCode.setText(course.getCourse_code());
        edtStartAt.setText(course.getStart_at());
        edtEndAt.setText(course.getEnd_at());

        return root;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onStart() {
        super.onStart();

        btnSaveCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCourse();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_edit:
                edit();
                return true;
            case R.id.action_delete:
                delete();
                return true;
            case android.R.id.home:

                dismiss();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        //HIDE KEYBOARD
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null)
        {
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }


    //METHODS
    private void edit()
    {
        btnSaveCourse.setVisibility(View.VISIBLE);
        edtID.setEnabled(true);
        edtName.setEnabled(true);
        edtCourseCode.setEnabled(true);
        edtStartAt.setEnabled(true);
        edtEndAt.setEnabled(true);
    }

    private void delete()
    {
        DeleteConfirmationDialog deleteConfirmationDialog = new DeleteConfirmationDialog();
        deleteConfirmationDialog.setCourse(course);
        deleteConfirmationDialog.show(getActivity().getSupportFragmentManager(), "deleteConfirmationDialog");
    }

    private void saveCourse()
    {
        final String sID = edtID.getText().toString();
        final String sName = edtName.getText().toString();
        final String sCourseCode = edtCourseCode.getText().toString();
        final String sStartAt = edtStartAt.getText().toString();
        final String sEndAt = edtEndAt.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase.getInstance(getContext())
                        .courseDAO()
                        .edit(new Course(course.get_id(), sID, sName, sCourseCode, sStartAt, sEndAt));
            }
        }).start();

        dismiss();
    }

    @Override
    public void handleDialogClose() {
        dismiss();
    }
}
