package de.fraunhofer.iese.ids.odrl.pap.model.MydataModel;

import de.fraunhofer.iese.ids.odrl.pap.model.Action;
import lombok.Data;

@Data
public class Parameter {

    ParameterType type;
    String name;
    String value;

    public Parameter(ParameterType type, String name, String value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public Parameter() {
    }

    @Override
    public String toString() {
        return  "<parameter:"+ type.toString().toLowerCase() +" name='"+ name +"' value='"+ value +"'/>";
    }
}
