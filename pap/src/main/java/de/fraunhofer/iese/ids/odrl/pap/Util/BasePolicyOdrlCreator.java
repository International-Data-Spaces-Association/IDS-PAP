package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.Action;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.BasePolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;
import de.fraunhofer.iese.ids.odrl.pap.model.RuleType;

public class BasePolicyOdrlCreator {
	
	public static String createODRL(BasePolicy basePolicy){

		// set rule type
		String ruleType = "";
		if(basePolicy.getRuleType()!= null)
		{
			switch(basePolicy.getRuleType()) {
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
		if(null != basePolicy.getPolicyType()) {
			type = basePolicy.getPolicyType().toString();
		}

		//set assigner
		String assigner = "";
		if(null != basePolicy.getAssigner() && !basePolicy.getAssigner().isEmpty() && !basePolicy.getPolicyType().equals(PolicyType.Request)) {
			assigner = "      \"assigner\": \"" + basePolicy.getAssigner() + "\",    \r\n";
		}

		//set type and assignee
		String assignee = "";
		if( null != basePolicy.getAssignee() && !basePolicy.getAssignee().isEmpty() && !basePolicy.getPolicyType().equals(PolicyType.Offer)) {
			assignee = "      \"assignee\": \"" + basePolicy.getAssignee() + "\",    \r\n";
		}

		//set target
		String target = "";
		if(null != basePolicy.getDataUrl()) {
			target = basePolicy.getDataUrl().toString();
		}

		//set uid
		String uid = "";
		//set action
		String action = "";
		if(null != basePolicy.getAction()) {
			if (basePolicy.getAction().equals(Action.PRINT))
				uid = "restrict-print";

			else if (basePolicy.getAction().equals(Action.ANONYMIZE))
				uid = "anonymize-in-rest";

			else uid = "restrict-access";

			action = basePolicy.getAction().getIdsAction();
		}

		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:%s\",    \r\n" +
				"  \"%s\": [{    \r\n" +
				"      \"target\": \"%s\",    \r\n%s%s" +
				"      \"action\": \"%s\"     \r\n" +
				"  }]    \r\n" + 
				"} ", type,uid , ruleType, target, assigner, assignee, action);
	}
}
