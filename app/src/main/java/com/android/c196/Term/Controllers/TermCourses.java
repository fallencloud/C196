package com.android.c196.Term.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.Course.Controllers.AddCourse;
import com.android.c196.R;
import com.android.c196.Course.Adapters.CourseAdapter;
import com.android.c196.Course.Models.Course;
import com.android.c196.Course.Models.CourseViewModel;
import com.android.c196.Term.Model.Term;
import com.android.c196.Term.Model.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermCourses extends AppCompatActivity {
    private Term term;
    private int termId;

    private CourseViewModel courseViewModel;
    private TermViewModel termViewModel;
    private CourseAdapter adapter;
    private RecyclerView courseRecyclerView;
    private FloatingActionButton addCourseFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_courses);

        termId = getIntent().getIntExtra("termId", -1);
        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        term = termViewModel.getTerm(termId);
        setTitle(term.getTermTitle());

        courseRecyclerView = findViewById(R.id.termCourseRecyclerView);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(TermCourses.this));
        courseRecyclerView.setHasFixedSize(true);

        adapter = new CourseAdapter(this, getApplication());
        courseRecyclerView.setAdapter(adapter);

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);

        courseViewModel.getTermCourses(termId).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> termCourses) {
                adapter.setCourses(termCourses);
            }
        });

        addCourseFab = findViewById(R.id.addCourseFab);

        addCourseFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermCourses.this, AddCourse.class);
                intent.putExtra("termId", term.getTermId());
                startActivity(intent);
            }
        });
    }
}