package de.fraunhofer.iese.ids.odrl.pap.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class DeleteAtferPolicy extends AbstractPolicy {
	Duration duration;

    public Duration getDuration() {
        return duration;
    }

//	public void setValue(int value) {
//		duration.setValue(value);
//	}
//	
//	public void setTimeUnit(TimeUnit timeUnit) {
//		duration.setTimeUnit(timeUnit);
//	}
}
