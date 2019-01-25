package de.fraunhofer.iese.ids.odrl.pap.model;

import java.net.URL;

public interface CategorizedPolicy {
	
	
	public PolicyType getPolicyType();
	public void setPolicyType(PolicyType policyType);
	
	public URL getDataUrl();
	public void setDataUrl(URL dataUrl);

	public String getAssignee();
	public void setAssignee(String assignee);
	
	public String getAssigner();
	public void setAssigner(String assigner);
}
