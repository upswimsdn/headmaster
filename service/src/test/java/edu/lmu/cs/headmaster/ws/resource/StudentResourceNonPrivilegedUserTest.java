package edu.lmu.cs.headmaster.ws.resource;

import org.junit.Assert;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.test.framework.AppDescriptor;

import edu.lmu.cs.headmaster.ws.domain.Student;

public class StudentResourceNonPrivilegedUserTest extends ResourceTest {

    @Override
    protected AppDescriptor configure() {
        return configure("edu.lmu.cs.headmaster.ws.resource.SecurityContextNonPrivilegedUserContainerRequestFilter");
    }

    @Test
    public void getStudentsByCumulativeGpaNotAsAdminThrowsException() {
        ClientResponse response = wr.path("students")
                .queryParam("cumulativeGpaFrom", "3.0")
                .queryParam("cumulativeGpaTo", "4.0")
                .get(ClientResponse.class);

        Assert.assertEquals(403, response.getStatus());
    }

    @Test
    public void getStudentNotTiedToUserResponds401() {
        ClientResponse response = wr.path("students/1000000").get(ClientResponse.class);
        Assert.assertEquals(401, response.getStatus());
        Assert.assertEquals("401 " + AbstractResource.USER_UNAUTHORIZED, response.getEntity(String.class));
    }

    @Test
    public void getStudentTiedToUser() {
        Student student = wr.path("students/1000001").get(Student.class);
        Assert.assertEquals(Long.valueOf(1000001L), student.getId());
        Assert.assertEquals("testuser", student.getLogin());
    }

    @Test
    public void getStudentRecordAsWrongUserResponds401() {
        ClientResponse response = wr.path("students/1000000/record").get(ClientResponse.class);
        Assert.assertEquals(401, response.getStatus());
        Assert.assertEquals("401 " + AbstractResource.USER_UNAUTHORIZED, response.getEntity(String.class));
    }

    @Test
    public void getStudentRecordAsLinkedUser() {
        ClientResponse response = wr.path("students/1000001/record").get(ClientResponse.class);
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void updateStudentRecordResponds401() {
        Student student = wr.path("students/1000001").get(Student.class);
        Assert.assertEquals(Long.valueOf(1000001L), student.getId());
        Assert.assertEquals("testuser", student.getLogin());

        ClientResponse response = wr.path("students/1000001/record").put(ClientResponse.class, student.getRecord());
        Assert.assertEquals(403, response.getStatus());
        Assert.assertEquals("403 " + AbstractResource.USER_FORBIDDEN, response.getEntity(String.class));
    }

}
