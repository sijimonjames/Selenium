package com.acneuro.test.automation.mvno_project_library;

import static com.acneuro.test.automation.db_connection_libraries.OracleDBUtils.*;
import static java.lang.Thread.sleep;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.acneuro.test.automation.db_connection_libraries.OracleDBUtils;
import com.acneuro.test.automation.db_connection_libraries.OracleJdbcConnection;
import com.acneuro.test.automation.libraries.ConfigLib;
import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.libraries.Generic;
import com.acneuro.test.automation.libraries.RestApiAutomation;
import com.acneuro.test.automation.libraries.RioGenerator;
import com.acneuro.test.automation.page_objects.My_Subscription_Change_My_Plan_Page;

public class MVNOProjectSpecific extends ConfigLib {

	public static void hideCookieMessage() {

		WebElement message = driver.findElement(By.xpath("//*[@id='cookiemyjoi']/div/a"));
		if (message.isDisplayed()) {
			message.click();
		}
	}

	// This is a generic method to verify a text present in the page
	public static String getAndVerifyText(String identifier) {
		String Text = driver.findElement(By.xpath(identifier)).getText();
		return Text;
	}

	public static void verifyTitleOfPage(String expectedTitle) {
		String actualTitle = driver.getTitle();
		Assert.assertEquals(expectedTitle, actualTitle);
		if (actualTitle.equals(expectedTitle)) {
			Reporter.log("PASS:MyJOi Page " + expectedTitle + " is as expected", true);

		} else {
			Reporter.log("FAIL: My Joi Page " + expectedTitle + " is NOT as expected", true);
		}
	}

	public static void scrollDownToView(WebElement element) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", element);
			sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void scrollWebPage(WebElement element) {

		Locatable hoverItem = (Locatable) element;
		int y = hoverItem.getCoordinates().onPage().getY();
		((JavascriptExecutor) driver).executeScript("window.scroll(0," + y + ");");
		return;
	}

