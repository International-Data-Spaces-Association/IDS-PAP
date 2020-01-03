package de.fraunhofer.iese.ids.odrl.pap.model.MydataModel;

import de.fraunhofer.iese.ids.odrl.pap.model.Action;
import de.fraunhofer.iese.ids.odrl.pap.model.TimeUnit;
import lombok.Data;

@Data
public class Timer {
    TimeUnit timeUnit;
    String cron;
    String tid;
    String solution;
    Action action;
    Parameter parameter;

    public Timer(TimeUnit timeUnit, String exactCron, String tid, String solution, Action action, Parameter parameter) {
        this.timeUnit = timeUnit;
        this.cron = exactCron;
        this.tid = tid;
        this.solution = solution;
        this.action = action;
        this.parameter = parameter;
    }

    public Timer(String tid) {
        this.tid = tid;
    }


    @Override
    public String toString() {
        return  "  <timer cron='"+ getCron() +"' id='urn:timer:"+ solution +":"+ tid +"'>  \r\n" +
                "    <event action='urn:action:"+ solution +":"+ action.name().toLowerCase() +"'>  \r\n" +
                getParameter() +
                "    </event>  \r\n" +
                "  </timer> \r\n" ;
    }

    private String getParameter() {
        if (null != parameter)
        {
            return "      "+ parameter.toString() +"  \r\n";
        }
        return "";
    }

    private String getCron(){
        if(null != timeUnit)
        {
            return timeUnit.getMydataCron();
        }else
        {
            return cron;
        }
    }
}
