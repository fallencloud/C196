package com.android.c196.Instructors.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.android.c196.Course.Controllers.CourseDetailed;
import com.android.c196.Home.Home;
import com.android.c196.Instructors.Model.Instructor;
import com.android.c196.Instructors.Model.InstructorViewModel;
import com.android.c196.R;

import org.apache.commons.lang3.StringUtils;

public class AddInstructor extends AppCompatActivity {
    long courseId;
    int instrId;
    Instructor instructor;
    TextView instrNameInput;
    TextView lastNameInput;
    TextView emailInput;
    TextView phoneInput;
    Button saveInstructorButton;
    Button cancelInstructorButton;
    InstructorViewModel instructorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor);
        Intent intent = getIntent();
        courseId = intent.getLongExtra("courseId", -1);
        instrId = intent.getIntExtra("instrId", -1);
        setTitle(instrId == -1 ? "Add Instructor" : "Edit Instructor");
        instructorViewModel = ViewModelProviders.of(this).get(InstructorViewModel.class);

        instrNameInput = findViewById(R.id.instrNameInput);
        emailInput = findViewById(R.id.emailInput);
        phoneInput = findViewById(R.id.phoneInput);
        saveInstructorButton = findViewById(R.id.saveInstructorButton);
        cancelInstructorButton = findViewById(R.id.cancelInstructorButton);

        if (instrId != -1) {
            instructor = instructorViewModel.getInstructor(instrId);
            instrNameInput.setText(instructor.getInstrName());
            emailInput.setText(instructor.getEmail());
            phoneInput.setText(instructor.getPhone());
        }

        cancelInstructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        saveInstructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check for nulls
                String name = instrNameInput.getText().toString();
                String email = emailInput.getText().toString();
                String phone = phoneInput.getText().toString();

                if (hasNulls(name, email, phone)) {
                    return;
                }

                if (instrId == -1 ) {
                    addInstructor(name, email, phone);
                } else {
                    addInstructor(instrId, name, email, phone);
                }
            }
        });

    }

    private void addInstructor(int instrId, String name, String email, String phone) {
        instructor.setInstrName(name);
        instructor.setEmail(email);
        instructor.setPhone(phone);
        instructorViewModel.updateInstructor(instructor);

        Intent intent = new Intent(AddInstructor.this, CourseDetailed.class);
        intent.putExtra("courseId", courseId);
        startActivity(intent);


    }

    private void addInstructor(String name, String email, String phone) {
        instructorViewModel.insertInstructor(new Instructor(name, email, phone, courseId));

        Intent intent = new Intent(AddInstructor.this, CourseDetailed.class);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }

    private Boolean hasNulls(String name, String email, String phone) {
        if(StringUtils.isEmpty(name) || StringUtils.isBlank(name)) {
            Toast toast = Toast.makeText(this, "First name is required", Toast.LENGTH_LONG);
            toast.show();
            return true;

        } else if (StringUtils.isEmpty(email) || StringUtils.isBlank(email)) {
            Toast toast = Toast.makeText(this, "Email is required", Toast.LENGTH_LONG);
            toast.show();
            return true;
        } else if (StringUtils.isEmpty(phone) || StringUtils.isBlank(phone)) {
            Toast toast = Toast.makeText(this, "Phone number is required", Toast.LENGTH_LONG);
            toast.show();
            return true;
        }

        return false;
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