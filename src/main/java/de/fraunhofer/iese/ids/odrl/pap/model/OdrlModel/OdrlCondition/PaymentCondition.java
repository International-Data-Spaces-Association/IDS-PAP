package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition;


import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.ConditionType;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.RightOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.Operator;
import lombok.Data;

@Data
public class PaymentCondition {
 ConditionType type;
 Operator operator;
 LeftOperand leftOperand;
 RightOperand rightOperand;
 String unit;
 String contract;
 String comment;

 public PaymentCondition()
 {

 }

 public PaymentCondition(ConditionType conditionType, RightOperand rightOperand, String comment) {
  this.type = conditionType;
  this.operator = Operator.EQUALS;
  this.leftOperand = LeftOperand.PAYAMOUNT;
  this.rightOperand= rightOperand;
  this.unit = "http://dbpedia.org/resource/Euro";
  this.contract = "http://dbpedia.org/page/Rent";
  this.comment = comment;
 }

 @Override
 public String toString() {
  String contractBlock = getContractBlock();
  String unitBlock = getUnitBlock();
  String commentBlock = getCommentBlock();

  return  !rightOperand.toString().isEmpty() ?"{    \r\n" +
          "        \"leftOperand\": \""+ leftOperand.getIdsLeftOperand() +"\",  \n" +
          "        \"operator\": \""+ operator.getOdrlOp() +"\",  \n" +
          "        \"rightOperand\": { " + rightOperand.toString() +"}" +
          contractBlock +
          unitBlock +
          commentBlock +
          " \n"+
          "      }":"";
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
