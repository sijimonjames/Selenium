package com.acneuro.test.automation.SIM_Replacement_Tests.myJOi;

import java.sql.ResultSet;

import org.junit.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.acneuro.test.automation.db_connection_libraries.OracleDBUtils;
import com.acneuro.test.automation.db_connection_libraries.OracleJdbcConnection;
import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;
import com.acneuro.test.automation.mvno_project_library.UKProjectSpecific;

public class TC_01_UK_SIM_Replacement_Lost {

	private static final String reasonForReplacement = "LOST";
	private static String joiNumber = "07000003304";
	private static String emailId = "";
	private static String orderID = "";
	private static String customerNumber = "";

	@Test
	public void simReplacementLost() {

		// Step 1.1: latest active CIN product, fail if no active cin product
		// Step 1.2: Customer number for latest cin product
		// Step 1.3: customer login details found from customer number
		// Step 1.4: Login to myjoi with above login details
		joiNumber = UKProjectSpecific.LoginToUKMyJoiWithActiveSubscription();
		System.out.println("Joi Number: " + joiNumber);

		// get Email ID and customer number
		getEmailIdAndCustomerNumber();
		// Changed customer Email ID to Amsterdam test team mail id
		MVNOProjectSpecific.updateEmailIdInCust_customersTable(Constant.PERSONAL_EMAIL_ADRRESS, customerNumber);
		// Step 5: My Subscriptions > find the same active subscription > My
		// Settings
		UKProjectSpecific.ukManageSettingsFromSubscriptions(joiNumber);
		// Step 7: My settings > 'Replace Sim'> Lost Sim > check simType >
		// Submit, verify the submit message
		UKProjectSpecific.selectAndSubmitReplaceSimFromMySettings(reasonForReplacement);
		// Get the Order id and check the different processes and process ID
		orderID = UKProjectSpecific.simReplacementOrderdetails(joiNumber);

		Reporter.log(reasonForReplacement + " SIM Replacement Order ID: " + orderID, true);

		UKProjectSpecific.simReplacementSuspensionOfProvisioningUsertaskFix(orderID);
		// Shipping of New SIM with a different ICC ID
		boolean shipWaitIsProcessed = UKProjectSpecific.processShipWait(orderID, "simReplacementShipWait");
		// Test is failed if shipping is not processed
		Assert.assertTrue(shipWaitIsProcessed);
		// Login and Activation of SIM

		boolean simActivationAction = MVNOProjectSpecific.checkIfActionExistsForOrder(orderID,
				"simReplacementActivation");
		Assert.assertTrue(simActivationAction);
		UKProjectSpecific.LoginToUKMyJoiWithLatestEmailID(emailId);
		UKProjectSpecific.activationOfSIMReplacement(reasonForReplacement);
		// fixing all user tasks
		UKProjectSpecific.simReplacementActivationByProvisioningUsertaskFix(orderID, reasonForReplacement);
		// Check for action "simReplacementFinish" is "SUCCESS"
		boolean simOrderIsDone = UKProjectSpecific.checkForSimReplacementOrderIsDone(orderID);
		Assert.assertTrue(simOrderIsDone);
		// Verify CIN_PRODUCT with order id, should be Active
		boolean verifyProductIsActive = UKProjectSpecific.verifyCinProductIsActive(orderID);
		Assert.assertTrue(verifyProductIsActive);
		Reporter.getOutput();
	}

	private String queryToGetEmailId(String joiNumber) {
		return "SELECT * FROM CUST_USERS WHERE CUST_ID = (SELECT CUSTOMER_ID FROM CIN_PRODUCTS where ATTRIBUTE1 = '"
				+ joiNumber + "')";

	}

	private void getEmailIdAndCustomerNumber() {
		try {

			ResultSet queryEmailId = OracleJdbcConnection.crmDatabaseSelectQuery(queryToGetEmailId(joiNumber));
			while (queryEmailId.next()) {

				emailId = queryEmailId.getString("USERNAME");
			}
			ResultSet queryCustomerNumber = OracleJdbcConnection
					.crmDatabaseSelectQuery(OracleDBUtils.queryCinProductsWithMsisdn(joiNumber));
			while (queryCustomerNumber.next()) {

				customerNumber = queryCustomerNumber.getString("CUSTOMER_NUMBER");
				System.out.println("Customer Number" + customerNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterMethod
	private static void postMethodToChangeEmail() {
		// change the email id back to normal
		MVNOProjectSpecific.updateEmailIdInCust_customersTable(emailId, customerNumber);
		System.out.println(String.format("Email Adress is changed to original: %s", emailId));
		Reporter.log("Email Adress of the Customer: " + emailId);
	}
}
