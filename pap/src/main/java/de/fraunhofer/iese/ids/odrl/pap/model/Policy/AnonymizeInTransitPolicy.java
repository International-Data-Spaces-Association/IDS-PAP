package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AnonymizeInTransitPolicy extends AbstractPolicy {
	String jsonPath;
	String digit;

	//TODO: digit param does not belong to anonymize method but Round method!
	public String getJsonPath() {
		return jsonPath;
	}

	public String getDigit() {
		return digit;
	}
}
