package com.example.cs3270a7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.cs3270a7.db.AppDatabase;
import com.example.cs3270a7.db.Course;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CourseEditFragment courseEditFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_1, courseEditFrag = new CourseEditFragment()).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Course> courses = AppDatabase.getInstance(getApplicationContext())
                        .courseDAO()
                        .getAll();

                for (Course c: courses)
                {
                    Log.d("Courses", "Course: " + c.toString());
                }
            }
        }).start();
    }
}

