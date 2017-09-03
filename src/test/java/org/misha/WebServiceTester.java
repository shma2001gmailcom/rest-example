package org.misha;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.junit.Assert.assertTrue;
@Ignore
public class WebServiceTester {
    private static final String SUCCESS_RESULT = "<result>success</result>";
    private static final String PASS = "pass";
    private static final String FAIL = "fail";
    private static Client client;
    private String REST_SERVICE_URL = "http://localhost:8080/rest-example/rest/UserService/users";

    @BeforeClass
    public static void init() {
        client = ClientBuilder.newClient();
    }

    @Test
    public void test() {
        WebServiceTester tester = new WebServiceTester();
        tester.testGetAllUsers();
        tester.testGetUser();
        tester.testUpdateUser();
        tester.testAddUser();
        tester.testDeleteUser();
    }

    private void testGetAllUsers() {
        GenericType<List<User>> list = new GenericType<List<User>>() {
        };
        List<User> users = client.target(REST_SERVICE_URL).request(MediaType.APPLICATION_XML).get(list);
        String result = PASS;
        if (users.isEmpty()) {
            result = FAIL;
        }
        assertTrue(result.equals(PASS));
    }

    private void testGetUser() {
        User sampleUser = new User();
        sampleUser.setId(1);
        User user = client.target(REST_SERVICE_URL).path("/{userid}").resolveTemplate("userid", 1)
                          .request(MediaType.APPLICATION_XML).get(User.class);
        String result = FAIL;
        if (sampleUser.getId() == user.getId()) {
            result = PASS;
        }
        assertTrue(result.equals(PASS));
    }

    private void testUpdateUser() {
        Form form = new Form();
        form.param("id", "1");
        form.param("name", "misha");
        form.param("profession", "programmer");
        String callResult = client.target(REST_SERVICE_URL).request(MediaType.APPLICATION_XML)
                                  .put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);
        String result = PASS;
        if (!SUCCESS_RESULT.equals(callResult)) {
            result = FAIL;
        }
        assertTrue(result.equals(PASS));
    }

    private void testAddUser() {
        Form form = new Form();
        form.param("id", "2");
        form.param("name", "Misha");
        form.param("profession", "mathematician");
        String callResult = client.target(REST_SERVICE_URL).request(MediaType.APPLICATION_XML)
                                  .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);
        String result = PASS;
        if (!SUCCESS_RESULT.equals(callResult)) {
            result = FAIL;
        }
        assertTrue(result.equals(PASS));
    }

    private void testDeleteUser() {
        String callResult = client.target(REST_SERVICE_URL).path("/{userid}").resolveTemplate("userid", 2)
                                  .request(MediaType.APPLICATION_XML).delete(String.class);
        String result = PASS;
        if (!SUCCESS_RESULT.equals(callResult)) {
            result = FAIL;
        }
        assertTrue(result.equals(PASS));
    }
}