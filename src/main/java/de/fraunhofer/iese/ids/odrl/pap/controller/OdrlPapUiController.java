/**
 * 
 */
package de.fraunhofer.iese.ids.odrl.pap.controller;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.fraunhofer.iese.ids.odrl.policy.library.model.*;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.jsonldjava.utils.JsonUtils;

import de.fraunhofer.iese.ids.odrl.pap.model.JsonOdrlPolicy;
import de.fraunhofer.iese.ids.odrl.pap.util.OdrlCreator;
import de.fraunhofer.iese.ids.odrl.pap.util.OdrlTranslator;
import de.fraunhofer.iese.ids.odrl.pap.util.TransformPolicy;
import de.fraunhofer.iese.ids.odrl.pap.util.UcAppService;
import de.fraunhofer.iese.ids.odrl.policy.library.model.tooling.IdsOdrlUtil;

@Controller
public class OdrlPapUiController {
	
	public static final String POLICY_FRAGMENT = "policyFragment";
	
	  @RequestMapping("/")
	  public String index(Model model) {
		  model.addAttribute(POLICY_FRAGMENT, "index");
	  	return "index";
	  }

	@RequestMapping("/policy/ProvideAccessPolicyForm")
	public String accessPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/restrict-access"));
		model.addAttribute(POLICY_FRAGMENT, "BasePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InhibitAccessPolicyForm")
	public String inhibitPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PROHIBITION, useAction);

		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/restrict-access"));
		model.addAttribute(POLICY_FRAGMENT, "BasePolicyForm");
		return "index";
	}

	  @RequestMapping("/policy/SpecificPurposePolicyForm")
	  public String providePurposePolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		  RightOperand rightOperand = new RightOperand();
		  rightOperand.setType(RightOperandType.ANYURI);
		  Condition purposeConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.PURPOSE, Operator.EQ, rightOperand, "");
		  ArrayList<Condition> constraints = new ArrayList<>();
		  constraints.add(purposeConstraint);
		  Action useAction = new Action(ActionType.USE);
		  Rule rule = new Rule(RuleType.PERMISSION, useAction);
		  rule.setConstraints(constraints);
		  ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		  Party consumer = new Party();
		  consumer.setType(PartyType.CONSUMER);
		  odrlPolicy.setConsumer(consumer);
		  odrlPolicy.setRules(rules);
		  odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/restrict-access-purpose"));
		  model.addAttribute(POLICY_FRAGMENT, "SpecificPurposePolicyForm");
	    return "index";
	  }

	@RequestMapping("/policy/InhibitSpecificPurposePolicyForm")
	public String inhibitPurposePolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		Condition purposeConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.PURPOSE, Operator.EQ, rightOperand, "");
		ArrayList<Condition> constraints = new ArrayList<>();
		constraints.add(purposeConstraint);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PROHIBITION, useAction);
		rule.setConstraints(constraints);
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/restrict-access-purpose"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificPurposePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/SpecificSystemPolicyForm")
	public String provideSystemPolicy(@ModelAttribute OdrlPolicy odrlPolicy,  Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		Condition systemConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.SYSTEM, Operator.EQ, rightOperand, "");
		ArrayList<Condition> constraints = new ArrayList<>();
		constraints.add(systemConstraint);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setConstraints(constraints);
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/restrict-access-system"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificSystemPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InhibitSpecificSystemPolicyForm")
	public String inhibitSystemPolicy(@ModelAttribute OdrlPolicy odrlPolicy,  Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		Condition systemConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.SYSTEM, Operator.EQ, rightOperand, "");
		ArrayList<Condition> constraints = new ArrayList<>();
		constraints.add(systemConstraint);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PROHIBITION, useAction);
		rule.setConstraints(constraints);
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/restrict-access-system"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificSystemPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/SpecificLocationPolicyForm")
	public String provideLocationPolicy(@ModelAttribute OdrlPolicy odrlPolicy,  Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		Condition locationConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ABSOLUTESPATIALPOSITION, Operator.EQ, rightOperand, "");
		ArrayList<Condition> constraints = new ArrayList<>();
		constraints.add(locationConstraint);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setConstraints(constraints);
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/restrict-access-location"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificLocationPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InhibitSpecificLocationPolicyForm")
	public String inhibitLocationPolicy(@ModelAttribute OdrlPolicy odrlPolicy,  Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		Condition locationConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ABSOLUTESPATIALPOSITION, Operator.EQ, rightOperand, "");
		ArrayList<Condition> constraints = new ArrayList<>();
		constraints.add(locationConstraint);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PROHIBITION, useAction);
		rule.setConstraints(constraints);
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/restrict-access-location"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificLocationPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/SpecificEventPolicyForm")
	public String provideEventPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {

		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		Condition eventConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.EVENT, Operator.EQ, rightOperand, "");
		ArrayList<Condition> constraints = new ArrayList<>();
		constraints.add(eventConstraint);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setConstraints(constraints);
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/restrict-access-event"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificEventPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InhibitSpecificEventPolicyForm")
	public String inhibitEventPolicy(@ModelAttribute OdrlPolicy odrlPolicy,  Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		Condition eventConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.EVENT, Operator.EQ, rightOperand, "");
		ArrayList<Condition> constraints = new ArrayList<>();
		constraints.add(eventConstraint);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PROHIBITION, useAction);
		rule.setConstraints(constraints);
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/restrict-access-event"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificEventPolicyForm");
		return "index";
	}

	/*  @RequestMapping("/policy/DeleteAfterUsagePeriodPolicyForm")
	  public String deletePolicyAfterUsagePeriod(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		  RightOperand delayPeriodRightOperand = new RightOperand();
		  delayPeriodRightOperand.setType(RightOperandType.DURATIONENTITY);
		  RightOperandEntity hasDurationEntity = new RightOperandEntity();
		  hasDurationEntity.setEntityType(EntityType.HASDURATION);
		  hasDurationEntity.setDataType(RightOperandType.DURATION);
		  hasDurationEntity.setTimeUnit(TimeUnit.HOURS);
		  ArrayList<RightOperandEntity> durationEntities = new ArrayList<>();
		  durationEntities.add(hasDurationEntity);
		  delayPeriodRightOperand.setEntities(durationEntities);
		  Condition delayPeriodRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.DELAY, Operator.DURATION_EQ, delayPeriodRightOperand, "");
		  RightOperand dateTimeRightOperand = new RightOperand();
		  dateTimeRightOperand.setType(RightOperandType.DATETIMESTAMP);
		  Condition dateTimeRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.DATE_TIME, Operator.BEFORE, dateTimeRightOperand, "");
		  ArrayList<Condition> refinements = new ArrayList<>();
		  refinements.add(delayPeriodRefinement);
		  refinements.add(dateTimeRefinement);
		  Action deleteAction = new Action(ActionType.DELETE);
		  deleteAction.setRefinements(refinements);
		  Rule rule = new Rule(RuleType.OBLIGATION, deleteAction);
		  ArrayList<Rule> rules = new ArrayList<>();
		  rules.add(rule);
		  Party consumer = new Party();
		  consumer.setType(PartyType.CONSUMER);
		  odrlPolicy.setConsumer(consumer);
		  odrlPolicy.setRules(rules);
		  odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/delete-data"));
	  	model.addAttribute(POLICY_FRAGMENT, "DeleteAfterUsagePeriodPolicyForm");
	    return "index";
	  }*/

	@RequestMapping("/policy/DeleteAfterUsagePolicyForm")
	public String deletePolicyAfterUsage(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand delayPeriodRightOperand = new RightOperand();
		delayPeriodRightOperand.setType(RightOperandType.DURATIONENTITY);
		RightOperandEntity hasDurationEntity = new RightOperandEntity();
		hasDurationEntity.setEntityType(EntityType.HASDURATION);
		hasDurationEntity.setDataType(RightOperandType.DURATION);
		hasDurationEntity.setTimeUnit(TimeUnit.HOURS);
		ArrayList<RightOperandEntity> durationEntities = new ArrayList<>();
		durationEntities.add(hasDurationEntity);
		delayPeriodRightOperand.setEntities(durationEntities);
		Condition delayPeriodRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.DELAY, Operator.DURATION_EQ, delayPeriodRightOperand, "");
		RightOperand dateTimeRightOperand = new RightOperand();
		dateTimeRightOperand.setType(RightOperandType.DATETIMESTAMP);
		Condition dateTimeRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.DATE_TIME, Operator.BEFORE, dateTimeRightOperand, "");
		ArrayList<Condition> refinements = new ArrayList<>();
		refinements.add(delayPeriodRefinement);
		refinements.add(dateTimeRefinement);

	  	Action useAction = new Action(ActionType.USE);
		Action deleteDutyAction = new Action(ActionType.DELETE);
		deleteDutyAction.setRefinements(refinements);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		Rule postobligation = new Rule(RuleType.POSTDUTY, deleteDutyAction);
		ArrayList<Rule> postDuties = new ArrayList<>();
		postDuties.add(postobligation);
		rule.setPostduties(postDuties);
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/delete-after-usage"));
		model.addAttribute(POLICY_FRAGMENT, "DeleteAfterUsagePolicyForm");
		return "index";
	}

	  @RequestMapping("/policy/ReadDataIntervalPolicyForm")
	  public String provideInterval(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		  RightOperand elapsedTimeRightOperand = new RightOperand();
		  elapsedTimeRightOperand.setType(RightOperandType.DURATIONENTITY);
		  RightOperandEntity beginEntity = new RightOperandEntity();
		  beginEntity.setEntityType(EntityType.BEGIN);
		  beginEntity.setDataType(RightOperandType.DATETIMESTAMP);
		  RightOperandEntity hasDurationEntity = new RightOperandEntity();
		  hasDurationEntity.setEntityType(EntityType.HASDURATION);
		  hasDurationEntity.setDataType(RightOperandType.DURATION);
		  hasDurationEntity.setTimeUnit(TimeUnit.HOURS);
		  ArrayList<RightOperandEntity> durationEntities = new ArrayList<>();
		  durationEntities.add(beginEntity);
		  durationEntities.add(hasDurationEntity);
		  elapsedTimeRightOperand.setEntities(durationEntities);
		  Condition elapsedTimeConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ELAPSED_TIME, Operator.SHORTER_EQ, elapsedTimeRightOperand, "");

		  RightOperand rightOperand = new RightOperand();
		  rightOperand.setType(RightOperandType.INTERVAL);
		  RightOperandEntity startEntity = new RightOperandEntity();
		  startEntity.setEntityType(EntityType.BEGIN);
		  startEntity.setDataType(RightOperandType.DATETIMESTAMP);
		  RightOperandEntity endEntity = new RightOperandEntity();
		  endEntity.setEntityType(EntityType.END);
		  endEntity.setDataType(RightOperandType.DATETIMESTAMP);
		  ArrayList<RightOperandEntity> entities = new ArrayList<>();
		  entities.add(startEntity);
		  entities.add(endEntity);
		  rightOperand.setEntities(entities);
		  Condition timeIntervalCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.POLICY_EVALUATION_TIME, Operator.TEMPORAL_EQUALS, rightOperand, "");
		  ArrayList<Condition> constraints = new ArrayList<>();
		  constraints.add(elapsedTimeConstraint);
		  constraints.add(timeIntervalCondition);
		  Action useAction = new Action(ActionType.USE);
		  Rule rule = new Rule(RuleType.PERMISSION, useAction);
		  rule.setConstraints(constraints);
		  ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		  Party consumer = new Party();
		  consumer.setType(PartyType.CONSUMER);
		  odrlPolicy.setConsumer(consumer);
		  odrlPolicy.setRules(rules);
		  odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/restrict-access-interval"));
		  model.addAttribute(POLICY_FRAGMENT, "ReadDataIntervalPolicyForm");
	    return "index";
	  }

	@RequestMapping("/policy/InhibitReadDataIntervalPolicyForm")
	public String inhibitInterval(@ModelAttribute OdrlPolicy odrlPolicy,  Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.DATETIMESTAMP);
		Condition timeIntervalCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.POLICY_EVALUATION_TIME, Operator.EQ, rightOperand, "");
		ArrayList<Condition> constraints = new ArrayList<>();
		constraints.add(timeIntervalCondition);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PROHIBITION, useAction);
		rule.setConstraints(constraints);
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/restrict-access-interval"));
		model.addAttribute(POLICY_FRAGMENT, "ReadDataIntervalPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/ReadAfterPaymentPolicyForm")
	public String provideAfterPayment(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.DOUBLE);
		Condition paymentCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.PAY_AMOUNT, Operator.EQ, rightOperand, "");
		paymentCondition.setUnit("http://dbpedia.org/resource/Euro");
		ArrayList<Condition> constraints = new ArrayList<>();
		constraints.add(paymentCondition);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setConstraints(constraints);
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/provide-access-after-payment"));
		model.addAttribute(POLICY_FRAGMENT, "ReadAfterPaymentPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/LogAccessPolicyForm")
	public String logPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand logLevelRightOperand = new RightOperand();
		logLevelRightOperand.setType(RightOperandType.STRING);
		logLevelRightOperand.setValue(LogLevelType.DEBUG_LEVEL.toString());
		Condition logLevelRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.LOG_LEVEL, Operator.EQUALS, logLevelRightOperand, "");
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		Condition systemDeviceRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.SYSTEM_DEVICE, Operator.SAME_AS, rightOperand, "");
		ArrayList<Condition> refinements = new ArrayList<>();
		refinements.add(logLevelRefinement);
		refinements.add(systemDeviceRefinement);
		Action useAction = new Action(ActionType.USE);
		Action logDutyAction = new Action(ActionType.LOG);
		logDutyAction.setRefinements(refinements);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		Rule postobligation = new Rule(RuleType.POSTDUTY, logDutyAction);
		ArrayList<Rule> postDuties = new ArrayList<>();
		postDuties.add(postobligation);
		rule.setPostduties(postDuties);
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/log-access"));
		model.addAttribute(POLICY_FRAGMENT, "LogAccessPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/DistributeDataPolicyForm")
	public String encodingPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand dutyRightOperand = new RightOperand();
		dutyRightOperand.setType(RightOperandType.ANYURI);
		Condition thirdPartyRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.TARGET_POLICY, Operator.SAME_AS, dutyRightOperand, "");
		ArrayList<Condition> dutyRefinements = new ArrayList<>();
		dutyRefinements.add(thirdPartyRefinement);

		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.STRING);
		rightOperand.setValue(ArtifactStateType.ENCRYPTED.toString());
		Condition artifactStateConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ARTIFACT_STATE, Operator.EQUALS, rightOperand, "");
		ArrayList<Condition> constraints = new ArrayList<>();
		constraints.add(artifactStateConstraint);
		Action distributeAction = new Action(ActionType.DISTRIBUTE);
		Action nextPolicyDutyAction = new Action(ActionType.NEXT_POLICY);
		nextPolicyDutyAction.setRefinements(dutyRefinements);
		Rule rule = new Rule(RuleType.PERMISSION, distributeAction);
		Rule preDuties = new Rule(RuleType.PREDUTY, nextPolicyDutyAction);
		ArrayList<Rule> preDutiess = new ArrayList<>();
		preDutiess.add(preDuties);
		rule.setPreduties(preDutiess);
		rule.setConstraints(constraints);
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/restrict-access-encoding"));
		model.addAttribute(POLICY_FRAGMENT, "DistributeDataPolicyForm");
		return "index";
	}

