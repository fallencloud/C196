package com.android.c196.util;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.c196.Note.Model.Note;

import java.util.List;

@Dao
public interface NoteDao {

    //Create
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNote(Note note);

    //Read
    @Query("SELECT * FROM note_table WHERE note_table.noteCourseId = :courseId")
    LiveData<List<Note>> getCourseNotes(long courseId);

    @Query("SELECT * FROM note_table WHERE note_table.noteId = :noteId")
    Note getNote(int noteId);

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
