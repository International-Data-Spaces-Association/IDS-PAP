package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition;


import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.ConditionType;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.RightOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.Operator;
import lombok.Data;

@Data
public class CountCondition {
 ConditionType type;
 Operator operator;
 LeftOperand leftOperand;
 RightOperand rightOperand;
 String comment;

 public CountCondition()
 {
  this.leftOperand = LeftOperand.COUNT;
 }

 public CountCondition(ConditionType conditionType, RightOperand rightOperand, String comment) {
  this.type = conditionType;
  this.operator = Operator.LESS;
  this.leftOperand = LeftOperand.COUNT;
  this.rightOperand= rightOperand;
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
