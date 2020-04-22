package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel;

public enum RuleType {

    PERMISSION("allow","permission"),

    PROHIBITION("inhibit", "prohibition"),

    OBLIGATION("allow", "obligation");

    private final String mydataDecision;
    private final String odrlRuleType;

    RuleType(String op1, String op2) {
        mydataDecision = op1;
        odrlRuleType = op2;
    }

    public String getMydataDecision() {
        return mydataDecision;
    }

    public String getOdrlRuleType() {
        return odrlRuleType;
    }
}

