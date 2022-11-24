package de.fraunhofer.iese.ids.odrl.pap.util;

import java.util.Map;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import de.fraunhofer.iese.ids.odrl.mydata.translator.util.MyDataUtil;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Action;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Condition;
import de.fraunhofer.iese.ids.odrl.policy.library.model.RightOperand;
import de.fraunhofer.iese.ids.odrl.policy.library.model.RightOperandEntity;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Rule;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.ActionType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.RuleType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.interfaces.IPolicy;
import de.fraunhofer.iese.ids.odrl.policy.library.model.tooling.PatternUtil;


public class OdrlTranslator {

	public static String translate(Map map, Boolean providerSide, IPolicy defaultPolicy) {

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
			if(null != policy.getRules() && !policy.getRules().isEmpty())
			{
				for(Rule rule:policy.getRules())
				{
					//set target
					String target = "";
					if(null != rule.getTarget()) {
						target = rule.getTarget().toString();
					}

					RuleType ruleType = rule.getRuleType();
					ActionType action = rule.getAction().getType();
					if(RuleType.OBLIGATION.equals(ruleType)){

						switch (action){
							case ANONYMIZE:
								translation = translation.concat("In this Policy " + policyType + " example, the " + ruleType.getOdrlRepresentation() + " rule demands the Data Consumer to " +
										action.toString().toLowerCase() + " the target asset.\n");
								translation = translation.concat("The identifier of this policy and the target asset are " + pid + " and " + target + ", respectively.\n\n");
								break;
							case DELETE:
								translation = translation.concat("The " + provider + " party demands that the Data Consumer " +
										action.toString().toLowerCase() + "s the data asset at a specified date and time.\n");

								if (null != rule.getAction().getRefinements()) {
									for (Condition refinement : rule.getAction().getRefinements()) {
										for(RightOperand rightOperand: refinement.getRightOperands()) {
											switch (refinement.getLeftOperand()) {
												case DELAY:
													RightOperandEntity delayEntity = rightOperand.getRightOperandEntities().get(0);
													String delayPeriod = delayEntity.getValue();
													//Duration d = BuildMydataPolicyUtils.getDurationFromPeriodValue(delayPeriod);
													translation = translation.concat("The Data Consumer may delay performing the action. Here the specified delay value is: " + delayPeriod + " .\n");
													break;
												case DATE_TIME:
													String dateTimeRefinement = rightOperand.getValue();
													translation = translation.concat("The Data Consumer has to exercise the action which is demanded by the Data Provider at " + dateTimeRefinement + ".\n");
													break;
											}
										}
									}
								}
								break;
						}

					}else {

						translation = translation.concat("In this Policy " + policyType + " example, the " + ruleType.getOdrlRepresentation() + " rule " +
								MyDataUtil.getMyDataDecision(ruleType) + "s the Data Consumer to " + action.toString().toLowerCase() + " the target asset.\n");
						translation = translation.concat("The identifier of this policy and the target asset are " + pid + " and " + target + ", respectively.\n\n");

						if(null != rule.getConstraints()) {
							for (Condition constraint : rule.getConstraints()) {
								for(RightOperand rightOperand: constraint.getRightOperands()) {
									String rightOperandValue = rightOperand.getValue();
									switch (constraint.getLeftOperand()) {
										case EVENT:
											translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific event; the Data Consumer is " +
													MyDataUtil.getMyDataDecision(ruleType) + "ed to " + action.toString().toLowerCase() + " the data asset when the " +
													getLastSplitElement(rightOperandValue) + " event has been triggered.\n\n");
											break;
										case PURPOSE:
											translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific purpose; the Data Consumer is " +
													MyDataUtil.getMyDataDecision(ruleType) + "ed to " + action.toString().toLowerCase() + " the data asset for " +
													getLastSplitElement(rightOperandValue) + " purpose.\n\n");
											break;
										case SYSTEM:
											translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific system; the Data Consumer is " +
													MyDataUtil.getMyDataDecision(ruleType) + "ed to " + action.toString().toLowerCase() + " the data asset when the system which will process the data is " +
													getLastSplitElement(rightOperandValue) + ".\n\n");
											break;
										case APPLICATION:
											translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific Data App; the Data Consumer is " +
													MyDataUtil.getMyDataDecision(ruleType) + "ed to " + action.toString().toLowerCase() + " the data asset when the Data App which will process the data is " +
													getLastSplitElement(rightOperandValue) + ".\n\n");
											break;
										case CONNECTOR:
											translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific Connector of a specific Data Consumer; the Data Consumer is " +
													MyDataUtil.getMyDataDecision(ruleType) + "ed to " + action.toString().toLowerCase() + " the data asset when the connector which will process the data is " +
													getLastSplitElement(rightOperandValue) + ".\n\n");
											break;
										case STATE:
											translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific State of a Data Consumer connector; the Data Consumer is " +
													MyDataUtil.getMyDataDecision(ruleType) + "ed to " + action.toString().toLowerCase() + " the data asset when the state in which the data will be processed is " +
													getLastSplitElement(rightOperandValue) + ".\n\n");
											break;
										case SECURITY_LEVEL:
											translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific Data Consumer with a specific connector security level; the Data Consumer is " +
													MyDataUtil.getMyDataDecision(ruleType) + "ed to " + action.toString().toLowerCase() + " the data asset when the security level of the connector which will process the data is " +
													getLastSplitElement(rightOperandValue) + ".\n\n");
											break;
										case ROLE:
											translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific user roles in the user group of a specific Data Consumer; the Data Consumer is " +
													MyDataUtil.getMyDataDecision(ruleType) + "ed to " + action.toString().toLowerCase() + " the data asset when the user which will use the data has the role " +
													getLastSplitElement(rightOperandValue) + ".\n\n");
											break;
										case ABSOLUTE_SPATIAL_POSITION:
											translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific location; the Data Consumer is " +
													MyDataUtil.getMyDataDecision(ruleType) + "ed to " + action.toString().toLowerCase() + " the data asset when it's connector is located in " +
													getLastElement(rightOperandValue) + ".\n\n");
											break;
										case PAY_AMOUNT:
											String contract = ((Condition) constraint).getContract();
											String unit = ((Condition) constraint).getUnit();
											translation = translation.concat("In this policy, the " + provider + " party announces the Data " + getLastElement(contract) +
													" under specific condition; the Data Consumer is only allowed to " + ActionType.USE.toString().toLowerCase() +
													" the data after paying " + rightOperandValue + " " + getLastElement(unit) + ".\n\n");
											break;
											/*
										case POLICY_EVALUATION_TIME:
											translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific time interval; ");

											for (RightOperandEntity entity : rightOperand.getRightOperandEntities()) {
												switch (entity.getEntityType()) {
													case BEGIN:
														String begin = entity.getInnerEntity().getValue();
														translation = translation.concat("The Data Consumer is " + decision.getMydataDecision() + "ed to " + action.toString().toLowerCase() + " the data from this date: " + begin + " .\n" );
														break;
													case END:
														String end = entity.getInnerEntity().getValue();
														translation = translation.concat(" The end date is: " + end + ".\n");
														break;
												}
											}
											break;
											*/
										case DATE_TIME:
											translation = translation.concat("The " + provider + " party restricts the usage of the data asset to a specific time interval; ");

											for (RightOperandEntity entity : rightOperand.getRightOperandEntities()) {
												switch (entity.getEntityType()) {
													case BEGIN:
														String begin = entity.getInnerEntity().getValue();
														translation = translation.concat("The Data Consumer is " + MyDataUtil.getMyDataDecision(ruleType) + "ed to " + action.toString().toLowerCase() + " the data from this date: " + begin + " .\n" );
														break;
													case END:
														String end = entity.getInnerEntity().getValue();
														translation = translation.concat(" The end date is: " + end + ".\n");
														break;
												}
											}

											break;
										case ELAPSED_TIME:
											//Duration d = BuildMydataPolicyUtils.getDurationFromPeriodValue(rightOperandValue);
											//RightOperandEntity elapsedTimeEntity = rightOperand.getRightOperandEntities().get(0);
											translation = translation.concat("The Data Consumer can use the data for the duration of " + rightOperandValue + " "
													+ "(from the date that the agreement policy was issued!).\n");
											break;
										case COUNT:
											translation = translation.concat("This policy specifies that the Data Consumer is " + MyDataUtil.getMyDataDecision(ruleType) + "ed to " +
													action.toString().toLowerCase() + " the data asset (not more than) " + rightOperandValue + " times.\n\n");
											break;
