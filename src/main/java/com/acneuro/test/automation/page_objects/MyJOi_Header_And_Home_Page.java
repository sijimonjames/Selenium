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
public class MyJOi_Header_And_Home_Page extends BaseClass {

	public MyJOi_Header_And_Home_Page(WebDriver driver) {
		super(driver);
	}

	@FindBy(how = How.LINK_TEXT, using = "ORDER")
	public static WebElement Home_Page_Order_Button;

	
	@FindBy(how = How.LINK_TEXT, using = "LOGIN")
	public static WebElement Home_Page_Login_Button_UK;

	@FindBy(how = How.XPATH, using = "//a[contains(text(), 'JOi LIBERTY')][@class='button']")
	public static WebElement Home_Page_Order_JOi_Liberty;
	
	@FindBy(how = How.XPATH, using = "//nav[@class = 'main-menu']//a[contains(text(),'HOME')]")
	public static WebElement Header_Page_Home;
	
	@FindBy(how = How.XPATH, using = "//nav[@class = 'main-menu']//a[@href = '/uk/my-joi/overview']")
	public static WebElement Header_Page_MyJOi;
	
	@FindBy(how = How.XPATH, using = "//nav[@class = 'main-menu']//span[contains(text(),'OUR OFFER')]")
	public static WebElement Header_Page_Our_Offer;
	
	@FindBy(how = How.XPATH, using = "//nav[@class = 'main-menu']//a[contains (text(), 'SUPPORT')]")
	public static WebElement Header_Page_Support;

}
