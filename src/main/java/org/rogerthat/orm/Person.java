package org.rogerthat.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "person")
public class Person extends EntitySuperclassIdOnly{

	@Column(nullable = false, name = "first_name")
	public String firstName;

	@Column(nullable = false,name = "last_name")
	public String lastName;

	@Column(nullable = false,name = "age")
	public String age;

	@OneToMany
	@JoinTable(name = "person_transactions",
			joinColumns = {@JoinColumn(name = "person_id")},
			inverseJoinColumns = {@JoinColumn(name = "transaction_id")}
	)
	public List<Transactions> transactions;

	@Column(name = "address")
	public String address;

	@Column(name = "gender")
	public String gender;
}
