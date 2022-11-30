package com.android.c196.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.R;
import com.android.c196.model.Course;

import java.util.List;

public class Course_RecyclerViewAdapter extends RecyclerView.Adapter<Course_RecyclerViewAdapter.CourseViewHolder> {
    private List<Course> courses;
    private Context context;
    private LayoutInflater inflater;

    //constructor
    public Course_RecyclerViewAdapter(Context context) {
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

        }

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    class CourseViewHolder  extends RecyclerView.ViewHolder {

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
