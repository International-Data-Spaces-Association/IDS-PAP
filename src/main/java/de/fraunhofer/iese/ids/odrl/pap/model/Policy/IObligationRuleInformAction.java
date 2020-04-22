package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.*;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.RuleType;

public interface IObligationRuleInformAction {

	public RuleType getType();
	public void setType(RuleType type);

	public IInformAction getInformAction();
	public void setInformAction(IInformAction informAction);
}
