package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.*;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.*;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.*;
import de.fraunhofer.iese.ids.odrl.pattern.PatternUtil;

import java.util.Arrays;
import java.util.Map;

public class MydataCreator {
	
	public static String createMYDATA(Map map){

		CategorizedPolicy categorizedPolicy = PatternUtil.getCategorizedPolicy(map);

		try
		{
			//set mydataPolicy target
			String target = "";
			if(null != categorizedPolicy.getDataUrl()) {
				target = categorizedPolicy.getDataUrl().toString();
			}

			//set mydataPolicy id
			String pid = "";
			if(null != categorizedPolicy.getPolicyUrl()) {
				pid = getLastSplitElement(categorizedPolicy.getPolicyUrl().toString());
			}

			//set mydataPolicy action
			Action action = null;
			if(null != categorizedPolicy.getAction()) {
				action = categorizedPolicy.getAction();
			}

			//set mydataPolicy decision
			RuleType decision = null;
			if(null != categorizedPolicy.getRuleType()) {
				decision = categorizedPolicy.getRuleType();
			}

			//set mydataPolicy assignee : if type is agreement or request, it will have an assignee condition
			String solution = "";
			String assignee = "";
			String assigner = "";
			if(!categorizedPolicy.getAssigner().isEmpty()) {
				assigner = getLastSplitElement(categorizedPolicy.getAssigner());
				// by default, it is a provider side policy
				solution = assigner;
			}
			if(!categorizedPolicy.getAssignee().isEmpty()) {
				assignee = getLastSplitElement(categorizedPolicy.getAssignee());
			}

			if(!categorizedPolicy.getProviderSide())
			{
				// when it is not a provider side policy, set the solution to assignee
				solution = assignee;
			}

			// get timer
			//get boolean PIP for delay period
			Timer timer = null;
			PIPBoolean delayPeriodPipBoolean = null;
			Parameter targetP = new Parameter(ParameterType.STRING,"target-uri",target);
			if(categorizedPolicy instanceof DeleteAtferPolicy)
			{
				Duration d = ((DeleteAtferPolicy) categorizedPolicy).getDuration();
				timer = new Timer(d.getTimeUnit(),pid, solution, action,targetP);

				Parameter valueP = new Parameter(ParameterType.NUMBER,"value",String.valueOf(d.getValue()));
				Parameter unitP = new Parameter(ParameterType.STRING,"value",d.getTimeUnit().toString());
				Parameter[] pipParams = {valueP, unitP, targetP};
				delayPeriodPipBoolean = new PIPBoolean(solution, LeftOperand.DELAY_PERIOD, pipParams);
			}

			//get execute action
			ExecuteAction pxp = null;
			if (categorizedPolicy.getAction().equals(Action.DELETE) && categorizedPolicy.getRuleType().equals(RuleType.OBLIGATION))
			{
				Parameter[] params = {targetP};
				pxp = new ExecuteAction(solution, Action.DELETE, params);
			}

			if (categorizedPolicy.getAction().equals(Action.ANONYMIZE) && categorizedPolicy.getRuleType().equals(RuleType.OBLIGATION))
			{
				Parameter[] params = {targetP};
				pxp = new ExecuteAction(solution, Action.ANONYMIZE, params);
			}

			// get conditions
			Event targetFirstOperand = new Event(ParameterType.STRING, EventParameter.TARGET.getEventParameter(), "uri" );
			Constant targetSecondOperand = new Constant(ParameterType.STRING, target);
			Condition targetCondition = new Condition(targetFirstOperand,Operator.EQUALS, targetSecondOperand);
			Condition[] conditions = {targetCondition};
			if(!assignee.isEmpty() && categorizedPolicy.getProviderSide())
			{
				Event assigneeFirstOperand = new Event(ParameterType.STRING, EventParameter.ASSIGNEE.getEventParameter(), "name");
				Constant assigneeSecondOperand = new Constant(ParameterType.STRING, assignee);
				Condition assigneeCondition = new Condition(assigneeFirstOperand, Operator.EQUALS, assigneeSecondOperand);
				conditions = (Condition[]) addElement(conditions, assigneeCondition);
			}

			if(categorizedPolicy instanceof SpecificEventPolicy)
			{
				Parameter eventP = new Parameter(ParameterType.STRING,"event-uri", ((SpecificEventPolicy) categorizedPolicy).getEvent());
				Parameter[] countParams = {eventP};
				Count eventFirstOperand = new Count(solution, LeftOperand.EVENT, null, countParams, FixedTime.THIS_HOUR);
				Constant eventSecondOperand = new Constant(ParameterType.NUMBER, "1");
				Condition eventCondition = new Condition(eventFirstOperand, Operator.GREATER_EQUAL, eventSecondOperand);
				conditions = (Condition[]) addElement(conditions, eventCondition);
			}

			if(categorizedPolicy instanceof CountAccessPolicy)
			{
				Parameter[] countParams = {};
				Count eventFirstOperand = new Count(solution, null, action, countParams, FixedTime.ALWAYS);
				Constant eventSecondOperand = new Constant(ParameterType.NUMBER, ((CountAccessPolicy) categorizedPolicy).getCount());
				Condition eventCondition = new Condition(eventFirstOperand, Operator.LESS, eventSecondOperand);
				conditions = (Condition[]) addElement(conditions, eventCondition);
			}

			PIPBoolean purposePipBoolean = null;
			Parameter assigneeP = new Parameter(ParameterType.STRING,"assignee-name",assignee);
			if(categorizedPolicy instanceof SpecificPurposePolicy)
			{
				Parameter purposeP = new Parameter(ParameterType.STRING,LeftOperand.PURPOSE.getMydataLeftOperand()+"-uri",((SpecificPurposePolicy) categorizedPolicy).getPurpose());
				if(assignee.isEmpty())
				{
					Parameter[] pipParams = {purposeP};
					purposePipBoolean = new PIPBoolean(solution, LeftOperand.PURPOSE, pipParams);
				}else{
					Parameter[] pipParams = {purposeP, assigneeP};
					purposePipBoolean = new PIPBoolean(solution, LeftOperand.PURPOSE, pipParams);
				}
			}

			PIPBoolean systemPipBoolean = null;
			if(categorizedPolicy instanceof SpecificSystemPolicy)
			{
				Parameter systemP = new Parameter(ParameterType.STRING,LeftOperand.SYSTEM.getMydataLeftOperand()+"-uri",((SpecificSystemPolicy) categorizedPolicy).getSystem());

				Parameter[] pipParams = {systemP};
				systemPipBoolean = new PIPBoolean(solution, LeftOperand.SYSTEM, pipParams);
			}

			PIPBoolean encodingPipBoolean = null;
			if(categorizedPolicy instanceof EncodingPolicy)
			{
				Parameter encodingP = new Parameter(ParameterType.STRING,LeftOperand.ENCODING.getMydataLeftOperand()+"-uri",((EncodingPolicy) categorizedPolicy).getEncoding());
				Parameter[] pipParams = {encodingP, targetP};
				encodingPipBoolean = new PIPBoolean(solution, LeftOperand.ENCODING, pipParams);

			}

			// log pxp
			boolean hasDuty = false;
			if(categorizedPolicy instanceof LogAccessPolicy)
			{
				hasDuty = true;
				Parameter recipientP = new Parameter(ParameterType.STRING,LeftOperand.RECIPIENT.getMydataLeftOperand()+"-uri",((LogAccessPolicy) categorizedPolicy).getRecipient());
				Parameter[] params = {targetP, assigneeP, recipientP};
				pxp = new ExecuteAction(solution, categorizedPolicy.getDutyAction(), params);
			}

			if(categorizedPolicy instanceof InformPolicy)
			{
				hasDuty = true;
				Parameter informedPartyP = new Parameter(ParameterType.STRING,LeftOperand.INFORMEDPARTY.getMydataLeftOperand()+"-uri",((InformPolicy) categorizedPolicy).getInformedParty());
				Parameter[] params = {informedPartyP};
				pxp = new ExecuteAction(solution, categorizedPolicy.getDutyAction(), params);
			}

			Modify modify = null;
			if(categorizedPolicy instanceof AnonymizeInTransitPolicy)
			{
				// anonymize in transit policy needs a modifier!
				Parameter anonymizeMethodP = new Parameter(ParameterType.NUMBER,LeftOperand.DIGIT.getMydataLeftOperand(),((AnonymizeInTransitPolicy) categorizedPolicy).getDigit());
				Parameter[] params = {anonymizeMethodP};
				modify = new Modify(EventParameter.TARGET.getEventParameter(), categorizedPolicy.getDutyAction(), ((AnonymizeInTransitPolicy) categorizedPolicy).getJsonPath(),params);
			}

			// create MYDATA MydataPolicy
			MydataPolicy mydataPolicy = new MydataPolicy(solution, pid, action, decision, hasDuty, modify);
			//set timer
			if (null != timer)
			{
				mydataPolicy.setTimer(timer);
			}

			//set pipBooleans
			PIPBoolean[] pipBooleans = {};
			if (null != delayPeriodPipBoolean)
			{
				pipBooleans = (PIPBoolean[])addElement(pipBooleans, delayPeriodPipBoolean);
			}
			if(null != purposePipBoolean)
			{
				pipBooleans = (PIPBoolean[])addElement(pipBooleans, purposePipBoolean);
			}
			if(null != encodingPipBoolean)
			{
				pipBooleans = (PIPBoolean[])addElement(pipBooleans, encodingPipBoolean);
			}
			if(null != systemPipBoolean)
			{
				pipBooleans = (PIPBoolean[])addElement(pipBooleans, systemPipBoolean);
			}
			if(pipBooleans.length >0)
			{
				mydataPolicy.setPipBooleans(pipBooleans);
			}


			// set execute action
			if (null != pxp)
			{
				mydataPolicy.setPxp(pxp);
			}

			// get and set datetimes
			if(categorizedPolicy instanceof ReadDataIntervalPolicy)
			{
				DateTime startTime = new DateTime(IntervalCondition.GT, ((ReadDataIntervalPolicy) categorizedPolicy).getStartTime());
				DateTime endTime = new DateTime(IntervalCondition.LT, ((ReadDataIntervalPolicy) categorizedPolicy).getEndTime());
				DateTime[] dateTimes = {startTime, endTime};
				mydataPolicy.setDateTimes(dateTimes);
			}

			//set conditions
			mydataPolicy.setConditions(conditions);

			return mydataPolicy.toString();
		}
		catch (NullPointerException e){
			return "Please, be aware that your ODRL policy must comply to the IDS profile. " +
					"Undefined IDS Actions or Left Operands are not accepted. " +
					"Check your ODRL policy and try again!";
		}

	}

	 private static String getLastSplitElement(String url) {
		String value;
		String[] bits = url.split(":");
		value = bits[bits.length-1];
		return value;
	}

	static Object[] addElement(Object[] a, Object e) {
		a  = Arrays.copyOf(a, a.length + 1);
		a[a.length - 1] = e;
		return a;
	}
}
