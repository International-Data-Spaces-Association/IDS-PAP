package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.RuleType;

public interface IObligationRuleAnonymizeAction {

	public RuleType getType();
	public void setType(RuleType type);

	public IAnonymizeAction getAnonymizeAction();
	public void setAnonymizeAction(IAnonymizeAction anonymizeAction);
}
