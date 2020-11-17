package de.fraunhofer.iese.ids.odrl.pap.util;


import de.fraunhofer.iese.ids.odrl.policy.library.model.Condition;
import de.fraunhofer.iese.ids.odrl.policy.library.model.OdrlPolicy;
import de.fraunhofer.iese.ids.odrl.policy.library.model.RightOperand;
import de.fraunhofer.iese.ids.odrl.policy.library.model.RightOperandEntity;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.EntityType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.LeftOperand;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.RuleType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.TimeUnit;

public class OdrlCreator {
	
	public static String createODRL(OdrlPolicy policy){

		OdrlPolicy updatedPolicy = updateRightOperandValue(policy);
		return updatedPolicy.toString();
	}

	private static OdrlPolicy updateRightOperandValue(OdrlPolicy odrlPolicy)
	{
		if(odrlPolicy.getRules().get(0).getType().equals(RuleType.OBLIGATION))
		{
			if(null != odrlPolicy.getRules().get(0).getAction().getRefinements()) {
				for (Condition refinement : odrlPolicy.getRules().get(0).getAction().getRefinements()) {
					updateTimeUnitValue(refinement, LeftOperand.DELAY);
				}
			}
		}

		if(odrlPolicy.getRules().get(0).getType().equals(RuleType.PERMISSION))
		{
			if(null != odrlPolicy.getRules().get(0).getConstraints()) {
				for (Condition constraint : odrlPolicy.getRules().get(0).getConstraints()) {
					updateTimeUnitValue(constraint, LeftOperand.ELAPSED_TIME);
				}
			}
		}



		return odrlPolicy;
	}

	private static void updateTimeUnitValue(Condition refinement, LeftOperand delayperiod) {
		if (refinement.getLeftOperand().equals(delayperiod)) {
			if (null != refinement.getRightOperand().getEntities()) {
				for (RightOperandEntity entity : refinement.getRightOperand().getEntities()) {
					if (entity.getEntityType().equals(EntityType.HASDURATION)) {
						String value = entity.getValue();
						if (value != null && !value.isEmpty()) {
							String timeUnit = "";
							String xsdPrefix = "";

							switch (entity.getTimeUnit()) {
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
							entity.setValue("P" + xsdPrefix + value + timeUnit);
						}
					}
				}

			}
		}
	}

}
