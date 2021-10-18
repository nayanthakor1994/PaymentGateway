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
public class CreditCardTest extends BasePage {
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
//		Constants.loginToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiIxMTMwNjIxNS05NjM4LTRkYjYtYjM4Mi0yY2VmYjMwNWZkYTAiLCJpYXQiOjE2MzI3MzI1NjUsIm5iZiI6MTYzMjczMjU2NSwiZXhwIjoxNjMyNzM2MTY1LCJpc3MiOiJodHRwczovL3d3dy5zYWFzYmVycnlsYWJzLmNvbSIsImF1ZCI6ImRlZmF1bHQifQ.zibWlduxMkLUk553yMBRsfn_vX1VKtdzB8JfXM44PpA";
	}

	@Test(priority = 1, enabled = true, description = "Add the credit card with valid details")
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
		creditCardID = obj.get("id").toString();
		profileID = obj.get("profileid").toString();
	}
	
	
	
	@Test(priority = 2, enabled = false, description = "Update Credit Card Details")
	@Description("Update the credit card with valid details.")
	@Feature("Feature : Update Credit Card")
	@Story("Story : Update the credit card with valid details.")
	@Step("Update Credit Card Details")
	@Severity(SeverityLevel.CRITICAL)
	public void updateCreditCard() {
		apiRequestURL = apiURL + "/api/Payment/updatecreditcard";
		map = ExcelUtils.getRowFromRowNumber(Constants.excelFileName, CreditCardSheet, Testcase2);
		map.put(CreditCard.ID, creditCardID);
		map.put(CreditCard.ProfileID, profileID);
		
		body = cardBody.getUpdateCreditCardBody(map);
		response = apiPage.put(apiRequestURL, body, loginToken);
		
		responseCode = apiPage.getResponseCode(response);
		String bodyAsString = apiPage.getResponseBody(response);
		System.out.println("Response string : " + bodyAsString);
		Assert.assertEquals(responseCode, 200);
		System.out.println("Response code : " + responseCode);
		
		JSONObject obj = new JSONObject(bodyAsString);
		Assert.assertEquals(obj.get("resultmessage").toString(), Success);
		Assert.assertEquals(obj.get("profileid").toString(), profileID);
	}
	
	
	@Test(priority = 3, enabled = true, description = "Get Credit Card Details By Profile ID")
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
		Assert.assertEquals(obj.get("name").toString(), map.get(CreditCard.NameOnCard));
	}
	
	@Test(priority = 4, enabled = false, description = "Make Payment Without Profile | POST")
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
	}
	
	
	@Test(priority = 5, enabled = true, description = "Make Payment With Profile | POST")
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
	
	
	@Test(priority = 6, enabled = true, description = "Refund Payement | POST")
	@Description("Refund Payment.")
	@Feature("Feature : Refund Payment.")
	@Story("Story : Refund the payment to the customer with valid details.")
	@Step("Refund the payment to the customer with valid details.")
	@Severity(SeverityLevel.CRITICAL)
	public void refundPayment() {
		apiRequestURL = apiURL + "/api/Payment/refundpayment";
		map = ExcelUtils.getRowFromRowNumber(Constants.excelFileName, CreditCardSheet, Testcase3);
		map.put(CreditCard.ExternalID, externalID);
		map.put(CreditCard.Amount, "500");
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

	
}
