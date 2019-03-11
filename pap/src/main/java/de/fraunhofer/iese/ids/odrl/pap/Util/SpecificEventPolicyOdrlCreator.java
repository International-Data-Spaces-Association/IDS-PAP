package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.Action;
import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;
import de.fraunhofer.iese.ids.odrl.pap.model.RuleType;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.SpecificEventPolicy;

public class SpecificEventPolicyOdrlCreator {
	
	public static String createODRL(SpecificEventPolicy specificEventPolicy){

		// set rule type
		String ruleType = "";
		if(specificEventPolicy.getRuleType()!= null)
		{
			switch(specificEventPolicy.getRuleType()) {
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

		//set action
		String action = "";
		if(null != specificEventPolicy.getAction()) {
			action = specificEventPolicy.getAction().getIdsAction();
		}

		//set leftOperand
		String leftOperand = LeftOperand.EVENT.getIdsLeftOperand();

		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:restrict-access-event\",    \r\n" +
				"  \"%s\": [{    \r\n" +
				"      \"target\": \"%s\",    \r\n%s%s" +
				"      \"action\": \"%s\",     \r\n" +
				"      \"constraint\": [{    \r\n" + 
				"        \"leftOperand\": \"%s\",    \r\n" +
				"        \"operator\": \"eq\",    \r\n" +
				"        \"rightOperand\": { \"@value\": \"%s\", \"@type\": \"xsd:anyURI\" }     \r\n" +
				"      }]     \r\n" + 
				"  }]    \r\n" + 
				"} ", type, ruleType, target, assigner, assignee, action, leftOperand, event);
	}
}
