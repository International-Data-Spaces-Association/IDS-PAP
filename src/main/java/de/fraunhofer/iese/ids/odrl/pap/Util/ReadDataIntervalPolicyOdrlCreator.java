package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;
import de.fraunhofer.iese.ids.odrl.pap.model.ReadDataIntervalPolicy;

public class ReadDataIntervalPolicyOdrlCreator {
	
	public static String createODRL(ReadDataIntervalPolicy readDataIntervalPolicy){

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
				"  \"permission\": [{    \r\n" + 
				"      \"target\": \"%s\",    \r\n%s%s" +
				"      \"action\": \"read\",     \r\n" + 
				"      \"constraint\": {    \r\n" +
				"        \"and\": {    \r\n" +
				"          \"@list\": [{    \r\n" +
				"            \"leftOperand\": \"dateTime\",    \r\n" +
				"            \"operator\": \"gt\",    \r\n" +
				"            \"rightOperand\": { \"@value\": \"%s\", \"@type\": \"xsd:dateTime\" }     \r\n" +
				"            },{     \r\n" +
				"            \"leftOperand\": \"dateTime\",    \r\n" +
				"            \"operator\": \"lt\",    \r\n" +
				"            \"rightOperand\": { \"@value\": \"%s\", \"@type\": \"xsd:dateTime\" }     \r\n" +
				"          }]     \r\n" +
				"        }    \r\n" +
				"      }    \r\n" +
				"  }]    \r\n" + 
				"} ", type, target, assigner, assignee, startTime, endTime);
	}
}
