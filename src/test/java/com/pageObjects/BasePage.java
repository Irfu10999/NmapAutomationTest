package com.pageObjects;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage 
{


	WebDriver driver;
	
	public BasePage(WebDriver driver)
	{
		this.driver=driver;	
		PageFactory.initElements(driver, this);
	}
	
	public void clickElement(WebElement element) {
	    int attempts = 0;
	    WebDriverWait wait = null;
	    while (attempts < 3) {
	        try 
	        {
	            // 1. Wait for the element to be clickable
	        	wait= new WebDriverWait(driver, Duration.ofSeconds(10));
	            wait.until(ExpectedConditions.elementToBeClickable(element));
	            
	            // 2. Scroll to the element to bring it into view
	            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	            
	            // 3. Click the element
	            element.click();
	            break; // Exit loop if click is successful

	        } catch (ElementNotInteractableException e) 
	        {
	            System.out.println("Attempt " + (attempts + 1) + " - Element not interactable: " + e.getMessage());
	            
	            // 4. Try clicking using JavaScript as a fallback
	            try 
	            {
	                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	                break; // Exit loop if JavaScript click is successful
	            } catch (Exception jsException) 
	            {
	                System.out.println("JavaScript click also failed: " + jsException.getMessage());
	            }
	            
	            // 5. Wait for visibility as a final check
	            try 
	            {
	            	wait.until(ExpectedConditions.visibilityOf(element));
	                //wait.until(ExpectedConditions.visibilityOf(element));
	            } catch (TimeoutException timeoutException) 
	            {
	                System.out.println("Element not visible even after waiting: " + timeoutException.getMessage());
	            }

	            // 6. Wait before the next retry
	            try 
	            {
	                Thread.sleep(1000); // Wait for 1 second before retrying
	            } catch (InterruptedException ie) 
	            {
	                Thread.currentThread().interrupt();
	            }
	        }
	        attempts++;
	    }
	}

	//===================================================================================================================================
	
	public String getElementText(WebElement element) 
	{
	    try {
	        return element.getText();
	    } catch (Exception e) {
	        return e.getMessage();
	    }
	}
	
	//==================================================================================================================================
	
	public void enterText(WebElement element, String text) {
	    try {
	        element.clear(); // Clear any existing text
	        element.sendKeys(text); // Enter the new text
	    } catch (Exception e) {
	        System.out.println("Exception while entering text: " + e.getMessage());
	    }
	}
	
	//==================================================================================================================================
	
	
	
    // Common method to select an option from any dropdown
    public void selectOptionFromDropdown(WebElement dropdown, String option) 
    {
        try {
            Select select = new Select(dropdown);
            
            // First, try to select by visible text
            try 
            {
                select.selectByVisibleText(option);
                return; // Exit if successful
            } catch (NoSuchElementException e) 
            {
                System.out.println("Option not found by visible text: " + option);
            }

            // Then, try to select by value
            try {
                select.selectByValue(option); // Assuming 'option' holds the value
                return;
            } catch (NoSuchElementException e) {
                System.out.println("Option not found by value: " + option);
            }

            // Try selecting by index if needed
            int index = -1; 
            for (int i = 0; i < select.getOptions().size(); i++) 
            {
                if (select.getOptions().get(i).getText().trim().equals(option.trim())) 
                {
                    index = i;
                    break;
                }
            }

            if (index != -1) 
            {
                select.selectByIndex(index);
                return;
            } else {
                System.out.println("Option not found by index: " + option);
            }

            // Finally, try using JavaScript to select the option
            String script = "var select = arguments[0]; " +
                            "for (var i = 0; i < select.options.length; i++) { " +
                            "    if (select.options[i].text.trim() === arguments[1]) { " +
                            "        select.selectedIndex = i; " +
                            "        break; " +
                            "    } " +
                            "}";
            ((JavascriptExecutor) driver).executeScript(script, dropdown, option);

        } catch (Exception e) {
            System.out.println("Exception while selecting option: " + e.getMessage());
        }
    }
	
	

}
