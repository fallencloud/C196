package com.android.c196.Assessment.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.android.c196.Course.Models.Course;

import java.util.Date;

@Entity(tableName = "assess_table",
        foreignKeys = @ForeignKey(entity = Course.class,
        parentColumns = "courseId",
        childColumns = "assessCourseId",
        onDelete = ForeignKey.CASCADE)
)
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessId;
    private long assessCourseId;
    private String assessTitle;
    private Date assessStartDate;
    private Date assessEndDate;
    private AssessmentType assessType;

    public Assessment(long assessCourseId, String assessTitle, Date assessStartDate, Date assessEndDate, AssessmentType assessType) {
        this.assessCourseId = assessCourseId;
        this.assessTitle = assessTitle;
        this.assessStartDate = assessStartDate;
        this.assessEndDate = assessEndDate;
        this.assessType = assessType;
    }

    public int getAssessId() {
        return assessId;
    }
    public void setAssessId(int assessId) {
        this.assessId = assessId;
    };

    public long getAssessCourseId() {
        return assessCourseId;
    }

    public void setAssessCourseId(long assessCourseId) {
        this.assessCourseId = assessCourseId;
    }

    public String getAssessTitle() {
        return assessTitle;
    }

    public void setAssessTitle(String assessTitle) {
        this.assessTitle = assessTitle;
    }

    public Date getAssessStartDate() {
        return assessStartDate;
    }

    public void setAssessStartDate(Date assessStartDate) {
        this.assessStartDate = assessStartDate;
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
