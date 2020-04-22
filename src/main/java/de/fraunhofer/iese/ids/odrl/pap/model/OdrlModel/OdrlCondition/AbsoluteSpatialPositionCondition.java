package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition;


import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.ConditionType;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.RightOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.Operator;
import lombok.Data;

@Data
public class AbsoluteSpatialPositionCondition {
 ConditionType type;
 Operator operator;
 LeftOperand leftOperand;
 RightOperand rightOperand;
 String comment;

 public AbsoluteSpatialPositionCondition()
 {
  this.leftOperand = LeftOperand.ABSOLUTESPATIALPOSITION;
 }

 public AbsoluteSpatialPositionCondition(ConditionType conditionType, RightOperand rightOperand, String comment) {
  this.type = conditionType;
  this.operator = Operator.EQUALS;
  this.leftOperand = LeftOperand.ABSOLUTESPATIALPOSITION;
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
