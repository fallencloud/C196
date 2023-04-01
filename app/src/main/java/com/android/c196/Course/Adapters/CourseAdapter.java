package com.android.c196.Course.Adapters;

import android.app.Application;
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

import com.android.c196.Course.Controllers.AddCourse;
import com.android.c196.Course.Controllers.CourseDetailed;
import com.android.c196.Course.Models.Course;
import com.android.c196.Course.Models.CourseStatus;
import com.android.c196.Course.Models.CourseViewModel;
import com.android.c196.R;
import com.android.c196.Term.Controllers.Terms;
import com.android.c196.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    private List<Course> courses = new ArrayList<>();
    private Context context;
    private Application application;
    CourseViewModel courseViewModel;

    //constructor
    public CourseAdapter(Context context, Application application) {

        this.context = context;
        this.application = application;
        courseViewModel = new CourseViewModel(this.application);

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
                    courseViewModel.deleteCourse(course);
                    Intent intent = new Intent(context, Terms.class);
                    context.startActivity(intent);
                }
            });

            holder.editCourseIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddCourse.class);
                    intent.putExtra("courseId", course.getCourseId());
                    context.startActivity(intent);
                }
            });

            holder.detailedCourseIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CourseDetailed.class);
                    intent.putExtra("courseId", course.getCourseId());
                    context.startActivity(intent);
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
        ImageView detailedCourseIcon;
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
            detailedCourseIcon = itemView.findViewById(R.id.detailedCourseIcon);
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
