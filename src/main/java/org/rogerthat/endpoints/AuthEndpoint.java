package org.rogerthat.endpoints;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.IOException;

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
    public Response Login(String email, String pass) {
        String usersPass = getPass(email);

        // Check if the strings are matching
        if(pass.equals(usersPass)) {
            // Access the web page
            // 200 : OK successful response
            return Response.status(200).build();
        } else {
            // Deny access and let them try again
            // 401 : Unauthorized client error response
            return Response.status(401).build();
        }
    }

    public Response Register(String email, String pass) {
        if(UserExists(email)) {
            return Response.status(401).build();
        } else {
            // TODO: Query to add credentials
            return Response.status(200).build();
        }
    }

    public Response ChangePass(String email, String oldPass, String newPass) {
        if(oldPass.equals(getPass(email))) {
            // TODO: Query to update the email's oldPass to newPass
        } else {
            return Response.status(401).build();
        }
    }

    public String getPass(String email) {
        String pass;
        // TODO: Query to pull the pass of the given email

        if(UserExists(email)) {
            return pass;
        } else {
            // Return exception
        }
    }

    public boolean UserExists(String email) {
        // TODO: Query to check if the user exists with a password
        // Return true if it does
        // False if not
    }
}
