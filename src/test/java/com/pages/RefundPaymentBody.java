package com.pages;

import java.util.Map;

import com.excel.CreditCard;

public class RefundPaymentBody {
	
	public String getRefundPaymentBody(Map<String, String> map) {
		String value = "{\"externalid\":\""+map.get(CreditCard.ExternalID)+"\",\"amount\":"+map.get(CreditCard.Amount)+",\"invoicenumber\":\""+map.get(CreditCard.InvoiceNumber)+"\"}";
		return value;
	}

}
