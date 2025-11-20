package com.testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pageObjects.DashboardPage;
import com.pageObjects.LoginPage;
import com.testBase.BaseClass;

public class Tc02_PunchIn extends BaseClass
{
	@Test(groups = {"Sanity","Master"})
	public void verify_LoginwithValidCredentials()
	{
		logger.info("*********** Starting TC02_verifying_PunchIn  ***********");
		try
		{
			LoginPage lp= new LoginPage(driver);
			logger.info("*********** Entering Login Data  ***********");
			lp.login(p.getProperty("email"),p.getProperty("password"));
			logger.info("*********** Logged In  ***********");
			DashboardPage dp= new DashboardPage(driver);
			dp.clickPunchInButton();
			dp.enterPopupReport(p.getProperty("popupreporttext"));
			dp.clickDonePopupButton();
			
            boolean punchInEnabled = dp.isPunchInButtonEnabled();
			logger.info("*********** Checking Punch In Button status ***********");
	        logger.info("Punch In Button Enabled: " + punchInEnabled);
			
	        // Expecting false → disabled → SUCCESS
            Assert.assertFalse(punchInEnabled, "Punch In button is still enabled → FAIL");
		}
		catch (Exception e) 
		{
			  logger.error("Test failed due to exception: " + e.getMessage());
	            Assert.fail();
		}
		logger.info("*********** Finished TC_02_verifying_PunchIn ***********");
		
	}

}
