package de.fraunhofer.iese.ids.odrl.pap.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.jsonldjava.utils.JsonUtils;

import de.fraunhofer.iese.ids.odrl.pap.util.OdrlCreator;
import de.fraunhofer.iese.ids.odrl.pap.util.OdrlTranslator;
import de.fraunhofer.iese.ids.odrl.policy.library.model.OdrlPolicy;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Party;
import de.fraunhofer.iese.ids.odrl.policy.library.model.RightOperand;
import de.fraunhofer.iese.ids.odrl.policy.library.model.RightOperandEntity;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Rule;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.ActionType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.ConditionType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.EntityType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.LeftOperand;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.Operator;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.PartyType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.PolicyType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.RightOperandId;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.RightOperandType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.RuleType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.TimeUnit;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Action;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Condition;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.RightOperandId;
import lombok.Getter;

@Getter
public class RecievedOdrlPolicy {
	private int id;
	private String policyType;
	private String target;
	private String provider;
	private String consumer;
	private List<String> location_input;
	private String location_op;
	private List<String> application_input;
	private String application_op;
	private List<String> connector_input;
	private String connector_op;
	private List<String> role_input;
	private String role_op;
	private List<String> purpose_input;
	private String purpose_op;
	private List<String> event_input;
	private String event_op;
    private List<String> state_input;
    private String state_op;
    private List<String> securityLevel_input;
    private String securityLevel_op;
    

    private Boolean preduties_anomInRest;
    private String preduties_modifier;
    private String preduties_valueToChange;
    private String preduties_fieldToChange;

    
    private String postduties_systemDevice;
    private String postduties_logLevel;
    private String postduties_durationYear;
    private String postduties_durationMonth;
    private String postduties_durationDay;
    private String postduties_durationHour;
    private String postduties_timeAndDate;
    private String postduties_notificationLevel;
    private String postduties_informedParty;
    
    private String system;
    private String interval;
    private String payment;
    private String price;
    private String counter;
    private String encoding;
    private String policy;
    private String time;
    private String timeUnit;
    private String timeAndDate;
    private String informedParty;
    private String systemDevice;
    private String valueToChange;
    private String modifier;
    private String fieldToChange;
    private String restrictTimeIntervalStart;
    private String restrictTimeIntervalEnd;
    private String restrictEndTime;
    private String specifyBeginTime;
    private String durationHour;
    private String durationDay;
    private String durationMonth;
    private String durationYear;
    private String logLevel;
    private String notificationLevel;
    private String artifactState;
	private ArrayList<Condition> constraints = new ArrayList<>();

		
	private String checkIfEmptyValue(String value, String defaultValue) {
		if (value.length() > 0) {
			return value;
		}else {
			return defaultValue;
		}
	}

