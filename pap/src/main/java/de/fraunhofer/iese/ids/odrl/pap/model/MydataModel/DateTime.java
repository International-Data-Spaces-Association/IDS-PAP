package de.fraunhofer.iese.ids.odrl.pap.model.MydataModel;

import de.fraunhofer.iese.ids.odrl.pap.model.IntervalCondition;
import lombok.Data;

import javax.xml.bind.DatatypeConverter;
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
        if(this.is.equals(IntervalCondition.GT))
        {
            return "          <or> \r\n" +
                    "            <and> \r\n" +
                    "              <date is='exactly' value='"+ getDate(getMydataDateTime()) +"'/> \r\n" +
                    "              <time is='"+ is.getMydataInterval() +"' value='"+ getTime(getMydataDateTime()) +"'/> \r\n" +
                    "            </and> \r\n" +
                    "            <date is='"+ is.getMydataInterval() +"' value='"+ getDate(getMydataDateTime()) +"'/> \r\n" +
                    "          </or> \r\n";
        }else if(this.is.equals(IntervalCondition.LT))
        {
            return "          <or> \r\n" +
                    "            <date is='"+ is.getMydataInterval() +"' value='"+ getDate(getMydataDateTime()) +"'/> \r\n" +
                    "            <and> \r\n" +
                    "              <date is='exactly' value='"+ getDate(getMydataDateTime()) +"'/> \r\n" +
                    "              <time is='"+ is.getMydataInterval() +"' value='"+ getTime(getMydataDateTime()) +"'/> \r\n" +
                    "            </and> \r\n" +
                    "          </or> \r\n";
        }
        return "";
        // We may implement dateTime function for MYDATA in near future
        //return  "          <dateTime is='"+ is.getMydataInterval() +"' value='"+ getMydataDateTime() +"'/> \r\n";
    }


    private String getMydataDateTime() {
        Calendar cal = DatatypeConverter.parseDateTime(dateTime);
        cal.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
        return timestamp.toString();
    }

    private String getDate(String dateTime)
    {
        String[] parts = dateTime.split(" ");
        return parts[0];
    }

    private String getTime(String dateTime)
    {
        String[] parts = dateTime.split(" ");
        return parts[1];
    }
}
