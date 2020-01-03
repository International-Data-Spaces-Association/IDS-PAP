package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.ConstraintRightOperands;
import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.AbstractPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;

public class DeleteAfterEachUsagePolicyOdrlCreator {
	
	public static String createODRL(AbstractPolicy deleteAfterEachUsagePolicy){

		// set rule type
		String ruleType = "";
		if(deleteAfterEachUsagePolicy.getRuleType()!= null)
		{
			switch(deleteAfterEachUsagePolicy.getRuleType()) {
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
		if(null != deleteAfterEachUsagePolicy.getPolicyType()) {
			type = deleteAfterEachUsagePolicy.getPolicyType().toString();
		}

		//set assigner
		String assigner = "";
		if(null != deleteAfterEachUsagePolicy.getAssigner() && !deleteAfterEachUsagePolicy.getAssigner().isEmpty() && !deleteAfterEachUsagePolicy.getPolicyType().equals(PolicyType.Request)) {
			assigner = "      \"assigner\": \"" + deleteAfterEachUsagePolicy.getAssigner() + "\",    \r\n";
		}

		//set type and assignee
		String assignee = "";
		if( null != deleteAfterEachUsagePolicy.getAssignee() && !deleteAfterEachUsagePolicy.getAssignee().isEmpty() && !deleteAfterEachUsagePolicy.getPolicyType().equals(PolicyType.Offer)) {
			assignee = "      \"assignee\": \"" + deleteAfterEachUsagePolicy.getAssignee() + "\",    \r\n";
		}

		//set target
		String target = "";
		if(null != deleteAfterEachUsagePolicy.getDataUrl()) {
			target = deleteAfterEachUsagePolicy.getDataUrl().toString();
		}

		//set action
		String action = "";
		if(null != deleteAfterEachUsagePolicy.getAction()) {
			action = deleteAfterEachUsagePolicy.getAction().getIdsAction();
		}

		//set dutyAction
		String dutyAction = "";
		if(null != deleteAfterEachUsagePolicy.getDutyAction()) {
			dutyAction = deleteAfterEachUsagePolicy.getDutyAction().getIdsAction();
		}

		//set leftOperand
		String leftOperand = LeftOperand.EVENT.getIdsLeftOperand();

		//set rightOperand
		String  rightOperand = ConstraintRightOperands.Policy_Rule_Usage.getIdsRightOperand();

		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:delete-after-usage\",    \r\n" +
				"  \"profile\": \"http://example.com/ids-profile\",    \r\n" +
				"  \"%s\": [{    \r\n" +
				"      \"target\": \"%s\",    \r\n%s%s" +
				"      \"action\": \"%s\",     \r\n" +
				"      \"duty\": [{ \r\n" +
				"        \"action\": [{ \r\n" +
				"          \"rdf:value\": { \"@id\": \"%s\" } \r\n" +
				"        }],     \r\n" +
				"        \"constraint\": [{        \r\n" +
				"          \"leftOperand\": \"%s\",      \r\n" +
				"          \"operator\": \"gt\",       \r\n" +
				"          \"rightOperand\": { \"@id\": \"%s\"}      \r\n" +
				"        }]        \r\n" +
				"      }]     \r\n" +
				"  }]    \r\n" +
				"} ", type, ruleType, target, assigner, assignee, action, dutyAction, leftOperand, rightOperand);
	}
}
