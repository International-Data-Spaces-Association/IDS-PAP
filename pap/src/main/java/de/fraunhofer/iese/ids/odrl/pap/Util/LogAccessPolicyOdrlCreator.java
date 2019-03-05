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
			assigner = "    \"assigner\": \"" + logAccessPolicy.getAssigner() + "\",    \r\n";
		}

		//set type and assignee
		String assignee = "";
		if( null != logAccessPolicy.getAssignee() && !logAccessPolicy.getAssignee().isEmpty() && !logAccessPolicy.getPolicyType().equals(PolicyType.Offer)) {
			assignee = "    \"assignee\": \"" + logAccessPolicy.getAssignee() + "\",    \r\n";
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
				"  \"uid\": \"http://example.com/policy:log-access\",    \r\n" +
				"  \"permission\": [{    \r\n" +
				"    \"target\": \"%s\",    \r\n%s%s" +
				"    \"action\": \"read\",    \r\n" +
				"    \"duty\": [{    \r\n" +
				"      \"action\": [{    \r\n" +
				"        \"rdf:value\": { \"@id\": \"log\" },    \r\n" +
				"        \"refinement\": [{    \r\n" +
				"          \"leftOperand\": \"recipient\",    \r\n" +
				"          \"operator\": \"eq\",    \r\n" +
				"          \"rightOperand\":  { \"@value\": \"%s\", \"@type\": \"xsd:anyURI\" }    \r\n" +
				"        }]    \r\n" +
				"      }]     \r\n" +
				"    }]    \r\n" +
				"  }]    \r\n" +
				"} ", type, target, assigner, assignee, recipient);

	}
}
