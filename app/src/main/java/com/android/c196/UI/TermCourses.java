package com.android.c196.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.AddCourse;
import com.android.c196.R;
import com.android.c196.adapters.TermCourseAdapter;
import com.android.c196.model.Course;
import com.android.c196.model.Term;
import com.android.c196.util.Repository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermCourses extends AppCompatActivity {
    private Term term;
    private int termId;
    private List<Course> termCourses;
    private Repository repository;
    private TermCourseAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton addCourseFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_courses);

        repository = new Repository(getApplication());
        termId = getIntent().getIntExtra("termId", -1);
        term = repository.getTerm(termId);
        setTitle(term.getTermTitle());
        termCourses = repository.getTermCourses(termId);
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