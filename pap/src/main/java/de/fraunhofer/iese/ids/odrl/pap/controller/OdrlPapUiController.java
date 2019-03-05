/**
 * 
 */
package de.fraunhofer.iese.ids.odrl.pap.controller;

import java.io.IOException;
import java.util.Map;

import de.fraunhofer.iese.ids.odrl.pap.Util.*;
import de.fraunhofer.iese.ids.odrl.pap.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonParseException;
import com.github.jsonldjava.utils.JsonUtils;

import de.fraunhofer.iese.ids.odrl.pattern.PatternUtil;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
@Controller
public class OdrlPapUiController {
	
	public static final String POLICY_FRAGMENT = "policyFragment";
	
	  @RequestMapping("/")
	  public String index(Model model) {
		  model.addAttribute(POLICY_FRAGMENT, "index");
	  	return "index";
	  }

	@RequestMapping("/policy/ProvideAccessPolicyForm")
	public String accessPolicy(@ModelAttribute ProvideAccessPolicy provideAccessPolicy, Model model) {
	  	provideAccessPolicy.setRuleType(RuleType.PERMISSION);
		model.addAttribute(POLICY_FRAGMENT, "ProvideAccessPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/InhibitAccessPolicyForm")
	public String inhibitPolicy(@ModelAttribute ProvideAccessPolicy provideAccessPolicy, Model model) {
		provideAccessPolicy.setRuleType(RuleType.PROHIBITION);
		model.addAttribute(POLICY_FRAGMENT, "ProvideAccessPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/ProvideAccessPolicyODRL")
	public String odrlPolicy(@ModelAttribute ProvideAccessPolicy provideAccessPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy, Model model) {
		model.addAttribute(POLICY_FRAGMENT, "odrl");
		model.addAttribute("odrlPolicy", ProvideAccessPolicyOdrlCreator.createODRL(provideAccessPolicy));
		return "index";
	}

	  @RequestMapping("/policy/SpecificPurposePolicyForm")
	  public String providePurposePolicy(@ModelAttribute SpecificPurposePolicy specificPurposePolicy,  Model model) {
		  specificPurposePolicy.setRuleType(RuleType.PERMISSION);
		  model.addAttribute(POLICY_FRAGMENT, "SpecificPurposePolicyForm");
	    return "index";
	  }

	@RequestMapping("/policy/InhibitSpecificPurposePolicyForm")
	public String inhibitPurposePolicy(@ModelAttribute SpecificPurposePolicy specificPurposePolicy,  Model model) {
		specificPurposePolicy.setRuleType(RuleType.PROHIBITION);
		model.addAttribute(POLICY_FRAGMENT, "SpecificPurposePolicyForm");
		return "index";
	}
	  
	  @RequestMapping("/policy/SpecificPurposePolicyODRL")
	  public String odrlPolicy(@ModelAttribute SpecificPurposePolicy specificPurposePolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		  model.addAttribute(POLICY_FRAGMENT, "odrl");
		  model.addAttribute("odrlPolicy", SpecificPurposePolicyOdrlCreator.createODRL(specificPurposePolicy));
	    return "index";
	  }
	  
	  @RequestMapping("/policy/DeleteAfterPolicyForm")
	  public String policy(@ModelAttribute("deleteAfterPolicy") DeleteAtferPolicy deleteAfterPolicy,  Model model) {
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
	  public String provideInterval(@ModelAttribute ReadDataIntervalPolicy readDataIntervalPolicy,  Model model) {
		  readDataIntervalPolicy.setRuleType(RuleType.PERMISSION);
		  model.addAttribute(POLICY_FRAGMENT, "ReadDataIntervalPolicyForm");
	    return "index";
	  }

	@RequestMapping("/policy/InhibitReadDataIntervalPolicyForm")
	public String inhibitInterval(@ModelAttribute ReadDataIntervalPolicy readDataIntervalPolicy,  Model model) {
		readDataIntervalPolicy.setRuleType(RuleType.PROHIBITION);
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
	public String policy(@ModelAttribute LogAccessPolicy logAccessPolicy,  Model model) {
		model.addAttribute(POLICY_FRAGMENT, "LogAccessPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/LogAccessPolicyODRL")
	public String odrlPolicy(@ModelAttribute LogAccessPolicy logAccessPolicy, @ModelAttribute JsonOdrlPolicy jsonOdrlPolicy,  Model model) {
		model.addAttribute(POLICY_FRAGMENT, "odrl");
		model.addAttribute("odrlPolicy", LogAccessPolicyOdrlCreator.createODRL(logAccessPolicy));
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
