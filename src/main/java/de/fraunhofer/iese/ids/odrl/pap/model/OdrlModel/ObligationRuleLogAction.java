package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel;


import de.fraunhofer.iese.ids.odrl.pap.model.ActionType;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.ExecuteAction;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.MydataPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.ILogAction;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.IObligationRuleLogAction;
import de.fraunhofer.iese.ids.odrl.pap.util.BuildMydataPolicyUtils;
import lombok.Data;

import java.util.ArrayList;


@Data
public class ObligationRuleLogAction implements IObligationRuleLogAction {
 RuleType type;
 LogAction logAction;

private String solution = "";

 public ObligationRuleLogAction()
 {
  this.type = RuleType.OBLIGATION;
 }

 public ObligationRuleLogAction(ILogAction logAction)
 {
  this.type = RuleType.OBLIGATION;
  this.logAction = (LogAction) logAction;
 }

 @Override
 public String toString() {
  return  "  \"" + RuleType.OBLIGATION.getOdrlRuleType() + "\": [{    \r\n" +
          logAction.toString() + getConstraintBlock() + "\r\n" +
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
 public void setLogAction(ILogAction logAction) {
  this.logAction = (LogAction) logAction;
 }
}
