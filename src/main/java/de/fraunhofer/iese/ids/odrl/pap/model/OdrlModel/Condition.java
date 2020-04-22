package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel;


import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.Operator;
import lombok.Data;

@Data
public class Condition {
 ConditionType type;
 Operator operator;
 LeftOperand leftOperand;
 RightOperand rightOperand;
 String unit;
 String contract;
 String comment;

 public Condition()
 {

 }

 public Condition(ConditionType conditionType, Operator operator, LeftOperand leftOperand, RightOperand rightOperand, String unit, String contract, String comment) {
  this.type = conditionType;
  this.operator = operator;
  this.leftOperand = leftOperand;
  this.rightOperand= rightOperand;
  this.unit = unit;
  this.contract = contract;
  this.comment = comment;
 }

 @Override
 public String toString() {
  String contractBlock = getContractBlock();
  String unitBlock = getUnitBlock();
  String commentBlock = getCommentBlock();

  return  "      \"constraint\": [{    \r\n" +
          "        \"leftOperand\": \""+ leftOperand.getIdsLeftOperand() +"\",  \n" +
          "        \"operator\": \""+ operator.getOdrlOp() +"\",  \n" +
          "        \"rightOperand\": { " + rightOperand.toString() +"}" +
          contractBlock +
          unitBlock +
          commentBlock +
          " \n"+
          "      }]     \r\n";
 }


 private String getContractBlock() {
  if(this.contract != null)
  {
   return ", \n"+
           "        \"ids:contract\": \""+ contract +"\"";
  }
  return "";
 }

 private String getUnitBlock() {
  if(this.unit != null)
  {
   return ", \n"+
           "        \"unit\": \""+ unit +"\"";
  }
  return "";
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
