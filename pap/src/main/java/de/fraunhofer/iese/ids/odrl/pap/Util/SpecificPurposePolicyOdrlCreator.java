package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.SpecificPurposePolicy;

public class SpecificPurposePolicyOdrlCreator {
	
	public static String createODRL(SpecificPurposePolicy specificPurposePolicy){
		
		//set type and assignee
		String type = "Offer";
		String assignee = "";
		if( null != specificPurposePolicy.getAssignee() && !specificPurposePolicy.getAssignee().isEmpty()) {
			type = "Agreement";
			assignee = "      \"assignee\": \"" + specificPurposePolicy.getAssignee() + "\",    \r\n";
		}
		
		//set target
		String target = "";
		if(null != specificPurposePolicy.getDataUrl()) {
			target = specificPurposePolicy.getAssigner().toString();
		}
		
		//set assigner 
		String assigner = "";
		if(null != specificPurposePolicy.getAssigner()) {
			assigner = specificPurposePolicy.getAssigner();
		}
		
		//set assigner 
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
				"      \"target\": \"%s\",    \r\n" + 
				"      \"assigner\": \"%s\",    \r\n%s" + 
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