/*	@RequestMapping("/policy/DistributeToThirdPartyPolicyForm")
	public String distributeToThirdPartyPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		Condition thirdPartyRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.TARGET_POLICY, Operator.EQ, rightOperand, "");
		ArrayList<Condition> refinements = new ArrayList<>();
		refinements.add(thirdPartyRefinement);
		Action distributeAction = new Action(ActionType.DISTRIBUTE);
		Action nextPolicyDutyAction = new Action(ActionType.NEXT_POLICY);
		nextPolicyDutyAction.setRefinements(refinements);
		Rule rule = new Rule(RuleType.PERMISSION, distributeAction);
		Rule preDuties = new Rule(RuleType.PREDUTY, nextPolicyDutyAction);
		ArrayList<Rule> preDutiess = new ArrayList<>();
		preDutiess.add(preDuties);
		rule.setPreduties(preDutiess);
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/distribute-policy"));
		model.addAttribute(POLICY_FRAGMENT, "DistributeToThirdPartyPolicyForm");
		return "index";
	}*/

	@RequestMapping("/policy/PrintPolicyForm")
	public String inhibitPrintPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		Action printAction = new Action(ActionType.PRINT);
		Rule rule = new Rule(RuleType.PROHIBITION, printAction);
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/restrict-print"));
		model.addAttribute(POLICY_FRAGMENT, "BasePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/AnonymizeInRestPolicyForm")
	public String anonymizeInRestolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		Action anonymizeAction = new Action(ActionType.ANONYMIZE);
		Rule rule = new Rule(RuleType.OBLIGATION, anonymizeAction);
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
	  	Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/anonymize-in-rest"));
		model.addAttribute(POLICY_FRAGMENT, "BasePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/AnonymizeInTransitPolicyForm")
	public String anonymizeTransitPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		ModificationMethodParameter replaceWithParameter = new ModificationMethodParameter();
		replaceWithParameter.setType(RightOperandType.STRING);
		RightOperand modificationMethodRightOperand = new RightOperand();
		modificationMethodRightOperand.setType(RightOperandType.ANYURI);
		Condition modificationMethodRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.MODIFICATIONMETHOD, Operator.EQ, modificationMethodRightOperand, "");
		modificationMethodRefinement.setReplaceWith(replaceWithParameter);
		ArrayList<Condition> refinements = new ArrayList<>();
		refinements.add(modificationMethodRefinement);
		Action useAction = new Action(ActionType.USE);
		Action anonymizeDutyAction = new Action(ActionType.ANONYMIZE);
		anonymizeDutyAction.setRefinements(refinements);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		Rule preDuties = new Rule(RuleType.PREDUTY, anonymizeDutyAction);
		ArrayList<Rule> preDutiess = new ArrayList<>();
		preDutiess.add(preDuties);
		rule.setPreduties(preDutiess);
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/anonymize-in-transit"));
		model.addAttribute(POLICY_FRAGMENT, "AnonymizeInTransitPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/CountAccessPolicyForm")
	public String countPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.DECIMAL);
		Condition countCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.COUNT, Operator.LTEQ, rightOperand, "");
		ArrayList<Condition> constraints = new ArrayList<>();
		constraints.add(countCondition);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setConstraints(constraints);

		Action countDutyAction = new Action(ActionType.COUNT);
		Rule postobligation = new Rule(RuleType.POSTDUTY, countDutyAction);
		ArrayList<Rule> postDuties = new ArrayList<>();
		postDuties.add(postobligation);
		rule.setPostduties(postDuties);

		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/count-access"));
		model.addAttribute(POLICY_FRAGMENT, "CountAccessPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InformPolicyForm")
	public String policy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand notificationLevelRightOperand = new RightOperand();
		notificationLevelRightOperand.setType(RightOperandType.STRING);
		notificationLevelRightOperand.setValue(LogLevelType.DEBUG_LEVEL.toString());
		Condition notificationLevelRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.NOTIFICATION_LEVEL, Operator.EQUALS, notificationLevelRightOperand, "");
	  	RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		rightOperand.setValue("http://example.com/party/my-party");
		Condition recipientRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.RECIPIENT, Operator.EQUALS, rightOperand, "");
		ArrayList<Condition> refinements = new ArrayList<>();
		refinements.add(notificationLevelRefinement);
		refinements.add(recipientRefinement);
		Action useAction = new Action(ActionType.USE);
		Action notifyDutyAction = new Action(ActionType.NOTIFY);
		notifyDutyAction.setRefinements(refinements);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		Rule postobligation = new Rule(RuleType.POSTDUTY, notifyDutyAction);
		ArrayList<Rule> postDuties = new ArrayList<>();
		postDuties.add(postobligation);
		rule.setPostduties(postDuties);
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/notify"));
		model.addAttribute(POLICY_FRAGMENT, "InformPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/ComplexPolicyForm")
	public String complexPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand locationRightOperand = new RightOperand();
		locationRightOperand.setType(RightOperandType.ANYURI);
		Condition locationConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ABSOLUTESPATIALPOSITION, Operator.EQ, locationRightOperand, "");

		RightOperand systemRightOperand = new RightOperand();
		systemRightOperand.setType(RightOperandType.ANYURI);
		Condition systemConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.SYSTEM, Operator.EQ, systemRightOperand, "");

		RightOperand purposeRightOperand = new RightOperand();
		purposeRightOperand.setType(RightOperandType.ANYURI);
		Condition purposeConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.PURPOSE, Operator.EQ, purposeRightOperand, "");

		RightOperand eventRightOperand = new RightOperand();
		eventRightOperand.setType(RightOperandType.ANYURI);
		Condition eventConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.EVENT, Operator.EQ, eventRightOperand, "");

		RightOperand elapsedTimeRightOperand = new RightOperand();
		elapsedTimeRightOperand.setType(RightOperandType.DURATIONENTITY);
		RightOperandEntity beginEntity = new RightOperandEntity();
		beginEntity.setEntityType(EntityType.BEGIN);
		beginEntity.setDataType(RightOperandType.DATETIMESTAMP);
		RightOperandEntity hasDurationEntity = new RightOperandEntity();
		hasDurationEntity.setEntityType(EntityType.HASDURATION);
		hasDurationEntity.setDataType(RightOperandType.DURATION);
		hasDurationEntity.setTimeUnit(TimeUnit.HOURS);
		ArrayList<RightOperandEntity> durationEntities = new ArrayList<>();
		durationEntities.add(beginEntity);
		durationEntities.add(hasDurationEntity);
		elapsedTimeRightOperand.setEntities(durationEntities);
		Condition elapsedTimeConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ELAPSED_TIME, Operator.SHORTER_EQ, elapsedTimeRightOperand, "");

		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.INTERVAL);
		RightOperandEntity startEntity = new RightOperandEntity();
		startEntity.setEntityType(EntityType.BEGIN);
		startEntity.setDataType(RightOperandType.DATETIMESTAMP);
		RightOperandEntity endEntity = new RightOperandEntity();
		endEntity.setEntityType(EntityType.END);
		endEntity.setDataType(RightOperandType.DATETIMESTAMP);
		ArrayList<RightOperandEntity> entities = new ArrayList<>();
		entities.add(startEntity);
		entities.add(endEntity);
		rightOperand.setEntities(entities);
		Condition timeIntervalCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.POLICY_EVALUATION_TIME, Operator.TEMPORAL_EQUALS, rightOperand, "");

		RightOperand countRightOperand = new RightOperand();
		countRightOperand.setType(RightOperandType.DECIMAL);
		Condition countCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.COUNT, Operator.LTEQ, countRightOperand, "");

		RightOperand paymentRightOperand = new RightOperand();
		paymentRightOperand.setType(RightOperandType.DECIMAL);
		Condition paymentCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.PAY_AMOUNT, Operator.EQ, paymentRightOperand, "");
		paymentCondition.setUnit("http://dbpedia.org/resource/Euro");

		ArrayList<Condition> constraints = new ArrayList<>();
		constraints.add(locationConstraint);
		constraints.add(systemConstraint);
		constraints.add(purposeConstraint);
		constraints.add(eventConstraint);
		constraints.add(countCondition);
		constraints.add(timeIntervalCondition);
		constraints.add(paymentCondition);
		constraints.add(elapsedTimeConstraint);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setConstraints(constraints);
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);

		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/complex-policy"));

		model.addAttribute(POLICY_FRAGMENT, "ComplexPolicyForm");
		return "index";
	}

	/*
	@RequestMapping("/policy/ComplexPolicyODRL")
	public String odrlComplexPolicy(@ModelAttribute AbstractPolicy abstractPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute("odrlFragment", "true");
		model.addAttribute("odrlPolicy", ComplexPolicyOdrlCreator.createODRL(abstractPolicy));
		return "index";
	}

	 */

	@RequestMapping("/policy/HowTo")
	public String howto(Model model) {
		model.addAttribute(POLICY_FRAGMENT, "HowTo");
		return "index";
	}

	 @SuppressWarnings("rawtypes")
	@RequestMapping(value="/policy/JsonOrdlPolicyMAYDATA", params="action=transfer")
	  public String policy(@ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		OdrlPolicy odrlPolicy = IdsOdrlUtil.getOdrlPolicy(jsonOdrlPolicy.getJsonString());
		model.addAttribute("odrlFragment", "true");
		model.addAttribute("odrlPolicy", jsonOdrlPolicy.getJsonString());
		model.addAttribute("technologyDependentPolicyFragment", "true");
		boolean tempProviderSide = true;
		model.addAttribute("technologyDependentPolicy", TransformPolicy.createTechnologyDependentPolicy(odrlPolicy, tempProviderSide));
	    return "index";
	  }
	 
	 @SuppressWarnings("rawtypes")
	 @RequestMapping(value="/policy/JsonOrdlPolicyMAYDATA", params="action=send")
	  public String sendPolicy(@ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		String returnedPolicy = UcAppService.sendPolicy(jsonOdrlPolicy);
		model.addAttribute("odrlFragment", "true");
		model.addAttribute("odrlPolicy", jsonOdrlPolicy.getJsonString());
		model.addAttribute("technologyDependentPolicyFragment", "true");
		model.addAttribute("technologyDependentPolicy", returnedPolicy);
	    return "index";
	  }

	@RequestMapping("/policy/ODRLCreator")
	public String createOdrlPolicy(@ModelAttribute OdrlPolicy odrlPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute("odrlFragment", "true");
		model.addAttribute("odrlPolicy", OdrlCreator.createODRL(odrlPolicy));
		return "index";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/policy/InterpretOdrlPolicy")
	public String interpretPolicy(@ModelAttribute JsonOdrlPolicy odrlPolicy, Model model) throws MalformedURLException, URISyntaxException {
	  	OdrlPolicy basePolicy = new OdrlPolicy();
	  	basePolicy.setType(PolicyType.OFFER);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(rule);
	  	basePolicy.setRules(rules);
	  	basePolicy.setPolicyId(URI.create("https://w3id.org/idsa/autogen/contract/sample"));
	  	basePolicy.setTarget(URI.create("http://example.com/ids-data/sample"));
	  	basePolicy.setProvider(new Party(PartyType.PROVIDER, URI.create(("http://example.com/party/my-party"))));
	  	basePolicy.setProviderSide(true);
		OdrlCreator.createODRL(basePolicy);
		Map map = null;

		try {
			if(null != odrlPolicy.getJsonString())
			{
				Object o = JsonUtils.fromString(odrlPolicy.getJsonString());
				if (o instanceof Map) {
					map  = (Map) o;
				}
				model.addAttribute("odrlPolicy", odrlPolicy.getJsonString());
			}else{
				model.addAttribute("odrlPolicy", OdrlCreator.createODRL(basePolicy));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		model.addAttribute("translateFragment", "true");
		boolean tempProviderSide = true;
		model.addAttribute("translate", OdrlTranslator.translate(map, tempProviderSide, basePolicy));
		return "index";
	}
}
