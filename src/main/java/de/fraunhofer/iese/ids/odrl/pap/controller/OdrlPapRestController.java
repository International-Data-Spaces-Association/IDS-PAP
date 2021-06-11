package de.fraunhofer.iese.ids.odrl.pap.controller;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.modelmbean.ModelMBeanOperationInfo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.jsonldjava.utils.JsonUtils;

import de.fraunhofer.iese.ids.odrl.pap.model.JsonOdrlPolicy;
import de.fraunhofer.iese.ids.odrl.pap.util.OdrlCreator;
import de.fraunhofer.iese.ids.odrl.pap.util.OdrlTranslator;
import de.fraunhofer.iese.ids.odrl.pap.util.TransformPolicy;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Action;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Condition;
import de.fraunhofer.iese.ids.odrl.policy.library.model.ModificationMethodParameter;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Party;
import de.fraunhofer.iese.ids.odrl.policy.library.model.RightOperand;
import de.fraunhofer.iese.ids.odrl.policy.library.model.RightOperandEntity;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Rule;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.ActionType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.ArtifactStateType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.ConditionType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.EntityType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.LeftOperand;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.LogLevelType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.Operator;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.PartyType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.PolicyType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.RightOperandId;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.RightOperandType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.RuleType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.TimeUnit;
import de.fraunhofer.iese.ids.odrl.policy.library.model.tooling.IdsOdrlUtil;
import de.fraunhofer.iese.ids.odrl.policy.library.model.OdrlPolicy;

@RestController()
@CrossOrigin
public class OdrlPapRestController {
	String baseUid = "https://w3id.org/idsa/autogen/contract/";

	@PostMapping("/policy/ProvideAccess")
	public String accessPolicy(@RequestBody RecievedOdrlPolicy rp) {
		String uid = baseUid + "restrict-access";
		if (rp.addLocationCondition()) {
			uid =  baseUid + "restrict-access-location";
		} 
		if (rp.addApplicationCondition()) {
			uid =  baseUid + "restrict-access-application";
		}
		if (rp.addUserRoleCondition()) {
			uid =  baseUid + "restrict-access-user-role";
		}
		if (rp.addSecurityLevelCondition()) {
			uid =  baseUid + "restrict-access-user-security-level";
		}
		if (rp.addStateCondition()) {
			uid =  baseUid + "restrict-access-state";
		}
		if (rp.addConnectorCondition()) {
			uid =  baseUid + "restrict-access-connector";
		}
		if (rp.addPurposeCondition()) {
			uid =  baseUid + "restrict-access-purpose";
		} 
		if (rp.addEventCondition()) {
			uid =  baseUid + "restrict-access-event";
		} 
		if (rp.addRestrictTimeIntervalCondition()) {
			uid =  baseUid + "restrict-access-interval";
		}
		if (rp.addRestrictEndTimeCondition()) {
			uid =  baseUid + "restrict-access-end-time";
		}
		if (rp.addPaymentCondition()) {
			uid =  baseUid + "provide-access-after-payment";
		} 
		if (rp.addElapsedTimeRightOperand()) {
			uid =  baseUid + "restrict-access-interval";
		}
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setConstraints(rp.getConstraints());
		rule.setTarget(URI.create(rp.getTarget()));
		return rp.createPolicy(uid, rule);
	}

	@PostMapping("/policy/ComplexPolicyForm")
	public String complexPolicy(@RequestBody RecievedOdrlPolicy rp) {
		String uid = baseUid + "complex-policy-access";
		rp.addLocationCondition();
		rp.addApplicationCondition();
		rp.addUserRoleCondition();
		rp.addConnectorCondition();
		rp.addSecurityLevelCondition();
		rp.addStateCondition();
		rp.addPurposeCondition();
		rp.addEventCondition();
		rp.addCounterCondition();
		rp.addRestrictTimeIntervalCondition();
		rp.addRestrictEndTimeCondition();
		rp.addPaymentCondition();
		rp.addElapsedTimeRightOperand();
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setConstraints(rp.getConstraints());
		rule.setTarget(URI.create(rp.getTarget()));
		return rp.createPolicy(uid, rule);
	}

