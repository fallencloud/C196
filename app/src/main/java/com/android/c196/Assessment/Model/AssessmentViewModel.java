package com.android.c196.Assessment.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.c196.util.Repository;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Assessment>> allAssessments;

    public AssessmentViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allAssessments = repository.getAllAssessments();
    }

    public void insertAssessment(Assessment assessment) {
        repository.insertAssessment(assessment);
    }

    public LiveData<List<Assessment>> getAllAssessments() {
        return allAssessments;
    }

    public Assessment getAssessment(int assessId) {
        return repository.getAssessment(assessId);
    }

    public LiveData<List<Assessment>> getCourseAssessments(int courseId) {
        return repository.getCourseAssessments(courseId);
    }
    public void updateAssessment(Assessment assessment) {
        repository.updateAssessment(assessment);
    }
    public  void deleteAssessment(Assessment assessment) {
        repository.deleteAssessment(assessment);
    }

}
