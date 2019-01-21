package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;
import de.fraunhofer.iese.ids.odrl.pap.model.ProvideAccessPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.SpecificPurposePolicy;

public class ProvideAccessPolicyOdrlCreator {
	
	public static String createODRL(ProvideAccessPolicy provideAccessPolicy){

		//set type
		String type = "";
		if(null != provideAccessPolicy.getPolicyType()) {
			type = provideAccessPolicy.getPolicyType().toString();
		}

		//set assigner
		String assigner = "";
		if(null != provideAccessPolicy.getAssigner() && !provideAccessPolicy.getAssigner().isEmpty() && !provideAccessPolicy.getPolicyType().equals(PolicyType.Request)) {
			assigner = "      \"assigner\": \"" + provideAccessPolicy.getAssigner() + "\",    \r\n";
		}

		//set type and assignee
		String assignee = "";
		if( null != provideAccessPolicy.getAssignee() && !provideAccessPolicy.getAssignee().isEmpty() && !provideAccessPolicy.getPolicyType().equals(PolicyType.Offer)) {
			assignee = "      \"assignee\": \"" + provideAccessPolicy.getAssignee() + "\",    \r\n";
		}

		//set target
		String target = "";
		if(null != provideAccessPolicy.getDataUrl()) {
			target = provideAccessPolicy.getDataUrl().toString();
		}
		
		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:restrict-access\",    \r\n" + 
				"  \"permission\": [{    \r\n" + 
				"      \"target\": \"%s\",    \r\n%s%s" +
				"      \"action\": \"read\"     \r\n" +
				"  }]    \r\n" + 
				"} ", type, target, assigner, assignee);
	}
}
