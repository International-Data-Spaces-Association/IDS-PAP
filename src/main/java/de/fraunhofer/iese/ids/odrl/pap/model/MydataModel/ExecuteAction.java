package de.fraunhofer.iese.ids.odrl.pap.model.MydataModel;


import de.fraunhofer.iese.ids.odrl.pap.model.Action;
import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import lombok.Data;

@Data
public class ExecuteAction {

 String solution;
 Action action;
 Parameter[] parameters;

 public ExecuteAction(String solution, Action action, Parameter[] parameters) {
  this.solution = solution;
  this.action = action;
  this.parameters = parameters;
 }

 public ExecuteAction() {
 }


 @Override
 public String toString() {
  return  "          <allow/>  \r\n" +
          "          <execute action='urn:action:"+ solution +":"+ action.name().toLowerCase() +"'>  \r\n" +
          getParameters() +
          "          </execute> \r\n";
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
    params += "            " +parameters[i].toString() + "\r\n";
   }

   return  params;
  }
 }
}