	@PostMapping("/policy/CountAccessPolicyForm")
	public String countPolicy(@RequestBody RecievedOdrlPolicy rp) {
		String uid = baseUid + "count-access";
		rp.addCounterCondition();
		
		Action countDutyAction = new Action(ActionType.INCREMENT_COUNTER);
		Rule postobligation = new Rule(RuleType.POSTDUTY, countDutyAction);
		ArrayList<Rule> postDuties = new ArrayList<>();
		postDuties.add(postobligation);
		
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setPostduties(postDuties);
		rule.setConstraints(rp.getConstraints());
		rule.setTarget(URI.create(rp.getTarget()));
		return rp.createPolicy(uid, rule);
	}

//	@PostMapping("/policy/deletePolicyAfterUsage")
//	public String deletePolicyAfterUsage(@RequestBody RecievedOdrlPolicy rp) {
//		String uid = "http://example.com/policy/count-access";
//
//		ArrayList<Condition> refinements = new ArrayList<>();
//		if (rp.getTimeAndDate() != "") {
//			RightOperand dateTimeRightOperand = new RightOperand(rp.getTimeAndDate(), RightOperandType.DATETIMESTAMP);
//			Condition dateTimeRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.DATE_TIME, Operator.EQUALS, dateTimeRightOperand, "");
//			refinements.add(dateTimeRefinement);
//		}
//		if (rp.getTime() != "") {
//			RightOperand delayPeriodRightOperand = new RightOperand(rp.getTime(), RightOperandType.DURATION);
//			Condition delayPeriodRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.DELAY, Operator.EQUALS, delayPeriodRightOperand, "");
//			delayPeriodRefinement.setUnit(TimeUnit.valueOf(rp.getTimeUnit()).toString());
//			refinements.add(delayPeriodRefinement);
//		}
//		Action deleteAction = new Action(ActionType.DELETE);
//		deleteAction.setRefinements(refinements);
//		Rule rule = new Rule(RuleType.OBLIGATION, deleteAction);
//		String s = addHeaderAndCreatePolicy( rp, rule, "http://example.com/policy/delete-data");
//		return rp.cratePolicy(uid, ActionType.USE, RuleType.PERMISSION);
//
//	}
	@PostMapping("/policy/deletePolicyAfterUsage")
	public String deletePolicyAfterUsage(@RequestBody RecievedOdrlPolicy rp) {
		String uid = baseUid + "delete-after-usage";
		ArrayList<Condition> refinements = new ArrayList<>();
		RightOperand rightOperand = new RightOperand();
		if (rp.getTimeAndDate() != "") {
			rightOperand.setType(RightOperandType.INSTANT);
			RightOperandEntity endEntity = new RightOperandEntity(EntityType.BEGIN, rp.getTimeAndDate() ,
					RightOperandType.DATETIMESTAMP);
			endEntity.setEntityType(EntityType.END);
			endEntity.setDataType(RightOperandType.DATETIMESTAMP);
			ArrayList<RightOperandEntity> entities = new ArrayList<>();
			entities.add(endEntity);
			rightOperand.setEntities(entities);
			Condition timeIntervalCondition = new Condition(ConditionType.CONSTRAINT,
					LeftOperand.DATE_TIME, Operator.BEFORE, rightOperand, null);
			refinements.add(timeIntervalCondition);
		}
		else {
			rightOperand.setType(RightOperandType.DURATIONENTITY);
			ArrayList<RightOperandEntity> durationEntities = new ArrayList<>();

			String hour = "";
			String day = "";
			String month = "";
			String year = "";
			if (rp.getDurationHour() != null && !rp.getDurationHour().isEmpty()) {
				hour = "T" + rp.getDurationHour() + TimeUnit.HOURS.getOdrlXsdDuration();
			}
			if(rp.getDurationDay() != null && !rp.getDurationDay().isEmpty()) {
				day = rp.getDurationDay() + TimeUnit.DAYS.getOdrlXsdDuration();
			}
			if(rp.getDurationMonth() != null && !rp.getDurationMonth().isEmpty()) {
				month = rp.getDurationMonth() + TimeUnit.MONTHS.getOdrlXsdDuration();
			}
			if(rp.getDurationYear() != null && !rp.getDurationYear().isEmpty()) {
				year = rp.getDurationYear() + TimeUnit.YEARS.getOdrlXsdDuration();
			}
			String duration = "P" + year + month + day + hour;

			if(duration.equals("P"))
			{
				//set initial delay value
				duration = "P0D";
			}

			RightOperandEntity hasDurationEntity = new RightOperandEntity(EntityType.HASDURATION, duration  ,RightOperandType.DURATION);
			durationEntities.add(hasDurationEntity);
			rightOperand.setEntities(durationEntities);
			Condition delayPeriodRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.DELAY, Operator.DURATION_EQ, rightOperand, "");
			refinements.add(delayPeriodRefinement);
		}

