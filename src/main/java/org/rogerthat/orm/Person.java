package org.rogerthat.orm;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "person")
public class Person extends EntitySuperclassIdOnly{

	@Column(nullable = false, name = "first_name")
	public String firstName;

	@Column(nullable = false,name = "last_name")
	public String lastName;

	@Column(nullable = false,name = "age")
	public int age;

	@Column(nullable = false,name = "income")
	public Income income;

	@Column(nullable = false, name = "spendings")
	public Spendings spendings;
}
