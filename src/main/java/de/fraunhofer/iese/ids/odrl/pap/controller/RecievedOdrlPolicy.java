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

public class RecievedOdrlPolicy {
	public int id;
    public String policyType;
    public String target;
    public String provider;
    public String consumer;
    public List<String> location_input;
    public String location_op;
    public List<String> application_input;
    public String application_op;
    public List<String> connector_input;
    public String connector_op;
    public List<String> role_input;
	public String role_op;
    public List<String> purpose_input;
    public String purpose_op;
    public List<String> event_input;
    public String event_op;
    public List<String> state_input;
    public String state_op;
    public List<String> securityLevel_input;
    public String securityLevel_op;
    public String preduties_systemDevice;
    public String preduties_logLevel;
    public String preduties_durationYear;
    public String preduties_durationMonth;
    public String preduties_durationDay;
    public String preduties_durationHour;
    public String preduties_timeAndDate;
    public String preduties_notificationLevel;
    public String preduties_informedParty;
    public String postduties_systemDevice;
    public String postduties_logLevel;
    public String postduties_durationYear;
    public String postduties_durationMonth;
    public String postduties_durationDay;
    public String postduties_durationHour;
    public String postduties_timeAndDate;
    public String postduties_notificationLevel;
    public String postduties_informedParty;
    public String system;
    public String interval;
    public String payment;
    public String price;
    public String counter;
    public String encoding;
    public String policy;
    public String time;
    public String timeUnit;
    public String timeAndDate;
    public String informedParty;
    public String systemDevice;
    public String valueToChange;
    public String modifier;
    public String fieldToChange;
    public String restrictTimeIntervalStart;
    public String restrictTimeIntervalEnd;
    public String restrictEndTime;
    public String specifyBeginTime;
    public String durationHour;
    public String durationDay;
    public String durationMonth;
    public String durationYear;
    public String logLevel;
    public String notificationLevel;
    public String artifactState;
	private ArrayList<Condition> constraints = new ArrayList<>();

    public int getId() {
		return id;
	}

	public String getPolicyType() {
		return policyType;
	}

	public String getTarget() {
		return target;
	}

	public String getProvider() {
		return provider;
	}

	public String getConsumer() {
		return consumer;
	}

	public List<String> getLocation_input() {
		return location_input;
	}

	public String getLocation_op() {
		return location_op;
	}

	public List<String> getApplication_input() {
		return application_input;
	}

	public String getApplication_op() {
		return application_op;
	}

	public List<String> getConnector_input() {
		return connector_input;
	}

	public String getConnector_op() {
		return connector_op;
	}

	public List<String> getRole_input() {
		return role_input;
	}

	public String getRole_op() {
		return role_op;
	}

	public List<String> getPurpose_input() {
		return purpose_input;
	}

	public String getPurpose_op() {
		return purpose_op;
	}

	public List<String> getEvent_input() {
		return event_input;
	}

	public String getEvent_op() {
		return event_op;
	}

	public List<String> getState_input() {
		return state_input;
	}

	public String getState_op() {
		return state_op;
	}

	public List<String> getSecurityLevel_input() {
		return securityLevel_input;
	}

	public String getSecurityLevel_op() {
		return securityLevel_op;
	}

	public String getPreduties_systemDevice() {
		return preduties_systemDevice;
	}

	public String getPreduties_logLevel() {
		return preduties_logLevel;
	}

	public String getPreduties_durationYear() {
		return preduties_durationYear;
	}

	public String getPreduties_durationMonth() {
		return preduties_durationMonth;
	}

	public String getPreduties_durationDay() {
		return preduties_durationDay;
	}

	public String getPreduties_durationHour() {
		return preduties_durationHour;
	}

	public String getPreduties_timeAndDate() {
		return preduties_timeAndDate;
	}

	public String getPreduties_notificationLevel() {
		return preduties_notificationLevel;
	}

	public String getPreduties_informedParty() {
		return preduties_informedParty;
	}

	public String getPostduties_systemDevice() {
		return postduties_systemDevice;
	}

	public String getPostduties_logLevel() {
		return postduties_logLevel;
	}

	public String getPostduties_durationYear() {
		return postduties_durationYear;
	}

	public String getPostduties_durationMonth() {
		return postduties_durationMonth;
	}

	public String getPostduties_durationDay() {
		return postduties_durationDay;
	}

	public String getPostduties_durationHour() {
		return postduties_durationHour;
	}

	public String getPostduties_timeAndDate() {
		return postduties_timeAndDate;
	}

	public String getPostduties_notificationLevel() {
		return postduties_notificationLevel;
	}

	public String getPostduties_informedParty() {
		return postduties_informedParty;
	}

	public String getSystem() {
		return system;
	}

	public String getInterval() {
		return interval;
	}

	public String getPayment() {
		return payment;
	}

	public String getPrice() {
		return price;
	}

	public String getCounter() {
		return counter;
	}

	public String getEncoding() {
		return encoding;
	}

	public String getPolicy() {
		return policy;
	}

	public String getTime() {
		return time;
	}

	public String getTimeUnit() {
		return timeUnit;
	}

	public String getTimeAndDate() {
		return timeAndDate;
	}

	public String getInformedParty() {
		return informedParty;
	}

	public String getSystemDevice() {
		return systemDevice;
	}

	public String getValueToChange() {
		return valueToChange;
	}

	public String getModifier() {
		return modifier;
	}

	public String getFieldToChange() {
		return fieldToChange;
	}

	public String getRestrictTimeIntervalStart() {
		return restrictTimeIntervalStart;
	}

	public String getRestrictTimeIntervalEnd() {
		return restrictTimeIntervalEnd;
	}

	public String getRestrictEndTime() {
		return restrictEndTime;
	}

	public String getSpecifyBeginTime() {
		return specifyBeginTime;
	}

	public String getDurationHour() {
		return durationHour;
	}

	public String getDurationDay() {
		return durationDay;
	}

	public String getDurationMonth() {
		return durationMonth;
	}

	public String getDurationYear() {
		return durationYear;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public String getNotificationLevel() {
		return notificationLevel;
	}

	public String getArtifactState() {
		return artifactState;
	}

	public ArrayList<Condition> getConstraints() {
		return constraints;
	}
		
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
		}

		Action deleteDutyAction = new Action(ActionType.DELETE);
		deleteDutyAction.setRefinements(refinements);
		Rule postobligation = new Rule(type, deleteDutyAction);
		return postobligation;
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
		if (getPreduties_logLevel()!="" && getPreduties_systemDevice() !="") {
			preDuties.add(logDuty(getPreduties_logLevel(), getPreduties_systemDevice(), RuleType.PREDUTY));
		}
		if (getPreduties_notificationLevel()!="" && getPreduties_informedParty() !="") {
			preDuties.add(informDuty(getPreduties_notificationLevel(), getPreduties_informedParty(), RuleType.PREDUTY));
		}
		if (getPreduties_timeAndDate()!="" || (getPreduties_durationYear() !="" && getPreduties_durationMonth() !="" && getPreduties_durationDay() !="" && getPreduties_durationHour() !="")) {
			preDuties.add(deleteDuty(getPreduties_timeAndDate(), getPreduties_durationYear(), getPreduties_durationMonth(), getPreduties_durationDay(), getPreduties_durationHour(), RuleType.PREDUTY));
		}
		return preDuties;	
	}

}
