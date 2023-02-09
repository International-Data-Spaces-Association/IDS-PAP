package de.fraunhofer.iese.ids.odrl.pap.test;

import de.fraunhofer.iese.ids.odrl.pap.OdrlPapApplication;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 
 * This class is used to check the integrity of the pap backend.
 *
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest(classes =  OdrlPapApplication.class)
public class RecievedOdrlPolicyTest extends SuperTest {
    private String basePath = "src/test/resources/responses/ids/";

	public RecievedOdrlPolicyTest() {
		super("ODRL");
		// TODO Auto-generated constructor stub
	}

// ##################### New Tests #####################
	// Complex Policy
	@Test
	public void ComplexPolicyShort() throws Exception {
		String expectedResponseFilePath = basePath + "ComplexPolicyShort.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		check(json, url, expectedResponseFilePath);

	}
	
	// Counter
	@Test
	public void CountAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "CountAgreement.json";
		String url = baseUrl + "/policy/CountAccessPolicyForm";
		
		jsonObject.put("counter", "10");
		check(jsonObject, url, expectedResponseFilePath);
	}
	
	// Delete Data After
	@Test
	public void DeleteDataAfterAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "DeleteDataAfterAgreement"+".json";
		String url = baseUrl + "/policy/deletePolicyAfterUsagePeriod";
		
		check(jsonObject, url, expectedResponseFilePath);
	}
	
	//TODO Missing JSON
	@Test
	public void DeleteDataAfterExactTimeAndDateAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "DeleteDataAfterExactTimeAndDateAgreement"+".json";
		String url = baseUrl + "/policy/deletePolicyAfterUsage";
		
		jsonObject.put("postduties_timeAndDate", "2021-12-16T11:10");
		check(jsonObject, url, expectedResponseFilePath);
	}
	
	//TODO Missing JSON
	@Test
	public void DeleteDataAfterSpecificTimeDurationAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "DeleteDataAfterSpecificTimeDurationAgreement"+".json";
		String url = baseUrl + "/policy/deletePolicyAfterUsage";
		JSONObject json = createRequestHeader(emptyPolicy());
		jsonObject.put("postduties_durationDay", "1");
		jsonObject.put("postduties_durationHour", "10");
		jsonObject.put("postduties_durationMonth", "11");
		jsonObject.put("postduties_durationYear", "2021");
		check(jsonObject, url, expectedResponseFilePath);
	}
	
	// Anonymize in Rest
	
	@Test
	public void AnonymizeInRestAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "AnonymizeInRestAgreement"+".json";
		String url = baseUrl + "/policy/AnonymizeInRestPolicyForm";
		
		check(jsonObject, url, expectedResponseFilePath);
	}
	
	// Anonymize in Transit
	@Test
	public void AnonymizeInTransitReplaceAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "AnonymizeInTransitReplaceAgreement"+".json";
		String url = baseUrl + "/policy/AnonymizeInTransitPolicyForm";
		
		jsonObject.put("preduties_modifier", "idsc:REPLACE");
		jsonObject.put("preduties_fieldToChange", "FieldToChange");
		jsonObject.put("preduties_valueToChange", "ValueToChange");
		check(jsonObject, url, expectedResponseFilePath);
	}
	
	@Test
	public void AnonymizeInTransitDropAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "AnonymizeInTransitDropAgreement"+".json";
		String url = baseUrl + "/policy/AnonymizeInTransitPolicyForm";
		
		jsonObject.put("preduties_modifier", "idsc:DROP");
		jsonObject.put("preduties_fieldToChange", "FieldToChange");
		check(jsonObject, url, expectedResponseFilePath);
	}
	
	// Log Access
	@Test
	public void LogAccessOnDenyAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "LogAccessOnDenyAgreement"+".json";
		String url = baseUrl + "/policy/LogAccessPolicyForm";
		
		jsonObject.put("postduties_logLevel", "idsc:ON_DENY");
		jsonObject.put("postduties_systemDevice", "http://SystemDevice");
		check(jsonObject, url, expectedResponseFilePath);
	}
	
	@Test
	public void LogAccessOnDutyExercisedAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "LogAccessOnDutyExercisedAgreement"+".json";
		String url = baseUrl + "/policy/LogAccessPolicyForm";
		
		jsonObject.put("postduties_logLevel", "idsc:ON_DUTY_EXERCISED");
		jsonObject.put("postduties_systemDevice", "http://SystemDevice");
		check(jsonObject, url, expectedResponseFilePath);
	}

	// Inform Party
	@Test
	public void InformPartyOnDenyAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "LogAccessOnDenyOnDenyAgreement"+".json";
		String url = baseUrl + "/policy/InformPolicyForm";
		
		jsonObject.put("postduties_notificationLevel", "idsc:ON_DENY");
		jsonObject.put("postduties_informedParty", "http://SystemDevice");
		writeFile(jsonObject, url, expectedResponseFilePath);
		check(jsonObject, url, expectedResponseFilePath);
	}
	
}
