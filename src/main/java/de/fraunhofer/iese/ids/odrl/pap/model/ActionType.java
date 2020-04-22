package de.fraunhofer.iese.ids.odrl.pap.model;

public enum ActionType {
    ACTION("odrl:action", ""),

    USE("ids:use","ACTION"),

    READ("https://w3id.org/idsa/code/action/READ", "ACTION"),

    DISPLAY("ids:display", "ACTION"),

    DELETE("ids:delete", "ACTION"),

    INFORM("ids:inform", "ACTION"),

    PRINT("ids:print", "ACTION"),

    ANONYMIZE("ids:anonymize", "ACTION"),

    REPRODUCE("ids:reproduce", "ACTION"),

    NEXTPOLICY("ids:nextPolicy", "ACTION"),

    DISTRIBUTE("ids:distribute", "ACTION"),

    ROUND("ids:round", "ANONYMIZE"),

    LOG("ids:log", "ACTION");

    private final String idsAction;
    private final String abstractAction;

    ActionType(String a, String b) {

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

