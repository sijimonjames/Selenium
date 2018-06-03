package com.acneuro.test.automation.portingTests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.libraries.RestApiAutomation;
import com.acneuro.test.automation.mvno_project_library.FranceProjectSpecific;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;

public class TC_04_FrancePortOutTests {

	private static String msisdn = "";
	private static String emailID = "";
	private static String customerNumber = "";

	@Test
	public void portoutFrance() {
		String portOutId = "";
		String processId = "";

		// Take portout id from database/ ATTRIBUTE1 from CIN_PRODUCTS table
		String portOutIdAndMsisdn = FranceProjectSpecific.fetchPortOutRioCodeFromLatestCustomer("FR");
		portOutId = portOutIdAndMsisdn.split("@")[0];
		msisdn = portOutIdAndMsisdn.split("@")[1];
		customerNumber = portOutIdAndMsisdn.split("@")[2];

		System.out.println("joiNumber: " + msisdn);
		System.out.println("Rio Code: " + portOutId);
		System.out.println("Customer Number: " + customerNumber);

		emailID = MVNOProjectSpecific.emailIdUsingCustomerNumber(customerNumber);
		// temporarily update Email ID
		MVNOProjectSpecific.updateEmailIdInCust_customersTable(Constant.PERSONAL_EMAIL_ADRRESS, customerNumber);

		// fake EGP request API call to startPortout
		String apiPortOutForNumpos = RestApiAutomation.requestToStartPortoutForFrance(msisdn, portOutId);

		System.out.println("First portout request is processed----------"+apiPortOutForNumpos);

		RestApiAutomation.httpPostTest(Constant.NUMPOS_INCOMING_REQUEST, apiPortOutForNumpos);
		// Finish port out
		processId = MVNOProjectSpecific.verifyPortOutIsInitiatedInNumpos(msisdn);
		System.out.println("Process ID: " + processId);
		FranceProjectSpecific.checkIfActionExistsWithProcessId(processId, "pONumPos");
		boolean portoutFinish = FranceProjectSpecific.processpONumPos(processId, msisdn, portOutId);
		Assert.assertTrue(portoutFinish);
		boolean portOutProvisioning = FranceProjectSpecific.portOutProvisioningIsDone(processId);
		Assert.assertTrue(portOutProvisioning);
		// check for the porting status
		boolean portOutFinish = FranceProjectSpecific.processPortOutFinishIsDone(processId);
		Assert.assertTrue(portOutFinish);
		Reporter.log(String.format("The customer with msisdn %s is ported out", msisdn));
	}

	@AfterMethod
	private static void postMethodToChangeEmail() {
		// change the email id back to normal
		MVNOProjectSpecific.updateEmailIdInCust_customersTable(emailID, customerNumber);
		System.out.println(String.format("Email Adress is changed to original: %s", emailID));
	}
}