package com.android.c196.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.R;
import com.android.c196.adapters.TermCourseAdapter;
import com.android.c196.model.Course;
import com.android.c196.util.Repository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Courses extends AppCompatActivity {
    private List<Course> courses;
    private Repository repository;
    private TermCourseAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton addCourseFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        setTitle("All Courses");

        repository = new Repository(getApplication());
    }
}