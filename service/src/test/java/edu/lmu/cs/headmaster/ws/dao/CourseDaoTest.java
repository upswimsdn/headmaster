package edu.lmu.cs.headmaster.ws.dao;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import edu.lmu.cs.headmaster.ws.domain.Course;
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
    }

}
