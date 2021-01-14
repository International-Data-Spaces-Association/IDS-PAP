package de.fraunhofer.iese.ids.odrl.pap.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import de.fraunhofer.iese.ids.odrl.pap.util.OdrlCreator;
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
	private String location;
	private String system;
	private String purpose;
	private String event;
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
	private String modificator;
	private String fieldToChange;
	private String restrictTimeIntervalStart;
	private String restrictTimeIntervalEnd;
	private int numberOfUsage = -1;
	private String specifyBeginTime;
	private String restrictTimeDuration;
	private String restrictTimeDurationUnit;
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

	public String getLocation() {
		return location;
	}

	public String getSystem() {
		return system;
	}

	public String getPurpose() {
		return purpose;
	}

	public String getEvent() {
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

	public String getModificator() {
		return modificator;
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

	public int getNumberOfUsage() {
		return numberOfUsage;
	}

	public String getSpecifyBeginTime() {
		return specifyBeginTime;
	}

	public String getRestrictTimeDuration() {
		return restrictTimeDuration;
	}

	public String getRestrictTimeDurationUnit() {
		return restrictTimeDurationUnit;
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
		if (location != "") {
			RightOperand locationRightOperand = new RightOperand(location, RightOperandType.ANYURI);
			Condition locationConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ABSOLUTESPATIALPOSITION,
					Operator.EQ, locationRightOperand, "");
			constraints.add(locationConstraint);
			return true;
		}
		return false;
	}

	public boolean addSystemCondition() {
		if (system != "") {
			RightOperand systemRightOperand = new RightOperand(system, RightOperandType.ANYURI);
			systemRightOperand.setType(RightOperandType.ANYURI);
			Condition systemConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.SYSTEM, Operator.EQ,
					systemRightOperand, "");
			constraints.add(systemConstraint);
			return true;
		}
		return false;
	}

	public boolean addPurposeCondition() {
		if (purpose != "") {
			RightOperand purposeRightOperand = new RightOperand(purpose, RightOperandType.ANYURI);
			Condition purposeConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.PURPOSE, Operator.EQ,
					purposeRightOperand, "");
			constraints.add(purposeConstraint);
			return true;
		}
		return false;
	}

	public boolean addEventCondition() {
		if (event != "") {
			RightOperand eventRightOperand = new RightOperand(event, RightOperandType.ANYURI);
			Condition eventConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.EVENT, Operator.EQ,
					eventRightOperand, "");
			constraints.add(eventConstraint);
			return true;
		}
		return false;
	}

	public boolean addRestrictTimeIntervalCondition() {
		if (restrictTimeIntervalStart != "") {
			RightOperand rightOperand = new RightOperand();
			rightOperand.setType(RightOperandType.INTERVAL);
			RightOperandEntity startEntity = new RightOperandEntity(EntityType.BEGIN, restrictTimeIntervalStart,
					RightOperandType.DATETIMESTAMP);
			RightOperandEntity endEntity = new RightOperandEntity(EntityType.BEGIN, restrictTimeIntervalEnd,
					RightOperandType.DATETIMESTAMP);
			endEntity.setEntityType(EntityType.END);
			endEntity.setDataType(RightOperandType.DATETIMESTAMP);
			ArrayList<RightOperandEntity> entities = new ArrayList<>();
			entities.add(startEntity);
			entities.add(endEntity);
			rightOperand.setEntities(entities);
			Condition timeIntervalCondition = new Condition(ConditionType.CONSTRAINT,
					LeftOperand.POLICY_EVALUATION_TIME, Operator.TEMPORAL_EQUALS, rightOperand, "");
			constraints.add(timeIntervalCondition);
			return true;
		}
		return false;
	}

	public boolean addPaymentCondition() {
		if (payment != "") {
			RightOperand paymentRightOperand = new RightOperand(String.valueOf(price), RightOperandType.DOUBLE);
			Condition paymentCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.PAY_AMOUNT, Operator.EQ,
					paymentRightOperand, "");
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
			RightOperandEntity beginEntity = new RightOperandEntity(EntityType.BEGIN, specifyBeginTime, RightOperandType.DATETIMESTAMP);
			durationEntities.add(beginEntity);
		}

		if (restrictTimeDuration != "") {
			RightOperandEntity hasDurationEntity = new RightOperandEntity(EntityType.HASDURATION,restrictTimeDuration  ,RightOperandType.DURATION);
			hasDurationEntity.setTimeUnit(TimeUnit.valueOf(restrictTimeDurationUnit));
			durationEntities.add(hasDurationEntity);

		}
		if (durationEntities.size() >0) {
			elapsedTimeRightOperand.setEntities(durationEntities);
			Condition elapsedTimeConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ELAPSED_TIME, Operator.SHORTER_EQ, elapsedTimeRightOperand, "");
			constraints.add(elapsedTimeConstraint);
			return true;
		}return false;

	}

	public boolean addCounterCondition() {
		if (counter != "") {
			RightOperand rightOperand = new RightOperand(counter, RightOperandType.DECIMAL);
			Condition countCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.COUNT, Operator.LTEQ, rightOperand, "");
			constraints.add(countCondition);
			return true;
		}
		return false;
	}

	public boolean addTimeAndDate() {
		if (timeAndDate != "") {
			RightOperand dateTimeRightOperand = new RightOperand(timeAndDate, RightOperandType.DATETIMESTAMP);
			Condition dateTimeRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.DATE_TIME,
					Operator.BEFORE, dateTimeRightOperand, "");
			constraints.add(dateTimeRefinement);
			return true;
		}
		return false;
	}

	public boolean addTime() {
		if (time != "") {
			RightOperand delayPeriodRightOperand = new RightOperand(time, RightOperandType.DURATION);
			Condition delayPeriodRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.DELAY,
					Operator.DURATION_EQ, delayPeriodRightOperand, "");
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
		odrlPolicy.setTarget(URI.create(target));
		// odrlPolicy.setProvider(new Party(PartyType.CONSUMER, URI
		// .create(recievedPolicy.getProvider())));
		String jsonPolicy = OdrlCreator.createODRL(odrlPolicy);
		// String dtPolicy = policy(jsonPolicy);
		String dtPolicy = "dd";
		String policies = jsonPolicy + "DTPOLICY:" + dtPolicy;
		return policies;
	}

}
