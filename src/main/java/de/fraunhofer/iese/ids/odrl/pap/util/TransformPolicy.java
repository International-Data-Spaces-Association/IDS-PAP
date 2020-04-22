package de.fraunhofer.iese.ids.odrl.pap.util;

import de.fraunhofer.iese.ids.odrl.pap.model.Policy.IPolicy;
import de.fraunhofer.iese.ids.odrl.pattern.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class TransformPolicy {
	
	public static String createTechnologyDependentPolicy(Map map, Boolean providerSide){

		if(null == providerSide)
		{
			providerSide = true;
		}
		IPolicy policy = Utils.getPolicy(map, providerSide);
		ITranslator translator = new MydataTranslator();
		try
		{
			// USE ACTION
			if(null != policy.getPermissionRuleUseAction())
			{
				if(null != policy.getPermissionRuleUseAction().getEventConstraint())
				{
					//return translator.translatePolicyWithPermissionRuleUseActionEventConstraint(policy);
					return translator.translateComplexPolicyWithPermissionRuleUseAction(policy);
				}
				else if(null != policy.getPermissionRuleUseAction().getPurposeConstraint())
				{
					//return translator.translatePolicyWithPermissionRuleUseActionPurposeConstraint(policy);
					return translator.translateComplexPolicyWithPermissionRuleUseAction(policy);
				}
				else if(null != policy.getPermissionRuleUseAction().getSystemConstraint())
				{
					return translator.translateComplexPolicyWithPermissionRuleUseAction(policy);
				}
				else if(null != policy.getPermissionRuleUseAction().getAbsoluteSpatialPositionConstraint())
				{
					return translator.translateComplexPolicyWithPermissionRuleUseAction(policy);
				}
				else if(null != policy.getPermissionRuleUseAction().getPaymentConstraint())
				{
					return translator.translateComplexPolicyWithPermissionRuleUseAction(policy);
				}
				else if(null != policy.getPermissionRuleUseAction().getTimeIntervalConstraint() && null != policy.getPermissionRuleUseAction().getTimeIntervalConstraint().getRightOperand() )
				{
					return translator.translatePolicyWithPermissionRuleUseActionTimeIntervalConstraint(policy);
				}
				else if(null != policy.getPermissionRuleUseAction().getCountConstraint())
				{
					return translator.translatePolicyWithPermissionRuleUseActionAndCountConstraint(policy);
				}else if(null != policy.getPermissionRuleUseAction().getDeleteDutyAction() && null != policy.getPermissionRuleUseAction().getEventDutyConstraint())
				{
					return translator.translatePolicyWithPermissionRuleUseActionDeleteDutyActionAndEventDutyConstraint(policy);
				}else if(null != policy.getPermissionRuleUseAction().getAnonymizeDutyAction() && null != policy.getPermissionRuleUseAction().getEventDutyConstraint())
				{
					return translator.translatePolicyWithPermissionRuleUseActionAnonymizeDutyActionAndEventDutyConstraint(policy);
				}else if(null != policy.getPermissionRuleUseAction().getLogDutyAction())
				{
					return translator.translatePolicyWithPermissionRuleUseActionAndLogDutyAction(policy);
				}else if(null != policy.getPermissionRuleUseAction().getInformDutyAction())
				{
					return translator.translatePolicyWithPermissionRuleUseActionAndInformDutyAction(policy);
				}
				else{
					return translator.translateComplexPolicyWithPermissionRuleUseAction(policy);
				}
			}
			if(null != policy.getProhibitionRuleUseAction())
			{
				if(null != policy.getProhibitionRuleUseAction().getEventConstraint())
				{
					return translator.translatePolicyWithProhibitionRuleUseActionEventConstraint(policy);
				}
				else if(null != policy.getProhibitionRuleUseAction().getPurposeConstraint())
				{
					return translator.translatePolicyWithProhibitionRuleUseActionPurposeConstraint(policy);
				}
				else if(null != policy.getProhibitionRuleUseAction().getSystemConstraint())
				{
					return translator.translatePolicyWithProhibitionRuleUseActionSystemConstraint(policy);
				}
				else if(null != policy.getProhibitionRuleUseAction().getAbsoluteSpatialPositionConstraint())
				{
					return translator.translatePolicyWithProhibitionRuleUseActionAbsoluteSpatialPositionConstraint(policy);
				}
				else if(null != policy.getProhibitionRuleUseAction().getTimeIntervalConstraint())
				{
					return translator.translatePolicyWithProhibitionRuleUseActionTimeIntervalConstraint(policy);
				}else{
					return translator.translatePolicyWithProhibitionRuleUseActionNoConstraint(policy);
				}
			}

			//READ ACTION
			if(null != policy.getPermissionRuleReadAction())
			{
				if(null != policy.getPermissionRuleReadAction().getEventConstraint())
				{
					return translator.translateComplexPolicyWithPermissionRuleReadAction(policy);
				}
				else if(null != policy.getPermissionRuleReadAction().getPurposeConstraint())
				{
					return translator.translateComplexPolicyWithPermissionRuleReadAction(policy);
				}
				else if(null != policy.getPermissionRuleReadAction().getSystemConstraint())
				{
					return translator.translateComplexPolicyWithPermissionRuleReadAction(policy);
				}
				else if(null != policy.getPermissionRuleReadAction().getAbsoluteSpatialPositionConstraint())
				{
					return translator.translateComplexPolicyWithPermissionRuleReadAction(policy);
				}
				else if(null != policy.getPermissionRuleReadAction().getPaymentConstraint())
				{
					return translator.translateComplexPolicyWithPermissionRuleReadAction(policy);
				}
				else if(null != policy.getPermissionRuleReadAction().getTimeIntervalConstraint() && null != policy.getPermissionRuleReadAction().getTimeIntervalConstraint().getRightOperand() )
				{
					return translator.translatePolicyWithPermissionRuleReadActionTimeIntervalConstraint(policy);
				}
				else if(null != policy.getPermissionRuleReadAction().getCountConstraint())
				{
					return translator.translatePolicyWithPermissionRuleReadActionAndCountConstraint(policy);
				}else{
					return translator.translatePolicyWithPermissionRuleReadActionNoConstraint(policy);
				}
			}
			if(null != policy.getProhibitionRuleReadAction())
			{
				if(null != policy.getProhibitionRuleReadAction().getEventConstraint())
				{
					return translator.translatePolicyWithProhibitionRuleReadActionEventConstraint(policy);
				}
				else if(null != policy.getProhibitionRuleReadAction().getPurposeConstraint())
				{
					return translator.translatePolicyWithProhibitionRuleReadActionPurposeConstraint(policy);
				}
				else if(null != policy.getProhibitionRuleReadAction().getSystemConstraint())
				{
					return translator.translatePolicyWithProhibitionRuleReadActionSystemConstraint(policy);
				}
				else if(null != policy.getProhibitionRuleReadAction().getAbsoluteSpatialPositionConstraint())
				{
					return translator.translatePolicyWithProhibitionRuleReadActionAbsoluteSpatialPositionConstraint(policy);
				}
				else if(null != policy.getProhibitionRuleReadAction().getTimeIntervalConstraint())
				{
					return translator.translatePolicyWithProhibitionRuleReadActionTimeIntervalConstraint(policy);
				}else{
					return translator.translatePolicyWithProhibitionRuleReadActionNoConstraint(policy);
				}
			}

			//ANONYMIZE ACTION
			if(null != policy.getObligationRuleAnonymizeAction())
			{
				return translator.translatePolicyWithObligationRuleAnonymizeActionNoConstraint(policy);
			}

			//PRINT ACTION
			if(null != policy.getPermissionRulePrintAction())
			{
				translator.translateComplexPolicyWithPermissionRulePrintAction(policy);
			}
			if(null != policy.getProhibitionRulePrintAction())
			{
				return translator.translatePolicyWithProhibitionRulePrintActionNoConstraint(policy);
			}

			//DISTRIBUTE ACTION
			if(null != policy.getPermissionRuleDistributeAction())
			{
				if(null != policy.getPermissionRuleDistributeAction().getEncodingConstraint())
				{
					return translator.translatePolicyWithPermissionRuleDistributeActionAndEncodingConstraint(policy);
				}else if(null != policy.getPermissionRuleDistributeAction().getNextPolicyDutyAction())
				{
					return translator.translatePolicyWithPermissionRuleDistributeActionAndNextPolicyDutyAction(policy);
				}else{
					return translator.translatePolicyWithPermissionRuleDistributeActionNoConstraint(policy);
				}
			}
			if(null != policy.getProhibitionRuleDistributeAction())
			{
				return translator.translatePolicyWithProhibitionRuleDistributeActionNoConstraint(policy);
			}

			//DELETE ACTION
			if(null != policy.getObligationRuleDeleteAction())
			{
				if(null != policy.getObligationRuleDeleteAction().getDeleteAction().getDelayPeriodRefinement())
				{
					//return policy.getObligationRuleDeleteAction().getDeleteAction().translatePolicyWithDelayPeriodRefinement(policy);
					return translator.translatePolicyWithObligationRuleDeleteActionAndDelayPeriodRefinement(policy);
				}else if(null != policy.getObligationRuleDeleteAction().getDeleteAction().getDateTimeRefinement())
				{
					return translator.translatePolicyWithObligationRuleDeleteActionAndDateTimeRefinement(policy);				}else{
					return translator.translatePolicyWithObligationRuleDeleteActionNoConstraint(policy);
				}
			}

			return "";
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

	static Object[] addElement(Object[] a, Object e) {
		a  = Arrays.copyOf(a, a.length + 1);
		a[a.length - 1] = e;
		return a;
	}

	private static String createCron(String y, String m, String d, String th, String tm, String ts)
	{
		String cron = ts + " " + tm + " " + th + " " + d + " " + m + " ? " + y ;
		return cron;
	}
}
