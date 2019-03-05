package de.fraunhofer.iese.ids.odrl.pattern;

import java.net.MalformedURLException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;


import de.fraunhofer.iese.ids.odrl.pap.model.*;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.ConditionType;

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
	public static CategorizedPolicy getCategorizedPolicy(Map map) {

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

			CategorizedPolicy categorizedPolicy = recognizePattern(ruleMap);
			if(null != categorizedPolicy) {
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
		return isNotNull(map.get(attribute))? map.get(attribute).toString():"";
	}

	public static Map getRuleMap(Map map, RuleType ruleType) {
		return (Map) ((ArrayList) map.get(ruleType.getOdrlRuleType())).get(0);
	}

	public static Action getAction(Map map) {
		if(map.get("action") instanceof ArrayList)
		{
			Map actionBlock = (Map) ((ArrayList) map.get("action")).get(0);
			Map valueBlock = (Map) actionBlock.get("rdf:value");
			return Action.valueOf(valueBlock.get("@id").toString().toUpperCase());
		}else{
			return Action.valueOf(map.get("action").toString().toUpperCase());
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
	
	public static CategorizedPolicy recognizePattern(Map ruleMap) {
		if(isProvideAccess(ruleMap)) {
			ProvideAccessPolicy policy = new ProvideAccessPolicy();
			policy.setProviderSide(true);
			return policy;
		}
		if(isSpecificPurpose(ruleMap)) {
			SpecificPurposePolicy specificPurposePolicy = new SpecificPurposePolicy();

			Map constraintMap = getSingleConditionMap(ruleMap, ConditionType.CONSTRAINT);

			String rightOperandValue = getRightOperandValue(constraintMap);
			specificPurposePolicy.setPurpose(rightOperandValue);
			specificPurposePolicy.setProviderSide(true);
			return specificPurposePolicy;
		}
		if(isReadDataInterval(ruleMap)) {
			ReadDataIntervalPolicy readDataIntervalPolicy = new ReadDataIntervalPolicy();
			ArrayList<Map> conditions = getListConditionMap(ruleMap, ConditionType.CONSTRAINT);
			if(isNotNull(conditions))
			{
				for (Map m: conditions)
				{
					if(getLeftOperand(m).equals(LeftOperand.DATETIME) && getIntervalOperator(m).equals(IntervalCondition.GT))
					{
						readDataIntervalPolicy.setStartTime(getRightOperandValue(m));
					}else if(getLeftOperand(m).equals(LeftOperand.DATETIME) && getIntervalOperator(m).equals(IntervalCondition.LT))
					{
						readDataIntervalPolicy.setEndTime(getRightOperandValue(m));
					}
				}
			}
			readDataIntervalPolicy.setProviderSide(true);
			return  readDataIntervalPolicy;
		}
		if(isDeleteAfter(ruleMap))
		{
			DeleteAtferPolicy deleteAtferPolicy = new DeleteAtferPolicy();
			Map actionMap = getActionMap(ruleMap);

			if(isNotNull(actionMap))
			{
				Map refinementMap = getSingleConditionMap(actionMap, ConditionType.REFINEMENT);
				if (isNotNull(refinementMap))
				{
					if(getRightOperandType(refinementMap).equals("xsd:duration"))
					{
						String durationString = getRightOperandValue(refinementMap);
						Duration d = getDurationFromDelayPeriodValue(durationString);
						deleteAtferPolicy.setDuration(d);
					}

				}
			}
			deleteAtferPolicy.setProviderSide(false);
			return deleteAtferPolicy;
		}
		return null;
	}

	public static RuleType getRuleType(Map map) {
		return isNotNull(map.get(RuleType.PERMISSION.getOdrlRuleType()))? RuleType.PERMISSION :
				(isNotNull(map.get(RuleType.PROHIBITION.getOdrlRuleType()))? RuleType.PROHIBITION :
						(isNotNull(map.get(RuleType.OBLIGATION.getOdrlRuleType()))? RuleType.OBLIGATION : null ));
	}


	public static boolean isProvideAccess(Map permissionMap) {
		return (isAction(permissionMap, Action.READ) && isNull(getSingleConditionMap(permissionMap, ConditionType.CONSTRAINT))
				&& isNull(getListConditionMap(permissionMap, ConditionType.CONSTRAINT)));
	}

	public static boolean isSpecificPurpose(Map ruleMap) {

		return (isAction(ruleMap, Action.READ)&& isNotNull(getSingleConditionMap(ruleMap, ConditionType.CONSTRAINT))
				&& getLeftOperand(getSingleConditionMap(ruleMap, ConditionType.CONSTRAINT)).equals(LeftOperand.PURPOSE));
	}

	public static boolean isReadDataInterval(Map ruleMap) {
		ArrayList<Map> conditions = getListConditionMap(ruleMap, ConditionType.CONSTRAINT);
		boolean flag = true;
		if(isNotNull(conditions))
		{
			for (Map m: conditions)
			{
				if(!getLeftOperand(m).equals(LeftOperand.DATETIME))
				{
					flag = false;
					break;
				}
			}
		}

		return (isAction(ruleMap, Action.READ) && flag);
	}

	public static boolean isDeleteAfter(Map ruleMap) {
		if(ruleMap.get("action") instanceof ArrayList)
		{
			Map actionBlock = (Map) ((ArrayList) ruleMap.get("action")).get(0);
			Map conditionBlock = getSingleConditionMap(actionBlock, ConditionType.REFINEMENT);
			return (isAction(ruleMap, Action.DELETE)&& isNotNull(getLeftOperand(conditionBlock))
					&& getLeftOperand(conditionBlock).equals(LeftOperand.DELAY_PERIOD));
		}
		return false;
	}

	private static boolean isAction(Map permissionMap, Action action) {
		return getAction(permissionMap).equals(action);
	}

	public static LeftOperand getLeftOperand(Map conditionMap) {
		return isNotNull (conditionMap)? LeftOperand.valueOf(getValue(conditionMap, "leftOperand").toUpperCase()): null;
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

	public static boolean constraintAndListLeftOperandsAreDateTime(Map permissionMap) {
		Map constraintMap = getSingleConditionMap(permissionMap, ConditionType.CONSTRAINT);
		if (null != constraintMap && null != constraintMap.get("and") && constraintMap.get("and") instanceof Map) {
			Map andMap = (Map) constraintMap.get("and");
			if(null != andMap.get("@list") && andMap.get("@list") instanceof ArrayList) {
				ArrayList atListArrayList = ((ArrayList)andMap.get("@list"));
				if(atListArrayList.size() == 2 && atListArrayList.get(0) instanceof Map && atListArrayList.get(1) instanceof Map) {
					Map startMap = (Map) atListArrayList.get(0);
					Map endMap = (Map) atListArrayList.get(1);
					
					if(		null != startMap.get("leftOperand") 
						&&	null != endMap.get("leftOperand")
						&& "dateTime".equals(startMap.get("leftOperand").toString())
						&& "dateTime".equals(endMap.get("leftOperand").toString())
						&& 	null != startMap.get("operator")
						&&  null != endMap.get("operator")
						&&  "gt".equals(startMap.get("operator").toString())
						&&  "lt".equals(endMap.get("operator").toString())){
							return true;
						}
				}
			}
		}
		return false;
	}
	
	public static String getConstraintRightOperandValue(Map permissionMap) {
		Map constraintMap = getSingleConditionMap(permissionMap, ConditionType.CONSTRAINT);
		if(null != constraintMap && null != constraintMap.get("rightOperand")) {
			Object rightOperandObject = constraintMap.get("rightOperand");
			if(rightOperandObject instanceof Map) {
				Map rightOperandMap = (Map) rightOperandObject;
				if(null != rightOperandMap.get("@value")) {
					return rightOperandMap.get("@value").toString();
				}
			}
		}
		return null;
	}

	public static Map getSingleConditionMap(Map map, ConditionType conditionType) {
		//map can be permissionMap in case of constraint or ActionMap in case of refinement
		Object condition = map.get(conditionType.getOdrlConditionType());
		if(condition instanceof ArrayList) {
			if(!((ArrayList) condition).isEmpty()) {
				Object o = ((ArrayList) condition).get(0);
				if(o instanceof Map) {
					return (Map) o;
				}
			}
		}
		if(condition instanceof Map) {
			Map o = (Map) condition;
			if(null != o.get("and"))
			{
				return null;
			}
			return o;
		}
		return null;
	}

	public static ArrayList<Map> getListConditionMap(Map map, ConditionType conditionType) {
		//map can be permissionMap in case of constraint or ActionMap in case of refinement
		Map conditionMap = (Map)map.get(conditionType.getOdrlConditionType());
		if (null != conditionMap && null != conditionMap.get("and") && conditionMap.get("and") instanceof Map) {
			Map andMap = (Map) conditionMap.get("and");
			if(null != andMap.get("@list") && andMap.get("@list") instanceof ArrayList) {
				ArrayList atListArrayList = ((ArrayList)andMap.get("@list"));
				ArrayList<Map> conditionsMaps = new ArrayList<>();
				for(int i=0 ; i<atListArrayList.size(); i++)
				{
					conditionsMaps.add((Map) atListArrayList.get(i));
				}
				return conditionsMaps;
			}
		}
		return null;
	}

	public static Map getActionMap(Map ruleMap) {
		Object action = ruleMap.get("action");
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

	public static boolean isLogAccess(Map permissionMap) {
		//TODO
		return false;
	}
	
	public static boolean isNull(Object o) {
		return null == o;
	}
	
	public static boolean isNotNull(Object o) {
		return null != o;
	}
	
}

	
