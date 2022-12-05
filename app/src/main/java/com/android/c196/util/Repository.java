package com.android.c196.util;

import android.app.Application;

import com.android.c196.model.Course;
import com.android.c196.model.Instructor;
import com.android.c196.model.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    //declare Daos
    private TermDao termDao;
    private CourseDao courseDao;
    private InstructorDao instructorDao;
    private List<Term> allTerms;
    private List<Course> allCourses;
    private List<Course> termCourses;
    private List<Instructor> allInstructors;
    private Term currentTerm;
    private Course currentCourse;
    private List<Instructor> courseInstructors;
    private Instructor currentInstr;


    public static int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //instantiate DB
    public Repository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        termDao = database.termDao();
        courseDao = database.courseDao();
        instructorDao = database.instructorDao();

    }

    //create public interfaces
    /****************
     * Term API
     **************/
    public List<Term> getAllTerms() {
        databaseExecutor.execute(() -> {
            allTerms = termDao.getAllTerms();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

    public List<Course> getAllCourses() {
        databaseExecutor.execute(() -> {
            allCourses = courseDao.getAllCourses();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return allCourses; }

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

    public List<Course> getTermCourses(int termId) {
        databaseExecutor.execute(() -> {
            termCourses = courseDao.getTermCourses(termId);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return termCourses;
    }

    Course getCourse(int courseId) {
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
     * Term API
     **************/

    public List<Instructor> getAllInstructors() {
        databaseExecutor.execute(() -> {
            allInstructors = instructorDao.getAllInstructors();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

    public List<Instructor> getCourseInstructors(int courseId) {
        databaseExecutor.execute(() -> {
            courseInstructors = instructorDao.getCourseInstructors(courseId);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
}
