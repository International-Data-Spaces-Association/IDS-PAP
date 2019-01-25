package de.fraunhofer.iese.ids.odrl.pattern;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import de.fraunhofer.iese.ids.odrl.pap.model.CategorizedPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.PolicyType;

public class PatternUtil {
	
public static CategorizedPolicy getCategorizedPolicy(@SuppressWarnings("rawtypes") Map map) {
	
	PolicyType policyType = PolicyType.valueOf(map.get("@type").toString());
	@SuppressWarnings("rawtypes")
	Map permissionMap = (Map) ((ArrayList) map.get("permission")).get(0);
	URL dataURL = null;
	try {
		dataURL = new URL(permissionMap.get("target").toString());
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	String assigner = permissionMap.get("assigner").toString();
	String assignee = permissionMap.get("assignee").toString();
	
	System.out.println(policyType.toString());
	if(null != dataURL) System.out.println(dataURL.toString());
	System.out.println(assigner);
	System.out.println(assignee);
	
	return null;
}
}
