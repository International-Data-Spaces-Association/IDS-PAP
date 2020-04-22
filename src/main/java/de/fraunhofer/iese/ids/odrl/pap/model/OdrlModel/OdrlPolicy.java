package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel;


import de.fraunhofer.iese.ids.odrl.pap.model.Policy.*;
import lombok.Data;

import java.net.URI;
import java.util.List;


@Data
public class OdrlPolicy implements IPolicy {
PermissionRuleUseAction permissionRuleUseAction;
ProhibitionRuleUseAction prohibitionRuleUseAction;
ObligationRuleAnonymizeAction obligationRuleAnonymizeAction;
PermissionRuleReadAction permissionRuleReadAction;
ProhibitionRuleReadAction prohibitionRuleReadAction;
ObligationRuleDeleteAction obligationRuleDeleteAction;
ObligationRuleInformAction obligationRuleInformAction;
ObligationRuleLogAction obligationRuleLogAction;
PermissionRulePrintAction permissionRulePrintAction;
ProhibitionRulePrintAction prohibitionRulePrintAction;
PermissionRuleDistributeAction permissionRuleDistributeAction;
ProhibitionRuleDistributeAction prohibitionRuleDistributeAction;
URI profile;
URI policyId;
PolicyType type;
URI target;
Party consumer;
Party provider;
boolean providerSide;

 public OdrlPolicy()
 {
  this.profile = URI.create("http://example.com/ids-profile");
  this.provider = new Party(PartyType.PROVIDER, URI.create("http://example.com/party/my-party"));
 }

 public OdrlPolicy(URI policyId, PolicyType type, URI target, Party consumer, boolean providerSide) {
  this.profile = URI.create("http://example.com/ids-profile");
  this.policyId = policyId;
  this.type = type;
  this.target = target;
  this.consumer = consumer;
  this.provider = new Party(PartyType.PROVIDER, URI.create("http://example.com/party/my-party"));
  this.providerSide = providerSide;
 }

 @Override
 public String toString() {
  return  " {    \r\n" +
          "  \"@context\": \"http://www.w3.org/ns/odrl.jsonld\",    \r\n" +
          "  \"@type\": \""+ this.type.getStringRepresentation() +"\",    \r\n" +
          "  \"uid\": \""+ this.policyId.toString() +"\",    \r\n" +
          "  \"profile\": \""+ this.profile.toString() +"\",    \r\n" +
          "  \"target\": \""+ this.target.toString() +"\",    \r\n" +
          getProviderBlock() +
          getConsumerBlock() +
          getRulesBlock() +
          "} ";
 }

 private String getConsumerBlock() {
  if(null != this.consumer && !this.type.equals(PolicyType.OFFER)) {
   return this.consumer.toString();
  }
  return "";
 }

 private String getProviderBlock() {
  if(null != this.provider && !this.type.equals(PolicyType.REQUEST)) {
   return this.provider.toString();
  }
  return "";
 }

 private String getRulesBlock() {
  String rulesBlock = "";
  //USE
  if(null != this.permissionRuleUseAction)
  {
   rulesBlock += this.permissionRuleUseAction.toString();
  }
  if(null != this.prohibitionRuleUseAction)
  {
   rulesBlock += this.prohibitionRuleUseAction.toString();
  }
  //ANONYMIZE
  if(null != this.obligationRuleAnonymizeAction)
  {
   rulesBlock += this.obligationRuleAnonymizeAction.toString();
  }
  //DISTRIBUTE
  if(null != this.permissionRuleDistributeAction)
  {
   rulesBlock += this.permissionRuleDistributeAction.toString();
  }
  if(null != this.prohibitionRuleDistributeAction)
  {
   rulesBlock += this.prohibitionRuleDistributeAction.toString();
  }
  //DELETE
  if(null != this.obligationRuleDeleteAction)
  {
   rulesBlock += this.obligationRuleDeleteAction.toString();
  }
  //READ
  if(null != this.permissionRuleReadAction)
  {
   rulesBlock += this.permissionRuleReadAction.toString();
  }
  if(null != this.prohibitionRuleReadAction)
  {
   rulesBlock += this.prohibitionRuleReadAction.toString();
  }
  //PRINT
  if(null != this.permissionRulePrintAction)
  {
   rulesBlock += this.permissionRulePrintAction.toString();
  }
  if(null != this.prohibitionRulePrintAction)
  {
   rulesBlock += this.prohibitionRulePrintAction.toString();
  }
  return rulesBlock;
 }

 @Override
 public void setPermissionRuleUseAction(IPermissionRuleUseAction permissionRuleUseAction) {
  this.permissionRuleUseAction = (PermissionRuleUseAction) permissionRuleUseAction;
 }

 @Override
 public void setProhibitionRuleUseAction(IProhibitionRuleUseAction prohibitionRuleUseAction) {
  this.prohibitionRuleUseAction = (ProhibitionRuleUseAction) prohibitionRuleUseAction;
 }

 @Override
 public void setObligationRuleAnonymizeAction(IObligationRuleAnonymizeAction obligationRuleAnonymizeAction) {
this.obligationRuleAnonymizeAction = (ObligationRuleAnonymizeAction) obligationRuleAnonymizeAction;
 }

 @Override
 public void setPermissionRuleReadAction(IPermissionRuleReadAction permissionRuleReadAction) {
  this.permissionRuleReadAction = (PermissionRuleReadAction) permissionRuleReadAction;
 }

 @Override
 public void setProhibitionRuleReadAction(IProhibitionRuleReadAction prohibitionRuleReadAction) {
this.prohibitionRuleReadAction = (ProhibitionRuleReadAction) prohibitionRuleReadAction;
 }

 @Override
 public void setObligationRuleLogAction(IObligationRuleLogAction obligationRuleLogAction) {
this.obligationRuleLogAction = (ObligationRuleLogAction) obligationRuleLogAction;
 }

 @Override
 public void setObligationRuleInformAction(IObligationRuleInformAction obligationRuleInformAction) {
this.obligationRuleInformAction = (ObligationRuleInformAction) obligationRuleInformAction;
 }

 @Override
 public void setPermissionRulePrintAction(IPermissionRulePrintAction permissionRulePrintAction) {
this.permissionRulePrintAction = (PermissionRulePrintAction) permissionRulePrintAction;
 }

 @Override
 public void setProhibitionRulePrintAction(IProhibitionRulePrintAction prohibitionRulePrintAction) {
this.prohibitionRulePrintAction = (ProhibitionRulePrintAction) prohibitionRulePrintAction;
 }

 @Override
 public void setObligationRuleDeleteAction(IObligationRuleDeleteAction obligationRuleDeleteAction) {
this.obligationRuleDeleteAction = (ObligationRuleDeleteAction) obligationRuleDeleteAction;
 }

 @Override
 public void setPermissionRuleDistributeAction(IPermissionRuleDistributeAction permissionRuleDistributeAction) {
  this.permissionRuleDistributeAction = (PermissionRuleDistributeAction) permissionRuleDistributeAction;
 }

 @Override
 public void setProhibitionRuleDistributeAction(IProhibitionRuleDistributeAction prohibitionRuleDistributeAction) {
this.prohibitionRuleDistributeAction = (ProhibitionRuleDistributeAction) prohibitionRuleDistributeAction;
 }

}
