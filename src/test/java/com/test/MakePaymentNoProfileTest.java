package com.test;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.base.BasePage;
import com.base.Constants;
import com.excel.CreditCard;
import com.listeners.TestListeners;
import com.pages.APIPages;
import com.pages.CommonPage;
import com.pages.CreditCardAPIBody;
import com.pages.MakePaymentBody;
import com.pages.RefundPaymentBody;
import com.utils.ExcelUtils;
import com.utils.TestUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.response.Response;

@Listeners({TestListeners.class})
public class MakePaymentNoProfileTest extends BasePage {
	Response response;
	int responseCode;
	JSONObject jsonObj;
	JSONArray jsonArr;
	String apiTokenURL, apiURL, apiRequestURL, body, bodyAsString;
	String creditCardID, profileID, externalID, paymentID;
	boolean result;
	TestUtil util;
	Map<String, String> map = new HashMap<String, String>();
	APIPages apiPage;
	CreditCardAPIBody cardBody;
	MakePaymentBody paymentBody;
	RefundPaymentBody refundBody;
	CommonPage objCommon;
	@BeforeClass
	public void setup() {
		util = new TestUtil();
		apiPage = new APIPages();
		cardBody = new CreditCardAPIBody();
		objCommon = new CommonPage();
		paymentBody = new MakePaymentBody();
		refundBody = new RefundPaymentBody();
		apiTokenURL = prop.getProperty("apiTokenURL");
		apiURL = prop.getProperty("apiQAURL");
//		if (Constants.loginToken == null) {
//			Constants.loginToken = objCommon.getLoginTokens(objCommon.getloginTokensURL(apiTokenURL));
//		}
		Constants.loginToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiI2MmE0NjgzMy02MmY5LTRkZDAtOWVhYy04ZjY0YzE1NzFmYzkiLCJpYXQiOjE2MzM2MzA2MTIsIm5iZiI6MTYzMzYzMDYxMiwiZXhwIjoxNjMzNjM0MjEyLCJpc3MiOiJodHRwczovL3d3dy5zYWFzYmVycnlsYWJzLmNvbSIsImF1ZCI6ImRlZmF1bHQifQ.2B6AZSUiKQMQp5hco-zmgCOkMIA6DHzhcUMU1WPAw0U";
		profileID = "Ee04647D52c74F31B56d4178CDEc9354";
	}
	
	@Test(priority = 1, enabled = false, description = "Add the credit card with valid details")
	@Description("Add the credit card with valid details.")
	@Feature("Feature : Add Credit Card")
	@Story("Story : Add the Credit Card with valid details.")
	@Step("Add Credit Card Details")
	@Severity(SeverityLevel.CRITICAL)
	public void addCreditCard() {
		apiRequestURL = apiURL + "/api/Payment/addcreditcard";
		map = ExcelUtils.getRowFromRowNumber(Constants.excelFileName, CreditCardSheet, Testcase1);
		
		body = cardBody.getAddCreditCardBody(map);
		response = apiPage.post(apiRequestURL, body, Constants.loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		System.out.println("Response string : " + bodyAsString);
		Assert.assertEquals(responseCode, 200);
		System.out.println("Response code : " + responseCode);

		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("resultmessage"), Success);
		profileID = obj.get("profileid").toString();
	}
	
	@Test(priority = 2, enabled = false, description = "Make Payment Without Profile | POST")
	@Description("Make Payment Without Profile.")
	@Feature("Feature : Make Payment Without Profile")
	@Story("Story : Make a credit card payment with profileid. (With valid details)")
	@Step("Make a credit card payment with profileid. (With valid details)")
	@Severity(SeverityLevel.CRITICAL)
	public void makePaymentWithoutProfile() {
		apiRequestURL = apiURL + "/api/Payment/makepaymentwithoutprofile";
		map.put(CreditCard.ProfileID, profileID);
		map.put(CreditCard.Amount, "500");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		
		body = paymentBody.getMakePaymentBody(map);
		response = apiPage.post(apiRequestURL, body, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		Assert.assertEquals(responseCode, 200);
		
		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("resultmessage").toString(), Success);
		Assert.assertEquals(obj.get("amount").toString(), map.get(CreditCard.Amount));
		Assert.assertEquals(obj.get("invoicenumber").toString(), map.get(CreditCard.InvoiceNumber));
		externalID = obj.get("externalid").toString();
		paymentID = obj.get("paymentid").toString();
	}
	
