package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.EventCondition;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.JsonPathCondition;

public interface IAnonymizeAction extends IAction {


    public EventCondition getEventRefinement();
    public void setEventRefinement(EventCondition eventRefinement);

    public JsonPathCondition getJsonPathRefinement();
    public void setJsonPathRefinement(JsonPathCondition jsonPathRefinement);
    
}
