/**
 * 
 */
package de.fraunhofer.iese.ids.odrl.pap.controller;

import de.fraunhofer.iese.ids.odrl.pap.Util.*;
import de.fraunhofer.iese.ids.odrl.pap.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
@Controller
public class OdrlPapUiController {
	
	public static final String POLICY_FRAGMENT = "policyFragment";
	
	  @RequestMapping("/")
	  public String index() {
	    return "index";
	  }

	@RequestMapping("/policy/ProvideAccessPolicyForm")
	public String policy(@ModelAttribute ProvideAccessPolicy provideAccessPolicy, Model model) {
		model.addAttribute(POLICY_FRAGMENT, "ProvideAccessPolicyForm");
		return "index";
	}

	@RequestMapping("/policy/ProvideAccessPolicyODRL")
	public String odrlPolicy(@ModelAttribute ProvideAccessPolicy provideAccessPolicy,  Model model) {
		model.addAttribute(POLICY_FRAGMENT, "odrl");
		model.addAttribute("odrlPolicy", ProvideAccessPolicyOdrlCreator.createODRL(provideAccessPolicy));
		return "index";
	}

	  @RequestMapping("/policy/SpecificPurposePolicyForm")
	  public String policy(@ModelAttribute SpecificPurposePolicy specificPurposePolicy,  Model model) {
		  model.addAttribute(POLICY_FRAGMENT, "SpecificPurposePolicyForm");
	    return "index";
	  }
	  
	  @RequestMapping("/policy/SpecificPurposePolicyODRL")
	  public String odrlPolicy(@ModelAttribute SpecificPurposePolicy specificPurposePolicy,  Model model) {
		  model.addAttribute(POLICY_FRAGMENT, "odrl");
		  model.addAttribute("odrlPolicy", SpecificPurposePolicyOdrlCreator.createODRL(specificPurposePolicy));
	    return "index";
	  }
	  
	  @RequestMapping("/policy/DeleteAfterPolicyForm")
	  public String policy(@ModelAttribute("deleteAfterPolicy") DeleteAtferPolicy deleteAfterPolicy,  Model model) {
		  Duration duration = new Duration();
		  duration.setValue();
		  duration.setTimeUnit();
		  deleteAfterPolicy.setDuration(duration);
	  	model.addAttribute(POLICY_FRAGMENT, "DeleteAfterPolicyForm");
	    return "index";
	  }

	  @RequestMapping("/policy/DeleteAfterPolicyODRL")
	  public String odrlPolicy(@ModelAttribute DeleteAtferPolicy deleteAtferPolicy,  Model model) {
	  	model.addAttribute(POLICY_FRAGMENT, "odrl");
		model.addAttribute("odrlPolicy", DeleteAfterPolicyOdrlCreator.createODRL(deleteAtferPolicy));
		return "index";
	}

	  @RequestMapping("/policy/ReadDataIntervalPolicyForm")
	  public String policy(@ModelAttribute ReadDataIntervalPolicy readDataIntervalPolicy,  Model model) {
		  model.addAttribute(POLICY_FRAGMENT, "ReadDataIntervalPolicyForm");
	    return "index";
	  }

	@RequestMapping("/policy/ReadDataIntervalPolicyODRL")
	public String odrlPolicy(@ModelAttribute ReadDataIntervalPolicy readDataIntervalPolicy,  Model model) {
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
	public String odrlPolicy(@ModelAttribute LogAccessPolicy logAccessPolicy,  Model model) {
		model.addAttribute(POLICY_FRAGMENT, "odrl");
		model.addAttribute("odrlPolicy", LogAccessPolicyOdrlCreator.createODRL(logAccessPolicy));
		return "index";
	}
}
