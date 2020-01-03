/**
 * 
 */
package de.fraunhofer.iese.ids.odrl.pap.controller;

import java.io.IOException;
import java.util.Map;

import de.fraunhofer.iese.ids.odrl.pap.Util.*;
import de.fraunhofer.iese.ids.odrl.pap.model.*;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.*;
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
	public String accessPolicy(@ModelAttribute AbstractPolicy provideAccessPolicy, Model model) {
		provideAccessPolicy.setRuleType(RuleType.PERMISSION);
		provideAccessPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "BasePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InhibitAccessPolicyForm")
	public String inhibitPolicy(@ModelAttribute AbstractPolicy inhibitAccessPolicy, Model model) {
		inhibitAccessPolicy.setRuleType(RuleType.PROHIBITION);
		inhibitAccessPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "BasePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/BasePolicyODRL")
	public String odrlBasicPolicy(@ModelAttribute AbstractPolicy basePolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy, Model model) {
		model.addAttribute("odrlFragment", "true");
		model.addAttribute("odrlPolicy", BasePolicyOdrlCreator.createODRL(basePolicy));
		return "index";
	}

	  @RequestMapping("/policy/SpecificPurposePolicyForm")
	  public String providePurposePolicy(@ModelAttribute AbstractPolicy specificPurposePolicy, Model model) {
		  specificPurposePolicy.setRuleType(RuleType.PERMISSION);
		  specificPurposePolicy.setAction(Action.READ);
		  model.addAttribute(POLICY_FRAGMENT, "SpecificPurposePolicyForm");
	    return "index";
	  }

	@RequestMapping("/policy/InhibitSpecificPurposePolicyForm")
	public String inhibitPurposePolicy(@ModelAttribute AbstractPolicy specificPurposePolicy,  Model model) {
		specificPurposePolicy.setRuleType(RuleType.PROHIBITION);
		specificPurposePolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "SpecificPurposePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/SpecificPurposePolicyODRL")
	public String odrlPurposePolicy(@ModelAttribute AbstractPolicy specificPurposePolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute("odrlFragment", "true");
		model.addAttribute("odrlPolicy", SpecificPurposePolicyOdrlCreator.createODRL(specificPurposePolicy));
		return "index";
	}

	@RequestMapping("/policy/SpecificSystemPolicyForm")
	public String provideSystemPolicy(@ModelAttribute AbstractPolicy specificSystemPolicy,  Model model) {
		specificSystemPolicy.setRuleType(RuleType.PERMISSION);
		specificSystemPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "SpecificSystemPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InhibitSpecificSystemPolicyForm")
	public String inhibitSystemPolicy(@ModelAttribute AbstractPolicy specificSystemPolicy,  Model model) {
		specificSystemPolicy.setRuleType(RuleType.PROHIBITION);
		specificSystemPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "SpecificSystemPolicyForm");
		return "index";
	}

	  @RequestMapping("/policy/SpecificSystemPolicyODRL")
	  public String odrlSystemPolicy(@ModelAttribute AbstractPolicy specificSystemPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		  model.addAttribute("odrlFragment", "true");
		  model.addAttribute("odrlPolicy", SpecificSystemPolicyOdrlCreator.createODRL(specificSystemPolicy));
	    return "index";
	  }

	@RequestMapping("/policy/SpecificLocationPolicyForm")
	public String provideLocationPolicy(@ModelAttribute AbstractPolicy specificLocationPolicy,  Model model) {
		specificLocationPolicy.setRuleType(RuleType.PERMISSION);
		specificLocationPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "SpecificLocationPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InhibitSpecificLocationPolicyForm")
	public String inhibitLocationPolicy(@ModelAttribute AbstractPolicy specificLocationPolicy,  Model model) {
		specificLocationPolicy.setRuleType(RuleType.PROHIBITION);
		specificLocationPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "SpecificLocationPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/SpecificLocationPolicyODRL")
	public String odrlLocationPolicy(@ModelAttribute AbstractPolicy specificLocationPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute("odrlFragment", "true");
		model.addAttribute("odrlPolicy", SpecificLocationPolicyOdrlCreator.createODRL(specificLocationPolicy));
		return "index";
	}


	@RequestMapping("/policy/SpecificEventPolicyForm")
	public String provideEventPolicy(@ModelAttribute AbstractPolicy specificEventPolicy,  Model model) {
		specificEventPolicy.setRuleType(RuleType.PERMISSION);
		specificEventPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "SpecificEventPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InhibitSpecificEventPolicyForm")
	public String inhibitEventPolicy(@ModelAttribute AbstractPolicy specificEventPolicy,  Model model) {
		specificEventPolicy.setRuleType(RuleType.PROHIBITION);
		specificEventPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "SpecificEventPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/SpecificEventPolicyODRL")
	public String odrlEventPolicy(@ModelAttribute AbstractPolicy specificEventPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute("odrlFragment", "true");
		model.addAttribute("odrlPolicy", SpecificEventPolicyOdrlCreator.createODRL(specificEventPolicy));
		return "index";
	}

	  @RequestMapping("/policy/DeleteAfterUsagePeriodPolicyForm")
	  public String deletePolicyAfterUsagePeriod(@ModelAttribute AbstractPolicy deleteAfterPolicy, Model model) {
		  deleteAfterPolicy.setRuleType(RuleType.OBLIGATION);
		  deleteAfterPolicy.setAction(Action.DELETE);
		  Duration duration = new Duration(1, TimeUnit.HOURS);
		  duration.setValue();
		  duration.setTimeUnit();
		  deleteAfterPolicy.setDuration(duration);
	  	model.addAttribute(POLICY_FRAGMENT, "DeleteAfterUsagePeriodPolicyForm");
	    return "index";
	  }

	@RequestMapping("/policy/DeleteAfterPolicyODRL")
	public String odrlDeletePolicy(@ModelAttribute AbstractPolicy deleteAtferPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute("odrlFragment", "true");
		model.addAttribute("odrlPolicy", DeleteAfterPolicyOdrlCreator.createODRL(deleteAtferPolicy));
		return "index";
	}

	@RequestMapping("/policy/DeleteAfterUsagePolicyForm")
	public String deletePolicyAfterUsage(@ModelAttribute AbstractPolicy deleteAfterPolicy, Model model) {
		deleteAfterPolicy.setRuleType(RuleType.PERMISSION);
		deleteAfterPolicy.setAction(Action.USE);
		deleteAfterPolicy.setDutyAction(Action.DELETE);
		model.addAttribute(POLICY_FRAGMENT, "DeleteAfterUsagePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/DeleteAfterEachUsagePolicyODRL")
	public String odrlDeleteAfterEachUsagePolicy(@ModelAttribute AbstractPolicy deleteAtferPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute("odrlFragment", "true");
		model.addAttribute("odrlPolicy", DeleteAfterEachUsagePolicyOdrlCreator.createODRL(deleteAtferPolicy));
		return "index";
	}

	  @RequestMapping("/policy/ReadDataIntervalPolicyForm")
	  public String provideInterval(@ModelAttribute AbstractPolicy readDataIntervalPolicy, Model model) {
		  readDataIntervalPolicy.setRuleType(RuleType.PERMISSION);
		  readDataIntervalPolicy.setAction(Action.READ);
		  model.addAttribute(POLICY_FRAGMENT, "ReadDataIntervalPolicyForm");
	    return "index";
	  }

	@RequestMapping("/policy/InhibitReadDataIntervalPolicyForm")
	public String inhibitInterval(@ModelAttribute AbstractPolicy readDataIntervalPolicy,  Model model) {
		readDataIntervalPolicy.setRuleType(RuleType.PROHIBITION);
		readDataIntervalPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "ReadDataIntervalPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/ReadDataIntervalPolicyODRL")
	public String odrlIntervalPolicy(@ModelAttribute AbstractPolicy readDataIntervalPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute("odrlFragment", "true");
		model.addAttribute("odrlPolicy", ReadDataIntervalPolicyOdrlCreator.createODRL(readDataIntervalPolicy));
		return "index";
	}

	@RequestMapping("/policy/ReadAfterPaymentPolicyForm")
	public String provideAfterPayment(@ModelAttribute AbstractPolicy readDataPaymentPolicy, Model model) {
		readDataPaymentPolicy.setRuleType(RuleType.PERMISSION);
		readDataPaymentPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "ReadAfterPaymentPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/ReadAfterPaymentPolicyODRL")
	public String odrlAfterPayment(@ModelAttribute AbstractPolicy readDataPaymentPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute("odrlFragment", "true");
		model.addAttribute("odrlPolicy", ReadAfterPaymentPolicyOdrlCreator.createODRL(readDataPaymentPolicy));
		return "index";
	}

	@RequestMapping("/policy/LogAccessPolicyForm")
	public String logPolicy(@ModelAttribute AbstractPolicy logAccessPolicy, Model model) {
		logAccessPolicy.setRuleType(RuleType.PERMISSION);
		logAccessPolicy.setAction(Action.READ);
		logAccessPolicy.setDutyAction(Action.LOG);
		model.addAttribute(POLICY_FRAGMENT, "LogAccessPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/LogAccessPolicyODRL")
	public String odrlLogPolicy(@ModelAttribute AbstractPolicy logAccessPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute("odrlFragment", "true");
		model.addAttribute("odrlPolicy", LogAccessPolicyOdrlCreator.createODRL(logAccessPolicy));
		return "index";
	}

	@RequestMapping("/policy/EncodingPolicyForm")
	public String encodingPolicy(@ModelAttribute AbstractPolicy encodingPolicy, Model model) {
		encodingPolicy.setRuleType(RuleType.PERMISSION);
		encodingPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "EncodingPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/EncodingPolicyODRL")
	public String odrlEncodingPolicy(@ModelAttribute AbstractPolicy encodingPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute("odrlFragment", "true");
		model.addAttribute("odrlPolicy", EncodingPolicyOdrlCreator.createODRL(encodingPolicy));
		return "index";
	}

	@RequestMapping("/policy/DistributeToThirdPartyPolicyForm")
	public String distributeToThirdPartyPolicy(@ModelAttribute AbstractPolicy distributeToThirdPartyPolicy, Model model) {
		distributeToThirdPartyPolicy.setRuleType(RuleType.PERMISSION);
		distributeToThirdPartyPolicy.setAction(Action.READ);
		distributeToThirdPartyPolicy.setDutyAction(Action.NEXTPOLICY);
		model.addAttribute(POLICY_FRAGMENT, "DistributeToThirdPartyPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/DistributeToThirdPartyPolicyODRL")
	public String odrlDistributeToThirdPartyPolicy(@ModelAttribute AbstractPolicy distributeToThirdPartyPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute("odrlFragment", "true");
		model.addAttribute("odrlPolicy", DistributeToThirdPartyPolicyOdrlCreator.createODRL(distributeToThirdPartyPolicy));
		return "index";
	}

	@RequestMapping("/policy/PrintPolicyForm")
	public String inhibitPrintPolicy(@ModelAttribute AbstractPolicy printPolicy, Model model) {
		printPolicy.setRuleType(RuleType.PROHIBITION);
		printPolicy.setAction(Action.PRINT);
		model.addAttribute(POLICY_FRAGMENT, "BasePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/AnonymizeInRestPolicyForm")
	public String anonymizeInRestolicy(@ModelAttribute AbstractPolicy anonymizeInRestPolicy, Model model) {
		anonymizeInRestPolicy.setRuleType(RuleType.OBLIGATION);
		anonymizeInRestPolicy.setAction(Action.ANONYMIZE);
		model.addAttribute(POLICY_FRAGMENT, "BasePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/AnonymizeInTransitPolicyForm")
	public String anonymizeTransitPolicy(@ModelAttribute AbstractPolicy anonymizeInTransitPolicy, Model model) {
		anonymizeInTransitPolicy.setRuleType(RuleType.PERMISSION);
		anonymizeInTransitPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "AnonymizeInTransitPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/AnonymizeInTransitPolicyODRL")
	public String odrlAnonymizeInTransitPolicy(@ModelAttribute AbstractPolicy anonymizeInTransitPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute("odrlFragment", "true");
		model.addAttribute("odrlPolicy", AnonymizeInTransitPolicyOdrlCreator.createODRL(anonymizeInTransitPolicy));
		return "index";
	}

	@RequestMapping("/policy/CountAccessPolicyForm")
	public String countPolicy(@ModelAttribute AbstractPolicy countAccessPolicy, Model model) {
		countAccessPolicy.setRuleType(RuleType.PERMISSION);
		countAccessPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "CountAccessPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/CountAccessPolicyODRL")
	public String odrlCountPolicy(@ModelAttribute AbstractPolicy countAccessPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute("odrlFragment", "true");
		model.addAttribute("odrlPolicy", CountAccessPolicyOdrlCreator.createODRL(countAccessPolicy));
		return "index";
	}

	@RequestMapping("/policy/InformPolicyForm")
	public String policy(@ModelAttribute AbstractPolicy informPolicy, Model model) {
		informPolicy.setRuleType(RuleType.PERMISSION);
		informPolicy.setAction(Action.READ);
		informPolicy.setDutyAction(Action.INFORM);
		informPolicy.setInformedParty("http://example.com/ids-party:my-party");
		model.addAttribute(POLICY_FRAGMENT, "InformPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InformPolicyODRL")
	public String odrlInformPolicy(@ModelAttribute AbstractPolicy informPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute("odrlFragment", "true");
		model.addAttribute("odrlPolicy", InformPolicyOdrlCreator.createODRL(informPolicy));
		return "index";
	}

	@RequestMapping("/policy/ComplexPolicyForm")
	public String complexPolicy(@ModelAttribute AbstractPolicy abstractPolicy, Model model) {
		abstractPolicy.setRuleType(RuleType.PERMISSION);
		abstractPolicy.setAction(Action.USE);
		model.addAttribute(POLICY_FRAGMENT, "ComplexPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/ComplexPolicyODRL")
	public String odrlComplexPolicy(@ModelAttribute AbstractPolicy abstractPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute("odrlFragment", "true");
		model.addAttribute("odrlPolicy", ComplexPolicyOdrlCreator.createODRL(abstractPolicy));
		return "index";
	}

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
		model.addAttribute("mydataFragment", "true");
		boolean tempProviderSide = true;
		model.addAttribute("mydataPolicy", MydataCreator.createMYDATA(map, tempProviderSide));
	    return "index";
	  }
}
