package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.DeleteAtferPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.TimeUnit;

public class DeleteAfterPolicyOdrlCreator {
	
	public static String createODRL(DeleteAtferPolicy deleteAtferPolicy){
		
		//set type and assignee
		String type = "Offer";
		String assignee = "";
		if( null != deleteAtferPolicy.getAssignee() && !deleteAtferPolicy.getAssignee().isEmpty()) {
			type = "Agreement";
			assignee = "      \"assignee\": \"" + deleteAtferPolicy.getAssignee() + "\",    \r\n";
		}
		
		//set target
		String target = "";
		if(null != deleteAtferPolicy.getDataUrl()) {
			target = deleteAtferPolicy.getDataUrl().toString();
		}
		
		//set assigner 
		String assigner = "";
		if(null != deleteAtferPolicy.getAssigner()) {
			assigner = deleteAtferPolicy.getAssigner();
		}

		//set duration
		int value = 0;
		String timeUnit = "";
		String xsdPrefix = "";
		if(0 != deleteAtferPolicy.getDuration().getValue()) {
			value = deleteAtferPolicy.getDuration().getValue();
			// timeUnit = deleteAtferPolicy.getDuration().getTimeUnit().toString();
			switch(deleteAtferPolicy.getDuration().getTimeUnit()) {
				case HOURS:
					timeUnit = "H";
					xsdPrefix = "T";
					break;

				case DAYS:
					timeUnit = "D";
					break;

				case MONTHS:
					timeUnit = "M";
					break;

				case YEARS:
					timeUnit = "Y";
					break;

			}
		}

		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:delete-data\",    \r\n" +
				"  \"obligation\": [{    \r\n" +
				"      \"target\": \"%s\",    \r\n" + 
				"      \"assigner\": \"%s\",    \r\n%s" + 
				"      \"action\": [{    \r\n" +
				"          \"rdf:value\": { \"@id\": \"odrl:delete\" },     \r\n" +
				"      	   \"refinement\": [{    \r\n" +
				"        	  \"leftOperand\": \"Delay Period\",    \r\n" +
				"        	  \"operator\": \"eq\",    \r\n" +
				"        	  \"rightOperand\": { \"@value\": \"P%s%s%s\", \"@type\": \"xsd:duration\" }     \r\n" +
				"          }]     \r\n" +
				"      }]     \r\n" +
				"  }]    \r\n" + 
				"} ", type, target, assigner, assignee, xsdPrefix, value, timeUnit);
	}
}
