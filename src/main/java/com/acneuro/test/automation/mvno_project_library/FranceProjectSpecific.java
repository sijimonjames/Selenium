package com.acneuro.test.automation.mvno_project_library;

import static com.acneuro.test.automation.db_connection_libraries.OracleDBUtils.*;

import static com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific.*;
import static java.lang.Thread.sleep;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
import com.acneuro.test.automation.libraries.ConfigLib;
import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.libraries.Generic;
import com.acneuro.test.automation.libraries.RestApiAutomation;
import com.acneuro.test.automation.libraries.RioGenerator;
import com.acneuro.test.automation.page_objects.My_JOi_Account_Creation_Page;

public class FranceProjectSpecific extends ConfigLib {

	public static void FranceFrenchUrlSelection() {
		// get the URL of France English website
		String frenchURL = Generic.urlSelection("FR_French");
		driver.get(frenchURL);

		// Hide the cookie message
		MVNOProjectSpecific.hideCookieMessage();
	}

	public static void FranceEnglishUrlSelection() {
		// get the URL of France English website
		String franceEnglishURL = Generic.urlSelection("FR_English");
		driver.get(franceEnglishURL);

		// Hide the cookie message
		MVNOProjectSpecific.hideCookieMessage();
	}

	public static void orderCreationPageInFrench() {
		// Click on 'Order' button
		driver.findElement(By.xpath("//a [text()='COMMANDEZ']")).click();
	}

