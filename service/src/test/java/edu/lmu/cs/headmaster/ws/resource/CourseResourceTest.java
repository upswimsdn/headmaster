package edu.lmu.cs.headmaster.ws.resource;

import junit.framework.Assert;

import org.joda.time.DateTimeConstants;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;

import edu.lmu.cs.headmaster.ws.domain.Course;

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

}
