package com.example.cs3270a7.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CourseDAO {

    @Insert
    void insert(Course... courses);

    @Query("select * from Course")
    LiveData<List<Course>> getAll();

    @Query("SELECT * FROM Course WHERE id = :id LIMIT 1")
    Course details(String id);

    @Update
    void edit(Course course);

    @Delete
    void delete(Course course);
}
