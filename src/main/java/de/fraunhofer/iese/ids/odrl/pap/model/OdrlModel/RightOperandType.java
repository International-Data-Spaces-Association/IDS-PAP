package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel;

public enum RightOperandType {

    DATE("xsd:date"),

    DATETIME("xsd:datetime"),

    INTEGER("xsd:integer"),

    STRING("xsd:string"),

    DECIMAL("xsd:decimal"),

    DURATION("xsd:duration"),

    ANYURI("xsd:anyURI");



    private final String type;

    RightOperandType(String t) {
        this.type = t;
    }

    public String getType() {
        return type;
    }
}

