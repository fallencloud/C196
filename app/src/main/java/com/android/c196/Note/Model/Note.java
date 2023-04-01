package com.android.c196.Note.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.android.c196.Course.Models.Course;

@Entity(tableName = "note_table",
        foreignKeys = @ForeignKey(entity = Course.class,
        parentColumns = "courseId",
        childColumns = "noteCourseId",
        onDelete = ForeignKey.CASCADE))

public class Note {
    @PrimaryKey(autoGenerate = true)
    private int noteId;
    private long noteCourseId;
    private String noteTitle;
    private String noteBody;

    public Note(long noteCourseId, String noteTitle, String noteBody) {
        this.noteCourseId = noteCourseId;
        this.noteTitle = noteTitle;
        this.noteBody = noteBody;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public long getNoteCourseId() {
        return noteCourseId;
    }

    public void setNoteCourseId(long courseId) {
        this.noteCourseId = courseId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    @NonNull
    @Override
    public String toString() {
        return noteBody;
    }
}
