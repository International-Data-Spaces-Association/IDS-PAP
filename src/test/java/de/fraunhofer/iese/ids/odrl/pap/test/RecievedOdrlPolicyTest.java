package de.fraunhofer.iese.ids.odrl.pap.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.fraunhofer.iese.ids.odrl.pap.controller.IDSPapRestController;
import de.fraunhofer.iese.ids.odrl.pap.controller.RecievedOdrlPolicy;

/**
 * 
 * This class is used to check the integrity of the pap backend.
 *
 */
class RecievedOdrlPolicyTest {
	//private String baseUrl = "http://localhost:9090";
	private String basePath = "src/test/java/de/fraunhofer/iese/ids/odrl/pap/test/responses/";
	JSONObject emptyPolicy() throws JSONException {
		JSONArray array = new JSONArray();
		array.put("");

		JSONObject json = new JSONObject();
		json.put("id", 0);
		json.put("policyType", "");
		json.put("target", "");
		json.put("provider", "");
		json.put("consumer", "");
		json.put("location_input", array);
		json.put("location_op", "");
		json.put("application_input", array);
		json.put("application_op", "");
		json.put("connector_input", array);
		json.put("connector_op", "");
		json.put("role_input", array);
		json.put("role_op", "");
		json.put("purpose_input", array);
		json.put("purpose_op", "");
		json.put("event_input", array);
		json.put("event_op", "");
		json.put("state_input", array);
		json.put("state_op", "");
		json.put("securityLevel_input", array);
		json.put("securityLevel_op", "");
		json.put("preduties_anomInRest", "");
		json.put("preduties_modifier", "");
		json.put("preduties_valueToChange", "");
		json.put("preduties_fieldToChange", "");
		json.put("postduties_logLevel", "");
		json.put("postduties_durationYear", "");
		json.put("postduties_durationMonth", "");
		json.put("postduties_durationDay", "");
		json.put("postduties_durationHour", "");
		json.put("postduties_timeAndDate", "");
		json.put("postduties_notificationLevel", "");
		json.put("postduties_informedParty", "");
		json.put("postduties_systemDevice", "");
		json.put("system", "");
		json.put("interval", "");
		json.put("payment", "");
		json.put("price", "");
		json.put("counter", "");
		json.put("encoding", "");
		json.put("policy", "");
		json.put("time", "");
		json.put("timeUnit", "");
		json.put("timeAndDate", "");
		json.put("durationHour", "");
		json.put("durationDay", "");
		json.put("durationMonth", "");
		json.put("durationYear", "");
		json.put("restrictTimeIntervalStart", "");
		json.put("restrictTimeIntervalEnd", "");
		json.put("restrictEndTime", "");
		json.put("specifyBeginTime", "");
		json.put("artifactState", "");
		return json;

	}
	/**
	 *  Creates an agreement header
	 * @param json that contains arguments
	 * @return json object with header
	 * @throws JSONException
	 */
	JSONObject createAgreementHeader(JSONObject json) throws JSONException {
		json.put("policyType", "Agreement");
		json.put("consumer", "http://example.com/ids/party/consumer-party/");
		json.put("provider", "My party");
		json.put("target", "http://target/");
		return json;
	}
	
	/**
	 *  Creates an offer header
	 * @param json that contains arguments
	 * @return json object with header
	 * @throws JSONException
	 */
	JSONObject createOfferHeader(JSONObject json) throws JSONException {
		json.put("policyType", "Offer");
		json.put("provider", "My party");
		json.put("target", "http://target/");
		return json;
	}
	
	/**
	 * Creates a request header
	 * @param json that contains arguments
	 * @return json object with header
	 * @throws JSONException
	 */
	JSONObject createRequestHeader(JSONObject json) throws JSONException {
		json.put("policyType", "Request");
		json.put("consumer", "http://example.com/ids/party/consumer-party/");
		json.put("target", "http://target/");
		return json;
	}

