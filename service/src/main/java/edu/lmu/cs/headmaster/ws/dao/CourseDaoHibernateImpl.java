package edu.lmu.cs.headmaster.ws.dao;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.lmu.cs.headmaster.ws.dao.util.QueryBuilder;
import edu.lmu.cs.headmaster.ws.domain.Course;
import edu.lmu.cs.headmaster.ws.types.Term;

public class CourseDaoHibernateImpl extends HibernateDaoSupport implements CourseDao {

    @Override
    public Course getCourseById(Long id) {
        return getHibernateTemplate().get(Course.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Course> getCourses(String discipline, List<DateTime> classTimes, String instructor,
            Integer maxClassSize, Integer minClassSize, Term term, Integer year, int skip, int max) {
        QueryBuilder builder = new QueryBuilder("select c from Course c");

        if (discipline != null) {
            builder.clause("c.discipline = :field", discipline);
        }

        if (classTimes != null) {

        }

        if (instructor != null) {

        }

        if (maxClassSize != null) {

        }
        
        if (minClassSize != null) {
            
        }

        if (term != null) {
            builder.clause("c.term = :term", term);
        }

        if (year != null) {
            builder.clause("c.year = :year", year);
        }

        return builder.build(getSession()).setFirstResult(skip).setMaxResults(max).list();
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
