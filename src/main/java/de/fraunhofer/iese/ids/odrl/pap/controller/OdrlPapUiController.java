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
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.ActionType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.ConditionType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.LeftOperand;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.Operator;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.PartyType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.PolicyType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.RightOperandType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.RuleType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.TimeUnit;
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
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/restrict-access"));
		model.addAttribute(POLICY_FRAGMENT, "BasePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InhibitAccessPolicyForm")
	public String inhibitPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PROHIBITION, useAction);

		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/restrict-access"));
		model.addAttribute(POLICY_FRAGMENT, "BasePolicyForm");
		return "index";
	}

	  @RequestMapping("/policy/SpecificPurposePolicyForm")
	  public String providePurposePolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		  RightOperand rightOperand = new RightOperand();
		  rightOperand.setType(RightOperandType.ANYURI);
		  Condition purposeConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.PURPOSE, Operator.EQUALS, rightOperand, "");
		  List<Condition> constraints = new ArrayList<>();
		  constraints.add(purposeConstraint);
		  Action useAction = new Action(ActionType.USE);
		  Rule rule = new Rule(RuleType.PERMISSION, useAction);
		  rule.setConstraints(constraints);
		  List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		  Party consumer = new Party();
		  consumer.setType(PartyType.CONSUMER);
		  odrlPolicy.setConsumer(consumer);
		  odrlPolicy.setRules(rules);
		  odrlPolicy.setPolicyId(URI.create("http://example.com/policy/restrict-access-purpose"));
		  model.addAttribute(POLICY_FRAGMENT, "SpecificPurposePolicyForm");
	    return "index";
	  }

	@RequestMapping("/policy/InhibitSpecificPurposePolicyForm")
	public String inhibitPurposePolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		Condition purposeConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.PURPOSE, Operator.EQUALS, rightOperand, "");
		List<Condition> constraints = new ArrayList<>();
		constraints.add(purposeConstraint);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PROHIBITION, useAction);
		rule.setConstraints(constraints);
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/restrict-access-purpose"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificPurposePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/SpecificSystemPolicyForm")
	public String provideSystemPolicy(@ModelAttribute OdrlPolicy odrlPolicy,  Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		Condition systemConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.SYSTEM, Operator.EQUALS, rightOperand, "");
		List<Condition> constraints = new ArrayList<>();
		constraints.add(systemConstraint);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setConstraints(constraints);
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/restrict-access-system"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificSystemPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InhibitSpecificSystemPolicyForm")
	public String inhibitSystemPolicy(@ModelAttribute OdrlPolicy odrlPolicy,  Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		Condition systemConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.SYSTEM, Operator.EQUALS, rightOperand, "");
		List<Condition> constraints = new ArrayList<>();
		constraints.add(systemConstraint);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PROHIBITION, useAction);
		rule.setConstraints(constraints);
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/restrict-access-system"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificSystemPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/SpecificLocationPolicyForm")
	public String provideLocationPolicy(@ModelAttribute OdrlPolicy odrlPolicy,  Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		Condition locationConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ABSOLUTESPATIALPOSITION, Operator.EQUALS, rightOperand, "");
		List<Condition> constraints = new ArrayList<>();
		constraints.add(locationConstraint);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setConstraints(constraints);
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/restrict-access-location"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificLocationPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InhibitSpecificLocationPolicyForm")
	public String inhibitLocationPolicy(@ModelAttribute OdrlPolicy odrlPolicy,  Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		Condition locationConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ABSOLUTESPATIALPOSITION, Operator.EQUALS, rightOperand, "");
		List<Condition> constraints = new ArrayList<>();
		constraints.add(locationConstraint);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PROHIBITION, useAction);
		rule.setConstraints(constraints);
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/restrict-access-location"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificLocationPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/SpecificEventPolicyForm")
	public String provideEventPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {

		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		Condition eventConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.EVENT, Operator.EQUALS, rightOperand, "");
		List<Condition> constraints = new ArrayList<>();
		constraints.add(eventConstraint);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setConstraints(constraints);
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/restrict-access-event"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificEventPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InhibitSpecificEventPolicyForm")
	public String inhibitEventPolicy(@ModelAttribute OdrlPolicy odrlPolicy,  Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		Condition eventConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.EVENT, Operator.EQUALS, rightOperand, "");
		List<Condition> constraints = new ArrayList<>();
		constraints.add(eventConstraint);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PROHIBITION, useAction);
		rule.setConstraints(constraints);
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/restrict-access-event"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificEventPolicyForm");
		return "index";
	}

	  @RequestMapping("/policy/DeleteAfterUsagePeriodPolicyForm")
	  public String deletePolicyAfterUsagePeriod(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		  RightOperand delayPeriodRightOperand = new RightOperand();
		  delayPeriodRightOperand.setType(RightOperandType.DURATION);
		  Condition delayPeriodRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.DELAYPERIOD, Operator.EQUALS, delayPeriodRightOperand, "");
		  delayPeriodRefinement.setTimeUnit(TimeUnit.HOURS);
		  RightOperand dateTimeRightOperand = new RightOperand();
		  dateTimeRightOperand.setType(RightOperandType.DATETIME);
		  Condition dateTimeRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.DATETIME, Operator.EQUALS, dateTimeRightOperand, "");
		  List<Condition> refinements = new ArrayList<>();
		  refinements.add(delayPeriodRefinement);
		  refinements.add(dateTimeRefinement);
		  Action deleteAction = new Action(ActionType.DELETE);
		  deleteAction.setRefinements(refinements);
		  Rule rule = new Rule(RuleType.OBLIGATION, deleteAction);
		  List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		  Party consumer = new Party();
		  consumer.setType(PartyType.CONSUMER);
		  odrlPolicy.setConsumer(consumer);
		  odrlPolicy.setRules(rules);
		  odrlPolicy.setPolicyId(URI.create("http://example.com/policy/delete-data"));
	  	model.addAttribute(POLICY_FRAGMENT, "DeleteAfterUsagePeriodPolicyForm");
	    return "index";
	  }

	@RequestMapping("/policy/DeleteAfterUsagePolicyForm")
	public String deletePolicyAfterUsage(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		Action useAction = new Action(ActionType.USE);
		Action deleteDutyAction = new Action(ActionType.DELETE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		Rule postobligation = new Rule(RuleType.POSTOBLIGATION, deleteDutyAction);
		List<Rule> postobligations = new ArrayList<>();
		postobligations.add(postobligation);
		rule.setPostobligations(postobligations);
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/delete-after-usage"));
		model.addAttribute(POLICY_FRAGMENT, "DeleteAfterUsagePolicyForm");
		return "index";
	}

	  @RequestMapping("/policy/ReadDataIntervalPolicyForm")
	  public String provideInterval(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		  RightOperand elapsedTimeRightOperand = new RightOperand();
		  elapsedTimeRightOperand.setType(RightOperandType.DURATION);
		  Condition elapsedTimeConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ELAPSEDTIME, Operator.EQUALS, elapsedTimeRightOperand, "");
		  elapsedTimeConstraint.setTimeUnit(TimeUnit.HOURS);
		  RightOperand rightOperand = new RightOperand();
		  rightOperand.setType(RightOperandType.DATETIME);
		  RightOperand secondRightOperand = new RightOperand();
		  secondRightOperand.setType(RightOperandType.DATETIME);
		  Condition timeIntervalCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.DATETIME, Operator.GREATER, rightOperand, Operator.LESS, secondRightOperand, "");
		  List<Condition> constraints = new ArrayList<>();
		  constraints.add(elapsedTimeConstraint);
		  constraints.add(timeIntervalCondition);
		  Action useAction = new Action(ActionType.USE);
		  Rule rule = new Rule(RuleType.PERMISSION, useAction);
		  rule.setConstraints(constraints);
		  List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		  Party consumer = new Party();
		  consumer.setType(PartyType.CONSUMER);
		  odrlPolicy.setConsumer(consumer);
		  odrlPolicy.setRules(rules);
		  odrlPolicy.setPolicyId(URI.create("http://example.com/policy/restrict-access-interval"));
		  model.addAttribute(POLICY_FRAGMENT, "ReadDataIntervalPolicyForm");
	    return "index";
	  }

	@RequestMapping("/policy/InhibitReadDataIntervalPolicyForm")
	public String inhibitInterval(@ModelAttribute OdrlPolicy odrlPolicy,  Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.DATETIME);
		RightOperand secondRightOperand = new RightOperand();
		secondRightOperand.setType(RightOperandType.DATETIME);
		Condition timeIntervalCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.DATETIME, Operator.GREATER_EQUAL, rightOperand, Operator.LESS_EQUAL, secondRightOperand, "");
		List<Condition> constraints = new ArrayList<>();
		constraints.add(timeIntervalCondition);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PROHIBITION, useAction);
		rule.setConstraints(constraints);
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/restrict-access-interval"));
		model.addAttribute(POLICY_FRAGMENT, "ReadDataIntervalPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/ReadAfterPaymentPolicyForm")
	public String provideAfterPayment(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.DECIMAL);
		Condition paymentCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.PAYAMOUNT, Operator.EQUALS, rightOperand, "");
		paymentCondition.setUnit("http://dbpedia.org/resource/Euro");
		List<Condition> constraints = new ArrayList<>();
		constraints.add(paymentCondition);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setConstraints(constraints);
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/provide-access-after-payment"));
		model.addAttribute(POLICY_FRAGMENT, "ReadAfterPaymentPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/LogAccessPolicyForm")
	public String logPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		Condition systemDeviceRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.SYSTEMDEVICE, Operator.EQUALS, rightOperand, "");
		List<Condition> refinements = new ArrayList<>();
		refinements.add(systemDeviceRefinement);
		Action useAction = new Action(ActionType.USE);
		Action logDutyAction = new Action(ActionType.LOG);
		logDutyAction.setRefinements(refinements);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		Rule postobligation = new Rule(RuleType.POSTOBLIGATION, logDutyAction);
		List<Rule> postobligations = new ArrayList<>();
		postobligations.add(postobligation);
		rule.setPostobligations(postobligations);
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/log-access"));
		model.addAttribute(POLICY_FRAGMENT, "LogAccessPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/EncodingPolicyForm")
	public String encodingPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		Condition encodingConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ENCODING, Operator.EQUALS, rightOperand, "");
		List<Condition> constraints = new ArrayList<>();
		constraints.add(encodingConstraint);
		Action distributeAction = new Action(ActionType.DISTRIBUTE);
		Rule rule = new Rule(RuleType.PERMISSION, distributeAction);
		rule.setConstraints(constraints);
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/restrict-access-encoding"));
		model.addAttribute(POLICY_FRAGMENT, "EncodingPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/DistributeToThirdPartyPolicyForm")
	public String distributeToThirdPartyPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		Condition thirdPartyRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.TARGETPOLICY, Operator.EQUALS, rightOperand, "");
		List<Condition> refinements = new ArrayList<>();
		refinements.add(thirdPartyRefinement);
		Action distributeAction = new Action(ActionType.DISTRIBUTE);
		Action nextPolicyDutyAction = new Action(ActionType.NEXTPOLICY);
		nextPolicyDutyAction.setRefinements(refinements);
		Rule rule = new Rule(RuleType.PERMISSION, distributeAction);
		Rule preobligation = new Rule(RuleType.PREOBLIGATION, nextPolicyDutyAction);
		List<Rule> preobligations = new ArrayList<>();
		preobligations.add(preobligation);
		rule.setPreobligations(preobligations);
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/distribute-policy"));
		model.addAttribute(POLICY_FRAGMENT, "DistributeToThirdPartyPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/PrintPolicyForm")
	public String inhibitPrintPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		Action printAction = new Action(ActionType.PRINT);
		Rule rule = new Rule(RuleType.PROHIBITION, printAction);
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/restrict-print"));
		model.addAttribute(POLICY_FRAGMENT, "BasePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/AnonymizeInRestPolicyForm")
	public String anonymizeInRestolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		Action anonymizeAction = new Action(ActionType.ANONYMIZE);
		Rule rule = new Rule(RuleType.OBLIGATION, anonymizeAction);
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
	  	Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/anonymize-in-rest"));
		model.addAttribute(POLICY_FRAGMENT, "BasePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/AnonymizeInTransitPolicyForm")
	public String anonymizeTransitPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		ModificationMethodParameter replaceWithParameter = new ModificationMethodParameter();
		replaceWithParameter.setType(RightOperandType.STRING);
		RightOperand modificationMethodRightOperand = new RightOperand();
		modificationMethodRightOperand.setType(RightOperandType.ANYURI);
		Condition modificationMethodRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.MODIFICATIONMETHOD, Operator.EQUALS, modificationMethodRightOperand, "");
		modificationMethodRefinement.setReplaceWith(replaceWithParameter);
		List<Condition> refinements = new ArrayList<>();
		refinements.add(modificationMethodRefinement);
		Action useAction = new Action(ActionType.USE);
		Action anonymizeDutyAction = new Action(ActionType.ANONYMIZE);
		anonymizeDutyAction.setRefinements(refinements);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		Rule preobligation = new Rule(RuleType.PREOBLIGATION, anonymizeDutyAction);
		List<Rule> preobligations = new ArrayList<>();
		preobligations.add(preobligation);
		rule.setPreobligations(preobligations);
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/anonymize-in-transit"));
		model.addAttribute(POLICY_FRAGMENT, "AnonymizeInTransitPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/CountAccessPolicyForm")
	public String countPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.DECIMAL);
		Condition countCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.COUNT, Operator.EQUALS, rightOperand, "");
		List<Condition> constraints = new ArrayList<>();
		constraints.add(countCondition);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setConstraints(constraints);
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/count-access"));
		model.addAttribute(POLICY_FRAGMENT, "CountAccessPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InformPolicyForm")
	public String policy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
	  	RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		rightOperand.setValue("http://example.com/party/my-party");
		Condition informedPartyRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.INFORMEDPARTY, Operator.EQUALS, rightOperand, "");
		List<Condition> refinements = new ArrayList<>();
		refinements.add(informedPartyRefinement);
		Action useAction = new Action(ActionType.USE);
		Action informDutyAction = new Action(ActionType.INFORM);
		informDutyAction.setRefinements(refinements);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		Rule postobligation = new Rule(RuleType.POSTOBLIGATION, informDutyAction);
		List<Rule> postobligations = new ArrayList<>();
		postobligations.add(postobligation);
		rule.setPostobligations(postobligations);
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/notify"));
		model.addAttribute(POLICY_FRAGMENT, "InformPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/ComplexPolicyForm")
	public String complexPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand locationRightOperand = new RightOperand();
		locationRightOperand.setType(RightOperandType.ANYURI);
		Condition locationConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ABSOLUTESPATIALPOSITION, Operator.EQUALS, locationRightOperand, "");

		RightOperand systemRightOperand = new RightOperand();
		systemRightOperand.setType(RightOperandType.ANYURI);
		Condition systemConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.SYSTEM, Operator.EQUALS, systemRightOperand, "");

		RightOperand purposeRightOperand = new RightOperand();
		purposeRightOperand.setType(RightOperandType.ANYURI);
		Condition purposeConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.PURPOSE, Operator.EQUALS, purposeRightOperand, "");

		RightOperand eventRightOperand = new RightOperand();
		eventRightOperand.setType(RightOperandType.ANYURI);
		Condition eventConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.EVENT, Operator.EQUALS, eventRightOperand, "");

		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.DATETIME);
		RightOperand secondRightOperand = new RightOperand();
		secondRightOperand.setType(RightOperandType.DATETIME);
		Condition timeIntervalCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.DATETIME, Operator.GREATER, rightOperand, Operator.LESS, secondRightOperand, "");

		RightOperand paymentRightOperand = new RightOperand();
		paymentRightOperand.setType(RightOperandType.DECIMAL);
		Condition paymentCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.PAYAMOUNT, Operator.EQUALS, paymentRightOperand, "");
		paymentCondition.setUnit("http://dbpedia.org/resource/Euro");

		List<Condition> constraints = new ArrayList<>();
		constraints.add(locationConstraint);
		constraints.add(systemConstraint);
		constraints.add(purposeConstraint);
		constraints.add(eventConstraint);
		constraints.add(timeIntervalCondition);
		constraints.add(paymentCondition);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setConstraints(constraints);
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);

		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy/complex-policy"));

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
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
	  	basePolicy.setRules(rules);
	  	basePolicy.setPolicyId(URI.create("http://example.com/policy/sample"));
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
