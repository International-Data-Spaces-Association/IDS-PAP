package de.fraunhofer.iese.ids.odrl.pap.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SpecificSystemPolicy extends AbstractPolicy {
	String system;

	public String getSystem() {
		return system;
	}
}
