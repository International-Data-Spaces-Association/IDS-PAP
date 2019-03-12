package de.fraunhofer.iese.ids.odrl.pap.model.MydataModel;


import de.fraunhofer.iese.ids.odrl.pap.model.Action;
import de.fraunhofer.iese.ids.odrl.pap.model.RuleType;
import lombok.Data;

@Data
public class MydataPolicy {
 Timer timer;
 Condition[] conditions;
 PIPBoolean[] pipBooleans;
 DateTime[] dateTimes;
 String solution;
 String pid;
 Action action;
 RuleType decision;
 ExecuteAction pxp;
 boolean hasDuty;
 Modify modify;

 public MydataPolicy(String solution, String pid, Action action, RuleType decision, boolean hasDuty, Modify modify)
 {
  this.solution = solution;
  this.pid = pid;
  this.action = action;
  this.decision = decision;
  this.hasDuty = hasDuty;
  this.modify = modify;
 }

 @Override
 public String toString() {
  String conditionsBlock = getConditions();
  String decisionBlock = getDecisionBlock();
  String timer = getTimerForPolicy();

  final String returnPolicy = timer + "\r\n" +
          "  <policy id='urn:policy:" + solution + ":" + pid + "' xmlns='http://www.iese.fraunhofer.de/ind2uce/3.2.46/ind2uceLanguage'\n" +
      "  xmlns:tns='http://www.iese.fraunhofer.de/ind2uce/3.2.46/ind2uceLanguage'\n" +
      "  xmlns:parameter='http://www.iese.fraunhofer.de/ind2uce/3.2.46/parameter'\n" +
      "  xmlns:pip='http://www.iese.fraunhofer.de/ind2uce/3.2.46/pip'\n" +
      "  xmlns:function='http://www.iese.fraunhofer.de/ind2uce/3.2.46/function'\n" +
      "  xmlns:event='http://www.iese.fraunhofer.de/ind2uce/3.2.46/event'\n" +
      "  xmlns:constant='http://www.iese.fraunhofer.de/ind2uce/3.2.46/constant'\n" +
      "  xmlns:variable='http://www.iese.fraunhofer.de/ind2uce/3.2.46/variable'\n" +
      "  xmlns:variableDeclaration='http://www.iese.fraunhofer.de/ind2uce/3.2.46/variableDeclaration'\n" +
      "  xmlns:valueChanged='http://www.iese.fraunhofer.de/ind2uce/3.2.46/valueChanged'\n" +
      "  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'\n" +
      "  xmlns:date='http://www.iese.fraunhofer.de/ind2uce/3.2.46/date'\n" +
      "  xmlns:time='http://www.iese.fraunhofer.de/ind2uce/3.2.46/time'>    \r\n" +
          "    <mechanism event='urn:action:" + solution + ":" + action.name().toLowerCase() + "'>    \r\n" +
          "      <if>   \r\n" +
          conditionsBlock +
          decisionBlock +
          "    </mechanism>    \r\n" +
          "  </policy>    \r\n";
  return returnPolicy;
 }

 private String getDecisionBlock() {
  RuleType elseDecision = getElseDecision();
  if(null != modify)
  {
   return  "        <then>  \r\n" +
           modify.toString() +
           "        </then>  \r\n" +
           "      </if>   \r\n" ;
  } else if(decision.equals(RuleType.OBLIGATION) || (decision.equals(RuleType.PERMISSION) && this.hasDuty))
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

 private String getConditions() {
  if((conditions == null || conditions.length == 0) && (pipBooleans == null || pipBooleans.length == 0 ) && (dateTimes == null || dateTimes.length == 0 ))
  {
   return "";
  }else if (conditions.length == 1 && (pipBooleans == null || pipBooleans.length == 0 )&& (dateTimes == null || dateTimes.length == 0 ))
  {
   return conditions[0].toString();
  }else if ((conditions == null || conditions.length == 0) && pipBooleans.length == 1 && (dateTimes == null || dateTimes.length == 0 ))
  {
   return pipBooleans[0].toString();
  }else if((conditions == null || conditions.length == 0) && (pipBooleans == null || pipBooleans.length == 0 ) && dateTimes.length == 1)
  {
   return dateTimes[0].toString();
  }else //if bigger
  {
   String conditions = "";
   String pips= "";
   String dates= "";
   if(conditions != null ){
    for(int i = 0; i< this.conditions.length; i++)
    {
     conditions += this.conditions[i].toString();
    }
   }
   if(pipBooleans != null)
   {
    for(int i=0 ; i<pipBooleans.length; i++)
    {
     pips += pipBooleans[i].toString();
    }
   }
   if(dateTimes != null)
   {
    for(int i=0 ; i<dateTimes.length; i++)
    {
     dates += dateTimes[i].toString();
    }
   }


   return  "        <and>  \r\n" +
           conditions +
           pips +
           dates +
           "        </and>  \r\n";
  }
 }
}
