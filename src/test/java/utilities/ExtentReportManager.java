package utilities;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.testBase.BaseClass;

public class ExtentReportManager implements ITestListener 
{
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String repName;
	
	public void onStart(ITestContext testContenxt)
	{
		/*
		SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt=new Date();
		String currentDateTimeStamp=df.format(dt);
		*/
		
		// creating Time Stamp
		String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); 
		
		repName="Test-Report-"+timeStamp+".html";
		// 	Specify Location Of Report
		sparkReporter=new ExtentSparkReporter(".\\reports\\"+repName);
		//Title of Report
		sparkReporter.config().setDocumentTitle("Nimap Infotech Testing Report");
		// Name OF THe Report
		sparkReporter.config().setReportName("Nimap Infotech Functional Report");
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "NimapInfotech");// Application Name we Have to Add
		extent.setSystemInfo("Module", "User");  // Module Name Adding user/Admin/...
		extent.setSystemInfo("Sub Module", "Customers"); // Sub Module Names customer/owner/...
		extent.setSystemInfo("User Name",System.getProperty("user.name")); // Add Tester Name directly or else we can go through this part
		extent.setSystemInfo("Environment", "QA");  // Add Environment
		
		String os=testContenxt.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser=testContenxt.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> includeGroups=testContenxt.getCurrentXmlTest().getIncludedGroups();
		if(!includeGroups.isEmpty())
		{
			extent.setSystemInfo("Groups", includeGroups.toString());
		}
		
	}
	
	public void onTestSuccess(ITestResult result) 
	{
		//get the class name and enter the name in extent report
		test=extent.createTest(result.getTestClass().getName());
		// get the method name and group name and add to test report
		test.assignCategory(result.getMethod().getGroups());
		//printing the status
		test.log(Status.PASS,result.getName()+" got successfully executed");
	}
	
	public void onTestFailure(ITestResult result)
	{
		//get the class name and enter the name in extent report
		test=extent.createTest(result.getTestClass().getName());
		// get the method name and group name and add to test report
		test.assignCategory(result.getMethod().getGroups());
		//printing the error message and status
		test.log(Status.FAIL,result.getName()+" got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());   // why it got failed
		
		try
		{
			//capturing the screenshot and getting path of captured image
			String imgPath=new BaseClass().captureScreen(result.getName().toString());
			// attaching the image path to extent report
			test.addScreenCaptureFromPath(imgPath);
		}
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
	}
	
	public void onTestSkipped(ITestResult result)
	{
		//get the class name and enter the name in extent report
		test=extent.createTest(result.getTestClass().getName());
		// get the method name and group name and add to test report
		test.assignCategory(result.getMethod().getGroups());	
		//printing the status
		test.log(Status.SKIP,result.getName()+" got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage()); // why it got skipped
		
	}
	
	public void onFinish(ITestContext testContext)
	{
		extent.flush();  // consolidate all the information from the report and it will generate 
		
		String pathOfExtentReport=System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport=new File(pathOfExtentReport);
		//this code for open report automatically 
		try
		{
			//it will open report in browser
			Desktop.getDesktop().browse(extentReport.toURI());
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		

		
		
	}

}
