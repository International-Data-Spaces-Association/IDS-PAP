package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.AbstractPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;

public class DistributeToThirdPartyPolicyOdrlCreator {
	
	public static String createODRL(AbstractPolicy distributeToThirdPartyPolicy){

		// set rule type
		String ruleType = "";
		if(distributeToThirdPartyPolicy.getRuleType()!= null)
		{
			switch(distributeToThirdPartyPolicy.getRuleType()) {
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
		if(null != distributeToThirdPartyPolicy.getPolicyType()) {
			type = distributeToThirdPartyPolicy.getPolicyType().toString();
		}

		//set assigner
		String assigner = "";
		if(null != distributeToThirdPartyPolicy.getAssigner() && !distributeToThirdPartyPolicy.getAssigner().isEmpty() && !distributeToThirdPartyPolicy.getPolicyType().equals(PolicyType.Request)) {
			assigner = "      \"assigner\": \"" + distributeToThirdPartyPolicy.getAssigner() + "\",    \r\n";
		}

		//set type and assignee
		String assignee = "";
		if( null != distributeToThirdPartyPolicy.getAssignee() && !distributeToThirdPartyPolicy.getAssignee().isEmpty() && !distributeToThirdPartyPolicy.getPolicyType().equals(PolicyType.Offer)) {
			assignee = "      \"assignee\": \"" + distributeToThirdPartyPolicy.getAssignee() + "\",    \r\n";
		}

		//set target
		String target = "";
		if(null != distributeToThirdPartyPolicy.getDataUrl()) {
			target = distributeToThirdPartyPolicy.getDataUrl().toString();
		}

		//set nextPolicy
		String nextPolicy = "";
		if(null != distributeToThirdPartyPolicy.getNextPolicyUrl()) {
			nextPolicy = distributeToThirdPartyPolicy.getNextPolicyUrl().toString();;
		}

		//set action
		String action = "";
		if(null != distributeToThirdPartyPolicy.getAction()) {
			action = distributeToThirdPartyPolicy.getAction().getIdsAction();
		}

		//set dutyAction
		String dutyAction = "";
		if(null != distributeToThirdPartyPolicy.getDutyAction()) {
			dutyAction = distributeToThirdPartyPolicy.getDutyAction().getIdsAction();
		}

		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:restrict-access-encoding\",    \r\n" +
				"  \"profile\": \"http://example.com/ids-profile\",    \r\n" +
				"  \"%s\": [{    \r\n" +
				"      \"target\": \"%s\",    \r\n%s%s" +
				"      \"action\": \"%s\",     \r\n" +
				"      \"duty\": [{    \r\n" +
				"        \"action\": \"%s\",    \r\n" +
				"        \"target\": \"%s\"    \r\n" +
				"      }]     \r\n" + 
				"  }]    \r\n" + 
				"} ", type, ruleType, target, assigner, assignee, action, dutyAction, nextPolicy);
	}
}
