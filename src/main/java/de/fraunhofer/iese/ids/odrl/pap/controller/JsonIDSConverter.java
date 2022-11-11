package de.fraunhofer.iese.ids.odrl.pap.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import de.fraunhofer.iese.ids.odrl.pap.util.PapUtil;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;

import de.fraunhofer.iese.ids.odrl.pap.services.PolicyService;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Action;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Condition;
import de.fraunhofer.iese.ids.odrl.policy.library.model.IdsPolicy;
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
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.RightOperandType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.RuleType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.TimeUnit;
import de.fraunhofer.iese.ids.odrl.policy.library.model.interfaces.IPolicy;

@Log4j2
public class JsonIDSConverter {
	private RecievedOdrlPolicy recievedOdrlPolicy;
	private List<Condition> constraints = new ArrayList<>();
	private List<Rule> rules = new ArrayList<>();
	private List<Rule> postDuties = new ArrayList<>();
	private List<Rule> preDuties = new ArrayList<>();
	
	public JsonIDSConverter(RecievedOdrlPolicy rp, RuleType rt, ActionType at) {
		this.recievedOdrlPolicy = rp;
		Action useAction = new Action(at);
		Rule rule = new Rule(rt, useAction);
		rule.setTarget(URI.create(rp.getTarget()));
		rules.add(rule);
	}
	
	public void addCondition(Condition c) {
		constraints.add(c);
	}
	
	public String createPolicy(String policyUID, PolicyService policyRepo) {
		rules.get(0).setConstraints((ArrayList<Condition>) constraints);
		if (postDuties.size() > 0) {
			rules.get(0).setPostduties((ArrayList<Rule>) postDuties);
		}
		if (preDuties.size() > 0) {
			rules.get(0).setPreduties((ArrayList<Rule>) preDuties);
		}
		IPolicy odrlPolicy = null;
		if (recievedOdrlPolicy.getLanguage().equals("IDS")) {
			odrlPolicy = new IdsPolicy();
		} else {
			odrlPolicy = new OdrlPolicy();
		}
		odrlPolicy.setConsumer(createConsumer());
		odrlPolicy.setRules((ArrayList<Rule>) rules);
		odrlPolicy.setPolicyId(URI.create(policyUID));
		odrlPolicy.setType(PolicyType.getFromString(recievedOdrlPolicy.getPolicyType()));
		// odrlPolicy.setTarget(URI.create(target));
		// odrlPolicy.setProvider(new Party(PartyType.CONSUMER, URI
		// .create(recievedPolicy.getProvider())));
		String jsonPolicyString = odrlPolicy.toString();

		//Map map = null;
		//boolean tempProviderSide = true;
		//String dtPolicy = OdrlTranslator.translate(map, tempProviderSide, odrlPolicy);
		// String dtPolicy = policy(jsonPolicy);
		
		/**JSONObject json = new JSONObject();
		try {
			json.put("jsonPolicy", new JSONObject(jsonPolicyString));
			//json.put("jsonPolicy", jsonPolicyString);
			json.put("dtPolicy", dtPolicy);
		} catch (JSONException e) {
			e.printStackTrace();
		}**/

		log.debug(jsonPolicyString);
		String response = new JSONObject(jsonPolicyString).toString(4);
		
		/*
		// Store the policy in the database
		Policy policy = new Policy();
		policy.setDescription(rp.getPolicyType());
		policy.setIDSPolicy(response);
		policy.setPolicyID(policyUID);
		policy.setPolicyType(queryOrigin);
		policy.setFieldValues(new JSONObject(rp).toString());
		try {
			policyRepo.save(policy);
		} catch (Exception e) {
		}
		*/
		return response;

	}
	
	private String checkIfEmptyValue(String value, String defaultValue) {
		if (value.length() > 0) {
			return value;
		} else {
			return defaultValue;
		}
	}
	
