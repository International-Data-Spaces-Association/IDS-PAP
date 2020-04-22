package de.fraunhofer.iese.ids.odrl.pap.model;

public enum LeftOperand {

    DELAYPERIOD("delayperiod","ids:delayperiod"),

    DATETIME("datetime", "ids:datetime"),

    COUNT("count", "ids:count"),

    SYSTEM("system", "ids:system"),

    EVENT("event", "ids:event"),

    ENCODING("encoding", "ids:encoding"),

    RECIPIENT("recipient", "ids:recipient"),

    SYSTEMDEVICE("systemDevice", "ids:systemDevice"),

    PAYAMOUNT("payamount", "ids:payamount"),

    JSONPATH("jsonPathQuery","ids:jsonPath"),

    DIGIT("digit","ids:digit"),

    TARGET("target", "ids:target"),

    ABSOLUTESPATIALPOSITION("absoluteSpatialPosition","https://w3id.org/idsa/core/absoluteSpatialPosition"),

    PURPOSE("purpose", "https://w3id.org/idsa/core/purpose");

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

