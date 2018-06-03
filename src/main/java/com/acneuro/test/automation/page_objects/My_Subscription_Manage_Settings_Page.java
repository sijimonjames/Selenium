package com.acneuro.test.automation.page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.acneuro.test.automation.libraries.BaseClass;

/**
 * @author sijimon james
 * @Library Page Objects: subscriptions > Manage setting web element locations
 * 
 */

public class My_Subscription_Manage_Settings_Page extends BaseClass {

	public My_Subscription_Manage_Settings_Page(WebDriver driver) {
		super(driver);
	}

	@FindBy(how = How.XPATH, using = "//p[text() = 'Block internet']")
	public static WebElement manage_settings_Block_Internet;

	@FindBy(how = How.XPATH, using = "//p[text() = 'Block lost or stolen handset']")
	public static WebElement manage_settings_Block_Handset;

	@FindBy(how = How.XPATH, using = "//p[text() = 'Port your JOi number to your new operator']")
	public static WebElement manage_settings_Port_Out;

	@FindBy(how = How.XPATH, using = "//p[text() = 'Port your number to JOi']")
	public static WebElement manage_settings_Port_In;

	@FindBy(how = How.XPATH, using = "//p[text() = 'Replace your SIM']")
	public static WebElement manage_settings_SIM_Replacement;

}