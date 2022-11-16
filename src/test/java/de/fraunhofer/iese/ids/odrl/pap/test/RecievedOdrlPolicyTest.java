package de.fraunhofer.iese.ids.odrl.pap.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import de.fraunhofer.iese.ids.odrl.pap.OdrlPapApplication;

/**
 * 
 * This class is used to check the integrity of the pap backend.
 *
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes =  OdrlPapApplication.class)
public class RecievedOdrlPolicyTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	private JSONObject jsonObject;
	
	private String baseUrl = "http://localhost:9090";
	private String basePath = "src/test/resources/responses/odrl/";
	
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
		jsonObject.put("language", "ODRL");
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
		jsonObject.put("restrictStartTime", "");
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
		jsonObject.put("consumer", "http://example.com/ids/party/consumer-party/");
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
	public void writeFile(JSONObject jsonObject, String url, String path) {
		try {
			String response = new JSONObject(send(jsonObject, url)).toString(4);
			byte[] out = response.getBytes();
			try {
				new File(path).createNewFile();
				Files.write(Paths.get(path), out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
