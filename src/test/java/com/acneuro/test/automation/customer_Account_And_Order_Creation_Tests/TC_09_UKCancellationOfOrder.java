package com.acneuro.test.automation.customer_Account_And_Order_Creation_Tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.acneuro.test.automation.libraries.ConfigLib;
import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.libraries.RestApiAutomation;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;
import com.acneuro.test.automation.mvno_project_library.UKProjectSpecific;

public class TC_09_UKCancellationOfOrder {

	private static String emailId = "";
	private static String customerNumber = "";

	public static String orderCreationForExistingCustomer() {
		// Checking the database for status="RESOLVING_TASKS"(sometimes "NEW")
		// change this always to "RESOLVING_TASKS"
		customerNumber = UKProjectSpecific.existingCustomerNumberForUkCustomer();
		emailId = UKProjectSpecific.existingEmailIdForUkCustomer(customerNumber);
		UKProjectSpecific.LoginToUKMyJoiWithLatestEmailID(emailId);
		UKProjectSpecific.joiXLargeOrderSelectionFromOurOffer();
		UKProjectSpecific.Our_Offer_add_to_basket();
		UKProjectSpecific.addToBasketWithoutPorting();
		UKProjectSpecific.Check_Out_Order();
		UKProjectSpecific.continue_step_3();
		UKProjectSpecific.orderSummaryPageForExitstingCustomer("99999");

		// Changed customer Email ID to Amsterdam test team mail id
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

	@Test
	public static void cancellationOfOrder() {
		String orderNumber = orderCreationForExistingCustomer();
		System.out.println(orderNumber);

		String orderId = "";
		System.out.println(orderNumber);

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

		String ordercanCellationApiBody = RestApiAutomation.orderCancellationSignalApiBody(orderNumber, orderId,
				"Incomplete Order Cancellation", "Customer");
		boolean orderCancellation = UKProjectSpecific.cancellationOfIncompleteOrderInShipWaitingStage(orderId,
				ordercanCellationApiBody);

		Assert.assertTrue(orderCancellation);
		UKProjectSpecific.terminateBillingDuringOrderCancellation(orderId);
		Reporter.log("Order with Order Number " + orderNumber + " is Cancelled");

	}
}