	public static void simTypeSelection_France(String simType) {
		// Select "Combi"
		try {
			if (simType.equalsIgnoreCase("combi")) {

				WebElement combiSimLocation = driver
						.findElement(By.xpath("//section[@class='contentBlock']//p/b[contains(text(), 'Combi')]"));
				scrollWebPage(combiSimLocation);
				combiSimLocation.click();
			}
			if (simType.equalsIgnoreCase("Nano")) {

				WebElement combiSimLocation = driver
						.findElement(By.xpath("//section[@class='contentBlock']//p/b[contains(text(), 'Nano')]"));
				scrollWebPage(combiSimLocation);
				combiSimLocation.click();
			}
			sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void ourOfferPageAddToBasketButton() {
		// work for all 'Add To Basket' cases
		boolean enable = waitForAnElement(driver.findElement(By.cssSelector("input[class='button call-to-action']")))
				.isEnabled();

		if (enable == true) {
			driver.findElement(By.cssSelector("input[class='button call-to-action']")).click();
		}
	}

	public static void add_to_basket_without_porting() {
		// work for all 'Add To Basket' cases

		WebElement element = driver.findElement(By.xpath("//div/input[5]"));

		scrollWebPage(element);
		element.click();
	}

	public static void addToBasketWithPortin() {

		// Click the radio Button for Port IN

		WebElement element = driver.findElement(By.cssSelector("label[class='select-bar checkbox']"));
		element.click();
		scrollWebPage(element);
		// Click on Add to Basket Button
		driver.findElement(By.xpath("//div/input[5]")).click();
	}

	public static void checkOutOrder() {
		// Order check out button click

		driver.findElement(By.partialLinkText("CONTINUE")).click();
	}

	public static String FranceCustomerAccountCreation() {
		String Email_ID = uniqueEmailIdForFRCustomer();

		try {

			WebElement emailAddress = driver.findElement(By.name("email-fieldset__email"));

			emailAddress.sendKeys(Email_ID);
			driver.findElement(By.name("email-fieldset__confirm-email")).sendKeys(Email_ID);
			driver.findElement(By.name("password-fieldset__password")).sendKeys(Constant.DEFAULT_MYJOI_PASSWORD);
			driver.findElement(By.name("password-fieldset__confirm-password"))
					.sendKeys(Constant.DEFAULT_MYJOI_PASSWORD);

			scrollWebPage(emailAddress);
			sleep(1000);
			driver.findElement(By.id("nextPageButton")).click();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Email_ID;
	}

	public static void customerDetails(String Middle_Name, String Last_Name, String DOB, String Phone) {
		try {
			// drop down selection
			PageFactory.initElements(driver, My_JOi_Account_Creation_Page.class);
			WebElement ddList = waitForAnElement(My_JOi_Account_Creation_Page.customer_title);

			Select select = new Select(ddList);
			Random random = new Random();

			// random.nextInt() generated is 0 inclusive
			int optionIndex = random.nextInt(select.getOptions().size());

			// Increment optionIndex by 1 to pick an option between 1 to 3
			if (optionIndex == 0) {
				optionIndex = optionIndex + 1;
				select.selectByIndex(optionIndex);
			} else {
				select.selectByIndex(optionIndex);
			}

			System.out.println("index for title: " + optionIndex);

			// First name
			My_JOi_Account_Creation_Page.customer_first_name.sendKeys(username);
			// middle name
			WebElement middleName = My_JOi_Account_Creation_Page.customer_middle_name;
			
			middleName.sendKeys(Middle_Name);
			// Last name
			My_JOi_Account_Creation_Page.customer_last_name.sendKeys(Last_Name);
			// dob
			WebElement birthDate = My_JOi_Account_Creation_Page.customer_date_of_birth;
			scrollWebPage(middleName);
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

	private static void uploadCustomerIdCopy(String language) {

		WebElement fileUploadButton = null;
		try {
			if (language.equalsIgnoreCase("french")) {
				fileUploadButton = driver.findElement(By.cssSelector("input[value = 'T�L�CHARGER LE FICHIER']"));
			}
			if (language.equalsIgnoreCase("english")) {
				fileUploadButton = driver.findElement(By.cssSelector("input[value = 'UPLOAD FILE']"));
			}
			WebElement phoneNumber = driver.findElement(By.name("personal-details-fieldset__phone-number"));
			scrollDownToView(phoneNumber);
			fileUploadButton.click();
			scrollDownToView(fileUploadButton);
			// Handle the hidden division pop-up
			WebElement fileUpload = driver.findElement(By.xpath("//input[@type='file']"));
			sleep(1000);
			fileUpload.sendKeys(Constant.FranceIdFileName);
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
			System.out.println("ID is uploaded");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void uploadCustomerIdCopyForFrenchEnglish() {
		uploadCustomerIdCopy("english");

	}

	public static void uploadCustomerIdCopyForFrench_French() {
		uploadCustomerIdCopy("french");

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

	public static void continueButtonAfterAccountCreation() {
		// post code

		try {
			WebElement continueButton = driver.findElement(By.cssSelector("a[href='/our-offer/order']"));
			scrollWebPage(continueButton);
			continueButton.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void continueToStep3WithoutPorting() {
		// Click to next page from step 3

		// WebElement placeToScroll =
		// driver.findElement(By.xpath("//*[@id='receipt']/h2"));
		WebElement continueStep3 = waitForAnElement(driver.findElement(By.xpath("//form/dl/dd[2]/div/section/a[1]")));

		scrollWebPage(continueStep3);

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

			// WebElement placeToScroll =
			// driver.findElement(By.xpath("//*[@id='receipt']/h2"));

			WebElement continueStep3 = driver.findElement(By.xpath("//form/dl/dd[2]/div/section/a[1]"));

			// WebElement aJustClickNothingTodo =
			// driver.findElement(By.xpath("html/body/div[1]/div/footer"));

			scrollWebPage(continueStep3);
			// aJustClickNothingTodo.click();
			sleep(500);
			continueStep3.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void franceBankDetailsAndClickToContinue(String accountNumberInIban, String BIC) {
		driver.findElement(By.name("bank-account__iban")).sendKeys(accountNumberInIban);
		driver.findElement(By.name("bank-account__bic")).sendKeys(BIC);

		WebElement continueToNext = driver.findElement(By.xpath("//dl/dd[3]/div/section/a[1]"));
		scrollDownToView(continueToNext);
		continueToNext.click();

	}

	// Automating datacash page
	public static void Payment_Datacash(String Card_Type, String Card_Number, String Expiry_Year, String CVV) {
		try {
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
			sleep(7000); // New page loading
			// Authentication
			driver.findElement(By.xpath("//table/tbody/tr[1]/td[1]/input")).click();
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Resolving tasks API call
	public static void resolvingTasksSignal(String processId, String taskId) {
		String resolvingTasksApiBody = RestApiAutomation.resolvingTasksApiBody(processId, taskId);
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

	// Shipping API call

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
						String requestReplacementSimShip = RestApiAutomation.shippingSignalApiBody(Constant.ICCID_1_FR,
								processId);
						System.out.println(requestReplacementSimShip);
						RestApiAutomation.httpPostTest(Constant.cue_Update, requestReplacementSimShip);
					} else if (ShippingProcessName.equals("simReplacementShipWait")
							&& ShippingProcessName.equals(actionName) && !"FIN".equals(status)) {
						String requestReplacementSimShip = RestApiAutomation.shippingSignalApiBody(Constant.ICCID_2_FR,
								processId);
						System.out.println(requestReplacementSimShip);
						RestApiAutomation.httpPostTest(Constant.cue_Update, requestReplacementSimShip);
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
		String processId = "";

		try {

			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
				ResultSet resultSet = OracleJdbcConnection.crmDatabaseSelectQuery(
						queryActionInstanceWithOrderIdAndActionName(orderID, "mbNumPosWaiting"));

				while (resultSet.next()) {
					String actionName = resultSet.getString("NAME");
					String status = resultSet.getString("STATUS");
					String result = resultSet.getString("RESULT");
					processId = resultSet.getString("PRI_ID");

					if ("mbNumPosWaiting".equals(actionName) && "FIN".equals(status) && "SUCCESS".equals(result)) {
						return true;
					}
					if ("mbNumPosWaiting".equals(actionName) && "ERR".equals(status)) {
						return false;
					}
					if ("mbNumPosWaiting".equals(actionName) && "WAI".equals(status)) {

						ResultSet numposResultSet = OracleJdbcConnection.numposDatabaseSelectQuery(
								queryLatestPortinNumberfromNumposDB("FR", Constant.FR_Portin_Number));
						while (numposResultSet.next()) {
							portinNumber = numposResultSet.getString("MAX_PORTING_NUMBER");
						}
						rioCode = RioGenerator.generateRio(Constant.NUMPOS_OP_CODE_2,
								Constant.NUMPOS_DEFAULT_RIO_CODE.substring(2, 3),
								Constant.NUMPOS_DEFAULT_RIO_CODE.substring(3, 9), portinNumber);

						System.out.println("mbNumPosWaiting process id: " + processId);
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

	public static void loginToMyJoiFranceEnglish(String emailId) {

		try {
			// String franceEmailId = existingEmailIdForFRCustomer();
			// sleep(1000);

			driver.findElement(By.xpath("//section[1]/div/nav/ul/li[3]/a")).click();
			// login with existing customer details
			driver.findElement(By.name("login-fieldset__email")).sendKeys(emailId);
			driver.findElement(By.name("login-fieldset__password")).sendKeys(Constant.DEFAULT_MYJOI_PASSWORD);
			sleep(400);
			driver.findElement(By.cssSelector("input[value = 'LOGIN']")).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loginToMyJoiFranceFrench(String franceEmailId) {

		try {
			driver.findElement(By.xpath("//section[1]/div/nav/ul/li[3]/a")).click();
			// login with existing customer details
			driver.findElement(By.name("login-fieldset__email")).sendKeys(franceEmailId);
			driver.findElement(By.name("login-fieldset__password")).sendKeys(Constant.DEFAULT_MYJOI_PASSWORD);
			sleep(400);
			driver.findElement(By.cssSelector("input[value = 'CONNEXION']")).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void LoginToMyJoiFranceEnglish(String emailId) {

		openNewBrowserwithinTests();
		FranceEnglishUrlSelection();
		loginToMyJoiFranceEnglish(emailId);

	}

	public static void LoginToMyJoiFranceFrench(String franceEmailId) {

		openNewBrowserwithinTests();
		FranceFrenchUrlSelection();
		loginToMyJoiFranceFrench(franceEmailId);

	}

	public static void ActivateSimAfterLogin(String langauge) {

		WebElement activateSim = null;

		try {
			driver.findElement(By.cssSelector("a[href*= 'NC_MV_SUB_ACTV_REM1']")).click();
			driver.findElement(By.cssSelector("input[name='iccid']")).sendKeys(Constant.ICCID_1_FR);
			if (langauge.equalsIgnoreCase("EN")) {
				activateSim = driver.findElement(By.cssSelector("input[value = 'ACTIVATE YOUR SIM']"));
			}
			if (langauge.equalsIgnoreCase("FR")) {
				activateSim = driver.findElement(By.cssSelector("input[value = 'ACTIVER VOTRE SIM']"));
			}
			scrollDownToView(activateSim);
			activateSim.click();
			sleep(Constant.DEFAULT_SLEEP_TIME);
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void activateYourSIM_English() {
		ActivateSimAfterLogin("EN");
	}

	public static void activateYourSIM_French() {
		ActivateSimAfterLogin("FR");
	}

	public static void selectLibertyOrderFromOurOffer() {
		/*
		 * New wait until element is present WebDriverWait wait = new
		 * WebDriverWait(driver, 5000); // try { // /*wait for certain amount of
		 * time, since there are posibilities // //where mousehover performed in
		 * fraction of seconds and it may not be possible to see the options.
		 */
		try {
			sleep(2000);
			Actions mouseAction = new Actions(driver);
			WebElement ourOffer = driver.findElement(By.xpath("//ul/li[2]/span"));
			WebElement moveToJOiLiberty = driver.findElement(By.xpath("//li[2]/div/div/ul/li/ul/li[2]/a"));
			sleep(1000);
			mouseAction.moveToElement(ourOffer);
			sleep(1000);
			mouseAction.moveToElement(moveToJOiLiberty).click().build().perform();

			driver.findElement(By.xpath("//div/div[2]/div/a[1]")).click();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void joiLibertyOrderSelectionFromOurOffer() {

		String pricePlan = "JOi Liberty";
		selectPricePlanFromOurOffer(pricePlan);

	}

	public static void joiBigOrderSelectionFromOurOffer() {

		String pricePlan = "JOi Big";
		selectPricePlanFromOurOffer(pricePlan);

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
	
	private static void selectPricePlanFromOurOffer_French(String pricePlan) {

		Actions mouseHovering = new Actions(driver);
		WebElement ourOffer = waitForAnElement(driver.findElement(By.xpath("//span[contains(text(),'NOTRE OFFRE')]")));
		mouseHovering.moveToElement(ourOffer).perform();

		WebElement toPricePlan = waitForAnElement(driver.findElement(By.linkText(pricePlan)));

		mouseHovering.moveToElement(toPricePlan).click().build().perform();

		// Click on 'Order Now'
		driver.findElement(By.xpath("//div[@class='message']//a[text()='COMMANDEZ']")).click();

	}
	
	
	public static void joiEgaliteOrderSelectionFromOurOffer() {

		String pricePlan = "JOi Equality";
		selectPricePlanFromOurOffer(pricePlan);

	}

	public static void joiFratenityOrderSelectionFromOurOffer() {

		String pricePlan = "JOi Fraternity";
		selectPricePlanFromOurOffer(pricePlan);

	}

	public static void joiUnityOrderSelectionFromOurOffer() {

		String pricePlan = "JOi Unity";
		selectPricePlanFromOurOffer(pricePlan);

	}
	
	public static void joiEgaliteOrderSelectionFromOurOffer_FR() {

		String pricePlan = "JOi �galit�";
		selectPricePlanFromOurOffer_French(pricePlan);

	}

	public static void joiFratenityOrderSelectionFromOurOffer_FR() {

		String pricePlan = "JOi Fraternit�";
		selectPricePlanFromOurOffer_French(pricePlan);

	}

	public static void joiUnityOrderSelectionFromOurOffer_FR() {

		String pricePlan = "JOi Unit�";
		selectPricePlanFromOurOffer_French(pricePlan);

	}
	
	public static void joiLibertyOrderSelectionFromOurOffer_FR() {

		String pricePlan = "JOi Libert�";
		selectPricePlanFromOurOffer_French(pricePlan);

	}

	public static void joiGrandeOrderSelectionFromOurOffer_FR() {

		String pricePlan = "JOi Grande";
		selectPricePlanFromOurOffer_French(pricePlan);

	}

	public static boolean provisioningCheckForFrance(String orderId) {

		try {
			checkIfActionExistsForOrder(orderId, "mbProvisionWait");
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);

			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
				ResultSet resultSet = OracleJdbcConnection.crmDatabaseSelectQuery(
						queryActionInstanceWithOrderIdAndActionName(orderId, "mbProvisionWait"));

				while (resultSet.next()) {
					String actionName = resultSet.getString("NAME");
					String result = resultSet.getString("RESULT");
					String processId = resultSet.getString("PRI_ID");
					String status = resultSet.getString("STATUS");

					if ("mbProvisionWait".equals(actionName) && "FIN".equals(status) && "SUCCESS".equals(result)) {

						System.err.println("provisioning is Completed, now the customer should be active");
						return true;
					}
					if ("mbProvisionWait".equals(actionName) && "ERR".equals(status)) {
						return false;
					}
					if ("mbProvisionWait".equals(actionName) && "FIN".equals(status)
							&& "ERROR".equalsIgnoreCase(result)) {

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
						System.err.println("provisioning is faked, now the customer should be active");
						sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);

						return false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
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

	// Convert to common method as 'CheckForActionIsDone'
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
					sleep(Constant.DEFAULT_LONG_SLEEP_TIME);
				} else
					break;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String uniqueEmailIdForFRCustomer() {
		// Taking a unique id from existing email id
		String latestEmailId = existingEmailIdForFRCustomer();
		String existingId = latestEmailId.split("_")[2].split("@")[0];
		String uniqueId = String.valueOf(((Integer.parseInt(existingId)) + 1));
		// Creating a new Email ID
		String newUniqueEmailID = String.format("%s_Automation_%s@test.fr", username, uniqueId);
		System.out.println("New Email id:" + newUniqueEmailID);
		return newUniqueEmailID;
	}

	public static String existingEmailIdForFRCustomer() {
		String lastCustomerNumber = "";
		String latestEmailId = "";
		ResultSet customerNumber = null;

		try {
			customerNumber = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryMaxCustomerNumberFrWithUsername(username));

			while (customerNumber.next()) {
				lastCustomerNumber = customerNumber.getString("MAX_CUSTOMER_NUMBER");

				if (customerNumber.wasNull()) {
					latestEmailId = String.format("%s_Automation_01@test.fr", username);

					System.out.println(String.format(
							"No Email Id found with this username, creating a new one as: %s_Automation_01@test.fr",
							latestEmailId));

				} else {
					System.out.println("Old Customer Number: " + lastCustomerNumber);
					ResultSet emailId = OracleJdbcConnection
							.crmDatabaseSelectQuery(queryLatestExistingCustomerEmailId(lastCustomerNumber));

					while (emailId.next()) {
						latestEmailId = emailId.getString("EMAIL_ADDRESS");
						System.out.println("Previous Email Id: " + latestEmailId);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return latestEmailId;
	}
	
	
	public static String existingEmailIdForFRCustomer_Latest() {
		String lastCustomerNumber = "";
		String latestEmailId = "";
		ResultSet customerNumber = null;

		try {
			customerNumber = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryMaxCustomerNumberFrWithUsername(username));

			while (customerNumber.next()) {
				lastCustomerNumber = customerNumber.getString("MAX_CUSTOMER_NUMBER");

				if (customerNumber.wasNull()) {
					latestEmailId = String.format("%s_Automation_01@test.fr", username);

					System.out.println(String.format(
							"No Email Id found with this username, creating a new one as: %s_Automation_01@test.fr",
							latestEmailId));

				} else {
					System.out.println("Old Customer Number: " + lastCustomerNumber);
					ResultSet emailId = OracleJdbcConnection
							.crmDatabaseSelectQuery(queryLatestExistingCustomerEmailId(lastCustomerNumber));

					while (emailId.next()) {
						latestEmailId = emailId.getString("EMAIL_ADDRESS");
						System.out.println("Previous Email Id: " + latestEmailId);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lastCustomerNumber+"@"+latestEmailId;
	}

	public static String fetchPortOutRioCodeFromLatestCustomer(String country) {

		String rioCode = "";
		String joiNumber = "";
		String customerNumber = "";
		ResultSet customerDetails = null;
		customerDetails = OracleJdbcConnection.crmDatabaseSelectQuery(queryCinProductsWithMaxCinProductNumber(country));

		try {
			while (customerDetails.next()) {

				joiNumber = customerDetails.getString("ATTRIBUTE1");
				Reporter.log("JOi Number: " + joiNumber);
				rioCode = customerDetails.getString("ATTRIBUTE2");
				Reporter.log("Rio Code: " + rioCode);
				customerNumber = customerDetails.getString("CUSTOMER_NUMBER");
				Reporter.log("customer number: " + customerNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rioCode + "@" + joiNumber + "@" + customerNumber;
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

					Assert.assertEquals(cinProductNumber, "1");
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

	// Convert to common method as 'CheckForActionIsDone'
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
						sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
						return true;
					}
					if ("pOProv".equals(actionName) && "SCH".equals(status)) {
						// set sys date to now
						OracleJdbcConnection
								.crmDatabaseUpdateQuery(queryUpdateActionInstanceScheduledTime(processId, "pOProv"));
						sleep(Constant.DEFAULT_LONG_SLEEP_TIME); // 2mins
																	// sleep
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

	// Convert to common method as 'CheckForActionIsDone'
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

	// Move to common Project specific
	private static String LoginToFrMyJoiWithActiveSubscription(String language) {

		String country = "FR";
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			MVNOProjectSpecific.hideCookieMessage();
			// Login to UK MYJOi
			driver.findElement(By.xpath("//section[1]/div/nav/ul/li[3]/a")).click();
			// login with existing customer details
			driver.findElement(By.name("login-fieldset__email")).sendKeys(userName);
			driver.findElement(By.name("login-fieldset__password")).sendKeys(Constant.DEFAULT_MYJOI_PASSWORD);
			sleep(400);

			if (language.equalsIgnoreCase("English")) {

				driver.findElement(By.cssSelector("input[value = 'LOGIN']")).click();
			}
			if (language.equalsIgnoreCase("French")) {

				driver.findElement(By.cssSelector("input[value = 'CONNEXION']")).click();
			}
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myJOiNumber;
	}

	public static String loginToFranceEnglish() {

		String myJOiNumber = LoginToFrMyJoiWithActiveSubscription("English");
		return myJOiNumber;

	}

	public static String loginToFranceFrench() {

		String myJOiNumber = LoginToFrMyJoiWithActiveSubscription("French");
		return myJOiNumber;

	}

	// Move to common
	public static void franceManageSettingsFromSubscriptions(String activeJoiNumber) {
		String actualJOiNumber = "";
		int n;

		try {
			for (n = 1; n < 8; n++) {

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

	// move to common
	public static void selectAnyOptionFromMySettings(String pathToElement) {

		// Click on the element
		driver.findElement(By.xpath(pathToElement)).click();
	}

	// Changed submit button and SIM type, but still can be common
	// For UK, simType always 'COMBI' and any for France 'COMBI','NANO'
	public static void selectAndSubmitReplaceSimFromMySettings(String pathToReasonForReplacement, String simType) {

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
			if (simType.equalsIgnoreCase("COMBI")) {
				driver.findElement(By.xpath("//section/div/article/form/label[4]")).click();
			}

			if (simType.equalsIgnoreCase("NANO")) {
				driver.findElement(By.xpath("//section/div/article/form/label[5]")).click();
			}
			WebDriverWait waitUntilButtonEnabled = new WebDriverWait(driver, 10);
			waitUntilButtonEnabled.until(
					ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//input[@value='SUBMIT']"))));

			// submit button location
			WebElement submitButton = driver.findElement(By.xpath("//input[@value='SUBMIT']"));

			scrollDownToView(submitButton);
			sleep(1000);
			submitButton.click();
			driver.quit();

			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}

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

			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
				ResultSet resultSet = OracleJdbcConnection.crmDatabaseSelectQuery(
						queryActionInstanceWithOrderIdAndActionName(orderId, provisioningAction));

				while (resultSet.next()) {
					String actionName = resultSet.getString("NAME");
					String status = resultSet.getString("STATUS");
					String result = resultSet.getString("RESULT");

					if (provisioningAction.equals(actionName) && "FIN".equals(status)
							&& "SKIP_PROVISIONING".equals(result)) {
						return true;
					}
					if (provisioningAction.equals(actionName) && "FIN".equals(status) && "ERROR".equals(result)) {
						return false;
					}
					if (provisioningAction.equals(actionName) && "ERR".equals(status)) {
						return false;
					}
				}
				sleep(2500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

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

	public static void LoginToFrMyJoiWithMsisdn(String msisdn) {

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
				System.out.println("Login to MyJOI: " + userName);
			}

			// Open Browser
			openNewBrowserwithinTests();
			// Hide cookie message
			FranceEnglishUrlSelection();
			// Login to FR MYJOi
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
			activateSim.sendKeys(Constant.ICCID_2_FR);
			scrollDownToView(activateSim);
			driver.findElement(By.cssSelector("input[value = 'ACTIVATE YOUR SIM']")).click();
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
			driver.quit();
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
				System.out.println("Process ID: " + processID);

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
						"simReplacementResumeProvFailureUserTask");
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

	// Convert to common method as 'CheckForActionIsDone'
	public static boolean checkForSimReplacementOrderIsDone(String orderId) {
		boolean simReplaceFinishAction = checkIfActionExistsForOrder(orderId, "simReplacementFinish");
		Assert.assertTrue(simReplaceFinishAction);

		try {
			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
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
				if (provisioningAction.equals(actionName) && "FIN".equals(status) && !"SUCCESS".equals(result)) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

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

	public static boolean processpONumPos(String processId, String msisdn, String portOutId) {

		try {

			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {

				String queryNumpos = queryActionInstanceWithProcessIdAndActionName(processId, "pONumPos");
				ResultSet numposDetails = OracleJdbcConnection.crmDatabaseSelectQuery(queryNumpos);

				while (numposDetails.next()) {

					String name = numposDetails.getString("NAME");
					String status = numposDetails.getString("STATUS");
					String result = numposDetails.getString("RESULT");

					if (name.equals("pONumPos") && status.equals("FIN") && result.equals("SUCCESS")) {
						return true;
					}

					if (name.equals("pONumPos") && status.equals("WAI")) {
						String apiPortOutForCues = RestApiAutomation.secondRequestToEndPortoutForFrance(msisdn,
								portOutId);
						System.out.println(apiPortOutForCues);

						RestApiAutomation.httpPostTest(Constant.NUMPOS_INCOMING_REQUEST, apiPortOutForCues);
						System.out.println("Second portout post request is processed");
						sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
					}
					if (name.equals("pONumPos") && status.equals("FIN") && result.equals("ERROR")) {
						return false;
					}
					if (name.equals("pONumPos") && status.equals("ERR")) {
						return false;
					}
				}

			}
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean cancelNumpos(String processId, String msisdn, String portOutId) {

		try {

			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {

				String queryNumpos = queryActionInstanceWithProcessIdAndActionName(processId, "pONumPos");
				ResultSet numposDetails = OracleJdbcConnection.crmDatabaseSelectQuery(queryNumpos);

				while (numposDetails.next()) {

					String name = numposDetails.getString("NAME");
					String status = numposDetails.getString("STATUS");
					String result = numposDetails.getString("RESULT");

					if (name.equals("pONumPos") && status.equals("FIN") && result.equals("SUCCESS")) {
						return true;
					}

					if (name.equals("pONumPos") && status.equals("WAI")) {
						String apiPortOutForCues = RestApiAutomation.secondRequestToEndPortoutForFrance(msisdn,
								portOutId);
						System.out.println(apiPortOutForCues);

						RestApiAutomation.httpPostTest(Constant.NUMPOS_INCOMING_REQUEST, apiPortOutForCues);
						System.out.println("Second portout post request is processed");
						sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
					}
					if (name.equals("pONumPos") && status.equals("FIN") && result.equals("ERROR")) {
						return false;
					}
					if (name.equals("pONumPos") && status.equals("ERR")) {
						return false;
					}
				}

			}
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean cancellationOfNumpos(String processId, String msisdn, String portOutId) {

		try {

			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {

				String queryNumpos = queryActionInstanceWithProcessIdAndActionName(processId, "pONumPos");
				ResultSet numposDetails = OracleJdbcConnection.crmDatabaseSelectQuery(queryNumpos);

				while (numposDetails.next()) {

					String name = numposDetails.getString("NAME");
					String status = numposDetails.getString("STATUS");
					String result = numposDetails.getString("RESULT");

					if (name.equals("pONumPos") && status.equals("FIN") && result.equals("SUCCESS")) {
						return true;
					}

					if (name.equals("pONumPos") && status.equals("WAI")) {
						String apiPortOutForCues = RestApiAutomation.secondRequestToEndPortoutForFrance(msisdn,
								portOutId);
						System.out.println(apiPortOutForCues);

						RestApiAutomation.httpPostTest(Constant.NUMPOS_INCOMING_REQUEST, apiPortOutForCues);
						System.out.println("Second portout post request is processed");
						sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
					}
					if (name.equals("pONumPos") && status.equals("FIN") && result.equals("ERROR")) {
						return false;
					}
					if (name.equals("pONumPos") && status.equals("ERR")) {
						return false;
					}
				}

			}
			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}