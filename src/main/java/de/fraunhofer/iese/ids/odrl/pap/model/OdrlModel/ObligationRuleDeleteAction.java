package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel;


import de.fraunhofer.iese.ids.odrl.pap.model.ActionType;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.ExecuteAction;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.MydataPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.DateTimeCondition;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.DelayPeriodCondition;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.IDeleteAction;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.IObligationRuleDeleteAction;
import de.fraunhofer.iese.ids.odrl.pap.util.BuildMydataPolicyUtils;
import lombok.Data;

import java.util.ArrayList;


@Data
public class ObligationRuleDeleteAction implements IObligationRuleDeleteAction {
 RuleType type;
 DeleteAction deleteAction;
 DelayPeriodCondition delayPeriodConstraint;
 DateTimeCondition dateTimeConstraint;

private String solution = "";

 public ObligationRuleDeleteAction()
 {
  this.type = RuleType.OBLIGATION;
 }

 public ObligationRuleDeleteAction(IDeleteAction deleteAction)
 {
  this.type = RuleType.OBLIGATION;
  this.deleteAction = (DeleteAction) deleteAction;
 }

 @Override
 public String toString() {
  return  "  \"" + RuleType.OBLIGATION.getOdrlRuleType() + "\": [{    \r\n" +
          deleteAction.toString() + getConstraintBlock() + "\r\n" +
          "  }]    \r\n";
 }

 private String getConstraintBlock() {

  String conditionInnerBlock = "";
  ArrayList<Object> conditions= this.getAllConstraints();
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
           "      \"constraint\": [%s] " , conditionInnerBlock);
  }

  return "";
 }

 private ArrayList<Object> getAllConstraints() {
  ArrayList<Object> constraints = new ArrayList<>();

  return constraints;
 }


 @Override
 public void setDeleteAction(IDeleteAction deleteAction) {
  this.deleteAction = (DeleteAction) deleteAction;
 }
}
