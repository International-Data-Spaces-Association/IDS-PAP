package de.fraunhofer.iese.ids.odrl.pap.model;

public enum Action {
    ACTION("odrl:action", ""),

    USE("ids:use","ACTION"),

    READ("ids:read", "ACTION"),

    DISPLAY("ids:display", "ACTION"),

    DELETE("ids:delete", "ACTION"),

    INFORM("ids:inform", "ACTION"),

    PRINT("ids:print", "ACTION"),

    ANONYMIZE("ids:anonymize", "ACTION"),

    REPRODUCE("ids:reproduce", "ACTION"),

    DISTRIBUTE("ids:distribute", "ACTION"),

    ROUND("ids:round", "ANONYMIZE"),

    LOG("ids:log", "ACTION");

    private final String idsAction;
    private final String abstractAction;

    Action(String a, String b) {

        idsAction = a;
        abstractAction = b;
    }

    public String getIdsAction() {
        return idsAction;
    }
    public String getAbstractIdsAction() {
        return abstractAction;
    }

}

