package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.*;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.*;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.AbstractPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.CategorizedPolicy;
import de.fraunhofer.iese.ids.odrl.pattern.PatternUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class OdrlTranslator {
	
	public static String translate(Map map, Boolean providerSide, CategorizedPolicy defaultPolicy){

		CategorizedPolicy categorizedPolicy = null;
		if(null == map)
		{
			categorizedPolicy = defaultPolicy;
		}else{
			if(null == providerSide)
			{
				providerSide = true;
			}
			categorizedPolicy = PatternUtil.getCategorizedPolicy(map, providerSide);
		}

		try
		{
			String translation = "";
			//set policyType
			String policyType = "";
			if(null != categorizedPolicy.getPolicyType())
			{
				policyType = categorizedPolicy.getPolicyType().name();
			}

			//set target
			String target = "";
			if(null != categorizedPolicy.getDataUrl()) {
				target = categorizedPolicy.getDataUrl().toString();
			}

			//set pid
			String pid = "";
			if(null != categorizedPolicy.getPolicyUrl()) {
				pid = categorizedPolicy.getPolicyUrl().toString();
			}

			//set action
			Action action = null;
			if(null != categorizedPolicy.getAction()) {
				action = categorizedPolicy.getAction();
			}

			//set dutyAction
			Action dutyAction = null;
			boolean hasDuty = false;
			if(null != categorizedPolicy.getDutyAction()) {
				dutyAction = categorizedPolicy.getDutyAction();
				hasDuty = true;
			}

			//set decision
			RuleType decision = null;
			if(null != categorizedPolicy.getRuleType()) {
				decision = categorizedPolicy.getRuleType();
			}

			if(decision.equals(RuleType.OBLIGATION))
			{
				translation = translation.concat("In this Policy " + policyType + " example, the " + decision.getOdrlRuleType() + " rule demands the Data Consumer to " + action.toString().toLowerCase() + " the target asset.\n");
			}else{
				translation = translation.concat("In this Policy " + policyType + " example, the " + decision.getOdrlRuleType() + " rule " + decision.getMydataDecision() + "s the Data Consumer to " + action.toString().toLowerCase() + " the target asset.\n");
			}
			translation = translation.concat("The identifier of this policy and the target asset are " + pid + " and " + target + ", respectively.\n\n");

			String assignee = "";
			String assigner = "";
			if(null != categorizedPolicy.getAssigner() && !categorizedPolicy.getAssigner().isEmpty()) {
				assigner = getLastSplitElement(categorizedPolicy.getAssigner());
				translation = translation.concat("The assigner is an IDS party that is issuing the rule. Here the assigner is the " + assigner + " party. This party is either the Data Owner or the Data Provider of the specified data asset in the IDS context.\n");
			}
			if(null != categorizedPolicy.getAssignee() && !categorizedPolicy.getAssignee().isEmpty()) {
				assignee = getLastSplitElement(categorizedPolicy.getAssignee());
				translation = translation.concat("The assignee is an IDS party that is the recipient the of rule. Here the assignee is the " + assignee + " party.\n");
			}

			translation = translation.concat("\n");

			//get payment
			Payment p = ((AbstractPolicy) categorizedPolicy).getPayment();
			if (PatternUtil.isNotNull(p))
			{
				String value = String.valueOf(p.getValue());
				String contract = p.getContract();
				String unit = p.getUnit();
				translation = translation.concat("In this policy, the " + assigner + " party announces the Data " + getLastElement(contract) + " under specific condition; the Data Consumer is only allowed to " + action.toString().toLowerCase() + " the data after paying " + value + " " +  getLastElement(unit) +".\n\n");
			}

			//get execute action
			if ((hasDuty && dutyAction.equals(Action.DELETE)))
			{
				translation = translation.concat("The " + assigner + " party demands that the Data Consumer " + dutyAction.toString().toLowerCase() + "s the data asset right after it is used.\n\n");
			}

			if ((action.equals(Action.DELETE) && decision.equals(RuleType.OBLIGATION)))
			{
				translation = translation.concat("The " + assigner + " party demands that the Data Consumer " + action.toString().toLowerCase() + "s the data asset at a specified date and time.\n");
			}

			// get timer
			Duration d = ((AbstractPolicy) categorizedPolicy).getDuration();
			if (PatternUtil.isNotNull(d) && (hasDuty || decision.equals(RuleType.OBLIGATION)))
			{
				translation = translation.concat("The Data Consumer has to wait " + d.getValue() + " " + d.getTimeUnit().toString().toLowerCase() + ", before exercising the duty (the Data Provider's demand).\n");
			}

			// get date and time
			String dateTimeRefinement = ((AbstractPolicy) categorizedPolicy).getDateTime();
			if (PatternUtil.isNotNull(dateTimeRefinement) && (hasDuty || decision.equals(RuleType.OBLIGATION)))
			{
				translation = translation.concat("The Data Consumer has to exercise the action which is demanded by the Data Provider at " + dateTimeRefinement + ".\n");
			}

			// get conditions
			String event = ((AbstractPolicy) categorizedPolicy).getEvent();
			if (PatternUtil.isNotNull(event))
			{
				translation = translation.concat("The " + assigner + " party restricts the usage of the data asset to a specific event; the Data Consumer is " + decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset when the " +  getLastSplitElement(event) + " event has been triggered.\n\n");
			}

			String count = ((AbstractPolicy) categorizedPolicy).getCount();
			if (PatternUtil.isNotNull(count))
			{
				translation = translation.concat("This policy specifies that the Data Consumer is " + decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset (not more than) " +  count + " times.\n\n");
			}

			String purpose = ((AbstractPolicy) categorizedPolicy).getPurpose();
			if (PatternUtil.isNotNull(purpose))
			{
				translation = translation.concat("The " + assigner + " party restricts the usage of the data asset to a specific purpose; the Data Consumer is " + decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset for " +  getLastSplitElement(purpose) + " purpose.\n\n");
			}

			String location = ((AbstractPolicy) categorizedPolicy).getLocation();
			if (PatternUtil.isNotNull(location))
			{
				translation = translation.concat("The " + assigner + " party restricts the usage of the data asset to a specific location; the Data Consumer is " + decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset when it's connector is located in " +  getLastElement(location) +".\n\n");

			}

			String system = ((AbstractPolicy) categorizedPolicy).getSystem();
			if (PatternUtil.isNotNull(system))
			{
				translation = translation.concat("The " + assigner + " party restricts the usage of the data asset to a specific system; the Data Consumer is " + decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset when the system which will process the data is " +  getLastSplitElement(system) +".\n\n");
			}

			String encoding = ((AbstractPolicy) categorizedPolicy).getEncoding();
			if (PatternUtil.isNotNull(encoding))
			{
				translation = translation.concat("The data asset must be encrypted before it is distributed.\n\n");
			}

			String recipient = ((AbstractPolicy) categorizedPolicy).getRecipient();
			if (PatternUtil.isNotNull(recipient) && (hasDuty || decision.equals(RuleType.OBLIGATION)))
			{
				translation = translation.concat("The " + assigner + " party demands that the Data Consumer logs the information about the data usage in " + recipient + " system.\n\n");
			}

            String systemDevice = ((AbstractPolicy) categorizedPolicy).getSystemDevice();
            if (PatternUtil.isNotNull(systemDevice) && (hasDuty || decision.equals(RuleType.OBLIGATION)))
            {
				translation = translation.concat("The " + assigner + " party demands that the Data Consumer logs the information about the data usage in " + getLastSplitElement(systemDevice) + " system.\n\n");
			}

			String informedPartyValue = ((AbstractPolicy) categorizedPolicy).getInformedParty();
			if (PatternUtil.isNotNull(informedPartyValue) && (hasDuty || decision.equals(RuleType.OBLIGATION)))
			{
				translation = translation.concat("The " + assigner + " party demands that the Data Consumer informs the " + getLastSplitElement(informedPartyValue) + " party about the usage of data.\n\n");
			}

			if ((action.equals(Action.NEXTPOLICY) && decision.equals(RuleType.OBLIGATION))
					|| (hasDuty && dutyAction.equals(Action.NEXTPOLICY)))
			{
				translation = translation.concat("The next policy action expresses the allowable usages by third-parties; it is demanded that the usage of the data asset by the third party has to be limited to the conditions that are specified in the target policy.\n\n");
			}

			String digit = ((AbstractPolicy) categorizedPolicy).getDigit();
			String jsonPath = ((AbstractPolicy) categorizedPolicy).getJsonPath();
			if (PatternUtil.isNotNull(digit) && PatternUtil.isNotNull(jsonPath)) {
				translation = translation.concat("The Data Consumer has to exercise the action which is demanded by the Data Provider before the usage of the data asset. Here, the policy specifies that the Data Consumer must "
						+ dutyAction.toString().toLowerCase() + " the " + jsonPath + " field in the data asset up to " + digit + " digit.\n");
			}

			// get and set datetimes
			String start = ((AbstractPolicy) categorizedPolicy).getStartTime();
			String end = ((AbstractPolicy) categorizedPolicy).getEndTime();
			if (PatternUtil.isNotNull(start) && PatternUtil.isNotNull(end)) {
				translation = translation.concat("The " + assigner + " party restricts the usage of the data asset to a specific time interval; the Data Consumer is " + decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data from " + start + " to " + end + ".\n");
			}
			//return mydataPolicy.toString();
			return translation;
		}
		catch (NullPointerException e){
			return "Please, be aware that your ODRL policy must comply to the IDS profile. " +
					"Undefined IDS Actions or Left Operands are not accepted. " +
					"Check your ODRL policy and try again!";
		}

	}

	 private static String getLastSplitElement(String url) {
		String value;
		String[] bits = url.split(":");
		value = bits[bits.length-1];
		return value;
	}

	private static String getLastElement(String url) {
		String value;
		String[] bits = url.split("/");
		value = bits[bits.length-1];
		return value;
	}

}
