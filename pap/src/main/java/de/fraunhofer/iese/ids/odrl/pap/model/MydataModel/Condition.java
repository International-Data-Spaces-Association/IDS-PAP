package de.fraunhofer.iese.ids.odrl.pap.model.MydataModel;


import de.fraunhofer.iese.ids.odrl.pap.model.Operator;
import de.fraunhofer.iese.ids.odrl.pap.model.TimeUnit;
import lombok.Data;

@Data
public class Condition {
 Operator operator;
 String eventParameter;
 String value;
 //TODO: for now, we are assuming that we only compare event and constant and the data type is string.

 public Condition(Operator operator, String eventParameter, String value)
 {
  this.operator = operator;
  this.eventParameter = eventParameter;
  this.value = value;
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
          "            <event:string eventParameter='" + eventParameter + "'>  \r\n" +
          "            <constant:string value='" + value + "'>  \r\n" +
          "          </" + operator.getMydataOp() + ">  \r\n";
 }
}
