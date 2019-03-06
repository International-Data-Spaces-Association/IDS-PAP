package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.Action;
import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.EncodingPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.SpecificPurposePolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;
import de.fraunhofer.iese.ids.odrl.pap.model.RuleType;

public class EncodingPolicyOdrlCreator {
	
	public static String createODRL(EncodingPolicy encodingPolicy){

		// set rule type
		String ruleType = "permission";
		if(encodingPolicy.getRuleType()!= null && encodingPolicy.getRuleType().equals(RuleType.PROHIBITION))
		{
			ruleType = RuleType.PROHIBITION.getOdrlRuleType();
		}

		//set type
		String type = "";
		if(null != encodingPolicy.getPolicyType()) {
			type = encodingPolicy.getPolicyType().toString();
		}

		//set assigner
		String assigner = "";
		if(null != encodingPolicy.getAssigner() && !encodingPolicy.getAssigner().isEmpty() && !encodingPolicy.getPolicyType().equals(PolicyType.Request)) {
			assigner = "      \"assigner\": \"" + encodingPolicy.getAssigner() + "\",    \r\n";
		}

		//set type and assignee
		String assignee = "";
		if( null != encodingPolicy.getAssignee() && !encodingPolicy.getAssignee().isEmpty() && !encodingPolicy.getPolicyType().equals(PolicyType.Offer)) {
			assignee = "      \"assignee\": \"" + encodingPolicy.getAssignee() + "\",    \r\n";
		}

		//set target
		String target = "";
		if(null != encodingPolicy.getDataUrl()) {
			target = encodingPolicy.getDataUrl().toString();
		}

		//set encoding
		String encoding = "";
		if(null != encodingPolicy.getEncoding()) {
			encoding = encodingPolicy.getEncoding();
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
				"        \"leftOperand\": \""+ LeftOperand.ENCODING.getIdsLeftOperand() +"\",    \r\n" +
				"        \"operator\": \"eq\",    \r\n" + 
				"        \"rightOperand\": { \"@value\": \"%s\", \"@type\": \"xsd:anyURI\" }     \r\n" +
				"      }]     \r\n" + 
				"  }]    \r\n" + 
				"} ", type, ruleType, target, assigner, assignee, encoding);
	}
}
