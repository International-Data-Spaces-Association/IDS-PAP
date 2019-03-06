package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.Action;
import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.ReadDataIntervalPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.RuleType;

public class ReadDataIntervalPolicyOdrlCreator {
	
	public static String createODRL(ReadDataIntervalPolicy readDataIntervalPolicy){

		// set rule type
		String ruleType = "permission";
		if(readDataIntervalPolicy.getRuleType()!= null && readDataIntervalPolicy.getRuleType().equals(RuleType.PROHIBITION))
		{
			ruleType = RuleType.PROHIBITION.getOdrlRuleType();
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
		
		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:restrict-access\",    \r\n" + 
				"  \"%s\": [{    \r\n" +
				"      \"target\": \"%s\",    \r\n%s%s" +
				"      \"action\": \""+ Action.READ.getIdsAction() +"\",     \r\n" +
				"      \"constraint\": {    \r\n" +
				"        \"and\": {    \r\n" +
				"          \"@list\": [{    \r\n" +
				"            \"leftOperand\": \""+ LeftOperand.DATETIME.getIdsLeftOperand() +"\",    \r\n" +
				"            \"operator\": \"gt\",    \r\n" +
				"            \"rightOperand\": { \"@value\": \"%s\", \"@type\": \"xsd:dateTime\" }     \r\n" +
				"            },{     \r\n" +
				"            \"leftOperand\": \""+ LeftOperand.DATETIME.getIdsLeftOperand() +"\",    \r\n" +
				"            \"operator\": \"lt\",    \r\n" +
				"            \"rightOperand\": { \"@value\": \"%s\", \"@type\": \"xsd:dateTime\" }     \r\n" +
				"          }]     \r\n" +
				"        }    \r\n" +
				"      }    \r\n" +
				"  }]    \r\n" + 
				"} ", type, ruleType, target, assigner, assignee, startTime, endTime);
	}
}
