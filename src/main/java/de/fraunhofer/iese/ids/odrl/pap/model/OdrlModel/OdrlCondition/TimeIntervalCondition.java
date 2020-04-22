package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition;


import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.ConditionType;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.RightOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.Operator;
import lombok.Data;

@Data
public class TimeIntervalCondition {
 ConditionType type;
 Operator operator;
 Operator secondOperator;
 LeftOperand leftOperand;
 RightOperand rightOperand;
 RightOperand secondRightOperand;
 String comment;

 public TimeIntervalCondition()
 {
  this.leftOperand = LeftOperand.DATETIME;
 }

 public TimeIntervalCondition(ConditionType type, String comment) {
  this.type = type;
  this.leftOperand = LeftOperand.DATETIME;
  this.comment = comment;
 }

 public TimeIntervalCondition(ConditionType conditionType, Operator operator, RightOperand rightOperand, Operator secondOperator, RightOperand secondRightOperand, String comment) {
  this.type = conditionType;
  this.operator = operator;
  this.secondOperator = secondOperator;
  this.leftOperand = LeftOperand.DATETIME;
  this.rightOperand= rightOperand;
  this.secondRightOperand = secondRightOperand;
  this.comment = comment;
 }

 @Override
 public String toString() {
  String commentBlock = getCommentBlock();

  return  !rightOperand.toString().isEmpty() ?"{    \r\n" +
          "        \"leftOperand\": \""+ leftOperand.getIdsLeftOperand() +"\",  \n" +
          "        \"operator\": \""+ operator.getOdrlOp() +"\",  \n" +
          "        \"rightOperand\": { " + rightOperand.toString() +"}" +
          commentBlock +
          " \n"+
          "        },{ \n " +
          "        \"leftOperand\": \""+ leftOperand.getIdsLeftOperand() +"\",  \n" +
          "        \"operator\": \""+ secondOperator.getOdrlOp() +"\",  \n" +
          "        \"rightOperand\": { " + secondRightOperand.toString() +"}" +
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