	/**
	 * Calls a rest end point with parameters specified in the JSON object.
	 * @param json that contains arguments
	 * @param methodName method name of rest end point
	 * @return response of rest end point
	 */
	String send(JSONObject json, String methodName) {
		ObjectMapper mapper = new ObjectMapper();
		IDSPapRestController controller = new IDSPapRestController();
		try {
			RecievedOdrlPolicy recievedOdrlPolicy = mapper.readValue(json.toString(), RecievedOdrlPolicy.class);
			
	        Method method = controller.getClass().getMethod(methodName, RecievedOdrlPolicy.class);
	        String response = (String) method.invoke(controller, recievedOdrlPolicy);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	/**
	 * All expected responses are saved as .txt files and this method can load one file
	 * @param path to file with expected response
	 * @return The specified file as string
	 */
	String readFile(String path) {
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}

	/**
	 * All expected responses are saved as .txt files and this method can save a response as a file
	 * @param out string to write in the file
	 * @param path of the file
	 */
	void writeFile(String out, String path) {
		try {
			new File(path).createNewFile();
			FileWriter writer = new FileWriter(path, false);
			writer.write(out);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param json with arguments to be sent
	 * @param url rest end point
	 * @param path of file which contains the expected response
	 */
	public void check(JSONObject json, String methodName, String path) {
		String response = send(json, methodName);
		//writeFile(response, path); // This line will override all expected responses!
		String expectedResponse = readFile(path);
		assertTrue(response.equals(expectedResponse));
	}
// ##################### Tests #####################
	
	// Complex Policy
	@Test
	void ComplexPolicyAgreement() throws JSONException {
		System.out.println("Hallo");
		String expectedResponseFilePath = basePath + "ComplexPolicyAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyApplicationSingleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyApplicationSingleAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		JSONArray application = new JSONArray();
		application.put("Application1");
		json.put("application_input", application);
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyApplicationMultipleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyApplicationMultipleAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		JSONArray application = new JSONArray();
		application.put("Application1");
		application.put("Application2");
		json.put("application_input", application);
		json.put("application_op", "IN");
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyConnectorSingleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyConnectorSingleAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		JSONArray connector = new JSONArray();
		connector.put("Connector1");
		json.put("connector_input", connector);
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyConnectornMultipleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyConnectornMultipleAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		JSONArray connector = new JSONArray();
		connector.put("Connector1");
		connector.put("Connector2");
		json.put("connector_input", connector);
		json.put("connector_op", "IN");
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyCounterAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyCounterAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("counter", "222");
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyDurationAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyDurationAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("durationDay", "21");
		json.put("durationHour", "10");
		json.put("durationMonth", "10");
		json.put("durationYear", "2021");
		json.put("specifyBeginTime", "2021-12-16T20:43");
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyRestrictEndTimeAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyRestrictEndTimeAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("restrictEndTime", "2021-12-16T20:43");
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicySecurityLevelSingleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicySecurityLevelSingleAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		JSONArray securityLevel = new JSONArray();
		securityLevel.put("idsc:TRUST_SECURITY_PROFILE");
		json.put("securityLevel_input", securityLevel);
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicySecurityLevelMultipleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicySecurityLevelMultipleAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		JSONArray securityLevel = new JSONArray();
		securityLevel.put("idsc:TRUST_SECURITY_PROFILE");
		securityLevel.put("idsc:TRUST_PLUS_SECURITY_PROFILE");
		json.put("securityLevel_input", securityLevel);
		json.put("securityLevel_op", "IN");
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyIntervalAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyIntervalAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("restrictTimeIntervalEnd", "2022-01-05T20:47");
		json.put("restrictTimeIntervalStart", "2021-12-16T20:47");
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyLocationSingleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyLocationSingleAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		JSONArray location = new JSONArray();
		location.put("location1");
		json.put("location_input", location);
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyLocationMultipleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyLocationMultipleAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		JSONArray location = new JSONArray();
		location.put("location1");
		location.put("location2");
		json.put("location_input", location);
		json.put("location_op", "IN");
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyPurposeSingleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyPurposeSingleAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		JSONArray purpose = new JSONArray();
		purpose.put("http://example.com/ids-purpose/Marketing");
		json.put("purpose_input", purpose);
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyPurposeMultipleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyPurposeMultipleAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		JSONArray purpose = new JSONArray();
		purpose.put("http://example.com/ids-purpose/Marketing");
		purpose.put("http://example.com/ids-purpose/Risk_Management");
		json.put("purpose_input", purpose);
		json.put("purpose_op", "IN");
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyRoleSingleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyRoleSingleAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		JSONArray role = new JSONArray();
		role.put("idsc:ADMIN");
		json.put("role_input", role);
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyRoleMultipleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyRoleMultipleAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		JSONArray role = new JSONArray();
		role.put("idsc:ADMIN");
		role.put("idsc:DEVELOPER");
		json.put("role_input", role);
		json.put("role_op", "IN");
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyStateSingleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyStateSingleAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		JSONArray state = new JSONArray();
		state.put("CONTRACTNOTTERMINATED");
		json.put("state_input", state);
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyStateMultipleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyStateMultipleAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		JSONArray state = new JSONArray();
		state.put("CONTRACTNOTTERMINATED");
		state.put("FIREWALLACTIVATED");
		json.put("state_input", state);
		json.put("state_op", "IN");
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyEventSingleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyEventSingleAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		JSONArray event = new JSONArray();
		event.put("event1");
		json.put("event_input", event);
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyEventMultipleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyEventMultipleAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		JSONArray event = new JSONArray();
		event.put("event1");
		event.put("event2");
		json.put("event_input", event);
		json.put("event_op", "IN");
		check(json, "complexPolicy", expectedResponseFilePath);
	}
	

	@Test
	void ComplexPolicyPaymentAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyPaymentAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("price", "10");
		json.put("payment", "http://dbpedia.org/page/Rent");
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyDistributeDataAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyDistributeDataAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("policy", "Policy1");
		json.put("artifactState", "idsc:ANONYMIZED");
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyAnonymizeinRestAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyAnonymizeinRestAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("preduties_anomInRest", "Active");
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyAnonymizeinTransitReplaceAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyAnonymizeinTransitReplaceAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("preduties_fieldToChange", "Field1");
		json.put("preduties_modifier", "idsc:REPLACE");
		json.put("preduties_valueToChange", "ValueToChange");
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyAnonymizeinTransitDeleteAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyAnonymizeinTransitDeleteAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("preduties_modifier", "idsc:DELETE");
		json.put("preduties_fieldToChange", "Field1");
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyLogDataUsageAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyLogDataUsageAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("postduties_logLevel", "idsc:ON_DUTY_EXERCISED");
		json.put("postduties_systemDevice", "SystemDevice1");
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyInformPartyAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyInformPartyAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("postduties_informedParty", "InformParty");
		json.put("postduties_notificationLevel", "idsc:ON_ALLOW");
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyDeleteDataAfterTimeDurationAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyDeleteDataAfterTimeDurationAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("postduties_durationDay", "1");
		json.put("postduties_durationHour", "2");
		json.put("postduties_durationMonth", "3");
		json.put("postduties_durationYear", "2021");
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyDeleteDataAfterSpecificTimeAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyDeleteDataAfterSpecificTimeAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("postduties_timeAndDate", "2021-12-16T21:08");
		check(json, "complexPolicy", expectedResponseFilePath);
	}

	@Test
	void ComplexPolicyWithAllArgumentsAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyWithAllArgumentsAgreement.txt";
		//String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		JSONArray application = new JSONArray();
		application.put("Application1");
		json.put("application_input", application);
	
		JSONArray connector = new JSONArray();
		connector.put("Connector1");
		json.put("connector_input", connector);
	
		json.put("counter", "222");

	
		json.put("durationDay", "21");
		json.put("durationHour", "10");
		json.put("durationMonth", "10");
		json.put("durationYear", "2021");
		json.put("specifyBeginTime", "2021-12-16T20:43");

	
		json.put("restrictEndTime", "2021-12-16T20:43");

		JSONArray securityLevel = new JSONArray();
		securityLevel.put("idsc:TRUST_SECURITY_PROFILE");
		json.put("securityLevel_input", securityLevel);

		json.put("restrictTimeIntervalEnd", "2022-01-05T20:47");
		json.put("restrictTimeIntervalStart", "2021-12-16T20:47");
	
		JSONArray location = new JSONArray();
		location.put("location1");
		json.put("location_input", location);
	
		JSONArray purpose = new JSONArray();
		purpose.put("http://example.com/ids-purpose/Marketing");
		json.put("purpose_input", purpose);
	
		JSONArray role = new JSONArray();
		role.put("idsc:ADMIN");
		json.put("role_input", role);

		JSONArray state = new JSONArray();
		state.put("CONTRACTNOTTERMINATED");
		json.put("state_input", state);
	
		JSONArray event = new JSONArray();
		event.put("event1");
		json.put("event_input", event);
	
		json.put("price", "10");
		json.put("payment", "http://dbpedia.org/page/Rent");
	
		json.put("policy", "Policy1");
		json.put("artifactState", "idsc:ANONYMIZED");
	
		json.put("preduties_anomInRest", "Active");
	
		json.put("preduties_fieldToChange", "Field1");
		json.put("preduties_modifier", "idsc:REPLACE");
		json.put("preduties_valueToChange", "ValueToChange");

	
		json.put("postduties_logLevel", "idsc:ON_DUTY_EXERCISED");
		json.put("postduties_systemDevice", "SystemDevice1");
	
		json.put("postduties_informedParty", "InformParty");
		json.put("postduties_notificationLevel", "idsc:ON_ALLOW");

		json.put("postduties_durationDay", "1");
		json.put("postduties_durationHour", "2");
		json.put("postduties_durationMonth", "3");
		json.put("postduties_durationYear", "2021");
	
		json.put("postduties_timeAndDate", "2021-12-16T21:08");	
		
		check(json, "complexPolicy", expectedResponseFilePath);
	}
	
	
	// Counter
	@Test
	void CountAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "CountAgreement.txt";
		//String url = baseUrl + "/policy/CountAccessPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("counter", "22");
		check(json, "countPolicy", expectedResponseFilePath);
	}
	
	// Delete Data After
	
	@Test
	void DeleteDataAfterAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "DeleteDataAfterAgreement"+".txt";
		//String url = baseUrl + "/policy/deletePolicyAfterUsagePeriod";
		JSONObject json = createAgreementHeader(emptyPolicy());
		check(json, "deletePolicyAfterUsagePeriod", expectedResponseFilePath);
	}
	
