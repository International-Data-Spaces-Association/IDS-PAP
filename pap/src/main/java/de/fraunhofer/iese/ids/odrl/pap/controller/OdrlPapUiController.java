/**
 * 
 */
package de.fraunhofer.iese.ids.odrl.pap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import de.fraunhofer.iese.ids.odrl.pap.Util.SpecificPurposePolicyOdrlCreator;
import de.fraunhofer.iese.ids.odrl.pap.model.DeleteAtferPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.ReadDataIntervalPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.SpecificPurposePolicy;

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
		  model.addAttribute(POLICY_FRAGMENT, "DeleteAfterPolicyForm");
	    return "index";
	  }
	  
	  @RequestMapping("/policy/ReadDataIntervalPolicyForm")
	  public String policy(@ModelAttribute ReadDataIntervalPolicy readDataIntervalPolicy,  Model model) {
		  model.addAttribute(POLICY_FRAGMENT, "ReadDataIntervalPolicyForm");
	    return "index";
	  }

}
