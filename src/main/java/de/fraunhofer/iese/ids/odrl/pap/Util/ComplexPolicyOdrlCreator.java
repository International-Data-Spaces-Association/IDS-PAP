package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.Condition;
import de.fraunhofer.iese.ids.odrl.pap.model.Operator;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.AbstractPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;

import java.util.ArrayList;

public class ComplexPolicyOdrlCreator {
	
	public static String createODRL(AbstractPolicy abstractPolicy){

		// set rule type
		String ruleType = "";
		if(abstractPolicy.getRuleType()!= null)
		{
			switch(abstractPolicy.getRuleType()) {
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
		if(null != abstractPolicy.getPolicyType()) {
			type = abstractPolicy.getPolicyType().toString();
		}

		//set assigner
		String assigner = "";
		if(null != abstractPolicy.getAssigner() && !abstractPolicy.getAssigner().isEmpty() && !abstractPolicy.getPolicyType().equals(PolicyType.Request)) {
			assigner = "      \"assigner\": \"" + abstractPolicy.getAssigner() + "\",    \r\n";
		}

		//set type and assignee
		String assignee = "";
		if( null != abstractPolicy.getAssignee() && !abstractPolicy.getAssignee().isEmpty() && !abstractPolicy.getPolicyType().equals(PolicyType.Offer)) {
			assignee = "      \"assignee\": \"" + abstractPolicy.getAssignee() + "\",    \r\n";
		}

		//set target
		String target = "";
		if(null != abstractPolicy.getDataUrl()) {
			target = abstractPolicy.getDataUrl().toString();
		}

		//set action
		String action = "";
		if(null != abstractPolicy.getAction()) {
			action = abstractPolicy.getAction().getIdsAction();
		}

		ArrayList<Condition> odrlConditions = new ArrayList();

		//set location
		if(null != abstractPolicy.getLocation()) {
			String location = abstractPolicy.getLocation();
			Condition locationCondition = new Condition(Operator.EQUALS, LeftOperand.ABSOLUTESPATIALPOSITION , location, "xsd:anyURI",
					null, null,null,null);

			odrlConditions.add(locationCondition);
		}

		//set system
		if(null != abstractPolicy.getSystem()) {
			String system = abstractPolicy.getSystem();
			Condition systemCondition = new Condition(Operator.EQUALS, LeftOperand.SYSTEM , system, "xsd:anyURI",
					null, null,null,null);
			odrlConditions.add(systemCondition);
		}

		//set purpose
		if(null != abstractPolicy.getPurpose()) {
			String purpose = abstractPolicy.getPurpose();
			Condition purposeCondition = new Condition(Operator.EQUALS, LeftOperand.PURPOSE , purpose, "xsd:anyURI",
					null, null,null,null);
			odrlConditions.add(purposeCondition);
		}

		//set event
		if(null != abstractPolicy.getEvent()) {
			String event = abstractPolicy.getEvent();
			Condition eventCondition = new Condition(Operator.EQUALS, LeftOperand.EVENT , event, "xsd:anyURI",
					null, null,null,null);
			odrlConditions.add(eventCondition);
		}

		//set time interval
		if(null != abstractPolicy.getStartTime()) {
			String startTime = abstractPolicy.getStartTime() + "Z";
			Condition startTimeCondition = new Condition(Operator.GREATER, LeftOperand.DATETIME , startTime, "xsd:dateTime",
					null, null,null,null);
			odrlConditions.add(startTimeCondition);
		}

		//set time interval
		if(null != abstractPolicy.getEndTime()) {
			String endTime = abstractPolicy.getEndTime() + "Z";
			Condition endTimeCondition = new Condition(Operator.LESS, LeftOperand.DATETIME , endTime, "xsd:dateTime",
					null, null,null,null);
			odrlConditions.add(endTimeCondition);
		}

		//set time interval
		if(null != abstractPolicy.getDateTime()) {
			String dateTime = abstractPolicy.getDateTime() + "Z";
			Condition dateTimeCondition = new Condition(Operator.EQUALS, LeftOperand.DATETIME , dateTime, "xsd:dateTime",
					null, null,null,null);
			odrlConditions.add(dateTimeCondition);
		}

		//set payment
		if(null != abstractPolicy.getPayment()) {
			int value = abstractPolicy.getPayment().getValue();
			String contract = abstractPolicy.getPayment().getContract();
			Condition paymentCondition = new Condition(Operator.EQUALS, LeftOperand.PAYAMOUNT , String.valueOf(value), "xsd:decimal",
					null, null,contract,null);
			odrlConditions.add(paymentCondition);
		}

		//set count
		if(null != abstractPolicy.getCount()) {
			String count = abstractPolicy.getCount();
			Condition countCondition = new Condition(Operator.EQUALS, LeftOperand.COUNT , count, "xsd:decimal",
					null, null,null,null);
			odrlConditions.add(countCondition);
		}

		//set systemDevice
		if(null != abstractPolicy.getCount()) {
			String systemDevice = abstractPolicy.getCount();
			Condition systemDeviceCondition = new Condition(Operator.EQUALS, LeftOperand.SYSTEMDEVICE , systemDevice, "xsd:anyURI",
					null, null,null,null);
			odrlConditions.add(systemDeviceCondition);
		}

		//set encoding
		if(null != abstractPolicy.getEncoding()) {
			String encoding = abstractPolicy.getEncoding();
			Condition encodingCondition = new Condition(Operator.EQUALS, LeftOperand.ENCODING , encoding, "xsd:anyURI",
					null, null,null,null);
			odrlConditions.add(encodingCondition);
		}

		String conditionBlock = getConditionBlock(odrlConditions);


		//return the formated String
		return String.format(" {    \r\n" + 
				"  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" + 
				"  \"@type\": \"%s\",    \r\n" + 
				"  \"uid\": \"http://example.com/policy:restrict-access-purpose\",    \r\n" +
				"  \"profile\": \"http://example.com/ids-profile\",    \r\n" +
				"  \"%s\": [{    \r\n" +
				"      \"target\": \"%s\",    \r\n%s%s" +
				"      \"action\": \"%s\"%s     \r\n" +
				"  }]    \r\n" +
				"} ", type, ruleType, target, assigner, assignee, action, conditionBlock);
	}

	private static String getConditionBlock(ArrayList<Condition> conditions) {

		String conditionInnerBlock = "";
		if (!conditions.isEmpty())
		{
			String firstCondition = conditions.get(0).toString();
			String otherConditions = "";
			for(int i=1 ; i<conditions.size(); i++)
			{
				otherConditions += "," + conditions.get(i).toString();
			}
			conditionInnerBlock = firstCondition + otherConditions;

			return String.format(",     \r\n" +
					"      \"constraint\": [%s] " , conditionInnerBlock);
		}

		return "";
	}
}
