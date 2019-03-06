package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SpecificPurposePolicy extends AbstractPolicy {
	String purpose;

	public String getPurpose() {
		return purpose;
	}
}
