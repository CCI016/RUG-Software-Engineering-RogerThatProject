package org.rogerthat.orm;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

public class CsvFiles extends EntitySuperclassIdOnly{

	@Column(nullable =  false, name = "original_name")
	public String originalName;

	@Column(nullable = false, name = "changed_name")
	public String changedName;

	@OneToMany
	@JoinColumn(nullable = false, name = "user")
	public User user;


}
