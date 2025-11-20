package com.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage
{

	public DashboardPage (WebDriver driver)
	{
		super(driver);
	}

	
	@FindBy(xpath = ".//h3[text()='Dashboard']")
	WebElement dashboardText;
	
	@FindBy(xpath = ".//button/span[text()='Punch In']")
	WebElement btnPunchIn;
	
	
	@FindBy(xpath = ".//textarea[@id='mat-input-3']")
	WebElement txtPopupReport;
	
	@FindBy(xpath = ".//button/span[text()='Done']")
	WebElement btnDonePopup;
	
	@FindBy(xpath = ".//span[text()='My Customers']")
	WebElement btnMyCustomer;
	
	@FindBy(xpath = ".//button/span[text()=' New Customer ']")
	WebElement btnNewCustomer;
	
	//--------- New Customer Elements 
	
	
	@FindBy(xpath = ".//input[@id='mat-input-553']")
	WebElement txtCustomerName;
	
	@FindBy(xpath = ".//input[@id='mat-input-554']")
	WebElement txtReferenceNo;
	
	
	@FindBy(xpath = ".//button/span[text()=' Save ']")
	WebElement btnSave;

	// ------------------- Action Methods

    public String getDashboardText() {
        return getElementText(dashboardText);
    }

    public boolean isDashboardDisplayed() {
        return dashboardText.isDisplayed();
    }

    // Punch In
    public void clickPunchInButton() {
        clickElement(btnPunchIn);
    }

    public void enterPopupReport(String text) {
        enterText(txtPopupReport, text);
    }

    public void clickDonePopupButton() {
        clickElement(btnDonePopup);
    }

    // Navigate to My Customers
    public void clickMyCustomersButton() {
        clickElement(btnMyCustomer);
    }

    public void clickNewCustomerButton() {
        clickElement(btnNewCustomer);
    }

    // Add New Customer
    public void enterCustomerName(String custName) {
        enterText(txtCustomerName, custName);
    }

    public void enterReferenceNo(String refNo) {
        enterText(txtReferenceNo, refNo);
    }

    public void clickSaveCustomer() {
        clickElement(btnSave);
    }

    public void addNewCustomer(String name, String refNo) {
        enterCustomerName(name);
        enterReferenceNo(refNo);
        clickSaveCustomer();
    }

    //----------- Validating Methods
    
    
    public boolean isMyAccountLogged()
	{
		boolean status;
		try
		{
		if(dashboardText.isDisplayed())
		{
			status=true;
		}else 
		{
			status=false;
		}
		}catch (Exception e) 
		{
			status=false;
		}
		return status;
	}

    //Validate Punch-in button enabled state
    public boolean isPunchInButtonEnabled() 
    {
        try 
        {
            return btnPunchIn.isEnabled();
        } 
        catch (Exception e) 
        {
            return false;
        }
    }

}
