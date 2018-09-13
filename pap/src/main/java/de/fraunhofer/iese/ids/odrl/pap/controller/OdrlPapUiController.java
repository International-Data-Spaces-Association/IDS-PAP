/**
 * 
 */
package de.fraunhofer.iese.ids.odrl.pap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
@Controller
public class OdrlPapUiController {
	
	  @RequestMapping("/")
	  public String index() {
	    return "index";
	  }
	  
	  @RequestMapping("/policy/{name}(")
	  public String policy(@PathVariable String name) {
	    return name;
	  }

}
