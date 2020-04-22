package de.fraunhofer.iese.ids.odrl.pap.util;

import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.*;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.OdrlCondition.DelayPeriodCondition;
import de.fraunhofer.iese.ids.odrl.pap.model.TimeUnit;

public class OdrlCreator {
	
	public static String createODRL(OdrlPolicy policy){

		OdrlPolicy updatedPolicy = updateRightOperandValue(policy);
		return updatedPolicy.toString();
	}

	private static OdrlPolicy updateRightOperandValue(OdrlPolicy odrlPolicy)
	{
		if(null != odrlPolicy.getObligationRuleDeleteAction())
		{
			if(null != odrlPolicy.getObligationRuleDeleteAction().getDeleteAction().getDelayPeriodRefinement())
			{
				if(null != odrlPolicy.getObligationRuleDeleteAction().getDeleteAction().getDelayPeriodRefinement().getRightOperand().getValue())
				{
					String value = odrlPolicy.getObligationRuleDeleteAction().getDeleteAction().getDelayPeriodRefinement().getRightOperand().getValue();
					if(value != "")
					{
						DelayPeriodCondition updatedDelayPeriodRefinement = odrlPolicy.getObligationRuleDeleteAction().getDeleteAction().getDelayPeriodRefinement();
						RightOperand updatedRightOperand = odrlPolicy.getObligationRuleDeleteAction().getDeleteAction().getDelayPeriodRefinement().getRightOperand();
						String timeUnit = "";
						String xsdPrefix = "";

						switch(odrlPolicy.getObligationRuleDeleteAction().getDeleteAction().getDelayPeriodRefinement().getTimeUnit()) {
							case HOURS:
								timeUnit = TimeUnit.HOURS.getOdrlXsdDuration();
								xsdPrefix = "T";
								break;

							case DAYS:
								timeUnit = TimeUnit.DAYS.getOdrlXsdDuration();
								break;

							case MONTHS:
								timeUnit = TimeUnit.MONTHS.getOdrlXsdDuration();
								break;

							case YEARS:
								timeUnit = TimeUnit.YEARS.getOdrlXsdDuration();
								break;

						}
						updatedRightOperand.setValue("P" + xsdPrefix + value + timeUnit);
						updatedDelayPeriodRefinement.setRightOperand(updatedRightOperand);
						odrlPolicy.getObligationRuleDeleteAction().getDeleteAction().setDelayPeriodRefinement(updatedDelayPeriodRefinement);
					}
				}
			}
		}
		return odrlPolicy;
	}
}
