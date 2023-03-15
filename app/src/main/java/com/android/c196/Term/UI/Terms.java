package com.android.c196.Term.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.R;
import com.android.c196.Term.Adapters.TermsAdapter;
import com.android.c196.Term.Model.Term;
import com.android.c196.Term.Model.TermViewModel;
import com.android.c196.UI.AddTerm;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Terms extends AppCompatActivity {
    private TermViewModel termViewModel;
    private FloatingActionButton addTermFab;
    //recyclerView
    private RecyclerView termRecyclerView;
    private TermsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        setTitle("All Terms");

        termRecyclerView = findViewById(R.id.termsRecyclerView);
        termRecyclerView.setLayoutManager(new LinearLayoutManager(Terms.this));
        termRecyclerView.setHasFixedSize(true);

        adapter = new TermsAdapter(this);
        termRecyclerView.setAdapter(adapter);


        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        termViewModel.getAllTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                //pass LiveData to RecyclerView
                adapter.setTerms(terms);

            }
        });



        addTermFab = findViewById(R.id.addTermFab);



        addTermFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Terms.this, AddTerm.class);
                startActivity(intent);
            }
        });


    }

}