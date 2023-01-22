package com.android.c196.util;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.c196.model.Term;

import java.util.List;

@Dao
public interface TermDao {

    //Create
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTerm(Term term);

    //Read
    @Query("SELECT * FROM term_table ORDER BY termStartDate DESC")
    LiveData<List<Term>> getAllTerms();

    @Query("SELECT * FROM term_table WHERE term_table.termId == :termId")
    Term getTerm(int termId);

    //update
    @Update
    void updateTerm(Term term);

    //Delete
    @Delete
    void deleteTerm(Term term);

    //clear database
    @Query("DELETE FROM term_table")
    void deleteAllTerms();

}
