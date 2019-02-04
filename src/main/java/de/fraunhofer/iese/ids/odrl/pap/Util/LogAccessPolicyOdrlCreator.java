package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.LogAccessPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;
import de.fraunhofer.iese.ids.odrl.pap.model.SpecificPurposePolicy;

public class LogAccessPolicyOdrlCreator {
	
	public static String createODRL(LogAccessPolicy logAccessPolicy){

		//set type
		String type = "";
		if(null != logAccessPolicy.getPolicyType()) {
			type = logAccessPolicy.getPolicyType().toString();
		}

		//set assigner
		String assigner = "";
		if(null != logAccessPolicy.getAssigner() && !logAccessPolicy.getAssigner().isEmpty() && !logAccessPolicy.getPolicyType().equals(PolicyType.Request)) {
			assigner = "      \"assigner\": \"" + logAccessPolicy.getAssigner() + "\",    \r\n";
		}

		//set type and assignee
		String assignee = "";
		if( null != logAccessPolicy.getAssignee() && !logAccessPolicy.getAssignee().isEmpty() && !logAccessPolicy.getPolicyType().equals(PolicyType.Offer)) {
			assignee = "      \"assignee\": \"" + logAccessPolicy.getAssignee() + "\",    \r\n";
		}

		//set target
		String target = "";
		if(null != logAccessPolicy.getDataUrl()) {
			target = logAccessPolicy.getDataUrl().toString();
		}

		//set purpose
		String recipient = "";
		if(null != logAccessPolicy.getRecipient()) {
			recipient = logAccessPolicy.getRecipient();
		}
		
		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:restrict-access\",    \r\n" + 
				"  \"permission\": [{    \r\n" + 
				"     \"target\": \"%s\",    \r\n%s%s" +
				"     \"action\": [{    \r\n" +
				"        \"rdf:value\": { \"@id\": \"odrl:read\" }, \r\n" +
				"        \"refinement\": {    \r\n" +
				"           \"andSequence\": {  \r\n" +
				"              \"@list\": [ \r\n" +
				"                 {\"leftOperand\": \"fileFormat\", \r\n" +
				"                 \"operator\": \"eq\", \r\n" +
				"                 \"rightOperand\": \"https://www.wikidata.org/wiki/Q13769783\", \r\n" +
				"                 \"rdfs:comment\": \"log (Q13769783)\"}, \r\n" +
				"                 \"{leftOperand\": \"recipient\",    \r\n" +
				"                 \"operator\": \"eq\",    \r\n" +
				"                 \"rightOperand\": { \"@value\": \"%s\", \"@type\": \"xsd:string\" }}     \r\n" +
				"               ] \r\n" +
				"            }  \r\n" +
				"         }     \r\n" +
				"      }]     \r\n" +
				"   }]     \r\n" +
				"} ", type, target, assigner, assignee, recipient);
	}
}
