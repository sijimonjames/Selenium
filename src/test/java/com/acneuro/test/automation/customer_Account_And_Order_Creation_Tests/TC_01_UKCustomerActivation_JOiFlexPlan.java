package com.acneuro.test.automation.customer_Account_And_Order_Creation_Tests;

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
 * @Test: UK customer account creation, order a price plan and subscription activation
 */

public class TC_01_UKCustomerActivation_JOiFlexPlan extends ConfigLib {

	private static String orderId = "";
	private static String orderNumber = "";
	private static String newEmailId = "unknown";
	private static String customerNumber = "unknown";

	@Test
	public void CustomerActivation() throws InterruptedException {

		orderNumber = accountCreationForUKCustomer();
		/*
		 * Fail the test if Order is in any other status other than resolving
		 * tasks, verify for resolving tasks
		 */
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
		UKProjectSpecific.LoginToUKMyJoiWithLatestEmailID(newEmailId);
		UKProjectSpecific.ActivateSimAfterLogin();
		Reporter.log("SIM activation is done using MyJOi", true);

		boolean checkActionmbProvisionWait = MVNOProjectSpecific.checkIfActionExistsForOrder(orderId,
				"mbProvisionWait");
		Assert.assertTrue(checkActionmbProvisionWait);

		UKProjectSpecific.provisioningCheckForUK(orderId);

	}

	public String accountCreationForUKCustomer() {

		// VideoRecording.startRecording();
		UKProjectSpecific.ukUrlSelection();

		// Step 1: verify the home page title
		MVNOProjectSpecific.verifyTitleOfPage("JOi Telecom");
		// Step 2: Click on order button
		MVNOProjectSpecific.orderCreationPageInEnglish();
		// step 3:check the radio button 'Joi Base price plan' ----PRICE PLAN
		// SELECTION
		UKProjectSpecific.Joi_Base_price_plan();
		// step 4: Click on 'Add To Basket' on same page
		UKProjectSpecific.Our_Offer_add_to_basket();
		// step 5: Click on 'Add To Basket' without Number porting
		UKProjectSpecific.addToBasketWithoutPorting();
		// Step 6: click on "check out"
		UKProjectSpecific.Check_Out_Order();

		// Get the Latest Email Id
		newEmailId = UKProjectSpecific.createUkCustomerAccount();
		UKProjectSpecific.customerDetails("MVNO", "Flex Automation", "31121966", "03800000010");
		UKProjectSpecific.contactAndBillingAdress("AB7 5DP", "01");

		UKProjectSpecific.continueFromAccountCreationPage();
		customerNumber = MVNOProjectSpecific.queryLatestCustomerNumber("UK");
		Reporter.log("Customer Number created: " + customerNumber, true);
		// Subscription details - Subscription is for same customer
		UKProjectSpecific.continue_step_3();
		// UK-Bank Account details
		UKProjectSpecific.Bank_details(Constant.Bank_Name_UK, Constant.UK_Account_Number, Constant.UK_Sort_code);
		UKProjectSpecific.orderSummaryPageForNewCustomer("999999");

		// Changed customer Email ID to Amsterdam test team mail id
		MVNOProjectSpecific.updateEmailIdInCust_customersTable(Constant.PERSONAL_EMAIL_ADRRESS, customerNumber);

		UKProjectSpecific.Payment_Datacash(Constant.Card_Type1, Constant.Type1_CC_number, "2018",
				Constant.Type1_CC_CVV);
		MVNOProjectSpecific.verifyTitleOfPage("Your JOi Order Confirmation");
		MVNOProjectSpecific.takeScreenShot("C:/testing/workspace/Screenshots/mvno/Order_Creation.png");
		String getOrderNumber = MVNOProjectSpecific.getAndVerifyText("//div/article/section/p[2]/strong");
		String orderNumber = getOrderNumber.substring(1);
		// video recording
		// VideoRecording.stopRecording();

		ConfigLib.postCondition();
		Reporter.log("Order is created successfully. UK Order number is: " + orderNumber, true);
		return orderNumber;
	}

	@AfterMethod
	private static void postMethodToChangeEmail() {
		// change the email id back to normal
		MVNOProjectSpecific.updateEmailIdInCust_customersTable(newEmailId, customerNumber);
	}
}