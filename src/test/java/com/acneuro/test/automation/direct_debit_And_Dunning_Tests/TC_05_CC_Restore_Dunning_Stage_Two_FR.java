package com.acneuro.test.automation.direct_debit_And_Dunning_Tests;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.acneuro.test.automation.db_connection_libraries.DiseDB2Connection;
import com.acneuro.test.automation.db_connection_libraries.DiseDBUtils;
import com.acneuro.test.automation.fileslibrary.FileTransferLibrary;
import com.acneuro.test.automation.fileslibrary.FileUtilsLibrary;
import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.mvno_project_library.Country;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;
import com.acneuro.test.automation.mvno_project_library.MVNO_DunningWorkflowSpecific;

/*
 *  @Author: SIJIMON JAMES
 *  @Test: Reinstate of DD as payment method after payment is done when CC retry rejected after a NON-Fatal DD rejection
 */

public class TC_05_CC_Restore_Dunning_Stage_Two_FR {

	private static final String country = "FR";
	private static final String paymentMethodCCInDise = "VISA";
	private static final String paymentMethodCC = "CC";
	private static final String paymentMethodDD = "DD";
	private static final String paymentMethodManual = "GIRO";
	private static final String ddRejectionReason = "FATAL";
	private static final String ccRejectionReason = "NON-FATAL";
	private static final String remoteDir_DDActivation = "/opt/applications/sepa-mandate-update-disemp/test/input/";
	private static final String remoteDir_Payment_Rejection = "/opt/jackal-batch/disemp/input/";

	private static String diseAccountNumber = "unknown";
	private static String customerNumber = "unknown";
	private static String customerId = "unknown";
	private static String customerEmailId = "unknown";
	private static String disePaymentType = "unknown";

