package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel;


import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.Operator;
import lombok.Data;

@Data
public class Condition {
 ConditionType type;
 Operator operator;
 LeftOperand leftOperand;
 String rightOperandValue;
 String rightOperandType;

 public Condition(ConditionType type, Operator operator, LeftOperand leftOperand, String rightOperandValue, String rightOperandType) {
  this.type = type;
  this.operator = operator;
  this.leftOperand = leftOperand;
  this.rightOperandValue = rightOperandValue;
  this.rightOperandType = rightOperandType;
 }


 public void setOperator(Operator op){
  this.operator = op;
 }

 public Operator getOperator() {
  return operator;
 }

 @Override
 public String toString() {
  return  "      \" "+ type.getOdrlConditionType() +" \": [{  \n" +
          "        \"leftOperand\": \" "+ leftOperand.getIdsLeftOperand() +" \",  \n" +
          "        \"operator\": \" "+ operator.getOdrlOp() +" \",  \n" +
          "        \"rightOperand\": { \"@value\": \" "+ rightOperandValue +" \", \"@type\": \" "+ rightOperandType +" \" }  \n" +
          "      }]";
 }
}
