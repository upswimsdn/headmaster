package edu.lmu.cs.headmaster.ws.resource;

import org.junit.Assert;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.test.framework.AppDescriptor;

import edu.lmu.cs.headmaster.ws.domain.Course;

public class CourseResourceNonPrivilegedUserTest extends ResourceTest {

    @Override
    protected AppDescriptor configure() {
        return configure("edu.lmu.cs.headmaster.ws.resource.SecurityContextNonPrivilegedUserContainerRequestFilter");
    }
    
    @Test
    public void createCourseNotAsAdminThrowsException() {
        ClientResponse response = wr.path("courses").post(ClientResponse.class, new Course());
        Assert.assertEquals(403, response.getStatus());
        Assert.assertEquals("403 " + AbstractResource.USER_FORBIDDEN, response.getEntity(String.class));
    }
    
    @Test
    public void updateCourseNotAsAdminThrowsException() {
        Course c = wr.path("courses/100001").get(Course.class);
        ClientResponse response = wr.path("courses/100001").put(ClientResponse.class, c);
        Assert.assertEquals(403, response.getStatus());
        Assert.assertEquals("403 " + AbstractResource.USER_FORBIDDEN, response.getEntity(String.class));
    }
}
