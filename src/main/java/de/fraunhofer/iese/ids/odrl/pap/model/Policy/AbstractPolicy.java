/**
 * 
 */
package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import java.net.URL;

import de.fraunhofer.iese.ids.odrl.pap.model.*;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.Condition;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AbstractPolicy implements CategorizedPolicy{
	RuleType ruleType;
	Action action;
	Action dutyAction;
	Action abstractAction;
	URL policyUrl;
	URL nextPolicyUrl;
	PolicyType policyType;
	Condition condition;
	URL dataUrl;
	String assigner;
	String assignee;
	boolean providerSide;

	String jsonPath;
	String digit;
	String count;
	Duration duration;
	String encoding;
	String informedParty;
	String recipient;
	IntervalCondition intervalCondition;
	String startTime;
	String endTime;
	String dateTime;
	String event;
	String purpose;
	String system;
	String location;
	String systemDevice;
	Payment payment;


	public AbstractPolicy(){
		this.assigner = "http://example.com/ids-party:my-party";
	}

	public RuleType getRuleType() {
		return ruleType;
	}

	public Action getAction() {
		return action;
	}

	public Action getDutyAction() {
		return dutyAction;
	}
	public void setDutyAction(Action dutyAction)
	{
		this.dutyAction = dutyAction;
		this.setAbstractAction(Action.valueOf(dutyAction.getAbstractIdsAction()));
	}

	public Action getAbstractAction() {
		return abstractAction;
	}

	public URL getPolicyUrl() {
		return policyUrl;
	}

	public URL getNextPolicyUrl() {
		return nextPolicyUrl;
	}

	public PolicyType getPolicyType() {
		return policyType;
	}

	public String getAssignee() {
		return assignee;
	}

	public URL getDataUrl() {
		return dataUrl;
	}

	public String getAssigner() {
		return assigner;
	}

	public boolean getProviderSide() {
		return providerSide;
	}

	public String getSystem() {
		return system;
	}

	public String getSystemDevice() {
		return systemDevice;
	}

	public String getLocation() {
		return location;
	}

	public String getPurpose() {
		return purpose;
	}

	public String getEvent() {
		return event;
	}

	public TimeInterval getTimeInterval(){
		TimeInterval timeinterval = new TimeInterval();
		//timeinterval.setAfter(startTime);
		//timeinterval.setBefore(endTime);
		//timeinterval.setInterval(intervalCondition, startTime, endTime);
		return timeinterval;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getDateTime() {
		return dateTime;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setInformPolicy(String informedParty){
		this.informedParty = informedParty;
	}

	public String getInformedParty() {
		return informedParty;
	}

	public String getEncoding() {
		return encoding;
	}

	public Duration getDuration() {
		return duration;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public String getCount() {
		return count;
	}

	//TODO: digit param does not belong to anonymize method but Round method!
	public String getJsonPath() {
		return jsonPath;
	}

	public String getDigit() {
		return digit;
	}
}
