package com.android.c196.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class CourseNote {
    @PrimaryKey(autoGenerate = true)
    private int noteId;
    private int courseId;
    private String noteBody;

    public CourseNote(int courseId, String noteBody) {
        this.courseId = courseId;
        this.noteBody = noteBody;
    }

    public int getNoteId() {
        return noteId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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
