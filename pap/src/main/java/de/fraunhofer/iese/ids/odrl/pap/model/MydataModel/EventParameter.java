package de.fraunhofer.iese.ids.odrl.pap.model.MydataModel;

public enum EventParameter {

    TARGET("target"),

    ASSIGNER("assigner"),

    ASSIGNEE("assignee"),

    DIGIT("digit");

    private final String eventParameter;

    EventParameter(String a) {
        eventParameter = a;
    }

    public String getEventParameter() {
        return eventParameter;
    }

}

