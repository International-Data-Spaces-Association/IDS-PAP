/**
 * 
 */
package de.fraunhofer.iese.ids.odrl.pap.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ReadDataIntervalPolicy extends AbstractPolicy {
	IntervalCondition intervalCondition;
	String startTime;
	String endTime;

	public TimeInterval getTimeInterval(){
		TimeInterval timeinterval = new TimeInterval();
		timeinterval.setInterval(intervalCondition, startTime, endTime);
		return timeinterval;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}
}
