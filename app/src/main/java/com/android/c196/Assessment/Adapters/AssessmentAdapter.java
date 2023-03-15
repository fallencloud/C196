package com.android.c196.Assessment.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.Assessment.Model.Assessment;
import com.android.c196.Assessment.Model.AssessmentType;
import com.android.c196.R;
import com.android.c196.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentHolder> {
    private List<Assessment> assessments = new ArrayList<>();
    private Context context;

    public AssessmentAdapter(Context context) {
        this.context = context;
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
        if (assessments != null) {
            Assessment assessment = assessments.get(position);
            holder.assessmentTitleText.setText(assessment.getAssessTitle());
            holder.assessmentEndText.setText(Utils.formatDate(assessment.getAssessEndDate() ));
            holder.assessmentTypeText.setText(showType(assessment.getAssessType()));

            holder.assessmentHeader.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //toggle detail display
                    if(holder.assessmentDetailsDisplay.getVisibility() == View.GONE) {
                        holder.assessmentDetailsDisplay.setVisibility(View.VISIBLE);
                    } else {
                        holder.assessmentDetailsDisplay.setVisibility(View.GONE);
                    }
                }
            });
        }
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

    class  AssessmentHolder extends RecyclerView.ViewHolder {
        ConstraintLayout assessmentHeader;
        TextView assessmentTitleText;
        ImageView editAssessmentIcon;
        ImageView deleteAssessmentIcon;
        LinearLayout assessmentDetailsDisplay;
        TextView assessmentEndText;
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

            assessmentDetailsDisplay.setVisibility(View.GONE);
        }
    }
}
