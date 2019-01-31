package de.fraunhofer.iese.ids.odrl.pap.model;

public enum Action {

    READ("read"),

    DISPLAY("display"),

    DELETE("delete"),

    LOG("log");

    private final String action;

    Action(String a) {
        action = a;
    }

    public String getAction() {
        return action;
    }
}

