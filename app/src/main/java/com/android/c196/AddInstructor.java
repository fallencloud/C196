package com.android.c196;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddInstructor extends AppCompatActivity {
    TextView firstNameInput;
    TextView lastNameInput;
    TextView emailInput;
    TextView phoneInput;
    Button saveInstructorButton;
    Button cancelInstructorButton;
    ArrayList<String> courseInstructors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor);
        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        emailInput = findViewById(R.id.emailInput);
        phoneInput = findViewById(R.id.phoneInput);
        saveInstructorButton = findViewById(R.id.saveInstructorButton);
        cancelInstructorButton = findViewById(R.id.cancelInstructorButton);

        Intent intent = getIntent();
        courseInstructors = intent.getStringArrayListExtra("courseInstructors");

        saveInstructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check for nulls
                //add values to the array
                //pass the array
            }
        });

    }
}