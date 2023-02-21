package com.android.c196.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.AddCourse;
import com.android.c196.R;
import com.android.c196.adapters.TermCourseAdapter;
import com.android.c196.model.Course;
import com.android.c196.model.CourseViewModel;
import com.android.c196.model.Term;
import com.android.c196.model.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermCourses extends AppCompatActivity {
    private Term term;
    private int termId;
    private List<Course> termCourses;

    private TermViewModel termViewModel;
    private CourseViewModel courseViewModel;
    private TermCourseAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton addCourseFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_courses);

        termId = getIntent().getIntExtra("termId", -1);
        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        term = termViewModel.getTerm(termId);
        setTitle(term.getTermTitle());

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);

        courseViewModel.getTermCourses(term.getTermId()).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> termCourses) {
                TermCourses.this.termCourses = termCourses;
            }
        });
        addCourseFab = findViewById(R.id.addCourseFab);
        recyclerView = findViewById(R.id.termCourseRecyclerView);
        adapter = new TermCourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(termCourses);

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