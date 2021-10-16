package de.fraunhofer.iese.ids.odrl.pap.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
	private String id;
	private String policyType;
	private String target;
	private String provider;
	private String consumer;
	private MultiSelectInputField location;
	private String system;
	private MultiSelectInputField application;
	private MultiSelectInputField role;
	private MultiSelectInputField securityLevel;
	private MultiSelectInputField connector;
	private MultiSelectInputField state;
	private MultiSelectInputField purpose;
	private MultiSelectInputField event;
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
	private int numberOfUsage = -1;
	private String specifyBeginTime;
	private String durationYear;
	private String durationMonth;
	private String durationDay;
	private String durationHour;
	private String logLevel;
	private String notificationLevel;
	private String artifactState;
	private ArrayList<Condition> constraints = new ArrayList<>();

	public String getId() {
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

	public MultiSelectInputField getLocation() {
		return location;
	}

	public String getSystem() {
		return system;
	}

	public MultiSelectInputField getApplication() {
		return application;
	}

	public MultiSelectInputField getConnector() {return connector;}

	public MultiSelectInputField getSecurityLevel() {return securityLevel;}

	public MultiSelectInputField getRole() {return role;}

	public MultiSelectInputField getState() {return state;}

	public MultiSelectInputField getPurpose() {
		return purpose;
	}

	public MultiSelectInputField getEvent() {
		return event;
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

	public int getNumberOfUsage() {
		return numberOfUsage;
	}

	public String getSpecifyBeginTime() {
		return specifyBeginTime;
	}

	public String getDurationYear() {
		return durationYear;
	}

	public String getDurationMonth() { return durationMonth; }

	public String getDurationDay() {
		return durationDay;
	}

	public String getDurationHour() {
		return durationHour;
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

	// policy components
	public boolean addLocationCondition() {
		if (location != null && location.getInput().size() >0 && !location.getInput().get(0).equals("")) {
			ArrayList<RightOperand> locationRightOperands = new ArrayList<>();
			location.getInput().forEach((e) -> {
				RightOperand locationRightOperand = new RightOperand(e, RightOperandType.ANYURI);
				locationRightOperands.add(locationRightOperand);
			});
			//Operator.valueOf(location.getOp())
			Condition locationConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ABSOLUTE_SPATIAL_POSITION, Operator.SAME_AS, locationRightOperands, null);
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
		if (connector != null && connector.getInput().size() >0 && !connector.getInput().get(0).equals("")) {
			ArrayList<RightOperand> connectorRightOperands = new ArrayList<>();
			connector.getInput().forEach((e) -> {
				RightOperand connectorRightOperand = new RightOperand(e, RightOperandType.ANYURI);
				connectorRightOperands.add(connectorRightOperand);
			});
			Condition connectorConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.CONNECTOR, Operator.SAME_AS, connectorRightOperands, null);
			constraints.add(connectorConstraint);
			return true;
		}
		return false;
	}

	public boolean addApplicationCondition() {
		if (application != null && application.getInput().size() >0 && !application.getInput().get(0).equals("")) {
			ArrayList<RightOperand> applicationRightOperands = new ArrayList<>();
			application.getInput().forEach((e) -> {
				RightOperand applicationRightOperand = new RightOperand(e, RightOperandType.ANYURI);
				applicationRightOperands.add(applicationRightOperand);
			});
			Condition applicationConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.APPLICATION, Operator.SAME_AS,
					applicationRightOperands, null);
			constraints.add(applicationConstraint);
			return true;
		}
		return false;
	}

	public boolean addStateCondition() {
		if (state != null && state.getInput().size() >0 && !state.getInput().get(0).equals("")) {
			ArrayList<RightOperand> stateRightOperands = new ArrayList<>();
			state.getInput().forEach((e) -> {
				RightOperand stateRightOperand = new RightOperand(e, RightOperandType.STRING);
				stateRightOperands.add(stateRightOperand);
			});
			Condition stateConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.STATE, Operator.EQUALS,
					stateRightOperands, null);
			constraints.add(stateConstraint);
			return true;
		}
		return false;
	}

	public boolean addUserRoleCondition() {
		if (role != null && role.getInput().size() >0 && !role.getInput().get(0).equals("")) {
			ArrayList<RightOperand> userRoleRightOperands = new ArrayList<>();
			role.getInput().forEach((e) -> {
				RightOperand userRoleRightOperand = new RightOperand(e, RightOperandType.STRING);
				userRoleRightOperands.add(userRoleRightOperand);
			});
			Condition userRoleConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ROLE, Operator.EQUALS,
					userRoleRightOperands, null);
			constraints.add(userRoleConstraint);
			return true;
		}
		return false;
	}

	public boolean addSecurityLevelCondition() {
		if (securityLevel != null && securityLevel.getInput().size() >0 && !securityLevel.getInput().get(0).equals("")) {
			ArrayList<RightOperand> userSecurityLevelRightOperands = new ArrayList<>();
			securityLevel.getInput().forEach((e) -> {
				RightOperand userSecurityLevelRightOperand = new RightOperand(e, RightOperandType.STRING);
				userSecurityLevelRightOperands.add(userSecurityLevelRightOperand);
			});
			Condition userSecurityLevelConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.SECURITY_LEVEL, Operator.EQUALS,
					userSecurityLevelRightOperands, null);
			constraints.add(userSecurityLevelConstraint);
			return true;
		}
		return false;
	}

	public boolean addPurposeCondition() {
		if (purpose != null && purpose.getInput().size() >0 && !purpose.getInput().get(0).equals("")) {
			ArrayList<RightOperand> purposeRightOperands = new ArrayList<>();
			purpose.getInput().forEach((e) -> {
				RightOperand purposeRightOperand = new RightOperand(e, RightOperandType.ANYURI);
				purposeRightOperands.add(purposeRightOperand);
			});
			Condition purposeConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.PURPOSE, Operator.SAME_AS,
					purposeRightOperands, null);
			constraints.add(purposeConstraint);
			return true;
		}
		return false;
	}

	public boolean addEventCondition() {
		if (event != null && event.getInput().size() >0 && !event.getInput().get(0).equals("")) {
			ArrayList<RightOperand> eventRightOperands = new ArrayList<>();
			event.getInput().forEach((e) -> {
				RightOperand eventRightOperand = new RightOperand(e, RightOperandType.ANYURI);
				eventRightOperands.add(eventRightOperand);
			});
			Condition eventConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.EVENT, Operator.SAME_AS,
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

}
