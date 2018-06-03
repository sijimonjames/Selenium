package com.acneuro.test.automation.mvno_project_library;

import static com.acneuro.test.automation.db_connection_libraries.OracleDBUtils.*;
import static com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific.*;
import static java.lang.Thread.sleep;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.acneuro.test.automation.db_connection_libraries.OracleJdbcConnection;
import com.acneuro.test.automation.libraries.BaseClass;
import com.acneuro.test.automation.libraries.ConfigLib;
import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.libraries.Generic;
import com.acneuro.test.automation.libraries.RestApiAutomation;
import com.acneuro.test.automation.page_objects.MyJOi_Header_And_Home_Page;
import com.acneuro.test.automation.page_objects.My_JOi_Account_Creation_Page;
import com.acneuro.test.automation.page_objects.My_Subscription_Manage_Settings_Page;

public class UKProjectSpecific extends ConfigLib {

	public UKProjectSpecific() {
		driver = BaseClass.driver;
	}

	public static void ukUrlSelection() {
		// get the URL of France English website
		String ulUrl = Generic.urlSelection("UK");
		driver.get(ulUrl);

		// Hide the cookie message
		MVNOProjectSpecific.hideCookieMessage();
	}

	public static void Login_Page_MyJOi() {
		PageFactory.initElements(driver, MyJOi_Header_And_Home_Page.class);
		MyJOi_Header_And_Home_Page.Header_Page_MyJOi.click();
	}

	public static void JOi_LIBERTY_creation_page() {
		// Click on 'Joi Liberty' button
		driver.findElement(By.xpath("//a [text()='JOi LIBERTY']")).click();
	}

