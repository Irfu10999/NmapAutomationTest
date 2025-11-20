package com.testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.text.RandomStringGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass 
{


	public Properties p;
	public Logger logger;
	public static WebDriver driver;
	

	@BeforeClass(groups = {"Sanity", "Master", "Regression" })
	@Parameters({"os","browser"})
	 public void setup(String os, String br) throws IOException
	 {
		//Loading config.properties File
		FileReader fr=new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(fr);
		
		logger=LogManager.getLogger(this.getClass());
		
		switch(br.toLowerCase())
		{
		case "chrome" : driver =new ChromeDriver();break;
		case "firefox" : driver =new FirefoxDriver();break;
		case "edge" : driver =new EdgeDriver(); break;
		default : System.out.println("Invalid Browser Name...."); return	;
		}
		
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(p.getProperty("AppURL"));  // reading url from Property File
		driver.manage().window().maximize();
		 
	 }
	
	@AfterClass(groups = {"Sanity", "Master", "Regression" })
	 public void tearDown()
	 {
		 driver.quit();
	 }
	
	//================ ScreenShot Method on Failure of testCse ===========================
	
	public String captureScreen(String tname) throws IOException
	{
		//Create Time Stamp
		String 	timeStamp=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		// take Screenshot by TakeScreenshot 
		TakesScreenshot takeScreenshot=(TakesScreenshot) driver;
		File sourceFile=takeScreenshot.getScreenshotAs(OutputType.FILE);
		// creating File path with class Name and time stamp
		//String targetFilePath="/AutomationTestStore/screenshots/"+tname+"_"+timeStamp+".png";
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetFile=new File(targetFilePath);
		// rename
		sourceFile.renameTo(targetFile);
		
		return targetFilePath;
	}
	
	 public String generateRandomString(int length) 
	  {
	        // Create a RandomStringGenerator
	        RandomStringGenerator generator = new RandomStringGenerator.Builder()
	            .withinRange('a', 'z') // Specify the range for lowercase letters
	            .build();

	        // Generate and return the random string of the specified length
	        return generator.generate(length);
	   }
	  
	  public String randomNumeric()
	  {
		  Random random = new Random();
	        
	        // Generate a random number with 10 digits
	        StringBuilder phoneNumber = new StringBuilder();
	        // Ensure the first digit is not 0
	        phoneNumber.append(random.nextInt(9) + 1); // First digit (1-9)
	        
	        // Next 9 digits (0-9)
	        for (int i = 1; i < 10; i++) {
	            phoneNumber.append(random.nextInt(10)); // Add random digit
	        }
	        
	        return phoneNumber.toString();
	  }
	   
	  //=======================
	  
	  public String randomAlphaNumberic(int length)
	  {
		  // Create a RandomStringGenerator
	        RandomStringGenerator generator = new RandomStringGenerator.Builder()
	            .withinRange('a', 'z') // Specify the range for lowercase letters
	            .build();

	        String text=generator.generate(length);
	        // Generate and return the random string of the specified length
		  
	        Random random = new Random();
	        
	        // Generate a random number with 10 digits
	        StringBuilder phoneNumber = new StringBuilder();
	        // Ensure the first digit is not 0
	        phoneNumber.append(random.nextInt(9) + 1); // First digit (1-9)
	        
	        // Next 9 digits (0-9)
	        for (int i = 1; i < 10; i++) {
	            phoneNumber.append(random.nextInt(10)); // Add random digit
	        }
	        
	        String phoneNumberText =phoneNumber.toString();
	        return (text+phoneNumberText);
	  }
	
	

}
