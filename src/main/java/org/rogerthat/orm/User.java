package org.rogerthat.orm;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import java.util.List;

public class User extends EntitySuperclassIdOnly {

	@JoinColumn(nullable = false, name = "person")
	public Person person;

	@Column(nullable = false, name = "email")
	public String email;

	@Column(nullable = false, name = "password")
	public String password; //Need to encrypt it at later stages, now we can work with exposed passwords.

}
