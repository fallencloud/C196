package com.android.c196.Term.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.c196.util.Repository;

import java.util.List;

public class TermViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Term>> allTerms;

    public TermViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allTerms = repository.getAllTerms();
    }

    public LiveData<List<Term>> getAllTerms() { return repository.getAllTerms(); }

    public void insertTerm(Term term) { repository.insertTerm(term);}

    public Term getTerm(int termId) {
        return repository.getTerm(termId);
    }

    public void updateTerm(Term term) { repository.updateTerm(term);}

    public void deleteTerm(Term term) {
        repository.deleteTerm(term);
    }


}
