package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel;


import de.fraunhofer.iese.ids.odrl.pap.model.ActionType;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.Action;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.EventCondition;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.IReadAction;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.IUseAction;
import lombok.Data;

import java.util.ArrayList;


@Data
 public class ReadAction extends Action implements IReadAction {
  ActionType type;
  EventCondition eventRefinement;

 public ReadAction()
 {
  this.type = ActionType.READ;
 }

  public ReadAction(EventCondition eventRefinement) {
   this.type = ActionType.READ;
   this.eventRefinement = eventRefinement;
  }

  @Override
  public String toString() {
   String refinementBlock = this.getRefinementBlock();
   if(refinementBlock != "")
   {
    return  "    \"action\": [{\n" +
            "      \"rdf:value\": { \"@id\": \""+ type.getIdsAction() +"\" }" + refinementBlock + "\r\n" +
            "    }]";
   }else {
    return "      \"action\": \"" + type.getIdsAction() + "\"";
   }
  }

 private String getRefinementBlock() {

  String conditionInnerBlock = "";
  ArrayList<Object> conditions= this.getAllRefinements();
  if (!conditions.isEmpty())
  {
   String firstCondition = conditions.get(0).toString();
   String otherConditions = "";
   for(int i=1 ; i<conditions.size(); i++)
   {
    otherConditions += "," + conditions.get(i).toString();
   }
   conditionInnerBlock = firstCondition + otherConditions;
   conditions.clear();
   return String.format(",     \r\n" +
           "      \"refinement\": [%s] " , conditionInnerBlock);
  }

  return "";
 }

 private ArrayList<Object> getAllRefinements() {
  ArrayList<Object> refinements = new ArrayList<>();
  if(null != this.eventRefinement && null != this.eventRefinement.getRightOperand().getValue())
  {
   refinements.add(this.eventRefinement);
  }
  return refinements;
 }
}