	public static void takeScreenShot(String File_Location) {
		try {
			sleep(2000);
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			sleep(2000);
			// File Location: .//ScreenShots
			FileUtils.copyFile(scrFile, new File(File_Location));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static WebElement waitForAnElement(WebElement element) {

		new WebDriverWait(driver, 60).pollingEvery(1, TimeUnit.SECONDS).until(ExpectedConditions.visibilityOf(element));
		return element;
	}

	public static void orderCreationPageInEnglish() {
		// Click on 'Order' button
		driver.findElement(By.xpath("//a [text()='ORDER']")).click();
	}

	public static void JOi_LIBERTY_creation_page() {
		// Click on 'Joi Liberty' button
		driver.findElement(By.xpath("//a [text()='JOi LIBERTY']")).click();
	}

	// Select FR/Joi Liberty UK/Joi Liberty FR/Egalitte FR
	public static void pricePlanSelection_France_English(String pricePlanName) {

		try {
			// check the radio button 'Joi Liberty Fr'
			if (pricePlanName.equalsIgnoreCase("joiLibertyFR")) {

				driver.findElement(By.xpath("//label/p/b[contains(text(), 'JOi Liberty')]")).click();

			}
			if (pricePlanName.equalsIgnoreCase("joiEgalitteFR")) {
				// check the radio button 'Joi Egalitte Fr'
				driver.findElement(By.xpath("//label/p/b[contains(text(), 'JOi Equality')]")).click();
			}

			if (pricePlanName.equalsIgnoreCase("joiFraternityFR")) {
				// check the radio button 'Joi Fraternity Fr'

				driver.findElement(By.xpath("//label/p/b[contains(text(), 'JOi Fraternity')]")).click();
			}
			if (pricePlanName.equalsIgnoreCase("joiUnityFR")) {
				// check the radio button 'Joi Unity Fr'

				driver.findElement(By.xpath("//label/p/b[contains(text(), 'JOi Unity')]")).click();
			}

			if (pricePlanName.equalsIgnoreCase("joiBigFR")) {
				// check the radio button 'Joi Big Fr'

				driver.findElement(By.xpath("//label/p/b[contains(text(), 'JOi Big')]")).click();
			}

			sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void pricePlanSelection_France_French(String pricePlanName) {

		try {
			// check the radio button 'Joi Liberty Fr'
			if (pricePlanName.equalsIgnoreCase("joiLibertyFR")) {

				driver.findElement(By.xpath("//label/p/b[contains(text(), 'JOi Libert�')]")).click();

			}
			if (pricePlanName.equalsIgnoreCase("joiEgalitteFR")) {
				// check the radio button 'Joi Egalitte Fr'
				driver.findElement(By.xpath("//label/p/b[contains(text(), 'JOi �galit�')]")).click();
			}

			if (pricePlanName.equalsIgnoreCase("joiFraternityFR")) {
				// check the radio button 'Joi Fraternity Fr'

				driver.findElement(By.xpath("//label/p/b[contains(text(), 'JOi Fraternit�')]")).click();
			}
			if (pricePlanName.equalsIgnoreCase("joiUnityFR")) {
				// check the radio button 'Joi Unity Fr'

				driver.findElement(By.xpath("//label/p/b[contains(text(), 'JOi Unit�')]")).click();
			}

			if (pricePlanName.equalsIgnoreCase("joiBigFR")) {
				// check the radio button 'Joi Big Fr'

				driver.findElement(By.xpath("//label/p/b[contains(text(), 'JOi Grande')]")).click();
			}

			sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void select_300_MB_For_Unknown_Talkplan() {
		// check the radio button 'Joi Liberty Fr'

		WebElement elementMbSelection = driver
				.findElement(By.xpath("//*[@id='FRMV1407OF29']/section[2]/div[3]/label[1]/p/b"));

		elementMbSelection.click();

	}

	public static void select_3000_MB_For_Unknown_Talkplan() {
		// check the radio button 'Joi Liberty Fr'

		WebElement elementMbSelection = driver
				.findElement(By.xpath("//*[@id='FRMV1407OF29']/section[2]/div[3]/label[1]/p/b"));

		elementMbSelection.click();

	}

	// Select FR/Joi Liberty UK/Joi Liberty FR/Egalitte FR
	private static void pricePlanSelectionForFrenchLangauge(String pricePlanName) {
		// check the radio button 'Joi Liberty Fr'
		if (pricePlanName.equalsIgnoreCase("joiLibertyFR")) {
			// driver.findElement(By.xpath("//div[2]/label[1]/p[1]/b")).click();

			WebElement JoiLiberty = driver.findElement(By.xpath("//label/p/b[contains(text(), 'JOi Libert�')]"));
			if (JoiLiberty.isEnabled()) {
				System.err.println("Joi Liberty is selected");
			} else {
				JoiLiberty.click();
			}
		}
		if (pricePlanName.equalsIgnoreCase("joiEgalitteFR")) {

			WebElement JoiEgalitte = driver.findElement(By.xpath("//label/p/b[contains(text(), 'JOi �galit�')]"));

			if (JoiEgalitte.isEnabled()) {
				System.err.println("Joi Egalitte is selected");
			} else {
				JoiEgalitte.click();
			}
		}
	}

	public static void joiLibertyFrenchPricePlan() {
		pricePlanSelectionForFrenchLangauge("joiLibertyFR");
	}

	public static void joiEgalitteFrenchPricePlan() {
		pricePlanSelectionForFrenchLangauge("joiEgalitteFR");
	}

	public static void VariousFeesForPricePlans(String pricePlanFee, String ConnFee, String totalAmount) {

		try {
			sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String JOiPricePalnFee = "";
		String connectionFee = "";
		String amountToBePaid = "";

		try {
			sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Fee for Joi Libery
		String getJOiLibertyFee = MVNOProjectSpecific.getAndVerifyText("//*[@id='receipt']/ul/li[1]/ul/li[3]/span");
		JOiPricePalnFee = getJOiLibertyFee.replaceAll("\\s+", " ").trim();
		System.out.println("JOi Liberty Fee: " + JOiPricePalnFee);
		Assert.assertEquals(JOiPricePalnFee, pricePlanFee);

		// Fee for Connection
		String getConnectionFee = getAndVerifyText("//*[@id='receipt']/ul/li[2]/ul/li[3]/span");
		connectionFee = getConnectionFee.replaceAll("\\s+", " ").trim();
		System.out.println("Connection Fee: " + connectionFee);
		Assert.assertEquals(connectionFee, ConnFee);

		// Total amount to be paid
		String getAmountToBePaid = getAndVerifyText("//*[@id='receipt']/ul/li[2]/div/p/span");
		amountToBePaid = getAmountToBePaid.replaceAll("\\s+", " ").trim();
		System.out.println("Total amount to be paid for Liberty Plan: " + amountToBePaid);
		Assert.assertEquals(amountToBePaid, totalAmount);
	}

	public static void verifyPricePlanAmountInDatacashPage(String DATACASH_PRICE) {

		try {
			sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Total amount to be paid
		String getAmountToBePaidInDatacah = getAndVerifyText("//*[@id='payment-form']/tbody/tr[2]/td/p/strong");
		System.out.println("Total amount to be paid for Liberty Plan: " + getAmountToBePaidInDatacah);
		Assert.assertEquals(getAmountToBePaidInDatacah, DATACASH_PRICE);
	}

	// Reqired only for France
	public static void SimTypeSelectionForPricePlan(String SIMType, String pricePlan) {
		WebElement scrollElement = driver.findElement(By.xpath("//div/article/form/section/div[2]/label[1]"));
		scrollDownToView(scrollElement);
		// Select "Combi"
		if (SIMType.equalsIgnoreCase("combi") && pricePlan.equalsIgnoreCase("liberty")) {
			driver.findElement(By.xpath("//*[@id='FRMV1504OF60']/section/div[3]/label[1]/p/b")).click();
		}
		if (SIMType.equalsIgnoreCase("combi") && pricePlan.equalsIgnoreCase("egalitte")) {
			driver.findElement(By.xpath("//*[@id='FRMV1509OF66']/section/div[3]/label[1]/p/b")).click();
		}
		if (SIMType.equalsIgnoreCase("nano")) {
			driver.findElement(By.xpath("//div[1]/section/div[3]/label[2]/p/b")).click();
		}
	}

	// Only valid for France // move to France specific
	public static void ourOfferPageAddToBasketButton() {
		// work for all 'Add To Basket' cases
		WebElement addToBasketButton = driver.findElement(By.xpath("//input[6]"));
		boolean verifyAddToBasketButton = driver.findElement(By.xpath("//input[6]")).isEnabled();
		// Assert.assertTrue(addToBasketButton);

		if (verifyAddToBasketButton == false) {
			// wait for 60 sec but check every 1 sec
			waitForAnElement(addToBasketButton);
		}

		driver.findElement(By.xpath("//input[6]")).click();
	}

	public static void add_to_basket_without_porting() {
		// work for all 'Add To Basket' cases

		WebElement element = driver.findElement(By.xpath("//div/input[5]"));

		// MVNOProjectSpecific.scrollDownToView(element);
		element.click();
	}

	public static void addToBasketWithPortin() {

		// Click the radio Button for Port IN

		WebElement element = driver.findElement(By.cssSelector("label[class='select-bar checkbox']"));
		element.click();
		MVNOProjectSpecific.scrollDownToView(element);
		// Click on Add to Basket Button
		driver.findElement(By.xpath("//div/input[5]")).click();
	}

	public static void checkOutOrder() {
		// Order check out button click
		driver.findElement(By.xpath("//div/article/section/div[2]/a")).click();
	}

	public static void Customer_details(String salutation, String First_Name, String Middle_Name, String Last_Name,
			String DOB, String Phone) {
		// drop down selection
		try {
			WebElement ddList = driver.findElement(By.xpath("//*[@id='page1']/fieldset[1]/div/select"));
			Select s = new Select(ddList);
			s.selectByValue(salutation);
			// First name
			driver.findElement(By.name("personal-details-fieldset__first-name")).sendKeys(First_Name);
			driver.findElement(By.name("personal-details-fieldset__prefix")).sendKeys(Middle_Name);
			driver.findElement(By.name("personal-details-fieldset__last-name")).sendKeys(Last_Name);
			// DOB field need a click before send the value
			driver.findElement(By.name("personal-details-fieldset__date-of-birth")).click();
			sleep(500);
			driver.findElement(By.name("personal-details-fieldset__date-of-birth")).sendKeys(DOB);
			sleep(500);
			driver.findElement(By.name("personal-details-fieldset__phone-number")).sendKeys(Phone);
		} catch (Exception e) {
			new Exception("Customer_details field has been failed");
			e.printStackTrace();
		}
	}

	public static void contactAndBillingAdress(String zipcode, String street, String addressValue) {

		try {
			// post code
			driver.findElement(By.name("addval__zip")).sendKeys(zipcode);
			// Street
			driver.findElement(By.name("addval__street")).sendKeys(street);
			// Click to search address
			WebElement element = driver
					.findElement(By.cssSelector("input[class='addvalValidateButton call-to-action button']"));
			element.click();
			// List of Addresses
			sleep(Constant.DEFAULT_SLEEP_TIME);
			WebElement addressList = driver.findElement(By.xpath("//div[2]/fieldset[2]/div/select"));
			Select select = new Select(addressList);

			select.selectByValue(addressValue);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// Click on create account button by scroll down the page to make the
		// element into view
		try {

			sleep(1000);

			WebElement createAccount = driver.findElement(By.xpath("//div/article/form/section[1]/input"));

			if ((createAccount.isEnabled()) == true) {
				// MVNOProjectSpecific.scrollDownToView(createAccount);
				createAccount.click();
				sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
			} else {
				System.err.println("The account creation button is not enabled");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void continueButtonAfterAccountCreation() {
		// post code
		WebElement continueButton = driver.findElement(By.xpath("//section/div/article/section/a"));
		MVNOProjectSpecific.scrollDownToView(continueButton);
		continueButton.click();
	}

	public static void continueToStep3WithoutPorting() {
		// Click to next page from step 3
		WebElement continueStep3 = driver.findElement(By.xpath("//form/dl/dd[2]/div/section/a[1]"));
		MVNOProjectSpecific.scrollDownToView(continueStep3);
		continueStep3.click();
	}

	public static void continueToStep3WithPortin() {
		// Click to next page from step 3

		try {
			String uniquePortinNumber = franceUniquePortInNumber();

			sleep(500);

			System.out.println("New Portin number: " + uniquePortinNumber + "");
			String rioCode = RioGenerator.generateRio(Constant.NUMPOS_OP_CODE_1,
					Constant.NUMPOS_DEFAULT_RIO_CODE.substring(2, 3), Constant.NUMPOS_DEFAULT_RIO_CODE.substring(3, 9),
					uniquePortinNumber);
			Reporter.log("RIO Code: " + rioCode);
			sleep(500);
			driver.findElement(By.cssSelector("input[name = 'number-porting__currentNumber']"))
					.sendKeys(uniquePortinNumber);
			driver.findElement(By.cssSelector("input[name = 'number-porting__riocode']")).sendKeys(rioCode);
			driver.findElement(By.cssSelector("label[class = 'select-bar checkbox cancel-current-subscription']"))
					.click();
			WebElement continueStep3 = driver.findElement(By.xpath("//form/dl/dd[2]/div/section/a[1]"));
			MVNOProjectSpecific.scrollDownToView(continueStep3);
			continueStep3.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void franceBankDetailsAndClickToContinue(String accountNumberInIban, String BIC) {
		driver.findElement(By.name("bank-account__iban")).sendKeys(accountNumberInIban);
		driver.findElement(By.name("bank-account__bic")).sendKeys(BIC);

		WebElement continueToNext = driver.findElement(By.xpath("//dl/dd[3]/div/section/a[1]"));
		MVNOProjectSpecific.scrollDownToView(continueToNext);
		continueToNext.click();

	}

	public static void orderSummaryPageForNewCustomer(String Rep_ID) {
		try {
			WebElement elementToScroll = driver
					.findElement(By.xpath("//*[@id='panel3']/fieldset[6]/div/section/div/div[1]/p"));
			scrollDownToView(elementToScroll);
			sleep(1000);
			scrollDownToView(elementToScroll);
			sleep(1000);
			WebElement MarkAs = driver.findElement(By.xpath("//*[@id='panel3']/fieldset[9]/label"));
			int Width = MarkAs.getSize().width;
			int Height = MarkAs.getSize().height;
			int MyX = (Width * 50) / 100;// spot to click is at 95% of the width
			int MyY = Height / 2;// anywhere above Height/2 works
			Actions Actions = new Actions(driver);
			Actions.moveToElement(MarkAs, MyX, MyY);
			sleep(500);
			Actions.click().build().perform();
			driver.findElement(By.xpath("//*[@id='panel3']/fieldset[10]/label/input")).click();
			driver.findElement(By.xpath("//*[id('panel3')]/fieldset[11]/label")).click();
			driver.findElement(By.name("teamid__teamid")).sendKeys(Rep_ID);
			WebElement finalPage = driver.findElement(By.xpath("//div/article/form/section/input"));
			scrollWebPage(finalPage);
			finalPage.click();
			// Explicit wait until Datacash external page opens up
			sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void orderSummaryPageForNewFrenchCustomer(String Rep_ID) {
		try {
			WebElement elementToScroll = driver
					.findElement(By.xpath("//*[@id='panel3']/fieldset[6]/div/section/div/div[1]/p"));
			scrollDownToView(elementToScroll);
			sleep(1000);
			scrollDownToView(elementToScroll);
			sleep(1000);
			WebElement authorisation1 = driver.findElement(By.xpath("//*[@id='panel3']/fieldset[9]/label/input"));
			sleep(1000);
			authorisation1.click();
			driver.findElement(By.xpath("//*[@id='panel3']/fieldset[10]/label/input")).click();
			driver.findElement(By.xpath("//*[id('panel3')]/fieldset[11]/label")).click();
			driver.findElement(By.name("teamid__teamid")).sendKeys(Rep_ID);
			WebElement finalPage = driver.findElement(By.xpath("//div/article/form/section/input"));
			scrollDownToView(finalPage);
			finalPage.click();
			// Explicit wait until Datacash external page opens up
			sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void orderSummaryPageForExitstingCustomer(String Rep_ID) {
		try {
			WebElement elementToScroll = driver.findElement(By.xpath("//*[@id='panel3']/fieldset[4]/div/ul/li[2]/h6"));
			scrollDownToView(elementToScroll);
			sleep(1000);
			scrollDownToView(elementToScroll);
			sleep(1000);
			WebElement MarkAs = driver.findElement(By.xpath("//*[id('panel3')]/fieldset[8]/label"));
			int Width = MarkAs.getSize().width;
			int Height = MarkAs.getSize().height;
			int MyX = (Width * 15) / 100;// spot to click is at 95% of the width
			int MyY = Height / 2;// anywhere above Height/2 works
			Actions Actions = new Actions(driver);
			Actions.moveToElement(MarkAs, MyX, MyY);
			Actions.click().build().perform();
			sleep(500);
			driver.findElement(By.xpath("//*[@id='panel3']/fieldset[9]/label/input")).click();
			driver.findElement(By.xpath("//*[id('panel3')]/fieldset[10]/label")).click();
			driver.findElement(By.name("teamid__teamid")).sendKeys(Rep_ID);
			WebElement submitOrderButton = driver.findElement(By.xpath("//div/article/form/section/input"));
			scrollDownToView(submitOrderButton);
			submitOrderButton.click();
			// Explicit wait until Datacash external page opens up
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void orderSummaryPageForExitstingFrenchCustomer(String Rep_ID) {
		try {
			WebElement elementToScroll = driver.findElement(By.xpath("//*[@id='panel3']/fieldset[4]/div/ul/li[2]/h6"));
			scrollDownToView(elementToScroll);
			sleep(1000);
			scrollDownToView(elementToScroll);
			sleep(1000);
			driver.findElement(By.xpath("//*[@id='panel3']/fieldset[8]/label/input")).click();
			sleep(500);
			driver.findElement(By.xpath("//*[@id='panel3']/fieldset[9]/label/input")).click();
			driver.findElement(By.xpath("//*[id('panel3')]/fieldset[10]/label")).click();
			driver.findElement(By.name("teamid__teamid")).sendKeys(Rep_ID);
			WebElement submitOrderButton = driver.findElement(By.xpath("//div/article/form/section/input"));
			scrollDownToView(submitOrderButton);
			submitOrderButton.click();
			// Explicit wait until Datacash external page opens up
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Automating datacash page
	public static void Payment_Datacash(String Card_Type, String Card_Number, String Expiry_Year, String CVV) {

		// Selecting the required Credit card Visa, master card etc..
		WebElement cardList = driver.findElement(By.xpath("//tbody/tr[4]/td/select"));
		Select s = new Select(cardList);
		s.selectByVisibleText(Card_Type);

		// Card Number
		driver.findElement(By.name("card_number")).sendKeys(Card_Number);

		// Expiry mmyy, Month retain same and year need to be in future
		WebElement yearList = driver.findElement(By.name("exp_year"));
		Select s1 = new Select(yearList);
		s1.selectByVisibleText(Expiry_Year);
		driver.findElement(By.name("cv2_number")).sendKeys(CVV);
		driver.findElement(By.xpath("/html/body/form/div/input")).click();
		try {
			sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} // New page loading
			// Authentication
		driver.findElement(By.xpath("//table/tbody/tr[1]/td[1]/input")).click();
		try {
			sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// check the status of Order
	public static boolean CheckStatusOfOrder(String orderNumber) {
		try {
			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
				ResultSet resultSet = OracleJdbcConnection.crmDatabaseSelectQuery(queryOrderStatus(orderNumber));
				while (resultSet.next()) {
					String status = resultSet.getString("STATUS");
					System.out.println("Status of Order: " + status);
					if ("RESOLVING_TASKS".equals(status) || "INPROCESS".equals(status)) {
						return true;

					} else if ("NEW".equals(status)) {
						System.err.format("Order status is %s, NOT 'RESOLVING_TASKS', rechecking...", status);

					} else if ("ERROR".equals(status)) {
						System.err.format("Test is failed as the order status is %s, NOT 'RESOLVING_TASKS'", status);
						return false;
					}
				}
				sleep(Constant.DEFAULT_SLEEP_TIME);
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// check the status of Order
	public static boolean CheckStatusOfOrderInCancelled(String orderNumber) {
		try {
			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
				ResultSet resultSet = OracleJdbcConnection.crmDatabaseSelectQuery(queryOrderStatus(orderNumber));
				while (resultSet.next()) {
					String status = resultSet.getString("STATUS");
					System.out.println("Status of Order: " + status);
					if ("CANCELLED".equals(status)) {
						return true;

					} else if ("ERROR".equals(status)) {
						System.err.format("Test is failed as the order status is %s, NOT 'RESOLVING_TASKS'", status);
						return false;
					}
				}
				sleep(Constant.DEFAULT_SLEEP_TIME);
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean checkIfActionExistsForOrder(String orderID, String actionName) {
		try {
			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
				ResultSet resultSet = OracleJdbcConnection
						.crmDatabaseSelectQuery(queryActionInstanceWithOrderIdAndActionName(orderID, actionName));
				while (resultSet.next()) {
					String Name = resultSet.getString("NAME");
					String Status = resultSet.getString("STATUS");
					if (actionName.equals(Name) && "WAI".equals(Status)) {
						System.out.println(String.format("Action '%s' is in WAI status", Name));
						return true;
					} else if (actionName.equals(Name) && "FIN".equals(Status)) {
						System.out.println(String.format("Action '%s' is in FIN status", Name));
						return true;
					} else if (actionName.equals(Name) && "SCH".equals(Status)) {
						System.out.println(String.format("Action '%s' is in SCH status", Name));
						return true;
					}

				}
				sleep(Constant.DEFAULT_SLEEP_TIME);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	// Resolving tasks API call
	public static String processResolvingTasksSignal(String orderNumber) {

		String taskId = "";
		String orderId = "";
		String latestPriId = "";

		try {
			ResultSet queryOrderId = OracleJdbcConnection.crmDatabaseSelectQuery(queryOrderStatus(orderNumber));
			while (queryOrderId.next()) {
				orderId = queryOrderId.getString("ID");
				System.out.println("Order ID: " + orderId);
			}

			ResultSet queryTaskId = OracleJdbcConnection.crmDatabaseSelectQuery(queryTaskId(orderId));
			while (queryTaskId.next()) {
				taskId = queryTaskId.getString("ID");
				System.out.println("Task Id: " + taskId);
			}

			ResultSet queryProcessId = OracleJdbcConnection.crmDatabaseSelectQuery(querylatestProcessId(orderId));
			while (queryProcessId.next()) {
				sleep(500);
				latestPriId = queryProcessId.getString("max(PRI_ID)");
				System.out.println("Process ID: " + latestPriId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// RESOLVING_TASKS API signal
		String resolvingTasksApiBody = RestApiAutomation.resolvingTasksApiBody(latestPriId, taskId);
		RestApiAutomation.httpPostTest(Constant.cue_Update, resolvingTasksApiBody);

		return orderId;
	}

	// Worker Run
	public static void workerQueryOrderStatusJobForBillingUpdate() {

		openNewBrowserwithinTests();

		driver.navigate().to(Constant.billsrvWorkerUrl);
		// Trigger worker 'QueryOrderStatusJob'
		WebElement workerQueryOrderStatusJob = driver.findElement(By.xpath("//div[3]/div/div[3]/div[2]/a[1]"));
		scrollDownToView(workerQueryOrderStatusJob);
		workerQueryOrderStatusJob.click();
		try {
			sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		driver.quit();
		try {
			sleep(Constant.DEFAULT_MEDIUM_SLEEP_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Trigger 'QuerySubscriptionJob' 2 times after 'QueryOrderStatusJob'
	public static void workerQuerySubscriptionJobForBillingUpdate() {

		try {
			System.out.println("ofUpfrontPaymentWait action finished in Success");
			openNewBrowserwithinTests();
			driver.navigate().to(Constant.billsrvWorkerUrl);
			// Trigger worker 'QuerySubscriptionJob' for first time
			WebElement workerQuerySubscriptionJob = driver
					.findElement(By.xpath("//div[2]/div[3]/div/div[16]/div[2]/a[1]"));
			scrollDownToView(workerQuerySubscriptionJob);
			workerQuerySubscriptionJob.click();

			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
			// running for 2nd time
			WebElement workerQuerySubscriptionJob2 = driver
					.findElement(By.xpath("//div[2]/div[3]/div/div[16]/div[2]/a[1]"));
			scrollDownToView(workerQuerySubscriptionJob2);
			workerQuerySubscriptionJob2.click();
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
			driver.quit();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static boolean checkIfActionExistsWithProcessId(String processId, String actionName) {
		try {

			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
				ResultSet resultSet = OracleJdbcConnection
						.crmDatabaseSelectQuery(queryActionInstanceWithProcessIdAndActionName(processId, actionName));
				while (resultSet.next()) {
					String Name = resultSet.getString("NAME");
					String Status = resultSet.getString("STATUS");
					if (actionName.equals(Name) && "WAI".equals(Status)) {
						System.out.println("Action is in WAI status");
						return true;
					} else if (actionName.equals(Name) && "FIN".equals(Status)) {
						System.out.println("Action is in FIN status");
						return true;
					} else if (actionName.equals(Name) && "SCH".equals(Status)) {
						System.out.println("Action is in SCH status");
						return true;
					}
				}
				sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	// Trigger 'QuerySubscriptionJob' 2 times after 'QueryOrderStatusJob'
	public static void workerResposeProvisioning_UK() {

		try {
			System.out.println("ofUpfrontPaymentWait action finished in Success");
			openNewBrowserwithinTests();
			driver.navigate().to("https://uatcocoon.eu.acncorp.com/prov-uk-mobile/gui/");
			// Trigger worker 'QuerySubscriptionJob' for first time
			WebElement workerProvisioningResponce = driver
					.findElement(By.xpath("//*[@id='PIXS1rD2d4orsaYziZUvXH7aDjc=']/div[2]/a[1]"));
			scrollDownToView(workerProvisioningResponce);
			workerProvisioningResponce.click();

			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
			driver.quit();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void openNewBrowserwithinTests() {
		String configFile = Constant.DEFAULT_TESTDATA_LOCATION + "/" + Constant.configFileName;
		String browserType = Generic.getCellValue(configFile, "Browser_selection", 0, 1);

		Reporter.log(String.format("Tests are performed on %s browser", browserType), true);

		if (browserType.equals("Internet Explorer")) {
			System.setProperty("webdriver.ie.driver", Constant.IE_Driver_Path);
			driver = new InternetExplorerDriver();
		} else if (browserType.equals("Google Chrome")) {
			System.setProperty("webdriver.chrome.driver", Constant.Chrome_Driver_Path);
			driver = new ChromeDriver();
		} else {
			driver = new FirefoxDriver();
		}
		// get the URL of the page JOi UK/FR_FR/FR_EN
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// to maximize the opening browser window
		driver.manage().window().maximize();

	}

	public static void openNewBrowserwithinTestsForWorkers() {
		String configFile = Constant.DEFAULT_TESTDATA_LOCATION + "/" + Constant.configFileName;
		String browserType = Generic.getCellValue(configFile, "Browser_selection", 0, 1);

		if (browserType.equals("Google Chrome")) {
			System.setProperty("webdriver.chrome.driver", Constant.Chrome_Driver_Path);
			driver = new ChromeDriver();
			Reporter.log("Opening Google Chrome", true);
		} else {
			driver = new FirefoxDriver();
			Reporter.log("Opening Mozilla FireFox", true);
		}
		// get the URL of the page JOi UK/FR_FR/FR_EN
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// to maximize the opening browser window
		driver.manage().window().maximize();

	}

	public static boolean processUpfrontPayment_Jackal(String orderNumber, String orderID) {

		try {
			for (int i = 1; i < Constant.DEFAULT_RETRY_COUNT; i++) {
				ResultSet resultSet = OracleJdbcConnection.crmDatabaseSelectQuery(
						queryActionInstanceWithOrderIdAndActionName(orderID, "ofUpfrontPaymentWait"));

				while (resultSet.next()) {
					String actionName = resultSet.getString("NAME");
					String status = resultSet.getString("STATUS");
					String result = resultSet.getString("RESULT");

					if ("ofUpfrontPaymentWait".equals(actionName) && "WAI".equals(status)) {
						Reporter.log(String.format("%s is in %s, awaitng to finish, count=%d", actionName, status, i),
								true);
					}
					if ("ofUpfrontPaymentWait".equals(actionName) && "FIN".equals(status) && "SUCCESS".equals(result)) {
						Reporter.log(String.format("%s is in %s", actionName, status), true);
						return true;
					}

					if ("ofUpfrontPaymentWait".equals(actionName) && "ERR".equals(status)) {
						Reporter.log("ofUpfrontPaymentWait action finished in Error", true);
						return false;
					}

					if ("ofUpfrontPaymentWait".equals(actionName) && "FIN".equals(status) && "ERROR".equals(result)) {
						Reporter.log("ofUpfrontPaymentWait action finished in Error", true);

						sleep(Constant.DEFAULT_SLEEP_TIME);
						ResultSet queryUsertask = OracleJdbcConnection.crmDatabaseSelectQuery(
								queryActionInstanceWithOrderIdAndActionName(orderID, "UpfrontPaymentUserTaskAction"));

						while (queryUsertask.next()) {
							String actionNameUserTask = queryUsertask.getString("NAME");
							String statusUsertask = queryUsertask.getString("STATUS");
							String resultUsertask = queryUsertask.getString("RESULT");
							String processId = queryUsertask.getString("PRI_ID");

							if ("UpfrontPaymentUserTaskAction".equalsIgnoreCase(actionNameUserTask)
									&& "WAI".equalsIgnoreCase(statusUsertask)) {
								// send a request to resolve the usertask

								retryUpfrontPaymentWaitUserTask(orderNumber, orderID, processId);
								sleep(5000);

							}
							if (("UpfrontPaymentUserTaskAction".equalsIgnoreCase(actionNameUserTask)
									&& "ERR".equalsIgnoreCase(statusUsertask))
									|| ("UpfrontPaymentUserTaskAction".equalsIgnoreCase(actionNameUserTask)
											&& "FIN".equalsIgnoreCase(statusUsertask)
											&& resultUsertask.equalsIgnoreCase("ERROR"))) {
								Reporter.log("Upfront payment wait user task is in error", true);
								return false;

							}
						}
					}
				}
				sleep(45000);// waiting 50 sec before the next check
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// simple date format for xmls
	public static String dateToString(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	private static void retryUpfrontPaymentWaitUserTask(String orderNumber, String orderId, String processId) {

		// find zipCode, Street, DOB of customer

		String requestForRetry = null;
		String zipCode = "";
		String DOB = "";
		String street = "";
		String cardExpiryDate = "";
		String customerId = "";

		try {
			// Order table query
			ResultSet queryOrderTable = OracleJdbcConnection
					.crmDatabaseSelectQuery(OracleDBUtils.queryOrderDetails(orderNumber));

			while (queryOrderTable.next()) {

				customerId = queryOrderTable.getString("CUSTOMER_ID");
				street = queryOrderTable.getString("STREET");
				zipCode = queryOrderTable.getString("POSTAL_CODE");
			}

			String CustomerTable = OracleDBUtils.queryCust_CustomersTableWithCustId(customerId);
			ResultSet queryCustomerTable = OracleJdbcConnection.crmDatabaseSelectQuery(CustomerTable);

			while (queryCustomerTable.next()) {
				Date dateOfBirth = queryCustomerTable.getDate("DOB");
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				DOB = df.format(dateOfBirth);
			}

			String cardTable = OracleDBUtils.Credit_card_details(customerId);
			ResultSet queryCardTable = OracleJdbcConnection.crmDatabaseSelectQuery(cardTable);

			while (queryCardTable.next()) {
				Date ccExpiryDate = queryCardTable.getDate("CARD_EXPIRY_DATE");
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				cardExpiryDate = df.format(ccExpiryDate);
			}
			requestForRetry = RestApiAutomation.requestToRetryUpfrontPaymentUsertask(orderNumber, orderId, processId,
					zipCode, cardExpiryDate, DOB, street);
			Reporter.log("CUET-R Request to retry: " + requestForRetry, true);
			RestApiAutomation.httpPostTest(Constant.cue_Execute, requestForRetry);
			Reporter.log("Upfront payment wait action is RETRIED", true);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return;

	}

	public static boolean processEligibilityCheckWaiting(String orderID) {
		String portinNumber = "";
		String rioCode = "";

		try {

			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
				ResultSet resultSet = OracleJdbcConnection.crmDatabaseSelectQuery(
						queryActionInstanceWithOrderIdAndActionName(orderID, "mbElegibilityCheck"));

				while (resultSet.next()) {
					String actionName = resultSet.getString("NAME");
					String status = resultSet.getString("STATUS");
					String processId = resultSet.getString("PRI_ID");
					System.out.println("eligibility Check process id: " + processId);

					if ("mbElegibilityCheck".equals(actionName) && "FIN".equals(status)) {
						return true;
					}
					if ("mbElegibilityCheck".equals(actionName) && "ERR".equals(status)) {
						return false;
					}
					if ("mbElegibilityCheck".equals(actionName) && !"FIN".equals(status)) {
						String date = dateToString(new Date(), "yyyyMMdd");
						ResultSet numposResultSet = OracleJdbcConnection.numposDatabaseSelectQuery(
								queryLatestPortinNumberfromNumposDB("FR", Constant.FR_Portin_Number));
						while (numposResultSet.next()) {
							portinNumber = numposResultSet.getString("MAX_PORTING_NUMBER");
						}
						rioCode = RioGenerator.generateRio(Constant.NUMPOS_OP_CODE_1,
								Constant.NUMPOS_DEFAULT_RIO_CODE.substring(2, 3),
								Constant.NUMPOS_DEFAULT_RIO_CODE.substring(3, 9), portinNumber);

						String eligibilityApiBody = RestApiAutomation.portInEligibilityCheckNumposeResponse(date,
								portinNumber, rioCode);

						System.out.println("====" + eligibilityApiBody);

						RestApiAutomation.httpPostTest(Constant.NUMPOS_INCOMING_REQUEST, eligibilityApiBody);
						sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);

					}
				}
				sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean processNumposWaiting(String orderID) {
		String portinNumber = "";
		String rioCode = "";

		try {

			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
				ResultSet resultSet = OracleJdbcConnection.crmDatabaseSelectQuery(
						queryActionInstanceWithOrderIdAndActionName(orderID, "mbNumPosWaiting"));

				while (resultSet.next()) {
					String actionName = resultSet.getString("NAME");
					String status = resultSet.getString("STATUS");
					String processId = resultSet.getString("PRI_ID");
					System.out.println("mbNumPosWaiting process id: " + processId);

					if ("mbNumPosWaiting".equals(actionName) && "FIN".equals(status)) {
						return true;
					}
					if ("mbNumPosWaiting".equals(actionName) && "ERR".equals(status)) {
						return false;
					}
					if ("mbNumPosWaiting".equals(actionName) && !"FIN".equals(status)) {
						ResultSet numposResultSet = OracleJdbcConnection.numposDatabaseSelectQuery(
								queryLatestPortinNumberfromNumposDB("FR", Constant.FR_Portin_Number));
						while (numposResultSet.next()) {
							portinNumber = numposResultSet.getString("MAX_PORTING_NUMBER");
						}
						rioCode = RioGenerator.generateRio(Constant.NUMPOS_OP_CODE_2,
								Constant.NUMPOS_DEFAULT_RIO_CODE.substring(2, 3),
								Constant.NUMPOS_DEFAULT_RIO_CODE.substring(3, 9), portinNumber);

						String numposApiBody = RestApiAutomation.processPortInNumposeWaitingToDone(portinNumber,
								rioCode);

						System.out.println("====" + numposApiBody);

						RestApiAutomation.httpPostTest(Constant.NUMPOS_INCOMING_REQUEST, numposApiBody);

					}
				}
				sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void selectLibertyOrderFromOurOffer() {
		Actions mouseAction = new Actions(driver);

		try {
			sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement ourOffer = driver.findElement(By.xpath("//ul/li[2]/span"));
		WebElement moveToJOiLiberty = driver.findElement(By.xpath("//li[2]/div/div/ul/li/ul/li[2]/a"));

		mouseAction.moveToElement(ourOffer).moveToElement(moveToJOiLiberty).click().build().perform();

		driver.findElement(By.xpath("//div/div[2]/div/a[1]")).click();
	}

	public static void selectProductDetails() {

		Actions mouseAction = new Actions(driver);
		try {
			sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement ourOffer = driver.findElement(By.xpath("//ul/li[2]/span"));
		WebElement moveToJOiLiberty = driver.findElement(By.xpath("//li[2]/div/div/ul/li/ul/li[2]/a"));

		mouseAction.moveToElement(ourOffer).moveToElement(moveToJOiLiberty).click().build().perform();

		driver.findElement(By.xpath("//div/div[2]/div/a[2]")).click();
	}

	public static void selectEgalitteOrderFromOurOffer() {
		Actions mouseAction = new Actions(driver);
		WebElement ourOffer = driver.findElement(By.xpath("//ul/li[2]/span"));
		WebElement moveToEgalitte = driver.findElement(By.xpath("//li[2]/div/div/ul/li/ul/li[3]/a"));

		mouseAction.moveToElement(ourOffer).moveToElement(moveToEgalitte).click().build().perform();
		// Click on 'Order Now'
		driver.findElement(By.xpath("//div/div[2]/div/a[1]")).click();
	}

	public static void provisioningDuringActivationOfOrders(String orderId) {
		String processId = "";
		checkIfActionExistsForOrder(orderId, "mbProvisionWait");

		// here we check if provisioning action is created
		try {
			ResultSet resultSet1 = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryActionInstanceWithOrderIdAndActionName(orderId, "mbProvisionWait"));

			while (resultSet1.next()) {
				String actionName = resultSet1.getString("NAME");
				String result = resultSet1.getString("RESULT");
				processId = resultSet1.getString("PRI_ID");

				if ("mbProvisionWait".equals(actionName) && "RETRY".equals(result)) {
					OracleJdbcConnection.crmDatabaseUpdateQuery(
							queryUpdateActionInstanceScheduledTime(processId, "mbProvisionStart"));
					sleep(Constant.DEFAULT_LONG_SLEEP_TIME); // 2mins
																// sleep
				} else
					break;
			}
			ResultSet resultSet2 = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryActionInstanceWithOrderIdAndActionName(orderId, "mbProvisionWait"));

			while (resultSet2.next()) {
				String actionName = resultSet2.getString("NAME");
				String result = resultSet2.getString("RESULT");
				processId = resultSet2.getString("PRI_ID");
				if ("mbProvisionWait".equals(actionName) && "ERROR".equals(result)) {
					// update action result=null, status = 'WAI', owner=null
					OracleJdbcConnection.crmDatabaseUpdateQuery(queryUpdateOrderLines(orderId));
					OracleJdbcConnection.crmDatabaseUpdateQuery(queryUpdateCinProducts(orderId));
					OracleJdbcConnection.crmDatabaseUpdateQuery(queryUpdateOrdOrders(orderId));
					OracleJdbcConnection
							.crmDatabaseUpdateQuery(queryUpdateActionInstances(processId, "mbProvisionWait"));
					OracleJdbcConnection
							.crmDatabaseUpdateQuery(queryUpdateProcessInstances(processId, "MBSubscription"));
				}
			}

			System.out.println("starting provisioning");
			String provisioningSuccess = RestApiAutomation.provisioningSuccessSignalApiBody(processId,
					"MBprovisioningSignal");
			System.out.println(provisioningSuccess);
			RestApiAutomation.httpPostTest(Constant.cue_Update, provisioningSuccess);
			System.out.println("provisioning is completed, now the customer should be active");

			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String franceUniquePortInNumber() {
		String uniquePortInNumber = "";

		try {

			ResultSet resultSet = OracleJdbcConnection
					.numposDatabaseSelectQuery(queryLatestPortinNumberfromNumposDB("FR", Constant.FR_Portin_Number));

			while (resultSet.next()) {
				String portInNumber = resultSet.getString("MAX_PORTING_NUMBER");
				uniquePortInNumber = "0" + String.valueOf(((Integer.parseInt(portInNumber)) + 1));
			}
		} catch (Exception e) {

		}
		return uniquePortInNumber;
	}

	public static boolean processNumposForFrancePortIn(String orderID) {

		try {
			ResultSet resultSet = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryActionInstanceWithOrderIdAndActionName(orderID, "mbNumPosStart"));

			while (resultSet.next()) {
				String actionName = resultSet.getString("NAME");
				String status = resultSet.getString("STATUS");
				String result = resultSet.getString("RESULT");

				if ("mbNumPosStart".equals(actionName) && "FIN".equals(status) && "SUCCESS".equals(result)) {
					return true;
				}
				if ("mbNumPosStart".equals(actionName) && "FIN".equals(status) && "ERROR".equals(result)) {
					return false;
				}
				if ("mbNumPosStart".equals(actionName) && "ERR".equals(status)) {
					return false;
				}
			}
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean numposProvisioningStartToNow(String orderId) {

		// here we check if mbProvisionStart action is created

		for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
			try {
				ResultSet resultSet = OracleJdbcConnection.crmDatabaseSelectQuery(
						queryActionInstanceWithOrderIdAndActionName(orderId, "mbProvisionStart"));

				while (resultSet.next()) {
					String actionName = resultSet.getString("NAME");
					String status = resultSet.getString("STATUS");
					String processId = resultSet.getString("PRI_ID");

					if ("mbProvisionStart".equals(actionName) && "FIN".equals(status)) {
						return true;
					}
					if ("mbProvisionStart".equals(actionName) && "ERR".equals(status)) {
						return false;
					}
					if ("mbProvisionStart".equals(actionName) && "SCH".equals(status)) {
						OracleJdbcConnection.crmDatabaseUpdateQuery(
								queryUpdateActionInstanceScheduledTime(processId, "mbProvisionStart"));
						// 2mins wait to process
						sleep(Constant.DEFAULT_LONG_SLEEP_TIME);

					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean cancellationOfIncompleteOrderInShipWaitingStage(String orderID, String CancellationApiBody) {

		try {

			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
				ResultSet resultSet = OracleJdbcConnection
						.crmDatabaseSelectQuery(queryActionInstanceWithOrderIdAndActionName(orderID, "mbShipWaiting"));

				while (resultSet.next()) {
					String actionName = resultSet.getString("NAME");
					String status = resultSet.getString("STATUS");

					if ("mbShipWaiting".equals(actionName) && "WAI".equals(status)) {
						RestApiAutomation.httpPostTest(Constant.cue_Execute, CancellationApiBody);
						return true;
					}
					if ("mbShipWaiting".equals(actionName) && "ERR".equals(status)) {
						return false;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void terminateBillingDuringOrderCancellation(String orderId) {
		MVNOProjectSpecific.checkIfActionExistsForOrder(orderId, "orTerminateBilling");

		try {
			ResultSet resultSet = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryActionInstanceWithOrderIdAndActionName(orderId, "orTerminateBilling"));

			while (resultSet.next()) {
				String actionName = resultSet.getString("NAME");
				String status = resultSet.getString("STATUS");
				// String result = resultSet.getString("RESULT");
				String processId = resultSet.getString("PRI_ID");
				System.err.println(processId);

				if ("orTerminateBilling".equals(actionName) && "SCH".equals(status)) {
					OracleJdbcConnection.crmDatabaseUpdateQuery(
							queryUpdateActionInstanceScheduledTime(processId, "orTerminateBilling"));
					sleep(Constant.DEFAULT_LONG_SLEEP_TIME); // 2mins
																// sleep
				} else
					break;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String fetchPortOutRioCodeFromLatestCustomer() {

		String rioCode = "";
		String customerNumber = "";
		String cinProductNumber = "";
		ResultSet customerDetails = null;
		customerDetails = OracleJdbcConnection.crmDatabaseSelectQuery(queryMaxCustomerNumberFrWithUsername(username));

		try {
			while (customerDetails.next()) {

				customerNumber = customerDetails.getString("MAX_CUSTOMER_NUMBER");
				System.out.println("CUSTOMER NUMBER: " + customerNumber);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResultSet cinProduct = null;
		cinProduct = OracleJdbcConnection.crmDatabaseSelectQuery(queryLatestCinProductnumber(customerNumber));

		try {
			while (cinProduct.next()) {

				cinProductNumber = cinProduct.getString("MAX(CIN_NUMBER)");
				if (cinProduct.wasNull()) {
					System.out.println("Latest Cin product has no active subscriptions for this account");

					throw new AssertionError();
				} else {
					System.out.println("Cin product id: " + cinProductNumber);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResultSet queryRioCode = null;
		queryRioCode = OracleJdbcConnection.crmDatabaseSelectQuery(queryPortoutID(cinProductNumber));

		try {
			while (queryRioCode.next()) {

				rioCode = queryRioCode.getString("ATTRIBUTE2");
				System.out.println("Port Out ID: " + rioCode);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rioCode;
	}

	public static String fetchMsidnOfLatestCustomer() {

		String msisdn = "";
		String customerNumber = "";
		String cinProductNumber = "";
		ResultSet customerDetails = null;
		customerDetails = OracleJdbcConnection.crmDatabaseSelectQuery(queryMaxCustomerNumberFrWithUsername(username));

		try {
			while (customerDetails.next()) {

				customerNumber = customerDetails.getString("MAX_CUSTOMER_NUMBER");
				// System.out.println("CUSTOMER NUMBER: " + customerNumber);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResultSet cinProduct = null;
		cinProduct = OracleJdbcConnection.crmDatabaseSelectQuery(queryLatestCinProductnumber(customerNumber));

		try {
			while (cinProduct.next()) {

				cinProductNumber = cinProduct.getString("MAX(CIN_NUMBER)");
				if (cinProduct.wasNull()) {
					System.out.println("Cin product has no active subscriptions for this account");

					throw new AssertionError();
				} else {
					// System.out.println("Cin product id: " +
					// cinProductNumber);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResultSet queryRioCode = null;

		queryRioCode = OracleJdbcConnection.crmDatabaseSelectQuery(queryCustomerMsisdn(cinProductNumber));

		try {
			while (queryRioCode.next()) {

				msisdn = queryRioCode.getString("ATTRIBUTE1");
				System.out.println("msidn: " + msisdn);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return msisdn;
	}

	public static boolean portOutProvisioningIsDone(String processId) {

		try {

			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
				ResultSet resultSet = OracleJdbcConnection
						.crmDatabaseSelectQuery(queryActionInstanceWithProcessIdAndActionName(processId, "pOProv"));

				while (resultSet.next()) {
					String actionName = resultSet.getString("NAME");
					String status = resultSet.getString("STATUS");
					String result = resultSet.getString("RESULT");

					if ("pOProv".equals(actionName) && "FIN".equals(status) && "SUCCESS".equals(result)) {
						return true;
					}
					if ("pOProv".equals(actionName) && "ERR".equals(status)) {
						return false;
					}
					if ("pOProv".equals(actionName) && "FIN".equals(status) && "ERROR".equals(result)) {
						// UPDATE ACTION_INSTANCES TABLE - ACTION TO WAITING
						OracleJdbcConnection.crmDatabaseUpdateQuery(queryUpdateActionInstances(processId, "pOProv"));
						// UPDATE PROCESS INSTANCES TO PRC
						OracleJdbcConnection.crmDatabaseUpdateQuery(queryUpdateProcessInstances(processId, "PortOut"));
						sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
						// UPDATE ACTION INSTANCES TABLE SCHEDULE TIME TO SYS
						// TIME
						// OracleJdbcConnection.crmDatabaseUpdateQuery(queryUpdateActionInstanceScheduledTime(processId,
						// "pOProv"));
						sleep(Constant.DEFAULT_LONG_SLEEP_TIME);
						String PortOutProvisioning = RestApiAutomation.provisioningSuccessSignalApiBody(processId,
								"POprovisioningSignal");
						RestApiAutomation.httpPostTest(Constant.cue_Update, PortOutProvisioning);
						sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
					}
				}
				sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	public static boolean processPortOutFinishIsDone(String processId) {

		try {
			ResultSet resultSet = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryActionInstanceWithProcessIdAndActionName(processId, "pONumPosFinish"));

			while (resultSet.next()) {
				String actionName = resultSet.getString("NAME");
				String status = resultSet.getString("STATUS");
				String result = resultSet.getString("RESULT");

				if ("pONumPosFinish".equals(actionName) && "FIN".equals(status) && "SUCCESS".equals(result)) {
					return true;
				}
				if ("pONumPosFinish".equals(actionName) && "FIN".equals(status) && "ERROR".equals(result)) {
					return false;
				}
				if ("pONumPosFinish".equals(actionName) && "ERR".equals(status)) {
					return false;
				}
			}
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// Specify UK/FR
	public static String queryLatestCustomerNumber(String country) {

		String customerNumber = "";

		ResultSet queryCustomerNumber = null;

		if (country.equals("FR")) {
			queryCustomerNumber = OracleJdbcConnection
					.crmDatabaseSelectQuery(OracleDBUtils.queryMaxCustomerNumberFrWithUsername(username));

		} else if (country.equals("UK") || country.equals("GB")) {
			queryCustomerNumber = OracleJdbcConnection
					.crmDatabaseSelectQuery(OracleDBUtils.queryMaxCustomerNumberUKCustomer(username));
		}

		try {
			while (queryCustomerNumber.next()) {

				customerNumber = queryCustomerNumber.getString("MAX_CUSTOMER_NUMBER");
				System.out.println("New customer Number: " + customerNumber);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Display new EmailId of the customer
		return customerNumber;
	}

	public static void updateEmailIdInCust_customersTable(String emailAdress, String customerNumber) {

		if (emailAdress.equalsIgnoreCase("unknown") || emailAdress == null) {

			System.err.println("Email not found, so skipping the DB change");

		} else {

			OracleJdbcConnection
					.crmDatabaseUpdateQuery(OracleDBUtils.queryUpdateEmailAdress(emailAdress, customerNumber));
			Reporter.log(String.format("Email Adress is changed to: %s", emailAdress), true);
		}

	}

	public static File pdfDownloadToSpecificLocation(String url, String fileLocation) throws IOException {

		File file = FileUtils.getFile(fileLocation);

		FileUtils.cleanDirectory(file);

		FirefoxProfile profile = new FirefoxProfile();

		profile.setAcceptUntrustedCertificates(true);
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.dir", fileLocation);
		// profile.setPreference("browser.helperapps.neverAsk.saveToDisk",
		// "application/pdf, application/msword");
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"application/msword, application/csv, application/ris, text/csv, image/png, "
						+ "application/pdf, text/html, text/plain, application/zip, application/x-zip, "
						+ "application/x-zip-compressed, application/download, application/octet-stream");
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.helperApps.alwaysAsk.force", false);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("pref.downloads.disable_button.edit_actions", true);
		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		profile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.manager.useWindow", false);
		driver = new FirefoxDriver(profile);
		try {
			Robot robot = new Robot();

			try {
				driver.navigate().to(url);
				// driver.get(url);
				sleep(3000);
				driver.findElement(By.xpath("//*[@id='download']")).click();
				sleep(2000);
				robot.keyPress(KeyEvent.VK_TAB);
				sleep(1000);
				robot.keyPress(KeyEvent.VK_TAB);
				sleep(1000);
				robot.keyPress(KeyEvent.VK_TAB);
				sleep(1000);
				robot.keyPress(KeyEvent.VK_ENTER);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// driver.quit();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
		return file;
	}

	public static void readandVerifyTextInPdf(String fileLocation, String fileName, int pageNumber) throws IOException {

		PDDocument pd; // Document
		// String textToVerify = "connection fee";
		pd = PDDocument.load(new File(fileLocation + fileName));

		System.out.println("Total Number of Pages: " + pd.getNumberOfPages());

		PDFTextStripper pdf = new PDFTextStripper();
		pdf.setStartPage(pageNumber);
		pdf.setEndPage(pageNumber);
		String text = pdf.getText(pd);
		System.out.println(text);
		pd.close();
	}

	public static String verifyPortOutIsInitiatedInNumpos(String portOutNumber) {
		// Check for the 'pOProv' action is created
		ResultSet queryProcessId = null;
		String processId = "";

		try {
			String queryProcess = queryPortOutProcessIdfromNuposDB(portOutNumber);

			System.out.println(queryProcess);

			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);

			queryProcessId = OracleJdbcConnection.numposDatabaseSelectQuery(queryProcess);

			while (queryProcessId.next()) {
				processId = queryProcessId.getString("MAX_REFERENCE_ID");

				if (queryProcessId.wasNull()) {
					System.out.println("Test failed as Numpos is not updated with processed ID");
					throw new AssertionError();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return processId;
	}

	public static boolean verifyDirectDebitDetails() {

		String customerNumber = "";
		String customerId = "";

		customerNumber = queryLatestCustomerNumber("FR");
		try {
			ResultSet resultsCustomerId = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryCustomerDetailsInCust_CustomersTable(customerNumber));
			while (resultsCustomerId.next()) {

				customerId = resultsCustomerId.getString("ID");

				ResultSet queryDirectDebit = OracleJdbcConnection
						.crmDatabaseSelectQuery(queryDirectDebitDetails(customerId));

				while (queryDirectDebit.next()) {
					queryDirectDebit.getString("CUST_ID");
					if (queryDirectDebit.wasNull()) {
						return false;

					} else {
						return true;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String processMBTerminationActionLatest(String country) {

		String cinProductId = "";
		String status = "";
		String myJoiNumber = "";

		try {

			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {

				String queryOrderId = queryOldestActiveCustomerNumber(country);
				ResultSet getCinProductDetails = OracleJdbcConnection.crmDatabaseSelectQuery(queryOrderId);

				while (getCinProductDetails.next()) {

					cinProductId = getCinProductDetails.getString("ID");
					myJoiNumber = getCinProductDetails.getString("ATTRIBUTE1");
					status = getCinProductDetails.getString("STATUS");
					String custNumber = getCinProductDetails.getString("CUSTOMER_NUMBER");

					System.err.println(String.format("Customer Number %s will be disconnected", custNumber));
					Reporter.log("Customer Number " + custNumber + " is selected for disconnection");
				}

				if (status.equalsIgnoreCase("ACT")) {

					String apiBodyForSubscriptionTermination = RestApiAutomation
							.requestToTerminateSubscription(myJoiNumber, cinProductId);
					System.out.println(apiBodyForSubscriptionTermination);
					RestApiAutomation.httpPostTest(Constant.CIN_EXECUTE, apiBodyForSubscriptionTermination);

					sleep(5000);

					break;

				} else {
					sleep(5000);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cinProductId;
	}

	public static String processBlockOrUnblockInternet(String country, String myJoiOrCuet_R) {

		String customerNumber = "";
		String cinProductId = "";
		String statusOfInternet = "";

		try {

			customerNumber = queryLatestCustomerNumber(country);
			String statusCinProductNumber = queryLatestCinProductnumber(customerNumber);
			ResultSet getCinProductDetails = OracleJdbcConnection.crmDatabaseSelectQuery(statusCinProductNumber);

			while (getCinProductDetails.next()) {

				cinProductId = getCinProductDetails.getString("max(CIN_NUMBER)");
			}

			// Search the status if Internet in CIN_PRODUCT_MBSUB
			ResultSet getStatusOfInternat = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryCinProductMBSUBtableDetails(cinProductId));
			while (getStatusOfInternat.next()) {

				statusOfInternet = getCinProductDetails.getString("BLOCKING_INTERNET");

			}

			if (statusOfInternet.equalsIgnoreCase("AED")) {

				System.out.println("The status of Internet is blocked, unblocking now....");

				if (myJoiOrCuet_R.equalsIgnoreCase("myJoi")) {
					// login to my Joi and perform unblock from my settings

				} else if (myJoiOrCuet_R.equalsIgnoreCase("cuetR")) {
					// Send API command BLOCK_INTERNET for unblock
				}

				// unblock internet using CuetR

			} else if ((statusOfInternet.equalsIgnoreCase("DED"))) {
				System.out.println("The status of Internet is not blocked, blocking now....");

				if (myJoiOrCuet_R.equalsIgnoreCase("myJoi")) {
					// login to my Joi and perform unblock from my settings

				} else if (myJoiOrCuet_R.equalsIgnoreCase("cuetR")) {
					// Send API command BLOCK_INTERNET for block
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cinProductId;
	}

	public static String processIdAsKeyFromCinProductLogsTable(String cinProductId) {
		String processId = "";

		String queryCinProductLogs = querycinProductsLogsTablesWithCnProductId(cinProductId);

		ResultSet cinProductlogsTable = OracleJdbcConnection.crmDatabaseSelectQuery(queryCinProductLogs);

		try {
			while (cinProductlogsTable.next()) {
				processId = cinProductlogsTable.getString("KEY");
				System.out.println(String.format("Process ID of MB termination is %s", processId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return processId;

	}

	public static boolean processMBTerminationProvisioningAction(String processId) {

		String action = "";
		String status = "";
		String result = "";

		try {

			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
				String queryActionmbTerminateProvisioning = queryActionInstanceWithProcessIdAndActionName(processId,
						"mbTerminateProvisioning");
				ResultSet getCinProductDetails = OracleJdbcConnection
						.crmDatabaseSelectQuery(queryActionmbTerminateProvisioning);

				while (getCinProductDetails.next()) {

					action = getCinProductDetails.getString("NAME");
					status = getCinProductDetails.getString("STATUS");
					result = getCinProductDetails.getString("RESULT");
				}

				if (action.equalsIgnoreCase("mbTerminateProvisioning") && status.equalsIgnoreCase("FIN")
						&& result.equalsIgnoreCase("ERROR")) {

					OracleJdbcConnection
							.crmDatabaseUpdateQuery(queryUpdateActionInstances(processId, "mbTerminateProvisioning"));
					sleep(1000);
					OracleJdbcConnection
							.crmDatabaseUpdateQuery(queryUpdateProcessInstances(processId, "MBTermination"));

					sleep(1000);

					String apiBodyForSubscriptionTermination = RestApiAutomation
							.provisioningSuccessSignalApiBody(processId, "MBProvTerminateSignal");
					System.out.println(apiBodyForSubscriptionTermination);
					RestApiAutomation.httpPostTest(Constant.cue_Update, apiBodyForSubscriptionTermination);
					sleep(5000);
				}
				if (action.equalsIgnoreCase("mbTerminateProvisioning") && status.equalsIgnoreCase("ERR")) {

					return false;

				}
				if (action.equalsIgnoreCase("mbTerminateProvisioning") && status.equalsIgnoreCase("FIN")
						&& result.equalsIgnoreCase("SUCCESS")) {

					return true;

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	public static String getProductStatus(String cinProductid) {

		String status = "";

		try {
			String queryProductStatus = querycinProductsWithCnProductId(cinProductid);
			ResultSet resultProductStatus = OracleJdbcConnection.crmDatabaseSelectQuery(queryProductStatus);

			while (resultProductStatus.next()) {

				status = resultProductStatus.getString("STATUS");

				if (status.equalsIgnoreCase("ACT")) {
					status = "Active";
				} else if (status.equalsIgnoreCase("TNG")) {
					status = "Terminating";
				} else if (status.equalsIgnoreCase("TRM")) {
					status = "Terminated";
				}
				// System.out.println(String.format("The status of the
				// subscription is %s", status));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}

	public static void selectBlockInternetFromMyJoiSettings() {

		driver.findElement(By.xpath("//*[@id='tab-COMMANDS']/ul/li[1]/a/p")).click();

		WebElement blockButton = driver.findElement(By.xpath("//section/section/div/article/form/label[1]"));

		blockButton.click();

		MVNOProjectSpecific.scrollDownToView(blockButton);
		// submit the changes
		driver.findElement(By.xpath("//section/div/article/form/section[3]/input")).click();
	}

	public static void selectUnBlockInternetFromMyJoiSettings() {
		try {
			driver.findElement(By.xpath("//*[@id='tab-COMMANDS']/ul/li[1]/a/p")).click();

			WebElement unBlockButton = driver.findElement(By.xpath("//section/section/div/article/form/label[2]"));

			unBlockButton.click();

			MVNOProjectSpecific.scrollDownToView(unBlockButton);
			// submit the changes
			driver.findElement(By.xpath("//section/div/article/form/section[3]/input")).click();
			sleep(3000);

			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String queryLatestAnyCustomerNumber(String country) {

		String customerNumber = "to be found";

		queryLatestActiveCinProductnumber(country);

		ResultSet queryCustomerNumber = null;
		try {
			if (country.equals("FR")) {
				queryCustomerNumber = OracleJdbcConnection.crmDatabaseSelectQuery(queryMaxCustomerNumberFrCustomer());
			} else if (country.equals("UK")) {
				queryCustomerNumber = OracleJdbcConnection.crmDatabaseSelectQuery(queryMaxCustomerNumberUKCustomer());
			}
			while (queryCustomerNumber.next()) {
				customerNumber = queryCustomerNumber.getString("MAX_CUSTOMER_NUMBER");
				System.out.println("New customer Number: " + customerNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// RETURNING EMAIL ID OF CUSTOMER
		return customerNumber;
	}

	public static void quitBrowser() {
		driver.quit();
	}

	public static void RescheduleOfMbRevocationforAutoBilling(String orderId) {
		String processId = "";
		checkIfActionExistsForOrder(orderId, "mbRevocation");

		// here we check if 'mbRevocation' action is created

		try {
			ResultSet resultSet = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryActionInstanceWithOrderIdAndActionName(orderId, "mbProvisionWait"));

			while (resultSet.next()) {
				String actionName = resultSet.getString("NAME");
				String result = resultSet.getString("RESULT");
				processId = resultSet.getString("PRI_ID");

				if ("mbProvisionWait".equals(actionName) && "RETRY".equals(result)) {
					OracleJdbcConnection.crmDatabaseUpdateQuery(
							queryUpdateActionInstanceScheduledTime(processId, "mbProvisionStart"));
					sleep(Constant.DEFAULT_LONG_SLEEP_TIME); // 2mins
																// sleep
				} else
					break;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ResultSet resultSet = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryActionInstanceWithOrderIdAndActionName(orderId, "mbProvisionWait"));

			while (resultSet.next()) {
				String actionName = resultSet.getString("NAME");
				String result = resultSet.getString("RESULT");
				processId = resultSet.getString("PRI_ID");
				if ("mbProvisionWait".equals(actionName) && "ERROR".equals(result)) {
					// update action result=null, status = 'WAI', owner=null
					OracleJdbcConnection.crmDatabaseUpdateQuery(queryUpdateOrderLines(orderId));
					OracleJdbcConnection.crmDatabaseUpdateQuery(queryUpdateCinProducts(orderId));
					OracleJdbcConnection.crmDatabaseUpdateQuery(queryUpdateOrdOrders(orderId));
					OracleJdbcConnection
							.crmDatabaseUpdateQuery(queryUpdateActionInstances(processId, "mbProvisionWait"));
					OracleJdbcConnection
							.crmDatabaseUpdateQuery(queryUpdateProcessInstances(processId, "MBSubscription"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println("starting provisioning");
			String provisioningSuccess = RestApiAutomation.provisioningSuccessSignalApiBody(processId,
					"MBprovisioningSignal");
			System.out.println(provisioningSuccess);
			RestApiAutomation.httpPostTest(Constant.cue_Update, provisioningSuccess);
			System.out.println("provisioning is completed, now the customer should be active");

			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean checkForAnyActionIsCompletedInSuccess(String orderId, String actionName) {

		try {
			ResultSet resultSet = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryActionInstanceWithOrderIdAndActionName(orderId, actionName));

			while (resultSet.next()) {
				String action = resultSet.getString("NAME");
				String status = resultSet.getString("STATUS");
				String result = resultSet.getString("RESULT");

				if (action.equalsIgnoreCase(actionName) && "FIN".equalsIgnoreCase(status)
						&& "SUCCESS".equalsIgnoreCase(result)) {
					return true;
				}
				if (actionName.equalsIgnoreCase(actionName) && "FIN".equalsIgnoreCase(status)
						&& "ERROR".equalsIgnoreCase(result)) {
					System.err.println(String.format("The action %s is in error,  pls investigate", actionName));
					return false;
				}
				if (actionName.equalsIgnoreCase(actionName) && "ERR".equalsIgnoreCase(status)) {
					System.err.println(String.format("The action %s is in error, pls investigate", actionName));
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String emailIdUsingCustomerNumber(String customerNumber) {
		String emailId = "";
		try {
			ResultSet getEmailId = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryLatestExistingCustomerEmailId(customerNumber));
			while (getEmailId.next()) {
				emailId = getEmailId.getString("EMAIL_ADDRESS");
				System.out.println("Email Id of customer: " + emailId);
				Reporter.log("Email Id of Customer: " + emailId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emailId;
	}

	public static boolean checkStatusOfOrderIsCompleted(String orderNumber) {
		try {
			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
				ResultSet resultSet = OracleJdbcConnection.crmDatabaseSelectQuery(queryOrderStatus(orderNumber));
				while (resultSet.next()) {
					String status = resultSet.getString("STATUS");
					System.out.println("Status of Order: " + status);
					if (status.equalsIgnoreCase("COMPLETED")) {
						return true;

					} else if (!status.equalsIgnoreCase("COMPLETED")) {
						System.err.format("Test is failed as the order status is %s, ", status);
						return false;
					}
				}
				sleep(Constant.DEFAULT_SLEEP_TIME);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void select_change_my_plan_option() {

		PageFactory.initElements(driver, My_Subscription_Change_My_Plan_Page.class);

		My_Subscription_Change_My_Plan_Page.change_my_plan_tab.click();
		MVNOProjectSpecific.scrollDownToView(My_Subscription_Change_My_Plan_Page.change_my_plan_tab);
	}

	public static void change_My_Plan_Maintenance_Data_Options() {

		// Click on change plan tab
		PageFactory.initElements(driver, My_Subscription_Change_My_Plan_Page.class);
		if (My_Subscription_Change_My_Plan_Page.our_offer_Base_Plan.isSelected()) {
			// CHOOSE YOUR DATA
			if (My_Subscription_Change_My_Plan_Page.our_offer_flex_default_data.isSelected()) {
				My_Subscription_Change_My_Plan_Page.our_offer_flex_boost_data.click();
			} else {
				My_Subscription_Change_My_Plan_Page.our_offer_flex_default_data.click();
			}
		} else {

			My_Subscription_Change_My_Plan_Page.our_offer_Base_Plan.click();
			My_Subscription_Change_My_Plan_Page.our_offer_flex_default_data.click();
			try {
				throw new Exception("The offer is not selected by default, Test shdould be failed");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void change_My_Plan(String currentPlan, String planToBe) {

		// Click on change plan tab
		PageFactory.initElements(driver, My_Subscription_Change_My_Plan_Page.class);
		// check for the

		if (My_Subscription_Change_My_Plan_Page.our_offer_Base_Plan.isSelected()) {
			// CHOOSE YOUR DATA
			if (My_Subscription_Change_My_Plan_Page.our_offer_flex_default_data.isSelected()) {
				My_Subscription_Change_My_Plan_Page.our_offer_flex_boost_data.click();
			} else {
				My_Subscription_Change_My_Plan_Page.our_offer_flex_default_data.click();
			}
		} else {

			My_Subscription_Change_My_Plan_Page.our_offer_Base_Plan.click();
			My_Subscription_Change_My_Plan_Page.our_offer_flex_default_data.click();
			try {
				throw new Exception("The offer is not selected by default, Test shdould be failed");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void change_My_Plan_Maintenance_Text_Options() {

		// Click on change plan tab
		PageFactory.initElements(driver, My_Subscription_Change_My_Plan_Page.class);
		My_Subscription_Change_My_Plan_Page.change_my_plan_tab.click();

		if (My_Subscription_Change_My_Plan_Page.our_offer_Base_Plan.isEnabled()) {

			// CHOOSE YOUR TEXTS
			if (My_Subscription_Change_My_Plan_Page.our_offer_default_text.isSelected()) {

				My_Subscription_Change_My_Plan_Page.our_offer_flex_boost_text.click();

			} else {
				My_Subscription_Change_My_Plan_Page.our_offer_default_text.click();
			}
		}

	}

	public static void verify_My_current_Plan(String activePlanCode) {

		// Click on change plan tab

		PageFactory.initElements(driver, My_Subscription_Change_My_Plan_Page.class);
		// CHOOSE YOUR DATA
		if (activePlanCode.equalsIgnoreCase("UKMV08MainMOB67")) {

			// verify active plan is selected
			Assert.assertTrue(waitForAnElement(My_Subscription_Change_My_Plan_Page.our_offer_UK_JOi_Small).isEnabled());

		}
		if (activePlanCode.equalsIgnoreCase("UKMV08MainMOB71")) {

			// verify active plan is selected
			Assert.assertTrue(My_Subscription_Change_My_Plan_Page.our_offer_UK_JOi_Medium.isEnabled());

		}
	}

	public static void Change_My_Pice_Plan(String planToBeChanged) {

		// Click on change plan tab
		PageFactory.initElements(driver, My_Subscription_Change_My_Plan_Page.class);
		// Change plan to some other price pla
		if (planToBeChanged.equalsIgnoreCase("UKMV08MainMOB67")) {

			// Change plan to some other price plan
			My_Subscription_Change_My_Plan_Page.our_offer_UK_JOi_Medium.click();

		}
		if (planToBeChanged.equalsIgnoreCase("UKMV08MainMOB71")) {

			// Checge plan to so other price plan

			My_Subscription_Change_My_Plan_Page.our_offer_UK_JOi_Medium.click();
		}
	}

	public static void change_My_Plan_Maintenance_Minutes_Options() {

		// Click on change plan tab
		PageFactory.initElements(driver, My_Subscription_Change_My_Plan_Page.class);
		My_Subscription_Change_My_Plan_Page.change_my_plan_tab.click();

		if (My_Subscription_Change_My_Plan_Page.our_offer_Base_Plan.isEnabled()) {

			// CHOOSE YOUR TEXTS
			if (My_Subscription_Change_My_Plan_Page.our_offer_default_text.isSelected()) {

				My_Subscription_Change_My_Plan_Page.our_offer_flex_boost_text.click();

			} else {
				My_Subscription_Change_My_Plan_Page.our_offer_default_text.click();
			}
		}

	}

	public static int FR_GetOfferIdFromPricePlanName(String pricePlan) {

		int offerID = 0;

		if ((pricePlan.contains("Frat")) || (pricePlan.equalsIgnoreCase("JOi Fraternit�"))
				|| (pricePlan.equalsIgnoreCase("JOi Fraternity"))) {
			offerID = 841;
		}
		if ((pricePlan.contains("Unit")) || (pricePlan.equalsIgnoreCase("JOi Unite"))
				|| (pricePlan.equalsIgnoreCase("JOi Unity"))) {
			offerID = 824;
		}

		return offerID;

	}

	public static void getProductIds(String pricePlan) {

		int offerId = FR_GetOfferIdFromPricePlanName(pricePlan);
		System.out.println("offer Id: " + offerId);

		String OfferProductId = "";
		String productId = "";

		String queryGetProducts = "SELECT * FROM PRD_OFFER_PRODUCTS WHERE OFR_ID = " + offerId + "";
		ResultSet productDetails = OracleJdbcConnection.crmDatabaseSelectQuery(queryGetProducts);
		int i = 0;
		String[] offerProductIds = new String[10];
		String[] productIds = new String[10];

		try {
			while (productDetails.next()) {

				OfferProductId = productDetails.getString("PRD_ID");
				String offerProductId = productDetails.getString("ID");

				offerProductIds[i] = OfferProductId;
				productIds[i] = offerProductId;
				i++;

			}
			System.out.println(Arrays.toString(offerProductIds));
			System.out.println(Arrays.toString(productIds));

			for (int j = 0; j < offerProductIds.length; j++) {

				if (!(offerProductIds[j] == null)) {
					OfferProductId = offerProductIds[j];
					System.out.println("product Id: " + OfferProductId);
					productId = productIds[j];
					System.out.println("product Offer Id: " + productId);

					// If 'mbsubscription'

					// If not 'mbsubscription'

				} else
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void continueAfterChangeMyPlan() throws InterruptedException {

		PageFactory.initElements(driver, My_Subscription_Change_My_Plan_Page.class);
		WebElement continueButton = waitForAnElement(
				My_Subscription_Change_My_Plan_Page.change_my_plan_continue_button);
		MVNOProjectSpecific.scrollDownToView(continueButton);
		continueButton.click();

		// click on submit button
		(waitForAnElement(
				My_Subscription_Change_My_Plan_Page.change_my_plan_submit_plan_and_monthly_payment_change_button))
						.click();
		// confirming the order
		// WebElement confirmButton =

		try {
			for (int i = 0; i < 3; i++) {
				WebElement confirmButton = My_Subscription_Change_My_Plan_Page.change_my_plan_confirm_button;
				if (confirmButton.isDisplayed()) {

					confirmButton.click();
					System.err.println(String.format("Confirm Button is CLICKED n = %d", i));
					sleep(1000);
				} else
					break;
			}
		} catch (NoSuchElementException e1) {

		} catch (StaleElementReferenceException e2) {

		} finally {
			Reporter.log("Upgrade order is successfully submitted", true);
		}

	}

	// check the status of Upgrade Order
	private static String upgradeOrder_Created(String customerId) {
		String orderNumber = null;
		String orderId = null;

		try {
			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {

				if (orderNumber == null) {
					ResultSet resultSet = OracleJdbcConnection
							.crmDatabaseSelectQuery(queryToFindUpgradeOrderwithCustid(customerId));

					while (resultSet.next()) {
						orderNumber = resultSet.getString("ORD_NUMBER");
						orderId = resultSet.getString("ID");
						sleep(1000);
					}
				} else
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderNumber + "@" + orderId;
	}

	public static String orderNumberForUpgradeOrder(String customerId) {

		String[] OrderDetails = upgradeOrder_Created(customerId).split("@");
		String orderNumber = OrderDetails[0];

		System.out.println("Order Number: " + orderNumber);
		return orderNumber;

	}

	public static String orderIdForUpgradeOrder(String customerId) {
		String[] OrderDetails = upgradeOrder_Created(customerId).split("@");
		String orderId = OrderDetails[1];
		System.out.println("Order ID: " + orderId);
		return orderId;

	}

}