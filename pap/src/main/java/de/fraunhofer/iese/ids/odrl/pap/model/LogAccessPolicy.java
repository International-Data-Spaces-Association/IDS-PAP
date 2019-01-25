package de.fraunhofer.iese.ids.odrl.pap.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class LogAccessPolicy extends AbstractPolicy {
	String recipient;

	public String getRecipient() {
		return recipient;
	}

}
