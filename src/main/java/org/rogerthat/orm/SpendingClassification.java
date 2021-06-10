package org.rogerthat.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "spendings_classifications")
public class SpendingClassification extends EntitySuperclassIdOnly {

	@Column(name = "category")
	public SpendingCategories category;

	@Column(name = "word_associated")
	public String wordAssociated;
}
