package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.AbstractPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;

public class ReadAfterPaymentPolicyOdrlCreator {
	
	public static String createODRL(AbstractPolicy readAfterPaymentPolicy){

		// set rule type
		String ruleType = "";
		if(readAfterPaymentPolicy.getRuleType()!= null)
		{
			switch(readAfterPaymentPolicy.getRuleType()) {
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
		if(null != readAfterPaymentPolicy.getPolicyType()) {
			type = readAfterPaymentPolicy.getPolicyType().toString();
		}

		//set assigner
		String assigner = "";
		if(null != readAfterPaymentPolicy.getAssigner() && !readAfterPaymentPolicy.getAssigner().isEmpty() && !readAfterPaymentPolicy.getPolicyType().equals(PolicyType.Request)) {
			assigner = "      \"assigner\": \"" + readAfterPaymentPolicy.getAssigner() + "\",    \r\n";
		}

		//set type and assignee
		String assignee = "";
		if( null != readAfterPaymentPolicy.getAssignee() && !readAfterPaymentPolicy.getAssignee().isEmpty() && !readAfterPaymentPolicy.getPolicyType().equals(PolicyType.Offer)) {
			assignee = "      \"assignee\": \"" + readAfterPaymentPolicy.getAssignee() + "\",    \r\n";
		}

		//set target
		String target = "";
		if(null != readAfterPaymentPolicy.getDataUrl()) {
			target = readAfterPaymentPolicy.getDataUrl().toString();
		}

		//set payment
		int value = 0;
		String contract = "";
		String unit = "";
		if(null != readAfterPaymentPolicy.getPayment()) {
			value = readAfterPaymentPolicy.getPayment().getValue();
			contract = readAfterPaymentPolicy.getPayment().getContract();
			unit = readAfterPaymentPolicy.getPayment().getUnit();
		}

		//set action
		String action = "";
		if(null != readAfterPaymentPolicy.getAction()) {
			action = readAfterPaymentPolicy.getAction().getIdsAction();
		}

		//set leftOperand
		String leftOperand = LeftOperand.PAYAMOUNT.getIdsLeftOperand();

		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:provide-access-after-payment\",    \r\n" +
				"  \"profile\": \"http://example.com/ids-profile\",    \r\n" +
				"  \"%s\": [{    \r\n" +
				"      \"target\": \"%s\",    \r\n%s%s" +
				"      \"action\": \"%s\",     \r\n" +
				"      \"constraint\": [{    \r\n" +
				"        \"leftOperand\": \"%s\",    \r\n" +
				"        \"operator\": \"eq\",    \r\n" +
				"        \"rightOperand\": { \"@value\": \"%s\", \"@type\": \"xsd:decimal\" },     \r\n" +
				"        \"ids:contract\": \"%s\" ,     \r\n" +
				"        \"unit\": \"%s\"     \r\n" +
				"      }]     \r\n" +
				"  }]    \r\n" + 
				"} ", type, ruleType, target, assigner, assignee, action, leftOperand, value, contract, unit);
	}
}
