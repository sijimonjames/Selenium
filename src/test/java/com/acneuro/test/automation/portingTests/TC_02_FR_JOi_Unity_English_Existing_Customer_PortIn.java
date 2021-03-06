package com.acneuro.test.automation.portingTests;

import java.sql.SQLException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.acneuro.test.automation.libraries.ConfigLib;
import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.mvno_project_library.FranceProjectSpecific;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;

public class TC_02_FR_JOi_Unity_English_Existing_Customer_PortIn {

	private static String newEmailId = "unknown";
	private static String customerNumber = "unknown";
	private static String directory = ".//Screenshots/Portin/";
	private static String EmailId = "";

	public String accountCreation() {

		EmailId = FranceProjectSpecific.existingEmailIdForFRCustomer();

		FranceProjectSpecific.LoginToMyJoiFranceEnglish(EmailId);
		FranceProjectSpecific.joiEgaliteOrderSelectionFromOurOffer();
		// check the radio button 'Joi Fraternity' ----PRICE PLAN SELECTION
		MVNOProjectSpecific.pricePlanSelection_France_English("joiUnityFR");
		FranceProjectSpecific.simTypeSelection_France("nano");
		FranceProjectSpecific.ourOfferPageAddToBasketButton();

		// step 5: Click on 'Add To Basket' with Number porting
		FranceProjectSpecific.addToBasketWithPortin();
		MVNOProjectSpecific.takeScreenShot(String.format("%s" + "portin_order_creation_page_Existing.png", directory));
		// Step 6: click on "check out / Continue for Fr"
		FranceProjectSpecific.checkOutOrder();

		// Portin details
		FranceProjectSpecific.continueToStep3WithPortin();

		// check for payment details, if found proceed to next
		boolean directDebitPreset = MVNOProjectSpecific.verifyDirectDebitDetails();

		if (directDebitPreset == false) {
			FranceProjectSpecific.franceBankDetailsAndClickToContinue(Constant.FrIbanNumber, Constant.FrSwiftCode);
			System.out.println("No previous Order is Present for this customer");
			MVNOProjectSpecific.orderSummaryPageForNewCustomer("33333");
		} else {
			MVNOProjectSpecific.orderSummaryPageForExitstingCustomer("44444");
		}

		// temporarily update Email ID
		MVNOProjectSpecific.updateEmailIdInCust_customersTable(Constant.PERSONAL_EMAIL_ADRRESS, customerNumber);

		FranceProjectSpecific.Payment_Datacash(Constant.Card_Type1, Constant.Type1_CC_number, "2018",
				Constant.Type1_CC_CVV);

		MVNOProjectSpecific.verifyTitleOfPage("Your JOi Order Confirmation");

		MVNOProjectSpecific.takeScreenShot(String.format("%s//portin_order_Details.png", directory));

		String getOrderNumber = MVNOProjectSpecific.getAndVerifyText("//div/article/section/p[2]/strong");
		String orderNumber = getOrderNumber.substring(1);
		ConfigLib.driver.quit();
		Reporter.log("Order is created successfully. France Order number is: " + orderNumber, true);
		return orderNumber;
	}

	@Test
	public void customerActivationWithPortIN() throws SQLException {

		// Pre-requisite: Create a new account
		String orderNumber = accountCreation();
		String orderId = "";
		/*
		 * Fail the test if Order is in any other status other than resolving
		 * tasks
		 */

		// verify for resolving tasks
		Assert.assertTrue(MVNOProjectSpecific.CheckStatusOfOrder(orderNumber));

		// RESOLVING_TASKS API signal
		orderId = MVNOProjectSpecific.processResolvingTasksSignal(orderNumber);

		// here we check if upfront payment action created
		boolean checkActionUpfrontPayment = MVNOProjectSpecific.checkIfActionExistsForOrder(orderId,
				"ofUpfrontPaymentWait");
		Assert.assertTrue(checkActionUpfrontPayment);

		// here we check if upfront payment is finished successfully
		boolean upfrontPaymentActionIsDone = MVNOProjectSpecific.processUpfrontPayment_Jackal(orderNumber, orderId);
		Assert.assertTrue(upfrontPaymentActionIsDone);

		// Eligibility check waiting
		Assert.assertTrue(FranceProjectSpecific.processNumposForFrancePortIn(orderId));
		Assert.assertTrue(FranceProjectSpecific.processEligibilityCheckWaiting(orderId));

		// here we check if shipping signal action is created
		boolean checkActionmbShipWaiting = MVNOProjectSpecific.checkIfActionExistsForOrder(orderId, "mbShipWaiting");
		Assert.assertTrue(checkActionmbShipWaiting);

		// here we check if upfront payment is finished successfully
		boolean mbShipWaitingIsDone = FranceProjectSpecific.processShipWait(orderId, "mbShipWaiting");
		Assert.assertTrue(mbShipWaitingIsDone);

		// here we check if 'mbNumPosWaiting' action is created
		Assert.assertTrue(MVNOProjectSpecific.checkIfActionExistsForOrder(orderId, "mbNumPosWaiting"));

		// Process 'mbNumPosWaiting' action
		Assert.assertTrue(FranceProjectSpecific.processNumposWaiting(orderId));

		// Provisioning start changing to Now
		Assert.assertTrue(MVNOProjectSpecific.checkIfActionExistsForOrder(orderId, "mbProvisionStart"));

		Assert.assertTrue(FranceProjectSpecific.numposProvisioningStartToNow(orderId));

		Assert.assertTrue(MVNOProjectSpecific.checkIfActionExistsForOrder(orderId, "mbProvisionWait"));

		FranceProjectSpecific.provisioningCheckForFrance(orderId);
	}

	@AfterMethod
	private static void postMethodToChangeEmail() {
		// change the email id back to normal
		MVNOProjectSpecific.updateEmailIdInCust_customersTable(newEmailId, customerNumber);
		System.out.println(String.format("Email Adress is changed to original: %s", newEmailId));
	}
}