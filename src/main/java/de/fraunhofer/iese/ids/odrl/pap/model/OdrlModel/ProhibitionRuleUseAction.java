package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel;


import de.fraunhofer.iese.ids.odrl.pap.model.IntervalCondition;
import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.Condition;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.*;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.*;
import de.fraunhofer.iese.ids.odrl.pap.model.Operator;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.IProhibitionRuleUseAction;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.IUseAction;
import de.fraunhofer.iese.ids.odrl.pap.util.BuildMydataPolicyUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class ProhibitionRuleUseAction implements IProhibitionRuleUseAction {
 RuleType type;
 UseAction useAction;
 EventCondition eventConstraint;
 PurposeCondition purposeConstraint;
 TimeIntervalCondition timeIntervalConstraint;
 SystemCondition systemConstraint;
 SystemDeviceCondition systemDeviceConstraint;
 RecipientCondition recipientConstraint;
 JsonPathCondition jsonPathConstraint;
 EncodingCondition encodingConstraint;
 DelayPeriodCondition delayPeriodConstraint;
 DateTimeCondition dateTimeConstraint;
 AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint;

private String solution = "";

 public ProhibitionRuleUseAction()
 {
  this.type = RuleType.PROHIBITION;
 }

 public ProhibitionRuleUseAction(IUseAction useAction)
 {
  this.type = RuleType.PROHIBITION;
  this.useAction = (UseAction) useAction;
 }

 @Override
 public String toString() {
  return  "  \"" + RuleType.PROHIBITION.getOdrlRuleType() + "\": [{    \r\n" +
          useAction.toString() + getConstraintBlock() + "\r\n" +
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
  if(null != this.eventConstraint && null != this.eventConstraint.getRightOperand().getValue())
  {
   constraints.add(this.eventConstraint);
  }
  if(null != this.purposeConstraint && null != this.purposeConstraint.getRightOperand().getValue())
  {
   constraints.add(this.purposeConstraint);
  }
  if(null != this.systemConstraint && null != this.systemConstraint.getRightOperand().getValue())
  {
   constraints.add(this.systemConstraint);
  }
  if(null != this.systemDeviceConstraint && null != this.systemDeviceConstraint.getRightOperand().getValue())
  {
   constraints.add(this.systemDeviceConstraint);
  }
  if(null != this.dateTimeConstraint && null != this.dateTimeConstraint.getRightOperand().getValue())
  {
   constraints.add(this.dateTimeConstraint);
  }
  if(null != this.absoluteSpatialPositionConstraint && null != this.absoluteSpatialPositionConstraint.getRightOperand().getValue())
  {
   constraints.add(this.absoluteSpatialPositionConstraint);
  }
  if(null != this.timeIntervalConstraint && null != this.timeIntervalConstraint.getRightOperand().getValue())
  {
   constraints.add(this.timeIntervalConstraint);
  }
  if(null != this.jsonPathConstraint && null != this.jsonPathConstraint.getRightOperand().getValue())
  {
   constraints.add(this.jsonPathConstraint);
  }
  if(null != this.recipientConstraint && null != this.recipientConstraint.getRightOperand().getValue())
  {
   constraints.add(this.recipientConstraint);
  }
  if(null != this.encodingConstraint && null != this.encodingConstraint.getRightOperand().getValue())
  {
   constraints.add(this.encodingConstraint);
  }
  if(null != this.delayPeriodConstraint && null != this.delayPeriodConstraint.getRightOperand().getValue())
  {
   constraints.add(this.delayPeriodConstraint);
  }
  return constraints;
 }

 @Override
 public void setUseAction(IUseAction useAction) {
  this.useAction = (UseAction) useAction;
 }

}
