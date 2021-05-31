package org.rogerthat.orm;

import javax.persistence.*;

/**
 * This class will be used to store the information about the user's transactions overview from the past 6 months
 */
@Entity
@Table(name = "interval_overview")
public class IntervalOverview extends EntitySuperclassIdOnly {

	@OneToOne
	@JoinColumn(name = "person_id", nullable = false)
	public Person person;

	@Column(name = "month_0")
	public String month_0;

	@Column(name = "month_1")
	public String month_1;

	@Column(name = "month_2")
	public String month_2;

	@Column(name = "month_3")
	public String month_3;

	@Column(name = "month_4")
	public String month_4;

	@Column(name = "month_5")
	public String month_5;
}
