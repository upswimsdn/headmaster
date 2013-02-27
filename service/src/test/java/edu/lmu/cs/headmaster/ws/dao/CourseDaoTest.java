package edu.lmu.cs.headmaster.ws.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.junit.Before;
import org.junit.Test;

import edu.lmu.cs.headmaster.ws.domain.Course;
import edu.lmu.cs.headmaster.ws.domain.Student;
import edu.lmu.cs.headmaster.ws.types.Term;
import edu.lmu.cs.headmaster.ws.util.ApplicationContextTest;

public class CourseDaoTest extends ApplicationContextTest {

    private CourseDao courseDao;

    @Before
    public void getRequiredBeans() {
        courseDao = (CourseDao) applicationContext.getBean("courseDao");
    }

    @Test
    public void testGetCourseById() {
        Course c = courseDao.getCourseById(100001L);
        Assert.assertEquals(Long.valueOf(100001L), c.getId());
        Assert.assertEquals("Dr. Dondi", c.getInstructor());

        Assert.assertEquals(3, c.getClassTimes().size());
        Assert.assertEquals(DateTimeConstants.MONDAY, c.getClassTimes().get(0).getDayOfWeek());
        Assert.assertEquals(11, c.getClassTimes().get(0).getHourOfDay());
        Assert.assertEquals(DateTimeConstants.WEDNESDAY, c.getClassTimes().get(1).getDayOfWeek());
        Assert.assertEquals(11, c.getClassTimes().get(1).getHourOfDay());
        Assert.assertEquals(DateTimeConstants.MONDAY, c.getClassTimes().get(0).getDayOfWeek());
        Assert.assertEquals(11, c.getClassTimes().get(0).getHourOfDay());
        Assert.assertEquals(DateTimeConstants.WEDNESDAY, c.getClassTimes().get(1).getDayOfWeek());
        Assert.assertEquals(11, c.getClassTimes().get(1).getHourOfDay());
        Assert.assertEquals(DateTimeConstants.FRIDAY, c.getClassTimes().get(2).getDayOfWeek());
        Assert.assertEquals(11, c.getClassTimes().get(2).getHourOfDay());

        Assert.assertEquals(1, c.getEnrolledStudents().size());
        Assert.assertEquals(Long.valueOf(1000000L), c.getEnrolledStudents().get(0).getId());

        Assert.assertEquals(c.getClassLength().getMillis(), 3000000L);
    }

    @Test
    public void testUpdatedCourseFieldsCanBeRead() {
        Course c = courseDao.getCourseById(100002L);
        Assert.assertEquals(Long.valueOf(100002L), c.getId());
        Assert.assertEquals("Prof. X", c.getInstructor());

        c.setInstructor("Dolores Umbridge");
        courseDao.createOrUpdateCourse(c);

        c = courseDao.getCourseById(100002L);
        Assert.assertEquals(Long.valueOf(100002L), c.getId());
        Assert.assertEquals("Dolores Umbridge", c.getInstructor());
    }

    @Test
    public void testEnrollingNewStudentToCourse() {
        Course beforeCourse = courseDao.getCourseById(100001L);
        Assert.assertEquals(1, beforeCourse.getEnrolledStudents().size());
        Student student = new Student();
        student.setId(1000001L);
        beforeCourse.getEnrolledStudents().add(student);
        courseDao.createOrUpdateCourse(beforeCourse);
        Course afterCourse = courseDao.getCourseById(100001L);
        Assert.assertEquals(2, afterCourse.getEnrolledStudents().size());
        Assert.assertEquals(Long.valueOf(1000001L), afterCourse.getEnrolledStudents().get(1).getId());
    }

