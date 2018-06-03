package com.acneuro.test.automation.direct_debit_And_Dunning_Tests;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.Assert;
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
 *  @Test: Reinstate of CC as payment method after dunning stage 1
 */

public class TC_03_CC_Restore_Dunning_Stage_One_UK {

	private static final String country = "GB";
	private static final String paymentMethodCCInDise = "VISA";
	private static final String paymentMethodCC = "CC";
	private static final String paymentMethodDD = "DD";
	private static final String paymentMethodManual = "GIRO";
	private static final String ddRejectionReason = "FATAL";
	private static final String ccRejectionReason = "NON-FATAL";
	private static final String remoteDir_Payment_Rejection = "/opt/jackal-batch/disemp/input/";

	private static String diseAccountNumber = "unknown";
	private static String customerNumber = "unknown";
	private static String customerId = "unknown";
	private static String customerEmailId = "unknown";
	private static String disePaymentType = "unknown";

	@Test
	public void CC_Retry_Tests() {

		try {

			// Getting dise account Id from Dise dataase
			getValuesFromDiseDB(customerNumber);
			// Check the value in Dise using web services
			String actualPaymentType = MVNO_DunningWorkflowSpecific.getAccountDetails(diseAccountNumber, "PaymentType",
					Country.GB.name());
			System.out.println(actualPaymentType);
			Assert.assertEquals(actualPaymentType, paymentMethodDD);
			// Update dise with GIRO, update recurring payment
			MVNO_DunningWorkflowSpecific.cancellationOfDirectDebit(diseAccountNumber, Country.GB.name());
			Thread.sleep(Constant.DEFAULT_SLEEP_TIME);
			// Verify the account payment method is changed to Giro
			actualPaymentType = MVNO_DunningWorkflowSpecific.getAccountDetails(diseAccountNumber, "PaymentType",
					Country.GB.name());
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
					Country.GB.name());
			Assert.assertEquals(disePaymentType, paymentMethodCCInDise);

			Thread.sleep(Constant.DEFAULT_SLEEP_TIME);

			// Update dise with GIRO, update recurring payment
			MVNO_DunningWorkflowSpecific.cancellationOfCreditCard(diseAccountNumber, Country.GB.name());
			Thread.sleep(Constant.DEFAULT_SLEEP_TIME);

			// Verify the account payment method is changed to Giro
			actualPaymentType = MVNO_DunningWorkflowSpecific.getAccountDetails(diseAccountNumber, "PaymentType",
					Country.GB.name());
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
					Country.GB.name());
			Assert.assertEquals(disePaymentType, paymentMethodManual);

			// dunningStageOne
			dunningStageOne();

			// Create a file for payment is done
			Repayment_dunningStageOne();

			// Check dise changed to DD to retry
			disePaymentType = MVNO_DunningWorkflowSpecific.getAccountDetails(diseAccountNumber, "PaymentType",
					Country.GB.name());

			Assert.assertEquals(disePaymentType, paymentMethodCCInDise);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getValuesFromDiseDB(String customerNumber) throws SQLException {

		try {

			ResultSet diseSelectQuery = DiseDB2Connection.diseDatabaseSelectQueryForUK(
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
					customerNumber, "STDDS1", "DMV1", "125", country);
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

	private static void Repayment_dunningStageOne() throws SQLException {

		try {
			// Dunning stage 1 file
			File localDunningStageOneRepaymentFile = FileUtilsLibrary.csvFileForDunningRejection(diseAccountNumber,
					customerNumber, "PAYREC", "MVPY", ".00", country);
			// Move the file from local directory to Remote location
			FileTransferLibrary.transferFileToRemoteDirectory(Constant.JACKAL_HOSTNAME,
					localDunningStageOneRepaymentFile, remoteDir_Payment_Rejection);
			// Run the dunning mediation job
			// Run the worker
			MVNO_DunningWorkflowSpecific.runWorkerDuuningMediation_Jackal();
			// verify the dunnning events raised in database
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeMethod
	private void findLatestCustomerWithDDActive() throws URISyntaxException, InterruptedException {

		customerId = MVNO_DunningWorkflowSpecific.latestCustomerNumberFromCuesWithDirectDebitActiveForGB();
		customerNumber = MVNO_DunningWorkflowSpecific.findCustomerNumberfromCustomerId(customerId);

		/*
		 * Change Email Address of customer to amsterdamtestteam
		 */
		customerEmailId = MVNOProjectSpecific.emailIdUsingCustomerNumber(customerNumber);
		MVNOProjectSpecific.updateEmailIdInCust_customersTable(Constant.PERSONAL_EMAIL_ADRRESS, customerNumber);
		System.err.println("Email Changed to " + Constant.PERSONAL_EMAIL_ADRRESS);

	}

	@AfterMethod
	private void postMethodToChangeEmail() {
		// change the email id back to normal
		MVNOProjectSpecific.updateEmailIdInCust_customersTable(customerEmailId, customerNumber);
		System.err.println(String.format("Email Adress is changed to original: %s", customerEmailId));
	}
}