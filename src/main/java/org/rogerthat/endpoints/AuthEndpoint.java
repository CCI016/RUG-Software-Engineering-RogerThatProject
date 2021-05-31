package org.rogerthat.endpoints;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.rogerthat.orm.Person;
import org.rogerthat.orm.User;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.*;

@Path("/rest/file")
public class AuthEndpoint {

    @ConfigProperty(name = "email")
    String email;

    @ConfigProperty(name = "password")
    String pass;

    /**
     * Endpoint for uploading user authorisation information.
     * @param email User's email for sign-up or sign-in functions
     * @param pass User's password for sign-up or sign-in functions
     * @return Response code.
     */
    @POST
    @Path("/upload")
    @Consumes("multipart/form-data")
    @Transactional
    public Response Login(@QueryParam("email") String email,@QueryParam("password") String pass) {
        String usersPass = getPass(email);

        // Check if the strings are matching and user exists
        if(pass.equals(usersPass) && UserExists(email)) {
            // Access the web page
            // 200 : OK successful response
            return Response.status(200).build();
        } else {
            // Deny access and let them try again
            // 401 : Unauthorized client error response
            return Response.status(401).build();
        }
    }

    public Response Register(String email, String pass, String firstName, String lastName, int age) {
        if(UserExists(email)) {
            return Response.status(401).build();
        } else {
            User user = new User();
            user.email = email;
            user.password = pass;
            user.person.firstName = firstName;
            user.person.lastName = lastName;
            user.person.age = age;
            user.persist();
            return Response.status(200).build();
        }
    }

    public Response ChangePass(@QueryParam("email") String email, @QueryParam("password") String oldPass, String newPass) {
        if(oldPass.equals(getPass(email)) && UserExists(email)) {
            User user = findUserByEmail(email);
            user.password = newPass;
            user.persist();
            return Response.status(200).build();
        } else {
            // Exception
            return Response.status(401).build();
        }
    }

    public String getPass(@QueryParam("email") String email) {
        if(UserExists(email)) {
            User user = findUserByEmail(email);
            return user.password;
        } else {
            // Exception
            return "";
        }
    }

    public boolean UserExists(String email) {
        return true;
    }

    public User findUserByEmail(String email) {
        if(UserExists(email)) {
            // TODO: Query to get user by email
        } else {
            // Exception
        }

        // For now return is this so the method won't be problematic
        return new User();
    }
}
