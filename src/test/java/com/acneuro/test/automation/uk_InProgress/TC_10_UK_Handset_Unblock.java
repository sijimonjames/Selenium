package com.acneuro.test.automation.uk_InProgress;

import org.junit.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.acneuro.test.automation.libraries.ConfigLib;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;
import com.acneuro.test.automation.mvno_project_library.UKProjectSpecific;

public class TC_10_UK_Handset_Unblock extends ConfigLib {

	@Test
	public void simReplacementLost() {
		String reasonForReplacement = "LOST";
		String joiNumber = "";
		String orderID = "";
		// Step 1.1: latest active CIN product, fail if no active cin product
		// Step 1.2: Customer number for latest cin product
		// Step 1.3: customer login details found from customer number
		// Step 1.4: Login to myjoi with above login details
		joiNumber = UKProjectSpecific.LoginToUKMyJoiWithActiveSubscription();
		System.out.println("Joi Number: " + joiNumber);
		// Step 5: My Subscriptions > find the same active subscription > My
		// Settings
		UKProjectSpecific.ukManageSettingsFromSubscriptions(joiNumber);
		// Step 7: My settings > 'Replace Sim'> Lost Sim > check simType >
		// Submit, verify the submit message
		UKProjectSpecific.selectAndSubmitReplaceSimFromMySettings(reasonForReplacement);
		// Get the Order id and check the different processes and process ID
		driver.quit();
		orderID = UKProjectSpecific.simReplacementOrderdetails(joiNumber);

		Reporter.log(reasonForReplacement + " SIM Replacement Order ID: " + orderID, true);

		UKProjectSpecific.simReplacementSuspensionOfProvisioningUsertaskFix(orderID);
		// Shipping of New SIM with a different ICC ID
		boolean shipWaitIsProcessed = UKProjectSpecific.processShipWait(orderID, "simReplacementShipWait");
		// Test is failed if shipping is not processed
		Assert.assertTrue(shipWaitIsProcessed);
		// Login and Activation of SIM

		boolean simActivationAction = MVNOProjectSpecific.checkIfActionExistsForOrder(orderID, "simReplacementActivation");
		Assert.assertTrue(simActivationAction);
		UKProjectSpecific.LoginToUKMyJoiWithMsisdn(joiNumber);
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

}