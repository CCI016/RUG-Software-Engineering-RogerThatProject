package org.rogerthat.orm.income;

import org.rogerthat.orm.EntitySuperclassIdOnly;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "income")
public class Income extends EntitySuperclassIdOnly {

	/**
	 * Used for sorting income: Regular income(salary), Occasional Income etc...
	 */
	@Column(nullable = false, name = "category")
	public int category;

	@Column(nullable = false, name = "amount")
	public double amount;

	@Column(nullable = false, name = "is_periodic")
	public boolean isPeriodic;

	/*If it's periodic, once in how many weeks does it come*/
	@Column(name = "periodicity")
	public int period;

}
