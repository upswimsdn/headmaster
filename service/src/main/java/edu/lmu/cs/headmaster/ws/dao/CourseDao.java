package edu.lmu.cs.headmaster.ws.dao;

import java.util.List;

import org.joda.time.DateTime;

import edu.lmu.cs.headmaster.ws.domain.Course;
import edu.lmu.cs.headmaster.ws.types.Term;

public interface CourseDao {

    Course getCourseById(Long id);
    
    List<Course> getCourses(String fieldOfStudy, List<DateTime> classTimes, String instructor,
            Integer classSize, Term term, Integer year,int skip, int max);
    
    Course createCourse(Course course);
    
    void createOrUpdateCourse(Course course);
}
