package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.EncodingCondition;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.EventCondition;

public interface IDistributeAction extends IAction {


    public EventCondition getEventRefinement();
    public void setEventRefinement(EventCondition eventRefinement);

    public EncodingCondition getEncodingRefinement();
    public void setEncodingRefinement(EncodingCondition encodingRefinement);
}
