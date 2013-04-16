package edu.lmu.cs.headmaster.ws.service;

import java.util.List;

import org.joda.time.DateTimeConstants;

import edu.lmu.cs.headmaster.ws.domain.Course;
import edu.lmu.cs.headmaster.ws.types.Term;

public interface CourseService {

    static final int DEFAULT_MONTH = DateTimeConstants.FEBRUARY;
    static final int DEFAULT_YEAR = 2013;
    static final int DEFAULT_MONDAY = 18;

    List<Course> getCourses(String discipline, String classTimes, String instructor, Integer maxClassSize,
            Integer minClassSize, Term term, Integer year, int skip, int max);

    void createCourse(Course course);

    void createOrUpdateCourse(Long id, Course course);

    Course getCourseById(Long id);
}
