package edu.lmu.cs.headmaster.ws.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.lmu.cs.headmaster.ws.domain.Course;

public class CourseDaoHibernateImpl extends HibernateDaoSupport implements CourseDao {

    @Override
    public Course getCourseById(Long id) {
        return getHibernateTemplate().get(Course.class, id);
    }

    @Override
    public List<Course> getCourses(String query, int skip, int max) {
        // TODO Auto-generated method stub
        // Possible queries: by discipline, term / year, teacher, credits, title,
        // class size, empty/full, id, start time, end time, more tba.
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
