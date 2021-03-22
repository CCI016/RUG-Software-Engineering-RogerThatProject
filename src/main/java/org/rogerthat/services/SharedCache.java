package org.rogerthat.services;

import org.rogerthat.orm.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class SharedCache {
	/**
	 * This class will be used to keep the objects needed for our processes, so in order to
	 * increase the speed of the program, we won't retrieve each time from the db, but we'll take everything from here.
	 */
	
	private List<User> users;

	public SharedCache() {
		users = User.listAll();
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
