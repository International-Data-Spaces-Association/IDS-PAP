package de.fraunhofer.iese.ids.odrl.pap.model;
import java.util.List;
import lombok.Getter;

@Getter
public class RecievedOdrlPolicy {
	private int id;
	private boolean is_template;
	private String language;
	private String comment;
	private String name;
	private String originQuery;
	
	private String policyType;
	private String target;
	private String provider;
	private String consumer;
	private List<String> location_input;
	private String location_op;
	private List<String> application_input;
	private String application_op;
	private List<String> connector_input;
	private String connector_op;
	private List<String> role_input;
	private String role_op;
	private List<String> purpose_input;
	private String purpose_op;
	private List<String> event_input;
	private String event_op;
	private List<String> state_input;
	private String state_op;
	private List<String> securityLevel_input;
	private String securityLevel_op;
	
	private String preduties_anomInRest;
	private String preduties_modifier;
	private String preduties_valueToChange;
	private String preduties_fieldToChange;
	
	private String postduties_systemDevice;
	private String postduties_logLevel;
	private String postduties_durationYear;
	private String postduties_durationMonth;
	private String postduties_durationDay;
	private String postduties_durationHour;
	private String postduties_timeAndDate;
	private String postduties_notificationLevel;
	private String postduties_informedParty;

	private String system;
	private String interval;
	private String payment;
	private String price;
	private String counter;
	private String encoding;
	private String policy;
	private String time;
	private String timeUnit;
	private String timeAndDate;
	private String restrictStartTime;
	private String restrictEndTime;
	private String restrictStartTimeInterval;
	private String restrictEndTimeInterval;
	private String specifyBeginTime;
	private String durationHour;
	private String durationDay;
	private String durationMonth;
	private String durationYear;
	private String artifactState;
	
	public void setIsTemplate(boolean b) {
		is_template = b;
	}
}
