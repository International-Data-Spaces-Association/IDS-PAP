package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.Action;
import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.EncodingPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;
import de.fraunhofer.iese.ids.odrl.pap.model.RuleType;

import java.lang.invoke.SwitchPoint;

import static de.fraunhofer.iese.ids.odrl.pap.model.RuleType.OBLIGATION;

public class EncodingPolicyOdrlCreator {
	
	public static String createODRL(EncodingPolicy encodingPolicy){

		// set rule type
		String ruleType = "";
		if(encodingPolicy.getRuleType()!= null)
		{
			switch(encodingPolicy.getRuleType()) {
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

		//set action
		String action = "";
		if(null != encodingPolicy.getAction()) {
			action = encodingPolicy.getAction().getIdsAction();
		}

		//set leftOperand
		String leftOperand = LeftOperand.ENCODING.getIdsLeftOperand();

		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:restrict-access-encoding\",    \r\n" +
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
				"} ", type, ruleType, target, assigner, assignee, action, leftOperand, encoding);
	}
}
