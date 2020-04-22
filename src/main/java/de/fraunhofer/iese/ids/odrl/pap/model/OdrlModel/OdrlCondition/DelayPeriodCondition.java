package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition;


import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.ConditionType;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.RightOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.Operator;
import de.fraunhofer.iese.ids.odrl.pap.model.TimeUnit;
import lombok.Data;

import java.sql.Time;

@Data
public class DelayPeriodCondition {
 ConditionType type;
 Operator operator;
 LeftOperand leftOperand;
 RightOperand rightOperand;
 String comment;
 TimeUnit timeUnit;

 public DelayPeriodCondition()
 {
  this.leftOperand = LeftOperand.DELAYPERIOD;
  this.timeUnit = TimeUnit.HOURS;
 }

 public DelayPeriodCondition(ConditionType conditionType, RightOperand rightOperand, String comment, TimeUnit timeUnit) {
  this.type = conditionType;
  this.operator = Operator.EQUALS;
  this.leftOperand = LeftOperand.DELAYPERIOD;
  this.rightOperand= rightOperand;
  this.comment = comment;
  this.timeUnit = timeUnit;
 }

 @Override
 public String toString() {
  String commentBlock = getCommentBlock();

  return !rightOperand.toString().isEmpty() ?"{    \r\n" +
          "        \"leftOperand\": \""+ leftOperand.getIdsLeftOperand() +"\",  \n" +
          "        \"operator\": \""+ operator.getOdrlOp() +"\",  \n" +
          "        \"rightOperand\": { " + rightOperand.toString() +"}" +
          commentBlock +
          " \n"+
          "      }":"";
 }

 private String getCommentBlock() {
  if(this.comment != null)
  {
   return ", \n"+
           "        \"comment\": \" "+ comment +" \"";
  }
  return "";
 }
}
