package com.example.cs3270a8;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fm = getSupportFragmentManager();
    CourseEditFragment courseEditFragment;
    CourseListFragment courseListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FRAGMENT
        if (courseListFragment == null)
        {
            courseListFragment = new CourseListFragment();
        }
        fm.beginTransaction().replace(R.id.fragment, courseListFragment).commit();

        //FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm.beginTransaction()
                        .replace(R.id.fragment, courseEditFragment = new CourseEditFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

}
