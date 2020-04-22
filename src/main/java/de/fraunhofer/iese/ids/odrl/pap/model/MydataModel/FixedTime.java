package de.fraunhofer.iese.ids.odrl.pap.model.MydataModel;

public enum FixedTime {

    ALWAYS("always"),

    THIS_HOUR("thisHour"),

    THIS_WEEK("thisWeek"),

    TODAY("today"),

    THIS_MONTH("thisMonth");

    private final String fixedTime;

    FixedTime(String a) {
        fixedTime = a;
    }

    public String getFixedTime() {
        return fixedTime;
    }
}

