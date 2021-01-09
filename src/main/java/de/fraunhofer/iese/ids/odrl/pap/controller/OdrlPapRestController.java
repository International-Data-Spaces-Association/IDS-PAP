package de.fraunhofer.iese.ids.odrl.pap.controller;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.fraunhofer.iese.ids.odrl.pap.model.JsonOdrlPolicy;
import de.fraunhofer.iese.ids.odrl.pap.util.OdrlCreator;
import de.fraunhofer.iese.ids.odrl.pap.util.TransformPolicy;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Action;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Condition;
import de.fraunhofer.iese.ids.odrl.policy.library.model.ModificationMethodParameter;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Party;
import de.fraunhofer.iese.ids.odrl.policy.library.model.RightOperand;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Rule;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.ActionType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.ConditionType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.LeftOperand;
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
	
	
	@PostMapping("/policy/ProvideAccess")
	public String accessPolicy(@RequestBody RecievedOdrlPolicy recievedPolicy) {	
		List<Condition> constraints = new ArrayList<>();
		String uid = "http://example.com/policy/restrict-access";
		if (recievedPolicy.getLocation() != "") {
			RightOperand locationRightOperand = new RightOperand(recievedPolicy.getLocation(), RightOperandType.ANYURI);
			Condition locationConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ABSOLUTESPATIALPOSITION, Operator.EQUALS, locationRightOperand, "");
			constraints.add(locationConstraint);
			uid = "http://example.com/policy/restrict-access-location";
		}
		if (recievedPolicy.getSystem() != "") {
			RightOperand systemRightOperand = new RightOperand(recievedPolicy.getSystem(), RightOperandType.ANYURI);
			systemRightOperand.setType(RightOperandType.ANYURI);
			Condition systemConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.SYSTEM, Operator.EQUALS, systemRightOperand, "");		
			constraints.add(systemConstraint);
			uid = "http://example.com/policy/restrict-access-system";
		}
		if (recievedPolicy.getPurpose() != "") {
			RightOperand purposeRightOperand = new RightOperand(recievedPolicy.getPurpose(), RightOperandType.ANYURI);
			Condition purposeConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.PURPOSE, Operator.EQUALS, purposeRightOperand, "");
			constraints.add(purposeConstraint);
			uid = "http://example.com/policy/restrict-access-purpose";
		}
		if (recievedPolicy.getEvent() != "") {
			RightOperand eventRightOperand = new RightOperand(recievedPolicy.getEvent(), RightOperandType.ANYURI);
			Condition eventConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.EVENT, Operator.EQUALS, eventRightOperand, "");
			constraints.add(eventConstraint);
			uid = "http://example.com/policy/restrict-access-event";
		}
		if (recievedPolicy.getRestrictTimeIntervalStart() != "") {
			RightOperand rightOperand = new RightOperand(recievedPolicy.getRestrictTimeIntervalStart(),RightOperandType.DATETIME);
			RightOperand secondRightOperand = new RightOperand(recievedPolicy.getRestrictTimeIntervalEnd(),RightOperandType.DATETIME);
			Condition timeIntervalCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.DATETIME, Operator.GREATER, rightOperand, Operator.LESS, secondRightOperand,"");
			constraints.add(timeIntervalCondition);
			uid = "http://example.com/policy/restrict-access-interval";
		}
		if (recievedPolicy.getPayment() != "") {
			RightOperand paymentRightOperand = new RightOperand(String.valueOf(recievedPolicy.getPrice()), RightOperandType.DECIMAL);
			Condition paymentCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.PAYAMOUNT, Operator.EQUALS, paymentRightOperand, "");
			paymentCondition.setUnit("http://dbpedia.org/resource/Euro");
			paymentCondition.setContract(recievedPolicy.getPayment());
			constraints.add(paymentCondition);
			uid = "http://example.com/policy/restrict-access-after-payment";
		}
		
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setConstraints(constraints);
		
		String s = addHeaderAndCreatePolicy( recievedPolicy, rule, uid);
		return s;
	}
	
	@PostMapping("/policy/ComplexPolicyForm")
	public String complexPolicy(@RequestBody RecievedOdrlPolicy recievedPolicy) {
		List<Condition> constraints = new ArrayList<>();

		if (recievedPolicy.getLocation() != "") {
			RightOperand locationRightOperand = new RightOperand(recievedPolicy.getLocation(), RightOperandType.ANYURI);
			Condition locationConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ABSOLUTESPATIALPOSITION, Operator.EQUALS, locationRightOperand, "");
			constraints.add(locationConstraint);
		}
		if (recievedPolicy.getSystem() != "") {
			RightOperand systemRightOperand = new RightOperand(recievedPolicy.getSystem(), RightOperandType.ANYURI);
			systemRightOperand.setType(RightOperandType.ANYURI);
			Condition systemConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.SYSTEM, Operator.EQUALS, systemRightOperand, "");
			constraints.add(systemConstraint);
		}
		if (recievedPolicy.getPurpose() != "") {
			RightOperand purposeRightOperand = new RightOperand("http://example.com/ids-purpose:" + recievedPolicy.getPurpose(), RightOperandType.ANYURI);
			Condition purposeConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.PURPOSE, Operator.EQUALS, purposeRightOperand, "");
			constraints.add(purposeConstraint);
		}
		if (recievedPolicy.getEvent() != "") {
			RightOperand eventRightOperand = new RightOperand(recievedPolicy.getEvent(), RightOperandType.ANYURI);
			Condition eventConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.EVENT, Operator.EQUALS, eventRightOperand, "");
			constraints.add(eventConstraint);
		}
		if (recievedPolicy.getRestrictTimeIntervalStart() != "") {
			RightOperand rightOperand = new RightOperand(recievedPolicy.getRestrictTimeIntervalStart(),RightOperandType.DATETIME);
			RightOperand secondRightOperand = new RightOperand(recievedPolicy.getRestrictTimeIntervalEnd(),RightOperandType.DATETIME);
			Condition timeIntervalCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.DATETIME, Operator.GREATER, rightOperand, Operator.LESS, secondRightOperand,"");
			constraints.add(timeIntervalCondition);
		}
		if (recievedPolicy.getPayment() != "") {
			RightOperand paymentRightOperand = new RightOperand(String.valueOf(recievedPolicy.getPrice()), RightOperandType.DECIMAL);
			Condition paymentCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.PAYAMOUNT, Operator.EQUALS, paymentRightOperand, "");
			paymentCondition.setUnit("http://dbpedia.org/resource/Euro");
			paymentCondition.setContract(recievedPolicy.getPayment());
			constraints.add(paymentCondition);
		}
		
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setConstraints(constraints);
		String s = addHeaderAndCreatePolicy( recievedPolicy, rule, "http://example.com/policy/complex-policy");
		return s;
	}
	
	@PostMapping("/policy/CountAccessPolicyForm")
	public String countPolicy(@RequestBody RecievedOdrlPolicy recievedPolicy) {

		RightOperand rightOperand = new RightOperand(Integer.toString(recievedPolicy.getCounter()), RightOperandType.DECIMAL);
		Condition countCondition = new Condition(ConditionType.CONSTRAINT, LeftOperand.COUNT, Operator.EQUALS, rightOperand, "");
		
		List<Condition> constraints = new ArrayList<>();
		constraints.add(countCondition);
		Action useAction = new Action(ActionType.USE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		rule.setConstraints(constraints);
		String s = addHeaderAndCreatePolicy( recievedPolicy, rule, "http://example.com/policy/count-access");
		return s;
	}
	
	@PostMapping("/policy/deletePolicyAfterUsage")
	public String deletePolicyAfterUsage(@RequestBody RecievedOdrlPolicy recievedPolicy) {

		List<Condition> refinements = new ArrayList<>();
		if (recievedPolicy.getTimeAndDate() != "") {
			RightOperand dateTimeRightOperand = new RightOperand(recievedPolicy.getTimeAndDate(), RightOperandType.DATETIME);
			Condition dateTimeRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.DATETIME, Operator.EQUALS, dateTimeRightOperand, "");
			refinements.add(dateTimeRefinement);
		}
		if (recievedPolicy.getTime() != "") {
			RightOperand delayPeriodRightOperand = new RightOperand(recievedPolicy.getTime(), RightOperandType.DURATION);
			Condition delayPeriodRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.DELAYPERIOD, Operator.EQUALS, delayPeriodRightOperand, "");
			delayPeriodRefinement.setTimeUnit(TimeUnit.valueOf(recievedPolicy.getTimeUnit()));
			refinements.add(delayPeriodRefinement);
		}
		Action deleteAction = new Action(ActionType.DELETE);
		deleteAction.setRefinements(refinements);
		Rule rule = new Rule(RuleType.OBLIGATION, deleteAction);
		String s = addHeaderAndCreatePolicy( recievedPolicy, rule, "http://example.com/policy/delete-data");
		return s;
	}
	
	@PostMapping("/policy/deletePolicyAfterUsagePeriod")
	public String deletePolicyAfterUsagePeriod(@RequestBody RecievedOdrlPolicy recievedPolicy) {		
		Action useAction = new Action(ActionType.USE);
		Action deleteDutyAction = new Action(ActionType.DELETE);
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		Rule postobligation = new Rule(RuleType.POSTOBLIGATION, deleteDutyAction);
		List<Rule> postobligations = new ArrayList<>();
		postobligations.add(postobligation);
		rule.setPostobligations(postobligations);
		String s = addHeaderAndCreatePolicy( recievedPolicy, rule, "http://example.com/policy/delete-after-usage");
		return s;
	}
	
	@PostMapping("/policy/AnonymizeInRestPolicyForm")
	public String anonymizeInRestolicy(@RequestBody RecievedOdrlPolicy recievedPolicy) {

		Action anonymizeAction = new Action(ActionType.ANONYMIZE);
		Rule rule = new Rule(RuleType.OBLIGATION, anonymizeAction);
		String s = addHeaderAndCreatePolicy( recievedPolicy, rule, "http://example.com/policy/anonymize-in-rest");
		return s;
	}
	
	@PostMapping("/policy/AnonymizeInTransitPolicyForm")
	public String anonymizeTransitPolicy(@RequestBody RecievedOdrlPolicy recievedPolicy) {
		List<Condition> refinements = new ArrayList<>();
		

		RightOperand modificationMethodRightOperand = new RightOperand(recievedPolicy.getModificator() ,RightOperandType.ANYURI);
		Condition modificationMethodRefinement = new Condition(ConditionType.REFINEMENT, LeftOperand.MODIFICATIONMETHOD, Operator.EQUALS, modificationMethodRightOperand, "");
		if (recievedPolicy.getFieldToChange() != "") {
			modificationMethodRefinement.setJsonPath(recievedPolicy.getFieldToChange());
		}
		ModificationMethodParameter replaceWithParameter = new ModificationMethodParameter(recievedPolicy.getValueToChange(),RightOperandType.STRING);
		modificationMethodRefinement.setReplaceWith(replaceWithParameter);
		
		refinements.add(modificationMethodRefinement);
		
		Action useAction = new Action(ActionType.USE);
		Action anonymizeDutyAction = new Action(ActionType.ANONYMIZE);
		anonymizeDutyAction.setRefinements(refinements);
		
		Rule rule = new Rule(RuleType.PERMISSION, useAction);
		Rule preobligation = new Rule(RuleType.PREOBLIGATION, anonymizeDutyAction);
		List<Rule> preobligations = new ArrayList<>();
		preobligations.add(preobligation);
		rule.setPreobligations(preobligations);
		String s = addHeaderAndCreatePolicy(recievedPolicy, rule, "http://example.com/policy/anonymize-in-transit");
		return s;
	}
	
	@PostMapping("/policy/LogAccessPolicyForm")
	public String logPolicy(@RequestBody RecievedOdrlPolicy recievedPolicy) {
		RightOperand rightOperand = new RightOperand(recievedPolicy.getSystemDevice(), RightOperandType.ANYURI);
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
		String s = addHeaderAndCreatePolicy(recievedPolicy, rule, "http://example.com/policy/log-access");
		return s;
	}
	
	@PostMapping("/policy/InformPolicyForm")
	public String policy(@RequestBody RecievedOdrlPolicy recievedPolicy) {
	  	RightOperand rightOperand = new RightOperand(recievedPolicy.getInformedParty(), RightOperandType.ANYURI);
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
		String s = addHeaderAndCreatePolicy(recievedPolicy, rule, "http://example.com/policy/notify");
		return s;
	}
	
	@PostMapping("/policy/DistributePolicyForm")
	public String encodingPolicy(@RequestBody RecievedOdrlPolicy recievedPolicy) {
		if (recievedPolicy.getEncoding() != "") {
			List<Condition> constraints = new ArrayList<>();
			RightOperand rightOperand = new RightOperand(recievedPolicy.getEncoding(), RightOperandType.ANYURI);
			Condition encodingConstraint = new Condition(ConditionType.CONSTRAINT, LeftOperand.ENCODING, Operator.EQUALS, rightOperand, "");
			constraints.add(encodingConstraint);
			Action distributeAction = new Action(ActionType.DISTRIBUTE);
			Rule rule = new Rule(RuleType.PERMISSION, distributeAction);
			rule.setConstraints(constraints);
			String s = addHeaderAndCreatePolicy(recievedPolicy, rule, "http://example.com/policy/restrict-access-encoding");
			return s;
		} else {
			RightOperand rightOperand = new RightOperand(recievedPolicy.getPolicy(), RightOperandType.ANYURI);
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
			String s = addHeaderAndCreatePolicy(recievedPolicy, rule, "http://example.com/policy/distribute-policy");
			return s;
		}
	}
	
	@PostMapping("/policy/JsonOdrlPolicyMYDATA")
	public String policy(@RequestBody String jsonPolicy) {
		OdrlPolicy odrlPolicy = IdsOdrlUtil.getOdrlPolicy(jsonPolicy);
		boolean tempProviderSide = true;
		return TransformPolicy.createTechnologyDependentPolicy(odrlPolicy, tempProviderSide);
	}

	
	private String addHeaderAndCreatePolicy(RecievedOdrlPolicy recievedPolicy, Rule rule, String uri) {
		OdrlPolicy odrlPolicy = new OdrlPolicy();
		List<Rule> rules = new ArrayList<>();
		rules.add(rule);
		
		odrlPolicy.setConsumer(createConsumer(recievedPolicy));
		odrlPolicy.setRules(rules);
		odrlPolicy.setPolicyId(URI.create(uri));
		odrlPolicy.setType(PolicyType.getFromIdsString("ids:Contract"+recievedPolicy.getPolicyType()));
		odrlPolicy.setTarget(URI.create(recievedPolicy.getTarget()));
		//odrlPolicy.setProvider(new Party(PartyType.CONSUMER, URI .create(recievedPolicy.getProvider())));
		String jsonPolicy = OdrlCreator.createODRL(odrlPolicy);
		String dtPolicy = policy(jsonPolicy);
		String policies = jsonPolicy + "DTPOLICY:"+ dtPolicy;
		return policies;

	}
	
	private Party createConsumer(RecievedOdrlPolicy recievedPolicy) {
		Party consumer = null;
		try {
			consumer = new Party(PartyType.CONSUMER, new URI(recievedPolicy.getConsumer()));
			consumer.setType(PartyType.CONSUMER);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return consumer;
	}
	
}
