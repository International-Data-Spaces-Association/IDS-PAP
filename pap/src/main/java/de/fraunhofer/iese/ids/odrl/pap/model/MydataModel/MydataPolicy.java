package de.fraunhofer.iese.ids.odrl.pap.model.MydataModel;


import de.fraunhofer.iese.ids.odrl.pap.model.Action;
import de.fraunhofer.iese.ids.odrl.pap.model.RuleType;
import lombok.Data;

@Data
public class MydataPolicy {
 Timer timer;
 Condition[] conditions;
 PIPBoolean[] pipBooleans;
 String solution;
 String pid;
 Action action;
 RuleType decision;
 ExecuteAction pxp;

 public MydataPolicy(String solution, String pid, Action action, RuleType decision)
 {
  this.solution = solution;
  this.pid = pid;
  this.action = action;
  this.decision = decision;
 }

 @Override
 public String toString() {
  String conditionsAndPIPs = getConditionsAndPIPs();
  String conditionsBlock = getDecisionBlock();
  String timer = getTimerForPolicy();

  String returnPolicy = timer + "\r\n" +
          "  <policy id='urn:policy:" + solution + ":" + pid + "'>    \r\n" +
          "    <mechanism event='urn:action:" + solution + ":" + action.getAction() + "'>    \r\n" +
          "      <if>   \r\n" +
          conditionsAndPIPs +
          conditionsBlock +
          "    </mechanism>    \r\n" +
          "  </policy>    \r\n";
  return returnPolicy;
 }

 private String getDecisionBlock() {
  RuleType elseDecision = getElseDecision();
  if(decision.equals(RuleType.OBLIGATION))
  {
   if(null != pxp)
   {
    return  "        <then>  \r\n" +
            pxp.toString() +
            "        </then>  \r\n" +
            "      </if>   \r\n" ;
   }else {
    return "";
   }

  }else {
   return  "        <then>  \r\n" +
           "          <" + decision.getMydataDecision() + "/>  \r\n" +
           "        </then>  \r\n" +
           "      </if>   \r\n" +
           "      <else>   \r\n" +
           "        <" + elseDecision.getMydataDecision() + "/>   \r\n" +
           "      </else>   \r\n";
  }
 }

 private String getTimerForPolicy() {
  if (null != timer)
  {
   return timer.toString();
  }
   return "";
 }

 private RuleType getElseDecision() {
  if(decision.equals(RuleType.PERMISSION) || decision.equals(RuleType.OBLIGATION))
  {
   return RuleType.PROHIBITION;
  }else if (decision.equals(RuleType.PROHIBITION))
  {
   return RuleType.PERMISSION;
  }
  return null;
 }

 private String getConditionsAndPIPs() {
  if((conditions == null || conditions.length == 0) && (pipBooleans == null || pipBooleans.length == 0 ))
  {
   return "";
  }else if (conditions.length == 1 && (pipBooleans == null || pipBooleans.length == 0 ))
  {
   return conditions[0].toString();
  }else if ((conditions == null || conditions.length == 0) && pipBooleans.length == 1)
  {
   return pipBooleans[0].toString();
  }else //if bigger
  {
   String conditions = "";
   String pips= "";
   for(int i = 0; i< this.conditions.length; i++)
   {
    conditions += this.conditions[i].toString();
   }
   for(int i=0 ; i<pipBooleans.length; i++)
   {
    pips += pipBooleans[i].toString();
   }

   return  "        <and>  \r\n" +
           conditions +
           pips +
           "        </and>  \r\n";
  }
 }
}
