package com.android.c196.Course.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.android.c196.Term.Model.Term;

import java.util.Date;

@Entity(tableName = "course_table",
    foreignKeys = @ForeignKey(entity = Term.class,
    parentColumns = "termId",
    childColumns = "courseTermId"))
public class Course {
    @PrimaryKey(autoGenerate = true)
    private long courseId;
    private int courseTermId;
    private String courseTitle;
    private Date courseStartDate;
    private Date courseEndDate;
    private CourseStatus courseStatus;

    public Course() {

    }

    public Course(int termId, String courseTitle, Date courseStartDate, Date courseEndDate, CourseStatus courseStatus) {
        this.courseTermId = termId;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public int getCourseTermId() {
        return courseTermId;
    }

    public void setCourseTermId(int termId) {
        this.courseTermId = termId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Date getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(Date courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public Date getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(Date courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }

    @Override
    public String toString() {
        return courseTitle;
    }
}

