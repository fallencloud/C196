package com.android.c196.Assessment.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.Assessment.Adapters.AssessmentAdapter;
import com.android.c196.Assessment.Model.Assessment;
import com.android.c196.Assessment.Model.AssessmentViewModel;
import com.android.c196.Course.Controllers.Courses;
import com.android.c196.R;
import com.android.c196.Term.Controllers.Terms;
import com.android.c196.Home.Home;

import java.util.List;

public class Assessments extends AppCompatActivity {
    private AssessmentViewModel assessmentViewModel;
    //recyclerView
    private RecyclerView assessRecyclerView;
    private AssessmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);
        setTitle("All Assessments");

        assessRecyclerView = findViewById(R.id.assessmentRecyclerView);
        assessRecyclerView.setLayoutManager(new LinearLayoutManager(Assessments.this));
        assessRecyclerView.setHasFixedSize(true);

        adapter = new AssessmentAdapter(this, getApplication());
        assessRecyclerView.setAdapter(adapter);
        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        assessmentViewModel.getAllAssessments().observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(List<Assessment> assessments) {
                adapter.setAssessments(assessments);
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