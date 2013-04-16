package edu.lmu.cs.headmaster.ws.service;

import java.util.List;

import org.joda.time.DateTime;

import edu.lmu.cs.headmaster.ws.dao.CourseDao;
import edu.lmu.cs.headmaster.ws.domain.Course;
import edu.lmu.cs.headmaster.ws.types.Term;

public class CourseServiceImpl implements CourseService {

    private CourseDao courseDao;

    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public List<Course> getCourses(String discipline, String classTimes, String instructor,
            Integer maxClassSize, Integer minClassSize, Term term, Integer year, int skip, int max) {

        List<DateTime> schedule = null;
        return courseDao.getCourses(discipline, schedule, instructor, maxClassSize, minClassSize, term, year, skip,
                max);
    }

    @Override
    public void createCourse(Course course) {
        courseDao.createOrUpdateCourse(course);
    }

    @Override
    public void createOrUpdateCourse(Long id, Course course) {
        courseDao.createOrUpdateCourse(course);
    }

    @Override
    public Course getCourseById(Long id) {
       return courseDao.getCourseById(id);
    }

}
