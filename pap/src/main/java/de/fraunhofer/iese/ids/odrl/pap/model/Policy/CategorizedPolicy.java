package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import de.fraunhofer.iese.ids.odrl.pap.model.Action;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.Condition;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;
import de.fraunhofer.iese.ids.odrl.pap.model.RuleType;

import java.net.URL;

public interface CategorizedPolicy {

	public RuleType getRuleType();
	public void setRuleType(RuleType ruleType);

	public Action getAction();
	public void setAction(Action action);

	public Action getDutyAction();
	public void setDutyAction(Action dutyAction);

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

	public boolean getProviderSide();
	public void setProviderSide(boolean providerSide);
}
