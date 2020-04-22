package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel;


import de.fraunhofer.iese.ids.odrl.pap.model.ActionType;
import de.fraunhofer.iese.ids.odrl.pap.model.Duration;
import de.fraunhofer.iese.ids.odrl.pap.model.IntervalCondition;
import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.*;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.DateTimeCondition;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.DelayPeriodCondition;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.EventCondition;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.IDeleteAction;
import de.fraunhofer.iese.ids.odrl.pap.util.BuildMydataPolicyUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class DeleteAction extends Action implements IDeleteAction {
 ActionType type;
 EventCondition eventRefinement;
 DelayPeriodCondition delayPeriodRefinement;
 DateTimeCondition dateTimeRefinement;
 private String solution = "";

 public DeleteAction()
 {
  this.type = ActionType.DELETE;
 }

 public DeleteAction(EventCondition eventRefinement) {
  this.type = ActionType.DELETE;
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
  if(null != this.delayPeriodRefinement && null != this.delayPeriodRefinement.getRightOperand().getValue())
  {
   refinements.add(this.delayPeriodRefinement);
  }
  if(null != this.dateTimeRefinement && null != this.dateTimeRefinement.getRightOperand().getValue())
  {
   refinements.add(this.dateTimeRefinement);
  }
  return refinements;
 }


}
