package de.fraunhofer.iese.ids.odrl.pap.repository;

import javax.persistence.Entity;


public interface ShortPolicy {
	 Long getId();
	 String getName();
	 String getPolicyType();
	 String getComment();
}
