package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.Action;
import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;
import de.fraunhofer.iese.ids.odrl.pap.model.RuleType;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.SpecificPurposePolicy;

public class SpecificPurposePolicyOdrlCreator {
	
	public static String createODRL(SpecificPurposePolicy specificPurposePolicy){

		// set rule type
		String ruleType = "";
		if(specificPurposePolicy.getRuleType()!= null)
		{
			switch(specificPurposePolicy.getRuleType()) {
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
		if(null != specificPurposePolicy.getPolicyType()) {
			type = specificPurposePolicy.getPolicyType().toString();
		}

		//set assigner
		String assigner = "";
		if(null != specificPurposePolicy.getAssigner() && !specificPurposePolicy.getAssigner().isEmpty() && !specificPurposePolicy.getPolicyType().equals(PolicyType.Request)) {
			assigner = "      \"assigner\": \"" + specificPurposePolicy.getAssigner() + "\",    \r\n";
		}

		//set type and assignee
		String assignee = "";
		if( null != specificPurposePolicy.getAssignee() && !specificPurposePolicy.getAssignee().isEmpty() && !specificPurposePolicy.getPolicyType().equals(PolicyType.Offer)) {
			assignee = "      \"assignee\": \"" + specificPurposePolicy.getAssignee() + "\",    \r\n";
		}

		//set target
		String target = "";
		if(null != specificPurposePolicy.getDataUrl()) {
			target = specificPurposePolicy.getDataUrl().toString();
		}

		//set purpose
		String purpose = "";
		if(null != specificPurposePolicy.getPurpose()) {
			purpose = specificPurposePolicy.getPurpose();
		}

		//set action
		String action = "";
		if(null != specificPurposePolicy.getAction()) {
			action = specificPurposePolicy.getAction().getIdsAction();
		}

		//set leftOperand
		String leftOperand = LeftOperand.PURPOSE.getIdsLeftOperand();
		
		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:restrict-access-purpose\",    \r\n" +
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
				"} ", type, ruleType, target, assigner, assignee, action, leftOperand, purpose);
	}
}
