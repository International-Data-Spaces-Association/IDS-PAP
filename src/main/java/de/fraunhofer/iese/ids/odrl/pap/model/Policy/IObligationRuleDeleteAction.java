package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.DateTimeCondition;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.DelayPeriodCondition;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.RuleType;

public interface IObligationRuleDeleteAction {

	public RuleType getType();
	public void setType(RuleType type);

	public IDeleteAction getDeleteAction ();
	public void setDeleteAction (IDeleteAction deleteAction );

	public DelayPeriodCondition getDelayPeriodConstraint();
	public void setDelayPeriodConstraint(DelayPeriodCondition DelayPeriodConstraint);

	public DateTimeCondition getDateTimeConstraint();
	public void setDateTimeConstraint(DateTimeCondition dateTimeConstraint);
}
