package de.fraunhofer.iese.ids.odrl.pap.model;

public enum LeftOperand {

    DELAY_PERIOD("delay-period","ids:delay period"),

    DATETIME("datetime", "ids:datetime"),

    COUNT("count", "ids:count"),

    SYSTEM("system", "ids:system"),

    EVENT("event", "ids:event"),

    ENCODING("encoding", "ids:encoding"),

    RECIPIENT("recipient", "ids:recipient"),

    PURPOSE("purpose", "ids:purpose");

    private final String mydataLeftOperand;
    private final String idsLeftOperand;

    LeftOperand(String op1, String op2) {
        mydataLeftOperand = op1;
        idsLeftOperand = op2;
    }

    public String getMydataLeftOperand() {
        return mydataLeftOperand;
    }

    public String getIdsLeftOperand() {
        return idsLeftOperand;
    }
}

