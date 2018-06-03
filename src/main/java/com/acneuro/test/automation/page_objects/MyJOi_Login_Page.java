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
public class MyJOi_Login_Page extends BaseClass {

	public MyJOi_Login_Page(WebDriver driver) {
		super(driver);
	}

	@FindBy(how = How.LINK_TEXT, using = "ORDER")
	public static WebElement order_button;

	@FindBy(how = How.LINK_TEXT, using = "LOGIN")
	public static WebElement login_button_only_for_uk;

	@FindBy(how = How.LINK_TEXT, using = "JOi LIBERTY 4G")
	public static WebElement joi_liberty_offer_from_home_page;
	
	@FindBy(how = How.LINK_TEXT, using = "JOi LIBERTY")
	public static WebElement joi_liberty_offer_from_home_page_Hippo;

}
