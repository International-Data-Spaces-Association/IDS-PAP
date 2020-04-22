package de.fraunhofer.iese.ids.odrl.pattern;

import de.fraunhofer.iese.ids.odrl.pap.model.*;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.DateTime;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.ExecuteAction;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.Parameter;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.*;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.*;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.UseAction;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.IAction;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.IPolicy;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class Utils {

	/*
	 * private Constructor
	 */
	private Utils() {

	}

	/**
	 * analyzes a map specific Pattern and returns the specialist class of the categorized policy with the given parameters
	 *
	 * @param map
	 * @return categorized policy
	 */
	public static IPolicy getPolicy(Map map, boolean providerSide) {

		try
		{

			PolicyType policyType = getPolicyType(map);

			// get target
			String target = getValue(map, "target");
			URI targetURI = URI.create(target);

			Party provider = null;
			if(!StringUtils.isEmpty(getValue(map, "provider")))
			{
				provider = new Party( PartyType.PROVIDER, URI.create(getValue(map, "provider")));
			}

			Party consumer = null;
			if(!StringUtils.isEmpty(getValue(map, "consumer")))
			{
				consumer = new Party(PartyType.CONSUMER, URI.create(getValue(map, "consumer")));
			}

			//if there is a valid odrl policy
			if(isNotNull(policyType))
			{
				//get policy id
				String pid = getValue(map, "uid");
				URI pidUri = URI.create(pid);

				IPolicy policy = addDetails(map);
				if(null != policy) {
					policy.setProviderSide(providerSide);
					policy.setTarget(targetURI);
					policy.setPolicyId(pidUri);
					policy.setType(policyType);
					policy.setProvider(provider);
					policy.setConsumer(consumer);
				}

				return policy;
			}

			return null;
		}
		catch (IllegalArgumentException e){
			return null;
		}

	}

	/**
	 *
	 * @param map
	 * @return PolicyType of the Map (parsed from json)
	 */
	public static PolicyType getPolicyType(Map map) {
		return PolicyType.valueOf(removeIdsTag(map.get("@type").toString()).substring(8));
	}

	public static String getValue(Map map, String attribute) {

		if(isNotNull(map.get(attribute)))
		{
			if(map.get(attribute) instanceof ArrayList)
			{
				Map attributeBlock = (Map) ((ArrayList) map.get(attribute)).get(0);
				return attributeBlock.get("@id").toString();
			} else {
				return map.get(attribute).toString();
			}
		}
		return null;
	}

	public static Map getRuleMap(Map map, RuleType ruleType) {
		return (Map) ((ArrayList) map.get(ruleType.getOdrlRuleType())).get(0);
	}

	public static ActionType getAction(Map map) {
		if(map.get("action") instanceof ArrayList)
		{
			Map actionBlock = (Map) ((ArrayList) map.get("action")).get(0);
			Map valueBlock = (Map) actionBlock.get("rdf:value");
			if (isNotNull(valueBlock))
			{
				return ActionType.valueOf(removeIdsTag(valueBlock.get("@id").toString()));
			} else
			{
				return ActionType.valueOf(removeIdsTag(actionBlock.get("@id").toString()));
			}
		}else{
			return ActionType.valueOf(removeIdsTag(map.get("action").toString()));
		}
	}

	public static ActionType getAbstractAction(Map map) {
		if(map.get("action") instanceof ArrayList)
		{
			Map actionBlock = (Map) ((ArrayList) map.get("action")).get(0);
			Map valueBlock = (Map) actionBlock.get("rdf:type");
			return ActionType.valueOf(removeIdsTag(valueBlock.get("@id").toString()));
		}else{
			return null;
		}
	}

	public static URL getUrl(String urlString) {
		URL dataURL = null;
		try {
			dataURL = new URL(urlString);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataURL;
	}

	public static IPolicy addDetails(Map map) {
        OdrlPolicy policy = new OdrlPolicy();

		// get rule type
		RuleType ruleType = getRuleType(map);
		Map ruleMap = getRuleMap(map, ruleType);

		ActionType action = getAction(ruleMap);
        Map ruleActionMap = getMap(ruleMap, "action");

		Map dutyMap = getMap(ruleMap, "duty");
		Map dutyActionMap = null;
		ActionType dutyMethod = null;
		String informedPartyValue = "";
		String nextPolicyTarget = "";
		if (isNotNull(dutyMap)) {
			dutyMethod = getAction(dutyMap);
			try {
				informedPartyValue = getPartyFunctionValue(dutyMap, PartyFunction.INFORMEDPARTY);
			} catch (Exception e) {
				//Nothing important: the ODRL policy has no Party Function on the Duty block.
			}
			try {
				nextPolicyTarget = getValue(dutyMap, "target");
			} catch (Exception e) {
				//Nothing important: the ODRL policy has no Next Policy Target!
			}
			dutyActionMap = getMap(dutyMap, "action");
		}

		ArrayList<Map> conditionList = new ArrayList<>();
		if ((isNotNull(ruleMap) && isNotNull(getConditionArrayList(ruleMap, ConditionType.CONSTRAINT))))
		{
			conditionList = getConditionArrayList(ruleMap, ConditionType.CONSTRAINT);
		}
		if (isNotNull(ruleActionMap) && isNotNull(getConditionArrayList(ruleActionMap, ConditionType.REFINEMENT)))
		{
			conditionList.addAll(getConditionArrayList(ruleActionMap, ConditionType.REFINEMENT));
		}

		if (isNotNull(dutyActionMap) && isNotNull(getConditionArrayList(dutyActionMap, ConditionType.REFINEMENT)))
		{
			conditionList.addAll(getConditionArrayList(dutyActionMap, ConditionType.REFINEMENT));
		}
		if (isNotNull(dutyMap) && isNotNull(getConditionArrayList(dutyMap, ConditionType.CONSTRAINT)))
		{
			conditionList.addAll(getConditionArrayList(dutyMap, ConditionType.CONSTRAINT));
		}

        if(ruleType.equals(RuleType.PERMISSION) && action.equals(ActionType.USE))
		{
			UseAction useAction = new UseAction();
			PermissionRuleUseAction permissionRuleUseAction = new PermissionRuleUseAction();
			permissionRuleUseAction.setUseAction(useAction);

			if(null != dutyMethod)
			{
				switch (dutyMethod) {
					case DELETE:
						DeleteAction deleteDutyAction = new DeleteAction();
						permissionRuleUseAction.setDeleteDutyAction(deleteDutyAction);
						break;
					case ANONYMIZE:
						AnonymizeAction anonymizeDutyAction = new AnonymizeAction();
						permissionRuleUseAction.setAnonymizeDutyAction(anonymizeDutyAction);
						break;
					case LOG:
						LogAction logDutyAction = new LogAction();
						permissionRuleUseAction.setLogDutyAction(logDutyAction);
						break;
					case INFORM:
						InformAction informDutyAction = new InformAction();
						permissionRuleUseAction.setInformDutyAction(informDutyAction);
						break;
				}
			}

			if(informedPartyValue != "")
			{
				Party informedParty = new Party( PartyType.INFORMEDPARTY, URI.create(informedPartyValue));
				permissionRuleUseAction.setInformedParty(informedParty);
			}

			TimeIntervalCondition timeIntervalCondition = new TimeIntervalCondition(ConditionType.CONSTRAINT, null);
			for (Map conditionMap : conditionList) {
				LeftOperand leftOperand = getLeftOperand(conditionMap);
				RightOperandId rightOperandId = getRightOperandId(conditionMap);
				String rightOperandValue = getRightOperandValue(conditionMap);
				RightOperandType rightOperandType = getRightOperandType(conditionMap);
				RightOperand rightOperand = new RightOperand(rightOperandId, rightOperandValue, rightOperandType);

				switch (leftOperand) {
					case PURPOSE:
						PurposeCondition purposeConstraint = new PurposeCondition(ConditionType.CONSTRAINT, rightOperand, null);
						permissionRuleUseAction.setPurposeConstraint(purposeConstraint);
						break;
					case SYSTEM:
						SystemCondition systemConstraint = new SystemCondition(ConditionType.CONSTRAINT, rightOperand, null);
						permissionRuleUseAction.setSystemConstraint(systemConstraint);
						break;
					case EVENT:
						EventCondition eventConstraint = new EventCondition(ConditionType.CONSTRAINT, rightOperand, null);
						if(null != rightOperandId && rightOperandId.equals(RightOperandId.POLICYUSAGE))
						{
							permissionRuleUseAction.setEventDutyConstraint(eventConstraint);
						}else{
							permissionRuleUseAction.setEventConstraint(eventConstraint);
						}
						break;
					case COUNT:
						CountCondition countConstraint = new CountCondition(ConditionType.CONSTRAINT, rightOperand, null);
						permissionRuleUseAction.setCountConstraint(countConstraint);
						break;
					case ENCODING:
						EncodingCondition encodingConstraint = new EncodingCondition(ConditionType.CONSTRAINT, rightOperand, null);
						permissionRuleUseAction.setEncodingConstraint(encodingConstraint);
						break;
					case DATETIME:
						if (getIntervalOperator(conditionMap).equals(IntervalCondition.EQ))
						{
							DateTimeCondition datetimeConstraint = new DateTimeCondition(ConditionType.CONSTRAINT, rightOperand, null);
							permissionRuleUseAction.setDateTimeConstraint(datetimeConstraint);
							break;
						}else{
							if (getIntervalOperator(conditionMap).equals(IntervalCondition.GT)) {
								timeIntervalCondition.setOperator(Operator.GREATER);
								timeIntervalCondition.setRightOperand(rightOperand);
							} else if (getIntervalOperator(conditionMap).equals(IntervalCondition.LT)) {
								timeIntervalCondition.setSecondOperator(Operator.GREATER);
								timeIntervalCondition.setSecondRightOperand(rightOperand);
							}
							permissionRuleUseAction.setTimeIntervalConstraint(timeIntervalCondition);
						}
						break;
					case DELAYPERIOD:
						DelayPeriodCondition delayPeriodConstraint = new DelayPeriodCondition(ConditionType.CONSTRAINT, rightOperand,null, TimeUnit.HOURS);
						permissionRuleUseAction.setDelayPeriodConstraint(delayPeriodConstraint);
						break;
					case RECIPIENT:
						RecipientCondition recipientConstraint = new RecipientCondition(ConditionType.CONSTRAINT, rightOperand, null);
						permissionRuleUseAction.setRecipientConstraint(recipientConstraint);
						break;
					case JSONPATH:
						if(null != permissionRuleUseAction.getAnonymizeDutyAction())
						{
							JsonPathCondition jsonPathRefinement = new JsonPathCondition(ConditionType.REFINEMENT, rightOperand, null);
							AnonymizeAction anonymizeDutyAction = permissionRuleUseAction.getAnonymizeDutyAction();
							anonymizeDutyAction.setJsonPathRefinement(jsonPathRefinement);
							permissionRuleUseAction.setAnonymizeDutyAction(anonymizeDutyAction);
						}else{
							JsonPathCondition jsonPathConstraint = new JsonPathCondition(ConditionType.CONSTRAINT, rightOperand, null);
							permissionRuleUseAction.setJsonPathConstraint(jsonPathConstraint);
						}
						break;
					case DIGIT:
						//policy.setDigit(rightOperandValue);
						break;
					case PAYAMOUNT:
						PaymentCondition paymentConstraint = new PaymentCondition(ConditionType.CONSTRAINT, rightOperand, null);
						permissionRuleUseAction.setPaymentConstraint(paymentConstraint);
						break;
					case ABSOLUTESPATIALPOSITION:
						AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint = new AbsoluteSpatialPositionCondition(ConditionType.CONSTRAINT, rightOperand, null);
						permissionRuleUseAction.setAbsoluteSpatialPositionConstraint(absoluteSpatialPositionConstraint);
						break;
					case SYSTEMDEVICE:
						if(null != permissionRuleUseAction.getLogDutyAction())
						{
							SystemDeviceCondition systemDeviceRefinement = new SystemDeviceCondition(ConditionType.REFINEMENT, rightOperand, null);
							LogAction logDutyAction = permissionRuleUseAction.getLogDutyAction();
							logDutyAction.setSystemDeviceRefinement(systemDeviceRefinement);
							permissionRuleUseAction.setLogDutyAction(logDutyAction);
						}else{
							SystemDeviceCondition systemDeviceConstraint = new SystemDeviceCondition(ConditionType.CONSTRAINT, rightOperand, null);
							permissionRuleUseAction.setSystemDeviceConstraint(systemDeviceConstraint);
						}
						break;
				}
			}
			policy.setPermissionRuleUseAction(permissionRuleUseAction);
		}

		if(ruleType.equals(RuleType.PROHIBITION) && action.equals(ActionType.USE))
		{
			UseAction useAction = new UseAction();
			ProhibitionRuleUseAction prohibitionRuleUseAction = new ProhibitionRuleUseAction();
			prohibitionRuleUseAction.setUseAction(useAction);
			TimeIntervalCondition timeIntervalCondition = new TimeIntervalCondition(ConditionType.CONSTRAINT, null);
			for (Map conditionMap : conditionList) {
				LeftOperand leftOperand = getLeftOperand(conditionMap);
				RightOperandId rightOperandId = getRightOperandId(conditionMap);
				String rightOperandValue = getRightOperandValue(conditionMap);
				RightOperandType rightOperandType = getRightOperandType(conditionMap);
				RightOperand rightOperand = new RightOperand(rightOperandValue, rightOperandType);

				switch (leftOperand) {
					case PURPOSE:
						PurposeCondition purposeConstraint = new PurposeCondition(ConditionType.CONSTRAINT, rightOperand, null);
						prohibitionRuleUseAction.setPurposeConstraint(purposeConstraint);
						break;
					case SYSTEM:
						SystemCondition systemConstraint = new SystemCondition(ConditionType.CONSTRAINT, rightOperand, null);
						prohibitionRuleUseAction.setSystemConstraint(systemConstraint);
						break;
					case EVENT:
						EventCondition eventConstraint = new EventCondition(ConditionType.CONSTRAINT, rightOperand, null);
						prohibitionRuleUseAction.setEventConstraint(eventConstraint);
						break;
					case ENCODING:
						EncodingCondition encodingConstraint = new EncodingCondition(ConditionType.CONSTRAINT, rightOperand, null);
						prohibitionRuleUseAction.setEncodingConstraint(encodingConstraint);
						break;
					case DATETIME:
						if (getIntervalOperator(conditionMap).equals(IntervalCondition.EQ))
						{
							DateTimeCondition datetimeConstraint = new DateTimeCondition(ConditionType.CONSTRAINT, rightOperand, null);
							prohibitionRuleUseAction.setDateTimeConstraint(datetimeConstraint);
							break;
						}else{
							if (getIntervalOperator(conditionMap).equals(IntervalCondition.GT)) {
								timeIntervalCondition.setOperator(Operator.GREATER);
								timeIntervalCondition.setRightOperand(rightOperand);
							} else if (getIntervalOperator(conditionMap).equals(IntervalCondition.LT)) {
								timeIntervalCondition.setSecondOperator(Operator.GREATER);
								timeIntervalCondition.setSecondRightOperand(rightOperand);
							}
							prohibitionRuleUseAction.setTimeIntervalConstraint(timeIntervalCondition);
						}
						break;
					case DELAYPERIOD:
						DelayPeriodCondition delayPeriodConstraint = new DelayPeriodCondition(ConditionType.CONSTRAINT, rightOperand,null, TimeUnit.HOURS);
						prohibitionRuleUseAction.setDelayPeriodConstraint(delayPeriodConstraint);
						break;
					case RECIPIENT:
						RecipientCondition recipientConstraint = new RecipientCondition(ConditionType.CONSTRAINT, rightOperand, null);
						prohibitionRuleUseAction.setRecipientConstraint(recipientConstraint);
						break;
					case JSONPATH:
						JsonPathCondition jsonPathConstraint = new JsonPathCondition(ConditionType.CONSTRAINT, rightOperand, null);
						prohibitionRuleUseAction.setJsonPathConstraint(jsonPathConstraint);
						break;
					case DIGIT:
						//policy.setDigit(rightOperandValue);
						break;
					case ABSOLUTESPATIALPOSITION:
						AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint = new AbsoluteSpatialPositionCondition(ConditionType.CONSTRAINT, rightOperand, null);
						prohibitionRuleUseAction.setAbsoluteSpatialPositionConstraint(absoluteSpatialPositionConstraint);
						break;
					case SYSTEMDEVICE:
						SystemDeviceCondition systemDeviceConstraint = new SystemDeviceCondition(ConditionType.CONSTRAINT, rightOperand, null);
						prohibitionRuleUseAction.setSystemDeviceConstraint(systemDeviceConstraint);
						break;
				}
			}
			policy.setProhibitionRuleUseAction(prohibitionRuleUseAction);
		}

		if(ruleType.equals(RuleType.PROHIBITION) && action.equals(ActionType.PRINT))
		{
			PrintAction printAction = new PrintAction();
			ProhibitionRulePrintAction prohibitionRulePrintAction = new ProhibitionRulePrintAction();
			prohibitionRulePrintAction.setPrintAction(printAction);
			policy.setProhibitionRulePrintAction(prohibitionRulePrintAction);
		}

		if(ruleType.equals(RuleType.PERMISSION) && action.equals(ActionType.DISTRIBUTE))
		{
			DistributeAction distributeAction = new DistributeAction();
			PermissionRuleDistributeAction permissionRuleDistributeAction = new PermissionRuleDistributeAction();
			permissionRuleDistributeAction.setDistributeAction(distributeAction);

			if(null != dutyMethod)
			{
				switch (dutyMethod) {
					case DELETE:
						DeleteAction deleteDutyAction = new DeleteAction();
						permissionRuleDistributeAction.setDeleteDutyAction(deleteDutyAction);
						break;
					case ANONYMIZE:
						AnonymizeAction anonymizeDutyAction = new AnonymizeAction();
						permissionRuleDistributeAction.setAnonymizeDutyAction(anonymizeDutyAction);
						break;
					case LOG:
						LogAction logDutyAction = new LogAction();
						permissionRuleDistributeAction.setLogDutyAction(logDutyAction);
						break;
					case INFORM:
						InformAction informDutyAction = new InformAction();
						permissionRuleDistributeAction.setInformDutyAction(informDutyAction);
						break;
					case NEXTPOLICY:
						NextPolicyAction nextPolicyDutyAction = new NextPolicyAction();
						permissionRuleDistributeAction.setNextPolicyDutyAction(nextPolicyDutyAction);
						break;
				}
			}

			if(informedPartyValue != "")
			{
				Party informedParty = new Party( PartyType.INFORMEDPARTY, URI.create(informedPartyValue));
				permissionRuleDistributeAction.setInformedParty(informedParty);
			}

			if(nextPolicyTarget != "")
			{
				permissionRuleDistributeAction.setNextPolicyTarget(URI.create(nextPolicyTarget));
			}

			TimeIntervalCondition timeIntervalCondition = new TimeIntervalCondition(ConditionType.CONSTRAINT, null);
			for (Map conditionMap : conditionList) {
				LeftOperand leftOperand = getLeftOperand(conditionMap);
				RightOperandId rightOperandId = getRightOperandId(conditionMap);
				String rightOperandValue = getRightOperandValue(conditionMap);
				RightOperandType rightOperandType = getRightOperandType(conditionMap);
				RightOperand rightOperand = new RightOperand(rightOperandId, rightOperandValue, rightOperandType);

				switch (leftOperand) {
					case PURPOSE:
						PurposeCondition purposeConstraint = new PurposeCondition(ConditionType.CONSTRAINT, rightOperand, null);
						permissionRuleDistributeAction.setPurposeConstraint(purposeConstraint);
						break;
					case SYSTEM:
						SystemCondition systemConstraint = new SystemCondition(ConditionType.CONSTRAINT, rightOperand, null);
						permissionRuleDistributeAction.setSystemConstraint(systemConstraint);
						break;
					case EVENT:
						EventCondition eventConstraint = new EventCondition(ConditionType.CONSTRAINT, rightOperand, null);
						if(null != rightOperandId && rightOperandId.equals(RightOperandId.POLICYUSAGE))
						{
							permissionRuleDistributeAction.setEventDutyConstraint(eventConstraint);
						}else{
							permissionRuleDistributeAction.setEventConstraint(eventConstraint);
						}
						break;
					case COUNT:
						CountCondition countConstraint = new CountCondition(ConditionType.CONSTRAINT, rightOperand, null);
						permissionRuleDistributeAction.setCountConstraint(countConstraint);
						break;
					case ENCODING:
						EncodingCondition encodingConstraint = new EncodingCondition(ConditionType.CONSTRAINT, rightOperand, null);
						permissionRuleDistributeAction.setEncodingConstraint(encodingConstraint);
						break;
					case DATETIME:
						if (getIntervalOperator(conditionMap).equals(IntervalCondition.EQ))
						{
							DateTimeCondition datetimeConstraint = new DateTimeCondition(ConditionType.CONSTRAINT, rightOperand, null);
							permissionRuleDistributeAction.setDateTimeConstraint(datetimeConstraint);
							break;
						}else{
							if (getIntervalOperator(conditionMap).equals(IntervalCondition.GT)) {
								timeIntervalCondition.setOperator(Operator.GREATER);
								timeIntervalCondition.setRightOperand(rightOperand);
							} else if (getIntervalOperator(conditionMap).equals(IntervalCondition.LT)) {
								timeIntervalCondition.setSecondOperator(Operator.GREATER);
								timeIntervalCondition.setSecondRightOperand(rightOperand);
							}
							permissionRuleDistributeAction.setTimeIntervalConstraint(timeIntervalCondition);
						}
						break;
					case DELAYPERIOD:
						DelayPeriodCondition delayPeriodConstraint = new DelayPeriodCondition(ConditionType.CONSTRAINT, rightOperand,null, TimeUnit.HOURS);
						permissionRuleDistributeAction.setDelayPeriodConstraint(delayPeriodConstraint);
						break;
					case RECIPIENT:
						RecipientCondition recipientConstraint = new RecipientCondition(ConditionType.CONSTRAINT, rightOperand, null);
						permissionRuleDistributeAction.setRecipientConstraint(recipientConstraint);
						break;
					case JSONPATH:
						if(null != permissionRuleDistributeAction.getAnonymizeDutyAction())
						{
							JsonPathCondition jsonPathRefinement = new JsonPathCondition(ConditionType.REFINEMENT, rightOperand, null);
							AnonymizeAction anonymizeDutyAction = permissionRuleDistributeAction.getAnonymizeDutyAction();
							anonymizeDutyAction.setJsonPathRefinement(jsonPathRefinement);
							permissionRuleDistributeAction.setAnonymizeDutyAction(anonymizeDutyAction);
						}else{
							JsonPathCondition jsonPathConstraint = new JsonPathCondition(ConditionType.CONSTRAINT, rightOperand, null);
							permissionRuleDistributeAction.setJsonPathConstraint(jsonPathConstraint);
						}
						break;
					case DIGIT:
						//policy.setDigit(rightOperandValue);
						break;
					case PAYAMOUNT:
						PaymentCondition paymentConstraint = new PaymentCondition(ConditionType.CONSTRAINT, rightOperand, null);
						permissionRuleDistributeAction.setPaymentConstraint(paymentConstraint);
						break;
					case ABSOLUTESPATIALPOSITION:
						AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint = new AbsoluteSpatialPositionCondition(ConditionType.CONSTRAINT, rightOperand, null);
						permissionRuleDistributeAction.setAbsoluteSpatialPositionConstraint(absoluteSpatialPositionConstraint);
						break;
					case SYSTEMDEVICE:
						if(null != permissionRuleDistributeAction.getLogDutyAction())
						{
							SystemDeviceCondition systemDeviceRefinement = new SystemDeviceCondition(ConditionType.REFINEMENT, rightOperand, null);
							LogAction logDutyAction = permissionRuleDistributeAction.getLogDutyAction();
							logDutyAction.setSystemDeviceRefinement(systemDeviceRefinement);
							permissionRuleDistributeAction.setLogDutyAction(logDutyAction);
						}else{
							SystemDeviceCondition systemDeviceConstraint = new SystemDeviceCondition(ConditionType.CONSTRAINT, rightOperand, null);
							permissionRuleDistributeAction.setSystemDeviceConstraint(systemDeviceConstraint);
						}
						break;
				}
			}
			policy.setPermissionRuleDistributeAction(permissionRuleDistributeAction);
		}

		if(ruleType.equals(RuleType.OBLIGATION) && action.equals(ActionType.DELETE))
		{
			DeleteAction deleteAction = new DeleteAction();
			ObligationRuleDeleteAction obligationRuleDeleteAction = new ObligationRuleDeleteAction();


			for (Map conditionMap : conditionList) {
				LeftOperand leftOperand = getLeftOperand(conditionMap);
				String rightOperandValue = getRightOperandValue(conditionMap);
				RightOperandType rightOperandType = getRightOperandType(conditionMap);
				RightOperand rightOperand = new RightOperand(rightOperandValue, rightOperandType);

				switch (leftOperand) {
					case DELAYPERIOD:
						DelayPeriodCondition delayPeriodRefinement = new DelayPeriodCondition(ConditionType.REFINEMENT, rightOperand,null, TimeUnit.HOURS);
						deleteAction.setDelayPeriodRefinement(delayPeriodRefinement);
						break;
					case DATETIME:
						if (getIntervalOperator(conditionMap).equals(IntervalCondition.EQ))
						{
							DateTimeCondition datetimeRefinement = new DateTimeCondition(ConditionType.REFINEMENT, rightOperand, null);
							deleteAction.setDateTimeRefinement(datetimeRefinement);
							break;
						}
						break;
				}
			}
			obligationRuleDeleteAction.setDeleteAction(deleteAction);
			policy.setObligationRuleDeleteAction(obligationRuleDeleteAction);
		}

		if(ruleType.equals(RuleType.OBLIGATION) && action.equals(ActionType.NEXTPOLICY))
			//	|| (hasDuty && dutyAction.equals(ActionType.NEXTPOLICY)))
		{
			//TODO
		}

		if(ruleType.equals(RuleType.OBLIGATION) && action.equals(ActionType.ANONYMIZE))
			//	|| (hasDuty && dutyAction.equals(ActionType.ANONYMIZE)))
		{
			AnonymizeAction anonymizeAction = new AnonymizeAction();
			ObligationRuleAnonymizeAction obligationRuleAnonymizeAction = new ObligationRuleAnonymizeAction();
			obligationRuleAnonymizeAction.setAnonymizeAction(anonymizeAction);
			policy.setObligationRuleAnonymizeAction(obligationRuleAnonymizeAction);
		}

        return policy;
    }

    public static RuleType getRuleType(Map map) {
		return isNotNull(map.get(RuleType.PERMISSION.getOdrlRuleType()))? RuleType.PERMISSION :
				(isNotNull(map.get(RuleType.PROHIBITION.getOdrlRuleType()))? RuleType.PROHIBITION :
						(isNotNull(map.get(RuleType.OBLIGATION.getOdrlRuleType()))? RuleType.OBLIGATION : null ));
	}


	private static boolean isAnonymizeInTransit(Map ruleMap) {
		Map dutyMap = getMap(ruleMap, "duty");
		if(isNotNull(dutyMap))
		{
			ActionType abstractAction = getAbstractAction(dutyMap);
			return (abstractAction.equals(ActionType.ANONYMIZE));
		}
		return false;
	}

	private static boolean isAction(Map permissionMap, IAction IAction) {
		return getAction(permissionMap).equals(IAction);
	}

	public static LeftOperand getLeftOperand(Map conditionMap) {
		return isNotNull (conditionMap) && isNotNull(getValue(conditionMap, "leftOperand")) ? LeftOperand.valueOf(removeIdsTag(getValue(conditionMap, "leftOperand"))): null;
	}

	public static String getPartyFunctionValue (Map dutyMap, PartyFunction partyFunction) {
		return isNotNull (dutyMap)? dutyMap.get(partyFunction.getIdsPartyFunction()).toString(): "";
	}

	public static Operator getOperator(Map conditionMap) {
		return isNotNull (conditionMap)? Operator.valueOf(getValue(conditionMap, "operator").toUpperCase()): null;
	}

	public static IntervalCondition getIntervalOperator(Map conditionMap) {
		return isNotNull (conditionMap) && isNotNull(getValue(conditionMap, "operator")) ? IntervalCondition.valueOf(getValue(conditionMap, "operator").toUpperCase()): null;
	}

	public static String getRightOperandValue(Map conditionMap) {
		Map rightOperandMap = (Map) conditionMap.get("rightOperand");
		return isNotNull (rightOperandMap)? getValue(rightOperandMap, "@value"): "";
	}

	public static RightOperandType getRightOperandType(Map conditionMap) {
		Map rightOperandMap = (Map) conditionMap.get("rightOperand");

		return isNotNull (rightOperandMap) && isNotNull(getValue(rightOperandMap, "@type")) ? RightOperandType.valueOf(removeXsdTag(getValue(rightOperandMap, "@type"))): null;
	}

	public static RightOperandId getRightOperandId(Map conditionMap) {
		Map rightOperandMap = (Map) conditionMap.get("rightOperand");
		return isNotNull (rightOperandMap) && isNotNull(getValue(rightOperandMap, "@id")) ? RightOperandId.valueOf(removeXsdTag(getValue(rightOperandMap, "@id"))): null;
	}

    public static ArrayList<Map> getConditionArrayList(Map map, ConditionType conditionType) {
        Object condition = map.get(conditionType.getOdrlConditionType());
        if(condition instanceof ArrayList) {
            if(!((ArrayList) condition).isEmpty()) {
                ArrayList list = ((ArrayList)condition);
                ArrayList<Map> conditionsMaps = new ArrayList<>();
                for(int i=0 ; i<list.size(); i++)
                {
                    conditionsMaps.add((Map) list.get(i));
                }
                return conditionsMaps;
            }
        }
        return null;
    }

	public static Map getMap(Map ruleMap, String tag) {
		Object action = ruleMap.get(tag);
		if(action instanceof ArrayList) {
			if(!((ArrayList) action).isEmpty()) {
				Object o = ((ArrayList) action).get(0);
				if(o instanceof Map) {
					return (Map) o;
				}
			}
		}
		if(action instanceof Map) {
			return (Map) action;
		}
		return null;
	}

	public static boolean isNull(Object o) {
		return null == o;
	}

	public static boolean isNotNull(Object o) {
		return null != o;
	}

	private static String removeIdsTag(String term)
	{
		if (term.startsWith("http"))
		{
			String[] termSplit = term.split("/");
			return termSplit[5].equalsIgnoreCase("action")?termSplit[6].toUpperCase():termSplit[5].toUpperCase();
		}else{
			String termWithoutTag = term.trim().replaceAll(" ", "_");
			return termWithoutTag.substring(4).toUpperCase();
		}
	}

	private static String removeXsdTag(String term)
	{
		String termWithoutTag = term.trim().replaceAll(" ", "_");
		return termWithoutTag.substring(4).toUpperCase();
	}

}


