package de.fraunhofer.iese.ids.odrl.pap.util;

import java.util.Map;

import de.fraunhofer.iese.ids.odrl.mydata.translator.model.Parameter;
import de.fraunhofer.iese.ids.odrl.mydata.translator.model.ParameterType;
import de.fraunhofer.iese.ids.odrl.mydata.translator.model.Timer;
import de.fraunhofer.iese.ids.odrl.mydata.translator.util.BuildMydataPolicyUtils;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Action;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Condition;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Duration;
import de.fraunhofer.iese.ids.odrl.policy.library.model.RightOperandEntity;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.ActionType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.Operator;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.RuleType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.interfaces.IPolicy;
import de.fraunhofer.iese.ids.odrl.policy.library.model.tooling.PatternUtil;


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
			policy = PatternUtil.getPolicy(map, providerSide);
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
			if(null != policy.getRules())
			{
				RuleType decision = policy.getRules().get(0).getType();
				if(policy.getRules().get(0).getType().equals(RuleType.PERMISSION)){
					ActionType action = policy.getRules().get(0).getAction().getType();
					translation = translation.concat("In this Policy " + policyType + " example, the " + decision.getOdrlRuleType() + " rule " +
							decision.getMydataDecision() + "s the Data Consumer to " + action.toString().toLowerCase() + " the target asset.\n");
					translation = translation.concat("The identifier of this policy and the target asset are " + pid + " and " + target + ", respectively.\n\n");

					if(null != policy.getRules().get(0).getConstraints()) {
						for (Condition constraint : policy.getRules().get(0).getConstraints()) {
							String rightOperandValue = constraint.getRightOperand().getValue();
							switch (constraint.getLeftOperand()) {
								case EVENT:
									translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific event; the Data Consumer is " +
											decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset when the " +
											getLastSplitElement(rightOperandValue) + " event has been triggered.\n\n");
									break;
								case PURPOSE:
									translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific purpose; the Data Consumer is " +
											decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset for " +
											getLastSplitElement(rightOperandValue) + " purpose.\n\n");
									break;
								case SYSTEM:
									translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific system; the Data Consumer is " +
											decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset when the system which will process the data is " +
											getLastSplitElement(rightOperandValue) + ".\n\n");
									break;
								case ABSOLUTESPATIALPOSITION:
									translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific location; the Data Consumer is " +
											decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data asset when it's connector is located in " +
											getLastElement(rightOperandValue) + ".\n\n");
									break;
								case PAY_AMOUNT:
									String contract = ((Condition) constraint).getContract();
									String unit = ((Condition) constraint).getUnit();
									translation = translation.concat("In this policy, the " + provider + " party announces the Data " + getLastElement(contract) +
											" under specific condition; the Data Consumer is only allowed to " + ActionType.USE.toString().toLowerCase() +
											" the data after paying " + rightOperandValue + " " + getLastElement(unit) + ".\n\n");
									break;
								case POLICY_EVALUATION_TIME:
									for(RightOperandEntity entity: constraint.getRightOperand().getEntities())
									{
										switch (entity.getEntityType()) {
											case END:
												String end = entity.getValue();
												translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific time interval; the Data Consumer is " +
														decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data from " + rightOperandValue + " to " + end + ".\n");
												break;
										}
									}
									break;
								case ELAPSED_TIME:
									Duration d = BuildMydataPolicyUtils.getDurationFromPeriodValue(rightOperandValue);
									translation = translation.concat("The Data Consumer can use the data for the duration of " + d.getValue() + " " +
											d.getTimeUnit().toString().toLowerCase() + "(from the data that the agreement policy was issued!).\n");
									break;
								case COUNT:
									translation = translation.concat("This policy specifies that the Data Consumer is " + decision.getMydataDecision() + "ed to " +
											action.toString().toLowerCase() + " the data asset (not more than) " + rightOperandValue + " times.\n\n");
									break;
								case ENCODING:
									translation = translation.concat("The data asset must be encoded (compressed or encrypted) before it is distributed.\n\n");
									break;
							}
						}
					}

					Action dutyAction = null;
					if(null != policy.getRules().get(0).getPreobligations() && policy.getRules().get(0).getPreobligations().size() > 0)
					{
						dutyAction = (Action) policy.getRules().get(0).getPreobligations().get(0).getAction();
					}

					if(null != policy.getRules().get(0).getPostobligations() && policy.getRules().get(0).getPostobligations().size() > 0)
					{
						dutyAction = (Action) policy.getRules().get(0).getPostobligations().get(0).getAction();
					}

					if(null != dutyAction) {
						ActionType actionType = dutyAction.getType();
						switch (actionType) {
							case DELETE:
								translation = translation.concat("The " + provider + " party demands that the Data Consumer " +
										actionType.toString().toLowerCase() + "s the data asset right after it is used.\n\n");
								break;
							case ANONYMIZE:
								translation = translation.concat("The Data Consumer has to exercise the action which is demanded by the Data Provider before the usage of the data asset. Here, the policy specifies that the Data Consumer must "
										+ actionType.toString().toLowerCase() + " the data.\n");
								if (null != dutyAction.getRefinements()) {
									for (Condition refinement : dutyAction.getRefinements()) {
										switch (refinement.getLeftOperand()) {
											case MODIFICATIONMETHOD:
												String jsonPath = refinement.getJsonPath();
												translation = translation.concat("The " + jsonPath + " field of the data asset (given as jsonPathQuery) is requested to be modified.\n");
												break;
										}
									}
								}
								break;
							case LOG:
								translation = translation.concat("The " + provider + " party demands that the Data Consumer logs the information about the data usage. \n");
								if (null != dutyAction.getRefinements()) {
									for (Condition refinement : dutyAction.getRefinements()) {
										switch (refinement.getLeftOperand()) {
											case SYSTEMDEVICE:
												String systemDevice = refinement.getRightOperand().getValue();
												translation = translation.concat("The location to store the logs is " + getLastSplitElement(systemDevice) + " system.\n\n");
												break;
										}
									}
								}
								break;
							case INFORM:
								translation = translation.concat("The " + provider + " party demands that the Data Consumer informs a party about the usage of data.\n\n");

								if (null != dutyAction.getRefinements()) {
									for (Condition refinement : dutyAction.getRefinements()) {
										switch (refinement.getLeftOperand()) {
											case INFORMEDPARTY:
												String informedPartyValue = refinement.getRightOperand().getValue();
												translation = translation.concat("This policy specifies that the informed party is " +
														getLastSplitElement(informedPartyValue) + " .\n\n");
												break;
										}
									}
								}
								break;
							case NEXT_POLICY:
								translation = translation.concat("The next policy action expresses the allowable usages by third-parties; " +
										"it is demanded that the usage of the data asset by the third party has to be limited to the conditions that are specified in the target policy.\n\n");

								if (null != dutyAction.getRefinements()) {
									for (Condition refinement : dutyAction.getRefinements()) {
										switch (refinement.getLeftOperand()) {
											case TARGETPOLICY:
												String thirdPartyValue = refinement.getRightOperand().getValue();
												translation = translation.concat("This policy specifies that the policy to be sent to the third party is " +
														getLastSplitElement(thirdPartyValue) + " .\n\n");
												break;
										}
									}
								}
								break;
							}
						}
				}else if(policy.getRules().get(0).getType().equals(RuleType.OBLIGATION)){
					ActionType action = policy.getRules().get(0).getAction().getType();
					switch (action){
						case ANONYMIZE:
							translation = translation.concat("In this Policy " + policyType + " example, the " + decision.getOdrlRuleType() + " rule demands the Data Consumer to " +
									action.toString().toLowerCase() + " the target asset.\n");
							translation = translation.concat("The identifier of this policy and the target asset are " + pid + " and " + target + ", respectively.\n\n");
							break;
						case DELETE:
							translation = translation.concat("The " + provider + " party demands that the Data Consumer " +
									action.toString().toLowerCase() + "s the data asset at a specified date and time.\n");

							if (null != policy.getRules().get(0).getAction().getRefinements()) {
								for (Condition refinement : policy.getRules().get(0).getAction().getRefinements()) {
									switch (refinement.getLeftOperand()) {
										case DELAY:
											String delayPeriod = ((Condition) refinement).getRightOperand().getValue();
											Duration d = BuildMydataPolicyUtils.getDurationFromPeriodValue(delayPeriod);
											translation = translation.concat("The Data Consumer has to wait " + d.getValue() + " " +
													d.getTimeUnit().toString().toLowerCase() + ", before exercising the duty (the Data Provider's demand).\n");
											break;
										case DATE_TIME:
											String dateTimeRefinement = ((Condition) refinement).getRightOperand().getValue();
											translation = translation.concat("The Data Consumer has to exercise the action which is demanded by the Data Provider at " + dateTimeRefinement + ".\n");
											break;
									}
								}
							}
							break;
					}

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
