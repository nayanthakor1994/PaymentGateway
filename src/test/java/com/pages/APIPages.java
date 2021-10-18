package com.pages;


import java.util.HashMap;
import java.util.Map;

import com.base.BasePage;
import com.listeners.ExtentTestManager;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIPages extends BasePage{

	RequestSpecification request;
	
	public Response get(String URL) {
		ExtentTestManager.log1("Requesting for GET method : " + URL);
		request = RestAssured.given();
		request.headers(getHeader());
		return request.get(URL);
	}
	
	public Response get(String URL, String loginTokens) {
		ExtentTestManager.log1("Requesting for GET method : " + URL);
		request = RestAssured.given();
		request.headers(getHeader(loginTokens));
		return request.get(URL);
	}
	
	public Response post(String URL, Map<String, Object> requestBody) {
		ExtentTestManager.log1("Requesting for POST method : " + URL);
		ExtentTestManager.log1("Request Body : " + requestBody);
		request = RestAssured.given();
		request.headers(getHeader());
		request.formParams(requestBody);
		return request.post(URL);
	}
	
	public Response post(String apiURL,String newBody) {
		ExtentTestManager.log1("Requesting for POST method : " + apiURL);
		ExtentTestManager.log1("Request Body : " + newBody);
		request = RestAssured.given();
		request.headers(getHeader());
		request.body(newBody);
		return request.post(apiURL);
	}
	
	public Response post(String apiURL,String newBody, String loginToken) {
		ExtentTestManager.log1("Requesting for POST method : " + apiURL);
		ExtentTestManager.log1("Request Body POST : " + newBody);
		ExtentTestManager.log1("LoginToken for API POST Request : " + loginToken);
		request = RestAssured.given();
		request.headers(getHeader(loginToken));
		request.body(newBody);
		return request.post(apiURL);
	}
	
	public Response put(String apiURL,String newBody, String loginToken) {
		ExtentTestManager.log1("Requesting for PUT method : " + apiURL);
		ExtentTestManager.log1("Request Body For PUT: " + newBody);
		ExtentTestManager.log1("LoginToken for API PUT Request : " + loginToken);
		request = RestAssured.given();
		request.headers(getHeader(loginToken));
		request.body(newBody);
		return request.put(apiURL);
	}
	
	public Map<String, Object> getHeader() {
		Map<String, Object> headerMap = new HashMap<String, Object>();
		headerMap.put("Content-Type", "application/json");
		headerMap.put("Accept", "*/*");
		return headerMap;
	}
	
	public Map<String, Object> getHeader(String loginTokens) {
		Map<String, Object> headerMap = new HashMap<String, Object>();
		headerMap.put("Content-Type", "application/json");
		if (loginTokens!=null)
			headerMap.put("Authorization", "Bearer " + loginTokens);
//		headerMap.put("Accept", "text/plain");
//		headerMap.put("Cookie", "ARRAffinity="+loginTokens+"; ARRAffinitySameSite="+loginTokens);
//		headerMap.put("Cookie", "ARRAffinity=aa3609a71c5ded8d6ad9807a67b9f65ee688c8b3c05bde8e0e9ef1d057a36aeb; ARRAffinitySameSite=aa3609a71c5ded8d6ad9807a67b9f65ee688c8b3c05bde8e0e9ef1d057a36aeb");
		ExtentTestManager.log1("Header for API Request : " + headerMap);
		return headerMap;
	}
	
	public int getResponseCode(Response response) {
		ExtentTestManager.log1("Response code is : " + response.getStatusCode());
		return response.getStatusCode();
	}

	public String getResponseBody(Response response) {
		ExtentTestManager.log1("Response body is : " + response.asString());
		return response.asString();
	}
}
