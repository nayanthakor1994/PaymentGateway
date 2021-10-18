package com.base;

import java.io.File;

public class Constants {

	public static String projectDirectory = System.getProperty("user.dir");
	public static String excelFileName = File.separator + "Testdata" + File.separator
			+ "Testdata.xlsx";
	public static String loginToken = null;
	
	
	/**
	 *  Excel file names
	 */
	public static final String CreditCardSheet = "CreditCard";
	
	
	public static final String Success = "Success";
	
	/**
	 * 	Testcases names
	 */
	public static final String Testcase1 = "Testcase1";
	public static final String Testcase2 = "UpdateCC";
	public static final String Testcase3 = "PaymentWithProfile";
	public static final String Testcase4 = "PastData";
	public static final String Testcase5 = "InvalidCreditCard";
	public static final String Testcase6 = "InvalidCVV";
	
	
}
