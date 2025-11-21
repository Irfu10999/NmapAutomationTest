package com.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage
{
	
	public LoginPage (WebDriver driver)
	{
		super (driver);
	}
	
	
	@FindBy(xpath = ".//input[@id='mat-input-0']")
	WebElement txtEmailId;
	
	
	@FindBy(xpath = ".//input[@id='mat-input-1']")
	WebElement txtPassword;
	
	@FindBy(xpath = ".//button[@id='kt_login_signin_submit']")
	WebElement btnSignIn;
	

	
	public void enterEmailId(String email) 
	{
	    enterText(txtEmailId, email);
	}

	public void enterPassword(String password) 
	{
	    enterText(txtPassword, password);
	}

	//  Click Methods 

	public void clickSignInButton() 
	{
	    clickElement(btnSignIn);
	}
	
	// Combined method
	
	public void login(String email, String password)
	{
	    enterEmailId(email);
	    enterPassword(password);
	    clickSignInButton();
	}
	

}
