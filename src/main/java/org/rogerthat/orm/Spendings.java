package org.rogerthat.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "spendings")
public class Spendings extends EntitySuperclassIdOnly{

	@Column(nullable = false, name = "category")
	public int category;

	@Column(nullable = false, name = "amount")
	public double amount;

	@Column(nullable = false, name = "is_periodic")
	public boolean isPeriodic;

	@Column(name = "period")
	public boolean period;
}