package com.android.c196.util;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.android.c196.Assessment.Model.Assessment;
import com.android.c196.Course.Model.Course;
import com.android.c196.model.Instructor;
import com.android.c196.model.Note;
import com.android.c196.Term.Model.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    //declare Daos
    private TermDao termDao;
    private CourseDao courseDao;
    private InstructorDao instructorDao;
    private AssessmentDao assessmentDao;
    private NoteDao noteDao;
    private LiveData<List<Term>> allTerms;
    private LiveData<List<Course>> allCourses;
    private LiveData<List<Assessment>> allAssessments;
    private List<Course> termCourses;
    private LiveData<List<Instructor>> allInstructors;
    private LiveData<List<Assessment>> courseAssessments;
    private Term currentTerm;
    private Course currentCourse;
    private LiveData<List<Instructor>> courseInstructors;
    private Instructor currentInstr;
    private Assessment currentAssess;


    public static int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //instantiate DB
    public Repository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        termDao = database.termDao();
        allTerms = termDao.getAllTerms();

        courseDao = database.courseDao();
        allCourses = courseDao.getAllCourses();

        instructorDao = database.instructorDao();
        allInstructors = instructorDao.getAllInstructors();

        assessmentDao = database.assessmentDao();
        allAssessments = assessmentDao.getAllAssessments();

        noteDao = database.noteDao();

    }

    //create public interfaces
    /****************
     * Term API
     **************/
    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

    public void insertTerm(Term term) {
        databaseExecutor.execute(() -> termDao.insertTerm(term));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Term getTerm(int termId) {
        databaseExecutor.execute(() -> currentTerm = termDao.getTerm(termId));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return currentTerm;
    }

    public void updateTerm(Term term) {
        databaseExecutor.execute(() -> termDao.updateTerm(term));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteTerm(Term term) {
        databaseExecutor.execute(() -> termDao.deleteTerm(term));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /****************
     * Course API
     **************/

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public void insertCourse(Course course) {

        databaseExecutor.execute(() -> {
            courseDao.insertCourse(course);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public LiveData<List<Course>> getTermCourses(int termId) {
        return courseDao.getTermCourses(termId);
    }

    public Course getCourse(int courseId) {
        databaseExecutor.execute(() -> {
            currentCourse = courseDao.getCourse(courseId);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return currentCourse;
    }

    public void updateCourse(Course course) {
        databaseExecutor.execute(() -> {
            courseDao.updateCourse(course);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteCourse(Course course) {
        courseDao.deleteCourse(course);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /****************
     * Instructor API
     **************/

    public LiveData<List<Instructor>> getAllInstructors() {
        return allInstructors;
    }

    public void insertInstructor(Instructor instructor) {
        databaseExecutor.execute(() -> {
            instructorDao.insertInstructor(instructor);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public LiveData<List<Instructor>> getCourseInstructors(int courseId) {
       courseInstructors = instructorDao.getCourseInstructors(courseId);
        return courseInstructors;
    }

    public Instructor getInstructor(int instrId) {
        databaseExecutor.execute(() -> currentInstr = instructorDao.getInstructor(instrId));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return currentInstr;
    }

    public void updateInstructor(Instructor instructor) {
        databaseExecutor.execute(() -> instructorDao.updateInstructor(instructor));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteInstructor(Instructor instructor) {
        databaseExecutor.execute(() -> instructorDao.deleteInstructor(instructor));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /****************
     * Assessment API
     **************/
    public void insertAssessment(Assessment assessment) {
        databaseExecutor.execute(() -> assessmentDao.insertAssessment(assessment));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public LiveData<List<Assessment>> getAllAssessments() {
        return allAssessments;
    }

    public Assessment getAssessment(int assessId) {
        databaseExecutor.execute(() -> currentAssess = assessmentDao.getAssessment(assessId));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return currentAssess;
    }

    public LiveData<List<Assessment>> getCourseAssessments(int courseId) {
        return assessmentDao.getCourseAssessments(courseId);
    }

    public void updateAssessment(Assessment assessment) {
        databaseExecutor.execute(() -> assessmentDao.updateAssessment(assessment));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  void deleteAssessment(Assessment assessment) {
        databaseExecutor.execute(() -> assessmentDao.deleteAssessment(assessment));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /****************
     * Note API
     **************/
    public void insertNote(Note note) {
        databaseExecutor.execute(() -> noteDao.insertNote(note));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  LiveData<List<Note>> getCourseNotes(int courseId) {
        return noteDao.getCourseNotes(courseId);
    }

    public void updateNote(Note note) {
        databaseExecutor.execute(() -> noteDao.updateNote(note));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteNote(Note note) {
        databaseExecutor.execute(() -> noteDao.deleteNote(note));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
