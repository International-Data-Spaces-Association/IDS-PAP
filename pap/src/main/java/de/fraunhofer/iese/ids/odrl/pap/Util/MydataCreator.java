package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.*;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.*;
import de.fraunhofer.iese.ids.odrl.pattern.PatternUtil;

import java.util.Arrays;
import java.util.Map;

public class MydataCreator {
	
	public static String createMYDATA(Map map){

		CategorizedPolicy categorizedPolicy = PatternUtil.getCategorizedPolicy(map);

		//set mydataPolicy target
		String target = "";
		if(null != categorizedPolicy.getDataUrl()) {
			target = getLastSplitElement(categorizedPolicy.getDataUrl().toString());
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
		//TODO: take care of obligation
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
		Parameter targetP = new Parameter(ParameterType.STRING,"target",target);
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

		// get conditions
		Condition targetCondition = new Condition(Operator.EQUALS, "target", target);
		Condition[] conditions = {targetCondition};
		if(!assignee.isEmpty() && categorizedPolicy.getProviderSide())
		{
			Condition assigneeCondition = new Condition(Operator.EQUALS, "assignee", assignee);
			conditions = (Condition[]) addElement(conditions, assigneeCondition);
		}

		if(categorizedPolicy instanceof SpecificSystemPolicy)
		{
			Condition systemCondition = new Condition(Operator.EQUALS, "system", ((SpecificSystemPolicy) categorizedPolicy).getSystem());
			conditions = (Condition[]) addElement(conditions, systemCondition);
		}

		PIPBoolean purposePipBoolean = null;
		Parameter assigneeP = new Parameter(ParameterType.STRING,"assignee",assignee);
		if(categorizedPolicy instanceof SpecificPurposePolicy)
		{
			Parameter purposeP = new Parameter(ParameterType.STRING,"value",getLastSplitElement(((SpecificPurposePolicy) categorizedPolicy).getPurpose()));
			if(assignee.isEmpty())
			{
				Parameter[] pipParams = {purposeP};
				purposePipBoolean = new PIPBoolean(solution, LeftOperand.PURPOSE, pipParams);
			}else{
				Parameter[] pipParams = {purposeP, assigneeP};
				purposePipBoolean = new PIPBoolean(solution, LeftOperand.PURPOSE, pipParams);
			}

		}

		// log pxp
		boolean hasDuty = false;
		if(categorizedPolicy instanceof LogAccessPolicy)
		{
			hasDuty = true;
			Parameter recipientP = new Parameter(ParameterType.STRING,"recipient",getLastSplitElement(((LogAccessPolicy) categorizedPolicy).getRecipient()));
			Parameter[] params = {targetP, assigneeP, recipientP};
			pxp = new ExecuteAction(solution, Action.LOG, params);
		}

		// create MYDATA MydataPolicy
		MydataPolicy mydataPolicy = new MydataPolicy(solution, pid, action, decision, hasDuty);
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
