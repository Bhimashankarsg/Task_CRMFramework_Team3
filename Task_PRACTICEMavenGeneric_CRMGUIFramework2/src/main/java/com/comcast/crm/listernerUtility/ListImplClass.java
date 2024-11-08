package com.comcast.crm.listernerUtility;

import java.util.Date;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.comcast.crm.basetest.BaseClass_practice;
import com.comcast.crm.generic.webdriverutility.UtilityClassObject;

public class ListImplClass implements ITestListener, ISuiteListener {
	
	public  ExtentReports report;
	public static ExtentTest test;

	@Override
	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
		// ISuiteListener.super.onStart(suite);

		Reporter.log("Report configuration");
		String time = new Date().toString().replace(" ", "_").replace(":", "_");
		// Spark report config
		ExtentSparkReporter spark = new ExtentSparkReporter("./AdanceReport_CRM/report"+time+".html");
		spark.config().setDocumentTitle("CRM Test Suite Results(Document Title)");
		spark.config().setReportName("CRM Report( Report Name)");
		String theme = "DARK";
		spark.config().setTheme(Theme.valueOf(theme));

		// add Envrionment info and create test
		report = new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("OS", "Windows-10");
		report.setSystemInfo("BROWSER", "CHROME-100");

	}

	@Override
	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		// ISuiteListener.super.onFinish(suite);
		Reporter.log("Report Backup");
		report.flush(); // For taking Backup - To save
	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		// ITestListener.super.onTestStart(result);

		Reporter.log("====>" + result.getMethod().getMethodName() + "<=======START=====");
		
		test=report.createTest(result.getMethod().getMethodName());
		UtilityClassObject.setTest(test);
		test.log(Status.PASS,result.getMethod().getMethodName()+"===> STARTED <===");
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		// ITestListener.super.onTestSuccess(result);

		Reporter.log("====>" + result.getMethod().getMethodName() + "<=======END=====");
		test.log(Status.PASS,result.getMethod().getMethodName()+"===> COMPLETED <===");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		// ITestListener.super.onTestFailure(result);
		String testName = result.getMethod().getMethodName();
		/*
		 * EventFiringWebDriver edriver=new
		 * EventFiringWebDriver(BaseClass_practice.sdriver);
		 * 
		 * File srcfile=edriver.getScreenshotAs(OutputType.FILE);
		 */

		TakesScreenshot ts = (TakesScreenshot) BaseClass_practice.sdriver;
		String filePAth = ts.getScreenshotAs(OutputType.BASE64);

		String time = new Date().toString().replace(" ", "_").replace(":", "_");

		/*
		 * try { FileUtils.copyFile(srcfile,new
		 * File("./screenshot/"+testName+"_"+time+".jpeg")); }catch(Exception e) {
		 * e.printStackTrace();
		 * 
		 * }
		 */

		test.addScreenCaptureFromBase64String(filePAth, testName+"_"+time);
		
		test.log(Status.FAIL,result.getMethod().getMethodName()+"===> FAILED <===");
test.log( Status.FAIL,result.getThrowable());
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		// ITestListener.super.onFinish(context);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		// ITestListener.super.onStart(context);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		// ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		// ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		// ITestListener.super.onTestSkipped(result);
	}

}