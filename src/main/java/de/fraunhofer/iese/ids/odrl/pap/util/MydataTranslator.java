package de.fraunhofer.iese.ids.odrl.pap.util;


import de.fraunhofer.iese.ids.odrl.pap.model.*;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.Condition;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.*;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.*;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.*;
import lombok.Data;
import org.apache.tomcat.util.digester.Rule;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@Data
public class MydataTranslator implements ITranslator {

 private String solution = "";

 public MydataTranslator()
 {

 }

 @Override
 public String translateComplexPolicyWithPermissionRuleUseAction(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.USE, RuleType.PERMISSION, this.solution);

  PurposeCondition purposeConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleUseAction().getPurposeConstraint();
  mydataPolicy = this.purposeConstraint(mydataPolicy, purposeConstraint);
  EventCondition eventConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleUseAction().getEventConstraint();
  mydataPolicy = this.eventConstraint(mydataPolicy, eventConstraint);
  AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleUseAction().getAbsoluteSpatialPositionConstraint();
  mydataPolicy = this.absoluteSpatialPositionConstraint(mydataPolicy, absoluteSpatialPositionConstraint);
  PaymentCondition paymentConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleUseAction().getPaymentConstraint();
  mydataPolicy = this.paymentConstraint(mydataPolicy, paymentConstraint);
  SystemCondition systemConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleUseAction().getSystemConstraint();
  mydataPolicy = this.systemConstraint(mydataPolicy, systemConstraint);
  TimeIntervalCondition timeIntervalConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleUseAction().getTimeIntervalConstraint();
  mydataPolicy = this.timeIntervalConstraint(mydataPolicy, timeIntervalConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translateComplexPolicyWithPermissionRuleReadAction(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.READ, RuleType.PERMISSION, this.solution);

  PurposeCondition purposeConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleReadAction().getPurposeConstraint();
  mydataPolicy = this.purposeConstraint(mydataPolicy, purposeConstraint);
  EventCondition eventConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleReadAction().getEventConstraint();
  mydataPolicy = this.eventConstraint(mydataPolicy, eventConstraint);
  AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleReadAction().getAbsoluteSpatialPositionConstraint();
  mydataPolicy = this.absoluteSpatialPositionConstraint(mydataPolicy, absoluteSpatialPositionConstraint);
  PaymentCondition paymentConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleReadAction().getPaymentConstraint();
  mydataPolicy = this.paymentConstraint(mydataPolicy, paymentConstraint);
  SystemCondition systemConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleReadAction().getSystemConstraint();
  mydataPolicy = this.systemConstraint(mydataPolicy, systemConstraint);
  TimeIntervalCondition timeIntervalConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleReadAction().getTimeIntervalConstraint();
  mydataPolicy = this.timeIntervalConstraint(mydataPolicy, timeIntervalConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translateComplexPolicyWithPermissionRuleDistributeAction(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DISTRIBUTE, RuleType.PERMISSION, this.solution);

  PurposeCondition purposeConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleDistributeAction().getPurposeConstraint();
  mydataPolicy = this.purposeConstraint(mydataPolicy, purposeConstraint);
  EventCondition eventConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleDistributeAction().getEventConstraint();
  mydataPolicy = this.eventConstraint(mydataPolicy, eventConstraint);
  AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleDistributeAction().getAbsoluteSpatialPositionConstraint();
  mydataPolicy = this.absoluteSpatialPositionConstraint(mydataPolicy, absoluteSpatialPositionConstraint);
  PaymentCondition paymentConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleDistributeAction().getPaymentConstraint();
  mydataPolicy = this.paymentConstraint(mydataPolicy, paymentConstraint);
  SystemCondition systemConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleDistributeAction().getSystemConstraint();
  mydataPolicy = this.systemConstraint(mydataPolicy, systemConstraint);
  TimeIntervalCondition timeIntervalConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleDistributeAction().getTimeIntervalConstraint();
  mydataPolicy = this.timeIntervalConstraint(mydataPolicy, timeIntervalConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translateComplexPolicyWithPermissionRulePrintAction(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.PRINT, RuleType.PERMISSION, this.solution);

  PurposeCondition purposeConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRulePrintAction().getPurposeConstraint();
  mydataPolicy = this.purposeConstraint(mydataPolicy, purposeConstraint);
  EventCondition eventConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRulePrintAction().getEventConstraint();
  mydataPolicy = this.eventConstraint(mydataPolicy, eventConstraint);
  AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRulePrintAction().getAbsoluteSpatialPositionConstraint();
  mydataPolicy = this.absoluteSpatialPositionConstraint(mydataPolicy, absoluteSpatialPositionConstraint);
  PaymentCondition paymentConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRulePrintAction().getPaymentConstraint();
  mydataPolicy = this.paymentConstraint(mydataPolicy, paymentConstraint);
  SystemCondition systemConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRulePrintAction().getSystemConstraint();
  mydataPolicy = this.systemConstraint(mydataPolicy, systemConstraint);
  TimeIntervalCondition timeIntervalConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRulePrintAction().getTimeIntervalConstraint();
  mydataPolicy = this.timeIntervalConstraint(mydataPolicy, timeIntervalConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleUseActionNoConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.USE, RuleType.PERMISSION, this.solution);
  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleReadActionNoConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.READ, RuleType.PERMISSION, this.solution);
  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleDistributeActionNoConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DISTRIBUTE, RuleType.PERMISSION, this.solution);
  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRulePrintActionNoConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.PRINT, RuleType.PERMISSION, this.solution);
  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleUseActionEventConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.USE, RuleType.PERMISSION, this.solution);

  EventCondition eventConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleUseAction().getEventConstraint();
  mydataPolicy = this.eventConstraint(mydataPolicy, eventConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleReadActionEventConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.READ, RuleType.PERMISSION, this.solution);

  EventCondition eventConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleReadAction().getEventConstraint();
  mydataPolicy = this.eventConstraint(mydataPolicy, eventConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleDistributeActionEventConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DISTRIBUTE, RuleType.PERMISSION, this.solution);

  EventCondition eventConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleDistributeAction().getEventConstraint();
  mydataPolicy = this.eventConstraint(mydataPolicy, eventConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRulePrintActionEventConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.PRINT, RuleType.PERMISSION, this.solution);

  EventCondition eventConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRulePrintAction().getEventConstraint();
  mydataPolicy = this.eventConstraint(mydataPolicy, eventConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleUseActionPurposeConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.USE, RuleType.PERMISSION, this.solution);

  PurposeCondition purposeConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleUseAction().getPurposeConstraint();
  mydataPolicy = purposeConstraint(mydataPolicy, purposeConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleReadActionPurposeConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.READ, RuleType.PERMISSION, this.solution);

  PurposeCondition purposeConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleReadAction().getPurposeConstraint();
  mydataPolicy = purposeConstraint(mydataPolicy, purposeConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleDistributeActionPurposeConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DISTRIBUTE, RuleType.PERMISSION, this.solution);

  PurposeCondition purposeConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleDistributeAction().getPurposeConstraint();
  mydataPolicy = purposeConstraint(mydataPolicy, purposeConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRulePrintActionPurposeConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.PRINT, RuleType.PERMISSION, this.solution);

  PurposeCondition purposeConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRulePrintAction().getPurposeConstraint();
  mydataPolicy = purposeConstraint(mydataPolicy, purposeConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleUseActionSystemConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.USE, RuleType.PERMISSION, this.solution);

  SystemCondition systemConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleUseAction().getSystemConstraint();
  mydataPolicy = this.systemConstraint(mydataPolicy, systemConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleReadActionSystemConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.READ, RuleType.PERMISSION, this.solution);

  SystemCondition systemConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleReadAction().getSystemConstraint();
  mydataPolicy = this.systemConstraint(mydataPolicy, systemConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleDistributeActionSystemConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DISTRIBUTE, RuleType.PERMISSION, this.solution);

  SystemCondition systemConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleDistributeAction().getSystemConstraint();
  mydataPolicy = this.systemConstraint(mydataPolicy, systemConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRulePrintActionSystemConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.PRINT, RuleType.PERMISSION, this.solution);

  SystemCondition systemConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRulePrintAction().getSystemConstraint();
  mydataPolicy = this.systemConstraint(mydataPolicy, systemConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleUseActionPaymentConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.USE, RuleType.PERMISSION, this.solution);

  PaymentCondition paymentConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleUseAction().getPaymentConstraint();
  mydataPolicy = this.paymentConstraint(mydataPolicy, paymentConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleReadActionPaymentConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.READ, RuleType.PERMISSION, this.solution);

  PaymentCondition paymentConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleReadAction().getPaymentConstraint();
  mydataPolicy = this.paymentConstraint(mydataPolicy, paymentConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleDistributeActionPaymentConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DISTRIBUTE, RuleType.PERMISSION, this.solution);

  PaymentCondition paymentConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleDistributeAction().getPaymentConstraint();
  mydataPolicy = this.paymentConstraint(mydataPolicy, paymentConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRulePrintActionPaymentConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.PRINT, RuleType.PERMISSION, this.solution);

  PaymentCondition paymentConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRulePrintAction().getPaymentConstraint();
  mydataPolicy = this.paymentConstraint(mydataPolicy, paymentConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleUseActionAndCountConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.USE, RuleType.PERMISSION, this.solution);

  Parameter[] countParams = {};
  Count countFirstOperand = new Count(this.solution, null, ActionType.USE, countParams, FixedTime.ALWAYS);
  CountCondition countConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleUseAction().getCountConstraint();
  Constant countSecondOperand = new Constant(ParameterType.NUMBER, countConstraint.getRightOperand().getValue());
  Condition countCondition = new Condition(countFirstOperand, Operator.LESS, countSecondOperand);

  List<Condition> cons = mydataPolicy.getConditions();
  cons.add(countCondition);
  mydataPolicy.setConditions(cons);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleReadActionAndCountConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.READ, RuleType.PERMISSION, this.solution);

  Parameter[] countParams = {};
  Count countFirstOperand = new Count(this.solution, null, ActionType.USE, countParams, FixedTime.ALWAYS);
  CountCondition countConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleReadAction().getCountConstraint();
  Constant countSecondOperand = new Constant(ParameterType.NUMBER, countConstraint.getRightOperand().getValue());
  Condition countCondition = new Condition(countFirstOperand, Operator.LESS, countSecondOperand);

  List<Condition> cons = mydataPolicy.getConditions();
  cons.add(countCondition);
  mydataPolicy.setConditions(cons);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleDistributeActionAndCountConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DISTRIBUTE, RuleType.PERMISSION, this.solution);

  Parameter[] countParams = {};
  Count countFirstOperand = new Count(this.solution, null, ActionType.USE, countParams, FixedTime.ALWAYS);
  CountCondition countConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleDistributeAction().getCountConstraint();
  Constant countSecondOperand = new Constant(ParameterType.NUMBER, countConstraint.getRightOperand().getValue());
  Condition countCondition = new Condition(countFirstOperand, Operator.LESS, countSecondOperand);

  List<Condition> cons = mydataPolicy.getConditions();
  cons.add(countCondition);
  mydataPolicy.setConditions(cons);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRulePrintActionAndCountConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.PRINT, RuleType.PERMISSION, this.solution);

  Parameter[] countParams = {};
  Count countFirstOperand = new Count(this.solution, null, ActionType.USE, countParams, FixedTime.ALWAYS);
  CountCondition countConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRulePrintAction().getCountConstraint();
  Constant countSecondOperand = new Constant(ParameterType.NUMBER, countConstraint.getRightOperand().getValue());
  Condition countCondition = new Condition(countFirstOperand, Operator.LESS, countSecondOperand);

  List<Condition> cons = mydataPolicy.getConditions();
  cons.add(countCondition);
  mydataPolicy.setConditions(cons);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleDistributeActionAndEncodingConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DISTRIBUTE, RuleType.PERMISSION, this.solution);

  EncodingCondition encodingConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleDistributeAction().getEncodingConstraint();
  Parameter encodingP = new Parameter(ParameterType.STRING,LeftOperand.ENCODING.getMydataLeftOperand()+"-uri", encodingConstraint.getRightOperand().getValue());

  Parameter[] pipParams = {encodingP};
  PIPBoolean encodingPipBoolean = new PIPBoolean(this.solution, LeftOperand.ENCODING, pipParams);

  List<PIPBoolean> pips = mydataPolicy.getPipBooleans();
  pips.add(encodingPipBoolean);
  mydataPolicy.setPipBooleans(pips);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleUseActionTimeIntervalConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.USE, RuleType.PERMISSION, this.solution);

  TimeIntervalCondition timeIntervalConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleUseAction().getTimeIntervalConstraint();
  mydataPolicy = this.timeIntervalConstraint(mydataPolicy, timeIntervalConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleReadActionTimeIntervalConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.READ, RuleType.PERMISSION, this.solution);

  TimeIntervalCondition timeIntervalConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleReadAction().getTimeIntervalConstraint();
  mydataPolicy = this.timeIntervalConstraint(mydataPolicy, timeIntervalConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleDistributeActionTimeIntervalConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DISTRIBUTE, RuleType.PERMISSION, this.solution);

  TimeIntervalCondition timeIntervalConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleUseAction().getTimeIntervalConstraint();
  mydataPolicy = this.timeIntervalConstraint(mydataPolicy, timeIntervalConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRulePrintActionTimeIntervalConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.PRINT, RuleType.PERMISSION, this.solution);

  TimeIntervalCondition timeIntervalConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleUseAction().getTimeIntervalConstraint();
  mydataPolicy = this.timeIntervalConstraint(mydataPolicy, timeIntervalConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleUseActionAbsoluteSpatialPositionConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.USE, RuleType.PERMISSION, this.solution);

  AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleUseAction().getAbsoluteSpatialPositionConstraint();
  mydataPolicy = this.absoluteSpatialPositionConstraint(mydataPolicy, absoluteSpatialPositionConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleReadActionAbsoluteSpatialPositionConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.READ, RuleType.PERMISSION, this.solution);

  AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleReadAction().getAbsoluteSpatialPositionConstraint();
  mydataPolicy = this.absoluteSpatialPositionConstraint(mydataPolicy, absoluteSpatialPositionConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleDistributeActionAbsoluteSpatialPositionConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DISTRIBUTE, RuleType.PERMISSION, this.solution);

  AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRuleDistributeAction().getAbsoluteSpatialPositionConstraint();
  mydataPolicy = this.absoluteSpatialPositionConstraint(mydataPolicy, absoluteSpatialPositionConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRulePrintActionAbsoluteSpatialPositionConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.PRINT, RuleType.PERMISSION, this.solution);

  AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint = ((OdrlPolicy) odrlPolicy).getPermissionRulePrintAction().getAbsoluteSpatialPositionConstraint();
  mydataPolicy = this.absoluteSpatialPositionConstraint(mydataPolicy, absoluteSpatialPositionConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleUseActionDeleteDutyActionAndEventDutyConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.USE, RuleType.PERMISSION, this.solution);

  ExecuteAction pxp = new ExecuteAction(solution, ActionType.DELETE, null);
  mydataPolicy.setPxp(pxp);
  mydataPolicy.setHasDuty(true);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleReadActionDeleteDutyActionAndEventDutyConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.READ, RuleType.PERMISSION, this.solution);

  ExecuteAction pxp = new ExecuteAction(solution, ActionType.DELETE, null);
  mydataPolicy.setPxp(pxp);
  mydataPolicy.setHasDuty(true);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleUseActionAnonymizeDutyActionAndEventDutyConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.USE, RuleType.PERMISSION, this.solution);

  AnonymizeAction anonymizeDutyAction = ((OdrlPolicy) odrlPolicy).getPermissionRuleUseAction().getAnonymizeDutyAction();
  Modify modify = new Modify(EventParameter.TARGET.getEventParameter(), ActionType.ANONYMIZE, anonymizeDutyAction.getJsonPathRefinement().getRightOperand().getValue(), null);
  mydataPolicy.setModify(modify);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleReadActionAnonymizeDutyActionAndEventDutyConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.READ, RuleType.PERMISSION, this.solution);

  AnonymizeAction anonymizeDutyAction = ((OdrlPolicy) odrlPolicy).getPermissionRuleReadAction().getAnonymizeDutyAction();
  Modify modify = new Modify(EventParameter.TARGET.getEventParameter(), ActionType.ANONYMIZE, anonymizeDutyAction.getJsonPathRefinement().getRightOperand().getValue(), null);
  mydataPolicy.setModify(modify);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleUseActionAndInformDutyAction(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.USE, RuleType.PERMISSION, this.solution);

  Party informedParty = ((OdrlPolicy) odrlPolicy).getPermissionRuleUseAction().getInformedParty();
  Parameter informedPartyP = new Parameter(ParameterType.STRING,PartyFunction.INFORMEDPARTY.getMydataPartyFunction()+"-uri", informedParty.getUri().toString());
  Parameter[] params = {informedPartyP};
  ExecuteAction pxp = new ExecuteAction(solution, ActionType.INFORM, params);
  mydataPolicy.setPxp(pxp);
  mydataPolicy.setHasDuty(true);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleReadActionAndInformDutyAction(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.READ, RuleType.PERMISSION, this.solution);

  Party informedParty = ((OdrlPolicy) odrlPolicy).getPermissionRuleReadAction().getInformedParty();
  Parameter informedPartyP = new Parameter(ParameterType.STRING,PartyFunction.INFORMEDPARTY.getMydataPartyFunction()+"-uri", informedParty.getUri().toString());
  Parameter[] params = {informedPartyP};
  ExecuteAction pxp = new ExecuteAction(solution, ActionType.INFORM, params);
  mydataPolicy.setPxp(pxp);
  mydataPolicy.setHasDuty(true);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleUseActionAndLogDutyAction(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.USE, RuleType.PERMISSION, this.solution);

  LogAction logDutyAction = ((OdrlPolicy) odrlPolicy).getPermissionRuleUseAction().getLogDutyAction();
  Parameter systemDeviceP = new Parameter(ParameterType.STRING,LeftOperand.SYSTEMDEVICE.getMydataLeftOperand()+"-uri", logDutyAction.getSystemDeviceRefinement().getRightOperand().getValue());
  Parameter[] params = {systemDeviceP};
  ExecuteAction pxp = new ExecuteAction(solution, logDutyAction.getType(), params);
  mydataPolicy.setPxp(pxp);
  mydataPolicy.setHasDuty(true);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleReadActionAndLogDutyAction(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.READ, RuleType.PERMISSION, this.solution);

  LogAction logDutyAction = ((OdrlPolicy) odrlPolicy).getPermissionRuleUseAction().getLogDutyAction();
  Parameter systemDeviceP = new Parameter(ParameterType.STRING,LeftOperand.SYSTEMDEVICE.getMydataLeftOperand()+"-uri", logDutyAction.getSystemDeviceRefinement().getRightOperand().getValue());
  Parameter[] params = {systemDeviceP};
  ExecuteAction pxp = new ExecuteAction(solution, logDutyAction.getType(), params);
  mydataPolicy.setPxp(pxp);
  mydataPolicy.setHasDuty(true);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithPermissionRuleDistributeActionAndNextPolicyDutyAction(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DISTRIBUTE, RuleType.PERMISSION, this.solution);

  URI nextPolicyTarget = ((OdrlPolicy) odrlPolicy).getPermissionRuleDistributeAction().getNextPolicyTarget();
  Parameter nextPolicyTargetP = new Parameter(ParameterType.STRING,LeftOperand.TARGET.getMydataLeftOperand()+"-uri", nextPolicyTarget.toString());
  Parameter[] params = {nextPolicyTargetP};
  ExecuteAction pxp = new ExecuteAction(solution, ActionType.NEXTPOLICY, params);
  mydataPolicy.setPxp(pxp);
  mydataPolicy.setHasDuty(true);

  return mydataPolicy.toString();
 }

 @Override
 public String translateComplexPolicyWithProhibitionRuleUseAction(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.USE, RuleType.PROHIBITION, this.solution);

  PurposeCondition purposeConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleUseAction().getPurposeConstraint();
  mydataPolicy = this.purposeConstraint(mydataPolicy, purposeConstraint);
  EventCondition eventConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleUseAction().getEventConstraint();
  mydataPolicy = this.eventConstraint(mydataPolicy, eventConstraint);
  AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleUseAction().getAbsoluteSpatialPositionConstraint();
  mydataPolicy = this.absoluteSpatialPositionConstraint(mydataPolicy, absoluteSpatialPositionConstraint);
  SystemCondition systemConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleUseAction().getSystemConstraint();
  mydataPolicy = this.systemConstraint(mydataPolicy, systemConstraint);
  TimeIntervalCondition timeIntervalConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleUseAction().getTimeIntervalConstraint();
  mydataPolicy = this.timeIntervalConstraint(mydataPolicy, timeIntervalConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translateComplexPolicyWithProhibitionRuleReadAction(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.READ, RuleType.PROHIBITION, this.solution);

  PurposeCondition purposeConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleReadAction().getPurposeConstraint();
  mydataPolicy = this.purposeConstraint(mydataPolicy, purposeConstraint);
  EventCondition eventConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleReadAction().getEventConstraint();
  mydataPolicy = this.eventConstraint(mydataPolicy, eventConstraint);
  AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleReadAction().getAbsoluteSpatialPositionConstraint();
  mydataPolicy = this.absoluteSpatialPositionConstraint(mydataPolicy, absoluteSpatialPositionConstraint);
  SystemCondition systemConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleReadAction().getSystemConstraint();
  mydataPolicy = this.systemConstraint(mydataPolicy, systemConstraint);
  TimeIntervalCondition timeIntervalConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleReadAction().getTimeIntervalConstraint();
  mydataPolicy = this.timeIntervalConstraint(mydataPolicy, timeIntervalConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translateComplexPolicyWithProhibitionRuleDistributeAction(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DISTRIBUTE, RuleType.PROHIBITION, this.solution);

  PurposeCondition purposeConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleDistributeAction().getPurposeConstraint();
  mydataPolicy = this.purposeConstraint(mydataPolicy, purposeConstraint);
  EventCondition eventConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleDistributeAction().getEventConstraint();
  mydataPolicy = this.eventConstraint(mydataPolicy, eventConstraint);
  AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleDistributeAction().getAbsoluteSpatialPositionConstraint();
  mydataPolicy = this.absoluteSpatialPositionConstraint(mydataPolicy, absoluteSpatialPositionConstraint);
  SystemCondition systemConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleDistributeAction().getSystemConstraint();
  mydataPolicy = this.systemConstraint(mydataPolicy, systemConstraint);
  TimeIntervalCondition timeIntervalConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleDistributeAction().getTimeIntervalConstraint();
  mydataPolicy = this.timeIntervalConstraint(mydataPolicy, timeIntervalConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translateComplexPolicyWithProhibitionRulePrintAction(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.PRINT, RuleType.PROHIBITION, this.solution);

  PurposeCondition purposeConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRulePrintAction().getPurposeConstraint();
  mydataPolicy = this.purposeConstraint(mydataPolicy, purposeConstraint);
  EventCondition eventConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRulePrintAction().getEventConstraint();
  mydataPolicy = this.eventConstraint(mydataPolicy, eventConstraint);
  AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRulePrintAction().getAbsoluteSpatialPositionConstraint();
  mydataPolicy = this.absoluteSpatialPositionConstraint(mydataPolicy, absoluteSpatialPositionConstraint);
  SystemCondition systemConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRulePrintAction().getSystemConstraint();
  mydataPolicy = this.systemConstraint(mydataPolicy, systemConstraint);
  TimeIntervalCondition timeIntervalConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRulePrintAction().getTimeIntervalConstraint();
  mydataPolicy = this.timeIntervalConstraint(mydataPolicy, timeIntervalConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRuleUseActionNoConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.USE, RuleType.PROHIBITION, this.solution);
  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRuleReadActionNoConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.READ, RuleType.PROHIBITION, this.solution);
  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRuleDistributeActionNoConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DISTRIBUTE, RuleType.PROHIBITION, this.solution);
  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRulePrintActionNoConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.PRINT, RuleType.PROHIBITION, this.solution);
  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRuleUseActionEventConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.USE, RuleType.PROHIBITION, this.solution);

  EventCondition eventConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleUseAction().getEventConstraint();
  mydataPolicy = this.eventConstraint(mydataPolicy, eventConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRuleReadActionEventConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.READ, RuleType.PROHIBITION, this.solution);

  EventCondition eventConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleReadAction().getEventConstraint();
  mydataPolicy = this.eventConstraint(mydataPolicy, eventConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRuleDistributeActionEventConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DISTRIBUTE, RuleType.PROHIBITION, this.solution);

  EventCondition eventConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleDistributeAction().getEventConstraint();
  mydataPolicy = this.eventConstraint(mydataPolicy, eventConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRulePrintActionEventConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.PRINT, RuleType.PROHIBITION, this.solution);

  EventCondition eventConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRulePrintAction().getEventConstraint();
  mydataPolicy = this.eventConstraint(mydataPolicy, eventConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRuleUseActionPurposeConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.USE, RuleType.PROHIBITION, this.solution);

  PurposeCondition purposeConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleUseAction().getPurposeConstraint();
  mydataPolicy = purposeConstraint(mydataPolicy, purposeConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRuleReadActionPurposeConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.READ, RuleType.PROHIBITION, this.solution);

  PurposeCondition purposeConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleReadAction().getPurposeConstraint();
  mydataPolicy = purposeConstraint(mydataPolicy, purposeConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRuleDistributeActionPurposeConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DISTRIBUTE, RuleType.PROHIBITION, this.solution);

  PurposeCondition purposeConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleDistributeAction().getPurposeConstraint();
  mydataPolicy = purposeConstraint(mydataPolicy, purposeConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRulePrintActionPurposeConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.PRINT, RuleType.PROHIBITION, this.solution);

  PurposeCondition purposeConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRulePrintAction().getPurposeConstraint();
  mydataPolicy = purposeConstraint(mydataPolicy, purposeConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRuleUseActionSystemConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.USE, RuleType.PROHIBITION, this.solution);

  SystemCondition systemConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleUseAction().getSystemConstraint();
  mydataPolicy = this.systemConstraint(mydataPolicy, systemConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRuleReadActionSystemConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.READ, RuleType.PROHIBITION, this.solution);

  SystemCondition systemConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleReadAction().getSystemConstraint();
  mydataPolicy = this.systemConstraint(mydataPolicy, systemConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRuleDistributeActionSystemConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DISTRIBUTE, RuleType.PROHIBITION, this.solution);

  SystemCondition systemConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleDistributeAction().getSystemConstraint();
  mydataPolicy = this.systemConstraint(mydataPolicy, systemConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRulePrintActionSystemConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.PRINT, RuleType.PROHIBITION, this.solution);

  SystemCondition systemConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRulePrintAction().getSystemConstraint();
  mydataPolicy = this.systemConstraint(mydataPolicy, systemConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRuleUseActionTimeIntervalConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.USE, RuleType.PROHIBITION, this.solution);

  TimeIntervalCondition timeIntervalConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleUseAction().getTimeIntervalConstraint();
  mydataPolicy = this.timeIntervalConstraint(mydataPolicy, timeIntervalConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRuleReadActionTimeIntervalConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.READ, RuleType.PROHIBITION, this.solution);

  TimeIntervalCondition timeIntervalConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleReadAction().getTimeIntervalConstraint();
  mydataPolicy = this.timeIntervalConstraint(mydataPolicy, timeIntervalConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRuleDistributeActionTimeIntervalConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DISTRIBUTE, RuleType.PROHIBITION, this.solution);

  TimeIntervalCondition timeIntervalConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleDistributeAction().getTimeIntervalConstraint();
  mydataPolicy = this.timeIntervalConstraint(mydataPolicy, timeIntervalConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRulePrintActionTimeIntervalConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.PRINT, RuleType.PROHIBITION, this.solution);

  TimeIntervalCondition timeIntervalConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRulePrintAction().getTimeIntervalConstraint();
  mydataPolicy = this.timeIntervalConstraint(mydataPolicy, timeIntervalConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRuleUseActionAbsoluteSpatialPositionConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.USE, RuleType.PROHIBITION, this.solution);

  AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleUseAction().getAbsoluteSpatialPositionConstraint();
  mydataPolicy = this.absoluteSpatialPositionConstraint(mydataPolicy, absoluteSpatialPositionConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRuleReadActionAbsoluteSpatialPositionConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.READ, RuleType.PROHIBITION, this.solution);

  AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleReadAction().getAbsoluteSpatialPositionConstraint();
  mydataPolicy = this.absoluteSpatialPositionConstraint(mydataPolicy, absoluteSpatialPositionConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRuleDistributeActionAbsoluteSpatialPositionConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DISTRIBUTE, RuleType.PROHIBITION, this.solution);

  AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRuleDistributeAction().getAbsoluteSpatialPositionConstraint();
  mydataPolicy = this.absoluteSpatialPositionConstraint(mydataPolicy, absoluteSpatialPositionConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithProhibitionRulePrintActionAbsoluteSpatialPositionConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.PRINT, RuleType.PROHIBITION, this.solution);

  AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint = ((OdrlPolicy) odrlPolicy).getProhibitionRulePrintAction().getAbsoluteSpatialPositionConstraint();
  mydataPolicy = this.absoluteSpatialPositionConstraint(mydataPolicy, absoluteSpatialPositionConstraint);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithObligationRuleDeleteActionNoConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DISTRIBUTE, RuleType.OBLIGATION, this.solution);

  ExecuteAction pxp = new ExecuteAction(solution, ActionType.DELETE, null);
  mydataPolicy.setPxp(pxp);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithObligationRuleAnonymizeActionNoConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.ANONYMIZE, RuleType.OBLIGATION, this.solution);

  ExecuteAction pxp = new ExecuteAction(solution, ActionType.ANONYMIZE, null);
  mydataPolicy.setPxp(pxp);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithObligationRuleLogActionNoConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.LOG, RuleType.OBLIGATION, this.solution);

  ExecuteAction pxp = new ExecuteAction(solution, ActionType.LOG, null);
  mydataPolicy.setPxp(pxp);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithObligationRuleInformActionNoConstraint(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.INFORM, RuleType.OBLIGATION, this.solution);

  ExecuteAction pxp = new ExecuteAction(solution, ActionType.INFORM, null);
  mydataPolicy.setPxp(pxp);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithObligationRuleDeleteActionEventConstraint(Object odrlPolicy) {
  return null;
 }

 @Override
 public String translatePolicyWithObligationRuleAnonymizeActionEventConstraint(Object odrlPolicy) {
  return null;
 }

 @Override
 public String translatePolicyWithObligationRuleLogActionEventConstraint(Object odrlPolicy) {
  return null;
 }

 @Override
 public String translatePolicyWithObligationRuleInformActionEventConstraint(Object odrlPolicy) {
  return null;
 }

 @Override
 public String translatePolicyWithObligationRuleDeleteActionAndDelayPeriodRefinement(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  DelayPeriodCondition delayPeriodRefinement = ((OdrlPolicy) odrlPolicy).getObligationRuleDeleteAction().getDeleteAction().getDelayPeriodRefinement();
  Duration d = BuildMydataPolicyUtils.getDurationFromDelayPeriodValue(delayPeriodRefinement.getRightOperand().getValue());
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DELETE, RuleType.OBLIGATION, this.solution);
  Timer timer = new Timer(delayPeriodRefinement.getTimeUnit(), "",mydataPolicy.getPid(), solution, ActionType.DELETE, null);
  mydataPolicy.setTimer(timer);

  Parameter valueP = new Parameter(ParameterType.NUMBER,"value",String.valueOf(d.getValue()));
  Parameter unitP = new Parameter(ParameterType.STRING,"value",delayPeriodRefinement.getTimeUnit().toString());
  Parameter[] pipParams = {valueP, unitP};
  PIPBoolean delayPeriodPipBoolean = new PIPBoolean(solution, LeftOperand.DELAYPERIOD, pipParams);

  List<PIPBoolean> pips = mydataPolicy.getPipBooleans();
  pips.add(delayPeriodPipBoolean);
  mydataPolicy.setPipBooleans(pips);

  ExecuteAction pxp = new ExecuteAction(solution, ActionType.DELETE, null);
  mydataPolicy.setPxp(pxp);

  return mydataPolicy.toString();
 }

 @Override
 public String translatePolicyWithObligationRuleDeleteActionAndDateTimeRefinement(Object odrlPolicy) {
  this.solution = BuildMydataPolicyUtils.getSolution((OdrlPolicy) odrlPolicy);
  MydataPolicy mydataPolicy = BuildMydataPolicyUtils.buildPolicy((OdrlPolicy) odrlPolicy, ActionType.DELETE, RuleType.OBLIGATION, this.solution);

  DateTimeCondition dateTimeRefinement = ((OdrlPolicy) odrlPolicy).getObligationRuleDeleteAction().getDeleteAction().getDateTimeRefinement();
  DateTime dateTime = new DateTime(IntervalCondition.EQ, dateTimeRefinement.getRightOperand().getValue());
  String cron = createCron(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());
  Timer timer = new Timer(null,cron,mydataPolicy.getPid(), solution, ActionType.DELETE,null);
  mydataPolicy.setTimer(timer);

  ExecuteAction pxp = new ExecuteAction(solution, ActionType.DELETE, null);
  mydataPolicy.setPxp(pxp);

  return mydataPolicy.toString();
 }

 private MydataPolicy absoluteSpatialPositionConstraint(MydataPolicy mydataPolicy, AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint) {
  if(null != absoluteSpatialPositionConstraint)
  {
   Parameter locationP = new Parameter(ParameterType.STRING,LeftOperand.ABSOLUTESPATIALPOSITION.getMydataLeftOperand()+"-uri", absoluteSpatialPositionConstraint.getRightOperand().getValue());

   Parameter[] pipParams = {locationP};
   PIPBoolean locationPipBoolean = new PIPBoolean(this.solution, LeftOperand.ABSOLUTESPATIALPOSITION, pipParams);

   List<PIPBoolean> pips = mydataPolicy.getPipBooleans();
   pips.add(locationPipBoolean);
   mydataPolicy.setPipBooleans(pips);
  }
  return mydataPolicy;
 }

 private MydataPolicy timeIntervalConstraint(MydataPolicy mydataPolicy, TimeIntervalCondition timeIntervalConstraint) {
  if(null != timeIntervalConstraint)
  {
   List<DateTime> dateTimes = new ArrayList<>();
   String start = timeIntervalConstraint.getRightOperand().getValue();
   DateTime startTime = new DateTime(IntervalCondition.GT, start);
   dateTimes.add(startTime);

   String end = timeIntervalConstraint.getSecondRightOperand().getValue();
   DateTime endTime = new DateTime(IntervalCondition.LT, end);
   dateTimes.add(endTime);

   mydataPolicy.setDateTimes(dateTimes);
  }
  return mydataPolicy;
 }

 private MydataPolicy purposeConstraint(MydataPolicy mydataPolicy, PurposeCondition purposeConstraint) {
  if(null != purposeConstraint)
  {
   Parameter purposeP = new Parameter(ParameterType.STRING, LeftOperand.PURPOSE.getMydataLeftOperand()+"-uri", purposeConstraint.getRightOperand().getValue());

   Parameter[] pipParams = {purposeP};
   PIPBoolean purposePipBoolean = new PIPBoolean(this.solution, LeftOperand.PURPOSE, pipParams);

   List<PIPBoolean> pips = mydataPolicy.getPipBooleans();
   pips.add(purposePipBoolean);
   mydataPolicy.setPipBooleans(pips);
  }
  return mydataPolicy;
 }

 private MydataPolicy systemConstraint(MydataPolicy mydataPolicy, SystemCondition systemConstraint) {
  if(null != systemConstraint)
  {
   Parameter systemP = new Parameter(ParameterType.STRING,LeftOperand.SYSTEM.getMydataLeftOperand()+"-uri", systemConstraint.getRightOperand().getValue());

   Parameter[] pipParams = {systemP};
   PIPBoolean systemPipBoolean = new PIPBoolean(this.solution, LeftOperand.SYSTEM, pipParams);

   List<PIPBoolean> pips = mydataPolicy.getPipBooleans();
   pips.add(systemPipBoolean);
   mydataPolicy.setPipBooleans(pips);
  }
  return mydataPolicy;
 }

 private MydataPolicy paymentConstraint(MydataPolicy mydataPolicy, PaymentCondition paymentConstraint) {
  if(null != paymentConstraint)
  {
   Parameter valueP = new Parameter(ParameterType.NUMBER,"value",String.valueOf(paymentConstraint.getRightOperand().getValue()));
   Parameter contractP = new Parameter(ParameterType.STRING,"value", paymentConstraint.getContract());
   Parameter[] pipParams = {valueP, contractP};
   PIPBoolean paymentPipBoolean = new PIPBoolean(this.solution, LeftOperand.PAYAMOUNT, pipParams);

   List<PIPBoolean> pips = mydataPolicy.getPipBooleans();
   pips.add(paymentPipBoolean);
   mydataPolicy.setPipBooleans(pips);
  }
  return mydataPolicy;
 }

 private MydataPolicy eventConstraint(MydataPolicy mydataPolicy, EventCondition eventConstraint) {
  if(null != eventConstraint)
  {
   Parameter eventP = new Parameter(ParameterType.STRING,"event-uri", eventConstraint.getRightOperand().getValue());
   Parameter[] eventParams = {eventP};
   Count eventFirstOperand = new Count(this.solution, LeftOperand.EVENT, null, eventParams, FixedTime.THIS_HOUR);
   Constant eventSecondOperand = new Constant(ParameterType.NUMBER, "1");
   Condition eventCondition = new Condition(eventFirstOperand, Operator.GREATER_EQUAL, eventSecondOperand);
   //set conditions
   List<Condition> cons = mydataPolicy.getConditions();
   cons.add(eventCondition);
   mydataPolicy.setConditions(cons);
  }
  return mydataPolicy;
 }

 private String createCron(String y, String m, String d, String th, String tm, String ts)
 {
  String cron = ts + " " + tm + " " + th + " " + d + " " + m + " ? " + y ;
  return cron;
 }
}
