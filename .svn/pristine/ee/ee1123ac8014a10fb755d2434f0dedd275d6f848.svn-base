package com.acneuro.test.automation._01_UK_price_plans;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.acneuro.test.automation.libraries.ConfigLib;
import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;
import com.acneuro.test.automation.mvno_project_library.UKProjectSpecific;

/*
 * @Author: Sijimon James
 * Test: UK Add Liberty Price plan Order to Existing customer account and subscription activation
 */

public class TC_07_UK_JOi_Medium_Talkplan_ExistingCustomer {

	private static String emailId = "";
	private static String customerNumber = "";

	@Test
	public static void activationOfNewOrder() {
		String orderNumber = orderCreationForExistingCustomer();

		Reporter.log("Order Number: " + orderNumber, true);

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

		// here we check if shipping signal action is created
		boolean checkActionmbShipWaiting = MVNOProjectSpecific.checkIfActionExistsForOrder(orderId, "mbShipWaiting");
		Assert.assertTrue(checkActionmbShipWaiting);

		// here we check if upfront payment is finished successfully
		boolean mbShipWaitingIsDone = UKProjectSpecific.processShipWait(orderId, "mbShipWaiting");
		Assert.assertTrue(mbShipWaitingIsDone);

		// here we check if SIM activation action is created
		boolean checkActionmbSimActivation = MVNOProjectSpecific.checkIfActionExistsForOrder(orderId,
				"mbSimActivation");
		Assert.assertTrue(checkActionmbSimActivation);

		// If SIM activation action present activate the SIM in MYJOi
		UKProjectSpecific.LoginToUKMyJoiWithLatestEmailID(emailId);
		UKProjectSpecific.ActivateSimAfterLogin();
		Reporter.log("SIM activation is done using MyJOi", true);

		boolean checkActionmbProvisionWait = MVNOProjectSpecific.checkIfActionExistsForOrder(orderId,
				"mbProvisionWait");
		Assert.assertTrue(checkActionmbProvisionWait);

		UKProjectSpecific.provisioningCheckForUK(orderId);

	}

	public static String orderCreationForExistingCustomer() {

		customerNumber = UKProjectSpecific.existingCustomerNumberForUkCustomer();
		emailId = UKProjectSpecific.existingEmailIdForUkCustomer(customerNumber);
		UKProjectSpecific.LoginToUKMyJoiWithLatestEmailID(emailId);
		UKProjectSpecific.joiMediumOrderSelectionFromOurOffer();
		UKProjectSpecific.Our_Offer_add_to_basket();
		UKProjectSpecific.addToBasketWithoutPorting();
		UKProjectSpecific.Check_Out_Order();
		UKProjectSpecific.continue_step_3();
		UKProjectSpecific.orderSummaryPageForExitstingCustomer("123456");

		// Changed customer Email ID to Amsterdam test team mail id
		MVNOProjectSpecific.updateEmailIdInCust_customersTable(Constant.PERSONAL_EMAIL_ADRRESS, customerNumber);

		UKProjectSpecific.Payment_Datacash(Constant.Card_Type1, Constant.Type1_CC_number, "2018",
				Constant.Type1_CC_CVV);
		MVNOProjectSpecific.verifyTitleOfPage("Your JOi Order Confirmation");
		MVNOProjectSpecific.takeScreenShot("C:/testing/workspace/Screenshots/mvno/Order_Creation.png");
		String getOrderNumber = MVNOProjectSpecific.getAndVerifyText("//div/article/section/p[2]/strong");
		String orderNumber = getOrderNumber.substring(1);
		System.out.println("Order is created successfully!! Your Order number is: " + orderNumber);
		ConfigLib.driver.quit();
		return orderNumber;
	}

	@AfterMethod
	private static void postMethodToChangeEmail() {
		// change the email id back to normal
		MVNOProjectSpecific.updateEmailIdInCust_customersTable(emailId, customerNumber);
	}

}