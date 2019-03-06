package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.Action;
import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;
import de.fraunhofer.iese.ids.odrl.pap.model.RuleType;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.SpecificEventPolicy;

public class SpecificEventPolicyOdrlCreator {
	
	public static String createODRL(SpecificEventPolicy specificEventPolicy){

		// set rule type
		String ruleType = "permission";
		if(specificEventPolicy.getRuleType()!= null && specificEventPolicy.getRuleType().equals(RuleType.PROHIBITION))
		{
			ruleType = RuleType.PROHIBITION.getOdrlRuleType();
		}

		//set type
		String type = "";
		if(null != specificEventPolicy.getPolicyType()) {
			type = specificEventPolicy.getPolicyType().toString();
		}

		//set assigner
		String assigner = "";
		if(null != specificEventPolicy.getAssigner() && !specificEventPolicy.getAssigner().isEmpty() && !specificEventPolicy.getPolicyType().equals(PolicyType.Request)) {
			assigner = "      \"assigner\": \"" + specificEventPolicy.getAssigner() + "\",    \r\n";
		}

		//set type and assignee
		String assignee = "";
		if( null != specificEventPolicy.getAssignee() && !specificEventPolicy.getAssignee().isEmpty() && !specificEventPolicy.getPolicyType().equals(PolicyType.Offer)) {
			assignee = "      \"assignee\": \"" + specificEventPolicy.getAssignee() + "\",    \r\n";
		}

		//set target
		String target = "";
		if(null != specificEventPolicy.getDataUrl()) {
			target = specificEventPolicy.getDataUrl().toString();
		}

		//set event
		String event = "";
		if(null != specificEventPolicy.getEvent()) {
			event = specificEventPolicy.getEvent();
		}
		
		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:restrict-access\",    \r\n" + 
				"  \"%s\": [{    \r\n" +
				"      \"target\": \"%s\",    \r\n%s%s" +
				"      \"action\": \""+ Action.READ.getIdsAction() +"\",     \r\n" +
				"      \"constraint\": [{    \r\n" + 
				"        \"leftOperand\": \""+ LeftOperand.EVENT.getIdsLeftOperand() +"\",    \r\n" +
				"        \"operator\": \"eq\",    \r\n" + 
				"        \"rightOperand\": { \"@value\": \"%s\", \"@type\": \"xsd:anyURI\" }     \r\n" +
				"      }]     \r\n" + 
				"  }]    \r\n" + 
				"} ", type, ruleType, target, assigner, assignee, event);
	}
}
