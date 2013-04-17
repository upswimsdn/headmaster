package edu.lmu.cs.headmaster.ws.service;

import java.util.List;

import org.joda.time.DateTime;

import edu.lmu.cs.headmaster.ws.domain.Course;
import edu.lmu.cs.headmaster.ws.types.Term;

public interface CourseService {

    List<Course> getCourses(String discipline, List<DateTime> classTimes, String instructor, Integer maxClassSize,
            Integer minClassSize, Term term, Integer year, int skip, int max);

    void createCourse(Course course);

    void createOrUpdateCourse(Long id, Course course);

    Course getCourseById(Long id);
}
