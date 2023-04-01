package com.android.c196.Course.Controllers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.Assessment.Adapters.AssessmentAdapter;
import com.android.c196.Assessment.Controllers.AddAssessment;
import com.android.c196.Assessment.Model.Assessment;
import com.android.c196.Assessment.Model.AssessmentViewModel;
import com.android.c196.Course.Models.Course;
import com.android.c196.Course.Models.CourseViewModel;
import com.android.c196.Home.Home;
import com.android.c196.Instructors.Adapters.CourseInstructorAdapter;
import com.android.c196.Instructors.Controllers.AddInstructor;
import com.android.c196.Instructors.Model.Instructor;
import com.android.c196.Instructors.Model.InstructorViewModel;
import com.android.c196.Note.Controllers.CourseNotes;
import com.android.c196.R;
import com.android.c196.util.AlertReceiver;
import com.android.c196.util.Utils;

import java.util.List;

public class CourseDetailed extends AppCompatActivity {
    private Course course;
    private long courseId;

    private TextView courseOverviewText;
    private TextView courseDetailedStartText;
    private ImageView courseStartNotify;
    private TextView courseDetailedEndText;
    private ImageView courseEndNotify;

    private CourseViewModel courseViewModel;
    private InstructorViewModel instructorViewModel;
    private CourseInstructorAdapter instructorAdapter;
    private AssessmentViewModel assessmentViewModel;
    private AssessmentAdapter assessmentAdapter;

    private RecyclerView instructorRecyclerView;
    private RecyclerView assessmentRecyclerView;

