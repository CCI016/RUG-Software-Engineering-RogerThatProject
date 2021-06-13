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
	 * stayed the same or increased. Also it will look for how many withdrawals were made for the last 6 months,
	 * how many incasso transactions were made. All this gathered data will be used for our report
	 */
	@Transactional
	public void transactionsFromSixMonths() {
		List<Integer> monthsInterval = new ArrayList<>();
		List<Double> incomePerMonth = new ArrayList<>();
		int month = Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("MM")));
		String year = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
		boolean isMixedYear = false;
		int withdrawals = 0, incasso = 0;
		double withdrawalsSum = 0.0, incassoSum = 0.0;
		int index = 0;

		for (int i = 0; i < 6; i++) {
			if ((month - i) < 1) {
				monthsInterval.add(12 - i);
			} else {
				monthsInterval.add(month - i);
			}
			incomePerMonth.add(0.00);
		}

		if (abs(monthsInterval.get(0) - monthsInterval.get(5)) > 5) {
			isMixedYear = true;
		}

		System.out.println(personTransactions.size());
		for (Transactions transaction : personTransactions) {

			String transactionYear = transaction.dateTime.substring(1, 5);
			int transactionMonth = Integer.parseInt(transaction.dateTime.substring(5, 7));

			if (monthsInterval.contains(transactionMonth) && (transactionYear.equals(year)
					|| (isMixedYear && (Integer.parseInt(transactionYear) == (Integer.parseInt(year) - 1))))) {
				index = monthsInterval.indexOf(transactionMonth);
				if (transaction.transactionType.equals("\"Geldautomaat\"") && transaction.transactionCategory
						== TransactionCategory.SPENDING) {
					withdrawals++;
					withdrawalsSum += Double.parseDouble(transaction.amount
							.substring(0, transaction.amount.length() - 1).replace(",", "."));
				}
				if (transaction.transactionType.equals("\"Incasso\"")) {
					incasso++;
					incassoSum += Double.parseDouble(transaction.amount
							.substring(0, transaction.amount.length() - 1).replace(",", "."));
				}
				if (transaction.transactionCategory == TransactionCategory.INCOME) {
					incomePerMonth.set(index, incomePerMonth.get(index) + Double.parseDouble(transaction.amount.replace(",", ".")));
				} else {
					incomePerMonth.set(index, incomePerMonth.get(index) - Double.parseDouble(transaction.amount
							.replace(",", ".")));
				}

			}

		}

		List<IntervalOverview> intervalOverviews = IntervalOverview.listAll();

		IntervalOverview intervalOverview = intervalOverviews.stream().filter(io -> io.person ==
				Person.findById(this.personID)).findFirst().orElse(null);

		if (intervalOverview == null) {
			intervalOverview = new IntervalOverview();
			intervalOverview.person = Person.findById(this.personID);
		}

		intervalOverview.month_0 = monthsInterval.get(0) + ":" + incomePerMonth.get(0);
		intervalOverview.month_1 = monthsInterval.get(1) + ":" + incomePerMonth.get(1);
		intervalOverview.month_2 = monthsInterval.get(2) + ":" + incomePerMonth.get(2);
		intervalOverview.month_3 = monthsInterval.get(3) + ":" + incomePerMonth.get(3);
		intervalOverview.month_4 = monthsInterval.get(4) + ":" + incomePerMonth.get(4);
		intervalOverview.month_5 = monthsInterval.get(5) + ":" + incomePerMonth.get(5);
		intervalOverview.withdrawals = withdrawals;
		intervalOverview.withdrawalsSum = String.valueOf(withdrawalsSum);
		intervalOverview.incasso = incasso;
		intervalOverview.incassoSum = String.valueOf(incassoSum);
		intervalOverview.persist();
	}
}
