package com.android.c196.adapters;

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
import com.android.c196.model.Course;
import com.android.c196.model.CourseStatus;
import com.android.c196.util.Utils;

import java.util.List;

public class TermCourseAdapter extends RecyclerView.Adapter<TermCourseAdapter.CourseViewHolder> {
    private List<Course> courses;
    private Context context;
    private LayoutInflater inflater;

    //constructor
    public TermCourseAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.course_recycler_view_row, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
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

    class CourseViewHolder  extends RecyclerView.ViewHolder {
        ConstraintLayout courseHeader;
        TextView courseTitleText;
        ImageView editCourseIcon;
        ImageView deleteCourseIcon;
        LinearLayout courseDetailDisplay;
        TextView courseStartText;
        TextView courseEndText;
        TextView statusText;

        public CourseViewHolder(@NonNull View itemView) {
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
