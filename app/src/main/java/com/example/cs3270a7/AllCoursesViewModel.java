package com.example.cs3270a7;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.cs3270a7.db.AppDatabase;
import com.example.cs3270a7.db.Course;

import java.util.List;

public class AllCoursesViewModel extends ViewModel {
    private LiveData<List<Course>> courseList;

    public LiveData<List<Course>> getCourseList(Context c) {
        if(courseList != null)
        {
            return courseList;
        }

        return courseList = AppDatabase.getInstance(c).courseDAO().getAll();
    }
}
