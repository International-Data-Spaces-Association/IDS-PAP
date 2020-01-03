package de.fraunhofer.iese.ids.odrl.pap.model;

public enum ConstraintRightOperands {

    Policy_Rule_Usage("policyUsage", "ids:policyUsage");


    private final String mydataRightOperand;
    private final String idsRightOperand;

    ConstraintRightOperands(String op1, String op2) {
        mydataRightOperand = op1;
        idsRightOperand = op2;
    }

    public String getMydataRightOperand() {
        return mydataRightOperand;
    }

    public String getIdsRightOperand() {
        return idsRightOperand;
    }
}

