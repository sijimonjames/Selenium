package com.acneuro.test.automation.customer_Account_And_Order_Creation_Tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.acneuro.test.automation.libraries.ConfigLib;
import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.mvno_project_library.FranceProjectSpecific;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;

public class TC_05_FranceAddEgalittePricePlanOrderToExistingCustomer {

	private static String EmailId = "";

	public static String orderCreationForExistingCustomer() {

		EmailId = FranceProjectSpecific.existingEmailIdForFRCustomer();

		FranceProjectSpecific.LoginToMyJoiFranceEnglish(EmailId);
		FranceProjectSpecific.joiEgaliteOrderSelectionFromOurOffer();
		MVNOProjectSpecific.pricePlanSelection_France_English("joiEgalitteFR");
		FranceProjectSpecific.simTypeSelection_France("Combi");
		FranceProjectSpecific.ourOfferPageAddToBasketButton();
		FranceProjectSpecific.add_to_basket_without_porting();
		FranceProjectSpecific.checkOutOrder();
		FranceProjectSpecific.continueToStep3WithoutPorting();

		// check for payment details, if found proceed to next
		boolean directDebitPreset = MVNOProjectSpecific.verifyDirectDebitDetails();

		if (directDebitPreset == false) {
			FranceProjectSpecific.franceBankDetailsAndClickToContinue(Constant.FrIbanNumber, Constant.FrSwiftCode);
			System.out.println("No previous Order is Present for this customer");
			MVNOProjectSpecific.orderSummaryPageForNewCustomer("99999");
		} else {
			MVNOProjectSpecific.orderSummaryPageForExitstingCustomer("99999");
		}

		FranceProjectSpecific.Payment_Datacash(Constant.Card_Type1, Constant.Type1_CC_number, "2018",
				Constant.Type1_CC_CVV);
		// Get Order Number
		String getOrderNumber = MVNOProjectSpecific.getAndVerifyText("//div/article/section/p[2]/strong");
		String orderNumber = getOrderNumber.substring(1);
		System.out.println("Order is created successfully!! Your Order number is: " + orderNumber);
		ConfigLib.driver.quit();
		return orderNumber;
	}

	String orderId = "";
	String orderNumber = "";
	String taskId = ""; // Need task id to 'resolving_tasks'
	String latestPriId = "";

	@Test
	public void CustomerActivation() throws InterruptedException {
		// Checking the database for status="RESOLVING_TASKS"(sometimes "NEW")
		// change this always to "RESOLVING_TASKS"

		String orderNumber = orderCreationForExistingCustomer();

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

		// here we check if shipping signal action is created
		boolean checkActionmbShipWaiting = MVNOProjectSpecific.checkIfActionExistsForOrder(orderId, "mbShipWaiting");
		Assert.assertTrue(checkActionmbShipWaiting);

		// here we check if upfront payment is finished successfully
		boolean mbShipWaitingIsDone = FranceProjectSpecific.processShipWait(orderId, "mbShipWaiting");
		Assert.assertTrue(mbShipWaitingIsDone);

		// here we check if SIM activation action is created
		boolean checkActionmbSimActivation = MVNOProjectSpecific.checkIfActionExistsForOrder(orderId,
				"mbSimActivation");
		Assert.assertTrue(checkActionmbSimActivation);

		// If SIM activation action present activate the SIM in MYJOi
		FranceProjectSpecific.LoginToMyJoiFranceEnglish(EmailId);
		FranceProjectSpecific.activateYourSIM_English();
		Reporter.log("SIM activation is completed using MyJOi", true);

		boolean checkActionmbProvisionWait = MVNOProjectSpecific.checkIfActionExistsForOrder(orderId,
				"mbProvisionWait");
		Assert.assertTrue(checkActionmbProvisionWait);

		FranceProjectSpecific.provisioningCheckForFrance(orderId);

	}
}