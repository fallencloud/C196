package com.android.c196.Note.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.Course.Models.Course;
import com.android.c196.Course.Models.CourseViewModel;
import com.android.c196.Note.Adapters.NoteAdapter;
import com.android.c196.Note.Model.Note;
import com.android.c196.Note.Model.NoteViewModel;
import com.android.c196.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CourseNotes extends AppCompatActivity {
    private long courseId;
    private NoteAdapter noteAdapter;
    private NoteViewModel noteViewModel;
    private CourseViewModel courseViewModel;
    private RecyclerView noteRecyclerView;
    private FloatingActionButton addNoteFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_notes);
        courseId = getIntent().getLongExtra("courseId", -1);
        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        Course course = courseViewModel.getCourse(courseId);
        setTitle(course.getCourseTitle() + " " + "Notes");

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteRecyclerView = findViewById(R.id.notesRecyclerView);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(CourseNotes.this));
        noteRecyclerView.setHasFixedSize(true);

        noteAdapter = new NoteAdapter(this, getApplication());
        noteRecyclerView.setAdapter(noteAdapter);

        noteViewModel.getCourseNotes(courseId).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setNotes(notes);
            }
        });

        addNoteFab = findViewById(R.id.addNoteFab);
        addNoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseNotes.this, AddNote.class);
                intent.putExtra("courseId", courseId);
                startActivity(intent);
            }
        });
    }
}