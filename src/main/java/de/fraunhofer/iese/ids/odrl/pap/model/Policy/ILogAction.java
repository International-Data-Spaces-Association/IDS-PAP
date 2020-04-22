package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.EventCondition;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.SystemDeviceCondition;

public interface ILogAction extends IAction {


    public EventCondition getEventRefinement();
    public void setEventRefinement(EventCondition eventRefinement);

    public SystemDeviceCondition getSystemDeviceRefinement();
    public void setSystemDeviceRefinement(SystemDeviceCondition systemDeviceRefinement);
    
}
