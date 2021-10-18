package com.test;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.base.BasePage;
import com.base.Constants;
import com.pages.APIPages;
import com.utils.ExcelUtils;
import com.utils.ReadProperty;
import com.utils.TestUtil;

import io.restassured.response.Response;

public class APITest extends BasePage {
	
	Response response;
	int responseCode;
	JSONObject jsonObj;
	JSONArray jsonArr;
	String apiRequestURL, apiURL, adminQaURL, body, bodyAsString, accountNumber, firstName, lastName, emailAddress, phoneNumber;
	boolean result;
	Map<String, String> map = new HashMap<String, String>();
	TestUtil util = new TestUtil();
	
	@BeforeClass
	public void setup() {
		apiURL = prop.getProperty("apiURL");
		adminQaURL = prop.getProperty("adminQAURL");
	}
	
	@Test(priority = 1, enabled = true)
	public void getNoToken() {
		apiRequestURL = apiURL + "/api/Auth/GenerateToken/9B0CC7B6-1562-4924-8EE9-E7A1B1C87193-XXXXXX";
		APIPages apiPage = new APIPages();
		
		response = apiPage.get(apiRequestURL);
		
		responseCode = apiPage.getResponseCode(response);
		Assert.assertEquals(responseCode, 401);
		String bodyAsString = apiPage.getResponseBody(response);
		System.out.println("Response code : " + responseCode);
		System.out.println("Response string : " + bodyAsString);
		
		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("title").toString(), "Unauthorized");
	}
	
	@Test(priority = 2, enabled = true)
	public void getToken() {
		apiRequestURL = apiURL + "/api/Auth/GenerateToken/" + prop.getProperty("tokenID");
		APIPages apiPage = new APIPages();
		
		response = apiPage.get(apiRequestURL);
		
		responseCode = apiPage.getResponseCode(response);
		Assert.assertEquals(responseCode, 200);
		String bodyAsString = apiPage.getResponseBody(response);
		System.out.println("Response code : " + responseCode);
//		System.out.println("Response string : " + bodyAsString);
		
		JSONObject obj = new JSONObject(bodyAsString);
		Constants.loginToken = obj.get("token").toString();
		System.out.println("Token string : " + loginToken);
	}
	
	@Test(priority = 3, enabled = true)
	public void createUser() {
		apiRequestURL = adminQaURL+"/Lead/SaveProspect";
		APIPages apiPage = new APIPages();
		map = ExcelUtils.getRowFromRowNumber(Constants.excelFileName, "Testdata","Testcase1");
//		firstName = map.get(Constants.Firstname);
//		lastName = map.get(Constants.Lastname);
//		emailAddress = util.randomGenerator(4) + map.get(Constants.EmailID);
//		phoneNumber = map.get(Constants.PhoneNumber);
		
//		body = apiBody.getCreateUserBody(firstName, lastName, emailAddress, phoneNumber);
		response = apiPage.post(apiRequestURL, body, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		System.out.println("Response string : " + bodyAsString);
		Assert.assertEquals(responseCode, 200);
		System.out.println("Response code : " + responseCode);

		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("isSaveLead"), true);
		JSONArray leadData = obj.getJSONArray("leads");
		accountNumber = leadData.getJSONObject(0).get("returnAccountId").toString();
		System.out.println("Account Number : " + accountNumber);
		ReadProperty.setPropertyValue("accountNumber", accountNumber);
	}
	
	@Test(priority = 4, enabled = true)
	public void createDuplicateUser() {
		apiRequestURL = adminQaURL + "/Lead/SaveProspect";
		APIPages apiPage = new APIPages();
		map = ExcelUtils.getRowFromRowNumber(Constants.excelFileName, "Testdata","Testcase1");
		
//		body = apiBody.getCreateUserBody(firstName, lastName, emailAddress, phoneNumber);
		response = apiPage.post(apiRequestURL, body, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		System.out.println("Response string : " + bodyAsString);
		Assert.assertEquals(responseCode, 200);
		System.err.println("Response code : " + responseCode);
		
		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("isSaveLead"), false);
		JSONArray leadData = obj.getJSONArray("lstDuplicateLead");
		Assert.assertEquals(leadData.getJSONObject(0).get("email").toString(), emailAddress);
	}
	
	
	@Test(priority = 5, enabled = true)
	public void getAccountData() {
		if(accountNumber.length() > 0) {
			apiRequestURL = apiURL + "/api/Accounts/GetAccountByAccountId/" + accountNumber;
			APIPages apiPage = new APIPages();
			response = apiPage.get(apiRequestURL, Constants.loginToken);
			
			responseCode = apiPage.getResponseCode(response);
			Assert.assertEquals(responseCode, 200);
			String bodyAsString = apiPage.getResponseBody(response);
			System.out.println("Response code : " + responseCode);
			System.out.println("Response string : " + bodyAsString);
			
			JSONObject obj = new JSONObject(bodyAsString);
			Assert.assertEquals(obj.get("accountId").toString(), accountNumber);
			Assert.assertEquals(obj.get("firstName").toString(), firstName);
			Assert.assertEquals(obj.get("lastName").toString(), lastName);
			Assert.assertEquals(obj.get("email").toString(), emailAddress);
			Assert.assertEquals(obj.get("mobile").toString(), phoneNumber);
		}
		
	}
	@Test(priority = 6, enabled = true)
	public void getNegativeAccountData() {
		if(accountNumber.length() > 0) {
			apiRequestURL = apiURL + "/api/Accounts/GetAccountByAccountId/" + accountNumber + "XX";
			APIPages apiPage = new APIPages();
			response = apiPage.get(apiRequestURL, loginToken);
			
			responseCode = apiPage.getResponseCode(response);
			Assert.assertEquals(responseCode, 400);
			String bodyAsString = apiPage.getResponseBody(response);
			System.out.println("Response code : " + responseCode);
			System.out.println("Response string : " + bodyAsString);
			
			JSONObject obj = new JSONObject(bodyAsString);
//			Assert.assertEquals(obj.get("title").toString(), Constants.validationMessage);
			Assert.assertEquals(obj.get("status").toString(), "400");
		}
		
	}

}
