package com.android.c196.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "instructor_table")
public class Instructor {
    @PrimaryKey(autoGenerate = true)
    private int instrId;
    private int courseId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public Instructor() {
    }

    public Instructor(int courseId, String firstName, String lastName, String email, String phone) {
        this.courseId = courseId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
