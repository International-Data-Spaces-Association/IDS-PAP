package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.Action;
import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.DeleteAtferPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;
import de.fraunhofer.iese.ids.odrl.pap.model.TimeUnit;
import org.thymeleaf.templateparser.text.AbstractChainedTextHandler;

public class DeleteAfterPolicyOdrlCreator {
	
	public static String createODRL(DeleteAtferPolicy deleteAtferPolicy){

		//set type
		String type = "";
		if(null != deleteAtferPolicy.getPolicyType()) {
			type = deleteAtferPolicy.getPolicyType().toString();
		}

		//set assigner
		String assigner = "";
		if(null != deleteAtferPolicy.getAssigner() && !deleteAtferPolicy.getAssigner().isEmpty() && !deleteAtferPolicy.getPolicyType().equals(PolicyType.Request)) {
			assigner = "      \"assigner\": \"" + deleteAtferPolicy.getAssigner() + "\",    \r\n";
		}

		//set type and assignee
		String assignee = "";
		if( null != deleteAtferPolicy.getAssignee() && !deleteAtferPolicy.getAssignee().isEmpty() && !deleteAtferPolicy.getPolicyType().equals(PolicyType.Offer)) {
			assignee = "      \"assignee\": \"" + deleteAtferPolicy.getAssignee() + "\",    \r\n";
		}

		//set target
		String target = "";
		if(null != deleteAtferPolicy.getDataUrl()) {
			target = deleteAtferPolicy.getDataUrl().toString();
		}

		//set duration
		int value = 1;
		String timeUnit = "";
		String xsdPrefix = "";
		if(0 != deleteAtferPolicy.getDuration().getValue()) {
			value = deleteAtferPolicy.getDuration().getValue();
			// timeUnit = deleteAtferPolicy.getDuration().getTimeUnit().toString();
			switch(deleteAtferPolicy.getDuration().getTimeUnit()) {
				case HOURS:
					timeUnit = TimeUnit.HOURS.getOdrlXsdDuration();
					xsdPrefix = "T";
					break;

				case DAYS:
					timeUnit = TimeUnit.DAYS.getOdrlXsdDuration();
					break;

				case MONTHS:
					timeUnit = TimeUnit.MONTHS.getOdrlXsdDuration();
					break;

				case YEARS:
					timeUnit = TimeUnit.YEARS.getOdrlXsdDuration();
					break;

			}
		}

		//set action
		String action = "";
		if(null != deleteAtferPolicy.getAction()) {
			action = deleteAtferPolicy.getAction().getIdsAction();
		}

		//set leftOperand
		String leftOperand = LeftOperand.DELAY_PERIOD.getIdsLeftOperand();

		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:delete-data\",    \r\n" +
				"  \"obligation\": [{    \r\n" +
				"      \"target\": \"%s\",    \r\n%s%s" +
				"      \"action\": [{    \r\n" +
				"          \"rdf:value\": { \"@id\": \"%s\" },     \r\n" +
				"      	   \"refinement\": [{    \r\n" +
				"        	  \"leftOperand\": \"%s\",    \r\n" +
				"        	  \"operator\": \"eq\",    \r\n" +
				"        	  \"rightOperand\": { \"@value\": \"P%s%s%s\", \"@type\": \"xsd:duration\" }     \r\n" +
				"          }]     \r\n" +
				"      }]     \r\n" +
				"  }]    \r\n" + 
				"} ", type, target, assigner, assignee, action, leftOperand, xsdPrefix, value, timeUnit);
	}
}
