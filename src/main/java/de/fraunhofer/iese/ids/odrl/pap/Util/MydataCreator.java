package de.fraunhofer.iese.ids.odrl.pap.Util;

import de.fraunhofer.iese.ids.odrl.pap.model.*;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.*;
import de.fraunhofer.iese.ids.odrl.pap.model.MydataModel.Timer;
import de.fraunhofer.iese.ids.odrl.pap.model.Policy.*;
import de.fraunhofer.iese.ids.odrl.pattern.PatternUtil;

import java.util.*;


public class MydataCreator {
	
	public static String createMYDATA(Map map, Boolean providerSide){

		if(null == providerSide)
		{
			providerSide = true;
		}
		CategorizedPolicy categorizedPolicy = PatternUtil.getCategorizedPolicy(map, providerSide);

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

			//set mydataPolicy dutyAction
			Action dutyAction = null;
			boolean hasDuty = false;
			if(null != categorizedPolicy.getDutyAction()) {
				dutyAction = categorizedPolicy.getDutyAction();
				hasDuty = true;
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
			if(null != categorizedPolicy.getAssigner() && !categorizedPolicy.getAssigner().isEmpty()) {
				assigner = getLastSplitElement(categorizedPolicy.getAssigner());
				// by default, it is a provider side policy
				solution = assigner;
			}
			if(null != categorizedPolicy.getAssignee() && !categorizedPolicy.getAssignee().isEmpty()) {
				assignee = getLastSplitElement(categorizedPolicy.getAssignee());
			}

			if(!categorizedPolicy.getProviderSide())
			{
				// when it is not a provider side policy, set the solution to assignee
				solution = assignee;
			}

			Parameter targetP = new Parameter(ParameterType.STRING,"target-uri",target);

			// get timer
			//get boolean PIP for delay period
			Timer timer = null;
			PIPBoolean delayPeriodPipBoolean = null;

			Duration d = ((AbstractPolicy) categorizedPolicy).getDuration();
			if (PatternUtil.isNotNull(d) && (hasDuty || decision.equals(RuleType.OBLIGATION)))
			{
				timer = new Timer(d.getTimeUnit(), "",pid, solution, action,targetP);

				Parameter valueP = new Parameter(ParameterType.NUMBER,"value",String.valueOf(d.getValue()));
				Parameter unitP = new Parameter(ParameterType.STRING,"value",d.getTimeUnit().toString());
				Parameter[] pipParams = {valueP, unitP, targetP};
				delayPeriodPipBoolean = new PIPBoolean(solution, LeftOperand.DELAYPERIOD, pipParams);
			}

			//get boolean PIP for payment
			PIPBoolean paymentPipBoolean = null;

			Payment p = ((AbstractPolicy) categorizedPolicy).getPayment();
			if (PatternUtil.isNotNull(p))
			{
				Parameter valueP = new Parameter(ParameterType.NUMBER,"value",String.valueOf(p.getValue()));
				Parameter contractP = new Parameter(ParameterType.STRING,"value",p.getContract());
				Parameter[] pipParams = {valueP, contractP, targetP};
				paymentPipBoolean = new PIPBoolean(solution, LeftOperand.PAYAMOUNT, pipParams);
			}

			String dateTimeRefinement = ((AbstractPolicy) categorizedPolicy).getDateTime();
			DateTime dateTime = null;
			if (PatternUtil.isNotNull(dateTimeRefinement))
			{
				dateTime = new DateTime(IntervalCondition.EQ, dateTimeRefinement);
			}
			if (PatternUtil.isNotNull(dateTime) && (hasDuty || decision.equals(RuleType.OBLIGATION)))
			{
				String cron = createCron(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());
				timer = new Timer(null,cron,pid, solution, action,targetP);
			}

			//get execute action
			ExecuteAction pxp = null;
			if ((action.equals(Action.DELETE) && decision.equals(RuleType.OBLIGATION))
					|| (hasDuty && dutyAction.equals(Action.DELETE)))
			{
				Parameter[] params = {targetP};
				pxp = new ExecuteAction(solution, Action.DELETE, params);
			}

            if ((action.equals(Action.NEXTPOLICY) && decision.equals(RuleType.OBLIGATION))
                    || (hasDuty && dutyAction.equals(Action.NEXTPOLICY)))
            {
                Parameter[] params = {targetP};
                pxp = new ExecuteAction(solution, Action.NEXTPOLICY, params);
            }

			if ((action.equals(Action.ANONYMIZE) && decision.equals(RuleType.OBLIGATION))
					|| (hasDuty && dutyAction.equals(Action.ANONYMIZE)))
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

			String event = ((AbstractPolicy) categorizedPolicy).getEvent();
			if (PatternUtil.isNotNull(event))
			{
				Parameter eventP = new Parameter(ParameterType.STRING,"event-uri", event);
				Parameter[] countParams = {eventP};
				Count eventFirstOperand = new Count(solution, LeftOperand.EVENT, null, countParams, FixedTime.THIS_HOUR);
				Constant eventSecondOperand = new Constant(ParameterType.NUMBER, "1");
				Condition eventCondition = new Condition(eventFirstOperand, Operator.GREATER_EQUAL, eventSecondOperand);
				conditions = (Condition[]) addElement(conditions, eventCondition);
			}

			String count = ((AbstractPolicy) categorizedPolicy).getCount();
			if (PatternUtil.isNotNull(count))
			{
				Parameter[] countParams = {};
				Count countFirstOperand = new Count(solution, null, action, countParams, FixedTime.ALWAYS);
				Constant countSecondOperand = new Constant(ParameterType.NUMBER, count);
				Condition countCondition = new Condition(countFirstOperand, Operator.LESS, countSecondOperand);
				conditions = (Condition[]) addElement(conditions, countCondition);
			}

			PIPBoolean purposePipBoolean = null;
			Parameter assigneeP = new Parameter(ParameterType.STRING,"assignee-name",assignee);
			String purpose = ((AbstractPolicy) categorizedPolicy).getPurpose();
			if (PatternUtil.isNotNull(purpose))
			{
				Parameter purposeP = new Parameter(ParameterType.STRING,LeftOperand.PURPOSE.getMydataLeftOperand()+"-uri",purpose);
				if(assignee.isEmpty())
				{
					Parameter[] pipParams = {purposeP};
					purposePipBoolean = new PIPBoolean(solution, LeftOperand.PURPOSE, pipParams);
				}else{
					Parameter[] pipParams = {purposeP, assigneeP};
					purposePipBoolean = new PIPBoolean(solution, LeftOperand.PURPOSE, pipParams);
				}
			}

			PIPBoolean locationPipBoolean = null;
			String location = ((AbstractPolicy) categorizedPolicy).getLocation();
			if (PatternUtil.isNotNull(location))
			{
				Parameter locationP = new Parameter(ParameterType.STRING,LeftOperand.ABSOLUTESPATIALPOSITION.getMydataLeftOperand()+"-uri", location);

				Parameter[] pipParams = {locationP};
				locationPipBoolean = new PIPBoolean(solution, LeftOperand.ABSOLUTESPATIALPOSITION, pipParams);
			}

			PIPBoolean systemPipBoolean = null;
			String system = ((AbstractPolicy) categorizedPolicy).getSystem();
			if (PatternUtil.isNotNull(system))
			{
				Parameter systemP = new Parameter(ParameterType.STRING,LeftOperand.SYSTEM.getMydataLeftOperand()+"-uri", system);

				Parameter[] pipParams = {systemP};
				systemPipBoolean = new PIPBoolean(solution, LeftOperand.SYSTEM, pipParams);
			}

			PIPBoolean encodingPipBoolean = null;
			String encoding = ((AbstractPolicy) categorizedPolicy).getEncoding();
			if (PatternUtil.isNotNull(encoding))
			{
				Parameter encodingP = new Parameter(ParameterType.STRING,LeftOperand.ENCODING.getMydataLeftOperand()+"-uri", encoding);
				Parameter[] pipParams = {encodingP, targetP};
				encodingPipBoolean = new PIPBoolean(solution, LeftOperand.ENCODING, pipParams);

			}

			String recipient = ((AbstractPolicy) categorizedPolicy).getRecipient();
			if (PatternUtil.isNotNull(recipient) && (hasDuty || decision.equals(RuleType.OBLIGATION)))
			{
				Parameter recipientP = new Parameter(ParameterType.STRING,LeftOperand.RECIPIENT.getMydataLeftOperand()+"-uri", recipient);
				Parameter[] params = {targetP, assigneeP, recipientP};
				pxp = new ExecuteAction(solution, categorizedPolicy.getDutyAction(), params);
			}

            String systemDevice = ((AbstractPolicy) categorizedPolicy).getSystemDevice();
            if (PatternUtil.isNotNull(systemDevice) && (hasDuty || decision.equals(RuleType.OBLIGATION)))
            {
                Parameter systemDeviceP = new Parameter(ParameterType.STRING,LeftOperand.SYSTEMDEVICE.getMydataLeftOperand()+"-uri", systemDevice);
                Parameter[] params = {targetP, assigneeP, systemDeviceP};
                pxp = new ExecuteAction(solution, categorizedPolicy.getDutyAction(), params);
            }

			String informedPartyValue = ((AbstractPolicy) categorizedPolicy).getInformedParty();
			if (PatternUtil.isNotNull(informedPartyValue) && (hasDuty || decision.equals(RuleType.OBLIGATION)))
			{
				hasDuty = true;
				Parameter informedPartyP = new Parameter(ParameterType.STRING,PartyFunction.INFORMEDPARTY.getMydataPartyFunction()+"-uri", informedPartyValue);
				Parameter[] params = {informedPartyP};
				pxp = new ExecuteAction(solution, categorizedPolicy.getDutyAction(), params);
			}

			Modify modify = null;
			String digit = ((AbstractPolicy) categorizedPolicy).getDigit();
			String jsonPath = ((AbstractPolicy) categorizedPolicy).getJsonPath();
			if (PatternUtil.isNotNull(digit) && PatternUtil.isNotNull(jsonPath)) {
				// anonymize in transit policy needs a modifier!
				Parameter anonymizeMethodP = new Parameter(ParameterType.NUMBER, LeftOperand.DIGIT.getMydataLeftOperand(), digit);
				Parameter[] params = {anonymizeMethodP};
				modify = new Modify(EventParameter.TARGET.getEventParameter(), categorizedPolicy.getDutyAction(), jsonPath, params);

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
			if(null != paymentPipBoolean)
			{
				pipBooleans = (PIPBoolean[])addElement(pipBooleans, paymentPipBoolean);
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
			if(null != locationPipBoolean)
			{
				pipBooleans = (PIPBoolean[])addElement(pipBooleans, locationPipBoolean);
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
			List<DateTime> dateTimes = new ArrayList<>();
			String start = ((AbstractPolicy) categorizedPolicy).getStartTime();
			DateTime startTime = null;
			if (PatternUtil.isNotNull(start))
			{
				startTime = new DateTime(IntervalCondition.GT, start);
				dateTimes.add(startTime);
			}

			String end = ((AbstractPolicy) categorizedPolicy).getEndTime();
			DateTime endTime = null;
			if (PatternUtil.isNotNull(end))
			{
				endTime = new DateTime(IntervalCondition.LT, end);
				dateTimes.add(endTime);
			}

			DateTime[] dateTimesArray = new DateTime[dateTimes.size()];
			mydataPolicy.setDateTimes(dateTimes.toArray(dateTimesArray));
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

	private static String createCron(String y, String m, String d, String th, String tm, String ts)
	{
		String cron = ts + " " + tm + " " + th + " " + d + " " + m + " ? " + y ;
		return cron;
	}
}
