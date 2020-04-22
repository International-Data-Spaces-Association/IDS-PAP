package de.fraunhofer.iese.ids.odrl.pap.model.MydataModel;


import de.fraunhofer.iese.ids.odrl.pap.model.Operator;
import de.fraunhofer.iese.ids.odrl.pap.model.TimeUnit;
import lombok.Data;

@Data
public class Condition {

 Operand firstOperand;
 Operator operator;
 Operand secondOperand;

 public Condition(Operand firstOperand, Operator operator, Operand secondOperand)
 {
  this.firstOperand = firstOperand;
  this.operator = operator;
  this.secondOperand = secondOperand;
 }

 public Condition() {
 }

 public void setOperator(Operator op){
  this.operator = op;
 }

 public Operator getOperator() {
  return operator;
 }

 @Override
 public String toString() {
  return  "          <" + operator.getMydataOp() + ">  \r\n" +
          firstOperand +
          secondOperand +
          "          </" + operator.getMydataOp() + ">  \r\n";
 }
}
