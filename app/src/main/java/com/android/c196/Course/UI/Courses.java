package com.android.c196.Course.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.Assessment.UI.Assessments;
import com.android.c196.Course.Adapters.CourseAdapter;
import com.android.c196.Course.Model.Course;
import com.android.c196.Course.Model.CourseViewModel;
import com.android.c196.R;
import com.android.c196.Term.UI.Terms;
import com.android.c196.UI.Home;
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

        addCourseFab = findViewById(R.id.addCourseFab);

        addCourseFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Courses.this, AddCourse.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_level_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_home:
                intent = new Intent(this, Home.class);
                startActivity(intent);
                return true;
            case R.id.menu_terms:
                intent = new Intent(this, Terms.class);
                startActivity(intent);
                return true;
            case R.id.menu_courses:
                intent = new Intent(this, Courses.class);
                startActivity(intent);
                return true;
            case R.id.menu_assessments:
                intent = new Intent(this, Assessments.class);
                startActivity(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}