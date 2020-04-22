package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.EventCondition;

public interface INextPolicyAction extends IAction {


    public EventCondition getEventRefinement();
    public void setEventRefinement(EventCondition eventRefinement);
    
}
