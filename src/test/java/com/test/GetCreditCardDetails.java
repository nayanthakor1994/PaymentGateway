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
public class GetCreditCardDetails extends BasePage {
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
		Constants.loginToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiI0ODY5ZjRkZi0xZTdhLTQ5NmEtOTc3Ny0zZTQxOWUxNDU4YTQiLCJpYXQiOjE2MzM2MjY4NTksIm5iZiI6MTYzMzYyNjg1OSwiZXhwIjoxNjMzNjMwNDU5LCJpc3MiOiJodHRwczovL3d3dy5zYWFzYmVycnlsYWJzLmNvbSIsImF1ZCI6ImRlZmF1bHQifQ.jW_Y_vAKAPUDRGT6PKVF3CbvGCmjk6xfU5lUKiBfM0w";
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
//		creditCardID = obj.get("id").toString();
		Assert.assertEquals(obj.get("resultmessage"), Success);
		profileID = obj.get("profileid").toString();
	}
	
	@Test(priority = 2, enabled = false, description = "Get Credit Card Details By Profile ID")
	@Description("Get Credit Card Details By Profile ID")
	@Feature("Feature : Get Credit Card Details By Profile ID")
	@Story("Story : Update the credit card with valid details.")
	@Step("Update Credit Card Details")
	@Severity(SeverityLevel.CRITICAL)
	public void getCreditCardDetailsByProfileID() {
		apiRequestURL = apiURL + "/api/payment/getcreditcarddetailbyprofileid/" + profileID;
		response = apiPage.get(apiRequestURL, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		System.out.println("Response string : " + bodyAsString);
		Assert.assertEquals(responseCode, 200);
		System.out.println("Response code : " + responseCode);
		
		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("resultmessage"), Success);
		Assert.assertEquals(obj.get("profileid").toString(), profileID);
	}
	
	@Test(priority = 3, enabled = false, description = "Get Credit Card Details with expired authentication.")
	@Description("Get Credit Card Details with expired authentication.")
	@Feature("Feature : Get Credit Card Details with expired authentication.")
	@Story("Story : Update the credit card with expired authentication.")
	@Step("Update Credit Card Details with expired authentication.")
	@Severity(SeverityLevel.CRITICAL)
	public void getCreditCardDetailsExpiredAuthentication() {
		apiRequestURL = apiURL + "/api/payment/getcreditcarddetailbyprofileid/" + profileID;
		String expiredLoginToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiIxMTMwNjIxNS05NjM4LTRkYjYtYjM4Mi0yY2VmYjMwNWZkYTAiLCJpYXQiOjE2MzI3MzI1NjUsIm5iZiI6MTYzMjczMjU2NSwiZXhwIjoxNjMyNzM2MTY1LCJpc3MiOiJodHRwczovL3d3dy5zYWFzYmVycnlsYWJzLmNvbSIsImF1ZCI6ImRlZmF1bHQifQ.zibWlduxMkLUk553yMBRsfn_vX1VKtdzB8JfXM44PpA";
		response = apiPage.get(apiRequestURL, expiredLoginToken);
		
		responseCode = apiPage.getResponseCode(response);
		Assert.assertEquals(responseCode, 401);
	}
	
	@Test(priority = 4, enabled = false, description = "Get Credit Card Details with no authentication.")
	@Description("Get Credit Card Details with no authentication.")
	@Feature("Feature : Get Credit Card Details with no authentication.")
	@Story("Story : Update the credit card with no authentication.")
	@Step("Update Credit Card Details with no authentication.")
	@Severity(SeverityLevel.CRITICAL)
	public void getCreditCardDetailsNoAuthentication() {
		apiRequestURL = apiURL + "/api/payment/getcreditcarddetailbyprofileid/" + profileID;
		response = apiPage.get(apiRequestURL, null);
		
		responseCode = apiPage.getResponseCode(response);
		Assert.assertEquals(responseCode, 500);
	}
	
	@Test(priority = 5, enabled = false, description = "Get Credit Card Details with invalid profileid.")
	@Description("Get Credit Card Details with invalid profileid.")
	@Feature("Feature : Get Credit Card Details with invalid profileid.")
	@Story("Story : Update the credit card with invalid profileid.")
	@Step("Update Credit Card Details with invalid profileid.")
	@Severity(SeverityLevel.CRITICAL)
	public void getCreditCardDetailsByInvalidProfileID() {
		apiRequestURL = apiURL + "/api/payment/getcreditcarddetailbyprofileid/XX" + profileID;
		response = apiPage.get(apiRequestURL, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		System.out.println("Response string : " + bodyAsString);
		Assert.assertEquals(responseCode, 400);
		
		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("message"), "ValidationFailed");
		Assert.assertEquals(obj.getJSONObject("dataexception").getJSONObject("data").get("profileid"), 
				CreditCard.invalidProfileValidationMessage);
	}
	
	@Test(priority = 6, enabled = true, description = "Get Credit Card Details with no profileid.")
	@Description("Get Credit Card Details with no profileid.")
	@Feature("Feature : Get Credit Card Details with no profileid.")
	@Story("Story : Update the credit card with no profileid.")
	@Step("Update Credit Card Details with no profileid.")
	@Severity(SeverityLevel.CRITICAL)
	public void getCreditCardDetailsByNoProfileID() {
		apiRequestURL = apiURL + "/api/payment/getcreditcarddetailbyprofileid/";
		response = apiPage.get(apiRequestURL, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		Assert.assertEquals(responseCode, 404);
	}
		
}
