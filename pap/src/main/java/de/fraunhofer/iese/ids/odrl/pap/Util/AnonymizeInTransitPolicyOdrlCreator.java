package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.AnonymizeInTransitPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.SpecificSystemPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;

public class AnonymizeInTransitPolicyOdrlCreator {
	
	public static String createODRL(AnonymizeInTransitPolicy anonymizeInTransitPolicy){

		// set rule type
		String ruleType = "";
		if(anonymizeInTransitPolicy.getRuleType()!= null)
		{
			switch(anonymizeInTransitPolicy.getRuleType()) {
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
		if(null != anonymizeInTransitPolicy.getPolicyType()) {
			type = anonymizeInTransitPolicy.getPolicyType().toString();
		}

		//set assigner
		String assigner = "";
		if(null != anonymizeInTransitPolicy.getAssigner() && !anonymizeInTransitPolicy.getAssigner().isEmpty() && !anonymizeInTransitPolicy.getPolicyType().equals(PolicyType.Request)) {
			assigner = "      \"assigner\": \"" + anonymizeInTransitPolicy.getAssigner() + "\",    \r\n";
		}

		//set type and assignee
		String assignee = "";
		if( null != anonymizeInTransitPolicy.getAssignee() && !anonymizeInTransitPolicy.getAssignee().isEmpty() && !anonymizeInTransitPolicy.getPolicyType().equals(PolicyType.Offer)) {
			assignee = "      \"assignee\": \"" + anonymizeInTransitPolicy.getAssignee() + "\",    \r\n";
		}

		//set target
		String target = "";
		if(null != anonymizeInTransitPolicy.getDataUrl()) {
			target = anonymizeInTransitPolicy.getDataUrl().toString();
		}

		//set jsonPath
		String jsonPath = "";
		if(null != anonymizeInTransitPolicy.getJsonPath()) {
			jsonPath = anonymizeInTransitPolicy.getJsonPath();
		}

		//set digit
		String digit = "";
		if(null != anonymizeInTransitPolicy.getDigit()) {
			digit = anonymizeInTransitPolicy.getDigit();
		}

		//set action
		String action = "";
		if(null != anonymizeInTransitPolicy.getAction()) {
			action = anonymizeInTransitPolicy.getAction().getIdsAction();
		}

		//set dutyAction
		String dutyAction = "";
		if(null != anonymizeInTransitPolicy.getDutyAction()) {
			dutyAction = anonymizeInTransitPolicy.getDutyAction().getIdsAction();
		}

		//set abstractAction
		String abstractAction = "";
		if(null != anonymizeInTransitPolicy.getAbstractAction()) {
			abstractAction = anonymizeInTransitPolicy.getAbstractAction().getIdsAction();
		}

		//set leftOperand
		String firstLeftOperand = LeftOperand.JSONPATH.getIdsLeftOperand();

		//set leftOperand
		String secondLeftOperand = LeftOperand.DIGIT.getIdsLeftOperand();

		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:anonymize-in-transit\",    \r\n" +
				"  \"%s\": [{    \r\n" +
				"      \"target\": \"%s\",    \r\n%s%s" +
				"      \"action\": \"%s\",     \r\n" +
				"      \"duty\": [{ \r\n" +
				"        \"action\": [{ \r\n" +
				"          \"rdf:value\": { \"@id\": \"%s\" }, \r\n" +
				"          \"rdf:type\": { \"@id\": \"%s\"},    \r\n" +
				"          \"refinement\": {        \r\n" +
				"            \"and\": {   \r\n" +
				"              \"@list\": [{         \r\n" +
				"                \"leftOperand\": \"%s\",      \r\n" +
				"                \"operator\": \"eq\",       \r\n" +
				"                \"rightOperand\": { \"@value\": \"$.content.%s\", \"@type\": \"xsd:string\" }      \r\n" +
				"              },{       \r\n" +
				"                \"leftOperand\": \"%s\",     \r\n" +
				"                \"operator\": \"eq\",      \r\n" +
				"                \"rightOperand\": { \"@value\": \"%s\", \"@type\": \"xsd:number\"  }       \r\n" +
				"              }]        \r\n" +
				"            }      \r\n" +
				"          }    \r\n" +
				"        }]     \r\n" +
				"      }]     \r\n" +
				"  }]    \r\n" +
				"} ", type, ruleType, target, assigner, assignee, action, dutyAction, abstractAction, firstLeftOperand, jsonPath,secondLeftOperand, digit);
	}
}
