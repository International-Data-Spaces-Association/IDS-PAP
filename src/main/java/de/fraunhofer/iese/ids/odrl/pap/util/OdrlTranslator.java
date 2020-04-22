package de.fraunhofer.iese.ids.odrl.pap.util;

import de.fraunhofer.iese.ids.odrl.pap.model.*;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.RuleType;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.IPolicy;
import de.fraunhofer.iese.ids.odrl.pattern.Utils;

import java.util.Map;


public class OdrlTranslator {
	
	public static String translate(Map map, Boolean providerSide, IPolicy defaultPolicy){

		IPolicy policy = null;
		if(null == map)
		{
			policy = defaultPolicy;
		}else{
			if(null == providerSide)
			{
				providerSide = true;
			}
			policy = Utils.getPolicy(map, providerSide);
		}


		try
		{
			String translation = "";
			//set policyType
			String policyType = "";
			if(null != policy.getType())
			{
				policyType = policy.getType().name();
			}

			//set target
			String target = "";
			if(null != policy.getTarget()) {
				target = policy.getTarget().toString();
			}

			//set pid
			String pid = "";
			if(null != policy.getPolicyId()) {
				pid = policy.getPolicyId().toString();
			}

			String consumer = "";
			String provider = "";
			if(null != policy.getProvider()) {
				provider = policy.getProvider().getName();
				translation = translation.concat("The provider is an IDS party that is issuing the rule. Here the provider is the " + provider + " party. This party is either the Data Owner or the Data Provider of the specified data asset in the IDS context.\n");
			}
			if(null != policy.getConsumer()) {
				consumer = policy.getConsumer().getName();
				translation = translation.concat("The consumer is an IDS party that is the recipient the of rule. Here the consumer is the " + consumer + " party.\n");
			}

			translation = translation.concat("\n");

			// USE ACTION
			if(null != policy.getPermissionRuleUseAction())
			{
				ActionType action = policy.getPermissionRuleUseAction().getUseAction().getType();
				RuleType decision = policy.getPermissionRuleUseAction().getType();
				translation = translation.concat("In this Policy " + policyType + " example, the " + decision.getOdrlRuleType() + " rule " +
						decision.getMydataDecision() + "s the Data Consumer to " + action.toString().toLowerCase() + " the target asset.\n");
				translation = translation.concat("The identifier of this policy and the target asset are " + pid + " and " + target + ", respectively.\n\n");

				if(null != policy.getPermissionRuleUseAction().getEventConstraint())
				{
					String event = policy.getPermissionRuleUseAction().getEventConstraint().getRightOperand().getValue();
					translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific event; the Data Consumer is " +
							decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset when the " +
							getLastSplitElement(event) + " event has been triggered.\n\n");
				}
				if(null != policy.getPermissionRuleUseAction().getPurposeConstraint())
				{
					String purpose = policy.getPermissionRuleUseAction().getPurposeConstraint().getRightOperand().getValue();
					translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific purpose; the Data Consumer is " +
							decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset for " +
							getLastSplitElement(purpose) + " purpose.\n\n");
				}
				if(null != policy.getPermissionRuleUseAction().getSystemConstraint())
				{
					String system = policy.getPermissionRuleUseAction().getSystemConstraint().getRightOperand().getValue();
					translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific system; the Data Consumer is " +
							decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset when the system which will process the data is " +
							getLastSplitElement(system) +".\n\n");
				}
				if(null != policy.getPermissionRuleUseAction().getAbsoluteSpatialPositionConstraint())
				{
					String location = policy.getPermissionRuleUseAction().getAbsoluteSpatialPositionConstraint().getRightOperand().getValue();
					translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific location; the Data Consumer is " +
							decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset when it's connector is located in " +
							getLastElement(location) +".\n\n");

				}
				if(null != policy.getPermissionRuleUseAction().getPaymentConstraint())
				{
					String value = policy.getPermissionRuleUseAction().getPaymentConstraint().getRightOperand().getValue();
					String contract = policy.getPermissionRuleUseAction().getPaymentConstraint().getContract();
					String unit = policy.getPermissionRuleUseAction().getPaymentConstraint().getUnit();
					translation = translation.concat("In this policy, the " + provider + " party announces the Data " + getLastElement(contract) +
							" under specific condition; the Data Consumer is only allowed to " + ActionType.USE.toString().toLowerCase() +
							" the data after paying " + value + " " +  getLastElement(unit) +".\n\n");
				}
				if(null != policy.getPermissionRuleUseAction().getTimeIntervalConstraint() && null != policy.getPermissionRuleUseAction().getTimeIntervalConstraint().getRightOperand() )
				{
					// get and set datetimes
					String start = policy.getPermissionRuleUseAction().getTimeIntervalConstraint().getRightOperand().getValue();
					String end = policy.getPermissionRuleUseAction().getTimeIntervalConstraint().getSecondRightOperand().getValue();
					translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific time interval; the Data Consumer is " +
							decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data from " + start + " to " + end + ".\n");
				}
				if(null != policy.getPermissionRuleUseAction().getCountConstraint())
				{
					String count = policy.getPermissionRuleUseAction().getCountConstraint().getRightOperand().getValue();
					translation = translation.concat("This policy specifies that the Data Consumer is " + decision.getMydataDecision() + "ed to " +
							action.toString().toLowerCase() + " the data asset (not more than) " +  count + " times.\n\n");
				}
				if(null != policy.getPermissionRuleUseAction().getDeleteDutyAction() && null != policy.getPermissionRuleUseAction().getEventDutyConstraint())
				{
					ActionType dutyAction = policy.getPermissionRuleUseAction().getDeleteDutyAction().getType();
						translation = translation.concat("The " + provider + " party demands that the Data Consumer " +
								dutyAction.toString().toLowerCase() + "s the data asset right after it is used.\n\n");

				}
				if(null != policy.getPermissionRuleUseAction().getAnonymizeDutyAction() && null != policy.getPermissionRuleUseAction().getEventDutyConstraint())
				{
					String jsonPath = policy.getPermissionRuleUseAction().getAnonymizeDutyAction().getJsonPathRefinement().getRightOperand().getValue();
					ActionType dutyAction = policy.getPermissionRuleUseAction().getAnonymizeDutyAction().getType();
					translation = translation.concat("The Data Consumer has to exercise the action which is demanded by the Data Provider before the usage of the data asset. Here, the policy specifies that the Data Consumer must "
							+ dutyAction.toString().toLowerCase() + " the " + jsonPath + " field in the data asset.\n");
				}
				if(null != policy.getPermissionRuleUseAction().getLogDutyAction())
				{
					String systemDevice = policy.getPermissionRuleUseAction().getLogDutyAction().getSystemDeviceRefinement().getRightOperand().getValue();
					translation = translation.concat("The " + provider + " party demands that the Data Consumer logs the information about the data usage in " +
							getLastSplitElement(systemDevice) + " system.\n\n");
				}
				if(null != policy.getPermissionRuleUseAction().getInformDutyAction())
				{
					String informedPartyValue = policy.getPermissionRuleUseAction().getInformedParty().getName();
					translation = translation.concat("The " + provider + " party demands that the Data Consumer informs the " +
							getLastSplitElement(informedPartyValue) + " party about the usage of data.\n\n");

				}
			}
			if(null != policy.getProhibitionRuleUseAction())
			{
				ActionType action = policy.getProhibitionRuleUseAction().getUseAction().getType();
				RuleType decision = policy.getProhibitionRuleUseAction().getType();
				translation = translation.concat("In this Policy " + policyType + " example, the " + decision.getOdrlRuleType() + " rule " +
						decision.getMydataDecision() + "s the Data Consumer to " + action.toString().toLowerCase() + " the target asset.\n");
				translation = translation.concat("The identifier of this policy and the target asset are " + pid + " and " + target + ", respectively.\n\n");

				if(null != policy.getProhibitionRuleUseAction().getEventConstraint())
				{
					String event = policy.getProhibitionRuleUseAction().getEventConstraint().getRightOperand().getValue();
					translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific event; the Data Consumer is " +
							decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset when the " +
							getLastSplitElement(event) + " event has been triggered.\n\n");
				}
				if(null != policy.getProhibitionRuleUseAction().getPurposeConstraint())
				{
					String purpose = policy.getProhibitionRuleUseAction().getPurposeConstraint().getRightOperand().getValue();
					translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific purpose; the Data Consumer is " +
							decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset for " +
							getLastSplitElement(purpose) + " purpose.\n\n");
				}
				if(null != policy.getProhibitionRuleUseAction().getSystemConstraint())
				{
					String system = policy.getProhibitionRuleUseAction().getSystemConstraint().getRightOperand().getValue();
					translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific system; the Data Consumer is " +
							decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset when the system which will process the data is " +
							getLastSplitElement(system) +".\n\n");
				}
				if(null != policy.getProhibitionRuleUseAction().getAbsoluteSpatialPositionConstraint())
				{
					String location = policy.getProhibitionRuleUseAction().getAbsoluteSpatialPositionConstraint().getRightOperand().getValue();
					translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific location; the Data Consumer is " +
							decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset when it's connector is located in " +
							getLastElement(location) +".\n\n");
				}
				if(null != policy.getProhibitionRuleUseAction().getTimeIntervalConstraint())
				{
					//TODO
				}
			}
			/*if(null != policy.getObligationRuleUseAction())
			{
				ActionType action = policy.getObligationRuleUseAction().getUseAction().getType();
				RuleType decision = policy.getObligationRuleUseAction().getType();
				translation = translation.concat("In this Policy " + policyType + " example, the " + decision.getOdrlRuleType() + " rule demands the Data Consumer to " +
						action.toString().toLowerCase() + " the target asset.\n");
				translation = translation.concat("The identifier of this policy and the target asset are " + pid + " and " + target + ", respectively.\n\n");
			}*/

			//READ ACTION
			if(null != policy.getPermissionRuleReadAction())
			{
				ActionType action = policy.getPermissionRuleReadAction().getReadAction().getType();
				RuleType decision = policy.getPermissionRuleReadAction().getType();
				translation = translation.concat("In this Policy " + policyType + " example, the " + decision.getOdrlRuleType() + " rule " +
						decision.getMydataDecision() + "s the Data Consumer to " + action.toString().toLowerCase() + " the target asset.\n");
				translation = translation.concat("The identifier of this policy and the target asset are " + pid + " and " + target + ", respectively.\n\n");

				if(null != policy.getPermissionRuleReadAction().getEventConstraint())
				{
					String event = policy.getPermissionRuleReadAction().getEventConstraint().getRightOperand().getValue();
					translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific event; the Data Consumer is " +
							decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset when the " +
							getLastSplitElement(event) + " event has been triggered.\n\n");
				}
				if(null != policy.getPermissionRuleReadAction().getPurposeConstraint())
				{
					String purpose = policy.getPermissionRuleReadAction().getPurposeConstraint().getRightOperand().getValue();
					translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific purpose; the Data Consumer is " +
							decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset for " +
							getLastSplitElement(purpose) + " purpose.\n\n");
				}
				if(null != policy.getPermissionRuleReadAction().getSystemConstraint())
				{
					String system = policy.getPermissionRuleReadAction().getSystemConstraint().getRightOperand().getValue();
					translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific system; the Data Consumer is " +
							decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset when the system which will process the data is " +
							getLastSplitElement(system) +".\n\n");
				}
				if(null != policy.getPermissionRuleReadAction().getAbsoluteSpatialPositionConstraint())
				{
					String location = policy.getPermissionRuleReadAction().getAbsoluteSpatialPositionConstraint().getRightOperand().getValue();
					translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific location; the Data Consumer is " +
							decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset when it's connector is located in " +
							getLastElement(location) +".\n\n");
				}
				if(null != policy.getPermissionRuleReadAction().getPaymentConstraint())
				{
					String value = policy.getPermissionRuleReadAction().getPaymentConstraint().getRightOperand().getValue();
					String contract = policy.getPermissionRuleReadAction().getPaymentConstraint().getContract();
					String unit = policy.getPermissionRuleReadAction().getPaymentConstraint().getUnit();
					translation = translation.concat("In this policy, the " + provider + " party announces the Data " + getLastElement(contract) +
							" under specific condition; the Data Consumer is only allowed to " + ActionType.USE.toString().toLowerCase() +
							" the data after paying " + value + " " +  getLastElement(unit) +".\n\n");
				}
				if(null != policy.getPermissionRuleReadAction().getTimeIntervalConstraint() && null != policy.getPermissionRuleReadAction().getTimeIntervalConstraint().getRightOperand() )
				{
					//TODO
				}
				if(null != policy.getPermissionRuleReadAction().getCountConstraint())
				{
					String count = policy.getPermissionRuleReadAction().getCountConstraint().getRightOperand().getValue();
					translation = translation.concat("This policy specifies that the Data Consumer is " + decision.getMydataDecision() + "ed to " +
							action.toString().toLowerCase() + " the data asset (not more than) " +  count + " times.\n\n");

				}
			}
			if(null != policy.getProhibitionRuleReadAction())
			{
				ActionType action = policy.getProhibitionRuleReadAction().getReadAction().getType();
				RuleType decision = policy.getProhibitionRuleReadAction().getType();
				translation = translation.concat("In this Policy " + policyType + " example, the " + decision.getOdrlRuleType() + " rule " +
						decision.getMydataDecision() + "s the Data Consumer to " + action.toString().toLowerCase() + " the target asset.\n");
				translation = translation.concat("The identifier of this policy and the target asset are " + pid + " and " + target + ", respectively.\n\n");

				if(null != policy.getProhibitionRuleReadAction().getEventConstraint())
				{
					String event = policy.getProhibitionRuleReadAction().getEventConstraint().getRightOperand().getValue();
					translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific event; the Data Consumer is " +
							decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset when the " +
							getLastSplitElement(event) + " event has been triggered.\n\n");
				}
				if(null != policy.getProhibitionRuleReadAction().getPurposeConstraint())
				{
					String purpose = policy.getProhibitionRuleReadAction().getPurposeConstraint().getRightOperand().getValue();
					translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific purpose; the Data Consumer is " +
							decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset for " +
							getLastSplitElement(purpose) + " purpose.\n\n");
				}
				if(null != policy.getProhibitionRuleReadAction().getSystemConstraint())
				{
					String system = policy.getProhibitionRuleReadAction().getSystemConstraint().getRightOperand().getValue();
					translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific system; the Data Consumer is " +
							decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset when the system which will process the data is " +
							getLastSplitElement(system) +".\n\n");
				}
				if(null != policy.getProhibitionRuleReadAction().getAbsoluteSpatialPositionConstraint())
				{
					String location = policy.getProhibitionRuleReadAction().getAbsoluteSpatialPositionConstraint().getRightOperand().getValue();
					translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific location; the Data Consumer is " +
							decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset when it's connector is located in " +
							getLastElement(location) +".\n\n");
				}
				if(null != policy.getProhibitionRuleReadAction().getTimeIntervalConstraint())
				{
					//TODO
				}
			}

			//ANONYMIZE ACTION
			/*if(null != policy.getPermissionRuleAnonymizeAction())
			{
				ActionType action = policy.getPermissionRuleAnonymizeAction().getAnonymizeAction().getType();
				RuleType decision = policy.getPermissionRuleAnonymizeAction().getType();
				translation = translation.concat("In this Policy " + policyType + " example, the " + decision.getOdrlRuleType() + " rule " +
					decision.getMydataDecision() + "s the Data Consumer to " + action.toString().toLowerCase() + " the target asset.\n");
				translation = translation.concat("The identifier of this policy and the target asset are " + pid + " and " + target + ", respectively.\n\n");

			}
			if(null != policy.getProhibitionRuleAnonymizeAction())
			{
				ActionType action = policy.getProhibitionRuleAnonymizeAction().getAnonymizeAction().getType();
				RuleType decision = policy.getProhibitionRuleAnonymizeAction().getType();
				translation = translation.concat("In this Policy " + policyType + " example, the " + decision.getOdrlRuleType() + " rule " +
						decision.getMydataDecision() + "s the Data Consumer to " + action.toString().toLowerCase() + " the target asset.\n");
				translation = translation.concat("The identifier of this policy and the target asset are " + pid + " and " + target + ", respectively.\n\n");
			}*/
			if(null != policy.getObligationRuleAnonymizeAction())
			{
				ActionType action = policy.getObligationRuleAnonymizeAction().getAnonymizeAction().getType();
				RuleType decision = policy.getObligationRuleAnonymizeAction().getType();
				translation = translation.concat("In this Policy " + policyType + " example, the " + decision.getOdrlRuleType() + " rule demands the Data Consumer to " +
						action.toString().toLowerCase() + " the target asset.\n");
				translation = translation.concat("The identifier of this policy and the target asset are " + pid + " and " + target + ", respectively.\n\n");
			}

			//PRINT ACTION
			if(null != policy.getPermissionRulePrintAction())
			{
				ActionType action = policy.getPermissionRulePrintAction().getPrintAction().getType();
				RuleType decision = policy.getPermissionRulePrintAction().getType();
				translation = translation.concat("In this Policy " + policyType + " example, the " + decision.getOdrlRuleType() + " rule " +
						decision.getMydataDecision() + "s the Data Consumer to " + action.toString().toLowerCase() + " the target asset.\n");
				translation = translation.concat("The identifier of this policy and the target asset are " + pid + " and " + target + ", respectively.\n\n");
			}
			if(null != policy.getProhibitionRulePrintAction())
			{
				ActionType action = policy.getProhibitionRulePrintAction().getPrintAction().getType();
				RuleType decision = policy.getProhibitionRulePrintAction().getType();
				translation = translation.concat("In this Policy " + policyType + " example, the " + decision.getOdrlRuleType() + " rule " +
						decision.getMydataDecision() + "s the Data Consumer to " + action.toString().toLowerCase() + " the target asset.\n");
				translation = translation.concat("The identifier of this policy and the target asset are " + pid + " and " + target + ", respectively.\n\n");
			}

			//DISTRIBUTE ACTION
			if(null != policy.getPermissionRuleDistributeAction())
			{
				ActionType action = policy.getPermissionRuleDistributeAction().getDistributeAction().getType();
				RuleType decision = policy.getPermissionRuleDistributeAction().getType();
				translation = translation.concat("In this Policy " + policyType + " example, the " + decision.getOdrlRuleType() + " rule " +
						decision.getMydataDecision() + "s the Data Consumer to " + action.toString().toLowerCase() + " the target asset.\n");
				translation = translation.concat("The identifier of this policy and the target asset are " + pid + " and " + target + ", respectively.\n\n");
				if(null != policy.getPermissionRuleDistributeAction().getEncodingConstraint())
				{
					translation = translation.concat("The data asset must be encoded (compressed or encrypted) before it is distributed.\n\n");
				}if(null != policy.getPermissionRuleDistributeAction().getNextPolicyDutyAction())
				{
					translation = translation.concat("The next policy action expresses the allowable usages by third-parties; it is demanded that the usage of the data asset by the third party has to be limited to the conditions that are specified in the target policy.\n\n");

				}
			}
			if(null != policy.getProhibitionRuleDistributeAction())
			{
				ActionType action = policy.getProhibitionRuleDistributeAction().getDistributeAction().getType();
				RuleType decision = policy.getProhibitionRuleDistributeAction().getType();
				translation = translation.concat("In this Policy " + policyType + " example, the " + decision.getOdrlRuleType() + " rule " +
						decision.getMydataDecision() + "s the Data Consumer to " + action.toString().toLowerCase() + " the target asset.\n");
				translation = translation.concat("The identifier of this policy and the target asset are " + pid + " and " + target + ", respectively.\n\n");
			}

			//DELETE ACTION
			if(null != policy.getObligationRuleDeleteAction())
			{
				ActionType action = policy.getObligationRuleDeleteAction().getDeleteAction().getType();
				RuleType decision = policy.getObligationRuleDeleteAction().getType();
				translation = translation.concat("In this Policy " + policyType + " example, the " + decision.getOdrlRuleType() + " rule demands the Data Consumer to " +
						action.toString().toLowerCase() + " the target asset.\n");
				translation = translation.concat("The identifier of this policy and the target asset are " + pid + " and " + target + ", respectively.\n\n");
				translation = translation.concat("The " + provider + " party demands that the Data Consumer " +
						action.toString().toLowerCase() + "s the data asset at a specified date and time.\n");
				if(null != policy.getObligationRuleDeleteAction().getDeleteAction().getDelayPeriodRefinement())
				{
					String delayPeriod = policy.getObligationRuleDeleteAction().getDeleteAction().getDelayPeriodRefinement().getRightOperand().getValue();
					Duration d = BuildMydataPolicyUtils.getDurationFromDelayPeriodValue(delayPeriod);
					translation = translation.concat("The Data Consumer has to wait " + d.getValue() + " " +
							d.getTimeUnit().toString().toLowerCase() + ", before exercising the duty (the Data Provider's demand).\n");
				}if(null != policy.getObligationRuleDeleteAction().getDeleteAction().getDateTimeRefinement())
				{
					String dateTimeRefinement = policy.getObligationRuleDeleteAction().getDeleteAction().getDateTimeRefinement().getRightOperand().getValue();
					translation = translation.concat("The Data Consumer has to exercise the action which is demanded by the Data Provider at " + dateTimeRefinement + ".\n");
				}
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
