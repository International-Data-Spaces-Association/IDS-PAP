package de.fraunhofer.iese.ids.odrl.pap.model.MydataModel;


import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import lombok.Data;

@Data
public class Count implements Operand {
 String solution;
 LeftOperand leftOperand;
 Parameter[] parameters;
 FixedTime fixedTime;

 public Count(String solution, LeftOperand leftOperand, Parameter[] parameters, FixedTime fixedTime) {
  this.solution = solution;
  this.leftOperand = leftOperand;
  this.parameters = parameters;
  this.fixedTime = fixedTime;
 }

 public Count() {
 }


 @Override
 public String toString() {
  return  "            <count>  \r\n" +
          "              <eventOccurrence event='urn:action:"+ solution +":"+ leftOperand.getMydataLeftOperand() +"'>  \r\n" +
          getParameters() +
          "              </eventOccurrence>   \r\n"+
          "              <when fixedTime='"+ fixedTime.getFixedTime() +"'/>  \r\n" +
          "            </count>  \r\n" ;
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
