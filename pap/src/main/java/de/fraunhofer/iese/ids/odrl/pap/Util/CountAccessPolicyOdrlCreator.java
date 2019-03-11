package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.CountAccessPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.SpecificEventPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;

public class CountAccessPolicyOdrlCreator {
	
	public static String createODRL(CountAccessPolicy countAccessPolicy){

		// set rule type
		String ruleType = "";
		if(countAccessPolicy.getRuleType()!= null)
		{
			switch(countAccessPolicy.getRuleType()) {
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
		if(null != countAccessPolicy.getPolicyType()) {
			type = countAccessPolicy.getPolicyType().toString();
		}

		//set assigner
		String assigner = "";
		if(null != countAccessPolicy.getAssigner() && !countAccessPolicy.getAssigner().isEmpty() && !countAccessPolicy.getPolicyType().equals(PolicyType.Request)) {
			assigner = "      \"assigner\": \"" + countAccessPolicy.getAssigner() + "\",    \r\n";
		}

		//set type and assignee
		String assignee = "";
		if( null != countAccessPolicy.getAssignee() && !countAccessPolicy.getAssignee().isEmpty() && !countAccessPolicy.getPolicyType().equals(PolicyType.Offer)) {
			assignee = "      \"assignee\": \"" + countAccessPolicy.getAssignee() + "\",    \r\n";
		}

		//set target
		String target = "";
		if(null != countAccessPolicy.getDataUrl()) {
			target = countAccessPolicy.getDataUrl().toString();
		}

		//set count
		String count = "";
		if(null != countAccessPolicy.getCount()) {
			count = countAccessPolicy.getCount();
		}

		//set action
		String action = "";
		if(null != countAccessPolicy.getAction()) {
			action = countAccessPolicy.getAction().getIdsAction();
		}

		//set leftOperand
		String leftOperand = LeftOperand.COUNT.getIdsLeftOperand();

		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:restrict-access\",    \r\n" + 
				"  \"%s\": [{    \r\n" +
				"      \"target\": \"%s\",    \r\n%s%s" +
				"      \"action\": \"%s\",     \r\n" +
				"      \"constraint\": [{    \r\n" + 
				"        \"leftOperand\": \"%s\",    \r\n" +
				"        \"operator\": \"lt\",    \r\n" +
				"        \"rightOperand\": { \"@value\": \"%s\", \"@type\": \"xsd:decimal\" }     \r\n" +
				"      }]     \r\n" + 
				"  }]    \r\n" + 
				"} ", type, ruleType, target, assigner, assignee, action, leftOperand, count);
	}
}
