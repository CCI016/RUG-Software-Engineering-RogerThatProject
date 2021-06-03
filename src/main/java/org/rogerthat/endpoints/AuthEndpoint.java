package org.rogerthat.endpoints;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.rogerthat.orm.Person;
import org.rogerthat.orm.User;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.Base64;

@Path("/rest/auth")
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
    @Path("/auth")
    @Transactional
    public Response login(@QueryParam("email") String email,@QueryParam("password") String pass) {
        byte[] decodedPassBytes = Base64.getDecoder().decode(getPass(email));
        String usersPass = new String(decodedPassBytes);

        // Check if the strings are matching and user exists
        if(pass.equals(usersPass) && userExists(email)) {
            // Access the web page
            // 200 : OK successful response
            return Response.status(200).build();
        } else {
            // Deny access and let them try again
            // 401 : Unauthorized client error response
            return Response.status(401).build();
        }
    }

    public Response register(String email, String pass, String firstName, String lastName, int age) {
        if(userExists(email)) {
            return Response.status(401).build();
        } else {
            User user = new User();
            user.email = email;

            String encodedPass = Base64.getEncoder().encodeToString(pass.getBytes());
            user.password = encodedPass;

            user.person.firstName = firstName;
            user.person.lastName = lastName;
            user.person.age = age;
            user.persist();
            return Response.status(200).build();
        }
    }

    public Response changePass(String email, String oldPass, String newPass) {
        if(oldPass.equals(getPass(email)) && userExists(email)) {
            User user = findUserByEmail(email);
            String encodedPass = Base64.getEncoder().encodeToString(newPass.getBytes());
            user.password = encodedPass;
            user.persist();
            return Response.status(200).build();
        } else {
            return Response.status(401).build();
        }
    }

    public String getPass(@QueryParam("email") String email) {
        if(userExists(email)) {
            User user = findUserByEmail(email);
            byte[] decodedPassBytes = Base64.getDecoder().decode(user.password);
            String usersPass = new String(decodedPassBytes);
            return usersPass;
        } else {
            return "";
        }
    }

    public boolean userExists(String email) {
        return User.find("email = ?1", email).firstResult() != null;
    }

    public User findUserByEmail(String email) {
        User user = User.find("email = ?1", email).firstResult();
        return user;
    }
}
