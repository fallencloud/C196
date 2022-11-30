package com.android.c196.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.R;
import com.android.c196.adapters.Term_RecyclerViewAdapter;
import com.android.c196.model.Term;
import com.android.c196.util.Repository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Terms extends AppCompatActivity implements Term_RecyclerViewAdapter.HandleClickTerm {
    private List<Term> terms;
    private Repository repository;
    private Term_RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton addTermFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        setTitle("All Terms");

        repository = new Repository(getApplication());
        terms = repository.getAllTerms();
        addTermFab = findViewById(R.id.addTermFab);
        recyclerView = findViewById(R.id.termsRecyclerView);
        adapter = new Term_RecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(terms);



        addTermFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Terms.this, AddTerm.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void removeTerm(int termId) {

    }

    @Override
    public void editTerm(int termId) {

    }
}