package com.android.c196.Assessment.Adapters;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.Assessment.Controllers.AddAssessment;
import com.android.c196.Assessment.Model.Assessment;
import com.android.c196.Assessment.Model.AssessmentType;
import com.android.c196.Assessment.Model.AssessmentViewModel;
import com.android.c196.R;
import com.android.c196.util.AlertReceiver;
import com.android.c196.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentHolder> {
    private List<Assessment> assessments = new ArrayList<>();
    private Context context;
    private Application application;
    private AssessmentViewModel assessmentViewModel;

    public AssessmentAdapter(Context context, Application application) {
        this.context = context;
        this.application = application;
        assessmentViewModel = new AssessmentViewModel(this.application);
    }

    @NonNull
    @Override
    public AssessmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.assessment_recycler_view_row, parent, false);
        return new AssessmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentHolder holder, int position) {
        Assessment assessment = assessments.get(position);
        holder.assessmentTitleText.setText(assessment.getAssessTitle());
        holder.assessmentStartText.setText(Utils.formatDate(assessment.getAssessStartDate()));
        holder.assessmentEndText.setText(Utils.formatDate(assessment.getAssessEndDate()));
        holder.assessmentTypeText.setText(showType(assessment.getAssessType()));

        //handle initial notification states
        boolean isStartAlarmSet = isAlarmSet(assessment.getAssessId());
        boolean isEndAlarmSet = isAlarmSet(assessment.getAssessId() + 1000000);

        // Update the ImageView src based on the alarm status
        if (isStartAlarmSet) {
            holder.assessStartNotify.setImageResource(R.drawable.notifications_black_24dp);
            holder.assessStartNotify.setTag(R.drawable.notifications_black_24dp);
        } else {
            holder.assessStartNotify.setImageResource(R.drawable.notifications_off_black_24dp);
            holder.assessStartNotify.setTag(R.drawable.notifications_off_black_24dp);
        }

        if (isEndAlarmSet) {
            holder.assessEndNotify.setImageResource(R.drawable.notifications_black_24dp);
            holder.assessEndNotify.setTag(R.drawable.notifications_black_24dp);
        } else {
            holder.assessEndNotify.setImageResource(R.drawable.notifications_off_black_24dp);
            holder.assessEndNotify.setTag(R.drawable.notifications_off_black_24dp);
        }

        holder.assessmentHeader.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //toggle detail display
                if (holder.assessmentDetailsDisplay.getVisibility() == View.GONE) {
                    holder.assessmentDetailsDisplay.setVisibility(View.VISIBLE);
                } else {
                    holder.assessmentDetailsDisplay.setVisibility(View.GONE);
                }
            }
        });
        holder.deleteAssessmentIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                assessmentViewModel.deleteAssessment(assessment);

            }
        });

        holder.editAssessmentIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long courseId = assessment.getAssessCourseId();
                Intent intent = new Intent(context, AddAssessment.class);
                intent.putExtra("courseId", courseId);
                intent.putExtra("assessId", assessment.getAssessId());
                context.startActivity(intent);
            }
        });

        holder.assessStartNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the notification image
                Integer currentDrawableId = (Integer) holder.assessStartNotify.getTag();


                //turn on if off
                if (currentDrawableId == null || currentDrawableId.equals(R.drawable.notifications_off_black_24dp)) {
                    // Change the ImageView src to notifications_black_24dp
                    holder.assessStartNotify.setImageResource(R.drawable.notifications_black_24dp);
                    holder.assessStartNotify.setTag(R.drawable.notifications_black_24dp);
                    setStartAlarm(assessment);
                } else {
                    // turn off if on
                    holder.assessStartNotify.setImageResource(R.drawable.notifications_off_black_24dp);
                    holder.assessStartNotify.setTag(R.drawable.notifications_off_black_24dp);
                    cancelStartAlarm(assessment);
                }
            }
        });

        holder.assessEndNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current drawable resource ID
                Integer currentDrawableId = (Integer) holder.assessEndNotify.getTag();

                // If the current drawable is notifications_off_black_24dp
                if (currentDrawableId == null || currentDrawableId.equals(R.drawable.notifications_off_black_24dp)) {
                    // Change the ImageView src to notifications_black_24dp
                    holder.assessEndNotify.setImageResource(R.drawable.notifications_black_24dp);
                    holder.assessEndNotify.setTag(R.drawable.notifications_black_24dp);
                    setEndAlarm(assessment);
                } else {
                    // Change the ImageView src to notifications_off_black_24dp
                    holder.assessEndNotify.setImageResource(R.drawable.notifications_off_black_24dp);
                    holder.assessEndNotify.setTag(R.drawable.notifications_off_black_24dp);
                    cancelEndAlarm(assessment);
                }
            }
        });
    }

    private boolean isAlarmSet(int requestCode) {
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE);
        return pendingIntent != null;
    }


    private void setEndAlarm(Assessment assessment) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        intent.putExtra("title", "Assessment End");
        intent.putExtra("message", "The assessment " + assessment.getAssessTitle() + " is ending today!");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, assessment.getAssessId() + 1000000, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, assessment.getAssessEndDate().getTime(), pendingIntent);
    }

    private void cancelEndAlarm(Assessment assessment) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, assessment.getAssessId() + 1000000, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(pendingIntent);
    }

    private void cancelStartAlarm(Assessment assessment) {
        // Get the AlarmManager system service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Create an intent to call the AlertReceiver
        Intent intent = new Intent(context, AlertReceiver.class);

        // Create a PendingIntent with a unique requestCode for the start date
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, assessment.getAssessId(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Cancel the alarm
        alarmManager.cancel(pendingIntent);

    }

    private void setStartAlarm(Assessment assessment) {
        // Get the AlarmManager system service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Create an intent to call the AlertReceiver
        Intent intent = new Intent(context, AlertReceiver.class);
        intent.putExtra("title", "Assessment Start");
        intent.putExtra("message", "The assessment " + assessment.getAssessTitle() + " is starting today!");

        // Create a PendingIntent with a unique requestCode for the start date
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, assessment.getAssessId(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Set the alarm to trigger at the start date
        alarmManager.set(AlarmManager.RTC_WAKEUP, assessment.getAssessStartDate().getTime(), pendingIntent);
    }

    private String showType(AssessmentType assessType) {
        if (assessType == AssessmentType.PERFORMANCE) {
            return "Performance";
        }
        return "Objective";
    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
        notifyDataSetChanged();
    }

    class AssessmentHolder extends RecyclerView.ViewHolder {
        ConstraintLayout assessmentHeader;
        TextView assessmentTitleText;
        ImageView editAssessmentIcon;
        ImageView deleteAssessmentIcon;
        LinearLayout assessmentDetailsDisplay;
        TextView assessmentStartText;
        ImageView assessStartNotify;
        TextView assessmentEndText;
        ImageView assessEndNotify;
        TextView assessmentTypeText;

        public AssessmentHolder(@NonNull View itemView) {
            super(itemView);

            assessmentHeader = itemView.findViewById(R.id.assessmentHeader);
            assessmentTitleText = itemView.findViewById(R.id.assessmentTitleText);
            editAssessmentIcon = itemView.findViewById(R.id.editAssessmentIcon);
            deleteAssessmentIcon = itemView.findViewById(R.id.deleteAssessmentIcon);
            assessmentDetailsDisplay = itemView.findViewById(R.id.assessmentDetailsDisplay);
            assessmentEndText = itemView.findViewById(R.id.assessmentEndText);
            assessmentTypeText = itemView.findViewById(R.id.assessmentTypeText);
            assessmentStartText = itemView.findViewById(R.id.assessmentStartText);
            assessStartNotify = itemView.findViewById(R.id.assessStartNotify);
            assessEndNotify = itemView.findViewById(R.id.assessEndNotify);

            assessmentDetailsDisplay.setVisibility(View.GONE);
        }
    }
}
