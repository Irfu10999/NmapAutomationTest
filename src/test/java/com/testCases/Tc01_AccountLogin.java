package com.testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pageObjects.DashboardPage;
import com.pageObjects.LoginPage;
import com.testBase.BaseClass;

public class Tc01_AccountLogin extends BaseClass
{
	
	@Test(groups = {"Sanity","Master"})
	public void verify_LoginwithValidCredentials()
	{
		logger.info("*********** Starting TC01_verifying_Login_test_WIth_Valid_Credentials ***********");
		try
		{
			LoginPage lp= new LoginPage(driver);
			logger.info("*********** Entering Login Data  ***********");
			lp.enterEmailId(p.getProperty("email"));
			lp.enterPassword(p.getProperty("password"));
			lp.clickSignInButton();
			logger.info("*********** Clicked on SignIn  ***********");
			DashboardPage dp= new DashboardPage(driver);
			boolean target=dp.isMyAccountLogged();
			Assert.assertTrue(target);
		}
		catch (Exception e) 
		{
			Assert.fail();
		}
		logger.info("*********** Finished TC_01_verifying_Login_test_WIth_Valid_Credentials ***********");
		
		
	}

}
