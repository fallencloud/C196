package com.android.c196.Course.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.android.c196.Course.Models.Course;
import com.android.c196.Course.Models.CourseStatus;
import com.android.c196.Course.Models.CourseViewModel;
import com.android.c196.R;
import com.android.c196.Term.Controllers.TermCourses;
import com.android.c196.util.Utils;

import java.util.Calendar;
import java.util.Date;

public class AddCourse extends AppCompatActivity {
    //instance variables
    int termId;
    long courseId;
    Course course;
    private EditText courseTitleInput;
    private TextView statusText;
    private Spinner courseStatus;
    private Button startPickerButton;
    private Button endPickerButton;
    private Button saveButton;
    private Button cancelButton  ;
    private CalendarView courseCalendarView;
    private Boolean isStart;
    Date startDate;
    Date endDate;
    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        setTitle("Add Course");
        termId = getIntent().getIntExtra("termId", -1);

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);

        courseId = getIntent().getLongExtra("courseId", -1);

        courseTitleInput = findViewById(R.id.courseTitleInput);
        courseStatus = findViewById(R.id.courseStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.course_status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        courseStatus.setAdapter(adapter);
        startPickerButton = findViewById(R.id.startPickerButton);
        endPickerButton = findViewById(R.id.endPickerButton);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        courseCalendarView = findViewById(R.id.courseCalendarView);
        courseCalendarView.setVisibility(View.GONE);
        isStart = false;{

        }

        //retrieve course info if present
        if (courseId != -1) {
            course = courseViewModel.getCourse(courseId);
            courseTitleInput.setText(course.getCourseTitle());
            termId = course.getCourseTermId();

            // Set the spinner selection based on the course status
            int spinnerPosition = adapter.getPosition(course.getCourseStatus().toString());
            courseStatus.setSelection(spinnerPosition);
            startDate = course.getCourseStartDate();
            endDate = course.getCourseEndDate();
            startPickerButton.setText(Utils.formatButtonDate(startDate));
            endPickerButton.setText(Utils.formatButtonDate(endDate));

        }

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
            if (courseId == -1) {
                //save new course
                saveCourse();
            } else {
                saveCourse(courseId);
            }
        });

        cancelButton.setOnClickListener(view2 -> {
            finish();
        });

    }

    private void saveCourse() {
        String title = courseTitleInput.getText().toString().trim();
        String status = String.valueOf(courseStatus.getSelectedItem());

        if(!checkForErrors(title)) {
            //save the course
            Course course = new Course(termId, title, startDate, endDate, handleStatus(status));
            courseViewModel.insertCourse(course);

            //cleanUp
            courseTitleInput.setText("");
            startDate = null;
            endDate = null;

            Intent intent = new Intent(AddCourse.this, TermCourses.class);
            intent.putExtra("termId", termId);
            startActivity(intent);
        } else {
            return;
        }
    }

    private void saveCourse(long courseId) {
        String title = courseTitleInput.getText().toString().trim();
        String status = String.valueOf(courseStatus.getSelectedItem());

        if(!checkForErrors(title)) {
            //save the course
            course.setCourseTitle(title);
            course.setCourseStatus(handleStatus(status));
            course.setCourseStartDate(startDate);
            course.setCourseEndDate(endDate);
            courseViewModel.updateCourse(course);

            //cleanUp
            courseTitleInput.setText("");
            startDate = null;
            endDate = null;

            Intent intent = new Intent(AddCourse.this, TermCourses.class);
            intent.putExtra("termId", termId);
            startActivity(intent);
        } else {
            return;
        }
    }

    private boolean checkForErrors(String title) {
        boolean hasError = false;
        Toast toast;

        if(TextUtils.isEmpty(title)) {
            hasError = true;
            toast = Toast.makeText(this, "Course title is required", Toast.LENGTH_LONG);
            toast.show();
        }else if (startDate == null) {
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