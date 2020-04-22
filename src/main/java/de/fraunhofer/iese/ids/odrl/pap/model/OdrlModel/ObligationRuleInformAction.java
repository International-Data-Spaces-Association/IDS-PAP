package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel;


import de.fraunhofer.iese.ids.odrl.pap.model.ActionType;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.ExecuteAction;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.MydataPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.IInformAction;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.IObligationRuleInformAction;
import de.fraunhofer.iese.ids.odrl.pap.util.BuildMydataPolicyUtils;
import lombok.Data;

import java.util.ArrayList;


@Data
public class ObligationRuleInformAction implements IObligationRuleInformAction {
 RuleType type;
 InformAction informAction;

private String solution = "";

 public ObligationRuleInformAction()
 {
  this.type = RuleType.OBLIGATION;
 }

 public ObligationRuleInformAction(IInformAction informAction)
 {
  this.type = RuleType.OBLIGATION;
  this.informAction = (InformAction) informAction;
 }

 @Override
 public String toString() {
  return  "  \"" + RuleType.OBLIGATION.getOdrlRuleType() + "\": [{    \r\n" +
          informAction.toString() + getConstraintBlock() + "\r\n" +
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
 public void setInformAction(IInformAction informAction) {
  this.informAction = (InformAction) informAction;
 }

}
