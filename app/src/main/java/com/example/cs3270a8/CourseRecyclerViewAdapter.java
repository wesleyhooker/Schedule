package com.example.cs3270a8;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cs3270a8.db.Course;

import java.util.List;

public class CourseRecyclerViewAdapter extends RecyclerView.Adapter<CourseRecyclerViewAdapter.ViewHolder> {

    private final List<Course> courses;
    private Context context;


    public CourseRecyclerViewAdapter(List<Course> courses, Context context) {
        this.courses = courses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Course course = courses.get(i);
        if (course != null)
        {
            viewHolder.course = course;
            viewHolder.tvLine1.setText(course.getName());

            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //CourseViewFragment
                    CourseViewFragment courseViewFragment = new CourseViewFragment();
                    courseViewFragment.setCourse(course);

                    //SHOW
                    ((AppCompatActivity)context).getSupportFragmentManager()
                            .beginTransaction().add(android.R.id.content, courseViewFragment, "courseViewFrag")
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void addItems(List<Course> courses) {
        this.courses.clear();
        this.courses.addAll(courses);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvLine1;
        public Course course;
        public View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tvLine1 = itemView.findViewById(R.id.line1);
        }
    }
}
