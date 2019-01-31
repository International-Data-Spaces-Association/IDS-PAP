package de.fraunhofer.iese.ids.odrl.pap.model;


public enum Operator {

 EQUALS("equal", "eq"),

 LESS("less", "lt"),

 LESS_EQUAL("lessEqual", "lteq"),

 GREATER("greater", "gt"),

 GREATER_EQUAL("greaterEqual", "gteq");

 private final String mydataOp;
 private final String odrlOp;

 Operator(String op1, String op2) {
  mydataOp = op1;
  odrlOp = op2;
 }

 public String getMydataOp() {
  return mydataOp;
 }

 public String getOdrlOp() {
  return odrlOp;
 }

}
