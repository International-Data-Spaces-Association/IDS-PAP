package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.*;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.RuleType;

public interface IProhibitionRuleUseAction {

	public RuleType getType();
	public void setType(RuleType type);

	public IUseAction getUseAction();
	public void setUseAction(IUseAction useAction);

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

	public JsonPathCondition getJsonPathConstraint();
	public void setJsonPathConstraint(JsonPathCondition jsonPathConstraint);

	public EncodingCondition getEncodingConstraint();
	public void setEncodingConstraint(EncodingCondition EncodingConstraint);

	public DelayPeriodCondition getDelayPeriodConstraint();
	public void setDelayPeriodConstraint(DelayPeriodCondition DelayPeriodConstraint);

	public DateTimeCondition getDateTimeConstraint();
	public void setDateTimeConstraint(DateTimeCondition dateTimeConstraint);

	public AbsoluteSpatialPositionCondition getAbsoluteSpatialPositionConstraint();
	public void setAbsoluteSpatialPositionConstraint(AbsoluteSpatialPositionCondition absoluteSpatialPositionConstraint);

	public TimeIntervalCondition getTimeIntervalConstraint();
	public void setTimeIntervalConstraint(TimeIntervalCondition timeIntervalConstraint);
}
