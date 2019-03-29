package de.fraunhofer.iese.ids.odrl.pap.model.MydataModel;

import de.fraunhofer.iese.ids.odrl.pap.model.Action;
import lombok.Data;

@Data
public class Modify implements Operand {

    String eventParameter;
    Action dutyAction;
    String jsonPath;
    Parameter[] parameters;

    public Modify(String eventParameter, Action dutyAction, String jsonPath, Parameter[] parameters) {

        this.eventParameter = eventParameter;
        this.dutyAction = dutyAction;
        this.jsonPath = jsonPath;
        this.parameters = parameters;
    }

    public Modify() {
    }

    @Override
    public String toString() {
        return  "          <Modify eventParameter='" + eventParameter + "' method='"+ dutyAction.name().toLowerCase() +"' jsonPathQuery='" + jsonPath + "'>  \r\n" +
                getParameters() +
                "          </modify>    \r\n";
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
