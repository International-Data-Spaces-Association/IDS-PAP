package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.AbstractPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;

public class SpecificLocationPolicyOdrlCreator {
	
	public static String createODRL(AbstractPolicy specificLocationPolicy){

		// set rule type
		String ruleType = "";
		if(specificLocationPolicy.getRuleType()!= null)
		{
			switch(specificLocationPolicy.getRuleType()) {
				case OBLIGATION:
					ruleType = "obligation";
					break;

				case PERMISSION:
					ruleType = "permission";
					break;

				case PROHIBITION:
					ruleType = "prohibition";
					break;
			}
		}

		//set type
		String type = "";
		if(null != specificLocationPolicy.getPolicyType()) {
			type = specificLocationPolicy.getPolicyType().toString();
		}

		//set assigner
		String assigner = "";
		if(null != specificLocationPolicy.getAssigner() && !specificLocationPolicy.getAssigner().isEmpty() && !specificLocationPolicy.getPolicyType().equals(PolicyType.Request)) {
			assigner = "      \"assigner\": \"" + specificLocationPolicy.getAssigner() + "\",    \r\n";
		}

		//set type and assignee
		String assignee = "";
		if( null != specificLocationPolicy.getAssignee() && !specificLocationPolicy.getAssignee().isEmpty() && !specificLocationPolicy.getPolicyType().equals(PolicyType.Offer)) {
			assignee = "      \"assignee\": \"" + specificLocationPolicy.getAssignee() + "\",    \r\n";
		}

		//set target
		String target = "";
		if(null != specificLocationPolicy.getDataUrl()) {
			target = specificLocationPolicy.getDataUrl().toString();
		}

		//set location
		String location = "";
		if(null != specificLocationPolicy.getLocation()) {
			location = specificLocationPolicy.getLocation();
		}

		//set action
		String action = "";
		if(null != specificLocationPolicy.getAction()) {
			action = specificLocationPolicy.getAction().getIdsAction();
		}

		//set leftOperand
		String leftOperand = LeftOperand.ABSOLUTESPATIALPOSITION.getIdsLeftOperand();
		
		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:restrict-access-location\",    \r\n" +
				"  \"profile\": \"http://example.com/ids-profile\",    \r\n" +
				"  \"%s\": [{    \r\n" +
				"      \"target\": \"%s\",    \r\n%s%s" +
				"      \"action\": \"%s\",     \r\n" +
				"      \"constraint\": [{    \r\n" + 
				"        \"leftOperand\": \"%s\",    \r\n" +
				"        \"operator\": \"eq\",    \r\n" + 
				"        \"rightOperand\": { \"@value\": \"%s\", \"@type\": \"xsd:anyURI\" }     \r\n" +
				"      }]     \r\n" + 
				"  }]    \r\n" + 
				"} ", type, ruleType, target, assigner, assignee, action, leftOperand, location);
	}
}