	@Test(priority = 3, enabled = false, description = "Make Payment Without Profile | Expired authentication")
	@Description("Make Payment Without Profile.")
	@Feature("Feature : Make Payment Without Profile")
	@Story("Story : Make a credit card payment with expired authentication.")
	@Step("Make a credit card payment with expired authentication.")
	@Severity(SeverityLevel.CRITICAL)
	public void makePaymentExpiredAuthentication() {
		apiRequestURL = apiURL + "/api/Payment/makepaymentwithoutprofile";
		map.put(CreditCard.ProfileID, profileID);
		map.put(CreditCard.Amount, "500");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		body = paymentBody.getMakePaymentBody(map);

		String expiredLoginToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiIxMTMwNjIxNS05NjM4LTRkYjYtYjM4Mi0yY2VmYjMwNWZkYTAiLCJpYXQiOjE2MzI3MzI1NjUsIm5iZiI6MTYzMjczMjU2NSwiZXhwIjoxNjMyNzM2MTY1LCJpc3MiOiJodHRwczovL3d3dy5zYWFzYmVycnlsYWJzLmNvbSIsImF1ZCI6ImRlZmF1bHQifQ.zibWlduxMkLUk553yMBRsfn_vX1VKtdzB8JfXM44PpA";
		response = apiPage.post(apiRequestURL, body, expiredLoginToken);
		
		responseCode = apiPage.getResponseCode(response);
		Assert.assertEquals(responseCode, 401);
	}
	
	@Test(priority = 4, enabled = false, description = "Make Payment Without Profile | No authentication")
	@Description("Make Payment Without Profile.")
	@Feature("Feature : Make Payment Without Profile")
	@Story("Story : Make a credit card payment with no authentication.")
	@Step("Make a credit card payment with no authentication.")
	@Severity(SeverityLevel.CRITICAL)
	public void makePaymentNoAuthentication() {
		apiRequestURL = apiURL + "/api/Payment/makepaymentwithoutprofile";
		map.put(CreditCard.ProfileID, profileID);
		map.put(CreditCard.Amount, "500");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		body = paymentBody.getMakePaymentBody(map);

		response = apiPage.post(apiRequestURL, body, null);
		
		responseCode = apiPage.getResponseCode(response);
		Assert.assertEquals(responseCode, 500);
	}
	
	@Test(priority = 5, enabled = false, description = "Make Payment Without Profile | POST")
	@Description("Make Payment Without Profile.")
	@Feature("Feature : Make Payment Without Profile")
	@Story("Story : Make a credit card payment with invalid profileid.")
	@Step("Make a credit card payment with invalid profileid.")
	@Severity(SeverityLevel.CRITICAL)
	public void makePaymentWithInvalidProfile() {
		apiRequestURL = apiURL + "/api/Payment/makepaymentwithoutprofile";
		map.put(CreditCard.ProfileID, "Ee04647D52c74F31B56d4178CDEXXXXX");
		map.put(CreditCard.Amount, "500");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		
		body = paymentBody.getMakePaymentBody(map);
		response = apiPage.post(apiRequestURL, body, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		Assert.assertEquals(responseCode, 400);
		
		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("message"), "ValidationFailed");
		Assert.assertEquals(obj.getJSONObject("dataexception").getJSONObject("data").get("profileid"), 
				CreditCard.invalidProfileValidationMessage);
	}
	
	@Test(priority = 6, enabled = true, description = "Make Payment Without Profile | POST")
	@Description("Make Payment Without Profile.")
	@Feature("Feature : Make Payment Without Profile")
	@Story("Story : Make a credit card payment with invalid Amount.")
	@Step("Make a credit card payment with invalid Amount.")
	@Severity(SeverityLevel.CRITICAL)
	public void makePaymentWithInvalidAmount() {
		apiRequestURL = apiURL + "/api/Payment/makepaymentwithoutprofile";
		map.put(CreditCard.ProfileID, profileID);
		map.put(CreditCard.Amount, "0");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		
		body = paymentBody.getMakePaymentBody(map);
		response = apiPage.post(apiRequestURL, body, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		Assert.assertEquals(responseCode, 400);
		
		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("message"), "ValidationFailed");
		Assert.assertEquals(obj.getJSONObject("dataexception").getJSONObject("data").get("amount"), 
				CreditCard.invalidAmountValidationMessage);
	}
	
	@Test(priority = 7, enabled = true, description = "Make Payment Without Profile | POST")
	@Description("Make Payment Without Profile.")
	@Feature("Feature : Make Payment Without Profile")
	@Story("Story : Make a credit card payment with invalid Amount.")
	@Step("Make a credit card payment with invalid Amount.")
	@Severity(SeverityLevel.CRITICAL)
	public void makePaymentWithInvalidNegativeAmount() {
		apiRequestURL = apiURL + "/api/Payment/makepaymentwithoutprofile";
		map.put(CreditCard.ProfileID, profileID);
		map.put(CreditCard.Amount, "-11");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		
		body = paymentBody.getMakePaymentBody(map);
		response = apiPage.post(apiRequestURL, body, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		Assert.assertEquals(responseCode, 400);
		
		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("message"), "ValidationFailed");
		Assert.assertEquals(obj.getJSONObject("dataexception").getJSONObject("data").get("amount"), 
				CreditCard.negativeAmountValidationMessage);
	}

}
