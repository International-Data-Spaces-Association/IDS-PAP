package de.fraunhofer.iese.ids.odrl.pap.entity;

import javax.persistence.Entity;


public interface ShortPolicy {
	 Long getId();
	 String getPolicyID();	
	 String getDescription();
	 String getPolicyType();
}
