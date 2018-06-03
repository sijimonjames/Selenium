package com.acneuro.test.automation.Order_Cancellation_Tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.acneuro.test.automation.libraries.ConfigLib;
import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.libraries.RestApiAutomation;
import com.acneuro.test.automation.mvno_project_library.FranceProjectSpecific;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;

public class TC_02_FR_Order_Cancellation_InProcess_Unity_PP {

	private static String EmailId = "";
	public static String orderCreationForExistingCustomer() {

		EmailId = FranceProjectSpecific.existingEmailIdForFRCustomer();

		FranceProjectSpecific.LoginToMyJoiFranceEnglish(EmailId);
		FranceProjectSpecific.joiUnityOrderSelectionFromOurOffer();
		MVNOProjectSpecific.pricePlanSelection_France_English("joiUnityFR");
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
			MVNOProjectSpecific.orderSummaryPageForNewCustomer("88888");
		} else {
			MVNOProjectSpecific.orderSummaryPageForExitstingCustomer("55555");
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

	@Test
	public static void cancellationOfOrder() {
		String orderNumber = orderCreationForExistingCustomer();
		System.out.println(orderNumber);

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

		String ordercanCellationApiBody = RestApiAutomation.orderCancellationSignalApiBody(orderNumber, orderId,
				"Incomplete Order Cancellation", "Customer");
		boolean orderCancellation = FranceProjectSpecific.cancellationOfIncompleteOrderInShipWaitingStage(orderId,
				ordercanCellationApiBody);

		Assert.assertTrue(orderCancellation);
		FranceProjectSpecific.terminateBillingDuringOrderCancellation(orderId);
		Reporter.log("Order with Order Number " + orderNumber + " is Cancelled");

	}
}