package de.fraunhofer.iese.ids.odrl.pap.model;

import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.Condition;

import java.net.URL;

public interface CategorizedPolicy {

	public RuleType getRuleType();
	public void setRuleType(RuleType ruleType);

	public Action getAction();
	public void setAction(Action action);

	public URL getPolicyUrl();
	public void setPolicyUrl(URL policyUrl);

	public PolicyType getPolicyType();
	public void setPolicyType(PolicyType policyType);

	public Condition getCondition();
	public void setCondition(Condition condition);

	public URL getDataUrl();
	public void setDataUrl(URL dataUrl);

	public String getAssignee();
	public void setAssignee(String assignee);
	
	public String getAssigner();
	public void setAssigner(String assigner);
}
