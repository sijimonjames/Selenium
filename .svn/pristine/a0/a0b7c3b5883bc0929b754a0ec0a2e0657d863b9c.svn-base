package com.acneuro.test.automation.payment_rejection_tests;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.acneuro.test.automation.db_connection_libraries.DiseDB2Connection;
import com.acneuro.test.automation.db_connection_libraries.DiseDBUtils;
import com.acneuro.test.automation.db_connection_libraries.OracleDBUtils;
import com.acneuro.test.automation.db_connection_libraries.OracleJdbcConnection;
import com.acneuro.test.automation.fileslibrary.FileTransferLibrary;
import com.acneuro.test.automation.fileslibrary.FileUtilsLibrary;
import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.mvno_project_library.Country;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;
import com.acneuro.test.automation.mvno_project_library.MVNO_DunningWorkflowSpecific;

/*
 * 
 *  @Author: SIJIMON JAMES
 *  @Test: Payment type should be GIRO After FATAL DD and CC rejections, also manual re-payment is done.
 *  
 */

public class TC_02_UK_Giro_After_CC_Rejction_Fatal {

	private static final String country = "GB";
	private static final String paymentMethodCCInDise = "VISA";
	private static final String paymentMethodCC = "CC";
	private static final String paymentMethodManual = "GIRO";
	private static final String ccRejectionReason = "FATAL";
	private static final String remoteDir_Payment_Rejection = "/opt/jackal-batch/disemp/input/";

	private static String diseAccountNumber = "unknown";
	private static String customerNumber = "unknown";
	private static String customerEmailId = "unknown";
	private static String disePaymentType = "unknown";

	@Test
	public void CC_Retry_Tests() {
		try {
			cinProductDetailsFromCRMdb();
			changeEmailIDForCommunications();
			// Getting dise account Id from Dise dataase
			getValuesFromDiseDB(customerNumber);
			// Check the value in Dise using web services
			String actualPaymentType = MVNO_DunningWorkflowSpecific.getAccountDetails(diseAccountNumber, "PaymentType",
					Country.GB.name());
			System.out.println(actualPaymentType);
			Assert.assertEquals(actualPaymentType, paymentMethodCCInDise);
			// Update dise with GIRO, update recurring payment
			MVNO_DunningWorkflowSpecific.cancellationOfCreditCard(diseAccountNumber, Country.GB.name());
			Thread.sleep(Constant.DEFAULT_SLEEP_TIME);
			// Verify the account payment method is changed to Giro
			actualPaymentType = MVNO_DunningWorkflowSpecific.getAccountDetails(diseAccountNumber, "PaymentType",
					Country.GB.name());
			Assert.assertEquals(actualPaymentType, paymentMethodManual);
			Thread.sleep(Constant.DEFAULT_SLEEP_TIME);
			// Check dise changed to CC to retry - Should not raise a CC (Still
			// in GIRO)
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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void cinProductDetailsFromCRMdb() {

		try {

			ResultSet crmSelectQuery = OracleJdbcConnection
					.crmDatabaseSelectQuery(OracleDBUtils.queryCIN_PRODUCT_TableForCreditCardActiveAccount());
			while (crmSelectQuery.next()) {

				customerNumber = crmSelectQuery.getString("CUSTOMER_NUMBER");
				
				System.out.println("CUSTOMER NUMBER: "+customerNumber);
			}

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

	private void changeEmailIDForCommunications() {
		/*
		 * Change Email Address of customer to amsterdamtestteam
		 */
		customerEmailId = MVNOProjectSpecific.emailIdUsingCustomerNumber(customerNumber);
		MVNOProjectSpecific.updateEmailIdInCust_customersTable(Constant.PERSONAL_EMAIL_ADRRESS, customerNumber);

	}

	@AfterMethod
	private void postMethodToChangeEmail() {
		// change the email id back to normal
		MVNOProjectSpecific.updateEmailIdInCust_customersTable(customerEmailId, customerNumber);
	}
}