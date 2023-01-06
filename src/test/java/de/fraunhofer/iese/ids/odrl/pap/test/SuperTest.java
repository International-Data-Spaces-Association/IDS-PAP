package de.fraunhofer.iese.ids.odrl.pap.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;		


public class SuperTest {
    protected String baseUrl = "http://localhost:9090";
    private static boolean activateVisualizeDifferences = false;
    protected static WebDriver driver = null;

	@Autowired
	protected WebApplicationContext wac;
	
	protected MockMvc mockMvc;
	
	protected JSONObject jsonObject;
	
	protected String language = "";
	
	public SuperTest(String language) {
		this.language = language;
	}
	
	@BeforeClass
	public static void setUpWebdirver() {
	    if (activateVisualizeDifferences) {
			WebDriverManager.chromedriver().browserVersion("77.0.3865.40").setup();
			driver = new ChromeDriver(); 
	    }
	}
	
	  @Before
	  public void setup() throws JSONException {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	    this.jsonObject = createAgreementHeader(emptyPolicy());
	  }
	
	private void visualizeDifferences(String expected, String output, String headerText) {
		if (activateVisualizeDifferences) {
		    ((JavascriptExecutor)driver).executeScript("window.open()");
		    ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		    driver.switchTo().window(tabs.get(tabs.size()-1)); //switches to new tab
		    driver.get("https://jsondiff.com/");
		    
	        WebElement textareal = driver.findElement(By.id("textarealeft"));
	        textareal.sendKeys(expected);
	        WebElement textarear = driver.findElement(By.id("textarearight"));
	        textarear.sendKeys(output);
	        WebElement button = driver.findElement(By.id("compare"));
	        button.click();
	        String script = "var h1 = document.createElement('h1');" +
	                "h1.innerHTML = '" + headerText + "';" +
	                "document.body.insertBefore(h1, document.body.firstChild);";
			((JavascriptExecutor) driver).executeScript(script);
		}
	}
	
	JSONObject emptyPolicy() throws JSONException {
		JSONArray jsonArray = new JSONArray();
		jsonArray.put("");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", 0);
		jsonObject.put("language", language);
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
		jsonObject.put("consumer", "http://example.com/ids/party/consumer-party");
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
			if (errorMessage != "") {
		        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		        String callingMethodName = stackTraceElements[2].getMethodName();
				visualizeDifferences(expectedResponse, response, callingMethodName);
			}
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
	
	
}
