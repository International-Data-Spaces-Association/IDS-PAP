package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CountAccessPolicy extends AbstractPolicy {
	String count;

	public String getCount() {
		return count;
	}
}
