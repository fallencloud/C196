package com.android.c196.Course.Adapters;

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

import com.android.c196.Course.Model.Course;
import com.android.c196.R;
import com.android.c196.model.CourseStatus;
import com.android.c196.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    private List<Course> courses = new ArrayList<>();
    private Context context;

    //constructor
    public CourseAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.course_recycler_view_row, parent, false);
        return new CourseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        if(courses != null) {
            Course course = courses.get(position);
            holder.courseTitleText.setText(course.getCourseTitle());
            holder.courseStartText.setText(Utils.formatDate(course.getCourseStartDate()));
            holder.courseEndText.setText(Utils.formatDate(course.getCourseEndDate()));
            holder.statusText.setText(showStatus(course.getCourseStatus()));

            holder.courseHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //toggle detail display
                    if(holder.courseDetailDisplay.getVisibility() == View.GONE) {
                        holder.courseDetailDisplay.setVisibility(View.VISIBLE);
                    } else {
                        holder.courseDetailDisplay.setVisibility(View.GONE);
                    }
                }
            });

            holder.deleteCourseIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

    private String showStatus(CourseStatus courseStatus) {
        switch (courseStatus) {
            case IN_PROGRESS:
                return "In Progress";
            case COMPLETED:
                return "Completed";
            case DROPPED:
                return "Dropped";
            default:
                return "Plan to take";
        }
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }


    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    class CourseHolder extends RecyclerView.ViewHolder {
        ConstraintLayout courseHeader;
        TextView courseTitleText;
        ImageView editCourseIcon;
        ImageView deleteCourseIcon;
        LinearLayout courseDetailDisplay;
        TextView courseStartText;
        TextView courseEndText;
        TextView statusText;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);

            courseHeader = itemView.findViewById(R.id.courseHeader);
            courseTitleText = itemView.findViewById(R.id.courseTitleText);
            editCourseIcon = itemView.findViewById(R.id.editCourseIcon);
            deleteCourseIcon = itemView.findViewById(R.id.deleteCourseIcon);
            courseDetailDisplay = itemView.findViewById(R.id.courseDetailsDisplay);
            courseStartText = itemView.findViewById(R.id.courseStartText);
            courseEndText = itemView.findViewById(R.id.courseEndText);
            statusText = itemView.findViewById(R.id.statusText);

            courseDetailDisplay.setVisibility(View.GONE);

        }
    }
}
