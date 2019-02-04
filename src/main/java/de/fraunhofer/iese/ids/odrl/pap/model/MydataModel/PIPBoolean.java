package de.fraunhofer.iese.ids.odrl.pap.model.MydataModel;


import de.fraunhofer.iese.ids.odrl.pap.model.LeftOperand;
import de.fraunhofer.iese.ids.odrl.pap.model.Operator;
import lombok.Data;
import org.springframework.web.servlet.tags.Param;

@Data
public class PIPBoolean {
 String solution;
 LeftOperand leftOperand;
 Parameter[] parameters;

 public PIPBoolean(String solution, LeftOperand leftOperand, Parameter[] parameters) {
  this.solution = solution;
  this.leftOperand = leftOperand;
  this.parameters = parameters;
 }

 public PIPBoolean() {
 }


 @Override
 public String toString() {
  return  "        <pip:boolean method='urn:info:"+ solution +":"+ leftOperand.getMydataPIP() +"' default='false'>  \r\n" +
          getParameters() +
          "        </pip:boolean> \r\n" ;
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
    params += "          " +parameters[i].toString() + "\r\n";
   }

   return  params;
  }
 }
}
