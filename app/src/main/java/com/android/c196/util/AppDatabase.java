package com.android.c196.util;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.android.c196.model.Assessment;
import com.android.c196.model.Course;
import com.android.c196.model.Instructor;
import com.android.c196.model.Term;

@Database(entities = {Term.class, Course.class, Instructor.class, Assessment.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract InstructorDao instructorDao();
    public abstract AssessmentDao assessmentDao();
    public static final String DATABASE_NAME = "app_database";
    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if(instance ==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
