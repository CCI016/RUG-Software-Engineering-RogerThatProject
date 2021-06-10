package org.rogerthat.orm;

import javax.persistence.*;

@Entity
@Table(name = "csvfiles")
public class CsvFiles extends EntitySuperclassIdOnly{

	@Column(nullable =  false, name = "original_name")
	public String originalName;

	@Column(nullable = false, name = "changed_name")
	public String changedName;

	@OneToOne
	@JoinColumn(nullable = false, name = "user_id")
	public User user;
}
