package de.fraunhofer.iese.ids.odrl.pattern;

import java.net.MalformedURLException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;


import de.fraunhofer.iese.ids.odrl.pap.model.CategorizedPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;
import de.fraunhofer.iese.ids.odrl.pap.model.ProvideAccessPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.ReadDataIntervalPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.SpecificPurposePolicy;

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
		
		PolicyType policyType = getPolicyType(map);
		
		Map permissionMap = getPermissionMap(map);

		String dataUrlString = permissionMap.get("target").toString();
		URL dataUrl = getUrl(dataUrlString);
		
		String assigner = permissionMap.get("assigner").toString();
		String assignee = permissionMap.get("assignee").toString();
		
		CategorizedPolicy categorizedPolicy = recognizePattern(permissionMap);
		if(null != categorizedPolicy) {
			categorizedPolicy.setPolicyType(policyType);
			categorizedPolicy.setDataUrl(dataUrl);
			categorizedPolicy.setAssigner(assigner);
			categorizedPolicy.setAssignee(assignee);
		}
		
		return categorizedPolicy;
	}
	
	/**
	 * 
	 * @param map
	 * @return PolicyType of the Map (parsed from json)
	 */
	public static PolicyType getPolicyType(Map map) {
		return PolicyType.valueOf(map.get("@type").toString());
	}
	
	public static Map getPermissionMap(Map map) {
		return (Map) ((ArrayList) map.get("permission")).get(0);
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
	
	public static CategorizedPolicy recognizePattern(Map permissionMap) {
		if(isProvideAccess(permissionMap)) {
			return new ProvideAccessPolicy();
		}
		if(isSpecificPurpose(permissionMap)) {
			SpecificPurposePolicy specificPurposePolicy = new SpecificPurposePolicy();
			String rightOperandValue = getConstraintRightOperandValue(permissionMap);
			specificPurposePolicy.setPurpose(rightOperandValue);
			return specificPurposePolicy;
		}
		if(isReadDataInterval(permissionMap)) {
			//TODO retrive and fill data
			return new ReadDataIntervalPolicy();
		}
		return null;
	}
	


	public static boolean isProvideAccess(Map permissionMap) {
		return (actionIsRead(permissionMap) && isNull(getConstraint(permissionMap))); 
	}
	
	public static boolean isSpecificPurpose(Map permissionMap) {
		return (actionIsRead(permissionMap) && constraintLeftOperandIsPurpose(permissionMap));
	}

	public static boolean isReadDataInterval(Map permissionMap) {
		return (actionIsRead(permissionMap) && constraintAndListLeftOperandsAreDateTime(permissionMap));
	}
	

	
	public static boolean constraintLeftOperandIsPurpose(Map permissionMap) {
		Map constraintMap = getConstraintMap(permissionMap);
		return (null != constraintMap
				&& null != constraintMap.get("leftOperand")
				&& "purpose".equals(constraintMap.get("leftOperand").toString()));
	}
	
	public static boolean constraintAndListLeftOperandsAreDateTime(Map permissionMap) {
		Map constraintMap = getConstraintMap(permissionMap);
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
		Map constraintMap = getConstraintMap(permissionMap);
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

	public static Map getConstraintMap(Map permissionMap) {
		Object constraint = getConstraint(permissionMap);
		if(constraint instanceof ArrayList) {
			if(!((ArrayList) constraint).isEmpty()) {
				Object o = ((ArrayList) constraint).get(0);
				if(o instanceof Map) {
					return (Map) o;
				}
			}
		}
		if(constraint instanceof Map) {
			return (Map) constraint;
		}
		return null;
	}
	public static boolean isDeleteAfter(Map permissionMap) {
		//TODO
		return false;
	}
	

	
	public static boolean isLogAccess(Map permissionMap) {
		//TODO
		return false;
	}
	
	public static boolean actionIsRead(Map permissionMap) {
		return "read".equals((permissionMap.get("action").toString()));
	}
	
	public static Object getConstraint(Map permissionMap) {
		return (permissionMap.get("constraint"));
	}
	
	public static boolean isNull(Object o) {
		return null == o;
	}
	
	public static boolean isNotNull(Object o) {
		return null != o;
	}
	
}

	