	private boolean multiInputField(List<String> field, String operator, String defaultOp, RightOperandType rot, ConditionType ct, LeftOperand lo) {
		if (!field.get(0).equals("")) {
			ArrayList<RightOperand> rightOperands = new ArrayList<>();
			field.forEach((e) -> {
				RightOperand rightOperand = new RightOperand(e, rot);
				rightOperands.add(rightOperand);
			});
			Condition constraint = new Condition(ct, lo,
					Operator.valueOf(checkIfEmptyValue(operator, defaultOp)), rightOperands, null);
			constraints.add(constraint);
			return true;
		}
		return false;
	}
	private boolean singleInputField(String field, String operator, String defaultOp, RightOperandType rot, ConditionType ct, LeftOperand lo) {
		if (!field.equals("")) {
			ArrayList<RightOperand> rightOperands = new ArrayList<>();
			RightOperand rightOperand = new RightOperand(field, rot);
			rightOperands.add(rightOperand);
			Condition constraint = new Condition(ct, lo,
					Operator.valueOf(checkIfEmptyValue(operator, defaultOp)), rightOperands, null);
			constraints.add(constraint);
			return true;
		}
		return false;
	}
	
	// policy components
		public boolean addLocationCondition() {
			RightOperandType rot = RightOperandType.ANYURI;
			ConditionType ct = ConditionType.CONSTRAINT;
			LeftOperand lo = LeftOperand.ABSOLUTE_SPATIAL_POSITION;
			return multiInputField(recievedOdrlPolicy.getLocation_input(), recievedOdrlPolicy.getLocation_op(),"SAME_AS", rot, ct, lo);
		}

		public boolean addSystemCondition() {
			RightOperandType rot = RightOperandType.ANYURI;
			ConditionType ct = ConditionType.CONSTRAINT;
			LeftOperand lo = LeftOperand.SYSTEM;
			return singleInputField(recievedOdrlPolicy.getSystem(), "SAME_AS","SAME_AS", rot, ct, lo);
		}

		public boolean addConnectorCondition() {
			RightOperandType rot = RightOperandType.ANYURI;
			ConditionType ct = ConditionType.CONSTRAINT;
			LeftOperand lo = LeftOperand.CONNECTOR;
			return multiInputField(recievedOdrlPolicy.getConnector_input(), recievedOdrlPolicy.getConnector_op(),"SAME_AS", rot, ct, lo);
		}

		public boolean addApplicationCondition() {
			RightOperandType rot = RightOperandType.ANYURI;
			ConditionType ct = ConditionType.CONSTRAINT;
			LeftOperand lo = LeftOperand.APPLICATION;
			return multiInputField(recievedOdrlPolicy.getApplication_input(), recievedOdrlPolicy.getApplication_op(),"SAME_AS", rot, ct, lo);
		}

		public boolean addStateCondition() {
			RightOperandType rot = RightOperandType.STRING;
			ConditionType ct = ConditionType.CONSTRAINT;
			LeftOperand lo = LeftOperand.STATE;
			return multiInputField(recievedOdrlPolicy.getState_input(), recievedOdrlPolicy.getState_op(),"EQUALS", rot, ct, lo);
		}

		public boolean addUserRoleCondition() {
			RightOperandType rot = RightOperandType.STRING;
			ConditionType ct = ConditionType.CONSTRAINT;
			LeftOperand lo = LeftOperand.ROLE;
			return multiInputField(recievedOdrlPolicy.getRole_input(), recievedOdrlPolicy.getRole_op(),"EQUALS", rot, ct, lo);
		}

		public boolean addSecurityLevelCondition() {
			RightOperandType rot = RightOperandType.STRING;
			ConditionType ct = ConditionType.CONSTRAINT;
			LeftOperand lo = LeftOperand.SECURITY_LEVEL;
			return multiInputField(recievedOdrlPolicy.getSecurityLevel_input(), recievedOdrlPolicy.getSecurityLevel_op(),"EQUALS", rot, ct, lo);
		}

		public boolean addPurposeCondition() {
			RightOperandType rot = RightOperandType.ANYURI;
			ConditionType ct = ConditionType.CONSTRAINT;
			LeftOperand lo = LeftOperand.PURPOSE;
			return multiInputField(recievedOdrlPolicy.getPurpose_input(), recievedOdrlPolicy.getPurpose_op(),"SAME_AS", rot, ct, lo);
		}

