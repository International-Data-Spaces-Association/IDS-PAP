package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class InformPolicy extends AbstractPolicy {

	String informedParty;

	public InformPolicy(){
		this.informedParty = "http://example.com/ids-party:my-party";
	}

	public String getInformedParty() {
		return informedParty;
	}

}
