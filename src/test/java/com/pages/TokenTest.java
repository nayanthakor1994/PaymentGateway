package com.pages;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.base.BasePage;
import com.base.Constants;
import com.utils.TestUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.response.Response;

public class TokenTest extends BasePage {

	Response response;
	int responseCode;
	JSONObject jsonObj;
	JSONArray jsonArr;
	String apipiURL, apiRequestURL, body, bodyAsString;
	boolean result;
	TestUtil util;
	Map<String, String> map = new HashMap<String, String>();
	APIPages apiPage;
	CreditCardAPIBody apiBody;
	@BeforeClass
	public void setup() {
		util = new TestUtil();
		apiPage = new APIPages();
		apiBody = new CreditCardAPIBody();
		apipiURL = prop.getProperty("apiQAURL");
	}

	@Test(priority = 2, enabled = true, description = "Get Valid Token")
	@Description("Get Valid Token")
	@Feature("Feature : Validate Tokens")
	@Story("Story : Validate Valid Tokens")
	@Step("Validate Valid Tokens")
	@Severity(SeverityLevel.BLOCKER)
	public void getValidTokens() {
		apiRequestURL = apipiURL + "/api/Token/createtoken";

		response = apiPage.post(apiRequestURL, "");
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		System.out.println("Response string : " + bodyAsString);
		Assert.assertEquals(responseCode, 200);
		System.out.println("Response code : " + responseCode);

		JSONObject obj = new JSONObject(bodyAsString);
		Constants.loginToken = obj.get("token").toString();
		System.out.println("Token string : " + loginToken);
	}
}
