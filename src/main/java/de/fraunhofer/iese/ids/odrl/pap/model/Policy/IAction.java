package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import de.fraunhofer.iese.ids.odrl.pap.model.ActionType;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.Condition;

import java.util.List;

public abstract interface IAction {

	public ActionType getType();
	public void setType(ActionType type);

}
