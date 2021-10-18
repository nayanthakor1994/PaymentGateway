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
public class MakePaymentWithProfileTest extends BasePage {
	Response response;
	int responseCode;
	JSONObject jsonObj;
	JSONArray jsonArr;
	String apiTokenURL, apiURL, apiRequestURL, body, bodyAsString;
	String creditCardID, profileID, externalID;
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
		Constants.loginToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiIzZDE3ZTRiZi0wZWQyLTRhN2UtYTU5NC04ZmFlMGFhMzExN2IiLCJpYXQiOjE2MzM5NjU5NDUsIm5iZiI6MTYzMzk2NTk0NSwiZXhwIjoxNjMzOTY5NTQ1LCJpc3MiOiJodHRwczovL3d3dy5zYWFzYmVycnlsYWJzLmNvbSIsImF1ZCI6ImRlZmF1bHQifQ.AJOZlDEhPBtYPgqtzsDkmkYZTENljuRPa1g_Ggh9dhk";
//		profileID = "Ee04647D52c74F31B56d4178CDEc9354";
	}

	@Test(priority = 1, enabled = true, description = "Make Payment With Profile | POST")
	@Description("Make Payment With Profile.")
	@Feature("Feature : Make Payment With Profile")
	@Story("Story : Make a credit card payment with profile. (With valid details)")
	@Step("Make a credit card payment with profile. (With valid details)")
	@Severity(SeverityLevel.CRITICAL)
	public void makePaymentWithProfile() {
		apiRequestURL = apiURL + "/api/Payment/makepaymentwithprofile";
		map = ExcelUtils.getRowFromRowNumber(Constants.excelFileName, CreditCardSheet, Testcase3);
		map.put(CreditCard.Amount, "500");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		
		body = paymentBody.getMakePaymentWithProfileBody(map);
		response = apiPage.post(apiRequestURL, body, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		System.out.println("Response string : " + bodyAsString);
		Assert.assertEquals(responseCode, 200);
		System.out.println("Response code : " + responseCode);
		
		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("resultmessage").toString(), Success);
		Assert.assertTrue(obj.get("amount").toString().contains(map.get(CreditCard.Amount)));
		externalID = obj.get("externalid").toString();
	}
	
	@Test(priority = 2, enabled = true, description = "Make Payment With Profile | POST")
	@Description("Make Payment With Profile Expired Authentication.")
	@Feature("Feature : Make Payment With Profile Expired Authentication")
	@Story("Story : Make a credit card payment with profile Expired Authentication")
	@Step("Make a credit card payment with profile Expired Authentication")
	@Severity(SeverityLevel.CRITICAL)
	public void makePaymentWithProfileExpiredAuthentication() {
		apiRequestURL = apiURL + "/api/Payment/makepaymentwithprofile";
		map = ExcelUtils.getRowFromRowNumber(Constants.excelFileName, CreditCardSheet, Testcase3);
		map.put(CreditCard.Amount, "500");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		
		body = paymentBody.getMakePaymentWithProfileBody(map);
		
		String expiredLoginToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiIxMTMwNjIxNS05NjM4LTRkYjYtYjM4Mi0yY2VmYjMwNWZkYTAiLCJpYXQiOjE2MzI3MzI1NjUsIm5iZiI6MTYzMjczMjU2NSwiZXhwIjoxNjMyNzM2MTY1LCJpc3MiOiJodHRwczovL3d3dy5zYWFzYmVycnlsYWJzLmNvbSIsImF1ZCI6ImRlZmF1bHQifQ.zibWlduxMkLUk553yMBRsfn_vX1VKtdzB8JfXM44PpA";
		response = apiPage.post(apiRequestURL, body, expiredLoginToken);
		
		responseCode = apiPage.getResponseCode(response);
		Assert.assertEquals(responseCode, 401);
	}
	
	@Test(priority = 3, enabled = true, description = "Make Payment With Profile | POST")
	@Description("Make Payment With Profile No Authentication.")
	@Feature("Feature : Make Payment With Profile No Authentication")
	@Story("Story : Make a credit card payment with profile No Authentication")
	@Step("Make a credit card payment with profile No Authentication")
	@Severity(SeverityLevel.CRITICAL)
	public void makePaymentWithProfileNoAuthentication() {
		apiRequestURL = apiURL + "/api/Payment/makepaymentwithprofile";
		map = ExcelUtils.getRowFromRowNumber(Constants.excelFileName, CreditCardSheet, Testcase3);
		map.put(CreditCard.Amount, "500");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		
		body = paymentBody.getMakePaymentWithProfileBody(map);
		response = apiPage.post(apiRequestURL, body, null);
		
		responseCode = apiPage.getResponseCode(response);
		Assert.assertEquals(responseCode, 500);
	}
	
	@Test(priority = 4, enabled = true, description = "Make Payment With Profile | POST")
	@Description("Make Payment With Profile with past date. (expirationyear / expirationmonth).")
	@Feature("Feature : Make Payment With Profile")
	@Story("Story : Make a credit card payment with profile with past date. (expirationyear / expirationmonth)")
	@Step("Make a credit card payment with profile with past date. (expirationyear / expirationmonth)")
	@Severity(SeverityLevel.CRITICAL)
	public void makePaymentWithProfilePastData() {
		apiRequestURL = apiURL + "/api/Payment/makepaymentwithprofile";
		map = ExcelUtils.getRowFromRowNumber(Constants.excelFileName, CreditCardSheet, Testcase4);
		map.put(CreditCard.Amount, "500");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		
		body = paymentBody.getMakePaymentWithProfileBody(map);
		response = apiPage.post(apiRequestURL, body, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		System.out.println("Response string : " + bodyAsString);
		Assert.assertEquals(responseCode, 400);

		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("message"), "ValidationFailed");
		Assert.assertEquals(obj.getJSONObject("dataexception").getJSONObject("data").get("invalidexpirationdate"), 
				CreditCard.pastDateValidationMessage);
	}
	
	@Test(priority = 5, enabled = true, description = "Make Payment With Profile | POST")
	@Description("Make Payment With Profile with past date. (expirationyear / expirationmonth).")
	@Feature("Feature : Make Payment With Profile")
	@Story("Story : Make a credit card payment with profile with past date. (expirationyear / expirationmonth)")
	@Step("Make a credit card payment with profile with past date. (expirationyear / expirationmonth)")
	@Severity(SeverityLevel.CRITICAL)
	public void makePaymentWithProfileInvalidCard() {
		apiRequestURL = apiURL + "/api/Payment/makepaymentwithprofile";
		map = ExcelUtils.getRowFromRowNumber(Constants.excelFileName, CreditCardSheet, Testcase5);
		map.put(CreditCard.Amount, "500");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		
		body = paymentBody.getMakePaymentWithProfileBody(map);
		response = apiPage.post(apiRequestURL, body, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		System.out.println("Response string : " + bodyAsString);
		Assert.assertEquals(responseCode, 400);

		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("message"), "ValidationFailed");
		Assert.assertEquals(obj.getJSONObject("dataexception").getJSONObject("data").get("cardnumber"), 
				CreditCard.invalidCardValidationMessage);
	}
	
	@Test(priority = 6, enabled = true, description = "Make Payment With Profile | POST")
	@Description("Make Payment With Profile with invalid cvv number")
	@Feature("Feature : Make Payment With Profile")
	@Story("Story : Make a credit card payment with invalid cvv number")
	@Step("Make a credit card payment with profile with invalid cvv number")
	@Severity(SeverityLevel.CRITICAL)
	public void makePaymentWithProfileInvalidCVV() {
		apiRequestURL = apiURL + "/api/Payment/makepaymentwithprofile";
		map = ExcelUtils.getRowFromRowNumber(Constants.excelFileName, CreditCardSheet, Testcase6);
		map.put(CreditCard.Amount, "500");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		
		body = paymentBody.getMakePaymentWithProfileBody(map);
		response = apiPage.post(apiRequestURL, body, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		System.out.println("Response string : " + bodyAsString);
		Assert.assertEquals(responseCode, 400);

		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("message"), "ValidationFailed");
		Assert.assertEquals(obj.getJSONObject("dataexception").getJSONObject("data").get("cvvnumber"), 
				CreditCard.invalidCVVValidationMessage);
	}
	
	@Test(priority = 7, enabled = true, description = "Make Payment With Profile | POST")
	@Description("Make Payment With Profile with invalid amount")
	@Feature("Feature : Make Payment With Profile")
	@Story("Story : Make a credit card payment with invalid amount")
	@Step("Make a credit card payment with profile with invalid amount")
	@Severity(SeverityLevel.CRITICAL)
	public void makePaymentWithProfileInvalidAmount() {
		apiRequestURL = apiURL + "/api/Payment/makepaymentwithprofile";
		map = ExcelUtils.getRowFromRowNumber(Constants.excelFileName, CreditCardSheet, Testcase1);
		map.put(CreditCard.Amount, "0");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		
		body = paymentBody.getMakePaymentWithProfileBody(map);
		response = apiPage.post(apiRequestURL, body, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		System.out.println("Response string : " + bodyAsString);
		Assert.assertEquals(responseCode, 400);

		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("message"), "ValidationFailed");
		Assert.assertEquals(obj.getJSONObject("dataexception").getJSONObject("data").get("amount"), 
				CreditCard.invalidAmountValidationMessage);
	}
	
	@Test(priority = 8, enabled = true, description = "Make Payment With Profile | POST")
	@Description("Make Payment With Profile with negative amount")
	@Feature("Feature : Make Payment With Profile")
	@Story("Story : Make a credit card payment with negative amount")
	@Step("Make a credit card payment with profile with negative amount")
	@Severity(SeverityLevel.CRITICAL)
	public void makePaymentWithProfileNegativeAmount() {
		apiRequestURL = apiURL + "/api/Payment/makepaymentwithprofile";
		map = ExcelUtils.getRowFromRowNumber(Constants.excelFileName, CreditCardSheet, Testcase1);
		map.put(CreditCard.Amount, "-500");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		
		body = paymentBody.getMakePaymentWithProfileBody(map);
		response = apiPage.post(apiRequestURL, body, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		System.out.println("Response string : " + bodyAsString);
		Assert.assertEquals(responseCode, 400);

		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("message"), "ValidationFailed");
		Assert.assertEquals(obj.getJSONObject("dataexception").getJSONObject("data").get("amount"), 
				CreditCard.negativeAmountValidationMessage);
	}
}
