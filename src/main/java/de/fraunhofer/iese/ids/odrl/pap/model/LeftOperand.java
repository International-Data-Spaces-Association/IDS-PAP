package de.fraunhofer.iese.ids.odrl.pap.model;

public enum LeftOperand {

    DELAY_PERIOD("delay-period","delay period"),

    DATETIME("datetime", "datetime"),

    COUNT("count", "count"),

    PURPOSE("purpose", "purpose");

    private final String mydataPIP;
    private final String odrlLeftOperand;

    LeftOperand(String op1, String op2) {
        mydataPIP = op1;
        odrlLeftOperand = op2;
    }

    public String getMydataPIP() {
        return mydataPIP;
    }

    public String getOdrlLeftOperand() {
        return odrlLeftOperand;
    }
}