	// policy components
	public boolean addLocationCondition() {
		if (location_input.size() >0 && !location_input.get(0).equals("")) {
			ArrayList<RightOperand> locationRightOperands = new ArrayList<>();
			location_input.forEach((e) -> {
				RightOperand locationRightOperand = new RightOperand(e, RightOperandType.ANYURI);
				locationRightOperands.add(locationRightOperand);
			});
			//Operator.valueOf(location.getOp())
			Condition locationConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ABSOLUTE_SPATIAL_POSITION, Operator.valueOf(checkIfEmptyValue(location_op, "SAME_AS")), locationRightOperands, null);
			constraints.add(locationConstraint);
			return true;
		}
		return false;
	}

	public boolean addSystemCondition() {
		if (system != "") {
			RightOperand systemRightOperand = new RightOperand(system, RightOperandType.ANYURI);
			ArrayList<RightOperand> systemRightOperands = new ArrayList<>();
			systemRightOperands.add(systemRightOperand);
			Condition systemConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.SYSTEM, Operator.SAME_AS,
					systemRightOperands, null);
			constraints.add(systemConstraint);
			return true;
		}
		return false;
	}

	public boolean addConnectorCondition() {
		if (connector_input.size() >0 && !connector_input.get(0).equals("")) {
			ArrayList<RightOperand> connectorRightOperands = new ArrayList<>();
			connector_input.forEach((e) -> {
				RightOperand connectorRightOperand = new RightOperand(e, RightOperandType.ANYURI);
				connectorRightOperands.add(connectorRightOperand);
			});
			Condition connectorConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.CONNECTOR, Operator.valueOf(checkIfEmptyValue(connector_op, "SAME_AS")), connectorRightOperands, null);
			constraints.add(connectorConstraint);
			return true;
		}
		return false;
	}

	public boolean addApplicationCondition() {
		if (application_input.size() >0 && !application_input.get(0).equals("")) {
			ArrayList<RightOperand> applicationRightOperands = new ArrayList<>();
			application_input.forEach((e) -> {
				RightOperand applicationRightOperand = new RightOperand(e, RightOperandType.ANYURI);
				applicationRightOperands.add(applicationRightOperand);
			});
			Condition applicationConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.APPLICATION, Operator.valueOf(checkIfEmptyValue(application_op, "SAME_AS")),
					applicationRightOperands, null);
			constraints.add(applicationConstraint);
			return true;
		}
		return false;
	}

	public boolean addStateCondition() {
		if (state_input.size() >0 && !state_input.get(0).equals("")) {
			ArrayList<RightOperand> stateRightOperands = new ArrayList<>();
			state_input.forEach((e) -> {
				RightOperand stateRightOperand = new RightOperand(e, RightOperandType.STRING);
				stateRightOperands.add(stateRightOperand);
			});
			Condition stateConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.STATE, Operator.valueOf(checkIfEmptyValue(state_op, "EQUALS")),
					stateRightOperands, null);
			constraints.add(stateConstraint);
			return true;
		}
		return false;
	}

	public boolean addUserRoleCondition() {
		if (role_input.size() >0 && !role_input.get(0).equals("")) {
			ArrayList<RightOperand> userRoleRightOperands = new ArrayList<>();
			role_input.forEach((e) -> {
				RightOperand userRoleRightOperand = new RightOperand(e, RightOperandType.STRING);
				userRoleRightOperands.add(userRoleRightOperand);
			});
			Condition userRoleConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ROLE,  Operator.valueOf(checkIfEmptyValue(role_op, "EQUALS")),
					userRoleRightOperands, null);
			constraints.add(userRoleConstraint);
			return true;
		}
		return false;
	}

	public boolean addSecurityLevelCondition() {
		if (securityLevel_input.size() >0 && !securityLevel_input.get(0).equals("")) {
			ArrayList<RightOperand> userSecurityLevelRightOperands = new ArrayList<>();
			securityLevel_input.forEach((e) -> {
				RightOperand userSecurityLevelRightOperand = new RightOperand(e, RightOperandType.STRING);
				userSecurityLevelRightOperands.add(userSecurityLevelRightOperand);
			});
			Condition userSecurityLevelConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.SECURITY_LEVEL,  Operator.valueOf(checkIfEmptyValue(securityLevel_op, "EQUALS")),
					userSecurityLevelRightOperands, null);
			constraints.add(userSecurityLevelConstraint);
			return true;
		}
		return false;
	}

	public boolean addPurposeCondition() {
		if (purpose_input.size() >0 && !purpose_input.get(0).equals("")) {
			ArrayList<RightOperand> purposeRightOperands = new ArrayList<>();
			purpose_input.forEach((e) -> {
				RightOperand purposeRightOperand = new RightOperand(e, RightOperandType.ANYURI);
				purposeRightOperands.add(purposeRightOperand);
			});
			Condition purposeConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.PURPOSE,  Operator.valueOf(checkIfEmptyValue(purpose_op, "SAME_AS")),
					purposeRightOperands, null);
			constraints.add(purposeConstraint);
			return true;
		}
		return false;
	}

	public boolean addEventCondition() {
		if (event_input.size() >0 && !event_input.get(0).equals("")) {
			ArrayList<RightOperand> eventRightOperands = new ArrayList<>();
			event_input.forEach((e) -> {
				RightOperand eventRightOperand = new RightOperand(e, RightOperandType.ANYURI);
				eventRightOperands.add(eventRightOperand);
			});
			Condition eventConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.EVENT, Operator.valueOf(checkIfEmptyValue(event_op, "SAME_AS")),
					eventRightOperands, null);
			constraints.add(eventConstraint);
			return true;
		}
		return false;
	}

	public boolean addRestrictTimeIntervalCondition() {
		if (restrictTimeIntervalStart != "") {
			RightOperand rightOperand = new RightOperand();
			rightOperand.setType(RightOperandType.INTERVAL);
			RightOperandEntity startInnerEntity = new RightOperandEntity(EntityType.DATETIME, restrictTimeIntervalStart, RightOperandType.DATETIMESTAMP);
			RightOperandEntity startEntity = new RightOperandEntity(EntityType.BEGIN, startInnerEntity,
					RightOperandType.INSTANT);

			RightOperandEntity endInnerEntity = new RightOperandEntity(EntityType.DATETIME, restrictTimeIntervalEnd, RightOperandType.DATETIMESTAMP);
			RightOperandEntity endEntity = new RightOperandEntity(EntityType.END, endInnerEntity,
					RightOperandType.INSTANT);

			ArrayList<RightOperandEntity> entities = new ArrayList<>();
			entities.add(startEntity);
			entities.add(endEntity);
			rightOperand.setEntities(entities);
			ArrayList<RightOperand> rightOperands = new ArrayList<>();
			rightOperands.add(rightOperand);
			Condition timeIntervalCondition = new Condition(ConditionType.CONSTRAINT,
					LeftOperand.DATE_TIME, Operator.TEMPORAL_EQUALS, rightOperands, null);
			constraints.add(timeIntervalCondition);
			return true;
		}
		return false;
	}

	public boolean addRestrictEndTimeCondition() {
		if (restrictEndTime != "") {
			RightOperand rightOperand = new RightOperand();
			rightOperand.setType(RightOperandType.INSTANT);
			RightOperandEntity endEntity = new RightOperandEntity(EntityType.DATETIME, restrictEndTime,
					RightOperandType.DATETIMESTAMP);

			ArrayList<RightOperandEntity> entities = new ArrayList<>();
			entities.add(endEntity);
			rightOperand.setEntities(entities);
			ArrayList<RightOperand> rightOperands = new ArrayList<>();
			rightOperands.add(rightOperand);
			Condition dateTimeCondition = new Condition(ConditionType.CONSTRAINT,
					LeftOperand.DATE_TIME, Operator.BEFORE, rightOperands, null);
			constraints.add(dateTimeCondition);
			return true;
		}
		return false;
	}

	public boolean addPaymentCondition() {
		if (payment != "") {
			RightOperand paymentRightOperand = new RightOperand(String.valueOf(price), RightOperandType.DOUBLE);
			ArrayList<RightOperand> paymentRightOperands = new ArrayList<>();
			paymentRightOperands.add(paymentRightOperand);
			Condition paymentCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.PAY_AMOUNT, Operator.EQ,
					paymentRightOperands, null);
			paymentCondition.setUnit("http://dbpedia.org/resource/Euro");
			paymentCondition.setContract(payment);
			constraints.add(paymentCondition);
			return true;
		}
		return false;
	}

	public boolean addElapsedTimeRightOperand() {
		ArrayList<RightOperandEntity> durationEntities = new ArrayList<>();
		RightOperand elapsedTimeRightOperand = new RightOperand();
		elapsedTimeRightOperand.setType(RightOperandType.DURATIONENTITY);
		if (specifyBeginTime != "" ) {
			RightOperandEntity beginInnerEntity = new RightOperandEntity(EntityType.DATETIME, specifyBeginTime, RightOperandType.DATETIMESTAMP);
			RightOperandEntity beginEntity = new RightOperandEntity(EntityType.BEGIN, beginInnerEntity, RightOperandType.INSTANT);
			durationEntities.add(beginEntity);
		}

		String hour = "";
		String day = "";
		String month = "";
		String year = "";
		if (durationHour != null && !durationHour.isEmpty()) {
			hour = "T" + durationHour + TimeUnit.HOURS.getOdrlXsdDuration();
		}
		if(durationDay != null && !durationDay.isEmpty()) {
			day = durationDay + TimeUnit.DAYS.getOdrlXsdDuration();
		}
		if(durationMonth != null && !durationMonth.isEmpty()) {
			month = durationMonth + TimeUnit.MONTHS.getOdrlXsdDuration();
		}
		if(durationYear != null && !durationYear.isEmpty()) {
			year = durationYear + TimeUnit.YEARS.getOdrlXsdDuration();
		}
		String duration = "P" + year + month + day + hour;

		if(!duration.equals("P"))
		{
			RightOperandEntity hasDurationEntity = new RightOperandEntity(EntityType.HASDURATION, duration  ,RightOperandType.DURATION);
			//hasDurationEntity.setTimeUnit(TimeUnit.valueOf(restrictTimeDurationUnit));
			durationEntities.add(hasDurationEntity);
		}

		if (durationEntities.size() >0) {
			elapsedTimeRightOperand.setEntities(durationEntities);
			ArrayList<RightOperand> elapsedTimeRightOperands = new ArrayList<>();
			elapsedTimeRightOperands.add(elapsedTimeRightOperand);
			Condition elapsedTimeConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ELAPSED_TIME, Operator.SHORTER_EQ, elapsedTimeRightOperands, null);
			constraints.add(elapsedTimeConstraint);
			return true;
		}return false;

	}

	public boolean addCounterCondition() {
		if (counter != "") {
			RightOperand rightOperand = new RightOperand(counter, RightOperandType.DECIMAL);
			ArrayList<RightOperand> rightOperands = new ArrayList<>();
			rightOperands.add(rightOperand);
			Condition countCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.COUNT, Operator.LTEQ, rightOperands, null);
			constraints.add(countCondition);
			return true;
		}
		return false;
	}

	public boolean addTimeAndDate() {
		if (timeAndDate != "") {
			RightOperand dateTimeRightOperand = new RightOperand(timeAndDate, RightOperandType.DATETIMESTAMP);
			ArrayList<RightOperand> dateTimeRightOperands = new ArrayList<>();
			dateTimeRightOperands.add(dateTimeRightOperand);
			Condition dateTimeRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.DATE_TIME,
					Operator.BEFORE, dateTimeRightOperands, null);
			constraints.add(dateTimeRefinement);
			return true;
		}
		return false;
	}

	public boolean addTime() {
		if (time != "") {
			RightOperand delayPeriodRightOperand = new RightOperand(time, RightOperandType.DURATION);
			ArrayList<RightOperand> delayPeriodRightOperands = new ArrayList<>();
			delayPeriodRightOperands.add(delayPeriodRightOperand);
			Condition delayPeriodRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.DELAY,
					Operator.DURATION_EQ, delayPeriodRightOperands, null);
			delayPeriodRefinement.setUnit(TimeUnit.valueOf(timeUnit).toString());
			constraints.add(delayPeriodRefinement);
			return true;
		}
		return false;
	}

	private Party createConsumer() {
		Party consumer = null;
		try {
			consumer = new Party(PartyType.CONSUMER, new URI(this.consumer));
			consumer.setType(PartyType.CONSUMER);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return consumer;
	}

	// Create policy:
//	public String createPolicyConstraints(String policyUID) {
//		Action useAction = new Action(ActionType.USE);
//		Rule rule = new Rule(RuleType.PERMISSION, useAction);
//		rule.setConstraints(constraints);
//		return createPolicy(policyUID, rule);
//	}

//	public String createPolicy(String policyUID, ActionType at, RuleType rt) {
//		Action useAction = new Action(at);
//		Rule rule = new Rule(rt, useAction);
//		if (constraints.size() > 0)
//			rule.setConstraints(constraints);
//		return createPolicy(policyUID, rule);
//	}

	public String createPolicy(String policyUID, Rule rule) {
		OdrlPolicy odrlPolicy = new OdrlPolicy();
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);

		odrlPolicy.setConsumer(createConsumer());
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create(policyUID));
		odrlPolicy.setType(PolicyType.getFromIdsString("ids:Contract" + policyType));
		//odrlPolicy.setTarget(URI.create(target));
		// odrlPolicy.setProvider(new Party(PartyType.CONSUMER, URI
		// .create(recievedPolicy.getProvider())));
		String jsonPolicy = OdrlCreator.createODRL(odrlPolicy);
		Map map = null;
		boolean tempProviderSide = true;
		String dtPolicy = OdrlTranslator.translate(map, tempProviderSide, odrlPolicy);
		//String dtPolicy = policy(jsonPolicy);
		String policies = jsonPolicy + "DTPOLICY:" + dtPolicy;
		return policies;
	}

	public Rule logDuty(String logLevel, String systemDevice, RuleType type) {
		RightOperand logLevelRightOperand = new RightOperand();
		logLevelRightOperand.setType(RightOperandType.STRING);
		logLevelRightOperand.setValue(logLevel);
		ArrayList<RightOperand> logLevelRightOperands = new ArrayList<>();
		logLevelRightOperands.add(logLevelRightOperand);
		Condition logLevelRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.LOG_LEVEL, Operator.DEFINES_AS,
				logLevelRightOperands, "");

		RightOperand rightOperand = new RightOperand(systemDevice, RightOperandType.ANYURI);
		ArrayList<RightOperand> rightOperands = new ArrayList<>();
		rightOperands.add(rightOperand);
		Condition systemDeviceRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.SYSTEM_DEVICE,
				Operator.DEFINES_AS, rightOperands, "");
		
		ArrayList<Condition> refinements = new ArrayList<>();
		refinements.add(logLevelRefinement);
		refinements.add(systemDeviceRefinement);
		Action logDutyAction = new Action(ActionType.LOG);
		logDutyAction.setRefinements(refinements);
		Rule postobligation = new Rule(type, logDutyAction);
		return postobligation;
	}
	
	public Rule informDuty(String notificationLevel, String informedParty, RuleType type) {
		RightOperand notificationLevelRightOperand = new RightOperand();
		notificationLevelRightOperand.setType(RightOperandType.STRING);
		notificationLevelRightOperand.setValue(notificationLevel);
		ArrayList<RightOperand> notificationLevelRightOperands = new ArrayList<>();
		notificationLevelRightOperands.add(notificationLevelRightOperand);
		Condition notificationLevelRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.NOTIFICATION_LEVEL,
				Operator.DEFINES_AS, notificationLevelRightOperands, "");
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		rightOperand.setValue(informedParty);
		ArrayList<RightOperand> rightOperands = new ArrayList<>();
		rightOperands.add(rightOperand);
		Condition recipientRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.RECIPIENT, Operator.DEFINES_AS,
				rightOperands, "");
		ArrayList<Condition> refinements = new ArrayList<>();
		refinements.add(notificationLevelRefinement);
		refinements.add(recipientRefinement);
		Action notifyDutyAction = new Action(ActionType.NOTIFY);
		notifyDutyAction.setRefinements(refinements);
		Rule postobligation = new Rule(type, notifyDutyAction);
		return postobligation;
	}

	public Rule deleteDuty(String timeAndDate, String durationYear, String durationMonth, String durationDay, String durationHour, RuleType type) {
		ArrayList<Condition> refinements = new ArrayList<>();
		RightOperand rightOperand = new RightOperand();
		if (timeAndDate != "") {
			rightOperand.setType(RightOperandType.INSTANT);
			RightOperandEntity dateTimeEntity = new RightOperandEntity(EntityType.DATETIME, timeAndDate ,
					RightOperandType.DATETIMESTAMP);
			ArrayList<RightOperandEntity> entities = new ArrayList<>();
			entities.add(dateTimeEntity);
			rightOperand.setEntities(entities);
			ArrayList<RightOperand> rightOperands = new ArrayList<>();
			rightOperands.add(rightOperand);
			Condition timeIntervalCondition = new Condition(ConditionType.CONSTRAINT,
					LeftOperand.DATE_TIME, Operator.BEFORE, rightOperands, null);
			refinements.add(timeIntervalCondition);
		}
		else {
			rightOperand.setType(RightOperandType.DURATIONENTITY);
			ArrayList<RightOperandEntity> durationEntities = new ArrayList<>();

			String hour = "";
			String day = "";
			String month = "";
			String year = "";
			if (durationHour != null && !durationHour.isEmpty()) {
				hour = "T" + durationHour + TimeUnit.HOURS.getOdrlXsdDuration();
			}
			if(durationDay != null && !durationDay.isEmpty()) {
				day = durationDay + TimeUnit.DAYS.getOdrlXsdDuration();
			}
			if(durationMonth != null && !durationMonth.isEmpty()) {
				month = durationMonth + TimeUnit.MONTHS.getOdrlXsdDuration();
			}
			if(durationYear != null && !durationYear.isEmpty()) {
				year = durationYear + TimeUnit.YEARS.getOdrlXsdDuration();
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
			ArrayList<RightOperand> rightOperands = new ArrayList<>();
			rightOperands.add(rightOperand);
			Condition delayPeriodRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.DELAY, Operator.DURATION_EQ, rightOperands, "");
			refinements.add(delayPeriodRefinement);
		}t

		Action deleteDutyAction = new Action(ActionType.DELETE);
		deleteDutyAction.setRefinements(refinements);
		Rule postobligation = new Rule(type, deleteDutyAction);
		return postobligation;
	}
	
	public Rule anonymizeInTransit(String fieldToChange, String valueToChange, String modifier) {
		ArrayList<Condition> refinements = new ArrayList<>();

		if (fieldToChange != "") {
			RightOperand replaceWithRightOperand = new RightOperand();
			replaceWithRightOperand.setValue(fieldToChange);
			replaceWithRightOperand.setType(RightOperandType.STRING);
			ArrayList<RightOperand> replaceWithRightOperands = new ArrayList<>();
			replaceWithRightOperands.add(replaceWithRightOperand);
			Condition replaceWithRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.REPLACE_WITH,
					Operator.DEFINES_AS, replaceWithRightOperands, null);
			refinements.add(replaceWithRefinement);
		}

		RightOperand subsetSpecificationRightOperand = new RightOperand();
		subsetSpecificationRightOperand.setValue(valueToChange);
		subsetSpecificationRightOperand.setType(RightOperandType.STRING);
		ArrayList<RightOperand> subsetSpecificationRightOperands = new ArrayList<>();
		subsetSpecificationRightOperands.add(subsetSpecificationRightOperand);
		Condition subsetSpecificationRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.JSON_PATH,
				Operator.DEFINES_AS, subsetSpecificationRightOperands, null);
		refinements.add(subsetSpecificationRefinement);
		
		ActionType dutyActionType = ActionType.valueOf(modifier.substring(5));
		Action anonymizeDutyAction = new Action(dutyActionType);
		anonymizeDutyAction.setRefinements(refinements);
		Rule preDuty = new Rule(RuleType.PREDUTY, anonymizeDutyAction);
		return preDuty;
	}
	
	public ArrayList<Rule> addPostDuties() {
		ArrayList<Rule> postDuties = new ArrayList<>();
		if (getPostduties_logLevel()!="" && getPostduties_systemDevice() !="") {
			postDuties.add(logDuty(getPostduties_logLevel(), getPostduties_systemDevice(), RuleType.POSTDUTY));
		}
		if (getPostduties_notificationLevel()!="" && getPostduties_informedParty() !="") {
			postDuties.add(informDuty(getPostduties_notificationLevel(), getPostduties_informedParty(), RuleType.POSTDUTY));
		}
		if (getPostduties_timeAndDate()!="" || (getPostduties_durationYear() !="" && getPostduties_durationMonth() !="" && getPostduties_durationDay() !="" && getPostduties_durationHour() !="")) {
			postDuties.add(deleteDuty(getPostduties_timeAndDate(), getPostduties_durationYear(), getPostduties_durationMonth(), getPostduties_durationDay(), getPostduties_durationHour(), RuleType.POSTDUTY));
		}
		return postDuties;		
	}
	public ArrayList<Rule> addPreDuties() {
		ArrayList<Rule> preDuties = new ArrayList<>();
		if(getPreduties_modifier() != "" && getPreduties_valueToChange() != "") {
			preDuties.add(anonymizeInTransit(preduties_fieldToChange, preduties_valueToChange, preduties_modifier));
		}
		return preDuties;	
	}

}