	  	Action useAction = new Action(ActionType.USE);
		Action deleteDutyAction = new Action(ActionType.DELETE);
		deleteDutyAction.setRefinements(refinements);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		Rule postobligation = new Rule(RuleType.POSTDUTY, deleteDutyAction);
		ArrayList<Rule> postDuties = new ArrayList<>();
		postDuties.add(postobligation);
		rule.setPostduties(postDuties);
		rule.setTarget(URI.create(rp.getTarget()));
		return rp.createPolicy(uid, rule);
	}

	@PostMapping("/policy/deletePolicyAfterUsagePeriod")
	public String deletePolicyAfterUsagePeriod(@RequestBody RecievedOdrlPolicy rp) {	
		String uid = baseUid + "delete-after-usage";
	  	Action useAction = new Action(ActionType.USE);
		Action deleteDutyAction = new Action(ActionType.DELETE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		Rule postobligation = new Rule(RuleType.POSTDUTY, deleteDutyAction);
		ArrayList<Rule> postDuties = new ArrayList<>();
		postDuties.add(postobligation);
		rule.setPostduties(postDuties);
		rule.setTarget(URI.create(rp.getTarget()));
		return rp.createPolicy(uid, rule);
	}
	
	@PostMapping("/policy/AnonymizeInRestPolicyForm")
	public String anonymizeInRestolicy(@RequestBody RecievedOdrlPolicy rp) {
		String uid = baseUid + "anonymize-in-rest";
		Action anonymizeAction = new Action(ActionType.ANONYMIZE);
		Rule rule = new Rule(RuleType.OBLIGATION, anonymizeAction);
		rule.setTarget(URI.create(rp.getTarget()));
		return rp.createPolicy(uid, rule);
	}

	@PostMapping("/policy/AnonymizeInTransitPolicyForm")
	public String anonymizeTransitPolicy(@RequestBody RecievedOdrlPolicy rp) {
		String uid = baseUid + "anonymize-in-transit";
		ArrayList<Condition> refinements = new ArrayList<>();

		if (rp.getFieldToChange() != "") {
			RightOperand replaceWithRightOperand = new RightOperand();
			replaceWithRightOperand.setValue(rp.getValueToChange());
			replaceWithRightOperand.setType(RightOperandType.STRING);
			Condition replaceWithRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.REPLACE_WITH,
					Operator.DEFINES_AS, replaceWithRightOperand, null);
			refinements.add(replaceWithRefinement);
		}

		RightOperand subsetSpecificationRightOperand = new RightOperand();
		subsetSpecificationRightOperand.setValue(rp.getFieldToChange());
		subsetSpecificationRightOperand.setType(RightOperandType.STRING);
		Condition subsetSpecificationRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.SUBSET_SPECIFICATION,
				Operator.DEFINES_AS, subsetSpecificationRightOperand, null);
		refinements.add(subsetSpecificationRefinement);

		Action useAction = new Action(ActionType.USE);
		// remove the idsc: prefix from the duty action
		ActionType dutyActionType = ActionType.valueOf(rp.getModifier().substring(5));
		Action anonymizeDutyAction = new Action(dutyActionType);
		anonymizeDutyAction.setRefinements(refinements);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setTarget(URI.create(rp.getTarget()));
		Rule preDuty = new Rule(RuleType.PREDUTY, anonymizeDutyAction);
		ArrayList<Rule> preDuties = new ArrayList<>();
		preDuties.add(preDuty);
		rule.setPreduties(preDuties);

		return rp.createPolicy(uid, rule);
	}

	@PostMapping("/policy/LogAccessPolicyForm")
	public String logPolicy(@RequestBody RecievedOdrlPolicy rp) {
		String uid = baseUid + "log-access";
		RightOperand logLevelRightOperand = new RightOperand();
		logLevelRightOperand.setType(RightOperandType.STRING);
		logLevelRightOperand.setValue(rp.getLogLevel());
		Condition logLevelRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.LOG_LEVEL, Operator.DEFINES_AS,
				logLevelRightOperand, "");

		RightOperand rightOperand = new RightOperand(rp.getSystemDevice(), RightOperandType.ANYURI);
		Condition systemDeviceRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.SYSTEM_DEVICE,
				Operator.DEFINES_AS, rightOperand, "");

		ArrayList<Condition> refinements = new ArrayList<>();
		refinements.add(logLevelRefinement);
		refinements.add(systemDeviceRefinement);
		Action useAction = new Action(ActionType.USE);
		Action logDutyAction = new Action(ActionType.LOG);
		logDutyAction.setRefinements(refinements);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setTarget(URI.create(rp.getTarget()));
		Rule postobligation = new Rule(RuleType.POSTDUTY, logDutyAction);
		ArrayList<Rule> postDuties = new ArrayList<>();
		postDuties.add(postobligation);
		rule.setPostduties(postDuties);
		return rp.createPolicy(uid, rule);
	}

	@PostMapping("/policy/InformPolicyForm")
	public String policy(@RequestBody RecievedOdrlPolicy rp) {
		String uid = baseUid + "notify";
		RightOperand notificationLevelRightOperand = new RightOperand();
		notificationLevelRightOperand.setType(RightOperandType.STRING);
		notificationLevelRightOperand.setValue(rp.getNotificationLevel());
		Condition notificationLevelRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.NOTIFICATION_LEVEL,
				Operator.EQUALS, notificationLevelRightOperand, "");
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		rightOperand.setValue(rp.getInformedParty());
		Condition recipientRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.RECIPIENT, Operator.EQUALS,
				rightOperand, "");
		ArrayList<Condition> refinements = new ArrayList<>();
		refinements.add(notificationLevelRefinement);
		refinements.add(recipientRefinement);
		Action useAction = new Action(ActionType.USE);
		Action notifyDutyAction = new Action(ActionType.NOTIFY);
		notifyDutyAction.setRefinements(refinements);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setTarget(URI.create(rp.getTarget()));
		Rule postobligation = new Rule(RuleType.POSTDUTY, notifyDutyAction);
		ArrayList<Rule> postDuties = new ArrayList<>();
		postDuties.add(postobligation);
		rule.setPostduties(postDuties);
		return rp.createPolicy(uid, rule);
	}

	@PostMapping("/policy/DistributePolicyForm")
	public String encodingPolicy(@RequestBody RecievedOdrlPolicy rp) {
		String uid = baseUid + "restrict-access-encoding";
		RightOperand dutyRightOperand = new RightOperand();
		dutyRightOperand.setType(RightOperandType.ANYURI);
		dutyRightOperand.setValue(rp.getPolicy());
		Condition thirdPartyRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.TARGET_POLICY,
				Operator.SAME_AS, dutyRightOperand, "");
		ArrayList<Condition> dutyRefinements = new ArrayList<>();
		dutyRefinements.add(thirdPartyRefinement);

		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.STRING);
		rightOperand.setValue(rp.getArtifactState());
		Condition artifactStateConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ARTIFACT_STATE,
				Operator.EQUALS, rightOperand, "");
		ArrayList<Condition> constraints = new ArrayList<>();
		constraints.add(artifactStateConstraint);
		Action distributeAction = new Action(ActionType.DISTRIBUTE);
		Action nextPolicyDutyAction = new Action(ActionType.NEXT_POLICY);
		nextPolicyDutyAction.setRefinements(dutyRefinements);
		Rule rule = new Rule(RuleType.PERMISSION, distributeAction);
		rule.setTarget(URI.create(rp.getTarget()));
		Rule preDuties = new Rule(RuleType.PREDUTY, nextPolicyDutyAction);
		ArrayList<Rule> preDutiess = new ArrayList<>();
		preDutiess.add(preDuties);
		rule.setPreduties(preDutiess);
		rule.setConstraints(constraints);
		return rp.createPolicy(uid, rule);
	}

	@PostMapping("/policy/JsonOdrlPolicyMYDATA")
	public String policy(@RequestBody String jsonPolicy) {
		OdrlPolicy odrlPolicy = IdsOdrlUtil.getOdrlPolicy(jsonPolicy);

		boolean tempProviderSide = true;
		return TransformPolicy.createTechnologyDependentPolicy(odrlPolicy, tempProviderSide);
	}


	@PostMapping("/policy/InterpretOdrlPolicy")
	public String interpretPolicy(@RequestBody String jsonPolicy) {
		OdrlPolicy odrlPolicy = IdsOdrlUtil.getOdrlPolicy(jsonPolicy);
		Map map = null;
		try {
			if(null != jsonPolicy)
			{
				Object o = JsonUtils.fromString(jsonPolicy);
				if (o instanceof Map) {
					map  = (Map) o;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		boolean tempProviderSide = true;
		String dtPolicy = OdrlTranslator.translate(map, tempProviderSide, odrlPolicy);
		return dtPolicy;
	}
}