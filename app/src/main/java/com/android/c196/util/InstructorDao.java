package com.android.c196.util;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.c196.model.Instructor;

import java.util.List;

@Dao
public interface InstructorDao {

    //Create
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertInstructor(Instructor instructor);

    //Read
    @Query("SELECT * FROM instructor_table")
    LiveData<List<Instructor>> getAllInstructors();

    @Query("SELECT * FROM instructor_table WHERE instructor_table.courseId == :courseId")
    LiveData<List<Instructor>> getCourseInstructors(int courseId);

    @Query("SELECT * FROM instructor_table WHERE instructor_table.instrId == :instrId")
    Instructor getInstructor(int instrId);

    //Update
    @Update
    void updateInstructor(Instructor instructor);

    //Delete
    @Delete
    void deleteInstructor(Instructor instructor);

    //clear db
    @Query("DELETE FROM instructor_table")
    void deleteAllInstructors();
}
