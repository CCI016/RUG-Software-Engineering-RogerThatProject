package org.rogerthat.orm;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transactions extends EntitySuperclassIdOnly{

}
