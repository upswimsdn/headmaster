package edu.lmu.cs.headmaster.ws.resource;

import junit.framework.Assert;

import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.test.framework.AppDescriptor;

import edu.lmu.cs.headmaster.ws.domain.User;
import edu.lmu.cs.headmaster.ws.types.Role;
import edu.lmu.cs.headmaster.ws.util.DomainObjectUtils;

public class UserNonPrivilegedUserTest extends ResourceTest {

    @Override
    protected AppDescriptor configure() {
        return configure("edu.lmu.cs.headmaster.ws.resource.SecurityContextNonPrivilegedUserContainerRequestFilter");
    }

    @Test
    public void getWrongUserWithoutHeadmasterPrivileges() {
        // Users may only access themselves. The test fixtures logs in as "testuser".
        ClientResponse response = wr.path("users/login/admin").get(ClientResponse.class);

        // We should get a 404, so as not to give away an existing user.
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("404 " + UserResource.USER_NOT_FOUND, response.getEntity(String.class));
    }

    @Test
    public void testGetCorrectUserByLogin() {
        // The test fixture does not contain the testuser, so we need to create
        // it first (we assume here that creation works properly; verifying
        // *that* is the job of other unit tests).
        User user = DomainObjectUtils.createUserObject("testuser", "testuser@headmaster.test", "testpassword",
                Role.STUDENT);
        wr.path("users").post(user);

        // Now we test.
        User responseUser = wr.path("users/login/testuser").get(ClientResponse.class).getEntity(User.class);
        Assert.assertEquals(user.getLogin(), responseUser.getLogin());
        Assert.assertEquals(user.getEmail(), responseUser.getEmail());
        Assert.assertEquals(user.isActive(), responseUser.isActive());
        Assert.assertEquals(1, responseUser.getRoles().size());
        Assert.assertEquals(user.getRoles().get(0).getRole(), responseUser.getRoles().get(0).getRole());

        // Per our database fixture, we know the user ID to expect.
        Assert.assertEquals(Long.valueOf(1L), responseUser.getId());

        // The exception: challenge should not ride along.
        Assert.assertNull(responseUser.getChallenge());
    }

}
