package de.fraunhofer.iese.ids.odrl.pattern;

import java.net.MalformedURLException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;


import com.fasterxml.jackson.core.JsonParseException;
import com.github.jsonldjava.utils.JsonUtils;
import de.fraunhofer.iese.ids.odrl.pap.model.*;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.ConditionType;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.*;

@SuppressWarnings("rawtypes")
public class PatternUtil {

	/*
	 * private Constructor
	 */
	private PatternUtil() {

	}

	/**
	 * analyzes a map specific Pattern and returns the specialist class of the categorized policy with the given parameters
	 *
	 * @param map
	 * @return categorized policy
	 */
	public static CategorizedPolicy getCategorizedPolicy(Map map, boolean providerSide) {

		try
		{
			//Assumption: we are assuming that there is only one rule per policy
			PolicyType policyType = getPolicyType(map);

			//if there is a valid odrl policy
			if(isNotNull(policyType))
			{
				// get rule type
				RuleType ruleType = getRuleType(map);
				Map ruleMap = getRuleMap(map, ruleType);

				//get policy id
				String pid = getValue(map, "uid");
				URL pidUrl = getUrl(pid);

				// get target
				String target = getValue(ruleMap, "target");
				URL dataUrl = getUrl(target);

				//get action, assigner, assigner (if exists), decision
				Action action = getAction(ruleMap);
				String assigner = getValue(ruleMap, "assigner");
				String assignee = getValue(ruleMap, "assignee");
				RuleType decision = getRuleType(map);

				CategorizedPolicy categorizedPolicy = addDetails(ruleMap);
				if(null != categorizedPolicy) {
				    categorizedPolicy.setProviderSide(providerSide);
					categorizedPolicy.setRuleType(decision);
					categorizedPolicy.setPolicyType(policyType);
					categorizedPolicy.setAction(action);
					categorizedPolicy.setPolicyUrl(pidUrl);
					categorizedPolicy.setDataUrl(dataUrl);
					categorizedPolicy.setAssigner(assigner);
					categorizedPolicy.setAssignee(assignee);
				}

				return categorizedPolicy;
			}

			return null;
		}
		catch (IllegalArgumentException e){
			return null;
		}

	}

	// get Duration(2,TimeUnit.H) from "PT2H"
	public static Duration getDurationFromDelayPeriodValue(String value)
	{
		String valueWithoutP = value.substring(1);
		String lastChar = valueWithoutP.substring(valueWithoutP.length()-1);

		String d = "";
		if(lastChar.equals("H"))
		{
			valueWithoutP = valueWithoutP.substring(1);
		}
		// valueWithoutP is like : 2H
		int n = Integer.parseInt(valueWithoutP.substring(0,valueWithoutP.length()-1));
		TimeUnit t = TimeUnit.HOURS;
		switch(lastChar) {
			case "H":
				t = TimeUnit.HOURS;
				break;

			case "D":
				t = TimeUnit.DAYS;
				break;

			case "M":
				t = TimeUnit.MONTHS;
				break;

			case "Y":
				t = TimeUnit.YEARS;
				break;

		}

		return new Duration(n,t);

	}

