package org.rogerthat.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.rogerthat.orm.Person;
import org.rogerthat.orm.User;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/rest/profile")
public class ProfileEndpoint {

	private final ObjectMapper mapper;

	public ProfileEndpoint() {
		mapper = new ObjectMapper();
	}


	@GET
	@Path("info")
	@Transactional
	public Response profileInfo(@QueryParam("userId") Long userId) throws JsonProcessingException {
		User user = User.findById(userId);
		Person person = user.person;
		if (user == null) {
			return Response.status(401).build();
		}
		String info = user.email + "!" + person.firstName + "!" +  person.lastName + "!" + person.age;
		//try {
			return Response.ok(mapper.writeValueAsString(user)).build();
//		} catch (JsonProcessingException e) {
//			return Response.status(401).build();
//		}
	}
}
