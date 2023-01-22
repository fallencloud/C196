package com.android.c196.util;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.c196.model.Course;

import java.util.List;

@Dao
public interface CourseDao {
    //Create
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCourse(Course course);

    //Read
    @Query("SELECT * FROM course_table")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT * FROM course_table WHERE course_table.termId == :termId")
    LiveData<List<Course>> getTermCourses(int termId);

    @Query("SELECT * FROM course_table WHERE course_table.courseId == :courseId")
    Course getCourse(int courseId);

    //Update
    @Update
    void updateCourse(Course course);

    //Delete
    @Delete
    void deleteCourse(Course course);

    //clear database
    @Query("DELETE FROM course_table")
    void deleteAllCourses();
}
