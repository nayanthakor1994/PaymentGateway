package com.pages;

import java.util.Map;

import com.excel.CreditCard;

public class CreditCardAPIBody {

	public String getAddCreditCardBody(Map<String, String> map) {
		String value = "{\"vendorid\":\""+map.get(CreditCard.VendorID)+"\",\"firstname\":\""+map.get(CreditCard.Firstname)+"\",\"lastname\":\""+map.get(CreditCard.Lastname)+"\",\"nameoncard\":\""+map.get(CreditCard.NameOnCard)+"\",\"expirationmonth\":\""+map.get(CreditCard.ExpirationMonth)+"\",\"expirationyear\":\""+map.get(CreditCard.ExpirationYear)+"\",\"address1\":\""+map.get(CreditCard.Address1)+"\",\"address2\":\""+map.get(CreditCard.Address2)+"\",\"city\":\""+map.get(CreditCard.City)+"\",\"province\":\""+map.get(CreditCard.Province)+"\",\"postalcode\":\""+map.get(CreditCard.Postalcode)+"\",\"countrycode\":\""+map.get(CreditCard.Country)+"\",\"phone\":\""+map.get(CreditCard.Phone)+"\",\"email\":\""+map.get(CreditCard.EmailID)+"\",\"cardnumber\":\""+map.get(CreditCard.CardNumber)+"\",\"cvvnumber\":\""+map.get(CreditCard.CVVnumber)+"\",\"description\":\""+map.get(CreditCard.Description)+"\",\"company\":\""+map.get(CreditCard.Company)+"\"}";
		return value;
	}
	
	
	public String getUpdateCreditCardBodyOLD(Map<String, String> map) {
		String value = "{\"profileid\":\""+map.get(CreditCard.ProfileID)+"\",\"nameoncard\":\""+map.get(CreditCard.NameOnCard)+"\",\"expirationmonth\":\""+map.get(CreditCard.ExpirationMonth)+"\",\"expirationyear\":\""+map.get(CreditCard.ExpirationYear)+"\",\"cardnumber\":\""+map.get(CreditCard.CardNumber)+"\",\"cvvnumber\":\""+map.get(CreditCard.CVVnumber)+"\"}";
		return value;
	}
	public String getUpdateCreditCardBody(Map<String, String> map) {
		String value ="{\"profileid\":\""+map.get(CreditCard.ProfileID)+"\",\"firstname\":\""+map.get(CreditCard.Firstname)+"\",\"lastname\":\""+map.get(CreditCard.Lastname)+"\",\"nameoncard\":\""+map.get(CreditCard.NameOnCard)+"\",\"expirationmonth\":\""+map.get(CreditCard.ExpirationMonth)+"\",\"expirationyear\":\""+map.get(CreditCard.ExpirationYear)+"\",\"cardnumber\":\""+map.get(CreditCard.CardNumber)+"\",\"cvvnumber\":\""+map.get(CreditCard.CVVnumber)+"\"}"; 
		return value;
	}
	
}
