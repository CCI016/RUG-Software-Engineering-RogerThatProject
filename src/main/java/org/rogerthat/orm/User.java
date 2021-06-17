package org.rogerthat.orm;

import javax.persistence.*;


@Entity
@Table(name = "users")
public class User extends EntitySuperclassIdOnly {

	@OneToOne
	@JoinColumn(name = "person_id", nullable = false)
	public Person person;

	@Column(nullable = false, name = "email")
	public String email;

	@Column(nullable = false, name = "password")
	public String password; //Need to encrypt it at later stages, now we can work with exposed passwords.

	@Column(name = "phoneNumber")
	public String phoneNumber;

}
