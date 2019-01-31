/**
 * 
 */
package de.fraunhofer.iese.ids.odrl.pap.model;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
public enum IntervalCondition {
	BEFORE("before","lt"),

	AFTER("after", "eq"),

	EXACTLY("exactly", "gt");

	private final String mydataInterval;
	private final String odrlIntervalOperator;

	IntervalCondition(String op1, String op2) {
		mydataInterval = op1;
		odrlIntervalOperator = op2;
	}

	public String getMydataInterval() {
		return mydataInterval;
	}

	public String getOdrlIntervalOperator() {
		return odrlIntervalOperator;
	}
}
