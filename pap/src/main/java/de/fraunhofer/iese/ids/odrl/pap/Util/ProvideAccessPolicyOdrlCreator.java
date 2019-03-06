package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.Action;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.ProvideAccessPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.RuleType;

public class ProvideAccessPolicyOdrlCreator {
	
	public static String createODRL(ProvideAccessPolicy provideAccessPolicy){

		// set rule type
		String ruleType = "permission";
		if(provideAccessPolicy.getRuleType()!= null && provideAccessPolicy.getRuleType().equals(RuleType.PROHIBITION))
		{
			ruleType = RuleType.PROHIBITION.getOdrlRuleType();
		}

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
				"  \"%s\": [{    \r\n" +
				"      \"target\": \"%s\",    \r\n%s%s" +
				"      \"action\": \""+ Action.READ.getIdsAction() +"\"     \r\n" +
				"  }]    \r\n" + 
				"} ", type, ruleType, target, assigner, assignee);
	}
}
