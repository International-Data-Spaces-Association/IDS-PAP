package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel;


import de.fraunhofer.iese.ids.odrl.pap.model.*;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.Condition;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.*;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.*;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.*;
import de.fraunhofer.iese.ids.odrl.pap.util.BuildMydataPolicyUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class PermissionRuleReadAction implements IPermissionRuleReadAction {
 RuleType type;
 ReadAction readAction;
 AnonymizeAction anonymizeDutyAction;
 DeleteAction deleteDutyAction;
 LogAction logDutyAction;
 InformAction informDutyAction;
 EventCondition eventDutyConstraint;
 Party informedParty;
 EventCondition eventConstraint;
 PurposeCondition purposeConstraint;
 TimeIntervalCondition timeIntervalConstraint;
 SystemCondition systemConstraint;
 SystemDeviceCondition systemDeviceConstraint;
 RecipientCondition recipientConstraint;
 PaymentCondition paymentConstraint;
 JsonPathCondition jsonPathConstraint;
 EncodingCondition encodingConstraint;
 DelayPeriodCondition delayPeriodConstraint;
 DateTimeCondition dateTimeConstraint;
 CountCondition countConstraint;
 AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint;

 private String solution = "";

 public PermissionRuleReadAction()
 {
  this.type = RuleType.PERMISSION;
 }

 public PermissionRuleReadAction(IReadAction readAction)
 {
  this.type = RuleType.PERMISSION;
  this.readAction = (ReadAction) readAction;
 }

 @Override
 public String toString() {
  ArrayList<Object> conditions= this.getAllConstraints();
  return  "  \"" + RuleType.PERMISSION.getOdrlRuleType() + "\": [{    \r\n" +
          readAction.toString() + getDutyBlock() + getConstraintBlock(conditions) + "\r\n" +
          "  }]    \r\n";
 }

 private String getDutyBlock()
 {
  ArrayList<Object> dutyConditions= this.getAllDutyConstraints();
  if(null != this.deleteDutyAction)
  {
   return  ",\r\n" +
           "     \"duty\": [{ \r\n" +
           this.deleteDutyAction.toString() + getConstraintBlock(dutyConditions) + "\r\n" +
           "    }]        \r\n" ;
  }
  else if(null != this.anonymizeDutyAction)
  {
   return  ",\r\n" +
           "     \"duty\": [{ \r\n" +
           this.anonymizeDutyAction.toString() + getConstraintBlock(dutyConditions) + "\r\n" +
           "    }]        \r\n" ;
  }
  else if(null != this.logDutyAction)
  {
   return  ",\r\n" +
           "     \"duty\": [{ \r\n" +
           this.logDutyAction.toString() + getConstraintBlock(dutyConditions) + "\r\n" +
           "    }]        \r\n" ;
  }
  else if(null != this.informDutyAction)
  {
   return  ",\r\n" +
           "     \"duty\": [{ \r\n" +
           this.informDutyAction.toString() + ",\r\n" + getInformedPartyBlock() + getConstraintBlock(dutyConditions) + "\r\n" +
           "    }]        \r\n" ;
  }
  return "";
 }

 private String getInformedPartyBlock()
 {
  if (null != this.informedParty)
  {
   return "      \""+ PartyFunction.INFORMEDPARTY.getIdsPartyFunction() + "\": \""+ this.informedParty.getUri().toString() +"\"  \r\n" ;
  }
  return "";
 }

 private ArrayList<Object> getAllDutyConstraints() {
  ArrayList<Object> constraints = new ArrayList<>();
  if(null != this.eventDutyConstraint)
  {
   constraints.add(this.eventDutyConstraint);
  }
  return constraints;
 }

 private String getConstraintBlock(ArrayList<Object> conditions) {

  String conditionInnerBlock = "";
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
  if(null != this.countConstraint && null != this.countConstraint.getRightOperand().getValue())
  {
   constraints.add(this.countConstraint);
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
  if(null != this.paymentConstraint && null != this.paymentConstraint.getRightOperand().getValue())
  {
   constraints.add(this.paymentConstraint);
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
 public void setReadAction(IReadAction readAction) {
  this.readAction = (ReadAction) readAction;
 }

 @Override
 public void setDeleteDutyAction(IDeleteAction deleteDutyAction) {
  this.deleteDutyAction = (DeleteAction) deleteDutyAction;
 }

 @Override
 public void setAnonymizeDutyAction(IAnonymizeAction anonymizeDutyAction) {
  this.anonymizeDutyAction = (AnonymizeAction) anonymizeDutyAction;
 }

 @Override
 public void setLogDutyAction(ILogAction logDutyAction) {
  this.logDutyAction = (LogAction) logDutyAction;
 }

 @Override
 public void setInformDutyAction(IInformAction informDutyAction) {
  this.informDutyAction = (InformAction) informDutyAction;
 }
}
