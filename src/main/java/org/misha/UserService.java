package org.misha;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("/UserService")
public class UserService {
    private static final String SUCCESS_RESULT = "<result>success</result>";
    private static final String FAILURE_RESULT = "<result>failure</result>";
    private final UserDao userDao = new UserDao();

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_XML)
    public List<User> getUsers() {
        return userDao.getAllUsers();
    }

    @GET
    @Path("/users/{userid}")
    @Produces(MediaType.APPLICATION_XML)
    public User getUser(@PathParam("userid") final int userid) {
        return userDao.getUser(userid);
    }

    @POST
    @Path("/users")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String createUser(
            @FormParam("id") int id, final @FormParam("name") String name, @FormParam("profession") String profession,
            @Context HttpServletResponse servletResponse
    ) throws IOException {
        User user = new User(id, name, profession);
        int result = userDao.addUser(user);
        if (result == 1) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }

    @PUT
    @Path("/users")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String updateUser(
            @FormParam("id") int id, final @FormParam("name") String name, @FormParam("profession") String profession,
            @Context HttpServletResponse servletResponse
    ) throws IOException {
        User user = new User(id, name, profession);
        int result = userDao.updateUser(user);
        if (result == 1) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }

    @DELETE
    @Path("/users/{userid}")
    @Produces(MediaType.APPLICATION_XML)
    public String deleteUser(@PathParam("userid") final int userid) {
        int result = userDao.deleteUser(userid);
        if (result == 1) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }

    @OPTIONS
    @Path("/users")
    @Produces(MediaType.APPLICATION_XML)
    public String getSupportedOperations() {
        return "<operations>GET, PUT, POST, DELETE</operations>";
    }
}