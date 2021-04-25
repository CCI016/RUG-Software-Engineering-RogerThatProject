package org.rogerthat.orm;

import javax.persistence.*;


@Entity
@Table(name = "users")
public class User extends EntitySuperclassIdOnly {

	@OneToOne
	@JoinColumn(nullable = false, name = "person_id")
	public Person person;

	@Column(nullable = false, name = "email")
	public String email;

	@Column(nullable = false, name = "login")
	public String login;

	@Column(nullable = false, name = "password")
	public String password; //Need to encrypt it at later stages, now we can work with exposed passwords.

}
