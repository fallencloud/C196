package com.android.c196.util;

import androidx.room.TypeConverter;

import com.android.c196.model.CourseStatus;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static CourseStatus toCourseStatus(String courseStatus) {
        return courseStatus == null ? null : CourseStatus.valueOf(courseStatus);
    }

    @TypeConverter
    public static String fromCourseStatus(CourseStatus courseStatus) {
        return courseStatus == null ? null : courseStatus.name();
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Date fromTimestamp(Long date) {
        return date == null ? null : new Date(date);
    }
}
