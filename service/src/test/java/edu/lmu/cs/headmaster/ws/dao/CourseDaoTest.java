package edu.lmu.cs.headmaster.ws.dao;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import edu.lmu.cs.headmaster.ws.domain.Course;
import edu.lmu.cs.headmaster.ws.util.ApplicationContextTest;

import org.joda.time.DateTimeConstants;

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

}
