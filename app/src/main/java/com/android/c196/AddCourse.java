package com.android.c196;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.c196.UI.TermCourses;
import com.android.c196.model.Course;
import com.android.c196.model.CourseStatus;
import com.android.c196.model.Instructor;
import com.android.c196.util.Repository;
import com.android.c196.util.Utils;

import java.util.Calendar;
import java.util.Date;

public class AddCourse extends AppCompatActivity {
    //instance variables
    int termId;
    private EditText courseTitleInput;
    private TextView statusText;
    private Spinner courseStatus;
    private EditText instrName;
    private EditText instrPhone;
    private EditText instrEmail;
    private Button startPickerButton;
    private Button endPickerButton;
    private Button saveButton;
    private Button cancelButton;
    private CalendarView courseCalendarView;
    private Boolean isStart;
    Date startDate;
    Date endDate;
    private Repository repository;
    //Temporary List of Instructors
    private Instructor courseInstructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        setTitle("Add Course");
        termId = getIntent().getIntExtra("termId", -1);

        repository = new Repository(getApplication());

        courseTitleInput = findViewById(R.id.courseTitleInput);
        courseStatus = findViewById(R.id.courseStatus);
        instrName = findViewById(R.id.instrName);
        instrPhone = findViewById(R.id.instrPhone);
        instrEmail = findViewById(R.id.instrEmail);
        startPickerButton = findViewById(R.id.startPickerButton);
        endPickerButton = findViewById(R.id.endPickerButton);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.saveButton);
        courseCalendarView = findViewById(R.id.courseCalendarView);
        courseCalendarView.setVisibility(View.GONE);
        isStart = false;

        startPickerButton.setOnClickListener(view2 -> {
            isStart = true;
            courseCalendarView.setVisibility(courseCalendarView.getVisibility() == View.GONE?
                    View.VISIBLE : View.GONE);
        });

        endPickerButton.setOnClickListener(view3 -> {
            isStart = false;
            courseCalendarView.setVisibility(courseCalendarView.getVisibility() == View.GONE?
                    View.VISIBLE : View.GONE);
        });

        courseCalendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();

            calendar.clear();
            calendar.set(year, month, dayOfMonth);

            if(isStart) {
                startDate = calendar.getTime();
                startPickerButton.setText(Utils.formatButtonDate(startDate));
            } else {
                endDate = calendar.getTime();
                endPickerButton.setText(Utils.formatButtonDate(endDate));
            }

            courseCalendarView.setVisibility(View.GONE);
        } );

        saveButton.setOnClickListener(view1 -> {
            String title = courseTitleInput.getText().toString().trim();
            String status = String.valueOf(courseStatus.getSelectedItem());
            String name = instrName.getText().toString().trim();
            String email = instrEmail.getText().toString().trim();
            String phone = instrPhone.getText().toString().trim();

            if(!checkForErrors(title, name, email, phone)) {
                //save the course
                Course course = new Course(termId, title, startDate, endDate, handleStatus(status));
                repository.insertCourse(course);

                //save the instructors
                Instructor instructor = new Instructor(name,email, phone, course.getCourseId());
                repository.insertInstructor(instructor);

                //cleanUp
                courseTitleInput.setText("");
                startDate = null;
                endDate = null;

                Intent intent = new Intent(AddCourse.this, TermCourses.class);
                intent.putExtra("termId", termId);
                startActivity(intent);
            } else {
                //TODO: Add error message
                return;
            }
        });

    }

    private boolean checkForErrors(String title, String name, String email, String phone) {
        boolean hasError = false;
        Toast toast;

        if(TextUtils.isEmpty(title)) {
            hasError = true;
            toast = Toast.makeText(this, "Course title is required", Toast.LENGTH_LONG);
            toast.show();
        } else if (TextUtils.isEmpty(name)) {
            hasError = true;
            toast = Toast.makeText(this, "Instructor name is required", Toast.LENGTH_LONG);
            toast.show();
        } else if (TextUtils.isEmpty(email)) {
            hasError = true;
            toast = Toast.makeText(this, "Instructor email is required", Toast.LENGTH_LONG);
            toast.show();
        } else if (TextUtils.isEmpty(phone)) {
            hasError = true;
            toast = Toast.makeText(this, "Instructor phone is required", Toast.LENGTH_LONG);
            toast.show();
        } else if (startDate == null) {
            hasError = true;
            toast = Toast.makeText(this, "Start date is required", Toast.LENGTH_LONG);
            toast.show();
        } else if (endDate == null) {
            hasError = true;
            toast = Toast.makeText(this, "End date is required", Toast.LENGTH_LONG);
            toast.show();
        }

        return hasError;

    }

    private CourseStatus handleStatus(String status) {
        switch(status) {
            case "Completed":
                return CourseStatus.COMPLETED;
            case "Dropped":
                return CourseStatus.DROPPED;
            case "Plan to Take":
                return CourseStatus.PLAN_TO_TAKE;
            default:
                return CourseStatus.IN_PROGRESS;
        }
    }
}