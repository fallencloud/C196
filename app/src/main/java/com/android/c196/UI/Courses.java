package com.android.c196.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.R;
import com.android.c196.adapters.TermCourseAdapter;
import com.android.c196.model.Course;
import com.android.c196.model.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Courses extends AppCompatActivity {
    private CourseViewModel courseViewModel;
    private List<Course> allCourses;

    private TermCourseAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton addCourseFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        setTitle("All Courses");

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                allCourses = courses;
            }
        });
    }
}