    @Test
    public void testGetCoursesByDiscipline() {
        List<Course> courses = courseDao.getCourses("Computer Science", null, null, null, null, null, null, 0, 10);
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(0).getId());
    }

    @Test
    public void testGetCoursesByTerm() {
        List<Course> courses = courseDao.getCourses(null, null, null, null, null, Term.SUMMER, null, 0, 10);
        Assert.assertEquals(2, courses.size());
        Assert.assertEquals(Long.valueOf(100001L), courses.get(0).getId());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(1).getId());
    }

    public void testGetCoursesByYear() {
        List<Course> courses = courseDao.getCourses(null, null, null, null, null, null, 2013, 0, 10);
        Assert.assertEquals(2, courses.size());
        Assert.assertEquals(Long.valueOf(100002L), courses.get(0).getId());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(1).getId());
    }

    @Test
    public void testGetCoursesByTermAndYear() {
        List<Course> courses = courseDao.getCourses(null, null, null, null, null, Term.SUMMER, 2013, 0, 10);
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(0).getId());
    }

    @Test
    public void testGetCoursesByClassTime() {
        List<DateTime> times = new ArrayList<DateTime>();
        times.add(new DateTime(2013, 2, 18, 11, 0, 0, 0));
        times.add(new DateTime(2013, 2, 20, 11, 0, 0, 0));
        List<Course> courses = courseDao.getCourses(null, times, null, null, null, null, null, 0, 10);
        Assert.assertEquals(1, courses.size());

        times.clear();
        times.add(new DateTime(2013, 2, 20, 12, 0, 0, 0));
        courses = courseDao.getCourses(null, times, null, null, null, null, null, 0, 10);
        Assert.assertEquals(0, courses.size());

        times.add(new DateTime(2013, 2, 20, 12, 0, 0, 0));
        times.add(new DateTime(2013, 2, 18, 11, 0, 0, 0));
        courses = courseDao.getCourses(null, times, null, null, null, null, null, 0, 10);
        Assert.assertEquals(1, courses.size());
    }

    @Test
    public void testGetCoursesByMaxClassSize() {
        List<Course> courses = courseDao.getCourses(null, null, null, 1, null, null, null, 0, 10);
        Assert.assertEquals(0, courses.size());

        courses = courseDao.getCourses(null, null, null, 10, null, null, null, 0, 10);
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(0).getId());

        courses = courseDao.getCourses(null, null, null, 10, null, null, null, 0, 10);
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(0).getId());
    }

    @Test
    public void testGetCoursesByMinClassSize() {
        List<Course> courses = courseDao.getCourses(null, null, null, null, 10, null, null, 0, 10);
        Assert.assertEquals(3, courses.size());
        Assert.assertEquals(Long.valueOf(100001L), courses.get(0).getId());
        Assert.assertEquals(Long.valueOf(100002L), courses.get(1).getId());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(2).getId());

        courses = courseDao.getCourses(null, null, null, null, 20, null, null, 0, 10);
        Assert.assertEquals(2, courses.size());
        Assert.assertEquals(Long.valueOf(100001L), courses.get(0).getId());
        Assert.assertEquals(Long.valueOf(100002L), courses.get(1).getId());

        courses = courseDao.getCourses(null, null, null, null, 30, null, null, 0, 10);
        Assert.assertEquals(0, courses.size());
    }

    @Test
    public void testGetCoursesByMinAndMaxClassSize() {
        List<Course> courses = courseDao.getCourses(null, null, null, 15, 10, null, null, 0, 10);
        Assert.assertEquals(1, courses.size());

        courses = courseDao.getCourses(null, null, null, 30, 25, null, null, 0, 10);
        Assert.assertEquals(0, courses.size());
    }

    @Test
    public void testGetCoursesByInstructor() {
        List<Course> courses = courseDao.getCourses(null, null, "Prof. X", null, null, null, null, 0, 10);
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100002L), courses.get(0).getId());
        Assert.assertEquals("Prof. X", courses.get(0).getInstructor());

        courses = courseDao.getCourses(null, null, "prof. x", null, null, null, null, 0, 10);
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100002L), courses.get(0).getId());
        Assert.assertEquals("Prof. X", courses.get(0).getInstructor());

        courses = courseDao.getCourses(null, null, "asgsad", null, null, null, null, 0, 10);
        Assert.assertEquals(0, courses.size());
    }

}
