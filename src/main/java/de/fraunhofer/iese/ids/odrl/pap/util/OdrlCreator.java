package de.fraunhofer.iese.ids.odrl.pap.util;


import de.fraunhofer.iese.ids.odrl.policy.library.model.Condition;
import de.fraunhofer.iese.ids.odrl.policy.library.model.OdrlPolicy;
import de.fraunhofer.iese.ids.odrl.policy.library.model.RightOperand;
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
					if (refinement.getLeftOperand().equals(LeftOperand.DELAYPERIOD)) {
						if (null != refinement.getRightOperand().getValue()) {
							String value = refinement.getRightOperand().getValue();
							if(value != "")
							{
								RightOperand updatedRightOperand = refinement.getRightOperand();
								String timeUnit = "";
								String xsdPrefix = "";

								switch(refinement.getTimeUnit()) {
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
								refinement.setRightOperand(updatedRightOperand);

							}
						}
					}
				}
			}
		}

		if(odrlPolicy.getRules().get(0).getType().equals(RuleType.PERMISSION))
		{
			if(null != odrlPolicy.getRules().get(0).getConstraints()) {
				for (Condition constraint : odrlPolicy.getRules().get(0).getConstraints()) {
					if (constraint.getLeftOperand().equals(LeftOperand.ELAPSEDTIME)) {
						if (null != constraint.getRightOperand().getValue()) {
							String value = constraint.getRightOperand().getValue();
							if (value != "") {
								RightOperand updatedRightOperand = constraint.getRightOperand();
								String timeUnit = "";
								String xsdPrefix = "";

								switch ((constraint).getTimeUnit()) {
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
								constraint.setRightOperand(updatedRightOperand);
							}
						}
					}
				}
			}
		}



		return odrlPolicy;
	}

}
