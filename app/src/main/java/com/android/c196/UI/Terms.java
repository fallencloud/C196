package com.android.c196.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.R;
import com.android.c196.adapters.TermsRecyclerViewAdapter;
import com.android.c196.model.Term;
import com.android.c196.model.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Terms extends AppCompatActivity implements TermsRecyclerViewAdapter.HandleClickTerm {
    private TermViewModel termViewModel;
    private List<Term> allTerms;
    private TermsRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton addTermFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        setTitle("All Terms");

        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        termViewModel.getAllTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                allTerms = terms;
            }
        });
        addTermFab = findViewById(R.id.addTermFab);
        recyclerView = findViewById(R.id.termsRecyclerView);
        adapter = new TermsRecyclerViewAdapter(this, repository);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(allTerms);
        adapter.notifyDataSetChanged();



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