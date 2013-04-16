package edu.lmu.cs.headmaster.ws.service;

import junit.framework.Assert;

import org.joda.time.DateTimeConstants;
import org.junit.Before;
import org.junit.Test;

import edu.lmu.cs.headmaster.ws.domain.Course;
import edu.lmu.cs.headmaster.ws.util.ApplicationContextTest;

public class CourseServiceTest extends ApplicationContextTest {

    private CourseService courseService;

    @Before
    public void setUp() {
        courseService = (CourseService) applicationContext.getBean("courseService");
    }

    @Test
    public void getCourseById() {
        Course c = courseService.getCourseById(100001L);
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

        Assert.assertEquals(2, c.getEnrolledStudents().size());
        Assert.assertEquals(Long.valueOf(1000000L), c.getEnrolledStudents().get(0).getId());
        Assert.assertEquals(Long.valueOf(1000001L), c.getEnrolledStudents().get(1).getId());

        Assert.assertEquals(c.getClassLength().getMillis(), 3000000L);
    }

}
