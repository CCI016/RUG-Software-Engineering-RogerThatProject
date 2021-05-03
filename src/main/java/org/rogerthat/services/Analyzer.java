package org.rogerthat.services;

import org.rogerthat.orm.Person;
import org.rogerthat.orm.Transactions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Analyzer {

	private Long personID;
	private Person person;
	private List<Transactions> personTransactions;

	public Analyzer(Long personID, Person person) {
		this.personID = personID;
		this.person = person;
		this.personTransactions = person.transactions;
	}

	public void analyze() {
		// it will be ruled based,
		transactionsFromSixMonths();
	}

	/**
	 * This function will take the user's transaction from the previous six months
	 * It will look at the user's income and the user's spendings and see if they declined or
	 * stayed the same or increased.
	 */
	private void transactionsFromSixMonths() {
		String month = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));

		System.out.println(month);

	}
}
