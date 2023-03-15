package com.android.c196.Assessment.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.Assessment.Adapters.AssessmentAdapter;
import com.android.c196.Assessment.Model.Assessment;
import com.android.c196.Assessment.Model.AssessmentViewModel;
import com.android.c196.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Assessments extends AppCompatActivity {
    private AssessmentViewModel assessmentViewModel;
    private FloatingActionButton addAssessmentFab;
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

        adapter = new AssessmentAdapter(this);
        assessRecyclerView.setAdapter(adapter);
        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        assessmentViewModel.getAllAssessments().observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(List<Assessment> assessments) {
                adapter.setAssessments(assessments);
            }
        });
    }
}