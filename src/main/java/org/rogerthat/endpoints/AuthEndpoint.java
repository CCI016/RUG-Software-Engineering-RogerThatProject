package org.rogerthat.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.rogerthat.orm.Person;
import org.rogerthat.orm.User;

import javax.json.Json;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.StringReader;
import java.util.Base64;

@Path("/rest/auth")
public class AuthEndpoint {

    private final ObjectMapper mapper;

    public AuthEndpoint() {
		this.mapper = new ObjectMapper();
	}

    /**
     * Endpoint for uploading user authorisation information.
     * @param email User's email for sign-up or sign-in functions
     * @param pass User's password for sign-up or sign-in functions
     * @return Response code.
     */
    @GET
    @Path("auth")
    @Transactional
    public Response login(@QueryParam("email") String email,@QueryParam("password") String pass) {
        byte[] decodedPassBytes = Base64.getDecoder().decode(getPass(email));
        String usersPass = new String(decodedPassBytes);

        // Check if the strings are matching and user exists
        if(userExists(email) && pass.equals(getPass(email))) {
            // Access the web page
            User user = User.find("email = ?1", email).firstResult();
            // 200 : OK successful response
            try {
                return Response.ok(mapper.writeValueAsString(user.id)).build();
            } catch (JsonProcessingException e) {
                return Response.status(401).build();
            }
        } else {
            // Deny access and let them try again
            // 401 : Unauthorized client error response
            return Response.status(401).build();
        }
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response register(String body) {
        String email;
        JsonObject json = Json.createReader(new StringReader(body)).readObject();

        if (json.containsKey("email")) {
            email = json.getString("email");
            User user = User.find("email = ?1", email).firstResult();

            if (user == null) {
                user = new User();
                Person person = new Person();
                user.email = email;
                user.password = Base64.getEncoder().encodeToString(json.getString("password").getBytes());
                user.phoneNumber = json.getString("phoneNumber");

                person.address = json.getString("address");
                person.firstName = json.getString("firstName");
                person.lastName = json.getString("lastName");
                person.age = json.getString("age");
                person.gender = json.getString("gender");

                person.persist();
                user.person = person;
                user.persist();
                return Response.ok().build();
            } else {
                return Response.status(401).build(); // User already exists
            }


        } else {
            return Response.status(500).build();
        }
    }

    @POST
    @Path("changePass")
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

    public String getPass(String email) {
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
