package com.acneuro.test.automation.upgrade_downgrade_tests;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.acneuro.test.automation.db_connection_libraries.OracleJdbcConnection;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;
import com.acneuro.test.automation.mvno_project_library.UKProjectSpecific;

public class TC_01_UK_JOi_Small_4G_Upgrade_to_JOi_Medium_4G {

	public static String EMAIL_ID = null;
	public static String CUSTOMER_NUMBER = null;
	public static String CUSTOMER_ID = null;
	public static String MSISDN = "";
	public static String orderNumber = null;
	public static String orderId = null;
	public static final String ACTIVE_PRODUCT = "UKMV08MainMOB67";
	public static final String UPGRADE_PRODUCT = "UKMV08MainMOB71";


	@Test
	public void changeMyPlan() throws InterruptedException {

		// Prerequisite:
		findCustomerAndLoginToMyJoi();

		// There are no upgrade/maintenance orders that are pending for the
		// Subscription (any status except Cancelled / Complete)
		// Subscription status should be active. The related CIN product (main
		// sub) status must be Active (ACT)
		// Submission of the Upgrade/Downgrade order can only be done from 1st
		// of the month till 3rd last day of the month

		// change my plan
		
		UKProjectSpecific.selectMySubscriptionFromMyJOi(MSISDN);

		// Click on 'changemyplan'

		MVNOProjectSpecific.select_change_my_plan_option();
		MVNOProjectSpecific.verify_My_current_Plan(ACTIVE_PRODUCT);

		MVNOProjectSpecific.Change_My_Pice_Plan(UPGRADE_PRODUCT);
		MVNOProjectSpecific.continueAfterChangeMyPlan();

		
		//verify the status of the order
		orderNumber = MVNOProjectSpecific.orderNumberForUpgradeOrder(CUSTOMER_ID);
		
		
		orderId = MVNOProjectSpecific.orderIdForUpgradeOrder(CUSTOMER_ID);
		
		// Check first action is created 'ouCommunication'
		Assert.assertTrue(MVNOProjectSpecific.checkIfActionExistsForOrder(orderId, "ouCommunication"));
		
	}

	private static void findCustomerAndLoginToMyJoi() {
		// customer number
		// Email ID

		// QUERY TO FIND THE LATEST ACTIVE CUSTMER USING PRODUCT CODE
		// NEED TO FIND FROM CINPRODUCT TABLE
		
		String querytofindcustomernumber = "SELECT * FROM CIN_PRODUCTS WHERE CUSTOMER_NUMBER = "
				+ "(SELECT MAX(CUSTOMER_NUMBER) FROM CIN_PRODUCTS WHERE PRODUCT = "
				+ "'"+ACTIVE_PRODUCT+"' AND STATUS = 'ACT') AND PRODUCT_TYPE = 'MBSUBSCRIPTION'";

		ResultSet getLatestActiveCustomerNumber = OracleJdbcConnection
				.crmDatabaseSelectQuery(querytofindcustomernumber);
		try {
			while (getLatestActiveCustomerNumber.next()) {

				CUSTOMER_NUMBER = getLatestActiveCustomerNumber.getString("CUSTOMER_NUMBER");
				CUSTOMER_ID = getLatestActiveCustomerNumber.getString("CUSTOMER_ID");
				MSISDN = getLatestActiveCustomerNumber.getString("ATTRIBUTE1");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Reporter.log("customer number: " + CUSTOMER_NUMBER, true);
		Reporter.log("customer ID: " + CUSTOMER_ID, true);

		EMAIL_ID = MVNOProjectSpecific.emailIdUsingCustomerNumber(CUSTOMER_NUMBER);
		UKProjectSpecific.LoginToUKMyJoiWithLatestEmailID(EMAIL_ID);
		
	}
}