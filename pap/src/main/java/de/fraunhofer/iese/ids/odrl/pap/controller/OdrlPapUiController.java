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
	public String accessPolicy(@ModelAttribute BasePolicy provideAccessPolicy, Model model) {
		provideAccessPolicy.setRuleType(RuleType.PERMISSION);
		provideAccessPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "BasePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InhibitAccessPolicyForm")
	public String inhibitPolicy(@ModelAttribute BasePolicy inhibitAccessPolicy, Model model) {
		inhibitAccessPolicy.setRuleType(RuleType.PROHIBITION);
		inhibitAccessPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "BasePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/BasePolicyODRL")
	public String odrlPolicy(@ModelAttribute BasePolicy basePolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy, Model model) {
		model.addAttribute(POLICY_FRAGMENT, "odrl");
		model.addAttribute("odrlPolicy", BasePolicyOdrlCreator.createODRL(basePolicy));
		return "index";
	}

	  @RequestMapping("/policy/SpecificPurposePolicyForm")
	  public String providePurposePolicy(@ModelAttribute SpecificPurposePolicy specificPurposePolicy, Model model) {
		  specificPurposePolicy.setRuleType(RuleType.PERMISSION);
		  specificPurposePolicy.setAction(Action.READ);
		  model.addAttribute(POLICY_FRAGMENT, "SpecificPurposePolicyForm");
	    return "index";
	  }

	@RequestMapping("/policy/InhibitSpecificPurposePolicyForm")
	public String inhibitPurposePolicy(@ModelAttribute SpecificPurposePolicy specificPurposePolicy,  Model model) {
		specificPurposePolicy.setRuleType(RuleType.PROHIBITION);
		specificPurposePolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "SpecificPurposePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/SpecificPurposePolicyODRL")
	public String odrlPolicy(@ModelAttribute SpecificPurposePolicy specificPurposePolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute(POLICY_FRAGMENT, "odrl");
		model.addAttribute("odrlPolicy", SpecificPurposePolicyOdrlCreator.createODRL(specificPurposePolicy));
		return "index";
	}

	@RequestMapping("/policy/SpecificSystemPolicyForm")
	public String provideSystemPolicy(@ModelAttribute SpecificSystemPolicy specificSystemPolicy,  Model model) {
		specificSystemPolicy.setRuleType(RuleType.PERMISSION);
		specificSystemPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "SpecificSystemPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InhibitSpecificSystemPolicyForm")
	public String inhibitSystemPolicy(@ModelAttribute SpecificSystemPolicy specificSystemPolicy,  Model model) {
		specificSystemPolicy.setRuleType(RuleType.PROHIBITION);
		specificSystemPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "SpecificSystemPolicyForm");
		return "index";
	}

	  @RequestMapping("/policy/SpecificSystemPolicyODRL")
	  public String odrlPolicy(@ModelAttribute SpecificSystemPolicy specificSystemPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		  model.addAttribute(POLICY_FRAGMENT, "odrl");
		  model.addAttribute("odrlPolicy", SpecificSystemPolicyOdrlCreator.createODRL(specificSystemPolicy));
	    return "index";
	  }

	@RequestMapping("/policy/SpecificEventPolicyForm")
	public String provideEventPolicy(@ModelAttribute SpecificEventPolicy specificEventPolicy,  Model model) {
		specificEventPolicy.setRuleType(RuleType.PERMISSION);
		specificEventPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "SpecificEventPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InhibitSpecificEventPolicyForm")
	public String inhibitEventPolicy(@ModelAttribute SpecificEventPolicy specificEventPolicy,  Model model) {
		specificEventPolicy.setRuleType(RuleType.PROHIBITION);
		specificEventPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "SpecificEventPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/SpecificEventPolicyODRL")
	public String odrlPolicy(@ModelAttribute SpecificEventPolicy specificEventPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute(POLICY_FRAGMENT, "odrl");
		model.addAttribute("odrlPolicy", SpecificEventPolicyOdrlCreator.createODRL(specificEventPolicy));
		return "index";
	}

	  @RequestMapping("/policy/DeleteAfterPolicyForm")
	  public String policy(@ModelAttribute("deleteAfterPolicy") DeleteAtferPolicy deleteAfterPolicy, Model model) {
		  deleteAfterPolicy.setRuleType(RuleType.OBLIGATION);
		  deleteAfterPolicy.setAction(Action.DELETE);
		  Duration duration = new Duration(1, TimeUnit.HOURS);
		  duration.setValue();
		  duration.setTimeUnit();
		  deleteAfterPolicy.setDuration(duration);
	  	model.addAttribute(POLICY_FRAGMENT, "DeleteAfterPolicyForm");
	    return "index";
	  }

	  @RequestMapping("/policy/DeleteAfterPolicyODRL")
	  public String odrlPolicy(@ModelAttribute DeleteAtferPolicy deleteAtferPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
	  	model.addAttribute(POLICY_FRAGMENT, "odrl");
		model.addAttribute("odrlPolicy", DeleteAfterPolicyOdrlCreator.createODRL(deleteAtferPolicy));
		return "index";
	}

	  @RequestMapping("/policy/ReadDataIntervalPolicyForm")
	  public String provideInterval(@ModelAttribute ReadDataIntervalPolicy readDataIntervalPolicy, Model model) {
		  readDataIntervalPolicy.setRuleType(RuleType.PERMISSION);
		  readDataIntervalPolicy.setAction(Action.READ);
		  model.addAttribute(POLICY_FRAGMENT, "ReadDataIntervalPolicyForm");
	    return "index";
	  }

	@RequestMapping("/policy/InhibitReadDataIntervalPolicyForm")
	public String inhibitInterval(@ModelAttribute ReadDataIntervalPolicy readDataIntervalPolicy,  Model model) {
		readDataIntervalPolicy.setRuleType(RuleType.PROHIBITION);
		readDataIntervalPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "ReadDataIntervalPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/ReadDataIntervalPolicyODRL")
	public String odrlPolicy(@ModelAttribute ReadDataIntervalPolicy readDataIntervalPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute(POLICY_FRAGMENT, "odrl");
		model.addAttribute("odrlPolicy", ReadDataIntervalPolicyOdrlCreator.createODRL(readDataIntervalPolicy));
		return "index";
	}

	@RequestMapping("/policy/LogAccessPolicyForm")
	public String policy(@ModelAttribute LogAccessPolicy logAccessPolicy, Model model) {
		logAccessPolicy.setRuleType(RuleType.PERMISSION);
		logAccessPolicy.setAction(Action.READ);
		logAccessPolicy.setDutyAction(Action.LOG);
		model.addAttribute(POLICY_FRAGMENT, "LogAccessPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/LogAccessPolicyODRL")
	public String odrlPolicy(@ModelAttribute LogAccessPolicy logAccessPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute(POLICY_FRAGMENT, "odrl");
		model.addAttribute("odrlPolicy", LogAccessPolicyOdrlCreator.createODRL(logAccessPolicy));
		return "index";
	}

	@RequestMapping("/policy/EncodingPolicyForm")
	public String policy(@ModelAttribute EncodingPolicy encodingPolicy, Model model) {
		encodingPolicy.setRuleType(RuleType.PERMISSION);
		encodingPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "EncodingPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/EncodingPolicyODRL")
	public String odrlPolicy(@ModelAttribute EncodingPolicy encodingPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute(POLICY_FRAGMENT, "odrl");
		model.addAttribute("odrlPolicy", EncodingPolicyOdrlCreator.createODRL(encodingPolicy));
		return "index";
	}

	@RequestMapping("/policy/PrintPolicyForm")
	public String inhibitPrintPolicy(@ModelAttribute BasePolicy printPolicy, Model model) {
		printPolicy.setRuleType(RuleType.PROHIBITION);
		printPolicy.setAction(Action.PRINT);
		model.addAttribute(POLICY_FRAGMENT, "BasePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/AnonymizeInRestPolicyForm")
	public String anonymizeInRestolicy(@ModelAttribute BasePolicy anonymizeInRestPolicy, Model model) {
		anonymizeInRestPolicy.setRuleType(RuleType.OBLIGATION);
		anonymizeInRestPolicy.setAction(Action.ANONYMIZE);
		model.addAttribute(POLICY_FRAGMENT, "BasePolicyForm");
		return "index";
	}

	@RequestMapping("/policy/AnonymizeInTransitPolicyForm")
	public String policy(@ModelAttribute AnonymizeInTransitPolicy anonymizeInTransitPolicy, Model model) {
		anonymizeInTransitPolicy.setRuleType(RuleType.PERMISSION);
		anonymizeInTransitPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "AnonymizeInTransitPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/AnonymizeInTransitPolicyODRL")
	public String odrlPolicy(@ModelAttribute AnonymizeInTransitPolicy anonymizeInTransitPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute(POLICY_FRAGMENT, "odrl");
		model.addAttribute("odrlPolicy", AnonymizeInTransitPolicyOdrlCreator.createODRL(anonymizeInTransitPolicy));
		return "index";
	}

	@RequestMapping("/policy/CountAccessPolicyForm")
	public String policy(@ModelAttribute CountAccessPolicy countAccessPolicy, Model model) {
		countAccessPolicy.setRuleType(RuleType.PERMISSION);
		countAccessPolicy.setAction(Action.READ);
		model.addAttribute(POLICY_FRAGMENT, "CountAccessPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/CountAccessPolicyODRL")
	public String odrlPolicy(@ModelAttribute CountAccessPolicy countAccessPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute(POLICY_FRAGMENT, "odrl");
		model.addAttribute("odrlPolicy", CountAccessPolicyOdrlCreator.createODRL(countAccessPolicy));
		return "index";
	}

	@RequestMapping("/policy/InformPolicyForm")
	public String policy(@ModelAttribute InformPolicy informPolicy, Model model) {
		informPolicy.setRuleType(RuleType.PERMISSION);
		informPolicy.setAction(Action.READ);
		informPolicy.setDutyAction(Action.INFORM);
		model.addAttribute(POLICY_FRAGMENT, "InformPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InformPolicyODRL")
	public String odrlPolicy(@ModelAttribute InformPolicy informPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute(POLICY_FRAGMENT, "odrl");
		model.addAttribute("odrlPolicy", InformPolicyOdrlCreator.createODRL(informPolicy));
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

		  model.addAttribute(POLICY_FRAGMENT, "mydata");
		 model.addAttribute("mydataPolicy", MydataCreator.createMYDATA(map));
	    return "index";
	  }
}
