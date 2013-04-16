package edu.lmu.cs.headmaster.ws.resource;

import java.util.List;

import junit.framework.Assert;

import org.joda.time.DateTimeConstants;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

import edu.lmu.cs.headmaster.ws.domain.Course;
import edu.lmu.cs.headmaster.ws.domain.Student;
import edu.lmu.cs.headmaster.ws.types.Term;

public class CourseResourceTest extends ResourceTest {

    @Test
    public void testGetCourseById() {
        Course c = wr.path("/courses/100001").get(Course.class);

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

    @Test
    public void testGetNonExistantCourseByIdResponds404() {
        ClientResponse response = wr.path("courses/8008135").get(ClientResponse.class);
        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void testGetCourseWithNoQueryResponds400() {
        ClientResponse response = wr.path("courses").get(ClientResponse.class);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals("400 " + AbstractResource.QUERY_REQUIRED, response.getEntity(String.class));
    }

    @Test
    public void testMutualInclusionOfTermAndYearQueryParameters() {
        ClientResponse response = wr.path("courses")
                .queryParam("term", "FALL")
                .get(ClientResponse.class);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals("400 " + AbstractResource.ARGUMENT_CONFLICT, response.getEntity(String.class));

        response = wr.path("courses")
                .queryParam("year", "2012")
                .get(ClientResponse.class);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals("400 " + AbstractResource.ARGUMENT_CONFLICT, response.getEntity(String.class));

        response = wr.path("courses")
                .queryParam("term", "SUMMER")
                .queryParam("year", "2014")
                .get(ClientResponse.class);
        Assert.assertTrue(response.getStatus() >= 200);
    }

    @Test
    public void testGetCoursesByDiscipline() {
        List<Course> courses = wr.path("courses")
                .queryParam("discipline", "Computer Science")
                .get(new GenericType<List<Course>>(){});
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(0).getId());
        Assert.assertEquals("Computer Science", courses.get(0).getDiscipline());
    }

    @Test
    public void testGetCoursesByTermAndYear() {
        List<Course> courses = wr.path("courses")
                .queryParam("term", "SUMMER")
                .queryParam("year", "2013")
                .get(new GenericType<List<Course>>(){});
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(0).getId());
        Assert.assertEquals(Term.SUMMER, courses.get(0).getTerm());
        Assert.assertEquals(Integer.valueOf(2013), courses.get(0).getYear());
    }
    
    @Test
    public void testGetCoursesByMaxClassSize() {
        List<Course> courses = wr.path("courses")
                .queryParam("maxClassSize", "1")
                .get(new GenericType<List<Course>>(){});
        Assert.assertEquals(0, courses.size());

        courses = wr.path("courses")
                .queryParam("maxClassSize", "10")
                .get(new GenericType<List<Course>>(){});
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(0).getId());

        courses = wr.path("courses")
                .queryParam("maxClassSize", "20")
                .get(new GenericType<List<Course>>(){});
        Assert.assertEquals(3, courses.size());
        Assert.assertEquals(Long.valueOf(100001L), courses.get(0).getId());
        Assert.assertEquals(Long.valueOf(100002L), courses.get(1).getId());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(2).getId());
    }

    @Test
    public void testGetCoursesByMinClassSize() {
        List<Course> courses = wr.path("courses")
                .queryParam("minClassSize", "10")
                .get(new GenericType<List<Course>>(){});
        Assert.assertEquals(3, courses.size());
        Assert.assertEquals(Long.valueOf(100001L), courses.get(0).getId());
        Assert.assertEquals(Long.valueOf(100002L), courses.get(1).getId());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(2).getId());

        courses = wr.path("courses")
                .queryParam("minClassSize", "20")
                .get(new GenericType<List<Course>>(){});
        Assert.assertEquals(2, courses.size());
        Assert.assertEquals(Long.valueOf(100001L), courses.get(0).getId());
        Assert.assertEquals(Long.valueOf(100002L), courses.get(1).getId());

        courses = wr.path("courses")
                .queryParam("minClassSize", "30")
                .get(new GenericType<List<Course>>(){});
        Assert.assertEquals(0, courses.size());
    }

    @Test
    public void testGetCoursesByMinAndMaxClassSize() {
        List<Course> courses = wr.path("courses")
                .queryParam("minClassSize", "10")
                .queryParam("maxClassSize", "15")
                .get(new GenericType<List<Course>>(){});
        Assert.assertEquals(1, courses.size());

        courses = wr.path("courses")
                .queryParam("minClassSize", "30")
                .queryParam("maxClassSize", "25")
                .get(new GenericType<List<Course>>(){});
        Assert.assertEquals(0, courses.size());
    }

    @Test
    public void testGetCoursesByInstructor() {
        List<Course> courses = wr.path("courses")
                .queryParam("instructor", "Prof. X")
                .get(new GenericType<List<Course>>(){});
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100002L), courses.get(0).getId());
        Assert.assertEquals("Prof. X", courses.get(0).getInstructor());

        courses = wr.path("courses")
                .queryParam("instructor", "prof. x")
                .get(new GenericType<List<Course>>(){});
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100002L), courses.get(0).getId());
        Assert.assertEquals("Prof. X", courses.get(0).getInstructor());

        courses = wr.path("courses")
                .queryParam("instructor", "asgsad")
                .get(new GenericType<List<Course>>(){});
        Assert.assertEquals(0, courses.size());
    }

}
