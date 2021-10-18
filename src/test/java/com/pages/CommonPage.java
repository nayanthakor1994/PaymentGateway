package com.pages;

import org.json.JSONObject;
import org.testng.Assert;

import com.base.BasePage;

import io.restassured.response.Response;

public class CommonPage extends BasePage {
	

	public String getLoginTokens(String apiRequestURL) {
		System.out.println("******Getting Login Tokens for API******");
		System.out.println(apiRequestURL);
		APIPages apiPage = new APIPages();
		Response response = apiPage.post(apiRequestURL, "");
		int responseCode = apiPage.getResponseCode(response);
		Assert.assertEquals(responseCode, 200);
		String bodyAsString = apiPage.getResponseBody(response);
		JSONObject obj = new JSONObject(bodyAsString);
		loginToken = obj.get("token").toString();
		System.out.println("Token string : " + loginToken);
		return loginToken;
	}
	
	public String getloginTokensURL(String apiURL) {
		System.out.println("******Getting API Login URL******");
		return apiURL + "/api/Token/createtoken";
	}
	
}
