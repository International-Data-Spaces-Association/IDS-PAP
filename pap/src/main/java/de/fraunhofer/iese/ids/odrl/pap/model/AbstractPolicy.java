/**
 * 
 */
package de.fraunhofer.iese.ids.odrl.pap.model;

import java.net.URL;

import lombok.Data;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
@Data
public abstract class AbstractPolicy implements CategorizedPolicy{
	PolicyType policyType;
	URL dataUrl;
	String assigner;
	String assignee;

	public PolicyType getPolicyType() {
		return policyType;
	}

	public String getAssignee() {
		return assignee;
	}

	public URL getDataUrl() {
		return dataUrl;
	}

	public String getAssigner() {
		return assigner;
	}
}
