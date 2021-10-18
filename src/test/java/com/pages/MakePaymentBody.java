package com.pages;

import java.util.Map;

import com.excel.CreditCard;

public class MakePaymentBody {

	public String getMakePaymentBody(Map<String, String> map) {
		String value = "{\"profileid\":\""+map.get(CreditCard.ProfileID)+"\",\"amount\":\""+map.get(CreditCard.Amount)+"\",\"invoicenumber\":\""+map.get(CreditCard.InvoiceNumber)+"\"}";
		return value;
	}
	
	public String getMakePaymentWithProfileBody(Map<String, String> map) {
		String value = "{\"vendorid\":\""+map.get(CreditCard.VendorID)+"\",\"firstname\":\""+map.get(CreditCard.Firstname)+"\",\"lastname\":\""+map.get(CreditCard.Lastname)+"\",\"nameoncard\":\""+map.get(CreditCard.NameOnCard)+"\",\"expirationmonth\":\""+map.get(CreditCard.ExpirationMonth)+"\",\"expirationyear\":\""+map.get(CreditCard.ExpirationYear)+"\",\"address1\":\""+map.get(CreditCard.Address1)+"\",\"city\":\""+map.get(CreditCard.City)+"\",\"province\":\""+map.get(CreditCard.Province)+"\",\"postalcode\":\""+map.get(CreditCard.Postalcode)+"\",\"countrycode\":\""+map.get(CreditCard.Country)+"\",\"phone\":\""+map.get(CreditCard.Phone)+"\",\"email\":\""+map.get(CreditCard.EmailID)+"\",\"cardnumber\":\""+map.get(CreditCard.CardNumber)+"\",\"cvvnumber\":\""+map.get(CreditCard.CVVnumber)+"\",\"amount\":\""+map.get(CreditCard.Amount)+"\"}";
		return value;
	}
	
}
