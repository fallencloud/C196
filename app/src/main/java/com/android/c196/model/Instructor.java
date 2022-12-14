package com.android.c196.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "instructor_table")
public class Instructor {
    @PrimaryKey(autoGenerate = true)
    private int instrId;
    private int courseId;
    private String instrName;
    private String email;
    private String phone;

    public Instructor() {
    }

    public Instructor( String instrName, String email, String phone, int courseId) {
        this.instrName = instrName;
        this.email = email;
        this.phone = phone;
        this.courseId = courseId;
    }

    public int getInstrId() {
        return instrId;
    }

    public void setInstrId(int instrId) {
        this.instrId = instrId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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
                ", courseId=" + courseId +
                ", name='" + instrName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
