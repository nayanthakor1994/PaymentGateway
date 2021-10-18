package com.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.base.BasePage;


public class ExtentTestManager extends BasePage{


	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	private static ExtentReports extent = ExtentManager.getInstance();
	private static ThreadLocal<String> extentTestName = new ThreadLocal<String>();
	private static ThreadLocal<String> overrideExtentTestName = new ThreadLocal<String>();


	public synchronized static ExtentTest getTest() {
		return extentTest.get();
	}

	public synchronized static ExtentTest createTest(String name, String description, String category) {
		ExtentTest test = extent.createTest(name, description);

		if (category != null && !category.isEmpty())
			test.assignCategory(category);

		extentTest.set(test);

		return getTest();
	}

	public synchronized static ExtentTest createTest(String name, String description) {
		return createTest(name, description, null);
	}

	public synchronized static ExtentTest createTest(String name) {
		return createTest(name, null);
	}

	public synchronized static void log1(String message) {
		System.out.println(message);
//		getTest().info(message);
	}
	
	public synchronized static void fail(String message) {
		System.out.println("[FAILURE] " +message);
		getTest().fail(message);
	}
	
	public synchronized static void info(String message) {
		System.out.println("[INFO] "+message);
		getTest().info(message);
	}
	
	public synchronized static void pass(String message) {
		System.out.println("[PASS] "+message);
		getTest().pass(message);
	}
	
	public synchronized static void warning(String message) {
		System.out.println("[WARNING] "+message);
		getTest().warning(message);
//		try {
//			getTest().addScreenCaptureFromBase64String(getBase64ScreenshotString(getDriver()),message);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	public synchronized static void skip(String message) {
		System.out.println("[SKIPPING] "+message);
		getTest().skip(message);
	}

	public synchronized static String getTestName() {
		return extentTestName.get();
	}

	public synchronized static String setTestName(String testName) {
		extentTestName.set(testName);
		return getTestName();
	}

	public synchronized static String getOverrideTestName() {
		if(overrideExtentTestName.get() != null) {
			return overrideExtentTestName.get();
		}else {
			return getTestName();
		}
	}

	public synchronized static String setOverrideTestName(String testName) {
		overrideExtentTestName.set(testName);
		return getOverrideTestName();
	}

	
//	private synchronized static String getBase64ScreenshotString(WebDriver driver) throws IOException {
//		TakesScreenshot ts = (TakesScreenshot) driver;
//		File source = ts.getScreenshotAs(OutputType.FILE);
//
//		byte[] fileContent = FileUtils.readFileToByteArray(source);
//		String base64StringofScreenshot = "data:image/png;base64,"+Base64.getEncoder().encodeToString(fileContent);
//		return base64StringofScreenshot;
//	}
	
}


