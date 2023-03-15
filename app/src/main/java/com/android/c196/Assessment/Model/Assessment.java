package com.android.c196.Assessment.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assess_table")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessId;
    private int courseId;
    private String assessTitle;
    private Date assessEndDate;
    private AssessmentType assessType;

    public  Assessment() {

    }

    public Assessment(int courseId, String assessTitle, Date assessEndDate, AssessmentType assessType) {
        this.courseId = courseId;
        this.assessTitle = assessTitle;
        this.assessEndDate = assessEndDate;
        this.assessType = assessType;
    }


    public int getAssessId() {
        return assessId;
    }
    public void setAssessId(int assessId) {
        this.assessId = assessId;
    };

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getAssessTitle() {
        return assessTitle;
    }

    public void setAssessTitle(String assessTitle) {
        this.assessTitle = assessTitle;
    }

    public Date getAssessEndDate() {
        return assessEndDate;
    }

    public void setAssessEndDate(Date assessEndDate) {
        this.assessEndDate = assessEndDate;
    }

    public AssessmentType getAssessType() {
        return assessType;
    }

    public void setAssessType(AssessmentType assessType) {
        this.assessType = assessType;
    }
}
