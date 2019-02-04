package de.fraunhofer.iese.ids.odrl.pap.model.MydataModel;

import de.fraunhofer.iese.ids.odrl.pap.model.IntervalCondition;
import lombok.Data;

import javax.xml.bind.DatatypeConverter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

@Data
public class DateTime {

    IntervalCondition is;
    String dateTime;

    public DateTime() {
    }

    public DateTime(IntervalCondition interval, String dateTime) {
        this.is = interval;
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return  "          <dateTime is='"+ is.getMydataInterval() +"' value='"+ getMydataDateTime() +"'/> \r\n";
    }


    private String getMydataDateTime() {
        Calendar cal = DatatypeConverter.parseDateTime(dateTime);
        cal.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
        return timestamp.toString();
    }

}
