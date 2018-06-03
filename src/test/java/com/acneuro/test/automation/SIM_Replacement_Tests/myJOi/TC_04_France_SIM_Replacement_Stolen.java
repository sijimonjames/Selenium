package com.acneuro.test.automation.SIM_Replacement_Tests.myJOi;

import org.junit.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.acneuro.test.automation.libraries.ConfigLib;
import com.acneuro.test.automation.mvno_project_library.FranceProjectSpecific;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;

public class TC_04_France_SIM_Replacement_Stolen extends ConfigLib{

	@Test
	public void simReplacementStolen() {

		String reasonForReplacement = "STOLEN";
		String joiNumber = "";
		String orderID = "";
		
		FranceProjectSpecific.FranceEnglishUrlSelection();
		
		// Step 1.1: latest active CIN product, fail if no active cin product
		// Step 1.2: Customer number for latest cin product
		// Step 1.3: customer login details found from customer number
		// Step 1.4: Login to myjoi with above login details
		joiNumber = FranceProjectSpecific.loginToFranceEnglish();
		System.out.println("Joi Number: " + joiNumber);
		// Step 5: My Subscriptions > find the same active subscription > My
		// Settings
		FranceProjectSpecific.franceManageSettingsFromSubscriptions(joiNumber);
		// Step 7: My settings > 'Replace Sim'> Lost Sim > check simType >
		// Submit, verify the submit message
		FranceProjectSpecific.selectAndSubmitReplaceSimFromMySettings(reasonForReplacement, "NANO");
		// get order id
		orderID = FranceProjectSpecific.simReplacementOrderdetails(joiNumber);

		Reporter.log(reasonForReplacement + " SIM Replacement Order ID: " + orderID, true);

		FranceProjectSpecific.simReplacementSuspensionOfProvisioningUsertaskFix(orderID);
		// check for - simReplacementPoliceReportUserTask
		FranceProjectSpecific.simReplacementPoliceVerificationReportUsertaskFix(orderID);
		// Ship waiting process
		boolean shipWaitIsProcessed = FranceProjectSpecific.processShipWait(orderID, "simReplacementShipWait");
		// Test is failed if shipping is not processed
		Assert.assertTrue(shipWaitIsProcessed);
		// Login and Activation of SIM
		boolean simActivationAction = MVNOProjectSpecific.checkIfActionExistsForOrder(orderID,
				"simReplacementActivation");
		Assert.assertTrue(simActivationAction);
		FranceProjectSpecific.LoginToFrMyJoiWithMsisdn(joiNumber);
		FranceProjectSpecific.activationOfSIMReplacement(reasonForReplacement);
		// Usertasks fix
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