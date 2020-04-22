package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.*;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.Party;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.RuleType;

import java.net.URI;
import java.net.URL;

public interface IPermissionRuleReadAction {

	public RuleType getType();
	public void setType(RuleType type);

	public IReadAction getReadAction();
	public void setReadAction(IReadAction readAction);

	public IDeleteAction getDeleteDutyAction();
	public void setDeleteDutyAction(IDeleteAction deleteDutyAction);

	public IAnonymizeAction getAnonymizeDutyAction();
	public void setAnonymizeDutyAction(IAnonymizeAction anonymizeDutyAction);

	public ILogAction getLogDutyAction();
	public void setLogDutyAction(ILogAction logDutyAction);

	public IInformAction getInformDutyAction();
	public void setInformDutyAction(IInformAction informDutyAction);

	public EventCondition getEventDutyConstraint();
	public void setEventDutyConstraint(EventCondition eventDutyConstraint);

	public Party getInformedParty();
	public void setInformedParty(Party informedParty);

	public EventCondition getEventConstraint();
	public void setEventConstraint(EventCondition eventConstraint);

	public PurposeCondition getPurposeConstraint();
	public void setPurposeConstraint(PurposeCondition purposeConstraint);

	public SystemCondition getSystemConstraint();
	public void setSystemConstraint(SystemCondition systemConstraint);

	public SystemDeviceCondition getSystemDeviceConstraint();
	public void setSystemDeviceConstraint(SystemDeviceCondition systemDeviceConstraint);

	public RecipientCondition getRecipientConstraint();
	public void setRecipientConstraint(RecipientCondition recipientConstraint);

	public PaymentCondition getPaymentConstraint();
	public void setPaymentConstraint(PaymentCondition paymentConstraint);

	public JsonPathCondition getJsonPathConstraint();
	public void setJsonPathConstraint(JsonPathCondition jsonPathConstraint);

	public EncodingCondition getEncodingConstraint();
	public void setEncodingConstraint(EncodingCondition EncodingConstraint);

	public DelayPeriodCondition getDelayPeriodConstraint();
	public void setDelayPeriodConstraint(DelayPeriodCondition DelayPeriodConstraint);

	public DateTimeCondition getDateTimeConstraint();
	public void setDateTimeConstraint(DateTimeCondition dateTimeConstraint);

	public CountCondition getCountConstraint();
	public void setCountConstraint(CountCondition countConstraint);

	public AbsoluteSpatialPositionCondition getAbsoluteSpatialPositionConstraint();
	public void setAbsoluteSpatialPositionConstraint(AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint);

	public TimeIntervalCondition getTimeIntervalConstraint();
	public void setTimeIntervalConstraint(TimeIntervalCondition timeIntervalConstraint);

}
