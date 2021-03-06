package com.acneuro.test.automation.payment_rejection_tests;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.acneuro.test.automation.db_connection_libraries.DiseDB2Connection;
import com.acneuro.test.automation.db_connection_libraries.DiseDBUtils;
import com.acneuro.test.automation.fileslibrary.FileTransferLibrary;
import com.acneuro.test.automation.fileslibrary.FileUtilsLibrary;
import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.mvno_project_library.Country;
import com.acneuro.test.automation.mvno_project_library.MVNO_DunningWorkflowSpecific;

// @Author: SIJIMON JAMES
// @Test: Reinstate of DD as payment method when CC retry OK after a NON-Fatal DD rejection

public class TC_05_ReinstateDD_CCRetryAfterDDNonFalalRejection_France {

	private static final String country = "FR";
	private static final String paymentMethodCCInDise = "VISA";
	private static final String paymentMethodDD = "DD";
	private static final String paymentMethodManual = "GIRO";
	private static final String rejectionReason = "NON-FATAL";
	// private static final String remoteDir_DuePay =
	// "/opt/billsrv/disemp/outbound/";
	private static final String remoteDir_Payment_Rejection = "/opt/jackal-batch/disemp/input/";
	private static final String remoteDir_DDActivation = "/opt/applications/sepa-mandate-update-disemp/test/input/";

	private static String diseAccountNumber = "unknown";
	private static String customerNumber = "unknown";
	private static String customerId = "unknown";

	@BeforeTest
	private void updateCustomerToDD() throws URISyntaxException, InterruptedException {

		customerId = MVNO_DunningWorkflowSpecific.latestCustomerNumberFromCuesWithDirectDebitPending();
		customerNumber = MVNO_DunningWorkflowSpecific.findCustomerNumberfromCustomerId(customerId);
		File myOCXfile = FileUtilsLibrary.csvFileForDirectDebitActivation(customerNumber, country);

		FileTransferLibrary.transferFileToRemoteDirectory(Constant.DD_HOSTNAME, myOCXfile, remoteDir_DDActivation);

		MVNO_DunningWorkflowSpecific.runWorkerForDDActivation();

		Assert.assertTrue(MVNO_DunningWorkflowSpecific.verifyDirectDebitActivatedInCues(customerId));
	}

	@Test
	public void CC_Retry_Tests() {

		String actualPaymentType = "";

		Reporter.log(
				"TestLink Test: MVNO-73:verify reinstate of DD as payment method when CC retry is OK for a non-fatal DD rejection", true);
		
		try {

			System.out.println("DD is activated for FR customer: " + customerNumber);
			Reporter.log("DD is activated for FR customer Number: " + customerNumber);

			// get the dise account number using customer number
			getValuesFromDiseDB(customerNumber);
			// Check the value in Dise using web services
			actualPaymentType = MVNO_DunningWorkflowSpecific.getAccountDetails(diseAccountNumber, "PaymentType",Country.FR.name());
			System.out.println(actualPaymentType);
			Assert.assertEquals(actualPaymentType, paymentMethodDD);
			
			System.err.println("CHANGE THE CC TYPE");

			// Update dise with GIRO, update recurring payment
			MVNO_DunningWorkflowSpecific.cancellationOfDirectDebit(diseAccountNumber,Country.FR.name());
			Thread.sleep(Constant.DEFAULT_SLEEP_TIME);

			// Verify the account payment method is changed to Giro
			actualPaymentType = MVNO_DunningWorkflowSpecific.getAccountDetails(diseAccountNumber, "PaymentType",Country.FR.name());
			Assert.assertEquals(actualPaymentType, paymentMethodManual);

			Thread.sleep(Constant.DEFAULT_SLEEP_TIME);

			// Create a dd rejection csv file
			File localDDRejectionFile = FileUtilsLibrary.csvFileForCuesPaymentMethodRejection(customerNumber,
					diseAccountNumber, country, paymentMethodDD, rejectionReason);

			/*
			 * Move the file to required library
			 * 'file:///opt/billsrv/disemp/outbound'
			 */
			FileTransferLibrary.transferFileToRemoteDirectory(Constant.JACKAL_HOSTNAME, localDDRejectionFile,
					remoteDir_Payment_Rejection);

			// Run the worker
			MVNO_DunningWorkflowSpecific.runWorkerPaymentRejection_Jackal();

			// Check for CC in Dise is active
			// Check dise changed to CC to retry
			String disePaymentType = MVNO_DunningWorkflowSpecific.getAccountDetails(diseAccountNumber, "PaymentType",Country.FR.name());
			Assert.assertEquals(disePaymentType, paymentMethodCCInDise);
			// Create a csv file for CC retry OK
			File localCC_OK_File = FileUtilsLibrary.csvFileForCCRetryOK(customerNumber, diseAccountNumber, country);
			/*
			 * Move the file to required library
			 * 'file:///opt/billsrv/disemp/outbound'
			 */
			FileTransferLibrary.transferFileToRemoteDirectory(Constant.JACKAL_HOSTNAME, localCC_OK_File,
					remoteDir_Payment_Rejection);
			// Run the worker
			MVNO_DunningWorkflowSpecific.runWorkerCCRetryOK();
			// Verify DD is active everywhere
			String DD_Re_Instate = MVNO_DunningWorkflowSpecific.getAccountDetails(diseAccountNumber, "PaymentType",Country.FR.name());
			Assert.assertEquals(DD_Re_Instate, paymentMethodDD);

			/*
			 * Check 'UpdateCurrentRecurringPayment' is finished in success find
			 * the process ID
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getValuesFromDiseDB(String customerNumber) throws SQLException {

		try {
			ResultSet diseSelectQuery = DiseDB2Connection
					.diseDatabaseSelectQueryForFrance(DiseDBUtils.queryDiseAccountNumberFromCRMCustomerNumber(customerNumber));
			while (diseSelectQuery.next()) {

				diseAccountNumber = diseSelectQuery.getString("DISEMP_ACCOUNT_NUMBER");
				System.out.println("DISEMP Account Number: " + diseAccountNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}