	public static void Joi_Liberty_price_plan() {
		// check the radio button 'Joi Liberty'
		// driver.findElement(By.xpath("//div[2]/label[1]/p[1]/b")).click();
		driver.findElement(By.xpath("//b[contains(text(), 'JOi Liberty')]")).click();
		try {
			sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void Joi_Medium_price_plan() {

		driver.findElement(By.xpath("//p/b[contains(text(), 'JOi Medium')]")).click();
	}

	public static void Joi_Small_price_plan() {

		driver.findElement(By.xpath("//p/b[contains(text(), 'JOi Small')]")).click();

	}

	public static void Joi_X_Large_price_plan() {

		driver.findElement(By.xpath("//p/b[contains(text(), 'JOi X-Large')]")).click();
	}

	public static void Joi_Base_price_plan() {

		try {
			// check the radio button 'Joi Liberty'
			driver.findElement(By.xpath("//*[contains(text(), 'JOi Flex')]")).click();
			sleep(500);
			driver.findElement(By.xpath("//*[@id='UKMV1409OF53']/section[1]/div[3]/label[1]")).click();
			sleep(500);
			driver.findElement(By.xpath("//*[@id='UKMV1409OF53']/section[2]/div[3]/label[1]")).click();
			sleep(500);
			driver.findElement(By.xpath("//*[@id='UKMV1409OF53']/section[3]/div[3]/label[1]")).click();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void Our_Offer_add_to_basket() {
		// work for all 'Add To Basket' cases
		boolean enable = driver.findElement(By.cssSelector("input[value='ADD TO BASKET']")).isEnabled();
		System.out.println(enable);
		if (enable == true) {
			driver.findElement(By.cssSelector("input[value='ADD TO BASKET']")).click();
		}
	}

	public static void addToBasketWithoutPorting() {
		// work for all 'Add To Basket' cases

		WebElement element = driver.findElement(By.xpath("//div/input[5]"));

		scrollWebPage(element);
		element.click();
	}

	public static void Check_Out_Order() {
		// Order check out button click

		WebElement checkOut = driver.findElement(By.xpath("//section/div/article/section/section/h6[3]"));
		scrollWebPage(checkOut);
		driver.findElement(By.linkText("CHECK OUT")).click();
	}

	public static String createUkCustomerAccount() {

		String uniqueEmailId = uniqueEmailIdForUkCustomer();

		WebElement emailAddress = driver.findElement(By.name("email-fieldset__email"));

		emailAddress.sendKeys(uniqueEmailId);

		driver.findElement(By.name("email-fieldset__confirm-email")).sendKeys(uniqueEmailId);
		driver.findElement(By.name("password-fieldset__password")).sendKeys(Constant.DEFAULT_MYJOI_PASSWORD);
		driver.findElement(By.name("password-fieldset__confirm-password")).sendKeys(Constant.DEFAULT_MYJOI_PASSWORD);

		scrollWebPage(emailAddress);

		waitForAnElement(driver.findElement(By.xpath("//a [text()='CONTINUE']"))).click();

		return uniqueEmailId;

	}

	public static void customerDetails(String Middle_Name, String Last_Name, String DOB, String Phone) {
		try {
			// drop down selection
			PageFactory.initElements(driver, My_JOi_Account_Creation_Page.class);
			WebElement ddList = waitForAnElement(My_JOi_Account_Creation_Page.customer_title);

			Select select = new Select(ddList);
			// Assume the select has 5 options
			Random random = new Random();
			// random.nextInt() generated is 0 inclusive
			int optionIndex = random.nextInt(select.getOptions().size());
			if (optionIndex == 0) {
				optionIndex = optionIndex + 1;
			}

			select.selectByIndex(optionIndex);

			// First name
			My_JOi_Account_Creation_Page.customer_first_name.sendKeys(username);
			// middle name
			My_JOi_Account_Creation_Page.customer_middle_name.sendKeys(Middle_Name);
			// Last name
			My_JOi_Account_Creation_Page.customer_last_name.sendKeys(Last_Name);
			// dob
			WebElement birthDate = My_JOi_Account_Creation_Page.customer_date_of_birth;
			// DOB field need a click before send the value
			birthDate.click();
			sleep(2000);
			birthDate.sendKeys(DOB + "\n");
			sleep(2000);
			// phone number
			My_JOi_Account_Creation_Page.customer_phone_number.sendKeys(Phone);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static void contactAndBillingAdress(String zipcode, String street) {

		try {

			PageFactory.initElements(driver, My_JOi_Account_Creation_Page.class);
			// post code
			My_JOi_Account_Creation_Page.customer_postCode.sendKeys(zipcode);
			// Street
			My_JOi_Account_Creation_Page.customer_house_number.sendKeys(street);
			// Click to search address

			WebElement adressButton = My_JOi_Account_Creation_Page.customer_find_address_button;

			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {

				if (adressButton.isEnabled()) {
					adressButton.click();
					break;
				}
				sleep(3000);
			}

			WebElement addressList = waitForAnElement(My_JOi_Account_Creation_Page.customer_address_drop_down);

			Select select = new Select(addressList);

			// Assume the select has 5 options
			Random random = new Random();

			// Get a random number between 0 and 3 (that is size - 1,
			// random.nextInt() generated is 0 inclusive, 4 exclusive.
			int optionIndex = random.nextInt(select.getOptions().size() - 1);

			// Increment optionIndex by 1
			select.selectByIndex(optionIndex++);

			WebElement createAccountButton = My_JOi_Account_Creation_Page.customer_create_account_button;

			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {

				if (createAccountButton.isEnabled()) {

					createAccountButton.click();
					break;
				}
				sleep(3000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void continueFromAccountCreationPage() {
		// post code
		WebElement welcomeMessage = waitForAnElement(driver.findElement(By.xpath("//section/div/article/h1")));
		MVNOProjectSpecific.verifyTitleOfPage("JOi Account Created");
		scrollWebPage(welcomeMessage);
		driver.findElement(By.cssSelector("a[href='/uk/our-offer/order']")).click();
	}

	public static void continue_step_3() {
		// Click to next page from step 3
		WebElement continueStep3 = driver.findElement(By.xpath("//form/dl/dd[2]/div/section/a[1]"));
		scrollWebPage(continueStep3);
		continueStep3.click();
	}

	public static void Bank_details(String Bank_Name, String Account_Number, String Sort_Code) {
		try {
			WebElement element = driver.findElement(By.name("bank-account__name-of-bank"));
			element.sendKeys(Bank_Name);
			driver.findElement(By.name("bank-account__account-number")).sendKeys(Account_Number);
			driver.findElement(By.name("bank-account__sort-code")).sendKeys(Sort_Code);
			scrollWebPage(element);
			driver.findElement(By.xpath("//dl/dd[3]/div/fieldset[2]/label/input")).click();
			sleep(1000);
			driver.findElement(By.xpath("//dl/dd[3]/div/section/a[1]")).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void orderSummaryPageForNewCustomer(String Rep_ID) {
		try {

			WebElement elementToScroll = driver
					.findElement(By.xpath("//*[@id='panel3']/fieldset[6]/div/section/div/div[1]/p"));
			scrollDownToView(elementToScroll);
			Thread.sleep(1000);
			scrollDownToView(elementToScroll);
			Thread.sleep(1000);
			WebElement MarkAs = driver.findElement(By.xpath("//*[@id='panel3']/fieldset[9]/label"));
			int Width = MarkAs.getSize().width;
			int Height = MarkAs.getSize().height;
			int MyX = (Width * 95) / 100;// spot to click is at 95% of the width
			int MyY = Height / 2;// anywhere above Height/2 works
			Actions Actions = new Actions(driver);
			Actions.moveToElement(MarkAs, MyX, MyY);
			Actions.click().build().perform();

			// driver.findElement(By.xpath("//*[@id='panel3']/fieldset[9]/label")).click();
			driver.findElement(By.xpath("//article/form/dl/dd[4]/div/fieldset[10]/label")).click();
			driver.findElement(By.xpath("//article/form/dl/dd[4]/div/fieldset[11]/label")).click();

			WebElement finalPage = driver.findElement(By.name("teamid__teamid"));
			finalPage.sendKeys(Rep_ID);

			scrollDownToView(finalPage);
			driver.findElement(By.xpath("//div/article/form/section/input")).click();
			// Explicit wait until Datacash external page opens up

			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void orderSummaryPageForExitstingCustomer(String Rep_ID) {
		try {

			WebElement elementToScroll = driver.findElement(By.xpath("//*[@id='panel3']/fieldset[4]/div/ul/li[2]/h6"));

			scrollDownToView(elementToScroll);
			Thread.sleep(1000);
			scrollDownToView(elementToScroll);
			Thread.sleep(1000);

			WebElement MarkAs = driver.findElement(By.xpath("//*[id('panel3')]/fieldset[8]/label"));
			int Width = MarkAs.getSize().width;
			int Height = MarkAs.getSize().height;
			int MyX = (Width * 95) / 100;// spot to click is at 95% of the width
			int MyY = Height / 2;// anywhere above Height/2 works
			Actions Actions = new Actions(driver);
			Actions.moveToElement(MarkAs, MyX, MyY);
			Actions.click().build().perform();

			sleep(500);

			driver.findElement(By.xpath("//*[id('panel3')]/fieldset[9]/label")).click();
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
		try {
			WebElement cardList = driver.findElement(By.xpath("//tbody/tr[4]/td/select"));
			Select s = new Select(cardList);
			s.selectByVisibleText(Card_Type);

			// Card Number
			driver.findElement(By.name("card_number")).sendKeys(Card_Number);

			// Expiry month and year, Month retain same and year need to change
			// to a
			// future year
			WebElement yearList = driver.findElement(By.name("exp_year"));
			Select s1 = new Select(yearList);
			s1.selectByVisibleText(Expiry_Year);
			driver.findElement(By.name("cv2_number")).sendKeys(CVV);
			driver.findElement(By.xpath("/html/body/form/div/input")).click();
			sleep(7000); // New page loading
			// Authentication
			driver.findElement(By.xpath("//table/tbody/tr[1]/td[1]/input")).click();
			sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Resolving tasks API call, future should be done from cuET-Reload
	public static void resolvingTasksSignal(String processId, String taskId) {
		String resolvingTasksApiBody = RestApiAutomation.resolvingTasksApiBody(processId, taskId);
		System.out.println(resolvingTasksApiBody);
		RestApiAutomation.httpPostTest(Constant.cue_Update, resolvingTasksApiBody);
	}

	// Shipping API call
	public static void workerTriggerForBillingUpdate(String workerUrl) {

		try {
			driver = new FirefoxDriver();
			// implicit wait to complete each browser action
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get(workerUrl);
			// to maximize the opening browser window
			driver.manage().window().maximize();
			// Trigger worker 'QueryOrderStatusJob'
			WebElement workerQueryOrderStatusJob = driver.findElement(By.xpath("//div[3]/div/div[3]/div[2]/a[1]"));
			scrollDownToView(workerQueryOrderStatusJob);
			workerQueryOrderStatusJob.click();
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
			// Trigger worker 'QuerySubscriptionJob'
			WebElement workerQuerySubscriptionJob = driver
					.findElement(By.xpath("//div[2]/div[3]/div/div[16]/div[2]/a[1]"));
			scrollDownToView(workerQuerySubscriptionJob);
			workerQuerySubscriptionJob.click();
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean processUpfrontPayment(String orderID) {

		try {

			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
				ResultSet resultSet = OracleJdbcConnection.crmDatabaseSelectQuery(
						queryActionInstanceWithOrderIdAndActionName(orderID, "ofUpfrontPaymentWait"));

				while (resultSet.next()) {
					String actionName = resultSet.getString("NAME");
					String status = resultSet.getString("STATUS");
					String result = resultSet.getString("RESULT");

					if ("ofUpfrontPaymentWait".equals(actionName) && "FIN".equals(status) && "SUCCESS".equals(result)) {
						return true;
					}
					if ("ofUpfrontPaymentWait".equals(actionName) && "ERROR".equals(result)) {
						return false;
					}
					if ("ofUpfrontPaymentWait".equals(actionName) && !"FIN".equals(status)) {
						workerTriggerForBillingUpdate(Constant.billsrvWorkerUrl);
					}
				}
				sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// Shipping API call
	/**
	 * Shipping can be done during 1. activation of New customer - mbShipWaiting
	 * 2. Replacement of sim - simReplacementShipWait
	 */

	public static boolean processShipWait(String orderID, String ShippingProcessName) {

		try {

			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
				ResultSet resultSet = OracleJdbcConnection.crmDatabaseSelectQuery(
						queryActionInstanceWithOrderIdAndActionName(orderID, ShippingProcessName));

				while (resultSet.next()) {
					String actionName = resultSet.getString("NAME");
					String status = resultSet.getString("STATUS");
					String processId = resultSet.getString("PRI_ID");
					System.out.println("shipping process id: " + processId);

					if (ShippingProcessName.equals("mbShipWaiting") && ShippingProcessName.equals(actionName)
							&& !"FIN".equals(status)) {
						RestApiAutomation.httpPostTest(Constant.cue_Update,
								RestApiAutomation.shippingSignalApiBody(Constant.ICCID_1_GB, processId));
					} else if (ShippingProcessName.equals("simReplacementShipWait")
							&& ShippingProcessName.equals(actionName) && !"FIN".equals(status)) {
						RestApiAutomation.httpPostTest(Constant.cue_Update,
								RestApiAutomation.shippingSignalApiBody(Constant.ICCID_2_GB, processId));
						sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
					}
					if (ShippingProcessName.equals(actionName) && "FIN".equals(status)) {
						return true;
					}
					if (ShippingProcessName.equals(actionName) && "ERR".equals(status)) {
						return false;
					}

				}
				sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void LoginToUKMyJoiWithLatestEmailID(String emailId) {

		openNewBrowserwithinTests();
		ukUrlSelection();
		try {

			hideCookieMessage();
			// String emailId = existingEmailIdForUkCustomer();
			driver.findElement(By.xpath("//section[1]/div/nav/ul/li[3]/a")).click();
			// login with existing customer details
			driver.findElement(By.name("login-fieldset__email")).sendKeys(emailId);
			driver.findElement(By.name("login-fieldset__password")).sendKeys(Constant.DEFAULT_MYJOI_PASSWORD);
			sleep(400);
			driver.findElement(By.cssSelector("input[value = 'LOGIN']")).click();
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Move to common Project specific
	public static String LoginToUKMyJoiWithActiveSubscription() {

		String country = "GB";
		String latestActiveCinNumber = "";
		String customerId = "";
		String myJOiNumber = "";
		String userName = "";

		try {
			ResultSet CIN_number = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryLatestActiveCinProductnumber(country));

			while (CIN_number.next()) {
				latestActiveCinNumber = CIN_number.getString("max(CIN_NUMBER)");
			}

			ResultSet cinProductTable = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryCinProductTableWithCinProductNumber(latestActiveCinNumber));

			while (cinProductTable.next()) {
				customerId = cinProductTable.getString("CUSTOMER_ID");
				System.out.println("Customer ID: " + customerId);
				myJOiNumber = cinProductTable.getString("ATTRIBUTE1");
				System.out.println("MyJoi Number: " + myJOiNumber);
			}
			// Username from Cust_Users table
			ResultSet queryUsername = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryUsernameFromCustUsersTable(customerId));
			while (queryUsername.next()) {
				userName = queryUsername.getString("USERNAME");
				System.out.println("User Name: " + userName);
			}
			// Login with current browser
			LoginToUKMyJoiWithLatestEmailID(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return myJOiNumber;
	}

	public static void LoginToUKMyJoiWithMsisdn(String msisdn) {

		String customerId = "";
		String userName = "";
		try {
			ResultSet cinProductTable = OracleJdbcConnection.crmDatabaseSelectQuery(queryCinProductsWithMsisdn(msisdn));

			while (cinProductTable.next()) {
				customerId = cinProductTable.getString("CUSTOMER_ID");
				System.out.println("Customer ID: " + customerId);
			}
			// Username from Cust_Users table
			ResultSet queryUsername = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryUsernameFromCustUsersTable(customerId));
			while (queryUsername.next()) {
				userName = queryUsername.getString("USERNAME");
				System.out.println(String.format("Login to myJOi with Username %s", userName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			openNewBrowserwithinTests();
			ukUrlSelection();
			// Login to UK MYJOi
			driver.findElement(By.xpath("//section[1]/div/nav/ul/li[3]/a")).click();
			// login with existing customer details
			driver.findElement(By.name("login-fieldset__email")).sendKeys(userName);
			driver.findElement(By.name("login-fieldset__password")).sendKeys(Constant.DEFAULT_MYJOI_PASSWORD);
			sleep(400);
			driver.findElement(By.cssSelector("input[value = 'LOGIN']")).click();
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void ActivateSimAfterLogin() {
		try {

			WebElement elementToScroll = driver.findElement(By.xpath("//section[1]/div/article/p/strong"));
			scrollDownToView(elementToScroll);
			driver.findElement(By.cssSelector("a[href*= 'NC_MV_SUB_ACTV_REM1']")).click();
			sleep(2000);
			WebElement activateSim = driver.findElement(By.cssSelector("input[name='iccid']"));
			activateSim.sendKeys(Constant.ICCID_1_GB);
			scrollDownToView(activateSim);
			driver.findElement(By.cssSelector("input[value = 'ACTIVATE YOUR SIM']")).click();
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void activationOfSIMReplacement(String Reason) {
		try {

			WebElement elementToScroll = driver.findElement(By.xpath("//section[1]/div/article/p/strong"));
			scrollDownToView(elementToScroll);
			if (Reason.equalsIgnoreCase("LOST")) {
				driver.findElement(By.cssSelector("a[href*= 'NC_MV_SUB_SIM_LOST_DISP']")).click();
				sleep(2000);
			}
			if (Reason.equalsIgnoreCase("STOLEN")) {
				driver.findElement(By.cssSelector("a[href*= 'NC_MV_SUB_SIM_STLN_DISP']")).click();
				sleep(2000);
			}
			if (Reason.equalsIgnoreCase("OTHER")) {
				driver.findElement(By.cssSelector("a[href*= 'NC_MV_SUB_SIM_TYPE_CHNG']")).click();
				sleep(2000);
			}
			WebElement activateSim = driver.findElement(By.cssSelector("input[name='iccid']"));
			activateSim.sendKeys(Constant.ICCID_2_GB);
			scrollDownToView(activateSim);
			driver.findElement(By.cssSelector("input[value = 'ACTIVATE YOUR SIM']")).click();
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean provisioningCheckForUK(String orderId) {

		try {
			checkIfActionExistsForOrder(orderId, "mbProvisionWait");
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);

			for (int i = 0; i < Constant.PROVISIONING_RETRY_COUNT; i++) {
				ResultSet resultSet = OracleJdbcConnection.crmDatabaseSelectQuery(
						queryActionInstanceWithOrderIdAndActionName(orderId, "mbProvisionWait"));

				while (resultSet.next()) {
					String actionName = resultSet.getString("NAME");
					String result = resultSet.getString("RESULT");
					String processId = resultSet.getString("PRI_ID");
					String status = resultSet.getString("STATUS");

					if ("mbProvisionWait".equals(actionName) && "RETRY".equals(result)) {
						OracleJdbcConnection.crmDatabaseUpdateQuery(
								queryUpdateActionInstanceScheduledTime(processId, "mbProvisionStart"));
						sleep(Constant.DEFAULT_LONG_SLEEP_TIME); // 2mins
					}

					if ("mbProvisionWait".equals(actionName) && "FIN".equals(status) && "SUCCESS".equals(result)) {

						System.err.println("provisioning is Completed, now the customer should be active");
						return true;
					}
					if ("mbProvisionWait".equals(actionName) && "ERR".equals(status)) {
						return false;
					}

					if ("mbProvisionWait".equals(actionName) && "WAI".equals(status)) {
						// Raise a provisioning response worker run
						MVNOProjectSpecific.workerResposeProvisioning_UK();
						sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
					}

					if ("mbProvisionWait".equals(actionName) && "FIN".equals(status)
							&& "ERROR".equalsIgnoreCase(result)) {

						Reporter.log("Provisioning is in error, Faking DB with a prov message", true);

						OracleJdbcConnection.crmDatabaseUpdateQuery(queryUpdateOrderLines(orderId));
						OracleJdbcConnection.crmDatabaseUpdateQuery(queryUpdateCinProducts(orderId));
						OracleJdbcConnection.crmDatabaseUpdateQuery(queryUpdateOrdOrders(orderId));
						OracleJdbcConnection
								.crmDatabaseUpdateQuery(queryUpdateActionInstances(processId, "mbProvisionWait"));
						OracleJdbcConnection
								.crmDatabaseUpdateQuery(queryUpdateProcessInstances(processId, "MBSubscription"));

						sleep(Constant.DEFAULT_SLEEP_TIME);
						System.out.println("starting provisioning");
						String provisioningSuccess = RestApiAutomation.provisioningSuccessSignalApiBody(processId,
								"MBprovisioningSignal");
						System.out.println(provisioningSuccess);
						RestApiAutomation.httpPostTest(Constant.cue_Update, provisioningSuccess);
						sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private static void selectPricePlanFromOurOffer(String pricePlan) {

		Actions mouseHovering = new Actions(driver);
		WebElement ourOffer = waitForAnElement(driver.findElement(By.xpath("//span[contains(text(),'OUR OFFER')]")));
		mouseHovering.moveToElement(ourOffer).perform();

		WebElement toPricePlan = waitForAnElement(driver.findElement(By.linkText(pricePlan)));

		mouseHovering.moveToElement(toPricePlan).click().build().perform();

		// Click on 'Order Now'
		driver.findElement(By.xpath("//div[@class='message']//a[text()='ORDER NOW']")).click();

	}

	public static void joiLibertyOrderSelectionFromOurOffer() {

		String pricePlan = "JOi Liberty";
		selectPricePlanFromOurOffer(pricePlan);

	}

	public static void joiSmallOrderSelectionFromOurOffer() {

		String pricePlan = "JOi Small";
		selectPricePlanFromOurOffer(pricePlan);

	}

	public static void joiMediumOrderSelectionFromOurOffer() {

		String pricePlan = "JOi Medium";
		selectPricePlanFromOurOffer(pricePlan);

	}

	public static void joiXLargeOrderSelectionFromOurOffer() {

		String pricePlan = "JOi X-Large";
		selectPricePlanFromOurOffer(pricePlan);

	}

	public static String selectLatestOrderFromMySubscription() {

		int n = 0;
		String cinProductNumber = "";
		String joiNumber = "";
		String status = "";
		String customerNumber = "";
		String actualJoiNumber = "";

		// find the latest customer number
		ResultSet resultset = null;
		resultset = OracleJdbcConnection.crmDatabaseSelectQuery(queryMaxCustomerNumberUKCustomer(username));
		try {
			while (resultset.next()) {

				customerNumber = resultset.getString("max_CUSTOMER_NUMBER");
				System.out.println("customerNumber: " + customerNumber);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// find the latest active CIN product number
		ResultSet resultsetCinProduct = null;
		resultsetCinProduct = OracleJdbcConnection.crmDatabaseSelectQuery(queryLatestCinProductnumber(customerNumber));
		try {
			while (resultsetCinProduct.next()) {

				cinProductNumber = resultsetCinProduct.getString("max(CIN_NUMBER)");
				System.out.println("cinProductNumber: " + cinProductNumber);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// find the active JOi MSISDN
		ResultSet resultsetMsidn = null;

		resultsetMsidn = OracleJdbcConnection.crmDatabaseSelectQuery(queryCustomerMsisdn(cinProductNumber));

		try {
			while (resultsetMsidn.next()) {

				joiNumber = resultsetMsidn.getString("ATTRIBUTE1");
				System.out.println("joiNumber from DB: " + joiNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		driver.findElement(By.xpath("//a[text()='MY SUBSCRIPTIONS']")).click();
		driver.findElement(By.xpath("//li/a/div/h5")).click();
		actualJoiNumber =

				getAndVerifyText("//section[1]/h5/span");
		status = getAndVerifyText("//section[1]/div/div/p");

		for (n = 1; n < 5; n++) {

			if (!actualJoiNumber.equals(joiNumber)) {
				n = n + 1;
				driver.findElement(By.xpath("//a[text()='MY SUBSCRIPTIONS']")).click();
				String nextSubscription = String.format("//li[%d]/a/div/h5", n);
				driver.findElement(By.xpath(nextSubscription)).click();

				actualJoiNumber = getAndVerifyText("//section[1]/h5/span");
				System.out.println("actual JoiNumber from MyJoi:" + actualJoiNumber);

			} else {
				System.out.println("Status: " + status);
				System.out.println("Joi Number: " + actualJoiNumber);

				break;
			}

		}
		return actualJoiNumber;
	}

	public static void selectMySubscriptionFromMyJOi(String joiNumber) {

		int n = 0;
		String actualJoiNumber = "";
		driver.findElement(By.xpath("//a[text()='MY SUBSCRIPTIONS']")).click();
		driver.findElement(By.xpath("//li/a/div/h5")).click();
		actualJoiNumber = getAndVerifyText("//section[1]/h5/span");

		for (n = 1; n < 6; n++) {

			if (!actualJoiNumber.equals(joiNumber)) {
				n = n + 1;
				driver.findElement(By.xpath("//a[text()='MY SUBSCRIPTIONS']")).click();
				String nextSubscription = String.format("//li[%d]/a/div/h5", n);
				driver.findElement(By.xpath(nextSubscription)).click();

				actualJoiNumber = getAndVerifyText("//section[1]/h5/span");

			} else {
				System.out.println("Joi Number: " + actualJoiNumber);
				break;
			}

		}
	}

	public static String UniquePortInNumberUK() {
		String portInNumber = "";
		String uniquePortInNumber = "";

		try {

			ResultSet resultSet = OracleJdbcConnection
					.numposDatabaseSelectQuery(queryLatestPortinNumberfromNumposDB("GB", Constant.UK_Portin_Number));

			while (resultSet.next()) {
				portInNumber = resultSet.getString("MAX_PORTING_NUMBER");
				System.out.println(portInNumber);

			}
			portInNumber = portInNumber.substring(2);
			uniquePortInNumber = "07" + String.valueOf(((Integer.parseInt(portInNumber)) + 1));

			System.out.println("Customer PortIn number: " + uniquePortInNumber);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return uniquePortInNumber;
	}

	public static void ukPortInFromManageSettings(String myJoiNumber, String portInNumber) {

		try {
			PageFactory.initElements(driver, My_Subscription_Manage_Settings_Page.class);

			WebElement status = driver.findElement(By.xpath("//section[1]/div/div[2]/p"));

			scrollDownToView(status);

			driver.findElement(By.xpath("//article/section[2]/a[2]")).click();

			My_Subscription_Manage_Settings_Page.manage_settings_Port_In.click();

			driver.findElement(By.xpath("//article/form/input[2]")).sendKeys(myJoiNumber);
			driver.findElement(By.xpath("//article/form/input[3]")).sendKeys(portInNumber);
			WebElement pacCode = driver.findElement(By.xpath("//article/form/input[4]"));
			pacCode.sendKeys(Constant.UK_PAC_CODE);

			scrollDownToView(pacCode);

			driver.findElement(By.xpath("//div/article/form/label")).click();
			driver.findElement(By.xpath("//article/form/section[7]/input")).click();
			driver.quit();
			sleep(Constant.DEFAULT_LONG_SLEEP_TIME);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public static void ukManageSettingsFromSubscriptions(String activeJoiNumber) {
		String actualJOiNumber = "";
		int n;

		try {
			for (n = 1; n < 6; n++) {

				if (!actualJOiNumber.equals(activeJoiNumber)) {

					/**
					 * This will run until find a match with Active joi number
					 */
					// click on My Subscription
					driver.findElement(By.xpath("//a[text()='MY SUBSCRIPTIONS']")).click();
					String xpath = "//li[" + n + "]/a/div/h5";
					System.out.println(xpath);
					WebElement Subscription = driver.findElement(By.xpath(xpath));
					Subscription.click();
					actualJOiNumber = getAndVerifyText("//section[1]/h5/span");

				} else {// if found the number, click on Manage Settings option
					System.out.println("actual JoiNumber from MyJoi:" + actualJOiNumber);
					// manage settings element
					driver.findElement(By.xpath("//article/section[2]/a[2]")).click();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void selectAnyOptionFromMySettings(String pathToElement) {

		// Click on the element
		driver.findElement(By.xpath(pathToElement)).click();
	}

	public static void selectAndSubmitReplaceSimFromMySettings(String pathToReasonForReplacement) {

		/**
		 * pathToReasonForReplacement should be "LOST", "STOLEN","OTHER"
		 */
		try {
			selectAnyOptionFromMySettings("//*[@id='tab-COMMANDS']/ul/li[5]/a/p");

			if (pathToReasonForReplacement.equals("LOST")) {
				driver.findElement(By.xpath("//section/div/article/form/label[1]")).click();
			}

			if (pathToReasonForReplacement.equals("STOLEN")) {
				driver.findElement(By.xpath("//section/div/article/form/label[2]")).click();
			}
			if (pathToReasonForReplacement.equals("OTHER")) {
				driver.findElement(By.xpath("//section/div/article/form/label[3]")).click();
			}

			WebElement element = driver.findElement(By.xpath("//section/div/article/form/label[1]"));

			scrollDownToView(element);
			driver.findElement(By.xpath("//section/div/article/form/label[4]")).click();

			WebDriverWait waitUntilButtonEnabled = new WebDriverWait(driver, 10);
			waitUntilButtonEnabled.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.xpath("//div/article/form/section[11]/input"))));

			WebElement submitButton = driver.findElement(By.xpath("//div/article/form/section[11]/input"));

			scrollDownToView(submitButton);

			submitButton.click();
			driver.quit();

			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void selectAndSubmitBlockHandsetFromMySettings(String ReasonForReplacement) {

		/**
		 * pathToReasonForReplacement should be "LOST", "STOLEN","OTHER"
		 */
		try {
			selectAnyOptionFromMySettings("//*[@id='tab-COMMANDS']/ul/li[2]/a/p");

			// Handset Model

			driver.findElement(By.cssSelector("input[name='model'][type='text']")).sendKeys(Constant.HANDSET_MODEL);

			// IMEI number
			driver.findElement(By.cssSelector("input[name='imei'][type='text']")).sendKeys(Constant.HANDSET_IMEI);

			// Input a reason as 'LOST' or 'STOLEN'
			if (ReasonForReplacement.equals("LOST")) {
				driver.findElement(By.xpath("//section/div/article/form/label[1]")).click();
			}

			if (ReasonForReplacement.equals("STOLEN")) {
				driver.findElement(By.xpath("//section/div/article/form/label[2]")).click();
			}

			// Scroll to view 'SAVE CHANGES' Button and click
			WebElement saveChangesButton = driver.findElement(By.xpath("//div/article/form/section[5]/input"));
			scrollDownToView(saveChangesButton);
			saveChangesButton.click();
			driver.quit();

			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void selectPortInFromMySettings() {
		selectAnyOptionFromMySettings("");
	}

	public static void selectPortOutFromMySettings() {
		selectAnyOptionFromMySettings("");
	}

	public static void ukPortOutFromManageSettings() {

		try {

			driver.findElement(By.xpath("//article/section[2]/a[2]")).click();
			driver.findElement(By.xpath("//p[text() = 'Port your JOi number to your new operator'")).click();

			driver.findElement(By.xpath("//article/form/section[2]/input")).click();

			// String portOutText =
			// getAndVerifyText("//article/form/section[2]/input");

			// Assert.assertEquals(portOutText, "Port your JOi number to your
			// new operator");

			takeScreenShot("C:/testing/workspace/Screenshots/mvno/portout/UK/portout.png");

			driver.quit();

		} catch (Exception e) {
			e.printStackTrace();
		}
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
		checkIfActionExistsForOrder(orderId, "orTerminateBilling");

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

	// This is just a check, No specific action is needed
	public static boolean checkForCancellationOfOrderIsDone(String orderId) {
		checkIfActionExistsForOrder(orderId, "orCancelCRM");

		try {
			ResultSet resultSet = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryActionInstanceWithOrderIdAndActionName(orderId, "orCancelCRM"));

			while (resultSet.next()) {
				String actionName = resultSet.getString("NAME");
				String status = resultSet.getString("STATUS");
				String result = resultSet.getString("RESULT");

				if ("orCancelCRM".equals(actionName) && "FIN".equals(status) && "SUCCESS".equals(result)) {
					return true;
				}
				if ("orCancelCRM".equals(actionName) && "FIN".equals(status) && "ERROR".equals(result)) {
					return false;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean verifyCinProductIsActive(String orderId) {
		// Verify CIN_PRODUCT with order id, should be Active
		String Query = "Select * from CIN_PRODUCTS where ORDER_ID = '" + orderId + "'";
		ResultSet cinProducts = null;

		cinProducts = OracleJdbcConnection.crmDatabaseSelectQuery(Query);
		try {
			while (cinProducts.next()) {
				String status = cinProducts.getString("STATUS");
				if ("ACT".equals(status)) {
					return true;
				} else {
					return false;
				}
			}
		} catch (

		Exception e)

		{
			e.printStackTrace();
		}
		return false;

	}

	public static boolean checkForSimReplacementOrderIsDone(String orderId) {
		boolean simReplaceFinishAction = checkIfActionExistsForOrder(orderId, "simReplacementFinish");
		Assert.assertTrue(simReplaceFinishAction);

		try {
			ResultSet resultSet = OracleJdbcConnection.crmDatabaseSelectQuery(
					queryActionInstanceWithOrderIdAndActionName(orderId, "simReplacementFinish"));

			while (resultSet.next()) {
				String actionName = resultSet.getString("NAME");
				String status = resultSet.getString("STATUS");
				String result = resultSet.getString("RESULT");

				if ("simReplacementFinish".equals(actionName) && "FIN".equals(status) && "SUCCESS".equals(result)) {
					return true;
				}
				if ("simReplacementFinish".equals(actionName) && "FIN".equals(status) && "ERROR".equals(result)) {
					return false;
				}
				if ("simReplacementFinish".equals(actionName) && "ERR".equals(status)) {
					return false;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String uniqueEmailIdForUkCustomer() {
		// Taking a unique id from existing email id

		String customerNumber = existingCustomerNumberForUkCustomer();
		String newUniqueEmailID = "";

		if (customerNumber == null) {
			newUniqueEmailID = String.format("%s_Automation_01@test.uk", username);
		} else {

			String latestEmailId = existingEmailIdForUkCustomer(customerNumber);
			String existingId = latestEmailId.split("_")[2].split("@")[0];
			String uniqueId = String.valueOf(((Integer.parseInt(existingId)) + 1));
			// Creating a new Email ID
			newUniqueEmailID = String.format("%s_Automation_%s@test.uk", username, uniqueId);
			System.out.println("New Email Id / User Name:" + newUniqueEmailID);
		}
		return newUniqueEmailID;
	}

	public static String existingCustomerNumberForUkCustomer() {
		String customerNumber = "";

		try {
			String queryCustomerNumber = queryMaxCustomerNumberUKCustomer(username);
			ResultSet getCustomerNumber = OracleJdbcConnection.crmDatabaseSelectQuery(queryCustomerNumber);
			while (getCustomerNumber.next()) {

				customerNumber = getCustomerNumber.getString("MAX_CUSTOMER_NUMBER");

				if (getCustomerNumber.wasNull()) {

					System.out.println("No Customer found with this username");
				} else {

					System.out.println("Old Customer number: " + customerNumber);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return customerNumber;
	}

	public static String existingCustomerNumberWithNoUsername() {
		String customerNumber = "";

		try {
			String queryCustomerNumber = queryMaxCustomerNumberUKCustomer();
			ResultSet getCustomerNumber = OracleJdbcConnection.crmDatabaseSelectQuery(queryCustomerNumber);
			while (getCustomerNumber.next()) {
				customerNumber = getCustomerNumber.getString("MAX_CUSTOMER_NUMBER");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return customerNumber;
	}

	public static String existingEmailIdForUkCustomer(String customerNumber) {
		String latestEmailId = "";

		try {

			ResultSet emailId = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryLatestExistingCustomerEmailId(customerNumber));
			while (emailId.next()) {
				latestEmailId = emailId.getString("EMAIL_ADDRESS");
				System.out.println("Email ID: " + latestEmailId);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return latestEmailId;
	}

	/*
	 * If more user tasks to be resolved but with different actions, add a
	 * parameter
	 */

	public static void simReplacementSuspensionOfProvisioningUsertaskFix(String orderID) {
		// Find orderNumber from Order ID

		try {
			String orderNumber = "";
			String processID = "";
			ResultSet queryOrderNumber = OracleJdbcConnection.crmDatabaseSelectQuery(queryOrderUsingOrderID(orderID));

			while (queryOrderNumber.next()) {
				orderNumber = queryOrderNumber.getString("ORD_NUMBER");
				System.out.println("SIM Replacement Order Number: " + orderNumber);
			}

			// Fix the user task by rest API call with 'Provisioning_Fixed'.
			// 'simReplacementSuspendProvFailureUserTask'
			boolean provisioningUserTask = checkIfActionExistsForOrder(orderID,
					"simReplacementSuspendProvFailureUserTask");
			Assert.assertTrue(provisioningUserTask);

			// Find processID of 'simReplacementSuspendProvFailureUserTask'
			ResultSet resultSet = OracleJdbcConnection.crmDatabaseSelectQuery(
					queryActionInstanceWithOrderIdAndActionName(orderID, "simReplacementSuspendProvFailureUserTask"));
			while (resultSet.next()) {
				processID = resultSet.getString("PRI_ID");
			}
			String requestProvisioningUserTask = RestApiAutomation
					.requestToResolveProvisioningSuspensionUserTaskAction(orderNumber, orderID, processID);
			RestApiAutomation.httpPostTest(Constant.cue_Execute, requestProvisioningUserTask);
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);

			// Check the User task is done
			boolean usertask = checkForProvisioningUsertaskIsDone(orderID, "simReplacementSuspendProvFailureUserTask");
			// Test is failed if usertask is not done
			Assert.assertTrue(usertask);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * If more user tasks to be resolved but with different actions, add a
	 * parameter
	 */

	public static void simReplacementPoliceVerificationReportUsertaskFix(String orderID) {
		// Find orderNumber from Order ID

		try {
			String orderNumber = "";
			String processID = "";
			ResultSet queryOrderNumber = OracleJdbcConnection.crmDatabaseSelectQuery(queryOrderUsingOrderID(orderID));

			while (queryOrderNumber.next()) {
				orderNumber = queryOrderNumber.getString("ORD_NUMBER");
				System.out.println("SIM Replacement Order Number: " + orderNumber);
			}

			// Fix the user task by rest API call with 'Provisioning_Fixed'.
			// 'simReplacementSuspendProvFailureUserTask'
			boolean checkForPoliceReport = checkIfActionExistsForOrder(orderID, "simReplacementPoliceReportUserTask");
			Assert.assertTrue(checkForPoliceReport);

			// Find processID of 'simReplacementSuspendProvFailureUserTask'
			ResultSet resultSet = OracleJdbcConnection.crmDatabaseSelectQuery(
					queryActionInstanceWithOrderIdAndActionName(orderID, "simReplacementPoliceReportUserTask"));
			while (resultSet.next()) {

				processID = resultSet.getString("PRI_ID");
			}
			String requestPoliceReportUserTask = RestApiAutomation
					.requestToFixPoliceVerificationUserTaskAction(orderNumber, orderID, processID);
			RestApiAutomation.httpPostTest(Constant.cue_Execute, requestPoliceReportUserTask);
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);

			// Check the User task is done
			boolean usertask = checkForAnyUsertaskIsDone(orderID, "simReplacementPoliceReportUserTask");
			// Test is failed if usertask is not done
			Assert.assertTrue(usertask);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void simReplacementActivationByProvisioningUsertaskFix(String orderID, String reason) {
		/*
		 * Two user tasks to be fixed
		 * 1.simReplacementChangeSimProvFailureUserTask
		 * 2.simReplacementResumeProvFailureUserTask
		 */
		// Find orderNumber from Order ID

		try {
			String orderNumber = "";
			String processID = "";

			boolean provFailureUsertask = checkIfActionExistsForOrder(orderID,
					"simReplacementChangeSimProvFailureUserTask");
			Assert.assertTrue(provFailureUsertask);

			ResultSet queryOrderNumber = OracleJdbcConnection.crmDatabaseSelectQuery(queryOrderUsingOrderID(orderID));

			while (queryOrderNumber.next()) {
				orderNumber = queryOrderNumber.getString("ORD_NUMBER");
				System.out.println("SIM Replacement Order Number: " + orderNumber);
			}

			// Find processID of 'simReplacementSuspendProvFailureUserTask'
			ResultSet resultSet = OracleJdbcConnection.crmDatabaseSelectQuery(
					queryActionInstanceWithOrderIdAndActionName(orderID, "simReplacementChangeSimProvFailureUserTask"));
			while (resultSet.next()) {

				processID = resultSet.getString("PRI_ID");

			}

			String requestProvisioningChangeSimUserTask = RestApiAutomation
					.requestToProvisioningChangeSimUserTaskAction(orderNumber, orderID, processID);
			System.out.println(requestProvisioningChangeSimUserTask);
			RestApiAutomation.httpPostTest(Constant.cue_Execute, requestProvisioningChangeSimUserTask);
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
			boolean changeSimUsertask = checkForProvisioningUsertaskIsDone(orderID,
					"simReplacementChangeSimProvFailureUserTask");
			Assert.assertTrue(changeSimUsertask);

			if (reason != "OTHER") {

				boolean provResumeFailureUsertask = checkIfActionExistsForOrder(orderID,
						"simReplacementChangeSimProvFailureUserTask");
				Assert.assertTrue(provResumeFailureUsertask);
				// Find processID of 'simReplacementSuspendProvFailureUserTask'
				ResultSet ResumeProvisioning = OracleJdbcConnection
						.crmDatabaseSelectQuery(queryActionInstanceWithOrderIdAndActionName(orderID,
								"simReplacementResumeProvFailureUserTask"));
				while (ResumeProvisioning.next()) {

					processID = ResumeProvisioning.getString("PRI_ID");

				}

				String requestProvisioningActivationUserTask = RestApiAutomation
						.requestToProvisioningResumeUserTaskAction(orderNumber, orderID, processID);
				System.out.println(requestProvisioningActivationUserTask);
				RestApiAutomation.httpPostTest(Constant.cue_Execute, requestProvisioningActivationUserTask);
				sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);

				boolean provisioningResumeUsertask = checkForProvisioningUsertaskIsDone(orderID,
						"simReplacementResumeProvFailureUserTask");
				Assert.assertTrue(provisioningResumeUsertask);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String customerIdFromMSISDN(String msisdn) {
		String customerNumber = "";
		String customerId = "";
		String orderID = "";

		try {
			/* get customer number from joi Number */
			ResultSet customerNumberFromCinProducts = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryCinProductsWithMsisdn(msisdn));

			while (customerNumberFromCinProducts.next()) {
				customerNumber = customerNumberFromCinProducts.getString("CUSTOMER_NUMBER");
				System.out.println("Customer Number: " + customerNumber);
				sleep(2000);
			}

			// customer ID
			ResultSet customerIdFromCinProducts = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryCustomerDetailsInCust_CustomersTable(customerNumber));

			while (customerIdFromCinProducts.next()) {
				customerId = customerIdFromCinProducts.getString("ID");
				System.out.println("Customer ID: " + customerId);
				sleep(2000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderID;
	}

	public static String simReplacementOrderdetails(String msisdn) {
		String customerNumber = "";
		String customerId = "";
		String orderID = "";

		try {
			/* get customer number from joi Number */
			ResultSet customerNumberFromCinProducts = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryCinProductsWithMsisdn(msisdn));

			while (customerNumberFromCinProducts.next()) {
				customerNumber = customerNumberFromCinProducts.getString("CUSTOMER_NUMBER");
				System.out.println("Customer Number: " + customerNumber);
				sleep(2000);
			}

			// customer ID
			ResultSet customerIdFromCinProducts = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryCustomerDetailsInCust_CustomersTable(customerNumber));

			while (customerIdFromCinProducts.next()) {
				customerId = customerIdFromCinProducts.getString("ID");
				System.out.println("Customer ID: " + customerId);
				sleep(2000);
			}
			// OrderID
			String queryOrderIdForSimReplacement = queryOrderIdFromOrderTables(customerId);
			ResultSet replaceSimProduct = OracleJdbcConnection.crmDatabaseSelectQuery(queryOrderIdForSimReplacement);
			while (replaceSimProduct.next()) {
				orderID = replaceSimProduct.getString("MAX(ORD_ID)");
				System.out.println("SIM Replacement Order ID: " + orderID);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderID;
	}

	public static boolean checkForProvisioningUsertaskIsDone(String orderId, String provisioningAction) {

		try {
			ResultSet resultSet = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryActionInstanceWithOrderIdAndActionName(orderId, provisioningAction));

			while (resultSet.next()) {
				String actionName = resultSet.getString("NAME");
				String status = resultSet.getString("STATUS");
				String result = resultSet.getString("RESULT");

				if (provisioningAction.equals(actionName) && "FIN".equals(status)
						&& "SKIP_PROVISIONING".equals(result)) {
					return true;
				}
				if (provisioningAction.equals(actionName) && "FIN".equals(status)
						&& !"SKIP_PROVISIONING".equals(result)) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean checkForAnyUsertaskIsDone(String orderId, String provisioningAction) {

		try {
			ResultSet resultSet = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryActionInstanceWithOrderIdAndActionName(orderId, provisioningAction));

			while (resultSet.next()) {
				String actionName = resultSet.getString("NAME");
				String status = resultSet.getString("STATUS");
				String result = resultSet.getString("RESULT");

				if (provisioningAction.equals(actionName) && "FIN".equals(status) && "SUCCESS".equals(result)) {
					return true;
				}
				if (provisioningAction.equals(actionName) && "FIN".equals(status) && "ERROR".equals(result)) {
					return false;
				}
				if (provisioningAction.equals(actionName) && "ERR".equals(status)) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// Find Joi number from Max active cin product number, GB/FR

	public static String LatestActiveJoiNumberFromCinProducts(String country) {
		String joiNumber = "";

		ResultSet cinProductTableValues = null;

		cinProductTableValues = OracleJdbcConnection
				.crmDatabaseSelectQuery(queryCinProductsWithMaxCinProductNumber(country));

		try {
			while (cinProductTableValues.next()) {

				joiNumber = cinProductTableValues.getString("ATTRIBUTE1");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return joiNumber;
	}

	public static boolean processblockHandsetAction(String joiNumber) {

		String processId = "";
		String objectId = "";

		String bothObjectAndProcessId = processIdAndObjectIdFromCueCommandTable("POLICE_REPORT_BLOCK_HANDSET",
				joiNumber);
		processId = bothObjectAndProcessId.split("@")[0];
		objectId = bothObjectAndProcessId.split("@")[1];

		// Check for blockHandsetAction
		try {
			boolean blockHandsetAction = checkIfActionExistsWithProcessId(processId, "blockHandsetAction");
			Assert.assertTrue(blockHandsetAction);
			// Send a valid police verification request
			String validPoliceReportRequest = RestApiAutomation.requestToFixPoliceVerificationForHandsetLost(joiNumber,
					processId, objectId);
			System.out.println(validPoliceReportRequest);
			RestApiAutomation.httpPostTest(Constant.cue_Execute, validPoliceReportRequest);

			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);

			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {

				ResultSet resultSet = OracleJdbcConnection.crmDatabaseSelectQuery(
						queryActionInstanceWithProcessIdAndActionName(processId, "blockHandsetAction"));

				while (resultSet.next()) {
					String actionName = resultSet.getString("NAME");
					String status = resultSet.getString("STATUS");
					String result = resultSet.getString("RESULT");

					if ("blockHandsetAction".equals(actionName) && "FIN".equals(status) && "SUCCESS".equals(result)) {
						return true;
					}

					if ("blockHandsetAction".equals(actionName) && "FIN".equals(status) && "ERROR".equals(result)) {
						// do database update
						/*
						 * update action instances and process instance tables
						 */
						OracleJdbcConnection
								.crmDatabaseUpdateQuery(queryUpdateActionInstances(processId, "blockHandsetAction"));
						sleep(2000);
						OracleJdbcConnection
								.crmDatabaseUpdateQuery(queryUpdateProcessInstances(processId, "blockHandset"));
						sleep(2000);

						// send a success message

						String handsetProvRequest = RestApiAutomation.requestHandsetBlockUnblockSuccess(processId,
								"BlockedHandset");
						System.out.println(handsetProvRequest);
						RestApiAutomation.httpPostTest(Constant.cue_Update, handsetProvRequest);
						sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);

					}
					if ("blockHandsetAction".equals(actionName) && "ERR".equals(status)) {
						return false;
					}
				}
				sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static String processIdAndObjectIdFromCueCommandTable(String cueRequestedCommand, String joiNumber) {

		String processId = "";
		String separator = "@";
		String objectId = "";
		String bothProcessAndObjectId = "";

		String queryCueCommand = querySelectProcessIdFromCUE_COMMANDAndCIN_PRODUCT(cueRequestedCommand, joiNumber,
				"OPEN");
		ResultSet getProcessId = null;

		getProcessId = OracleJdbcConnection.crmDatabaseSelectQuery(queryCueCommand);

		try {
			while (getProcessId.next()) {
				processId = getProcessId.getString("PROCESS_ID");
				objectId = getProcessId.getString("OBJECT_ID");

				// return both process id and object id
				bothProcessAndObjectId = processId + separator + objectId;

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return bothProcessAndObjectId;
	}

	public static boolean processUnblockHandsetAction(String joiNumber) {

		String processId = ""; // Process id from action_instances table
		String objectId = ""; // Object id from cue_commands table

		String bothProcessAndObjectId = processIdAndObjectIdFromCueCommandTable("POLICE_REPORT_BLOCK_HANDSET",
				joiNumber);

		processId = bothProcessAndObjectId.split("@")[0];
		System.out.println("Process ID" + processId);
		objectId = bothProcessAndObjectId.split("@")[1];
		System.out.println("Object ID" + objectId);

		// Send the unblock request

		try {
			String unBlockRequest = RestApiAutomation.requestToUnblockHandsetLostOrStolen(joiNumber, processId,
					objectId);
			System.out.println(unBlockRequest);
			RestApiAutomation.httpPostTest(Constant.CIN_EXECUTE, unBlockRequest);

			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
			// Change the process Id from action instances table
			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
				// Wrong process id in CUE_REQUESTED_COMMANDS table FOR UNBLOCK
				// SO TAKE PROCESS ID FROM WF_ACTION_INSTANCES AND
				// WF_ACTION_INSTANCES PARAMETERS TABLES USING JOI NUMBER
				String queryUnblockAction = queryProcessIdOfUnblockHandsetUsingJoiNumberAndCommand(joiNumber,
						"unBlockHandsetAction");
				ResultSet unblockAction = OracleJdbcConnection.crmDatabaseSelectQuery(queryUnblockAction);

				while (unblockAction.next()) {
					processId = unblockAction.getString("PRI_ID");
					if (unblockAction.wasNull()) {
						Assert.assertNotNull(processId);
					} else {
						System.out.println("Process ID of Unblock Handset action" + processId);
						String actionName = unblockAction.getString("NAME");
						String status = unblockAction.getString("STATUS");
						String result = unblockAction.getString("RESULT");

						if ("unBlockHandsetAction".equals(actionName) && "FIN".equals(status)
								&& "SUCCESS".equals(result)) {
							return true;
						}

						if ("unBlockHandsetAction".equals(actionName) && "FIN".equals(status)
								&& "ERROR".equals(result)) {
							// do database update
							/*
							 * update action instances and process instance
							 * tables
							 */
							OracleJdbcConnection.crmDatabaseUpdateQuery(
									queryUpdateActionInstances(processId, "unBlockHandsetAction"));
							sleep(2000);
							OracleJdbcConnection
									.crmDatabaseUpdateQuery(queryUpdateProcessInstances(processId, "UnBlockHandset"));
							sleep(2000);

							/*
							 * send a success message
							 */
							String handsetProvRequest = RestApiAutomation.requestHandsetBlockUnblockSuccess(processId,
									"UnBlockedHandset");
							System.out.println(handsetProvRequest);
							RestApiAutomation.httpPostTest(Constant.cue_Update, handsetProvRequest);
							sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);

						}
						if ("unBlockHandsetAction".equals(actionName) && "ERR".equals(status)) {
							return false;
						}

					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void ConnectionFeeFromProductDetails() {

		// SUMMARY OF KEY TERMS
		// String expectedText = String.format("upfront connection fee of %s",
		// Constant.UK_CONNECTION_FEE.substring(0, 3));
		//
		// String rawText =
		// getAndVerifyText("//*[@id='tab-PRODUCTDETAILS']/p[1]/strong[2]/strong[1]");
		// String actualText = rawText.substring(74, 103);
		// System.out.println("Actual First Connection fee: " + actualText);
		//
		// Assert.assertEquals(expectedText, actualText);

		// Check for Connection fee in "HOW MUCH IS JOi LIBERTY?"
		String expectedUpfront = String.format("Up-frontconnection:%s", Constant.UK_CONNECTION_FEE.substring(0, 3));

		String getUpfront = MVNOProjectSpecific.getAndVerifyText("//*[@id='tab-PRODUCTDETAILS']/p[6]");

		String upfront = getUpfront.replaceAll("\\s+", "").trim();

		String actualUpfront = upfront.substring(17, 39);
		System.out.println("Actual Second Connection fee: " + actualUpfront);
		Assert.assertEquals(expectedUpfront, actualUpfront);

	}

}