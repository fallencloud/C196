package com.android.c196.Course.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.Course.Adapters.CourseAdapter;
import com.android.c196.Course.Model.Course;
import com.android.c196.R;
import com.android.c196.Course.Model.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Courses extends AppCompatActivity {
    private CourseViewModel courseViewModel;
    private FloatingActionButton addCourseFab;
    //recyclerView
    private RecyclerView courseRecyclerView;
    private CourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        setTitle("All Courses");

        courseRecyclerView = findViewById(R.id.coursesRecyclerView);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(Courses.this));
        courseRecyclerView.setHasFixedSize(true);

        adapter = new CourseAdapter(this);
        courseRecyclerView.setAdapter(adapter);

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                //update RecyclerView
                adapter.setCourses(courses);
            }
        });
    }
}