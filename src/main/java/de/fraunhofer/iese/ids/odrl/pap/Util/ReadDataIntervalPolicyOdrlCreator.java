package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.ReadDataIntervalPolicy;

public class ReadDataIntervalPolicyOdrlCreator {
	
	public static String createODRL(ReadDataIntervalPolicy readDataIntervalPolicy){
		
		//set type and assignee
		String type = "Offer";
		String assignee = "";
		if( null != readDataIntervalPolicy.getAssignee() && !readDataIntervalPolicy.getAssignee().isEmpty()) {
			type = "Agreement";
			assignee = "      \"assignee\": \"" + readDataIntervalPolicy.getAssignee() + "\",    \r\n";
		}
		
		//set target
		String target = "";
		if(null != readDataIntervalPolicy.getDataUrl()) {
			target = readDataIntervalPolicy.getDataUrl().toString();
		}
		
		//set assigner 
		String assigner = "";
		if(null != readDataIntervalPolicy.getAssigner()) {
			assigner = readDataIntervalPolicy.getAssigner();
		}
		
		//set time interval
		String startTime = "";
		String endTime = "";
		if(null != readDataIntervalPolicy.getStartTime()) {
			startTime = readDataIntervalPolicy.getStartTime();
		}
		if(null != readDataIntervalPolicy.getEndTime()) {
			endTime = readDataIntervalPolicy.getEndTime();
		}
		
		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:restrict-access\",    \r\n" + 
				"  \"permission\": [{    \r\n" + 
				"      \"target\": \"%s\",    \r\n" + 
				"      \"assigner\": \"%s\",    \r\n%s" + 
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
