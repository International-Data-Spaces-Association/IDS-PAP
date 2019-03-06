/**
 * 
 */
package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import java.net.URL;

import de.fraunhofer.iese.ids.odrl.pap.model.Action;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.Condition;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;
import de.fraunhofer.iese.ids.odrl.pap.model.RuleType;
import lombok.Data;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
@Data
public abstract class AbstractPolicy implements CategorizedPolicy{
	RuleType ruleType;
	Action action;
	URL policyUrl;
	PolicyType policyType;
	Condition condition;
	URL dataUrl;
	String assigner;
	String assignee;
	boolean providerSide;

	public AbstractPolicy(){
		this.assigner = "http://example.com/ids-party:my-party";
	}

	public RuleType getRuleType() {
		return ruleType;
	}

	public Action getAction() {
		return action;
	}

	public URL getPolicyUrl() {
		return policyUrl;
	}

	public PolicyType getPolicyType() {
		return policyType;
	}

	public String getAssignee() {
		return assignee;
	}

	public URL getDataUrl() {
		return dataUrl;
	}

	public String getAssigner() {
		return assigner;
	}

	public boolean getProviderSide() {
		return providerSide;
	}
}
