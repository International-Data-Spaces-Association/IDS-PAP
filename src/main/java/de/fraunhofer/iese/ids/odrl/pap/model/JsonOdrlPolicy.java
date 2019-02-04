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
public class JsonOdrlPolicy {
	String jsonString;


	public String getJsonString() {
		return jsonString;
	}
}


