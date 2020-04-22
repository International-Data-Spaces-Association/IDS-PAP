package de.fraunhofer.iese.ids.odrl.pap.model.MydataModel;


import de.fraunhofer.iese.ids.odrl.pap.model.ActionType;
import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import lombok.Data;

@Data
public class Count implements Operand {
 String solution;
 LeftOperand leftOperand;
 Parameter[] parameters;
 FixedTime fixedTime;
 ActionType action;

 public Count(String solution, LeftOperand leftOperand, ActionType action, Parameter[] parameters, FixedTime fixedTime) {
  this.solution = solution;
  this.leftOperand = leftOperand;
  this.action = action;
  this.parameters = parameters;
  this.fixedTime = fixedTime;
 }

 public Count() {
 }


 @Override
 public String toString() {
  if(null != leftOperand)
  {
   return  "            <count>  \r\n" +
           "              <eventOccurrence event='urn:action:"+ solution +":"+ leftOperand.getMydataLeftOperand() +"'>  \r\n" +
           getParameters() +
           "              </eventOccurrence>   \r\n"+
           "              <when fixedTime='"+ fixedTime.getFixedTime() +"'/>  \r\n" +
           "            </count>  \r\n" ;
  }else if(null != action){
   return  "            <count>  \r\n" +
           "              <eventOccurrence event='urn:action:"+ solution +":"+ action.name().toLowerCase() +"'>  \r\n" +
           getParameters() +
           "              </eventOccurrence>   \r\n"+
           "              <when fixedTime='"+ fixedTime.getFixedTime() +"'/>  \r\n" +
           "            </count>  \r\n" ;
  }
  return "";
 }

 private String getParameters() {
  if(parameters == null || parameters.length == 0)
  {
   return "";
  }else //if bigger
  {
   String params = "";
   for(int i=0 ; i<parameters.length; i++)
   {
    params += "                " +parameters[i].toString() + "\r\n";
   }

   return  params;
  }
 }
}
