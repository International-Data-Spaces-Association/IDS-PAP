package de.fraunhofer.iese.ids.odrl.pap.model;

public enum Action {

    READ("read"),

    DISPLAY("display"),

    DELETE("delete"),

    INFORM("inform"),

    PRINT("print"),

    ANONYMIZE("anonymize"),

    REPRODUCE("reproduce"),

    DISTRIBUTE("distribute"),

    LOG("log");

    private final String action;

    Action(String a) {
        action = a;
    }

    public String getAction() {
        return action;
    }
}

