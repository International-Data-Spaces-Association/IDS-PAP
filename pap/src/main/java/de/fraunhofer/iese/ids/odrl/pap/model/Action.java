package de.fraunhofer.iese.ids.odrl.pap.model;

public enum Action {

    READ("ids:read"),

    DISPLAY("ids:display"),

    DELETE("ids:delete"),

    INFORM("ids:inform"),

    PRINT("ids:print"),

    ANONYMIZE("ids:anonymize"),

    REPRODUCE("ids:reproduce"),

    DISTRIBUTE("ids:distribute"),

    LOG("ids:log");

    private final String action;

    Action(String a) {
        action = a;
    }

    public String getIdsAction() {
        return action;
    }

}

