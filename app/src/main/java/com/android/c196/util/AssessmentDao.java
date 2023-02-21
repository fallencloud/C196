package com.android.c196.util;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.c196.model.Assessment;

import java.util.List;

@Dao
public interface AssessmentDao {
    //Create
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAssessment(Assessment assessment);

    //Read
    @Query("SELECT * FROM assess_table")
    LiveData<List<Assessment>> getAllAssessments();

    //Read single assessment
    @Query("SELECT * FROM assess_table WHERE assess_table.assessId == :assessId")
    Assessment getAssessment(int assessId);

    //Read Course Assessments
    @Query("SELECT * FROM assess_table WHERE assess_table.courseId == :courseId")
    LiveData<List<Assessment>> getCourseAssessments(int courseId);

    //Update
    @Update
    void updateAssessment(Assessment assessment);

    //Delete
    @Delete
    void deleteAssessment(Assessment assessment);

    //clear database
    @Query("DELETE FROM assess_table")
    void deleteAllAssessments();
}