		public boolean addEventCondition() {
			RightOperandType rot = RightOperandType.ANYURI;
			ConditionType ct = ConditionType.CONSTRAINT;
			LeftOperand lo = LeftOperand.EVENT;
			return multiInputField(recievedOdrlPolicy.getEvent_input(), recievedOdrlPolicy.getEvent_op(),"SAME_AS", rot, ct, lo);
		}
		
		public boolean addTimeAndDate() {
			RightOperandType rot = RightOperandType.DATETIMESTAMP;
			ConditionType ct = ConditionType.REFINEMENT;
			LeftOperand lo = LeftOperand.DATE_TIME;
			return singleInputField(recievedOdrlPolicy.getTimeAndDate(), "BEFORE","BEFORE", rot, ct, lo);
		}
		/*
		private boolean PapUtil.isNotNullOrEmpty(String s) {
			return s != "" && s != null;
		}*/

/*		public boolean addRestrictTimeIntervalCondition() {
			if (PapUtil.isNotNullOrEmpty(recievedOdrlPolicy.getRestrictTimeIntervalStart()) && PapUtil.isNotNullOrEmpty(recievedOdrlPolicy.getRestrictTimeIntervalEnd())) {
				RightOperand startRightOperand = new RightOperand(recievedOdrlPolicy.getRestrictTimeIntervalStart(), RightOperandType.DATETIMESTAMP);
				ArrayList<RightOperand> startRightOperands = new ArrayList<>();
				startRightOperands.add(startRightOperand);
				Condition startCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.DATE_TIME,
						Operator.AFTER, startRightOperands, null);

				RightOperand endRightOperand = new RightOperand(recievedOdrlPolicy.getRestrictTimeIntervalEnd(), RightOperandType.DATETIMESTAMP);
				ArrayList<RightOperand> endRightOperands = new ArrayList<>();
				endRightOperands.add(endRightOperand);
				Condition endCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.DATE_TIME,
						Operator.BEFORE, endRightOperands, null);

				constraints.add(startCondition);
				constraints.add(endCondition);
				return true;
			}
			return false;
		}*/

