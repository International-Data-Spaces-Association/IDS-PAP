package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel;

public enum ConditionType {

    //applies on odrl rule
    CONSTRAINT("constraint"),

    //applies on action
    REFINEMENT("refinement");

    private final String cType;

    ConditionType(String c) {
        cType = c;
    }

    public String getOdrlConditionType() {
        return cType;
    }
}