    private boolean hasFiveAssess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detailed);

        courseId = getIntent().getLongExtra("courseId", -1);

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        course = courseViewModel.getCourse(courseId);
        String courseTitle = course.getCourseTitle();
        courseOverviewText = findViewById(R.id.courseOverviewText);
        courseDetailedStartText = findViewById(R.id.courseDetailedStartText);
        courseStartNotify = findViewById(R.id.courseStartNotify);
        courseDetailedEndText = findViewById(R.id.courseDetailedEndText);
        courseEndNotify = findViewById(R.id.courseEndNotify);

        courseOverviewText.setText(courseTitle);
        courseDetailedStartText.setText(Utils.formatDate(course.getCourseStartDate()));
        courseDetailedEndText.setText(Utils.formatDate(course.getCourseEndDate()));

        //initial alarm states
        boolean isStartAlarmSet = isAlarmSet((int) courseId);
        boolean isEndAlarmSet = isAlarmSet((int) courseId + 1000000);

        // Update the ImageView src based on the alarm status
        if (isStartAlarmSet) {
            courseStartNotify.setImageResource(R.drawable.notifications_black_24dp);
            courseStartNotify.setTag(R.drawable.notifications_black_24dp);
        } else {
            courseStartNotify.setImageResource(R.drawable.notifications_off_black_24dp);
            courseStartNotify.setTag(R.drawable.notifications_off_black_24dp);
        }

        if (isEndAlarmSet) {
            courseEndNotify.setImageResource(R.drawable.notifications_black_24dp);
            courseEndNotify.setTag(R.drawable.notifications_black_24dp);
        } else {
            courseEndNotify.setImageResource(R.drawable.notifications_off_black_24dp);
            courseEndNotify.setTag(R.drawable.notifications_off_black_24dp);
        }


        instructorRecyclerView = findViewById(R.id.instructorRecyclerView);
        instructorRecyclerView.setLayoutManager(new LinearLayoutManager(CourseDetailed.this));
        instructorRecyclerView.setHasFixedSize(true);

        assessmentRecyclerView = findViewById(R.id.assessmentRecyclerView);
        assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(CourseDetailed.this));
        assessmentRecyclerView.setHasFixedSize(true);

        instructorAdapter = new CourseInstructorAdapter(this, getApplication());
        instructorRecyclerView.setAdapter(instructorAdapter);

        assessmentAdapter = new AssessmentAdapter(this, getApplication());
        assessmentRecyclerView.setAdapter(assessmentAdapter);

        instructorViewModel = ViewModelProviders.of(this).get(InstructorViewModel.class);
        instructorViewModel.getCourseInstructors(courseId).observe(CourseDetailed.this, new Observer<List<Instructor>>() {
            @Override
            public void onChanged(List<Instructor> instructors) {
                instructorAdapter.setInstructors(instructors);
            }
        });

        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        assessmentViewModel.getCourseAssessments(courseId).observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(List<Assessment> assessments) {
                assessmentAdapter.setAssessments(assessments);
                hasFiveAssess = assessments.size() == 5;

            }
        });

        courseStartNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the notification image
                Integer currentDrawableId = (Integer) courseStartNotify.getTag();

                //turn on if off
                if (currentDrawableId == null || currentDrawableId.equals(R.drawable.notifications_off_black_24dp)) {
                    // Change the ImageView src to notifications_black_24dp
                    courseStartNotify.setImageResource(R.drawable.notifications_black_24dp);
                    courseStartNotify.setTag(R.drawable.notifications_black_24dp);
                    setStartAlarm();
                } else {
                    // turn off if on
                    courseStartNotify.setImageResource(R.drawable.notifications_off_black_24dp);
                    courseStartNotify.setTag(R.drawable.notifications_off_black_24dp);
                    cancelStartAlarm();
                }
            }
        });


        courseEndNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current drawable resource ID
                Integer currentDrawableId = (Integer) courseEndNotify.getTag();

                // If the current drawable is notifications_off_black_24dp
                if (currentDrawableId == null || currentDrawableId.equals(R.drawable.notifications_off_black_24dp)) {
                    // Change the ImageView src to notifications_black_24dp
                    courseEndNotify.setImageResource(R.drawable.notifications_black_24dp);
                    courseEndNotify.setTag(R.drawable.notifications_black_24dp);
                    setEndAlarm();
                } else {
                    // Change the ImageView src to notifications_off_black_24dp
                    courseEndNotify.setImageResource(R.drawable.notifications_off_black_24dp);
                    courseEndNotify.setTag(R.drawable.notifications_off_black_24dp);
                    cancelEndAlarm();
                }
            }
        });

    }

    private void checkAssessmentSize() {
       if (!hasFiveAssess) {
           Intent intent = new Intent(this, AddAssessment.class);
           intent.putExtra("courseId", courseId);
           startActivity(intent);
       }  else {
           Toast.makeText(this, "Only 5 assessments are allowed", Toast.LENGTH_LONG).show();
       }
    }

    private void setStartAlarm() {
        // Get the AlarmManager system service
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Create an intent to call the AlertReceiver
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("title", "Course Start");
        intent.putExtra("message", "The course " + course.getCourseTitle() + " is starting today!");

        // Create a PendingIntent with a unique requestCode for the start date
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) courseId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Set the alarm to trigger at the start date
        alarmManager.set(AlarmManager.RTC_WAKEUP, course.getCourseStartDate().getTime(), pendingIntent);
    }

    private void cancelStartAlarm() {
        // Get the AlarmManager system service
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Create an intent to call the AlertReceiver
        Intent intent = new Intent(this, AlertReceiver.class);

        // Create a PendingIntent with a unique requestCode for the start date
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) courseId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Cancel the alarm
        alarmManager.cancel(pendingIntent);
    }

    private void setEndAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("title", "Course End");
        intent.putExtra("message", "The course " + course.getCourseTitle() + " is ending today!");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) courseId + 1000000, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, course.getCourseEndDate().getTime(), pendingIntent);
    }

    private void cancelEndAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) courseId + 1000000, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(pendingIntent);
    }

    //check alarm status
    private boolean isAlarmSet(int requestCode) {
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE);
        return pendingIntent != null;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detailed_course_menu, menu);
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
            case R.id.add_instructor:
                intent = new Intent(this, AddInstructor.class);
                intent.putExtra("courseId", course.getCourseId());
                startActivity(intent);
                return true;
            case R.id.add_assessment:
                checkAssessmentSize();
                return true;
            case R.id.view_notes:
                intent = new Intent(this, CourseNotes.class);
                intent.putExtra("courseId", course.getCourseId());
                startActivity(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}