package de.fraunhofer.iese.ids.odrl.pap.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import de.fraunhofer.iese.ids.odrl.pap.OdrlPapApplication;

/**
 * 
 * This class is used to check the integrity of the pap backend.
 *
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes =  OdrlPapApplication.class)
public class RecievedIDSPolicyTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	private JSONObject jsonObject;
	
	private String baseUrl = "http://localhost:9090";
	private String basePath = "src/test/resources/responses/ids/";
	
	  @Before
	  public void setup() throws JSONException {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	     this.jsonObject = createAgreementHeader(emptyPolicy());
	    
	  }
	
	JSONObject emptyPolicy() throws JSONException {
		JSONArray jsonArray = new JSONArray();
		jsonArray.put("");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", 0);
		jsonObject.put("language", "IDS");
		jsonObject.put("policyType", "");
		jsonObject.put("target", "");
		jsonObject.put("provider", "");
		jsonObject.put("consumer", "");
		jsonObject.put("location_input", jsonArray);
		jsonObject.put("location_op", "");
		jsonObject.put("application_input", jsonArray);
		jsonObject.put("application_op", "");
		jsonObject.put("connector_input", jsonArray);
		jsonObject.put("connector_op", "");
		jsonObject.put("role_input", jsonArray);
		jsonObject.put("role_op", "");
		jsonObject.put("purpose_input", jsonArray);
		jsonObject.put("purpose_op", "");
		jsonObject.put("event_input", jsonArray);
		jsonObject.put("event_op", "");
		jsonObject.put("state_input", jsonArray);
		jsonObject.put("state_op", "");
		jsonObject.put("securityLevel_input", jsonArray);
		jsonObject.put("securityLevel_op", "");
		jsonObject.put("preduties_anomInRest", "");
		jsonObject.put("preduties_modifier", "");
		jsonObject.put("preduties_valueToChange", "");
		jsonObject.put("preduties_fieldToChange", "");
		jsonObject.put("postduties_anomInRest", "");
		jsonObject.put("postduties_logLevel", "");
		jsonObject.put("postduties_durationYear", "");
		jsonObject.put("postduties_durationMonth", "");
		jsonObject.put("postduties_durationDay", "");
		jsonObject.put("postduties_durationHour", "");
		jsonObject.put("postduties_timeAndDate", "");
		jsonObject.put("postduties_notificationLevel", "");
		jsonObject.put("postduties_informedParty", "");
		jsonObject.put("postduties_systemDevice", "");
		jsonObject.put("system", "");
		jsonObject.put("interval", "");
		jsonObject.put("payment", "");
		jsonObject.put("price", "");
		jsonObject.put("counter", "");
		jsonObject.put("encoding", "");
		jsonObject.put("policy", "");
		jsonObject.put("time", "");
		jsonObject.put("timeUnit", "");
		jsonObject.put("timeAndDate", "");
		jsonObject.put("durationHour", "");
		jsonObject.put("durationDay", "");
		jsonObject.put("durationMonth", "");
		jsonObject.put("durationYear", "");
		jsonObject.put("restrictTimeIntervalStart", "");
		jsonObject.put("restrictTimeIntervalEnd", "");
		jsonObject.put("restrictEndTime", "");
		jsonObject.put("specifyBeginTime", "");
		jsonObject.put("artifactState", "");
		return jsonObject;

	}
	

	
	
	/**
	 *  Creates an agreement header
	 * @param json that contains arguments
	 * @return json object with header
	 * @throws JSONException
	 */
	JSONObject createAgreementHeader(JSONObject jsonObject) throws JSONException {
		jsonObject.put("policyType", "Agreement");
		jsonObject.put("consumer", "http://example.com/ids/party/consumer-party");
		jsonObject.put("provider", "My party");
		jsonObject.put("target", "http://target/");
		return jsonObject;
	}
	
	/**
	 *  Creates an offer header
	 * @param json that contains arguments
	 * @return json object with header
	 * @throws JSONException
	 */
	JSONObject createOfferHeader(JSONObject jsonObject) throws JSONException {
		jsonObject.put("policyType", "Offer");
		jsonObject.put("provider", "My party");
		jsonObject.put("target", "http://target/");
		return jsonObject;
	}
	
	/**
	 * Creates a request header
	 * @param json that contains arguments
	 * @return json object with header
	 * @throws JSONException
	 */
	JSONObject createRequestHeader(JSONObject jsonObject) throws JSONException {
		jsonObject.put("policyType", "Request");
		jsonObject.put("consumer", "http://example.com/ids/party/consumer-party/");
		jsonObject.put("target", "http://target/");
		return jsonObject;
	}

	/**
	 * Send a json object to an rest end point specified by urlString
	 * @param json that contains arguments
	 * @param urlString of rest end point
	 * @return response of rest end point
	 */
	String send(JSONObject jsonObject, String urlString) {
		try {
			final MvcResult mvcResult = this.mockMvc
			        .perform(post(urlString)
			            .header("Content-Type", "application/json")
			            .content(jsonObject.toString()))
			        .andExpect(status().is(200)).andReturn();
			return mvcResult.getResponse().getContentAsString();
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		return "";
	}

	/**
	 * All expected responses are saved as .json files and this method can load one file
	 * @param path to file with expected response
	 * @return The specified file as string
	 */
	String readFile(String path) {
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}

		return content;
	}

	/**
	 * All expected responses are saved as .json files and this method can save a response as a file
	 * @param response.toString() string to write in the file
	 * @param path of the file
	 */
	public void writeFile(String response, String path) {
		byte[] out = response.getBytes();
		try {
			new File(path).createNewFile();
			Files.write(Paths.get(path), out);
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
	public void check(JSONObject jsonObject, String url, String path) {
		try {
			String response = new JSONObject(send(jsonObject, url)).toString(4);
			//writeFile(response, path); // This line will override all expected responses!
			String expectedResponse = new JSONObject(readFile(path)).toString(4);
			String errorMessage = JsonComparison.compareJson(expectedResponse, response);
			assertTrue(errorMessage == "", errorMessage);
			/*boolean isCorrect = expectedResponse.equals(response);
			
			if (!isCorrect) {
				System.out.println("Expected: \n" + expectedResponse);
				System.out.println("Repsonse: \n" + response);
			} 
			Assert.assertTrue(isCorrect);*/
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
// ##################### Tests #####################
	
	// Complex Policy
	@Test
	public void ComplexPolicyAgreement() throws Exception {
		String expectedResponseFilePath = basePath + "ComplexPolicyAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		JSONObject json = createAgreementHeader(emptyPolicy());
		check(json, url, expectedResponseFilePath);

	}

	@Test
	public void ComplexPolicyApplicationSingleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyApplicationSingleAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		JSONArray application = new JSONArray();
		application.put("http://Application1");
		jsonObject.put("application_input", application);
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyApplicationMultipleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyApplicationMultipleAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		JSONArray application = new JSONArray();
		application.put("http://Application1");
		application.put("http://Application2");
		jsonObject.put("application_input", application);
		jsonObject.put("application_op", "IN");
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyConnectorSingleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyConnectorSingleAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		JSONArray connector = new JSONArray();
		connector.put("http://Connector1");
		jsonObject.put("connector_input", connector);
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyConnectornMultipleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyConnectornMultipleAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		JSONArray connector = new JSONArray();
		connector.put("http://Connector1");
		connector.put("http://Connector2");
		jsonObject.put("connector_input", connector);
		jsonObject.put("connector_op", "IN");
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyCounterAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyCounterAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		jsonObject.put("counter", "222");
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyDurationAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyDurationAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		jsonObject.put("durationDay", "10");
		jsonObject.put("durationHour", "03");
		jsonObject.put("durationMonth", "01");
		jsonObject.put("durationYear", "2051");
		jsonObject.put("specifyBeginTime", "2050-12-16T20:47");
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyRestrictEndTimeAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyRestrictEndTimeAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		jsonObject.put("restrictEndTime", "2051-12-16T20:47");
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicySecurityLevelSingleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicySecurityLevelSingleAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		JSONArray securityLevel = new JSONArray();
		securityLevel.put("idsc:TRUST_SECURITY_PROFILE");
		jsonObject.put("securityLevel_input", securityLevel);
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicySecurityLevelMultipleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicySecurityLevelMultipleAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		JSONArray securityLevel = new JSONArray();
		securityLevel.put("idsc:TRUST_SECURITY_PROFILE");
		securityLevel.put("idsc:TRUST_PLUS_SECURITY_PROFILE");
		jsonObject.put("securityLevel_input", securityLevel);
		jsonObject.put("securityLevel_op", "IN");
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyIntervalAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyIntervalAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		jsonObject.put("restrictTimeIntervalEnd", "2052-01-05T20:47:00Z");
		jsonObject.put("restrictTimeIntervalStart", "2051-12-16T20:47:00Z");
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyLocationSingleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyLocationSingleAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		JSONArray location = new JSONArray();
		location.put("http://location1");
		jsonObject.put("location_input", location);
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyLocationMultipleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyLocationMultipleAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		JSONArray location = new JSONArray();
		location.put("http://location1");
		location.put("http://location2");
		jsonObject.put("location_input", location);
		jsonObject.put("location_op", "IN");
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyPurposeSingleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyPurposeSingleAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		JSONArray purpose = new JSONArray();
		purpose.put("http://example.com/ids/purpose/Marketing");
		jsonObject.put("purpose_input", purpose);
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyPurposeMultipleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyPurposeMultipleAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		JSONArray purpose = new JSONArray();
		purpose.put("http://example.com/ids/purpose/Marketing");
		purpose.put("http://example.com/ids/purpose/Risk_Management");
		jsonObject.put("purpose_input", purpose);
		jsonObject.put("purpose_op", "IN");
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyRoleSingleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyRoleSingleAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		JSONArray role = new JSONArray();
		role.put("idsc:ADMIN");
		jsonObject.put("role_input", role);
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyRoleMultipleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyRoleMultipleAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		JSONArray role = new JSONArray();
		role.put("idsc:ADMIN");
		role.put("idsc:DEVELOPER");
		jsonObject.put("role_input", role);
		jsonObject.put("role_op", "IN");
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyStateSingleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyStateSingleAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		JSONArray state = new JSONArray();
		state.put("CONTRACTNOTTERMINATED");
		jsonObject.put("state_input", state);
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyStateMultipleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyStateMultipleAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		JSONArray state = new JSONArray();
		state.put("CONTRACTNOTTERMINATED");
		state.put("FIREWALLACTIVATED");
		jsonObject.put("state_input", state);
		jsonObject.put("state_op", "IN");
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyEventSingleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyEventSingleAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		JSONArray event = new JSONArray();
		event.put("http://Event1");
		jsonObject.put("event_input", event);
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyEventMultipleAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyEventMultipleAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		JSONArray event = new JSONArray();
		event.put("http://Event1");
		event.put("http://Event2");
		jsonObject.put("event_input", event);
		jsonObject.put("event_op", "IN");
		check(jsonObject, url, expectedResponseFilePath);
	}
	

	@Test
	public void ComplexPolicyPaymentAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyPaymentAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		jsonObject.put("price", "10");
		jsonObject.put("payment", "http://dbpedia.org/page/Rent");
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyDistributeDataAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyDistributeDataAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		jsonObject.put("policy", "http://Policy1");
		jsonObject.put("artifactState", "idsc:ANONYMIZED");
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyAnonymizeinRestAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyAnonymizeinRestAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		jsonObject.put("preduties_anomInRest", "Active");
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyAnonymizeinTransitReplaceAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyAnonymizeinTransitReplaceAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		jsonObject.put("preduties_fieldToChange", "Field1");
		jsonObject.put("preduties_modifier", "idsc:REPLACE");
		jsonObject.put("preduties_valueToChange", "ValueToChange");
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyAnonymizeinTransitDeleteAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyAnonymizeinTransitDeleteAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		jsonObject.put("preduties_modifier", "idsc:DELETE");
		jsonObject.put("preduties_fieldToChange", "Field1");
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyLogDataUsageAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyLogDataUsageAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		jsonObject.put("postduties_logLevel", "idsc:ON_DUTY_EXERCISED");
		jsonObject.put("postduties_systemDevice", "http://SystemDevice");
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyInformPartyAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyInformPartyAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		jsonObject.put("postduties_informedParty", "InformParty");
		jsonObject.put("postduties_notificationLevel", "idsc:ON_ALLOW");
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyDeleteDataAfterTimeDurationAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyDeleteDataAfterTimeDurationAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		jsonObject.put("postduties_durationDay", "1");
		jsonObject.put("postduties_durationHour", "2");
		jsonObject.put("postduties_durationMonth", "3");
		jsonObject.put("postduties_durationYear", "2051");
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyDeleteDataAfterSpecificTimeAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyDeleteDataAfterSpecificTimeAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		jsonObject.put("postduties_timeAndDate", "2051-12-16T21:08");
		check(jsonObject, url, expectedResponseFilePath);
	}

	@Test
	public void ComplexPolicyWithAllArgumentsAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "ComplexPolicyWithAllArgumentsAgreement.json";
		String url = baseUrl + "/policy/ComplexPolicyForm";
		
		JSONArray application = new JSONArray();
		application.put("Application1");
		jsonObject.put("application_input", application);
	
		JSONArray connector = new JSONArray();
		connector.put("Connector1");
		jsonObject.put("connector_input", connector);
	
		jsonObject.put("counter", "222");

	
		jsonObject.put("durationDay", "21");
		jsonObject.put("durationHour", "10");
		jsonObject.put("durationMonth", "10");
		jsonObject.put("durationYear", "2021");
		jsonObject.put("specifyBeginTime", "2021-12-16T20:43");

	
		jsonObject.put("restrictEndTime", "2021-12-16T20:43");

		JSONArray securityLevel = new JSONArray();
		securityLevel.put("idsc:TRUST_SECURITY_PROFILE");
		jsonObject.put("securityLevel_input", securityLevel);

		jsonObject.put("restrictTimeIntervalEnd", "2022-01-05T20:47");
		jsonObject.put("restrictTimeIntervalStart", "2021-12-16T20:47");
	
		JSONArray location = new JSONArray();
		location.put("location1");
		jsonObject.put("location_input", location);
	
		JSONArray purpose = new JSONArray();
		purpose.put("http://example.com/ids-purpose/Marketing");
		jsonObject.put("purpose_input", purpose);
	
		JSONArray role = new JSONArray();
		role.put("idsc:ADMIN");
		jsonObject.put("role_input", role);

		JSONArray state = new JSONArray();
		state.put("CONTRACTNOTTERMINATED");
		jsonObject.put("state_input", state);
	
		JSONArray event = new JSONArray();
		event.put("event1");
		jsonObject.put("event_input", event);
	
		jsonObject.put("price", "10");
		jsonObject.put("payment", "http://dbpedia.org/page/Rent");
	
		jsonObject.put("policy", "Policy1");
		jsonObject.put("artifactState", "idsc:ANONYMIZED");
	
		jsonObject.put("preduties_anomInRest", "Active");
	
		jsonObject.put("preduties_fieldToChange", "Field1");
		jsonObject.put("preduties_modifier", "idsc:REPLACE");
		jsonObject.put("preduties_valueToChange", "ValueToChange");

	
		jsonObject.put("postduties_logLevel", "idsc:ON_DUTY_EXERCISED");
		jsonObject.put("postduties_systemDevice", "SystemDevice1");
	
		jsonObject.put("postduties_informedParty", "InformParty");
		jsonObject.put("postduties_notificationLevel", "idsc:ON_ALLOW");

		jsonObject.put("postduties_durationDay", "1");
		jsonObject.put("postduties_durationHour", "2");
		jsonObject.put("postduties_durationMonth", "3");
		jsonObject.put("postduties_durationYear", "2021");
	
		jsonObject.put("postduties_timeAndDate", "2021-12-16T21:08");	
		
		check(jsonObject, url, expectedResponseFilePath);
	}
	
	
	// Counter
	@Test
	public void CountAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "CountAgreement.json";
		String url = baseUrl + "/policy/CountAccessPolicyForm";
		
		jsonObject.put("counter", "22");
		check(jsonObject, url, expectedResponseFilePath);
	}
	
	// Delete Data After
	
	@Test
	public void DeleteDataAfterAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "DeleteDataAfterAgreement"+".json";
		String url = baseUrl + "/policy/deletePolicyAfterUsagePeriod";
		
		check(jsonObject, url, expectedResponseFilePath);
	}
	
	@Test
	public void DeleteDataAfterExactTimeAndDateAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "DeleteDataAfterExactTimeAndDateAgreement"+".json";
		String url = baseUrl + "/policy/deletePolicyAfterUsage";
		
		jsonObject.put("postduties_timeAndDate", "2021-12-16T11:10");
		check(jsonObject, url, expectedResponseFilePath);
	}
	
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
	public void AnonymizeInTransitDeleteAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "AnonymizeInTransitDeleteAgreement"+".json";
		String url = baseUrl + "/policy/AnonymizeInTransitPolicyForm";
		
		jsonObject.put("preduties_modifier", "idsc:DELETE");
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
	//Missing!
	@Test
	public void InformPolicyFormOnDenyAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "LogAccessOnDenyOnDenyAgreement"+".json";
		String url = baseUrl + "/policy/InformPolicyForm";
		
		jsonObject.put("postduties_notificationLevel", "idsc:ON_DENY");
		jsonObject.put("postduties_informedParty", "http://SystemDevice");
		check(jsonObject, url, expectedResponseFilePath);
	}
	
	@Test
	public void InformPartyOnDutyExerciseAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "InformPartyOnDutyExerciseAgreement"+".json";
		String url = baseUrl + "/policy/InformPolicyForm";
		
		jsonObject.put("postduties_notificationLevel", "idsc:ON_DUTY_EXERCISED");
		jsonObject.put("postduties_informedParty", "http://SystemDevice");
		check(jsonObject, url, expectedResponseFilePath);
	}
	
	// Distribute Party
	@Test
	public void DistributeANONYMIZEDAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "DistributeANONYMIZEDAgreement"+".json";
		String url = baseUrl + "/policy/DistributePolicyForm";
		
		jsonObject.put("artifactState", "idsc:ANONYMIZED");
		jsonObject.put("policy", "http://Policy1");
		check(jsonObject, url, expectedResponseFilePath);
	}
	
	@Test
	public void DistributePSEUDONYMIZEDAgreement() throws JSONException {
		String expectedResponseFilePath = basePath + "DistributePSEUDONYMIZEDAgreement"+".json";
		String url = baseUrl + "/policy/DistributePolicyForm";
		
		jsonObject.put("artifactState", "idsc:PSEUDONYMIZED");
		jsonObject.put("policy", "http://Policy1");
		check(jsonObject, url, expectedResponseFilePath);
	}
	
}
