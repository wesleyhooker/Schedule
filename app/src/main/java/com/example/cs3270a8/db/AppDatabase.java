package com.example.cs3270a8.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Course.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

   private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if(instance != null)
        {
            return instance;
        }
        else{
            instance = Room.databaseBuilder(context, AppDatabase.class, "course-database").build();
            return instance;
        }
    }

    //TABLES
    public abstract CourseDAO courseDAO();
}
