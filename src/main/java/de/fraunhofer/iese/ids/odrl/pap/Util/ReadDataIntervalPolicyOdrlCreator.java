package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.Action;
import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.ReadDataIntervalPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.RuleType;

public class ReadDataIntervalPolicyOdrlCreator {
	
	public static String createODRL(ReadDataIntervalPolicy readDataIntervalPolicy){

		// set rule type
		String ruleType = "";
		if(readDataIntervalPolicy.getRuleType()!= null)
		{
			switch(readDataIntervalPolicy.getRuleType()) {
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
		if(null != readDataIntervalPolicy.getPolicyType()) {
			type = readDataIntervalPolicy.getPolicyType().toString();
		}

		//set assigner
		String assigner = "";
		if(null != readDataIntervalPolicy.getAssigner() && !readDataIntervalPolicy.getAssigner().isEmpty() && !readDataIntervalPolicy.getPolicyType().equals(PolicyType.Request)) {
			assigner = "      \"assigner\": \"" + readDataIntervalPolicy.getAssigner() + "\",    \r\n";
		}

		//set type and assignee
		String assignee = "";
		if( null != readDataIntervalPolicy.getAssignee() && !readDataIntervalPolicy.getAssignee().isEmpty() && !readDataIntervalPolicy.getPolicyType().equals(PolicyType.Offer)) {
			assignee = "      \"assignee\": \"" + readDataIntervalPolicy.getAssignee() + "\",    \r\n";
		}

		//set target
		String target = "";
		if(null != readDataIntervalPolicy.getDataUrl()) {
			target = readDataIntervalPolicy.getDataUrl().toString();
		}

		//set time interval
		String startTime = "";
		String endTime = "";
		if(null != readDataIntervalPolicy.getStartTime()) {
			startTime = readDataIntervalPolicy.getStartTime() + "Z";
		}
		if(null != readDataIntervalPolicy.getEndTime()) {
			endTime = readDataIntervalPolicy.getEndTime() + "Z";
		}

		//set action
		String action = "";
		if(null != readDataIntervalPolicy.getAction()) {
			action = readDataIntervalPolicy.getAction().getIdsAction();
		}

		//set leftOperand
		String leftOperand = LeftOperand.DATETIME.getIdsLeftOperand();

		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:restrict-access-interval\",    \r\n" +
				"  \"profile\": \"http://example.com/ids-profile\",    \r\n" +
				"  \"%s\": [{    \r\n" +
				"      \"target\": \"%s\",    \r\n%s%s" +
				"      \"action\": \"%s\",     \r\n" +
				"      \"constraint\": {    \r\n" +
				"        \"and\": {    \r\n" +
				"          \"@list\": [{    \r\n" +
				"            \"leftOperand\": \"%s\",    \r\n" +
				"            \"operator\": \"gt\",    \r\n" +
				"            \"rightOperand\": { \"@value\": \"%s\", \"@type\": \"xsd:dateTime\" }     \r\n" +
				"            },{     \r\n" +
				"            \"leftOperand\": \"%s\",    \r\n" +
				"            \"operator\": \"lt\",    \r\n" +
				"            \"rightOperand\": { \"@value\": \"%s\", \"@type\": \"xsd:dateTime\" }     \r\n" +
				"          }]     \r\n" +
				"        }    \r\n" +
				"      }    \r\n" +
				"  }]    \r\n" + 
				"} ", type, ruleType, target, assigner, assignee, action, leftOperand, startTime, leftOperand, endTime);
	}
}
