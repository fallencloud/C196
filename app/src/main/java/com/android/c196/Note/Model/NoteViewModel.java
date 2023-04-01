package com.android.c196.Note.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.c196.util.Repository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private Repository repository;


    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void insertNote(Note note) {
        repository.insertNote(note);
    }

    public LiveData<List<Note>> getCourseNotes(long courseId) {
        return repository.getCourseNotes(courseId);
    }
    public Note getNote(int noteId) {
        return repository.getNote(noteId);
    }

    public void updateNote(Note note) {
        repository.updateNote(note);
    }

    public void deleteNote(Note note) {
        repository.deleteNote(note);
    }
}
