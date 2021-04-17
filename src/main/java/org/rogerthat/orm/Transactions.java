package org.rogerthat.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "transactions")
public class Transactions extends EntitySuperclassIdOnly{

	@Column(name = "date_time")
	public Date dateTime;

	@Column(name = "name")
	public String name;

	@Column(name = "category")
	public TransactionCategory transactionCategory;

	@Column(name = "spending_classification")
	public SpendingClassification spendingClassification;

	@Column(name = "income_classification")
	public IncomeClassification incomeClassification;

	@Column(name = "amount")
	public double amount;
	
}
