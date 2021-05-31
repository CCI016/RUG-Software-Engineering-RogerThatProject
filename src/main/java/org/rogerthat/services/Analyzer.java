package org.rogerthat.services;

import org.rogerthat.orm.IntervalOverview;
import org.rogerthat.orm.Person;
import org.rogerthat.orm.TransactionCategory;
import org.rogerthat.orm.Transactions;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Analyzer {

	private Long personID;
	private Person person;
	private List<Transactions> personTransactions;

	public Analyzer(Long personID, Person person) {
		this.personID = personID;
		this.person = person;
		this.personTransactions = person.transactions;
	}

	@Transactional
	public void analyze() {
		// it will be ruled based,
		transactionsFromSixMonths();
	}

	/**
	 * This function will take the user's transaction from the previous six months
	 * It will look at the user's income and the user's spendings and see if they declined or
	 * stayed the same or increased.
	 */
	@Transactional
	public void transactionsFromSixMonths() {
		List<Integer> monthsInterval = new ArrayList<>();
		List<Integer> incomePerMonth = new ArrayList<>();
		int month = Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("MM")));
		String year = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
		boolean isMixedYear = false;
		int index = 0;

		for (int i = 0; i < 6; i++) {
			if ((month - i) < 1) {
				monthsInterval.add(12 - i);
			} else {
				monthsInterval.add(month - i);
			}
			incomePerMonth.add(0);
		}

		if (abs(monthsInterval.get(0) - monthsInterval.get(5)) > 5) {
			isMixedYear = true;
		}
		

		for (Transactions transaction : personTransactions) {
			String transactionYear = transaction.dateTime.substring(1, 5);
			int transactionMonth = Integer.parseInt(transaction.dateTime.substring(5, 7));

			if (monthsInterval.contains(transactionMonth) && (transactionYear.equals(year)
					|| (isMixedYear && (Integer.parseInt(transactionYear) == (Integer.parseInt(year) - 1))))) {
				index = monthsInterval.indexOf(transactionMonth);

				if (transaction.transactionCategory == TransactionCategory.INCOME) {
					incomePerMonth.set(index, incomePerMonth.get(index) + Integer.parseInt(transaction.amount));
				} else {
					incomePerMonth.set(index, incomePerMonth.get(index) - Integer.parseInt(transaction.amount));
				}

			}

		}

		IntervalOverview intervalOverview = new IntervalOverview();
		intervalOverview.person = Person.findById(this.personID);

		intervalOverview.month_0 = monthsInterval.get(0) + ":" + incomePerMonth.get(0);
		intervalOverview.month_1 = monthsInterval.get(0) + ":" + incomePerMonth.get(0);
		intervalOverview.month_2 = monthsInterval.get(0) + ":" + incomePerMonth.get(0);
		intervalOverview.month_3 = monthsInterval.get(0) + ":" + incomePerMonth.get(0);
		intervalOverview.month_4 = monthsInterval.get(0) + ":" + incomePerMonth.get(0);
		intervalOverview.month_5 = monthsInterval.get(0) + ":" + incomePerMonth.get(0);

		intervalOverview.persist();
	}
}
