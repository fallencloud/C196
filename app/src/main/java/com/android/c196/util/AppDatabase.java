package com.android.c196.util;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.android.c196.Assessment.Model.Assessment;
import com.android.c196.Assessment.Model.AssessmentType;
import com.android.c196.Course.Model.Course;
import com.android.c196.Term.Model.Term;
import com.android.c196.model.CourseStatus;
import com.android.c196.model.Instructor;
import com.android.c196.Note.Model.Note;

import java.util.Calendar;
import java.util.Date;

@Database(entities = {Term.class, Course.class, Instructor.class, Assessment.class, Note.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract InstructorDao instructorDao();
    public abstract AssessmentDao assessmentDao();
    public abstract NoteDao noteDao();

    public static final String DATABASE_NAME = "app_database";
    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if(instance ==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(fillDbCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback fillDbCallback = new RoomDatabase.Callback()  {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new SeedDatabaseAsyncTask(instance).execute();
        }
    };

    private static class SeedDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        private TermDao termDao;
        private CourseDao courseDao;
        private AssessmentDao assessmentDao;

        private SeedDatabaseAsyncTask(AppDatabase db) {
            termDao = db.termDao();
            courseDao = db.courseDao();
            assessmentDao = db.assessmentDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Create dates
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2023);
            calendar.set(Calendar.MONTH, 2);
            calendar.set(Calendar.DATE, 1);

            Date start = calendar.getTime();
            calendar.set(Calendar.DATE, 30);
            Date end = calendar.getTime();
            Term spring = new Term("Spring", start, end);
            termDao.insertTerm(spring);

            int termId = spring.getTermId();
            Course finalCourse = new Course(termId, "final", start, end, CourseStatus.IN_PROGRESS);
            courseDao.insertCourse(finalCourse);

            int courseId = finalCourse.getCourseId();
            Assessment assessTest = new Assessment(courseId, "Final Test", end, AssessmentType.OBJECTIVE);
            assessmentDao.insertAssessment(assessTest);
            return null;
        }
    }
}
