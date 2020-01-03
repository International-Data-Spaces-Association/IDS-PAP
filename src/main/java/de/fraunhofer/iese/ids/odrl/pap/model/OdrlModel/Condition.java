package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel;


import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.Operator;
import lombok.Data;

@Data
public class Condition {
 Operator operator;
 LeftOperand leftOperand;
 String rightOperandValue;
 String rightOperandType;
 String rightOperandId;
 String unit;
 String contract;
 String comment;

 public Condition(Operator operator, LeftOperand leftOperand, String rightOperandValue, String rightOperandType,
                  String rightOperandId, String unit, String contract, String comment) {
  this.operator = operator;
  this.leftOperand = leftOperand;
  this.rightOperandValue = rightOperandValue;
  this.rightOperandType = rightOperandType;
  this.rightOperandId = rightOperandId;
  this.unit = unit;
  this.contract = contract;
  this.comment = comment;
 }


 public void setOperator(Operator op){
  this.operator = op;
 }

 public Operator getOperator() {
  return operator;
 }

 @Override
 public String toString() {
  String rightOperandBlock = getRightOperandBlock();
  String contractBlock = getContractBlock();
  String unitBlock = getUnitBlock();
  String commentBlock = getCommentBlock();

  return  "{        \r\n" +
          "        \"leftOperand\": \""+ leftOperand.getIdsLeftOperand() +"\",  \n" +
          "        \"operator\": \""+ operator.getOdrlOp() +"\",  \n" +
          "        \"rightOperand\": { " + rightOperandBlock +"}" +
          contractBlock +
          unitBlock +
          commentBlock +
          " \n"+
          "      }";
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

 private String getRightOperandBlock() {
  if(this.rightOperandId != null && this.rightOperandValue == null && this.rightOperandType == null)
  {
   return "\"@id\": \""+ rightOperandId +"\"";
  }else if(this.rightOperandId == null && this.rightOperandValue != null && this.rightOperandType != null)
  {
   return "\"@value\": \""+ rightOperandValue +" \", \"@type\": \""+ rightOperandType +"\"";
  }else
  {
   return "\"@id\": \""+ rightOperandId + "\"@value\": \""+ rightOperandValue +"\", \"@type\": \""+ rightOperandType +"\"";
  }
 }


}