//										case ENCODING:
//											translation = translation.concat("The data asset must be encoded (compressed or encrypted) before it is distributed.\n\n");
//											break;
									}
								}
							}
						}

						Action dutyAction = null;

						if(null != rule.getDuties() && rule.getDuties().size() > 0)
						{
							dutyAction = (Action) rule.getDuties().get(0).getAction();
						}

						if(null != dutyAction) {
							ActionType actionType = dutyAction.getType();
							switch (actionType) {
								case DELETE:

									translation = translation.concat("The " + provider + " party demands that the Data Consumer " +
											actionType.toString().toLowerCase() + "s the data asset after it is used.\n\n");
									if (null != dutyAction.getRefinements()) {
										for (Condition refinement : dutyAction.getRefinements()) {
											for(RightOperand rightOperand: refinement.getRightOperands())
											{
												switch (refinement.getLeftOperand()) {
													case DELAY:
														RightOperandEntity rightOperandEntity = rightOperand.getRightOperandEntities().get(0);
														String delayPeriod = rightOperandEntity.getValue();
														//Duration d = BuildMydataPolicyUtils.getDurationFromPeriodValue(delayPeriod);
														translation = translation.concat("The Data Consumer may delay exercising the duty. Here the specified delay value is: " + delayPeriod + " .\n");
														break;
													case DATE_TIME:
														RightOperandEntity dateTimeEntity = rightOperand.getRightOperandEntities().get(0);
														String dateTimeRefinement = dateTimeEntity.getValue();
														translation = translation.concat("The Data Consumer has to exercise the action which is demanded by the Data Provider " + refinement.getOperator().toString().toLowerCase() + " "
																+ dateTimeRefinement + ".\n");
														break;
												}
											}
										}
									}
									break;

								case ANONYMIZE:
									translation = translation.concat("The Data Consumer has to exercise the action which is demanded by the Data Provider before the usage of the data asset. Here, the policy specifies that the Data Consumer must "
											+ actionType.toString().toLowerCase() + " the data.\n");
									if (null != dutyAction.getRefinements()) {
										for (Condition refinement : dutyAction.getRefinements()) {
											for(RightOperand rightOperand: refinement.getRightOperands())
											{
												switch (refinement.getLeftOperand()) {
													case JSON_PATH:
														String jsonPath = rightOperand.getValue();
														translation = translation.concat("The " + jsonPath + " field of the data asset (given as jsonPathQuery) is requested to be modified (deleted, hashed, replaced, etc.).\n");
														break;
													case REPLACE_WITH:
														String replaceWith = rightOperand.getValue();
														translation = translation.concat("The addressed field shall be replaced with the given value \"" + replaceWith + "\".\n");
														break;
												}
											}
										}
									}
									break;
								case REPLACE:
									translation = translation.concat("The Data Consumer has to exercise the action which is demanded by the Data Provider before the usage of the data asset. Here, the policy specifies that the Data Consumer must "
											+ actionType.toString().toLowerCase() + " a subset of the data with a given value.\n");
									if (null != dutyAction.getRefinements()) {
										for (Condition refinement : dutyAction.getRefinements()) {
											for(RightOperand rightOperand: refinement.getRightOperands()) {
												switch (refinement.getLeftOperand()) {
													case JSON_PATH:
														String jsonPath = rightOperand.getValue();
														translation = translation.concat("The " + jsonPath + " field of the data asset (given as jsonPathQuery) is requested to be modified (deleted, hashed, replaced, etc.).\n");
														break;
													case REPLACE_WITH:
														String replaceWith = rightOperand.getValue();
														translation = translation.concat("The addressed field shall be replaced with the given value \"" + replaceWith + "\".\n");
														break;
												}
											}
										}
									}
									break;
								case DROP:
									translation = translation.concat("The Data Consumer has to exercise the action which is demanded by the Data Provider before the usage of the data asset. Here, the policy specifies that the Data Consumer must "
											+ actionType.toString().toLowerCase() + " a subset of the data.\n");
									if (null != dutyAction.getRefinements()) {
										for (Condition refinement : dutyAction.getRefinements()) {
											for(RightOperand rightOperand: refinement.getRightOperands()) {
												switch (refinement.getLeftOperand()) {
													case JSON_PATH:
														String jsonPath = rightOperand.getValue();
														translation = translation.concat("The " + jsonPath + " field of the data asset (given as jsonPathQuery) is requested to be modified (deleted, hashed, replaced, etc.).\n");
														break;
												}
											}
										}
									}
									break;
								case LOG:
									translation = translation.concat("The " + provider + " party demands that the Data Consumer logs the information about the data usage. \n");
									if (null != dutyAction.getRefinements()) {
										for (Condition refinement : dutyAction.getRefinements()) {
											for(RightOperand rightOperand: refinement.getRightOperands()) {
												switch (refinement.getLeftOperand()) {
													case SYSTEM_DEVICE:
														String systemDevice = rightOperand.getValue();
														translation = translation.concat("The location to store the logs is " + getLastSplitElement(systemDevice) + " system.\n\n");
														break;
												}
											}
										}
									}
									break;
//								case INFORM:
//									translation = translation.concat("The " + provider + " party demands that the Data Consumer informs a party about the usage of data.\n\n");
//
//									if (null != dutyAction.getRefinements()) {
//										for (Condition refinement : dutyAction.getRefinements()) {
//											for(RightOperand rightOperand: refinement.getRightOperands()) {
//												switch (refinement.getLeftOperand()) {
//													case INFORMEDPARTY:
//														String informedPartyValue = rightOperand.getValue();
//														translation = translation.concat("This policy specifies that the informed party is " +
//																getLastSplitElement(informedPartyValue) + " .\n\n");
//														break;
//												}
//											}
//										}
//									}
//									break;
								case NEXT_POLICY:
									translation = translation.concat("The next policy action expresses the allowable usages by third-parties; " +
											"it is demanded that the usage of the data asset by the third party has to be limited to the conditions that are specified in the target policy.\n\n");

									if (null != dutyAction.getRefinements()) {
										for (Condition refinement : dutyAction.getRefinements()) {
											for(RightOperand rightOperand: refinement.getRightOperands()) {
												switch (refinement.getLeftOperand()) {
													case TARGET_POLICY:
														String thirdPartyValue = rightOperand.getValue();
														translation = translation.concat("This policy specifies that the policy to be sent to the third party is " +
																getLastSplitElement(thirdPartyValue) + " .\n\n");
														break;
												}
											}
										}
									}
									break;
							}
						}
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
