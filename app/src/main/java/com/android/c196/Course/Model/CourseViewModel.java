package com.android.c196.Course.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.c196.util.Repository;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Course>> allCourses;
    private LiveData<List<Course>> termCourses;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allCourses = repository.getAllCourses();
    }

    public LiveData<List<Course>> getAllCourses() {
        return repository.getAllCourses();
    }

    public LiveData<List<Course>> getTermCourses(int termId) {
        return repository.getTermCourses(termId);
    }

    public void insertCourse(Course course) {
        repository.insertCourse(course);
    }

    public Course getCourse(int courseId) {
        return repository.getCourse(courseId);
    }

    public void updateCourse(Course course) {
        repository.updateCourse(course);
    }

    public void deleteCourse(Course course) {
        repository.deleteCourse(course);
    }
}
