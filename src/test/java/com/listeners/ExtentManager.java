package com.listeners;

import java.io.File;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.base.BasePage;
 
public class ExtentManager extends BasePage{
	static ExtentReports extent;
	
	public static synchronized ExtentReports createInstance() {
		String reportPath = projectDirectory + "\\Reports\\latest\\";
		File reportFolder = new File(reportPath);
		if (!reportFolder.exists()) {
			reportFolder.mkdirs();
		}
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportPath + "\\extent.html");
        htmlReporter.config().setReportName("Automation Testing");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        return extent;
	}
	public static ExtentReports getInstance() {
		if(extent == null) {
			createInstance();
		}
		return extent;
	}
}
