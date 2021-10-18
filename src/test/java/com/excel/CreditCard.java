package com.excel;

public class CreditCard {

	public static String VendorID = "VendorID";
	public static String Firstname = "Firstname";
	public static String Lastname = "Lastname";
	public static String EmailID = "EmailID";
	public static String Phone = "Phone";
	public static String NameOnCard = "NameOnCard";
	public static String Address1 = "Address1";
	public static String Address2 = "Address2";
	public static String City = "City";
	public static String Province = "Province";
	public static String Postalcode = "Postalcode";
	public static String Country = "Country";
	public static String CardNumber = "CardNumber";
	public static String CVVnumber = "CVVnumber";
	public static String ExpirationMonth = "ExpirationMonth";
	public static String ExpirationYear = "ExpirationYear";
	public static String Description = "Description";
	public static String Company = "Company";

	
	public static String ID = "ID";
	public static String ProfileID = "ProfileID";
	public static String Amount = "Amount";
	public static String InvoiceNumber = "invoicenumber";
	public static String ExternalID = "externalid";
	
	public static String pastDateValidationMessage = "Invalid expiry month or year. It must be future date.";
	public static String invalidCardValidationMessage = "Invalid card number.";
	public static String invalidCVVValidationMessage = "CVV number must be 3 or 4 digits number.";
	public static String invalidProfileValidationMessage = "Invalid ProfileId. Please enter valid ProfileId.";
	public static String invalidAmountValidationMessage = "Amount must not be empty.";
	public static String negativeAmountValidationMessage = "Amount must be greater than 0.";
	public static String invalidExternalIdValidationMessage = "Invalid ExternalId. Please enter valid ExternalId.";
	public static String maximumAmountValidationMessage = "Maximum refund amount limit is ";
	
}
