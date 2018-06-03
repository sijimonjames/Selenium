/*
 * Author: Sijimon James
 * Test: Block and Unblock Handset for UK customer
 * Known issues: A bug has been raised - DEVOPS-2083
 */


package com.acneuro.test.automation.uk_InProgress;

import org.junit.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.acneuro.test.automation.libraries.ConfigLib;
import com.acneuro.test.automation.mvno_project_library.UKProjectSpecific;

public class TC_09_UK_Block_And_Unblock_Lost_Handset extends ConfigLib {

	public String blockLostHandset() {
		String reasonForReplacement = "LOST";
		String joiNumber = "";
		// Step 1: Login to myjoi with active subscription
		joiNumber = UKProjectSpecific.LoginToUKMyJoiWithActiveSubscription();
		System.out.println("Joi Number: " + joiNumber);
		// Step 2: My Subscriptions > find the same active subscription > My
		// Settings
		UKProjectSpecific.ukManageSettingsFromSubscriptions(joiNumber);
		// Step 3: My settings > 'Replace Sim'> Lost Sim > check simType >
		// Submit, verify the submit message
		UKProjectSpecific.selectAndSubmitBlockHandsetFromMySettings(reasonForReplacement);
		// Process block handset action to success
		boolean blockHandsetActionIsDone = UKProjectSpecific.processblockHandsetAction(joiNumber);
		Assert.assertTrue(blockHandsetActionIsDone);
		System.out.println(reasonForReplacement + " Handset is successfully Blocked");
		Reporter.log(reasonForReplacement + " Handset is successfully Blocked", true);
		return joiNumber;
	}

	@Test
	public void block_And_Unblock_Lost_Handset() {
		String reasonForReplacement = "LOST";
		String joiNumber = "";
		// Test1: Blocking the Handset

		 joiNumber = blockLostHandset();

		System.out.println("Joi Number of Handset to be Unblocked: " + joiNumber);
		Reporter.log("Joi Number of Handset to be Unblocked: " + joiNumber, true);
		// Process unblock handset action to success
		boolean unblockHandsetActionIsDone = UKProjectSpecific.processUnblockHandsetAction(joiNumber);
		Assert.assertTrue(unblockHandsetActionIsDone);
		System.out.println(reasonForReplacement + " Handset is successfully Released");
		Reporter.log(reasonForReplacement + " Handset is successfully Released", true);
		// The action should be in resolved status in CUE_REQUESTED_COMMANDS

		Reporter.getOutput();
	}

}