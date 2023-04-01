package com.android.c196.Note.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.android.c196.Course.Controllers.CourseDetailed;
import com.android.c196.Course.Models.Course;
import com.android.c196.Course.Models.CourseViewModel;
import com.android.c196.Home.Home;
import com.android.c196.Note.Model.Note;
import com.android.c196.Note.Model.NoteViewModel;
import com.android.c196.R;

public class AddNote extends AppCompatActivity {
    Course course;
    private long courseId;
    private Note note;
    private int noteId;
    private NoteViewModel noteViewModel;
    private CourseViewModel courseViewModel;
    private EditText noteTitleInput;
    private EditText noteBody;
    private Button saveButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Intent intent = getIntent();
        courseId = intent.getLongExtra("courseId", -1);
        noteId = intent.getIntExtra("noteId", -1);
        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        course = courseViewModel.getCourse(courseId);
        setTitle(course.getCourseTitle() + (noteId == -1 ? " Add Note": " Edit Note"));

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteTitleInput = findViewById(R.id.noteTitleInput);
        noteBody = findViewById(R.id.noteBody);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        if (noteId != -1) {
            note = noteViewModel.getNote(noteId);
            noteTitleInput.setText(note.getNoteTitle());
            noteBody.setText(note.getNoteBody());
        }

        saveButton.setOnClickListener(view1 -> {
            if (noteId == -1 ) {
                saveNote();
            } else {
                saveNote(noteId);
            }
        });

        cancelButton.setOnClickListener(view2 -> cancelNote());

    }

    private void saveNote(int noteId) {
        String title = noteTitleInput.getText().toString();
        String body = noteBody.getText().toString();

        if(!checkForErrors(title, body)) {
            note.setNoteTitle(title);
            note.setNoteBody(body);
            noteViewModel.updateNote(note);

            Intent intent = new Intent(AddNote.this, CourseNotes.class);
            intent.putExtra("courseId", courseId);
            startActivity(intent);
        }
    }

    private void saveNote() {
        String title = noteTitleInput.getText().toString();
        String body = noteBody.getText().toString();

        if(!checkForErrors(title, body)) {
            noteViewModel.insertNote(new Note(courseId, title, body));
            Intent intent = new Intent(AddNote.this, CourseNotes.class);
            intent.putExtra("courseId", courseId);
            startActivity(intent);
        }
    }

    private void cancelNote() {
        finish();
    }

    private boolean checkForErrors(String title, String body) {
        boolean hasError = false;
        Toast toast;

        if(TextUtils.isEmpty(title)) {
            hasError = true;
            toast = Toast.makeText(this, "Note title is required", Toast.LENGTH_LONG);
            toast.show();
        }else if (TextUtils.isEmpty(body)) {
            hasError = true;
            toast = Toast.makeText(this, "Note body is required", Toast.LENGTH_LONG);
            toast.show();
        }

        return hasError;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sub_course_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.course_home:
                intent = new Intent(this, Home.class);
                startActivity(intent);
                return true;
            case R.id.course_detailed:
                intent = new Intent(this, CourseDetailed.class);
                intent.putExtra("courseId", courseId);
                startActivity(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}