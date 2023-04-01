package com.android.c196.Instructors.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.c196.util.Repository;

import java.util.List;

public class InstructorViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Instructor>> allInstructors;
    private LiveData<List<Instructor>> courseInstructor;

    public InstructorViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allInstructors = repository.getAllInstructors();
    }

    public LiveData<List<Instructor>> getAllInstructors() {return repository.getAllInstructors();}

    public void insertInstructor(Instructor instructor) {
        repository.insertInstructor(instructor);
    }

    public LiveData<List<Instructor>> getCourseInstructors(long courseId) {
        return repository.getCourseInstructors(courseId);
    }

    public Instructor getInstructor(int instrId) {
        return repository.getInstructor(instrId);
    }

    public void updateInstructor(Instructor instructor) {
        repository.updateInstructor(instructor);
    }

    public void deleteInstructor(Instructor instructor) {
        repository.deleteInstructor(instructor);
    }
}
