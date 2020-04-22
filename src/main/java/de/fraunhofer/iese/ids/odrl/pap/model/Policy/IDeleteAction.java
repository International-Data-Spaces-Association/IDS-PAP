package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.DateTime;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.DateTimeCondition;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.DelayPeriodCondition;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.EventCondition;

public interface IDeleteAction extends IAction {


    public EventCondition getEventRefinement();
    public void setEventRefinement(EventCondition eventRefinement);

    public DelayPeriodCondition getDelayPeriodRefinement();
    public void setDelayPeriodRefinement(DelayPeriodCondition delayPeriodRefinement);

    public DateTimeCondition getDateTimeRefinement();
    public void setDateTimeRefinement(DateTimeCondition dateTimeRefinement);
    
}
