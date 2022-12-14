package com.android.c196;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.lang3.StringUtils;

public class AddInstructor extends AppCompatActivity {
    int courseId;
    TextView firstNameInput;
    TextView lastNameInput;
    TextView emailInput;
    TextView phoneInput;
    Button saveInstructorButton;
    Button cancelInstructorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor);
        setTitle("Add Instructor");
        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        emailInput = findViewById(R.id.emailInput);
        phoneInput = findViewById(R.id.phoneInput);
        saveInstructorButton = findViewById(R.id.saveInstructorButton);
        cancelInstructorButton = findViewById(R.id.cancelInstructorButton);

        Intent intent = getIntent();
        courseId = intent.getIntExtra("courseId", -1);

        saveInstructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check for nulls
                String firstName = firstNameInput.getText().toString();
                String lastName = lastNameInput.getText().toString();
                String email = emailInput.getText().toString();
                String phone = phoneInput.getText().toString();

                if (hasNulls(firstName, lastName, email, phone)) {
                    return;
                } else {


                }
                //add values to the array
                //pass the array
            }
        });

    }

    private Boolean hasNulls(String firstName, String lastName, String email, String phone) {
        if(StringUtils.isEmpty(firstName) || StringUtils.isBlank(firstName)) {
            Toast toast = Toast.makeText(this, "First name is required", Toast.LENGTH_LONG);
            toast.show();
            return true;

        } else if (StringUtils.isEmpty(lastName) || StringUtils.isBlank(lastName)) {
            Toast toast = Toast.makeText(this, "Last name is required", Toast.LENGTH_LONG);
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
}