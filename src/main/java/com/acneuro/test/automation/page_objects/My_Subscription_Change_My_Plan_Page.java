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

public class My_Subscription_Change_My_Plan_Page extends BaseClass {

	public My_Subscription_Change_My_Plan_Page(WebDriver driver) {
		super(driver);
	}

	@FindBy(how = How.XPATH, using = "//a[@data-target='tab-UPDATES']")
	public static WebElement change_my_plan_tab;

	// @FindBy(how = How.XPATH, using = "//*[contains(text(), 'Base Plan (400
	// Minutes, 400 Texts, 400 MB)')]")
	@FindBy(how = How.XPATH, using = "//input[@data-label = 'Base Plan (400 Minutes, 400 Texts, 400 MB)']")
	public static WebElement our_offer_Base_Plan;

	@FindBy(how = How.XPATH, using = "//p/b[text() = '400 MB']")
	public static WebElement our_offer_flex_default_data;

	@FindBy(how = How.XPATH, using = "//p/b[text() = 'Boost to 3000 MB']")
	public static WebElement our_offer_flex_boost_data;

	@FindBy(how = How.XPATH, using = "//p/b[text() = '400 Texts']")
	public static WebElement our_offer_default_text;

	@FindBy(how = How.XPATH, using = "//p/b[text() = 'Boost to Unlimited Texts']")
	public static WebElement our_offer_flex_boost_text;

	@FindBy(how = How.XPATH, using = "//p[contains (. , 'JOi Small')]")
	public static WebElement our_offer_UK_JOi_Small;

	@FindBy(how = How.XPATH, using = "//p[contains (. , 'JOi Medium')]")
	public static WebElement our_offer_UK_JOi_Medium;

	@FindBy(how = How.XPATH, using = "//input[contains(@value, 'CONTINUE')]")
	public static WebElement change_my_plan_continue_button;
	
	
	@FindBy(how = How.XPATH, using = "//input[contains(@value, 'SUBMIT PLAN AND MONTHLY PAYMENT CHANGE')]")
	public static WebElement change_my_plan_submit_plan_and_monthly_payment_change_button;
	
	@FindBy(how = How.ID, using = "confirmcancel")
	public static WebElement change_my_plan_cancel_button;
	
	@FindBy(how = How.XPATH, using = "//input[@id = 'confirmconfirm']")
	public static WebElement change_my_plan_confirm_button;
}
