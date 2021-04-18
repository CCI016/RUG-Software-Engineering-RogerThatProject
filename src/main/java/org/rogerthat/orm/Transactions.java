package org.rogerthat.orm;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;

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

	@Column(name = "account_from")
	public String accountFrom;

	@Column(name = "account_to")
	public String accountTo;

	@Column(name = "code")
	public String code;

	@Column(name  = "in_or_out")
	public String inOrOut;

	@Column(name = "amount")
	public double amount;

	@Column(name = "transaction_type")
	public String transactionType;

	@Column(name = "notes")
	public String notes;

//	@Column(name = "category")
//	public TransactionCategory transactionCategory;

//	@Column(name = "spending_classification")
//	public SpendingClassification spendingClassification;

//	@Column(name = "income_classification")
//	public IncomeClassification incomeClassification;
	
}
