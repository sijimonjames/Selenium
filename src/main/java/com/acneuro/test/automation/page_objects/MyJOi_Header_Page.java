/**
 * 
 */
package com.acneuro.test.automation.page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.acneuro.test.automation.libraries.BaseClass;

/**
 * @author sijimon james
 * @Library Page Objects: Login page web element locations
 *
 */
public class MyJOi_Header_Page extends BaseClass{
	
	public MyJOi_Header_Page(WebDriver driver){
		super(driver);
	}
	
	@FindBy(how=How.ID, using="")
	public static WebElement remove_cookies_message;
	
	@FindBy(how=How.ID, using="")
	public static WebElement user_name_field;

}