	@Test
	void DeleteDataAfterExactTimeAndDateAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "DeleteDataAfterExactTimeAndDateAgreement"+".txt";
		//String url = baseUrl + "/policy/deletePolicyAfterUsage";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("postduties_timeAndDate", "2021-12-16T11:10");
		check(json, "deletePolicyAfterUsage", expectedResponseFilePath);
	}
	
	@Test
	void DeleteDataAfterSpecificTimeDurationAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "DeleteDataAfterSpecificTimeDurationAgreement"+".txt";
		//String url = baseUrl + "/policy/deletePolicyAfterUsage";
		JSONObject json = createRequestHeader(emptyPolicy());
		json.put("postduties_durationDay", "1");
		json.put("postduties_durationHour", "10");
		json.put("postduties_durationMonth", "11");
		json.put("postduties_durationYear", "2021");
		check(json, "deletePolicyAfterUsage", expectedResponseFilePath);
	}
	
	
	// Anonymize in Rest
	
	@Test
	void AnonymizeInRestAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "AnonymizeInRestAgreement"+".txt";
		//String url = baseUrl + "/policy/AnonymizeInRestPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		check(json, "anonymizeInRestolicy", expectedResponseFilePath);
	}
	
	// Anonymize in Transit
	
	@Test
	void AnonymizeInTransitReplaceAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "AnonymizeInTransitReplaceAgreement"+".txt";
		//String url = baseUrl + "/policy/AnonymizeInTransitPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("preduties_modifier", "idsc:REPLACE");
		json.put("preduties_fieldToChange", "FieldToChange");
		json.put("preduties_valueToChange", "ValueToChange");
		check(json, "anonymizeTransitPolicy", expectedResponseFilePath);
	}
	
	@Test
	void AnonymizeInTransitDeleteAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "AnonymizeInTransitDeleteAgreement"+".txt";
		//String url = baseUrl + "/policy/AnonymizeInTransitPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("preduties_modifier", "idsc:DELETE");
		json.put("preduties_fieldToChange", "FieldToChange");
		check(json, "anonymizeTransitPolicy", expectedResponseFilePath);
	}
	
	// Log Access
	@Test
	void LogAccessOnDenyAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "LogAccessOnDenyAgreement"+".txt";
		//String url = baseUrl + "/policy/LogAccessPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("postduties_logLevel", "idsc:ON_DENY");
		json.put("postduties_systemDevice", "SystemDevice");
		check(json, "logPolicy", expectedResponseFilePath);
	}
	
	@Test
	void LogAccessOnDutyExercisedAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "LogAccessOnDutyExercisedAgreement"+".txt";
		//String url = baseUrl + "/policy/LogAccessPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("postduties_logLevel", "idsc:ON_DUTY_EXERCISED");
		json.put("postduties_systemDevice", "SystemDevice");
		check(json, "logPolicy", expectedResponseFilePath);
	}

	// Inform Party
	void LogAccessOnDenyOnDenyAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "LogAccessOnDenyOnDenyAgreement"+".txt";
		//String url = baseUrl + "/policy/InformPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("postduties_notificationLevel", "idsc:ON_DENY");
		json.put("postduties_informedParty", "SystemDevice");
		check(json, "informPolicy", expectedResponseFilePath);
	}
	
	@Test
	void InformPartyOnDutyExerciseAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "InformPartyOnDutyExerciseAgreement"+".txt";
		//String url = baseUrl + "/policy/InformPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("postduties_notificationLevel", "idsc:ON_DUTY_EXERCISED");
		json.put("postduties_informedParty", "SystemDevice");
		check(json, "informPolicy", expectedResponseFilePath);
	}
	
	// Distribute Party
	void DistributeANONYMIZEDAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "DistributeANONYMIZEDAgreement"+".txt";
		//String url = baseUrl + "/policy/DistributePolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("artifactState", "idsc:ANONYMIZED");
		json.put("policy", "Policy1");
		check(json, "distributePolicyForm", expectedResponseFilePath);
	}
	
	@Test
	void DistributePSEUDONYMIZEDAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "DistributePSEUDONYMIZEDAgreement"+".txt";
		//String url = baseUrl + "/policy/DistributePolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		json.put("artifactState", "idsc:PSEUDONYMIZED");
		json.put("policy", "Policy1");
		check(json, "distributePolicyForm", expectedResponseFilePath);
	}
}
