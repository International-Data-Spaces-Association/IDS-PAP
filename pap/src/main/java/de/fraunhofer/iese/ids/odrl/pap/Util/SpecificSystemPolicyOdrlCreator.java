package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;
import de.fraunhofer.iese.ids.odrl.pap.model.RuleType;
import de.fraunhofer.iese.ids.odrl.pap.model.SpecificPurposePolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.SpecificSystemPolicy;

public class SpecificSystemPolicyOdrlCreator {
	
	public static String createODRL(SpecificSystemPolicy specificSystemPolicy){

		// set rule type
		String ruleType = "permission";
		if(specificSystemPolicy.getRuleType()!= null && specificSystemPolicy.getRuleType().equals(RuleType.PROHIBITION))
		{
			ruleType = RuleType.PROHIBITION.getOdrlRuleType();
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
		
		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:restrict-access\",    \r\n" + 
				"  \"%s\": [{    \r\n" +
				"      \"target\": \"%s\",    \r\n%s%s" +
				"      \"action\": \"read\",     \r\n" + 
				"      \"constraint\": [{    \r\n" + 
				"        \"leftOperand\": \"system\",    \r\n" +
				"        \"operator\": \"eq\",    \r\n" + 
				"        \"rightOperand\": { \"@value\": \"%s\", \"@type\": \"xsd:anyURI\" }     \r\n" +
				"      }]     \r\n" + 
				"  }]    \r\n" + 
				"} ", type, ruleType, target, assigner, assignee, system);
	}
}