	@Test
	public void CC_Retry_Tests() {

		try {
			System.out.println("DD is activated for FR customer: " + customerNumber);
			Reporter.log("DD is activated for FR customer Number: " + customerNumber);

			// Getting dise account Id from Dise dataase
			getValuesFromDiseDB(customerNumber);
			// Check the value in Dise using web services
			String actualPaymentType = MVNO_DunningWorkflowSpecific.getAccountDetails(diseAccountNumber, "PaymentType",
					Country.FR.name());
			System.out.println(actualPaymentType);
			Assert.assertEquals(actualPaymentType, paymentMethodDD);
			// Update dise with GIRO, update recurring payment
			MVNO_DunningWorkflowSpecific.cancellationOfDirectDebit(diseAccountNumber, Country.FR.name());
			Thread.sleep(Constant.DEFAULT_SLEEP_TIME);
			// Verify the account payment method is changed to Giro
			actualPaymentType = MVNO_DunningWorkflowSpecific.getAccountDetails(diseAccountNumber, "PaymentType",
					Country.FR.name());
			Assert.assertEquals(actualPaymentType, paymentMethodManual);
			Thread.sleep(Constant.DEFAULT_SLEEP_TIME);
			// Create a DD rejection csv file
			File localDDRejectionFile = FileUtilsLibrary.csvFileForCuesPaymentMethodRejection(customerNumber,
					diseAccountNumber, country, paymentMethodDD, ddRejectionReason);
			/*
			 * Move the file to required Jackal library
			 */
			FileTransferLibrary.transferFileToRemoteDirectory(Constant.JACKAL_HOSTNAME, localDDRejectionFile,
					remoteDir_Payment_Rejection);

			// Run the worker
			MVNO_DunningWorkflowSpecific.runWorkerPaymentRejection_Jackal();
			// Check for CC in Dise is active
			// Check dise changed to CC to retry
			disePaymentType = MVNO_DunningWorkflowSpecific.getAccountDetails(diseAccountNumber, "PaymentType",
					Country.FR.name());
			Assert.assertEquals(disePaymentType, paymentMethodCCInDise);

			Thread.sleep(Constant.DEFAULT_SLEEP_TIME);

			// Update dise with GIRO, update recurring payment
			MVNO_DunningWorkflowSpecific.cancellationOfCreditCard(diseAccountNumber, Country.FR.name());
			Thread.sleep(Constant.DEFAULT_SLEEP_TIME);

			// Verify the account payment method is changed to Giro
			actualPaymentType = MVNO_DunningWorkflowSpecific.getAccountDetails(diseAccountNumber, "PaymentType",
					Country.FR.name());
			Assert.assertEquals(actualPaymentType, paymentMethodManual);

			// Create a csv file for CC Rejection Non-Fatally
			File localCCRejectionFile = FileUtilsLibrary.csvFileForCuesPaymentMethodRejection(customerNumber,
					diseAccountNumber, country, paymentMethodCC, ccRejectionReason);
			/*
			 * Move the file to required library
			 * 'file:///opt/billsrv/disemp/outbound'
			 */
			FileTransferLibrary.transferFileToRemoteDirectory(Constant.JACKAL_HOSTNAME, localCCRejectionFile,
					remoteDir_Payment_Rejection);

			// Run the worker
			MVNO_DunningWorkflowSpecific.runWorkerPaymentRejection_Jackal();
			Thread.sleep(Constant.DEFAULT_SLEEP_TIME);

			// Check for CC in Dise is active
			// Check dise changed to CC to retry
			disePaymentType = MVNO_DunningWorkflowSpecific.getAccountDetails(diseAccountNumber, "PaymentType",
					Country.FR.name());
			Assert.assertEquals(disePaymentType, paymentMethodManual);

			// dunningStageOne
			dunningStageOne();

			// dunning Stage Two - Customer is suspended
			dunningStageTwo();

			// Create a file for payment is done
			Repayment_dunningStageTwo();
			// verify the dunnning events raised in database
			// Check dise changed to DD to retry
			disePaymentType = MVNO_DunningWorkflowSpecific.getAccountDetails(diseAccountNumber, "PaymentType",
					Country.FR.name());

			Assert.assertEquals(disePaymentType, paymentMethodCCInDise);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void getValuesFromDiseDB(String customerNumber) throws SQLException {

		try {

			ResultSet diseSelectQuery = DiseDB2Connection.diseDatabaseSelectQueryForFrance(
					DiseDBUtils.queryDiseAccountNumberFromCRMCustomerNumber(customerNumber));
			while (diseSelectQuery.next()) {

				diseAccountNumber = diseSelectQuery.getString("DISEMP_ACCOUNT_NUMBER");
				System.out.println("DISEMP Account Number: " + diseAccountNumber);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void dunningStageOne() throws SQLException {

		try {
			// Dunning stage 1 file
			File localDunningStageOneFile = FileUtilsLibrary.csvFileForDunningRejection(diseAccountNumber,
					customerNumber, "STDDS1", "DMV1", "135", country);
			// Move the file from local directory to Remote location
			FileTransferLibrary.transferFileToRemoteDirectory(Constant.JACKAL_HOSTNAME, localDunningStageOneFile,
					remoteDir_Payment_Rejection);
			// Run the dunning mediation job
			// Run the worker
			MVNO_DunningWorkflowSpecific.runWorkerDuuningMediation_Jackal();
			// verify the dunnning events raised in database

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	private static void dunningStageTwo() {

		try {
			// Dunning stage 1 file
			File localDunningStageOneFile = FileUtilsLibrary.csvFileForDunningRejection(diseAccountNumber,
					customerNumber, "SUSACC", "DMVS", "155", country);
			// Move the file from local directory to Remote location
			FileTransferLibrary.transferFileToRemoteDirectory(Constant.JACKAL_HOSTNAME, localDunningStageOneFile,
					remoteDir_Payment_Rejection);
			// Run the dunning mediation job
			// Run the worker
			MVNO_DunningWorkflowSpecific.runWorkerDuuningMediation_Jackal();
			// verify the dunnning events raised in database

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	private static void Repayment_dunningStageTwo() {

		try {
			// Dunning stage 1 file
			File localDunningStageOneRepaymentFile = FileUtilsLibrary.csvFileForDunningRejection(diseAccountNumber,
					customerNumber, "RSTACC", "MVP2", "125", country);
			// Move the file from local directory to Remote location
			FileTransferLibrary.transferFileToRemoteDirectory(Constant.JACKAL_HOSTNAME,
					localDunningStageOneRepaymentFile, remoteDir_Payment_Rejection);
			// Run the dunning mediation job
			// Run the worker
			MVNO_DunningWorkflowSpecific.runWorkerDuuningMediation_Jackal();

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeMethod
	private void updateCustomerToDD() throws URISyntaxException, InterruptedException {
		customerId = MVNO_DunningWorkflowSpecific.latestCustomerNumberFromCuesWithDirectDebitPending();
		customerNumber = MVNO_DunningWorkflowSpecific.findCustomerNumberfromCustomerId(customerId);

		/*
		 * Change Email Address of customer to amsterdamtestteam
		 */
		customerEmailId = MVNOProjectSpecific.emailIdUsingCustomerNumber(customerNumber);
		MVNOProjectSpecific.updateEmailIdInCust_customersTable(Constant.PERSONAL_EMAIL_ADRRESS, customerNumber);

		File myOCXfile = FileUtilsLibrary.csvFileForDirectDebitActivation(customerNumber, country);

		FileTransferLibrary.transferFileToRemoteDirectory(Constant.DD_HOSTNAME, myOCXfile, remoteDir_DDActivation);

		MVNO_DunningWorkflowSpecific.runWorkerForDDActivation();

		// May not be active in Cues, if not run DHC
		Assert.assertTrue(MVNO_DunningWorkflowSpecific.verifyDirectDebitActivatedInCues(customerId));
	}

	@AfterMethod
	private void postMethodToChangeEmail() {
		// change the email id back to normal
		MVNOProjectSpecific.updateEmailIdInCust_customersTable(customerEmailId, customerNumber);
		System.out.println(String.format("Email Adress is changed to original: %s", customerEmailId));
	}
}