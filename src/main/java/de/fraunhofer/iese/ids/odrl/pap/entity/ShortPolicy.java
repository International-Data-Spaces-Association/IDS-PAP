package de.fraunhofer.iese.ids.odrl.pap.entity;

import javax.persistence.Entity;


public interface ShortPolicy {
	 Long getId();
	 String getName();
	 String getPolicyType();
	 String getComment();
}