	/**
	 *
	 * @param map
	 * @return PolicyType of the Map (parsed from json)
	 */
	public static PolicyType getPolicyType(Map map) {
		return PolicyType.valueOf(map.get("@type").toString());
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

	public static Action getAction(Map map) {
		if(map.get("action") instanceof ArrayList)
		{
			Map actionBlock = (Map) ((ArrayList) map.get("action")).get(0);
			Map valueBlock = (Map) actionBlock.get("rdf:value");
			if (isNotNull(valueBlock))
			{
				return Action.valueOf(removeIdsTag(valueBlock.get("@id").toString()));
			} else
			{
				return Action.valueOf(removeIdsTag(actionBlock.get("@id").toString()));
			}
		}else{
			return Action.valueOf(removeIdsTag(map.get("action").toString()));
		}
	}

	public static Action getAbstractAction(Map map) {
		if(map.get("action") instanceof ArrayList)
		{
			Map actionBlock = (Map) ((ArrayList) map.get("action")).get(0);
			Map valueBlock = (Map) actionBlock.get("rdf:type");
			return Action.valueOf(removeIdsTag(valueBlock.get("@id").toString()));
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

	public static CategorizedPolicy addDetails(Map ruleMap) {
        AbstractPolicy policy = new AbstractPolicy();

        Map ruleActionMap = getMap(ruleMap, "action");

        Map dutyMap = getMap(ruleMap, "duty");
		Map dutyActionMap = null;
        if (isNotNull(dutyMap)) {
            Action dutyMethod = getAction(dutyMap);
            policy.setDutyAction(dutyMethod);

			try {
				String informedPartyValue = getPartyFunctionValue(dutyMap, PartyFunction.INFORMEDPARTY);
				policy.setInformedParty(informedPartyValue);
			} catch (Exception e) {
				//Nothing important: the ODRL policy has no Party Function on the Duty block.
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

		for (Map conditionMap : conditionList) {
			LeftOperand leftOperand = getLeftOperand(conditionMap);
			String rightOperandValue = getRightOperandValue(conditionMap);
			switch (leftOperand) {
				case PURPOSE:
					policy.setPurpose(rightOperandValue);
					break;
				case SYSTEM:
					policy.setSystem(rightOperandValue);
					break;
				case EVENT:
					policy.setEvent(rightOperandValue);
					break;
				case COUNT:
					policy.setCount(rightOperandValue);
					break;
				case ENCODING:
					policy.setEncoding(rightOperandValue);
					break;
				case DATETIME:
					if (getIntervalOperator(conditionMap).equals(IntervalCondition.GT)) {
						policy.setStartTime(rightOperandValue);
					} else if (getIntervalOperator(conditionMap).equals(IntervalCondition.LT)) {
						policy.setEndTime(rightOperandValue);
					} else {
						policy.setDateTime(rightOperandValue);
					}
					break;
				case DELAYPERIOD:
					Duration d = getDurationFromDelayPeriodValue(rightOperandValue);
					policy.setDuration(d);
					break;
				case RECIPIENT:
					policy.setRecipient(rightOperandValue);
					break;
				case JSONPATH:
					policy.setJsonPath(rightOperandValue);
					break;
				case DIGIT:
					policy.setDigit(rightOperandValue);
					break;
				case PAYAMOUNT:
					Payment p = new Payment();
					p.setValue(Integer.valueOf(rightOperandValue));
					p.setContract(getValue(conditionMap, "ids:contract"));
					p.setUnit(getValue(conditionMap, "unit"));
					policy.setPayment(p);
					break;
				case ABSOLUTESPATIALPOSITION:
					policy.setLocation(rightOperandValue);
					break;
				case SYSTEMDEVICE:
					policy.setSystemDevice(rightOperandValue);
			}
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
			Action abstractAction = getAbstractAction(dutyMap);
			return (abstractAction.equals(Action.ANONYMIZE));
		}
		return false;
	}

	private static boolean isAction(Map permissionMap, Action action) {
		return getAction(permissionMap).equals(action);
	}

	public static LeftOperand getLeftOperand(Map conditionMap) {
		return isNotNull (conditionMap)? LeftOperand.valueOf(removeIdsTag(getValue(conditionMap, "leftOperand"))): null;
	}

	public static String getPartyFunctionValue (Map dutyMap, PartyFunction partyFunction) {
		return isNotNull (dutyMap)? dutyMap.get(partyFunction.getIdsPartyFunction()).toString(): "";
	}

	public static Operator getOperator(Map conditionMap) {
		return isNotNull (conditionMap)? Operator.valueOf(getValue(conditionMap, "operator").toUpperCase()): null;
	}

	public static IntervalCondition getIntervalOperator(Map conditionMap) {
		return isNotNull (conditionMap)? IntervalCondition.valueOf(getValue(conditionMap, "operator").toUpperCase()): null;
	}

	public static String getRightOperandValue(Map conditionMap) {
		Map rightOperandMap = (Map) conditionMap.get("rightOperand");
		return isNotNull (rightOperandMap)? getValue(rightOperandMap, "@value"): "";
	}

	public static String getRightOperandType(Map conditionMap) {
		Map rightOperandMap = (Map) conditionMap.get("rightOperand");
		return isNotNull (rightOperandMap)? getValue(rightOperandMap, "@type"): "";
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
		  String termWithoutTag = term.trim().replaceAll(" ", "_");
		return termWithoutTag.substring(4).toUpperCase();
	}
}


