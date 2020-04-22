/**
 * 
 */
package de.fraunhofer.iese.ids.odrl.pap.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.*;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.*;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.UseAction;
import de.fraunhofer.iese.ids.odrl.pap.util.*;
import de.fraunhofer.iese.ids.odrl.pap.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonParseException;
import com.github.jsonldjava.utils.JsonUtils;

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
		UseAction useAction = new UseAction();
		PermissionRuleUseAction permissionRuleUseAction = new PermissionRuleUseAction(useAction);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setPermissionRuleUseAction(permissionRuleUseAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:restrict-access"));
		model.addAttribute(POLICY_FRAGMENT, "BasePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InhibitAccessPolicyForm")
	public String inhibitPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		UseAction useAction = new UseAction();
		ProhibitionRuleUseAction prohibitionRuleUseAction = new ProhibitionRuleUseAction(useAction);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setProhibitionRuleUseAction(prohibitionRuleUseAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:restrict-access"));
		model.addAttribute(POLICY_FRAGMENT, "BasePolicyForm");
		return "index";
	}

	  @RequestMapping("/policy/SpecificPurposePolicyForm")
	  public String providePurposePolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		  RightOperand rightOperand = new RightOperand();
		  rightOperand.setType(RightOperandType.ANYURI);
		  PurposeCondition purposeConstraint = new PurposeCondition(ConditionType.CONSTRAINT, rightOperand, null);
		  UseAction useAction = new UseAction();
		  PermissionRuleUseAction permissionRuleUseAction = new PermissionRuleUseAction(useAction);
		  permissionRuleUseAction.setPurposeConstraint(purposeConstraint);
		  Party consumer = new Party();
		  consumer.setType(PartyType.CONSUMER);
		  odrlPolicy.setConsumer(consumer);
		  odrlPolicy.setPermissionRuleUseAction(permissionRuleUseAction);
		  odrlPolicy.setPolicyId(URI.create("http://example.com/policy:restrict-access-purpose"));
		  model.addAttribute(POLICY_FRAGMENT, "SpecificPurposePolicyForm");
	    return "index";
	  }

	@RequestMapping("/policy/InhibitSpecificPurposePolicyForm")
	public String inhibitPurposePolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		PurposeCondition purposeConstraint = new PurposeCondition(ConditionType.CONSTRAINT, rightOperand, null);
		UseAction useAction = new UseAction();
		ProhibitionRuleUseAction prohibitionRuleUseAction = new ProhibitionRuleUseAction(useAction);
		prohibitionRuleUseAction.setPurposeConstraint(purposeConstraint);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setProhibitionRuleUseAction(prohibitionRuleUseAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:restrict-access-purpose"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificPurposePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/SpecificSystemPolicyForm")
	public String provideSystemPolicy(@ModelAttribute OdrlPolicy odrlPolicy,  Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		SystemCondition systemConstraint = new SystemCondition(ConditionType.CONSTRAINT, rightOperand, null);
		UseAction useAction = new UseAction();
		PermissionRuleUseAction permissionRuleUseAction = new PermissionRuleUseAction(useAction);
		permissionRuleUseAction.setSystemConstraint(systemConstraint);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setPermissionRuleUseAction(permissionRuleUseAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:restrict-access-system"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificSystemPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InhibitSpecificSystemPolicyForm")
	public String inhibitSystemPolicy(@ModelAttribute OdrlPolicy odrlPolicy,  Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		SystemCondition systemConstraint = new SystemCondition(ConditionType.CONSTRAINT, rightOperand, null);
		UseAction useAction = new UseAction();
		ProhibitionRuleUseAction prohibitionRuleUseAction = new ProhibitionRuleUseAction(useAction);
		prohibitionRuleUseAction.setSystemConstraint(systemConstraint);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setProhibitionRuleUseAction(prohibitionRuleUseAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:restrict-access-system"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificSystemPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/SpecificLocationPolicyForm")
	public String provideLocationPolicy(@ModelAttribute OdrlPolicy odrlPolicy,  Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		AbsoluteSpatialPositionCondition locationConstraint = new AbsoluteSpatialPositionCondition(ConditionType.CONSTRAINT, rightOperand, null);
		UseAction useAction = new UseAction();
		PermissionRuleUseAction permissionRuleUseAction = new PermissionRuleUseAction(useAction);
		permissionRuleUseAction.setAbsoluteSpatialPositionConstraint(locationConstraint);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setPermissionRuleUseAction(permissionRuleUseAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:restrict-access-location"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificLocationPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InhibitSpecificLocationPolicyForm")
	public String inhibitLocationPolicy(@ModelAttribute OdrlPolicy odrlPolicy,  Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		AbsoluteSpatialPositionCondition locationConstraint = new AbsoluteSpatialPositionCondition(ConditionType.CONSTRAINT, rightOperand, null);
		UseAction useAction = new UseAction();
		ProhibitionRuleUseAction prohibitionRuleUseAction = new ProhibitionRuleUseAction(useAction);
		prohibitionRuleUseAction.setAbsoluteSpatialPositionConstraint(locationConstraint);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setProhibitionRuleUseAction(prohibitionRuleUseAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:restrict-access-location"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificLocationPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/SpecificEventPolicyForm")
	public String provideEventPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {

		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		EventCondition eventConstraint = new EventCondition(ConditionType.CONSTRAINT, rightOperand, null);
		UseAction useAction = new UseAction();
		PermissionRuleUseAction permissionRuleUseAction = new PermissionRuleUseAction(useAction);
		permissionRuleUseAction.setEventConstraint(eventConstraint);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setPermissionRuleUseAction(permissionRuleUseAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:restrict-access-event"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificEventPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InhibitSpecificEventPolicyForm")
	public String inhibitEventPolicy(@ModelAttribute OdrlPolicy odrlPolicy,  Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		EventCondition eventConstraint = new EventCondition(ConditionType.CONSTRAINT, rightOperand, null);
		UseAction useAction = new UseAction();
		ProhibitionRuleUseAction prohibitionRuleUseAction = new ProhibitionRuleUseAction(useAction);
		prohibitionRuleUseAction.setEventConstraint(eventConstraint);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setProhibitionRuleUseAction(prohibitionRuleUseAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:restrict-access-event"));
		model.addAttribute(POLICY_FRAGMENT, "SpecificEventPolicyForm");
		return "index";
	}

	  @RequestMapping("/policy/DeleteAfterUsagePeriodPolicyForm")
	  public String deletePolicyAfterUsagePeriod(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		  RightOperand delayPeriodRightOperand = new RightOperand();
		  delayPeriodRightOperand.setType(RightOperandType.DURATION);
		  DelayPeriodCondition delayPeriodRefinement = new DelayPeriodCondition(ConditionType.REFINEMENT, delayPeriodRightOperand, null,TimeUnit.HOURS);
		  RightOperand dateTimeRightOperand = new RightOperand();
		  dateTimeRightOperand.setType(RightOperandType.DATETIME);
		  DateTimeCondition dateTimeRefinement = new DateTimeCondition(ConditionType.REFINEMENT, dateTimeRightOperand, null);
		  DeleteAction deleteAction = new DeleteAction();
		  deleteAction.setDelayPeriodRefinement(delayPeriodRefinement);
		  deleteAction.setDateTimeRefinement(dateTimeRefinement);
		  ObligationRuleDeleteAction obligationRuleDeleteAction = new ObligationRuleDeleteAction(deleteAction);
		  Party consumer = new Party();
		  consumer.setType(PartyType.CONSUMER);
		  odrlPolicy.setConsumer(consumer);
		  odrlPolicy.setObligationRuleDeleteAction(obligationRuleDeleteAction);
		  odrlPolicy.setPolicyId(URI.create("http://example.com/policy:delete-data"));
	  	model.addAttribute(POLICY_FRAGMENT, "DeleteAfterUsagePeriodPolicyForm");
	    return "index";
	  }

	@RequestMapping("/policy/DeleteAfterUsagePolicyForm")
	public String deletePolicyAfterUsage(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setId(RightOperandId.POLICYUSAGE);
		EventCondition eventDutyConstraint = new EventCondition(ConditionType.CONSTRAINT, rightOperand, null);
		eventDutyConstraint.setOperator(Operator.GREATER);

		UseAction useAction = new UseAction();
		DeleteAction deleteDutyAction = new DeleteAction();
		PermissionRuleUseAction permissionRuleUseAction = new PermissionRuleUseAction(useAction);
		permissionRuleUseAction.setDeleteDutyAction(deleteDutyAction);
		permissionRuleUseAction.setEventDutyConstraint(eventDutyConstraint);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setPermissionRuleUseAction(permissionRuleUseAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:delete-after-usage"));
		model.addAttribute(POLICY_FRAGMENT, "DeleteAfterUsagePolicyForm");
		return "index";
	}

	  @RequestMapping("/policy/ReadDataIntervalPolicyForm")
	  public String provideInterval(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		  RightOperand rightOperand = new RightOperand();
		  rightOperand.setType(RightOperandType.DATETIME);
		  RightOperand secondRightOperand = new RightOperand();
		  secondRightOperand.setType(RightOperandType.DATETIME);
		  TimeIntervalCondition timeIntervalCondition = new TimeIntervalCondition(ConditionType.CONSTRAINT, Operator.GREATER, rightOperand, Operator.LESS, secondRightOperand, null);
		  UseAction useAction = new UseAction();
		  PermissionRuleUseAction permissionRuleUseAction = new PermissionRuleUseAction(useAction);
		  permissionRuleUseAction.setTimeIntervalConstraint(timeIntervalCondition);
		  Party consumer = new Party();
		  consumer.setType(PartyType.CONSUMER);
		  odrlPolicy.setConsumer(consumer);
		  odrlPolicy.setPermissionRuleUseAction(permissionRuleUseAction);
		  odrlPolicy.setPolicyId(URI.create("http://example.com/policy:restrict-access-interval"));
		  model.addAttribute(POLICY_FRAGMENT, "ReadDataIntervalPolicyForm");
	    return "index";
	  }

	@RequestMapping("/policy/InhibitReadDataIntervalPolicyForm")
	public String inhibitInterval(@ModelAttribute OdrlPolicy odrlPolicy,  Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.DATETIME);
		RightOperand secondRightOperand = new RightOperand();
		secondRightOperand.setType(RightOperandType.DATETIME);
		TimeIntervalCondition timeIntervalCondition = new TimeIntervalCondition(ConditionType.CONSTRAINT, Operator.GREATER, rightOperand, Operator.LESS, secondRightOperand, null);
		UseAction useAction = new UseAction();
		ProhibitionRuleUseAction prohibitionRuleUseAction = new ProhibitionRuleUseAction(useAction);
		prohibitionRuleUseAction.setTimeIntervalConstraint(timeIntervalCondition);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setProhibitionRuleUseAction(prohibitionRuleUseAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:restrict-access-interval"));
		model.addAttribute(POLICY_FRAGMENT, "ReadDataIntervalPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/ReadAfterPaymentPolicyForm")
	public String provideAfterPayment(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.DECIMAL);
		PaymentCondition paymentCondition = new PaymentCondition(ConditionType.CONSTRAINT, rightOperand, null);
		UseAction useAction = new UseAction();
		PermissionRuleUseAction permissionRuleUseAction = new PermissionRuleUseAction(useAction);
		permissionRuleUseAction.setPaymentConstraint(paymentCondition);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setPermissionRuleUseAction(permissionRuleUseAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:provide-access-after-payment"));
		model.addAttribute(POLICY_FRAGMENT, "ReadAfterPaymentPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/LogAccessPolicyForm")
	public String logPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		SystemDeviceCondition SystemDeviceRefinement = new SystemDeviceCondition(ConditionType.REFINEMENT, rightOperand, null);

		UseAction useAction = new UseAction();
		LogAction logDutyAction = new LogAction();
		logDutyAction.setSystemDeviceRefinement(SystemDeviceRefinement);
		PermissionRuleUseAction permissionRuleUseAction = new PermissionRuleUseAction(useAction);
		permissionRuleUseAction.setLogDutyAction(logDutyAction);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setPermissionRuleUseAction(permissionRuleUseAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:log-access"));
		model.addAttribute(POLICY_FRAGMENT, "LogAccessPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/EncodingPolicyForm")
	public String encodingPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.ANYURI);
		EncodingCondition encodingConstraint = new EncodingCondition(ConditionType.CONSTRAINT, rightOperand, null);
		DistributeAction distributeAction = new DistributeAction();
		PermissionRuleDistributeAction permissionRuleDistributeAction = new PermissionRuleDistributeAction(distributeAction);
		permissionRuleDistributeAction.setEncodingConstraint(encodingConstraint);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setPermissionRuleDistributeAction(permissionRuleDistributeAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:restrict-access-encoding"));
		model.addAttribute(POLICY_FRAGMENT, "EncodingPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/DistributeToThirdPartyPolicyForm")
	public String distributeToThirdPartyPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		DistributeAction distributeAction = new DistributeAction();
		NextPolicyAction nextPolicyDutyAction = new NextPolicyAction();
		PermissionRuleDistributeAction permissionRuleDistributeAction = new PermissionRuleDistributeAction(distributeAction);
		permissionRuleDistributeAction.setNextPolicyDutyAction(nextPolicyDutyAction);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setPermissionRuleDistributeAction(permissionRuleDistributeAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:distribute-policy"));
		model.addAttribute(POLICY_FRAGMENT, "DistributeToThirdPartyPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/PrintPolicyForm")
	public String inhibitPrintPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		PrintAction printAction = new PrintAction();
		ProhibitionRulePrintAction prohibitionRulePrintAction = new ProhibitionRulePrintAction(printAction);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setProhibitionRulePrintAction(prohibitionRulePrintAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:restrict-print"));
		model.addAttribute(POLICY_FRAGMENT, "BasePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/AnonymizeInRestPolicyForm")
	public String anonymizeInRestolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		AnonymizeAction anonymizeAction = new AnonymizeAction();
		ObligationRuleAnonymizeAction obligationRuleAnonymizeAction = new ObligationRuleAnonymizeAction(anonymizeAction);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setObligationRuleAnonymizeAction(obligationRuleAnonymizeAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:anonymize-in-rest"));
		model.addAttribute(POLICY_FRAGMENT, "BasePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/AnonymizeInTransitPolicyForm")
	public String anonymizeTransitPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand EventRightOperand = new RightOperand();
		EventRightOperand.setId(RightOperandId.POLICYUSAGE);
		EventCondition eventDutyConstraint = new EventCondition(ConditionType.CONSTRAINT, EventRightOperand, null);
		eventDutyConstraint.setOperator(Operator.LESS);

		RightOperand jsonPathRightOperand = new RightOperand();
		jsonPathRightOperand.setType(RightOperandType.STRING);
		JsonPathCondition JsonPathRefinement = new JsonPathCondition(ConditionType.REFINEMENT, jsonPathRightOperand, null);

		UseAction useAction = new UseAction();
		AnonymizeAction anonymizeAction = new AnonymizeAction();
		anonymizeAction.setJsonPathRefinement(JsonPathRefinement);
		PermissionRuleUseAction permissionRuleUseAction = new PermissionRuleUseAction(useAction);
		permissionRuleUseAction.setAnonymizeDutyAction(anonymizeAction);
		permissionRuleUseAction.setEventDutyConstraint(eventDutyConstraint);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setPermissionRuleUseAction(permissionRuleUseAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:anonymize-in-transit"));
		model.addAttribute(POLICY_FRAGMENT, "AnonymizeInTransitPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/CountAccessPolicyForm")
	public String countPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.DECIMAL);
		CountCondition countCondition = new CountCondition(ConditionType.CONSTRAINT, rightOperand, null);
		UseAction useAction = new UseAction();
		PermissionRuleUseAction permissionRuleUseAction = new PermissionRuleUseAction(useAction);
		permissionRuleUseAction.setCountConstraint(countCondition);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setPermissionRuleUseAction(permissionRuleUseAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:provide-access-after-payment"));
		model.addAttribute(POLICY_FRAGMENT, "CountAccessPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InformPolicyForm")
	public String policy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		UseAction useAction = new UseAction();
		InformAction informDutyAction = new InformAction();
		Party informedParty = new Party(PartyType.INFORMEDPARTY, URI.create("http://example.com/party/my-party"));
		PermissionRuleUseAction permissionRuleUseAction = new PermissionRuleUseAction(useAction);
		permissionRuleUseAction.setInformDutyAction(informDutyAction);
		permissionRuleUseAction.setInformedParty(informedParty);
		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setPermissionRuleUseAction(permissionRuleUseAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:notify"));
		model.addAttribute(POLICY_FRAGMENT, "InformPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/ComplexPolicyForm")
	public String complexPolicy(@ModelAttribute OdrlPolicy odrlPolicy, Model model) {
		RightOperand locationRightOperand = new RightOperand();
		locationRightOperand.setType(RightOperandType.ANYURI);
		AbsoluteSpatialPositionCondition locationConstraint = new AbsoluteSpatialPositionCondition(ConditionType.CONSTRAINT, locationRightOperand, null);

		RightOperand systemRightOperand = new RightOperand();
		systemRightOperand.setType(RightOperandType.ANYURI);
		SystemCondition systemConstraint = new SystemCondition(ConditionType.CONSTRAINT, systemRightOperand, null);

		RightOperand purposeRightOperand = new RightOperand();
		purposeRightOperand.setType(RightOperandType.ANYURI);
		PurposeCondition purposeConstraint = new PurposeCondition(ConditionType.CONSTRAINT, purposeRightOperand, null);

		RightOperand eventRightOperand = new RightOperand();
		eventRightOperand.setType(RightOperandType.ANYURI);
		EventCondition eventConstraint = new EventCondition(ConditionType.CONSTRAINT, eventRightOperand, null);

		RightOperand rightOperand = new RightOperand();
		rightOperand.setType(RightOperandType.DATETIME);
		RightOperand secondRightOperand = new RightOperand();
		secondRightOperand.setType(RightOperandType.DATETIME);
		TimeIntervalCondition timeIntervalCondition = new TimeIntervalCondition(ConditionType.CONSTRAINT, Operator.GREATER, rightOperand, Operator.LESS, secondRightOperand, null);

		RightOperand paymentRightOperand = new RightOperand();
		paymentRightOperand.setType(RightOperandType.DECIMAL);
		PaymentCondition paymentCondition = new PaymentCondition(ConditionType.CONSTRAINT, paymentRightOperand, null);

		UseAction useAction = new UseAction();
		PermissionRuleUseAction permissionRuleUseAction = new PermissionRuleUseAction(useAction);

		permissionRuleUseAction.setAbsoluteSpatialPositionConstraint(locationConstraint);
		permissionRuleUseAction.setSystemConstraint(systemConstraint);
		permissionRuleUseAction.setPurposeConstraint(purposeConstraint);
		permissionRuleUseAction.setEventConstraint(eventConstraint);
		permissionRuleUseAction.setTimeIntervalConstraint(timeIntervalCondition);
		permissionRuleUseAction.setPaymentConstraint(paymentCondition);

		Party consumer = new Party();
		consumer.setType(PartyType.CONSUMER);
		odrlPolicy.setConsumer(consumer);
		odrlPolicy.setPermissionRuleUseAction(permissionRuleUseAction);
		odrlPolicy.setPolicyId(URI.create("http://example.com/policy:complex-policy"));

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
	@RequestMapping("/policy/JsonOrdlPolicyMAYDATA")
	  public String policy(@ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		  Map map = null;
			try {
				Object o = JsonUtils.fromString(jsonOdrlPolicy.getJsonString());
				if (o instanceof Map) {
					map  = (Map) o;
				}
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		model.addAttribute("odrlFragment", "true");
		model.addAttribute("odrlPolicy", jsonOdrlPolicy.getJsonString());
		model.addAttribute("technologyDependentPolicyFragment", "true");
		boolean tempProviderSide = true;
		model.addAttribute("technologyDependentPolicy", TransformPolicy.createTechnologyDependentPolicy(map, tempProviderSide));
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
	  	UseAction useAction = new UseAction();
	  	PermissionRuleUseAction permissionRuleUseAction = new PermissionRuleUseAction();
	  	permissionRuleUseAction.setUseAction(useAction);
	  	basePolicy.setPermissionRuleUseAction(permissionRuleUseAction);
	  	basePolicy.setPolicyId(URI.create("http://example.com/policy:sample"));
	  	basePolicy.setTarget(URI.create("http://example.com/ids-data:sample"));
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
