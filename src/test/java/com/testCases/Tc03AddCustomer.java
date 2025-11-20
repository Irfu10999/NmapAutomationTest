package com.testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pageObjects.DashboardPage;
import com.pageObjects.LoginPage;
import com.testBase.BaseClass;

public class Tc03AddCustomer extends BaseClass
{

	@Test(groups = {"Sanity","Master"})
	public void verify_LoginwithValidCredentials()
	{
		logger.info("*********** Starting TC03_Adding the Customer ***********");
		try
		{
			LoginPage lp= new LoginPage(driver);
			logger.info("*********** Entering Login Data  ***********");
			lp.login(p.getProperty("email"),p.getProperty("password"));
			logger.info("*********** Logged In  ***********");
			DashboardPage dp= new DashboardPage(driver);
			dp.clickMyCustomersButton();
			dp.clickNewCustomerButton();
			dp.enterCustomerName(p.getProperty("newcustomername"));
			dp.clickSaveCustomer();
			
		}
		catch (Exception e) 
		{
			Assert.fail();
		}
		logger.info("*********** Finished TTC03_Adding the Customer ***********");
	}
}
