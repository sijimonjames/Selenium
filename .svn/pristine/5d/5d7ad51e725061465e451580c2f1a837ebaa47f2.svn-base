package com.acneuro.test.automation.Order_Cancellation_Tests;

import static com.acneuro.test.automation.db_connection_libraries.OracleDBUtils.queryOrderStatus;

import java.sql.ResultSet;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.acneuro.test.automation.db_connection_libraries.OracleJdbcConnection;
import com.acneuro.test.automation.libraries.ConfigLib;
import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.libraries.RestApiAutomation;
import com.acneuro.test.automation.mvno_project_library.FranceProjectSpecific;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;

public class TC_01_FranceCancellationOfOrder_Resolving_Task_Test {

	private static String emailId = "";
	private static String orderNumber = "";
	private static String orderId = "";

	public static void orderCreationForExistingCustomer() {

		emailId = FranceProjectSpecific.existingEmailIdForFRCustomer();
		FranceProjectSpecific.LoginToMyJoiFranceEnglish(emailId);
		FranceProjectSpecific.selectLibertyOrderFromOurOffer();
		MVNOProjectSpecific.pricePlanSelection_France_English("joiLibertyFR");
		FranceProjectSpecific.simTypeSelection_France("Combi");
		FranceProjectSpecific.ourOfferPageAddToBasketButton();
		FranceProjectSpecific.add_to_basket_without_porting();
		FranceProjectSpecific.checkOutOrder();
		FranceProjectSpecific.continueToStep3WithoutPorting();
		MVNOProjectSpecific.orderSummaryPageForExitstingCustomer("99999");
		FranceProjectSpecific.Payment_Datacash(Constant.Card_Type1, Constant.Type1_CC_number, "2018",
				Constant.Type1_CC_CVV);
		MVNOProjectSpecific.verifyTitleOfPage("Your JOi Order Confirmation");
		MVNOProjectSpecific.takeScreenShot("C:/testing/workspace/Screenshots/mvno/Order_Creation.png");
		String getOrderNumber = MVNOProjectSpecific.getAndVerifyText("//div/article/section/p[2]/strong");
		orderNumber = getOrderNumber.substring(1);
		System.out.println("Order is created successfully!! Your Order number is: " + orderNumber);
		ConfigLib.driver.quit();
	}

	@Test
	public static void cancellationOfOrder() {

		orderCreationForExistingCustomer();
		System.out.println(orderNumber);

		/*
		 * Fail the test if Order is in any other status other than resolving
		 * tasks
		 */

		// verify for resolving tasks
		Assert.assertTrue(MVNOProjectSpecific.CheckStatusOfOrder(orderNumber));

		// Find Order ID
		orderId();
		
		
		String CancellationApiBody = RestApiAutomation.orderCancellationSignalApiBody(orderNumber, orderId, "Revocation", "Customer");
		RestApiAutomation.httpPostTest(Constant.cue_Execute, CancellationApiBody);

		// check for Order is cancelled
		boolean orderCancellation = MVNOProjectSpecific.CheckStatusOfOrderInCancelled(orderNumber);
		Assert.assertTrue(orderCancellation);
		Reporter.log("Order with Order Number " + orderNumber + " is Cancelled");
	}

	private static void orderId() {

		try {
			ResultSet queryOrderId = OracleJdbcConnection.crmDatabaseSelectQuery(queryOrderStatus(orderNumber));
			while (queryOrderId.next()) {
				orderId = queryOrderId.getString("ID");
				System.out.println("Order ID: " + orderId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}