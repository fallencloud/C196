package com.android.c196.Term.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.android.c196.R;
import com.android.c196.Term.Model.Term;
import com.android.c196.Term.Model.TermViewModel;
import com.android.c196.util.Utils;

import java.util.Calendar;
import java.util.Date;

public class AddTerm extends AppCompatActivity {

    //instance variables
    private EditText termTitleInput;
    private Button startPickerButton;
    private Button endPickerButton;
    private Button saveButton;
    private Button cancelButton;
    private CalendarView termCalendarView;
    private Boolean isStart;
    Date startDate;
    Date endDate;
    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        setTitle("Add Term");

        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);

        termTitleInput = findViewById(R.id.termTitleInput);
        startPickerButton = findViewById(R.id.startPickerButton);
        endPickerButton = findViewById(R.id.endPickerButton);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        termCalendarView = findViewById(R.id.termCalendarView);
        termCalendarView.setVisibility(View.GONE);
        isStart = false;

        startPickerButton.setOnClickListener(view2 -> {
            isStart = true;
            termCalendarView.setVisibility(termCalendarView.getVisibility() == View.GONE?
                    View.VISIBLE : View.GONE);
        });

        endPickerButton.setOnClickListener(view3 -> {
            isStart = false;
            termCalendarView.setVisibility(termCalendarView.getVisibility() == View.GONE?
                    View.VISIBLE : View.GONE);
        });

        termCalendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
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

            termCalendarView.setVisibility(View.GONE);
        } );

        saveButton.setOnClickListener(view1 -> {
            saveNote();
        });

        cancelButton.setOnClickListener(view2 -> {
            cancelSave();
        });
    } // end onCreate

    private void saveNote() {
        String title = termTitleInput.getText().toString().trim();

        if(!TextUtils.isEmpty(title) && startDate != null && endDate != null) {
            Term newTerm = new Term(title, startDate,
                    endDate);
            termViewModel.insertTerm(newTerm);
            //cleanUp
            termTitleInput.setText("");
            startDate = null;
            endDate = null;
            Intent intent = new Intent(AddTerm.this, Terms.class);
            startActivity(intent);
        } else {
            //TODO: Add error message
            Toast.makeText(this, "Title, start, and end dates are required", Toast.LENGTH_LONG)
                    .show();
            return;
        }
    }

    private void cancelSave() {
        Intent intent = new Intent(AddTerm.this,Terms.class);
        startActivity(intent);
    }
}