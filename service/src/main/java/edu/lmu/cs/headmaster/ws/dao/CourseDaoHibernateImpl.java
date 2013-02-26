package edu.lmu.cs.headmaster.ws.dao;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.lmu.cs.headmaster.ws.domain.Course;
import edu.lmu.cs.headmaster.ws.types.Term;

public class CourseDaoHibernateImpl extends HibernateDaoSupport implements CourseDao {

    @Override
    public Course getCourseById(Long id) {
        return getHibernateTemplate().get(Course.class, id);
    }

    @Override
    public List<Course> getCourses(String fieldOfStudy, List<DateTime> classTimes, String instructor,
            Integer classSize, Term term, Integer year, int skip, int max) {
        return null;
    }

    @Override
    public Course createCourse(Course course) {
        getHibernateTemplate().save(course);
        return course;
    }

    @Override
    public void createOrUpdateCourse(Course course) {
        getHibernateTemplate().saveOrUpdate(course);
    }

}