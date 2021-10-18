package com.listeners;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.base.BasePage;
import com.opencsv.CSVWriter;

public class TestListeners extends BasePage implements ITestListener, IInvokedMethodListener{
	private static ExtentReports extent = ExtentManager.getInstance();
	int extentPassCount = 0;
	int extentFailCount = 0;
	int extentTestTotal = 0;
	int extentTestSkipped = 0;
	
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub
		
	}

	
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub
		
	}

	
	public void onTestStart(ITestResult result) {
		try {
			log("*****"+ result.getMethod().getMethodName() + " Started......");
			ExtentTestManager.setOverrideTestName(result.getMethod().getMethodName());
			ExtentTestManager.createTest(ExtentTestManager.getOverrideTestName());
			ExtentTestManager.info(result.getMethod().getMethodName() + " Started...");
			extentPassCount++;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	public void onTestSuccess(ITestResult result) {
		log("*****"+ result.getMethod().getMethodName() + " Passed...");
	}

	
	public void onTestFailure(ITestResult result) {
		try {
			log("*****"+ result.getMethod().getMethodName()+ " failed because of "+ result.getThrowable());
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			ExtentTestManager.fail(result.getMethod().getMethodName()+ " failed because of "+ result.getThrowable());
		}catch (Exception e) {
			e.printStackTrace();
		}
		extentFailCount++;
//		WebDriver driverWithFailure = BasePage.getDriver();
//		try {
//			ExtentTestManager.info("Adding screen capture...");
//			ExtentTestManager.getTest().addScreenCaptureFromBase64String(getBase64ScreenshotString(driverWithFailure), result.getMethod().getMethodName());
//		} catch (Exception e) {
//			e.printStackTrace();
//			ExtentTestManager.fail("Failed to get screenshot");
//		}
	}

	
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	
	public void onFinish(ITestContext context) {
		List<String[]> dataLines = new ArrayList<String[]>();
		extentTestTotal = extentFailCount+extentPassCount+extentTestSkipped;
		dataLines.add(new String[] 
				{ context.getCurrentXmlTest().getName(),
						Integer.toString(extentTestTotal),
						Integer.toString(extentPassCount), 
						Integer.toString(extentFailCount),
						Integer.toString(extentTestSkipped)});
		String destPath = projectDirectory + "\\Reports\\csv\\"+context.getCurrentXmlTest().getName()+".csv";
		writeDataAtOnce(destPath, dataLines);
		extent.flush();	
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh-mm a");
		Date date = new Date();
		String currentDate = sdf.format(date);
		try {
			Path copiedFile = Files.move(Paths.get(projectDirectory + "\\Reports\\latest\\extent.html"),
					Paths.get(System.getProperty("user.dir")+"\\Reports\\latest\\"+context.getCurrentXmlTest().getName() + " " + currentDate +".html"),
					StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Info: copied file exits: " + Files.exists(copiedFile));
		}catch (Exception e) {
			System.out.println("--------------------Failure:---------------\\nFailed to copy the file to target folder"); 
		}

//		Path temp;
//		try {
//			temp = Files.move 
//					(Paths.get(projectDirectory + "\\Reports\\extent.html"),  
//							Paths.get(projectDirectory + "\\Reports\\latest\\"+context.getCurrentXmlTest().getName() + " " + currentDate +".html"));
//			if(temp != null) 
//			{ 
//				System.out.println("Info: File renamed to " +context.getCurrentXmlTest().getName() +" and moved successfully"); 
//			} 
//			else
//			{ 
//				System.out.println("--------------------Failure:---------------\nFailed to move the file"); 
//			} 
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
	}

//	public synchronized String getScreenShotPath(String TestCaseName,WebDriver driver) throws IOException {
//		TakesScreenshot ts = (TakesScreenshot) driver;
//		File source = ts.getScreenshotAs(OutputType.FILE);
//
//		LocalDateTime now = LocalDateTime.now();
//		int minute = now.getMinute();
//		int second = now.getSecond();
//		int nanosecond = now.getNano();
//
//		String destPath = System.getProperty("user.dir")+"/target/Screenshots/" + TestCaseName+minute+second+nanosecond+".png";
//		File file = new File(destPath);
//		FileUtils.copyFile(source, file);
//		return destPath;
//	}
	
//	public synchronized String getBase64ScreenshotString(WebDriver driver) throws IOException {
//		TakesScreenshot ts = (TakesScreenshot) driver;
//		File source = ts.getScreenshotAs(OutputType.FILE);
//
//		byte[] fileContent = FileUtils.readFileToByteArray(source);
//		String base64StringofScreenshot = "data:image/png;base64,"+Base64.getEncoder().encodeToString(fileContent);
//		return base64StringofScreenshot;
//	}
	
	public void writeDataAtOnce(String filePath,List<String[]> dataResults) 
	{ 
		File file = new File(filePath); 
		try { 
			FileWriter outputfile = new FileWriter(file); 
			CSVWriter writer = new CSVWriter(outputfile); 
			writer.writeAll(dataResults); 
			writer.close(); 
		} 
		catch (IOException e) { 
			e.printStackTrace(); 
		} 
	} 
	
}
