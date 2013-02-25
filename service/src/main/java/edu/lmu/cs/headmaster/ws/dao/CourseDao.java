package edu.lmu.cs.headmaster.ws.dao;

import java.util.List;

import edu.lmu.cs.headmaster.ws.domain.Course;

public interface CourseDao {

    Course getCourseById(Long id);
    
    List<Course> getCourses(String query, int skip, int max);
    
    Course createCourse(Course course);
    
    void createOrUpdateCourse(Course course);
}
