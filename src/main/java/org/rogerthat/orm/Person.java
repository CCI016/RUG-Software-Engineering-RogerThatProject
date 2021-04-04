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
	public int age;

//	@OneToMany
//	@JoinColumn(nullable = false,name = "income")
//	public Income income;
//
//	@OneToMany
//	@JoinColumn(nullable = false, name = "spendings")
//	public Spendings spendings;
//
//	@OneToMany
//	@JoinColumn(nullable = false, name = "transactions")
//	public Transactions transactions;

	@OneToMany
	@JoinTable(name = "transactions",
			joinColumns = {@JoinColumn(name = "person_id")},
			inverseJoinColumns = {@JoinColumn(name = "transaction_id")}
	)
	private List<Transactions> transactions;
}
