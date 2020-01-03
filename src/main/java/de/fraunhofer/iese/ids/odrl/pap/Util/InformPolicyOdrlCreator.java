package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.PartyFunction;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.AbstractPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;

public class InformPolicyOdrlCreator {
	
	public static String createODRL(AbstractPolicy informPolicy){

		// set rule type
		String ruleType = "";
		if(informPolicy.getRuleType()!= null)
		{
			switch(informPolicy.getRuleType()) {
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
		if(null != informPolicy.getPolicyType()) {
			type = informPolicy.getPolicyType().toString();
		}

		//set assigner
		String assigner = "";
		if(null != informPolicy.getAssigner() && !informPolicy.getAssigner().isEmpty() && !informPolicy.getPolicyType().equals(PolicyType.Request)) {
			assigner = "    \"assigner\": \"" + informPolicy.getAssigner() + "\",    \r\n";
		}

		//set type and assignee
		String assignee = "";
		if( null != informPolicy.getAssignee() && !informPolicy.getAssignee().isEmpty() && !informPolicy.getPolicyType().equals(PolicyType.Offer)) {
			assignee = "    \"assignee\": \"" + informPolicy.getAssignee() + "\",    \r\n";
		}

		//set target
		String target = "";
		if(null != informPolicy.getDataUrl()) {
			target = informPolicy.getDataUrl().toString();
		}

		//set informedParty
		String informedParty = "";
		if(null != informPolicy.getInformedParty()) {
			informedParty = informPolicy.getInformedParty();
		}

		//set action
		String action = "";
		if(null != informPolicy.getAction()) {
			action = informPolicy.getAction().getIdsAction();
		}

		//set dutyAction
		String dutyAction = "";
		if(null != informPolicy.getDutyAction()) {
			dutyAction = informPolicy.getDutyAction().getIdsAction();
		}

		//set partyFunction
		String partyFunction = PartyFunction.INFORMEDPARTY.getIdsPartyFunction();

		//return the formated String
		return String.format(" {    \r\n" +
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" +
				"  \"@type\": \"%s\",    \r\n" +
				"  \"uid\": \"http://example.com/policy:notify\",    \r\n" +
				"  \"profile\": \"http://example.com/ids-profile\",    \r\n" +
				"  \"%s\": [{    \r\n" +
				"    \"target\": \"%s\",    \r\n%s%s" +
				"    \"action\": \"%s\",    \r\n" +
				"    \"duty\": [{    \r\n" +
				"      \"action\": [{    \r\n" +
				"        \"rdf:value\": { \"@id\": \"%s\" }    \r\n" +
				"      }],     \r\n" +
				"      \"%s\": \"%s\"  \r\n" +
				"    }]    \r\n" +
				"  }]    \r\n" +
				"} ", type, ruleType, target, assigner, assignee, action, dutyAction, partyFunction, informedParty);

	}
}
