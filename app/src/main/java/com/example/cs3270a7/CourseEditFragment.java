package com.example.cs3270a7;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cs3270a7.db.AppDatabase;
import com.example.cs3270a7.db.Course;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseEditFragment extends Fragment {


    private View root;
    private TextInputEditText id, name, courseCode, startAt, endAt;
    private Button addCourse;

    public CourseEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_course_edit, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        id = (TextInputEditText) root.findViewById(R.id.txt_id);
        name = (TextInputEditText) root.findViewById(R.id.txt_Name);
        courseCode = (TextInputEditText) root.findViewById(R.id.txt_course_code);
        startAt = (TextInputEditText) root.findViewById(R.id.txt_start_at);
        endAt = (TextInputEditText) root.findViewById(R.id.txt_end_at);
        addCourse = root.findViewById(R.id.btn_add_course);

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String sID = id.getText().toString();
                final String sName = name.getText().toString();
                final String sCourseCode = courseCode.getText().toString();
                final String sStartAt = startAt.getText().toString();
                final String sEndAt = endAt.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase.getInstance(getContext())
                                .courseDAO()
                                .insert(new Course(null, sID, sName, sCourseCode, sStartAt, sEndAt));
                    }
                }).start();

                //Clear textboxes
                id.getText().clear();
                name.getText().clear();
                courseCode.getText().clear();
                startAt.getText().clear();
                endAt.getText().clear();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new CourseListFragment()).commit();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}