		public boolean addRestrictStartTimeCondition() {
			if (PapUtil.isNotNullOrEmpty(recievedOdrlPolicy.getRestrictStartTime())) {
				RightOperand dateTimeRightOperand = new RightOperand(recievedOdrlPolicy.getRestrictStartTime(), RightOperandType.DATETIMESTAMP);

				ArrayList<RightOperand> rightOperands = new ArrayList<>();
				rightOperands.add(dateTimeRightOperand);
				Condition dateTimeCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.DATE_TIME,
						Operator.AFTER, rightOperands, null);
				constraints.add(dateTimeCondition);

				return true;
			}
			return false;
		}
		
		public boolean addRestrictEndTimeCondition() {
			if (PapUtil.isNotNullOrEmpty(recievedOdrlPolicy.getRestrictEndTime())) {

				RightOperand dateTimeRightOperand = new RightOperand(recievedOdrlPolicy.getRestrictEndTime(), RightOperandType.DATETIMESTAMP);

				ArrayList<RightOperand> rightOperands = new ArrayList<>();
				rightOperands.add(dateTimeRightOperand);
				Condition dateTimeCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.DATE_TIME,
						Operator.BEFORE, rightOperands, null);
				constraints.add(dateTimeCondition);
				return true;
			}
			return false;
		}

		public boolean addPaymentCondition() {
			if (PapUtil.isNotNullOrEmpty(recievedOdrlPolicy.getPayment())) {
				RightOperand paymentRightOperand = new RightOperand(String.valueOf(recievedOdrlPolicy.getPrice()), RightOperandType.DOUBLE);
				ArrayList<RightOperand> paymentRightOperands = new ArrayList<>();
				paymentRightOperands.add(paymentRightOperand);
				Condition paymentCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.PAY_AMOUNT, Operator.EQ,
						paymentRightOperands, null);
				paymentCondition.setUnit("http://dbpedia.org/resource/Euro");
				paymentCondition.setContract(recievedOdrlPolicy.getPayment());
				constraints.add(paymentCondition);
				return true;
			}
			return false;
		}

		public boolean addElapsedTimeRightOperand() {
			ArrayList<RightOperandEntity> durationEntities = new ArrayList<>();
			if (recievedOdrlPolicy.getSpecifyBeginTime() != "") {
				RightOperandEntity beginInnerEntity = new RightOperandEntity(EntityType.DATETIME, recievedOdrlPolicy.getSpecifyBeginTime(),
						RightOperandType.DATETIMESTAMP);
				RightOperandEntity beginEntity = new RightOperandEntity(EntityType.BEGIN, beginInnerEntity,
						RightOperandType.INSTANT);
				durationEntities.add(beginEntity);
			}

			String hour = "";
			String day = "";
			String month = "";
			String year = "";
			if (recievedOdrlPolicy.getDurationHour() != null && !recievedOdrlPolicy.getDurationHour().isEmpty()) {
				hour = "T" + recievedOdrlPolicy.getDurationHour() + TimeUnit.HOURS.getOdrlXsdDuration();
			}
			if (recievedOdrlPolicy.getDurationDay() != null && !recievedOdrlPolicy.getDurationDay().isEmpty()) {
				day = recievedOdrlPolicy.getDurationDay() + TimeUnit.DAYS.getOdrlXsdDuration();
			}
			if (recievedOdrlPolicy.getDurationMonth() != null && !recievedOdrlPolicy.getDurationMonth().isEmpty()) {
				month = recievedOdrlPolicy.getDurationMonth() + TimeUnit.MONTHS.getOdrlXsdDuration();
			}
			if (recievedOdrlPolicy.getDurationYear() != null && !recievedOdrlPolicy.getDurationYear().isEmpty()) {
				year = recievedOdrlPolicy.getDurationYear() + TimeUnit.YEARS.getOdrlXsdDuration();
			}
			String duration = "P" + year + month + day + hour;

			if (!duration.equals("P")) {
				RightOperandEntity hasDurationEntity = new RightOperandEntity(EntityType.HASDURATION, duration,
						RightOperandType.DURATION);
				// hasDurationEntity.setTimeUnit(TimeUnit.valueOf(restrictTimeDurationUnit));
				durationEntities.add(hasDurationEntity);
			}

			if (durationEntities.size() > 0) {
				RightOperand elapsedTimeRightOperand = new RightOperand(durationEntities, RightOperandType.DURATIONENTITY);
				ArrayList<RightOperand> elapsedTimeRightOperands = new ArrayList<>();
				elapsedTimeRightOperands.add(elapsedTimeRightOperand);
				Condition elapsedTimeConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ELAPSED_TIME,
						Operator.SHORTER_EQ, elapsedTimeRightOperands, null);
				constraints.add(elapsedTimeConstraint);
				return true;
			}
			return false;

		}

		public boolean addCounterCondition() {
			if (PapUtil.isNotNullOrEmpty(recievedOdrlPolicy.getCounter())) {
				RightOperand rightOperand = new RightOperand(recievedOdrlPolicy.getCounter(), RightOperandType.DECIMAL);
				ArrayList<RightOperand> rightOperands = new ArrayList<>();
				rightOperands.add(rightOperand);
				Condition countCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.COUNT, Operator.LTEQ,
						rightOperands, null);
				constraints.add(countCondition);
				return true;
			}
			return false;
		}



		public boolean addTime() {
			if (PapUtil.isNotNullOrEmpty(recievedOdrlPolicy.getTime())) {
				RightOperand delayPeriodRightOperand = new RightOperand(recievedOdrlPolicy.getTime(), RightOperandType.DURATION);
				ArrayList<RightOperand> delayPeriodRightOperands = new ArrayList<>();
				delayPeriodRightOperands.add(delayPeriodRightOperand);
				Condition delayPeriodRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.DELAY,
						Operator.DURATION_EQ, delayPeriodRightOperands, null);
				delayPeriodRefinement.setUnit(TimeUnit.valueOf(recievedOdrlPolicy.getTimeUnit()).toString());
				constraints.add(delayPeriodRefinement);
				return true;
			}
			return false;
		}
		
		public void addRuleDistributeData() {
			if (PapUtil.isNotNullOrEmpty(recievedOdrlPolicy.getArtifactState()) && PapUtil.isNotNullOrEmpty(recievedOdrlPolicy.getPolicy())) {
				RightOperand dutyRightOperand = new RightOperand();
				dutyRightOperand.setType(RightOperandType.ANYURI);
				dutyRightOperand.setValue(recievedOdrlPolicy.getPolicy());
				ArrayList<RightOperand> dutyRightOperands = new ArrayList<>();
				dutyRightOperands.add(dutyRightOperand);
				Condition thirdPartyRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.TARGET_POLICY,
						Operator.DEFINES_AS, dutyRightOperands, "");
				ArrayList<Condition> dutyRefinements = new ArrayList<>();
				dutyRefinements.add(thirdPartyRefinement);
		
				RightOperand rightOperand = new RightOperand();
				rightOperand.setType(RightOperandType.STRING);
				rightOperand.setValue(recievedOdrlPolicy.getArtifactState());
				ArrayList<RightOperand> rightOperands = new ArrayList<>();
				rightOperands.add(rightOperand);
				Condition artifactStateConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ARTIFACT_STATE,
						Operator.EQUALS, rightOperands, "");
				ArrayList<Condition> constraints = new ArrayList<>();
				constraints.add(artifactStateConstraint);
		
				Action distributeAction = new Action(ActionType.DISTRIBUTE);
				Action nextPolicyDutyAction = new Action(ActionType.NEXT_POLICY);
				nextPolicyDutyAction.setRefinements(dutyRefinements);
				Rule rule = new Rule(RuleType.PERMISSION, distributeAction);
				rule.setTarget(URI.create(recievedOdrlPolicy.getTarget()));
				Rule preDuties = new Rule(RuleType.PREDUTY, nextPolicyDutyAction);
				ArrayList<Rule> preDutiess = new ArrayList<>();
				preDutiess.add(preDuties);
				rule.setPreduties(preDutiess);
				rule.setConstraints(constraints);
				rules.add(rule);
			}
		}
		
		public void distributeData() {	
			if (PapUtil.isNotNullOrEmpty(recievedOdrlPolicy.getArtifactState()) && PapUtil.isNotNullOrEmpty(recievedOdrlPolicy.getPolicy())) {
				RightOperand dutyRightOperand = new RightOperand();
				dutyRightOperand.setType(RightOperandType.ANYURI);
				dutyRightOperand.setValue(recievedOdrlPolicy.getPolicy());
				ArrayList<RightOperand> dutyRightOperands = new ArrayList<>();
				dutyRightOperands.add(dutyRightOperand);
				Condition thirdPartyRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.TARGET_POLICY,
						Operator.DEFINES_AS, dutyRightOperands, "");
				ArrayList<Condition> dutyRefinements = new ArrayList<>();
				dutyRefinements.add(thirdPartyRefinement);
		
				RightOperand rightOperand = new RightOperand();
				rightOperand.setType(RightOperandType.STRING);
				rightOperand.setValue(recievedOdrlPolicy.getArtifactState());
				ArrayList<RightOperand> rightOperands = new ArrayList<>();
				rightOperands.add(rightOperand);
				Condition artifactStateConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ARTIFACT_STATE,
						Operator.EQUALS, rightOperands, "");
				this.constraints.add(artifactStateConstraint);
		
				Action nextPolicyDutyAction = new Action(ActionType.NEXT_POLICY);
				nextPolicyDutyAction.setRefinements(dutyRefinements);
				//rule.setTarget(URI.create(rp.getTarget()));
				Rule preDuties = new Rule(RuleType.PREDUTY, nextPolicyDutyAction);
				this.preDuties.add(preDuties);
			}
		}

		private Party createConsumer() {
			Party consumer = null;
			try {
				consumer = new Party(PartyType.CONSUMER, new URI(recievedOdrlPolicy.getConsumer()));
				consumer.setType(PartyType.CONSUMER);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			return consumer;
		}

		public void logDuty(String logLevel, String systemDevice, RuleType type) {
			RightOperand logLevelRightOperand = new RightOperand();
			logLevelRightOperand.setType(RightOperandType.STRING);
			logLevelRightOperand.setValue(logLevel);
			ArrayList<RightOperand> logLevelRightOperands = new ArrayList<>();
			logLevelRightOperands.add(logLevelRightOperand);
			Condition logLevelRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.LOG_LEVEL,
					Operator.DEFINES_AS, logLevelRightOperands, "");

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
			postDuties.add(postobligation);
		}

		public void informDuty(String notificationLevel, String informedParty, RuleType type) {
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
			Condition recipientRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.RECIPIENT,
					Operator.DEFINES_AS, rightOperands, "");
			ArrayList<Condition> refinements = new ArrayList<>();
			refinements.add(notificationLevelRefinement);
			refinements.add(recipientRefinement);
			Action notifyDutyAction = new Action(ActionType.NOTIFY);
			notifyDutyAction.setRefinements(refinements);
			Rule postobligation = new Rule(type, notifyDutyAction);
			postDuties.add(postobligation);
		}

		public void deleteDuty(RuleType type) {
			String timeAndDate = recievedOdrlPolicy.getPostduties_timeAndDate();
			String durationYear = recievedOdrlPolicy.getPostduties_durationYear();
			String durationMonth = recievedOdrlPolicy.getPostduties_durationMonth();
			String durationDay = recievedOdrlPolicy.getPostduties_durationDay();
			String durationHour = recievedOdrlPolicy.getPostduties_durationHour();
			ArrayList<Condition> refinements = new ArrayList<>();
			if (timeAndDate != "") {
				RightOperandEntity dateTimeEntity = new RightOperandEntity(EntityType.DATETIME, timeAndDate,
						RightOperandType.DATETIMESTAMP);
				ArrayList<RightOperandEntity> entities = new ArrayList<>();
				entities.add(dateTimeEntity);
				RightOperand rightOperand = new RightOperand(entities, RightOperandType.INSTANT);
				ArrayList<RightOperand> rightOperands = new ArrayList<>();
				rightOperands.add(rightOperand);
				Condition timeIntervalCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.DATE_TIME,
						Operator.BEFORE, rightOperands, null);
				refinements.add(timeIntervalCondition);
			} else {
				ArrayList<RightOperandEntity> durationEntities = new ArrayList<>();

				String hour = "";
				String day = "";
				String month = "";
				String year = "";
				if (durationHour != null && !durationHour.isEmpty()) {
					hour = "T" + durationHour + TimeUnit.HOURS.getOdrlXsdDuration();
				}
				if (durationDay != null && !durationDay.isEmpty()) {
					day = durationDay + TimeUnit.DAYS.getOdrlXsdDuration();
				}
				if (durationMonth != null && !durationMonth.isEmpty()) {
					month = durationMonth + TimeUnit.MONTHS.getOdrlXsdDuration();
				}
				if (durationYear != null && !durationYear.isEmpty()) {
					year = durationYear + TimeUnit.YEARS.getOdrlXsdDuration();
				}
				String duration = "P" + year + month + day + hour;

				if (duration.equals("P")) {
					// set initial delay value
					duration = "P0D";
				}

				RightOperandEntity hasDurationEntity = new RightOperandEntity(EntityType.HASDURATION, duration,
						RightOperandType.DURATION);
				durationEntities.add(hasDurationEntity);
				ArrayList<RightOperand> rightOperands = new ArrayList<>();
				RightOperand rightOperand = new RightOperand(durationEntities, RightOperandType.DURATIONENTITY);
				rightOperands.add(rightOperand);
				Condition delayPeriodRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.DELAY,
						Operator.DURATION_EQ, rightOperands, "");
				refinements.add(delayPeriodRefinement);
			}

			Action deleteDutyAction = new Action(ActionType.DELETE);
			deleteDutyAction.setRefinements(refinements);
			Rule postobligation = new Rule(type, deleteDutyAction);
			postDuties.add(postobligation);
		}

		public void anonymizeInTransit(String fieldToChange, String valueToChange, String modifier) {
			ArrayList<Condition> refinements = new ArrayList<>();

			if (valueToChange != "") {
				RightOperand replaceWithRightOperand = new RightOperand();
				replaceWithRightOperand.setValue(valueToChange);
				replaceWithRightOperand.setType(RightOperandType.STRING);
				ArrayList<RightOperand> replaceWithRightOperands = new ArrayList<>();
				replaceWithRightOperands.add(replaceWithRightOperand);
				Condition replaceWithRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.REPLACE_WITH,
						Operator.DEFINES_AS, replaceWithRightOperands, null);
				refinements.add(replaceWithRefinement);
			}

			RightOperand subsetSpecificationRightOperand = new RightOperand();
			subsetSpecificationRightOperand.setValue(fieldToChange);
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
			preDuties.add(preDuty);
		}
		public void addCounterToPostduties() {
			if (recievedOdrlPolicy.getCounter() != "") {
				Action countDutyAction = new Action(ActionType.INCREMENT_COUNTER);
				Rule postobligation = new Rule(RuleType.POSTDUTY, countDutyAction);
				postDuties.add(postobligation);
			}
		}
		
		public void addPostDuties() {
			if (PapUtil.isNotNullOrEmpty(recievedOdrlPolicy.getPostduties_logLevel()) && PapUtil.isNotNullOrEmpty(recievedOdrlPolicy.getPostduties_systemDevice())) {
				logDuty(recievedOdrlPolicy.getPostduties_logLevel(), recievedOdrlPolicy.getPostduties_systemDevice(), RuleType.POSTDUTY);
			}
			if (PapUtil.isNotNullOrEmpty(recievedOdrlPolicy.getPostduties_notificationLevel()) && PapUtil.isNotNullOrEmpty(recievedOdrlPolicy.getPostduties_informedParty())) {
				informDuty(recievedOdrlPolicy.getPostduties_notificationLevel(), recievedOdrlPolicy.getPostduties_informedParty(), RuleType.POSTDUTY);
			}
			if (PapUtil.isNotNullOrEmpty(recievedOdrlPolicy.getPostduties_timeAndDate())
					|| (PapUtil.isNotNullOrEmpty(recievedOdrlPolicy.getPostduties_durationYear()) && PapUtil.isNotNullOrEmpty(recievedOdrlPolicy.getPostduties_durationMonth())
							&& PapUtil.isNotNullOrEmpty(recievedOdrlPolicy.getPostduties_durationDay()) && PapUtil.isNotNullOrEmpty(recievedOdrlPolicy.getPostduties_durationHour()))) {
				deleteDuty(RuleType.POSTDUTY);
			}
		}
		
		public void addDeletePolicyAfterUsage() {
			deleteDuty(RuleType.POSTDUTY);
		}
		
		public void addDeletePolicyAfterUsagePeriod() {
			Action deleteDutyAction = new Action(ActionType.DELETE);
			Rule postobligation = new Rule(RuleType.POSTDUTY, deleteDutyAction);
			postDuties.add(postobligation);
		}

		public void addPreDuties() {
			if (recievedOdrlPolicy.getPreduties_modifier() != "" && recievedOdrlPolicy.getPreduties_fieldToChange() != "") {
				anonymizeInTransit(recievedOdrlPolicy.getPreduties_fieldToChange(), recievedOdrlPolicy.getPreduties_valueToChange(), recievedOdrlPolicy.getPreduties_modifier());
			}
			
			if (recievedOdrlPolicy.getPreduties_anomInRest() != "") {
				anonymizeInRest();
			}
		}

		private void anonymizeInRest() {
			Action anonymizeAction = new Action(ActionType.ANONYMIZE);
			Rule rule = new Rule(RuleType.OBLIGATION, anonymizeAction);
			//rule.setTarget(URI.create());
			preDuties.add(rule);
		}
		
}
