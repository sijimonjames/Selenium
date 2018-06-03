package com.acneuro.test.automation.france_InProgress;

import org.junit.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.acneuro.test.automation.libraries.ConfigLib;
import com.acneuro.test.automation.mvno_project_library.FranceProjectSpecific;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;

public class TC_11_France_Handset_Unblock extends ConfigLib {

	@Test
	public void simReplacementOther() {

		String reasonForReplacement = "OTHER";
		String joiNumber = "";
		String orderID = "";
		// Step 1: Login to MyJOi with Active user id
		joiNumber = FranceProjectSpecific.loginToFranceEnglish();
		System.out.println("Joi Number: " + joiNumber);
		// Step 5: My Subscriptions > find the same active subscription > My
		// Settings
		FranceProjectSpecific.franceManageSettingsFromSubscriptions(joiNumber);
		// Step 7: My settings > 'Replace Sim'> Lost Sim > check simType >
		// Submit, verify the submit message
		FranceProjectSpecific.selectAndSubmitReplaceSimFromMySettings(reasonForReplacement, "NANO");
		driver.quit();
		orderID = FranceProjectSpecific.simReplacementOrderdetails(joiNumber);
		Reporter.log(reasonForReplacement + " SIM Replacement Order ID: " + orderID, true);
		// Shipping of New SIM with a different ICC ID
		boolean shipWaitIsProcessed = FranceProjectSpecific.processShipWait(orderID, "simReplacementShipWait");
		// Test is failed if shipping is not processed
		Assert.assertTrue(shipWaitIsProcessed);
		// Login and Activation of SIM
		boolean simActivationAction = MVNOProjectSpecific.checkIfActionExistsForOrder(orderID, "simReplacementActivation");
		Assert.assertTrue(simActivationAction);
		FranceProjectSpecific.LoginToFrMyJoiWithMsisdn(joiNumber);
		FranceProjectSpecific.activationOfSIMReplacement(reasonForReplacement);

		// Fixing all provisioning user task
		FranceProjectSpecific.simReplacementActivationByProvisioningUsertaskFix(orderID, reasonForReplacement);
		// Check for action "simReplacementFinish" is "SUCCESS"
		boolean simOrderIsDone = FranceProjectSpecific.checkForSimReplacementOrderIsDone(orderID);
		Assert.assertTrue(simOrderIsDone);
		// Verify CIN_PRODUCT with order id, should be Active
		boolean verifyProductIsActive = FranceProjectSpecific.verifyCinProductIsActive(orderID);
		Assert.assertTrue(verifyProductIsActive);

		Reporter.getOutput();

	}

}