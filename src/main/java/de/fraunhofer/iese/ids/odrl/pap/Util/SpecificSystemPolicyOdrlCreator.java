package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.AbstractPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;

public class SpecificSystemPolicyOdrlCreator {
	
	public static String createODRL(AbstractPolicy specificSystemPolicy){

		// set rule type
		String ruleType = "";
		if(specificSystemPolicy.getRuleType()!= null)
		{
			switch(specificSystemPolicy.getRuleType()) {
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
		if(null != specificSystemPolicy.getPolicyType()) {
			type = specificSystemPolicy.getPolicyType().toString();
		}

		//set assigner
		String assigner = "";
		if(null != specificSystemPolicy.getAssigner() && !specificSystemPolicy.getAssigner().isEmpty() && !specificSystemPolicy.getPolicyType().equals(PolicyType.Request)) {
			assigner = "      \"assigner\": \"" + specificSystemPolicy.getAssigner() + "\",    \r\n";
		}

		//set type and assignee
		String assignee = "";
		if( null != specificSystemPolicy.getAssignee() && !specificSystemPolicy.getAssignee().isEmpty() && !specificSystemPolicy.getPolicyType().equals(PolicyType.Offer)) {
			assignee = "      \"assignee\": \"" + specificSystemPolicy.getAssignee() + "\",    \r\n";
		}

		//set target
		String target = "";
		if(null != specificSystemPolicy.getDataUrl()) {
			target = specificSystemPolicy.getDataUrl().toString();
		}

		//set system
		String system = "";
		if(null != specificSystemPolicy.getSystem()) {
			system = specificSystemPolicy.getSystem();
		}

		//set action
		String action = "";
		if(null != specificSystemPolicy.getAction()) {
			action = specificSystemPolicy.getAction().getIdsAction();
		}

		//set leftOperand
		String leftOperand = LeftOperand.SYSTEM.getIdsLeftOperand();

		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:restrict-access-system\",    \r\n" +
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
				"} ", type, ruleType, target, assigner, assignee, action, leftOperand, system);
	}
}
