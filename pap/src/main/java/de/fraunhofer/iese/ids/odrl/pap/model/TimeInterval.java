/**
 * 
 */
package de.fraunhofer.iese.ids.odrl.pap.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
public class TimeInterval {
	ZonedDateTime start;
	ZonedDateTime end;
	
	public void setBefore(String endTime){
		start = ZonedDateTime.now();
		end = ZonedDateTime.parse(endTime, DateTimeFormatter.ISO_INSTANT);
	}
	public void setAfter(String startTime){
		start = ZonedDateTime.parse(startTime, DateTimeFormatter.ISO_INSTANT);
		end = ZonedDateTime.now().plus(1, ChronoUnit.CENTURIES);
	}
	
	public void setExactly(String startTime, String endTime){
		start = ZonedDateTime.parse(startTime, DateTimeFormatter.ISO_INSTANT);
		end = ZonedDateTime.parse(endTime, DateTimeFormatter.ISO_INSTANT);
	}
	
	public void setInterval(IntervalCondition intervalCondition, String startTime, String endTime) {
		if(IntervalCondition.BEFORE.equals(intervalCondition)) {
			setBefore(endTime);
		}
		else if(IntervalCondition.AFTER.equals(intervalCondition)) {
			setAfter(startTime);
		}
		else if(IntervalCondition.EXACTLY.equals(intervalCondition)){
			setExactly(startTime, endTime);
		}
	}
}
