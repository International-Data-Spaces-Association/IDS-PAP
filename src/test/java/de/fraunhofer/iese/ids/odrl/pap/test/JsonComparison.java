package de.fraunhofer.iese.ids.odrl.pap.test;

import java.lang.reflect.Type;
import java.util.Map;

import com.github.jsonldjava.shaded.com.google.common.collect.MapDifference;
import com.github.jsonldjava.shaded.com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonComparison {
	public static String compareJson(String output, String target) {
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, Object>>(){}.getType();
		String errorMessage = "";
		Map<String, Object> outputMap = gson.fromJson(output, type);
		Map<String, Object> targetMap = gson.fromJson(target, type);
		Map<String, Object> outputFlatMap = FlatMapUtil.flatten(outputMap);
		Map<String, Object> targetFlatMap = FlatMapUtil.flatten(targetMap);
		
		MapDifference<String, Object> difference = Maps.difference(outputFlatMap, targetFlatMap);
		
		if (difference.entriesOnlyOnLeft().size() > 0)
			errorMessage += "\nEntries only on output:\n";
		for (String key: difference.entriesOnlyOnLeft().keySet()) {
			errorMessage += ("\t\t" + key + ": " + difference.entriesOnlyOnLeft().get(key) + "\n");
		}

		if (difference.entriesOnlyOnRight().size() > 0)
			errorMessage += "\n\nEntries only on expected output:\n";
		for (String key: difference.entriesOnlyOnRight().keySet()) {
			errorMessage += ("\t\t" + key + ": " + difference.entriesOnlyOnLeft().get(key) + "\n");
		}
		
		if (difference.entriesDiffering().size() > 0)
			errorMessage += "\n\nEntries differing:\n";
		for (String key: difference.entriesDiffering().keySet()) {
			errorMessage += ("\t\t" + key + ": " + difference.entriesOnlyOnLeft().get(key) + "\n");
		}
		//difference.entriesOnlyOnLeft().forEach((key, value) -> System.out.println(key + ": " + value));

		//difference.entriesOnlyOnRight().forEach((key, value) -> System.out.println(key + ": " + value));

		//difference.entriesDiffering().forEach((key, value) -> System.out.println(key + ": " + value));

		//System.out.println("\n\nEntries in common\n--------------------------");
		//difference.entriesInCommon().forEach((key, value) -> System.out.println(key + ": " + value));
		return errorMessage;
	}
}
