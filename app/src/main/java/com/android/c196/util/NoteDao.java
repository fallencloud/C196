package com.android.c196.util;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.c196.model.Note;

import java.util.List;

@Dao
public interface NoteDao {

    //Create
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNote(Note note);

    //Read
    @Query("SELECT * FROM note_table WHERE note_table.courseId = :courseId")
    LiveData<List<Note>> getCourseNotes(int courseId);

    //Update
    @Update
    void updateNote(Note note);

    //Delete
    @Delete
    void deleteNote(Note note);

    //clear database
    @Query("DELETE FROM note_table")
    void deleteAllNotes();
}
