package com.android.c196.Assessment.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import com.android.c196.Assessment.Model.Assessment;
import com.android.c196.Assessment.Model.AssessmentType;
import com.android.c196.Assessment.Model.AssessmentViewModel;
import com.android.c196.Course.Controllers.CourseDetailed;
import com.android.c196.Home.Home;
import com.android.c196.R;
import com.android.c196.util.Utils;

import java.util.Calendar;
import java.util.Date;

public class AddAssessment extends AppCompatActivity {
    long courseId;
    int assessId;
    Assessment assessment;
    private EditText assessmentTitleInput;
    private TextView assessmentTypeText;
    private Spinner assessmentType;
    private Button startPickerButton;
    private Button endPickerButton;
    private Button saveButton;
    private Button cancelButton;
    private CalendarView assessmentCalendarView;
    private Boolean isStart;
    private Date startDate;
    private Date endDate;
    private AssessmentViewModel assessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        Intent intent = getIntent();
        courseId = intent.getLongExtra("courseId", -1);
        assessId = intent.getIntExtra("assessId", -1);
        setTitle((assessId == -1) ?"Add Assessment" : "Edit Assessment");

        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);

        assessmentTitleInput = findViewById(R.id.assessmentTitleInput);
        assessmentTypeText = findViewById(R.id.assessmentTypeText);
        assessmentType = findViewById(R.id.assessmentType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.assessment_type, android.R.layout.simple_spinner_item);
        startPickerButton = findViewById(R.id.startPickerButton);
        endPickerButton = findViewById(R.id.endPickerButton);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        assessmentCalendarView = findViewById(R.id.assessmentCalendarView);

        if (assessId != -1) {
            assessment = assessmentViewModel.getAssessment(assessId);
            assessmentTitleInput.setText(assessment.getAssessTitle());

            int spinnerPosition = adapter.getPosition(assessment.getAssessType().toString());
            assessmentType.setSelection(spinnerPosition);

            startDate = assessment.getAssessStartDate();
            endDate = assessment.getAssessEndDate();
            startPickerButton.setText(Utils.formatButtonDate(startDate));
            endPickerButton.setText(Utils.formatButtonDate(endDate));


        }

        startPickerButton.setOnClickListener(view2 -> {
            isStart = true;
            assessmentCalendarView.setVisibility(assessmentCalendarView.getVisibility() == View.GONE?
                    View.VISIBLE : View.GONE);
        });

        endPickerButton.setOnClickListener(view3 -> {
            isStart = false;
            assessmentCalendarView.setVisibility(assessmentCalendarView.getVisibility() == View.GONE?
                    View.VISIBLE : View.GONE);
        });

        assessmentCalendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
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

            assessmentCalendarView.setVisibility(View.GONE);
        } );

        saveButton.setOnClickListener(view1 -> {
            if (assessId == -1) {
                saveAssessment();
            } else {
                saveAssessment(assessId);
            }
        });

        cancelButton.setOnClickListener(view2 -> {
            finish();
        });
    }

    private void saveAssessment(int assessId) {
        String title = assessmentTitleInput.getText().toString().trim();
        String type = String.valueOf(assessmentType.getSelectedItem());

        if(!checkForErrors(title)) {
            assessment.setAssessTitle(title);
            assessment.setAssessStartDate(startDate);
            assessment.setAssessEndDate(endDate);
            assessment.setAssessType(handleType(type));
            //save the assessment
            assessmentViewModel.updateAssessment(assessment);

            //cleanUp
            assessmentTitleInput.setText("");
            startDate = null;
            endDate = null;

            Intent intent = new Intent(AddAssessment.this, CourseDetailed.class);
            intent.putExtra("courseId", courseId);
            startActivity(intent);
        }
    }

    private void saveAssessment() {
        String title = assessmentTitleInput.getText().toString().trim();
        String type = String.valueOf(assessmentType.getSelectedItem());

        if(!checkForErrors(title)) {
            //save the assessment
            assessmentViewModel.insertAssessment(new Assessment(courseId, title, startDate, endDate, handleType(type)));

            //cleanUp
            assessmentTitleInput.setText("");
            startDate = null;
            endDate = null;

            Intent intent = new Intent(AddAssessment.this, CourseDetailed.class);
            intent.putExtra("courseId", courseId);
            startActivity(intent);
        }
    }

    private boolean checkForErrors(String title) {
        boolean hasError = false;
        Toast toast;

        if(TextUtils.isEmpty(title)) {
            hasError = true;
            toast = Toast.makeText(this, "Assessment title is required", Toast.LENGTH_LONG);
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

    private AssessmentType handleType(String type) {
        if ("Performance".equals(type)) {
            return AssessmentType.PERFORMANCE;
        }
        return AssessmentType.OBJECTIVE;
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