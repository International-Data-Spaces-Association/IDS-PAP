package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.RuleType;

public interface IObligationRuleLogAction {

	public RuleType getType();
	public void setType(RuleType type);

	public ILogAction getLogAction();
	public void setLogAction(ILogAction logAction);
}
