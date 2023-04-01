package com.android.c196.Instructors.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.android.c196.Course.Models.Course;

@Entity(tableName = "instructor_table",
foreignKeys = @ForeignKey(entity = Course.class,
parentColumns = "courseId",
childColumns = "instrCourseId",
onDelete = ForeignKey.CASCADE))
public class Instructor {
    @PrimaryKey(autoGenerate = true)
    private int instrId;
    private long instrCourseId;
    private String instrName;
    private String email;
    private String phone;

    public Instructor(String instrName, String email, String phone, long instrCourseId) {
        this.instrName = instrName;
        this.email = email;
        this.phone = phone;
        this.instrCourseId = instrCourseId;
    }

    public int getInstrId() {
        return instrId;
    }

    public void setInstrId(int instrId) {
        this.instrId = instrId;
    }

    public long getInstrCourseId() {
        return instrCourseId;
    }

    public void setInstrCourseId(long courseId) {
        this.instrCourseId = courseId;
    }

    public String getInstrName() {
        return instrName;
    }

    public void setInstrName(String instrName) {
        this.instrName = instrName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "instrId=" + instrId +
                ", courseId=" + instrCourseId +
                ", name='" + instrName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
