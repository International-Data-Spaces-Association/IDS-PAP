package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class EncodingPolicy extends AbstractPolicy {
	String encoding;

	public String getEncoding() {
		return encoding;
	}
}