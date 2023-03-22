package com.android.c196.Instructors.Adapters;

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

import com.android.c196.R;
import com.android.c196.model.Instructor;

import java.util.ArrayList;
import java.util.List;

public class CourseInstructorAdapter extends RecyclerView.Adapter<CourseInstructorAdapter.InstructorHolder> {
    private List<Instructor> instructors = new ArrayList<>();
    private Context context;

    public CourseInstructorAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CourseInstructorAdapter.InstructorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.instructor_recycler_view_row, parent, false);
        return new InstructorHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseInstructorAdapter.InstructorHolder holder, int position) {
        if (instructors != null ) {
            Instructor instructor = instructors.get(position);
            holder.instructorName.setText(instructor.getInstrName());
            holder.instructorPhoneText.setText(instructor.getPhone());
            holder.instructorEmailText.setText(instructor.getEmail());
        }


    }

    @Override
    public int getItemCount() {
        return instructors.size();
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
        notifyDataSetChanged();
    }

    class InstructorHolder extends RecyclerView.ViewHolder {
        ConstraintLayout instructorHeader;
        LinearLayout instructorDetailsDisplay;
        TextView instructorName;
        ImageView editInstructorIcon;
        ImageView deleteInstructorIcon;
        TextView instructorPhoneText;
        TextView instructorEmailText;

        public InstructorHolder(@NonNull View itemView) {
            super(itemView);

            instructorHeader = itemView.findViewById(R.id.instructorHeader);
            instructorDetailsDisplay = itemView.findViewById(R.id.instructorDetailsDisplay);
            instructorName = itemView.findViewById(R.id.instructorName);
            editInstructorIcon = itemView.findViewById(R.id.editInstructorIcon);
            deleteInstructorIcon = itemView.findViewById(R.id.deleteInstructorIcon);
            instructorPhoneText = itemView.findViewById(R.id.instructorPhoneText);
            instructorEmailText = itemView.findViewById(R.id.instructorEmailText);

        }

    }
}
