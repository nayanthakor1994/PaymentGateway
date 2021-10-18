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
public class RefundPaymentTest extends BasePage {
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
		if (Constants.loginToken == null) {
			Constants.loginToken = objCommon.getLoginTokens(objCommon.getloginTokensURL(apiTokenURL));
		}
//		Constants.loginToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiIzYWFhZTkxOS0wZTBlLTQ1ODgtODA2OC1kMTM3OTE5NmU1ODMiLCJpYXQiOjE2MzQzODg5MTgsIm5iZiI6MTYzNDM4ODkxOCwiZXhwIjoxNjM0MzkyNTE4LCJpc3MiOiJodHRwczovL3d3dy5zYWFzYmVycnlsYWJzLmNvbSIsImF1ZCI6ImRlZmF1bHQifQ.Ne9cY3hpfrMf6-Kj9lOepCD9lmtWqs5nFxL_AnlTplM";
		externalID = "10000916";
	}
	

	@Test(priority = 1, enabled = false, description = "Make Payment With Profile | POST")
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
	
	@Test(priority = 2, enabled = false, description = "Refund Payement | POST")
	@Description("Refund Payment.")
	@Feature("Feature : Refund Payment.")
	@Story("Story : Refund the payment to the customer with valid details.")
	@Step("Refund the payment to the customer with valid details.")
	@Severity(SeverityLevel.CRITICAL)
	public void refundPayment() {
		apiRequestURL = apiURL + "/api/Payment/refundpayment";
		map = ExcelUtils.getRowFromRowNumber(Constants.excelFileName, CreditCardSheet, Testcase3);
		map.put(CreditCard.ExternalID, externalID);
		map.put(CreditCard.Amount, "10");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		
		body = refundBody.getRefundPaymentBody(map);
		response = apiPage.post(apiRequestURL, body, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		System.out.println("Response string : " + bodyAsString);
		Assert.assertEquals(responseCode, 200);
		System.out.println("Response code : " + responseCode);
		
		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("statuskey").toString(), "Success");
		Assert.assertTrue(obj.get("amount").toString().contains(map.get(CreditCard.Amount)));
	}
	
	@Test(priority = 3, enabled = false, description = "Refund Payement | POST")
	@Description("Refund Payment.")
	@Feature("Feature : Refund Payment.")
	@Story("Story : Refund the payment to the customer with expired authentication.")
	@Step("Refund the payment to the customer with expired authentication.")
	@Severity(SeverityLevel.CRITICAL)
	public void refundPaymentExpiredAuthentication() {
		apiRequestURL = apiURL + "/api/Payment/refundpayment";
		map = ExcelUtils.getRowFromRowNumber(Constants.excelFileName, CreditCardSheet, Testcase3);
		map.put(CreditCard.ExternalID, externalID);
		map.put(CreditCard.Amount, "1");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		
		body = refundBody.getRefundPaymentBody(map);
		String expiredLoginToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiIxMTMwNjIxNS05NjM4LTRkYjYtYjM4Mi0yY2VmYjMwNWZkYTAiLCJpYXQiOjE2MzI3MzI1NjUsIm5iZiI6MTYzMjczMjU2NSwiZXhwIjoxNjMyNzM2MTY1LCJpc3MiOiJodHRwczovL3d3dy5zYWFzYmVycnlsYWJzLmNvbSIsImF1ZCI6ImRlZmF1bHQifQ.zibWlduxMkLUk553yMBRsfn_vX1VKtdzB8JfXM44PpA";
		response = apiPage.post(apiRequestURL, body, expiredLoginToken);
		
		responseCode = apiPage.getResponseCode(response);
		Assert.assertEquals(responseCode, 401);
	}
	
	@Test(priority = 4, enabled = false, description = "Refund Payement | POST")
	@Description("Refund Payment.")
	@Feature("Feature : Refund Payment.")
	@Story("Story : Refund the payment to the customer with No authentication.")
	@Step("Refund the payment to the customer with No authentication.")
	@Severity(SeverityLevel.CRITICAL)
	public void refundPaymentNoAuthentication() {
		apiRequestURL = apiURL + "/api/Payment/refundpayment";
		map = ExcelUtils.getRowFromRowNumber(Constants.excelFileName, CreditCardSheet, Testcase3);
		map.put(CreditCard.ExternalID, externalID);
		map.put(CreditCard.Amount, "1");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		
		body = refundBody.getRefundPaymentBody(map);
		response = apiPage.post(apiRequestURL, body, null);
		
		responseCode = apiPage.getResponseCode(response);
		Assert.assertEquals(responseCode, 500);
	}
	
	@Test(priority = 5, enabled = false, description = "Refund Payement | POST")
	@Description("Refund Payment.")
	@Feature("Feature : Refund Payment.")
	@Story("Story : Refund the payment to the customer with Invalid External ID.")
	@Step("Refund the payment to the customer with Invalid External ID.")
	@Severity(SeverityLevel.CRITICAL)
	public void refundPaymentInvalidExternalID() {
		apiRequestURL = apiURL + "/api/Payment/refundpayment";
		map = ExcelUtils.getRowFromRowNumber(Constants.excelFileName, CreditCardSheet, Testcase3);
		map.put(CreditCard.ExternalID, externalID+"X");
		map.put(CreditCard.Amount, "1");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		
		body = refundBody.getRefundPaymentBody(map);
		response = apiPage.post(apiRequestURL, body, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		Assert.assertEquals(responseCode, 400);
		
		String bodyAsString = apiPage.getResponseBody(response);
		System.out.println("Response string : " + bodyAsString);
		
		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("message"), "ValidationFailed");
		Assert.assertEquals(obj.getJSONObject("dataexception").getJSONObject("data").get("externalid"), 
				CreditCard.invalidExternalIdValidationMessage);
	}
	
	@Test(priority = 6, enabled = true, description = "Refund Payement | POST")
	@Description("Refund Payment.")
	@Feature("Feature : Refund Payment.")
	@Story("Story : Refund the payment to the customer with Invalid Amount.")
	@Step("Refund the payment to the customer with Invalid Amount.")
	@Severity(SeverityLevel.CRITICAL)
	public void refundPaymentWithInvalidAmount() {
		apiRequestURL = apiURL + "/api/Payment/refundpayment";
		map.put(CreditCard.ExternalID, externalID);
		map.put(CreditCard.Amount, "0");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		
		body = refundBody.getRefundPaymentBody(map);
		response = apiPage.post(apiRequestURL, body, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		Assert.assertEquals(responseCode, 400);
		
		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("message"), "ValidationFailed");
		Assert.assertEquals(obj.getJSONObject("dataexception").getJSONObject("data").get("amount"), 
				CreditCard.negativeAmountValidationMessage);
	}
	
	@Test(priority = 7, enabled = true, description = "Refund Payement | POST")
	@Description("Refund Payment.")
	@Feature("Feature : Refund Payment.")
	@Story("Story : Refund Payment with invalid Amount.")
	@Step("Make a Refund Payment with invalid Amount.")
	@Severity(SeverityLevel.CRITICAL)
	public void refundPaymentWithInvalidNegativeAmount() {
		apiRequestURL = apiURL + "/api/Payment/refundpayment";
		map.put(CreditCard.ExternalID, externalID);
		map.put(CreditCard.Amount, "-11");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		
		body = refundBody.getRefundPaymentBody(map);
		response = apiPage.post(apiRequestURL, body, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		Assert.assertEquals(responseCode, 400);
		
		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("message"), "ValidationFailed");
		Assert.assertEquals(obj.getJSONObject("dataexception").getJSONObject("data").get("amount"), 
				CreditCard.negativeAmountValidationMessage);
	}
	
	@Test(priority = 8, enabled = true, description = "Refund Payement | POST")
	@Description("Refund Payment.")
	@Feature("Feature : Refund Payment.")
	@Story("Story : Refund Payment with more than actual Amount.")
	@Step("Make a Refund Payment with more than actual Amount.")
	@Severity(SeverityLevel.CRITICAL)
	public void refundPaymentWithMoreThanActualAmount() {
		apiRequestURL = apiURL + "/api/Payment/refundpayment";
		map.put(CreditCard.ExternalID, externalID);
		map.put(CreditCard.Amount, "1000");
		map.put(CreditCard.InvoiceNumber, "BAB123");
		
		body = refundBody.getRefundPaymentBody(map);
		response = apiPage.post(apiRequestURL, body, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		Assert.assertEquals(responseCode, 400);
		
		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("message"), "ValidationFailed");
		Assert.assertTrue(obj.getJSONObject("dataexception").getJSONObject("data").get("amount").toString()
				.contains(CreditCard.maximumAmountValidationMessage));
	}
	
}
