package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;
import de.fraunhofer.iese.ids.odrl.pap.model.SpecificPurposePolicy;

public class SpecificPurposePolicyOdrlCreator {
	
	public static String createODRL(SpecificPurposePolicy specificPurposePolicy){

		//set type
		String type = "";
		if(null != specificPurposePolicy.getPolicyType()) {
			type = specificPurposePolicy.getPolicyType().toString();
		}

		//set assigner
		String assigner = "";
		if(null != specificPurposePolicy.getAssigner() && !specificPurposePolicy.getAssigner().isEmpty() && !specificPurposePolicy.getPolicyType().equals(PolicyType.Request)) {
			assigner = "      \"assigner\": \"" + specificPurposePolicy.getAssigner() + "\",    \r\n";
		}

		//set type and assignee
		String assignee = "";
		if( null != specificPurposePolicy.getAssignee() && !specificPurposePolicy.getAssignee().isEmpty() && !specificPurposePolicy.getPolicyType().equals(PolicyType.Offer)) {
			assignee = "      \"assignee\": \"" + specificPurposePolicy.getAssignee() + "\",    \r\n";
		}

		//set target
		String target = "";
		if(null != specificPurposePolicy.getDataUrl()) {
			target = specificPurposePolicy.getDataUrl().toString();
		}

		//set purpose
		String purpose = "";
		if(null != specificPurposePolicy.getPurpose()) {
			purpose = specificPurposePolicy.getPurpose();
		}
		
		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:restrict-access\",    \r\n" + 
				"  \"permission\": [{    \r\n" + 
				"      \"target\": \"%s\",    \r\n%s%s" +
				"      \"action\": \"read\",     \r\n" + 
				"      \"constraint\": [{    \r\n" + 
				"        \"leftOperand\": \"purpose\",    \r\n" + 
				"        \"operator\": \"eq\",    \r\n" + 
				"        \"rightOperand\": { \"@value\": \"%s\", \"@type\": \"xsd:string\" }     \r\n" + 
				"      }]     \r\n" + 
				"  }]    \r\n" + 
				"} ", type, target, assigner, assignee, purpose);
	}
}
