package de.fraunhofer.iese.ids.odrl.pap.util;

import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.*;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.Party;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.RuleType;

public interface ITranslator {

	//PERMISSION
	String  translateComplexPolicyWithPermissionRuleUseAction(Object odrlPolicy);
	String  translateComplexPolicyWithPermissionRuleReadAction(Object odrlPolicy);
	String  translateComplexPolicyWithPermissionRuleDistributeAction(Object odrlPolicy);
	String  translateComplexPolicyWithPermissionRulePrintAction(Object odrlPolicy);

	String  translatePolicyWithPermissionRuleUseActionNoConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRuleReadActionNoConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRuleDistributeActionNoConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRulePrintActionNoConstraint(Object odrlPolicy);

	String  translatePolicyWithPermissionRuleUseActionEventConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRuleReadActionEventConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRuleDistributeActionEventConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRulePrintActionEventConstraint(Object odrlPolicy);

	String  translatePolicyWithPermissionRuleUseActionPurposeConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRuleReadActionPurposeConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRuleDistributeActionPurposeConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRulePrintActionPurposeConstraint(Object odrlPolicy);

	String  translatePolicyWithPermissionRuleUseActionSystemConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRuleReadActionSystemConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRuleDistributeActionSystemConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRulePrintActionSystemConstraint(Object odrlPolicy);

	String  translatePolicyWithPermissionRuleUseActionPaymentConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRuleReadActionPaymentConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRuleDistributeActionPaymentConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRulePrintActionPaymentConstraint(Object odrlPolicy);

	String  translatePolicyWithPermissionRuleUseActionAndCountConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRuleReadActionAndCountConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRuleDistributeActionAndCountConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRulePrintActionAndCountConstraint(Object odrlPolicy);

	String  translatePolicyWithPermissionRuleDistributeActionAndEncodingConstraint(Object odrlPolicy);

	String  translatePolicyWithPermissionRuleUseActionTimeIntervalConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRuleReadActionTimeIntervalConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRuleDistributeActionTimeIntervalConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRulePrintActionTimeIntervalConstraint(Object odrlPolicy);

	String  translatePolicyWithPermissionRuleUseActionAbsoluteSpatialPositionConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRuleReadActionAbsoluteSpatialPositionConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRuleDistributeActionAbsoluteSpatialPositionConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRulePrintActionAbsoluteSpatialPositionConstraint(Object odrlPolicy);

	String  translatePolicyWithPermissionRuleUseActionDeleteDutyActionAndEventDutyConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRuleReadActionDeleteDutyActionAndEventDutyConstraint(Object odrlPolicy);

	String  translatePolicyWithPermissionRuleUseActionAnonymizeDutyActionAndEventDutyConstraint(Object odrlPolicy);
	String  translatePolicyWithPermissionRuleReadActionAnonymizeDutyActionAndEventDutyConstraint(Object odrlPolicy);

	String  translatePolicyWithPermissionRuleUseActionAndInformDutyAction(Object odrlPolicy);
	String  translatePolicyWithPermissionRuleReadActionAndInformDutyAction(Object odrlPolicy);

	String  translatePolicyWithPermissionRuleUseActionAndLogDutyAction(Object odrlPolicy);
	String  translatePolicyWithPermissionRuleReadActionAndLogDutyAction(Object odrlPolicy);

	String  translatePolicyWithPermissionRuleDistributeActionAndNextPolicyDutyAction(Object odrlPolicy);

	//PROHIBITION
	String  translateComplexPolicyWithProhibitionRuleUseAction(Object odrlPolicy);
	String  translateComplexPolicyWithProhibitionRuleReadAction(Object odrlPolicy);
	String  translateComplexPolicyWithProhibitionRuleDistributeAction(Object odrlPolicy);
	String  translateComplexPolicyWithProhibitionRulePrintAction(Object odrlPolicy);

	String  translatePolicyWithProhibitionRuleUseActionNoConstraint(Object odrlPolicy);
	String  translatePolicyWithProhibitionRuleReadActionNoConstraint(Object odrlPolicy);
	String  translatePolicyWithProhibitionRuleDistributeActionNoConstraint(Object odrlPolicy);
	String  translatePolicyWithProhibitionRulePrintActionNoConstraint(Object odrlPolicy);

	String  translatePolicyWithProhibitionRuleUseActionEventConstraint(Object odrlPolicy);
	String  translatePolicyWithProhibitionRuleReadActionEventConstraint(Object odrlPolicy);
	String  translatePolicyWithProhibitionRuleDistributeActionEventConstraint(Object odrlPolicy);
	String  translatePolicyWithProhibitionRulePrintActionEventConstraint(Object odrlPolicy);

	String  translatePolicyWithProhibitionRuleUseActionPurposeConstraint(Object odrlPolicy);
	String  translatePolicyWithProhibitionRuleReadActionPurposeConstraint(Object odrlPolicy);
	String  translatePolicyWithProhibitionRuleDistributeActionPurposeConstraint(Object odrlPolicy);
	String  translatePolicyWithProhibitionRulePrintActionPurposeConstraint(Object odrlPolicy);

	String  translatePolicyWithProhibitionRuleUseActionSystemConstraint(Object odrlPolicy);
	String  translatePolicyWithProhibitionRuleReadActionSystemConstraint(Object odrlPolicy);
	String  translatePolicyWithProhibitionRuleDistributeActionSystemConstraint(Object odrlPolicy);
	String  translatePolicyWithProhibitionRulePrintActionSystemConstraint(Object odrlPolicy);

	String  translatePolicyWithProhibitionRuleUseActionTimeIntervalConstraint(Object odrlPolicy);
	String  translatePolicyWithProhibitionRuleReadActionTimeIntervalConstraint(Object odrlPolicy);
	String  translatePolicyWithProhibitionRuleDistributeActionTimeIntervalConstraint(Object odrlPolicy);
	String  translatePolicyWithProhibitionRulePrintActionTimeIntervalConstraint(Object odrlPolicy);

	String  translatePolicyWithProhibitionRuleUseActionAbsoluteSpatialPositionConstraint(Object odrlPolicy);
	String  translatePolicyWithProhibitionRuleReadActionAbsoluteSpatialPositionConstraint(Object odrlPolicy);
	String  translatePolicyWithProhibitionRuleDistributeActionAbsoluteSpatialPositionConstraint(Object odrlPolicy);
	String  translatePolicyWithProhibitionRulePrintActionAbsoluteSpatialPositionConstraint(Object odrlPolicy);

	//OBLIGATION
	String  translatePolicyWithObligationRuleDeleteActionNoConstraint(Object odrlPolicy);
	String  translatePolicyWithObligationRuleAnonymizeActionNoConstraint(Object odrlPolicy);
	String  translatePolicyWithObligationRuleLogActionNoConstraint(Object odrlPolicy);
	String  translatePolicyWithObligationRuleInformActionNoConstraint(Object odrlPolicy);

	String  translatePolicyWithObligationRuleDeleteActionEventConstraint(Object odrlPolicy);
	String  translatePolicyWithObligationRuleAnonymizeActionEventConstraint(Object odrlPolicy);
	String  translatePolicyWithObligationRuleLogActionEventConstraint(Object odrlPolicy);
	String  translatePolicyWithObligationRuleInformActionEventConstraint(Object odrlPolicy);

	String  translatePolicyWithObligationRuleDeleteActionAndDelayPeriodRefinement(Object odrlPolicy);
	String  translatePolicyWithObligationRuleDeleteActionAndDateTimeRefinement(Object odrlPolicy);

}
