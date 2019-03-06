package de.fraunhofer.iese.ids.odrl.pap.model.MydataModel;

import lombok.Data;

@Data
public class Event implements Operand {

    ParameterType type;
    String eventParameter;

    public Event(ParameterType type, String eventParameter) {
        this.type = type;
        this.eventParameter = eventParameter;
    }

    public Event() {
    }

    @Override
    public String toString() {
        return  "            <event:" + type.getParameterType() + " eventParameter='" + eventParameter + "'>  \r\n";
    }
}
