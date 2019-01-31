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
		// TODO: solution and assignee are always the same?
		String solution = "";
		String assignee = "";
		if(categorizedPolicy.getPolicyType().equals(PolicyType.Agreement) && !categorizedPolicy.getAssignee().isEmpty()) {
			assignee = getLastSplitElement(categorizedPolicy.getAssignee());
			solution = getLastSplitElement(categorizedPolicy.getAssignee());
		}

		// get timer
		//get boolean PIP for delay period
		Timer timer = null;
		PIPBoolean DelayPeriodPipBoolean = null;
		Parameter targetP = new Parameter(ParameterType.STRING,"target",target);
		if(categorizedPolicy instanceof DeleteAtferPolicy)
		{
			Duration d = ((DeleteAtferPolicy) categorizedPolicy).getDuration();
			timer = new Timer(d.getTimeUnit(),pid, solution, action,targetP);

			Parameter valueP = new Parameter(ParameterType.NUMBER,"value",String.valueOf(d.getValue()));
			Parameter unitP = new Parameter(ParameterType.STRING,"value",d.getTimeUnit().toString());
			Parameter[] pipParams = {valueP, unitP, targetP};
			DelayPeriodPipBoolean = new PIPBoolean(solution, LeftOperand.DELAY_PERIOD, pipParams);
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
		if(!assignee.isEmpty())
		{
			Condition assigneeCondition = new Condition(Operator.EQUALS, "assignee", assignee);
			addElement(conditions, assigneeCondition);
		}

		// create MYDATA MydataPolicy
		MydataPolicy mydataPolicy = new MydataPolicy(solution, pid, action, decision);
		//set timer
		if (null != timer)
		{
			mydataPolicy.setTimer(timer);
		}

		//set pipBooleans
		if (null != DelayPeriodPipBoolean)
		{
			PIPBoolean[] pipBooleans= {DelayPeriodPipBoolean};
			mydataPolicy.setPipBooleans(pipBooleans);
		}

		// set execute action
		if (null != pxp)
		{
			mydataPolicy.setPxp(pxp);
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

	static Condition[] addElement(Condition[] a, Condition e) {
		a  = Arrays.copyOf(a, a.length + 1);
		a[a.length - 1] = e;
		return a;
	}
}
