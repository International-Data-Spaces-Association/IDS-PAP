package de.fraunhofer.iese.ids.odrl.pap.model.MydataModel;

import lombok.Data;

@Data
public class Event implements Operand {

    ParameterType type;
    String eventParameter;
    String jsonPath;

    public Event(ParameterType type, String eventParameter, String jsonPath) {
        this.type = type;
        this.eventParameter = eventParameter;
        this.jsonPath = jsonPath;
    }

    public Event() {
    }

    @Override
    public String toString() {
        return  "            <event:" + type.getParameterType() + " eventParameter='" + eventParameter + "' jsonPathQuery='$." + jsonPath + "'/>  \r\n";
    }
}
