package de.fraunhofer.iese.ids.odrl.pap.util;

public class PapUtil {
    public static boolean isNullOrEmpty(String value) {
        return (null == value || value.isEmpty());
    }

    public static boolean isNotNullOrEmpty(String value) {
        return !isNullOrEmpty(value);
    }
}
