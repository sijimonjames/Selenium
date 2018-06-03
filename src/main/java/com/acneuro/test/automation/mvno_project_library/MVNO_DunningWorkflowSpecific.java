package com.acneuro.test.automation.mvno_project_library;

import static com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific.scrollDownToView;
import static java.lang.Thread.sleep;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.acneuro.test.automation.db_connection_libraries.OracleDBUtils;
import com.acneuro.test.automation.db_connection_libraries.OracleJdbcConnection;
import com.acneuro.test.automation.libraries.ConfigLib;
import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.libraries.WebServicesAutomationLibrary;

public class MVNO_DunningWorkflowSpecific extends ConfigLib {

	public static String latestCustomerNumberFromCuesWithDirectDebitPending() {
		String customerId = "";
		try {
			String queryCustID = OracleDBUtils.queryCustomerIdWithDirectDebitPendingForFrance();
			ResultSet getCustId = OracleJdbcConnection.crmDatabaseSelectQuery(queryCustID);

			while (getCustId.next()) {
				customerId = getCustId.getString("CUST_ID");
			}

			System.out.println("Cusotmer found with CC active and DD pending");
			System.out.println("Customer ID is: " + customerId);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerId;
	}

	public static String latestCustomerNumberFromCuesWithDirectDebitActiveForGB() {
		String customerId = "";
		try {
			String queryCustID = OracleDBUtils.queryCustomerIdWithDirectDebitActiveForGB();
			ResultSet getCustId = OracleJdbcConnection.crmDatabaseSelectQuery(queryCustID);

			while (getCustId.next()) {
				customerId = getCustId.getString("CUST_ID");
			}

			System.out.println("Cusotmer found with DD Active for UK");
			System.out.println("Customer ID is: " + customerId);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerId;
	}

	public static String findCustomerNumberfromCustomerId(String customerId) {
		String customerNumber = "";
		try {
			String queryCustomerNumber = OracleDBUtils.queryCinProductsWithCustId(customerId);
			ResultSet getCustomerNumber = OracleJdbcConnection.crmDatabaseSelectQuery(queryCustomerNumber);

			while (getCustomerNumber.next()) {
				customerNumber = getCustomerNumber.getString("CUSTOMER_NUMBER");
				System.out.println("Customer Number is: " + customerNumber);
				Reporter.log("Customer Number: " + customerNumber);

				if (getCustomerNumber.wasNull()) {
					System.err.println("No active customer found with DD Active / pending, Activate a new customer");
					throw new AssertionError();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerNumber;
	}

	public static boolean verifyDirectDebitActivatedInWildBill(String customerNumber) {
		String status = "";
		try {
			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {

				// verify DD is active in Wildbill
				String queryWildbillDirectDebitStatus = queryToVerifyDDisActiveInWildBill(customerNumber);
				ResultSet getDirectDebitStatus = OracleJdbcConnection
						.wildbillDatabaseSelectQuery(queryWildbillDirectDebitStatus);

				while (getDirectDebitStatus.next()) {
					String paymentMethod = getDirectDebitStatus.getString("PAY_METHOD");
					status = getDirectDebitStatus.getString("STATUS");

					if (paymentMethod.equalsIgnoreCase("DIRECT_DEBIT") && status.equalsIgnoreCase("ACTIVE")) {
						System.out.println("The DDs status is wildbill is ACTIVE");
						return true;
					} else {
						System.err.println("The Status of DD in Wild bill is not as expected, the status is " + status);
						return false;
					}
				}
				Thread.sleep(Constant.DEFAULT_SLEEP_TIME);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("The Status of DD in Wild bill is not as expected, the status is " + status);
		return false;
	}

	public static boolean verifyDirectDebitActivatedInCues(String customerId) {

		String ddStatus = "";
		String ccStatus = "";
		try {

			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
				// verify DD is active in Wildbill
				String queryCuesDirectDebitStatus = queryToVerifyDDisActiveInCues(customerId);
				String queryCuesCreditCardStatus = queryToVerifyCCisInactiveInCues(customerId);
				ResultSet getDirectDebitStatus = OracleJdbcConnection
						.crmDatabaseSelectQuery(queryCuesDirectDebitStatus);
				ResultSet getCreditCardStatus = OracleJdbcConnection.crmDatabaseSelectQuery(queryCuesCreditCardStatus);

				while (getDirectDebitStatus.next()) {
					ddStatus = getDirectDebitStatus.getString("PAYMENT_STATUS");
				}
				while (getCreditCardStatus.next()) {
					ccStatus = getCreditCardStatus.getString("PAYMENT_STATUS");
				}

				if (ddStatus.equalsIgnoreCase("ACTIVE") && ccStatus.equalsIgnoreCase("INACTIVE")) {
					System.err.println(String.format("The DD status is %s", ddStatus));
					System.out.println(String.format("The CC status is %s", ccStatus));
					Reporter.log(String.format("The DDs status is CRM is %s and CC is %s", ddStatus, ccStatus));
					return true;
				}

				Thread.sleep(Constant.DEFAULT_SLEEP_TIME);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void runWorkerForDDActivation() throws InterruptedException {

		MVNOProjectSpecific.openNewBrowserwithinTestsForWorkers();
		driver.get(Constant.DDMANDATEWorkerUrl);
		// Trigger worker 'test FR UMR'
		WebElement workerUpdateDirectDebit = driver
				.findElement(By.xpath("//*[@id='oszApKQssBS3BXXF2q+DAE+X4Us=']/div[2]/a[1]"));
		scrollDownToView(workerUpdateDirectDebit);
		workerUpdateDirectDebit.click();
		sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
		System.out.println("The DD activation worker is triggered");
		driver.quit();
	}

	// Send a request to get payment details
	public static String getAccountDetails(String diseAccountNumber, String nodeValue, String country) {

		String getAccountDetails = WebServicesAutomationLibrary.requestToDiseToGetAccountDetails(diseAccountNumber);
		String nodeValueResult = WebServicesAutomationLibrary.getResponseValueFromDise(getAccountDetails, nodeValue,
				country);
		return nodeValueResult;
	}

	// Send a request to get payment details
	private static void cancellationOfPaymentMethod(String diseAccountNumber, String payment_method, String country) {
		String requestToCancelPaymentMethod = null;
		// get 'LAST AMENDED DATE' from dise
		String disePaymentRequest = WebServicesAutomationLibrary.requestToDiseToGetAccountDetails(diseAccountNumber);

		String ResultLastAmendedDate = WebServicesAutomationLibrary.getResponseValueFromDise(disePaymentRequest,
				"LastAmendedDate", country);
		System.out.println("Last amended date: " + ResultLastAmendedDate);

		if (payment_method.equalsIgnoreCase("DD")) {

			requestToCancelPaymentMethod = WebServicesAutomationLibrary
					.requestToDiseToUpdatePaymentMethodForDirectDebit(diseAccountNumber, ResultLastAmendedDate);
			System.out.println("REQUEST TO CANCEL DD-------- " + requestToCancelPaymentMethod);
		} else if (payment_method.equalsIgnoreCase("CC")) {
			requestToCancelPaymentMethod = WebServicesAutomationLibrary
					.requestToDiseToUpdatePaymentMethodForCreditCard(diseAccountNumber, ResultLastAmendedDate);
			System.out.println("REQUEST TO CANCEL CC-------- " + requestToCancelPaymentMethod);
		}
		// modify external reference id to verify

		String ActualExternalReference = WebServicesAutomationLibrary
				.getResponseValueFromDise(requestToCancelPaymentMethod, "ExternalReference", country);
		System.out.println("actual external reference" + ActualExternalReference);
	}

	public static void cancellationOfDirectDebit(String diseAccountNumber, String country) {
		cancellationOfPaymentMethod(diseAccountNumber, "DD", country);
	}

	public static void cancellationOfCreditCard(String diseAccountNumber, String country) {
		cancellationOfPaymentMethod(diseAccountNumber, "CC", country);
	}

	public static boolean verifyDirectDebitRejectionInWildBill(String customerNumber, String rejectionReason) {
		String status = "";
		try {
			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
				// verify DD is active in Wildbill
				String queryWildbillDirectDebitStatus = queryToVerifyDDisActiveInWildBill(customerNumber);
				ResultSet getDirectDebitStatus = OracleJdbcConnection
						.wildbillDatabaseSelectQuery(queryWildbillDirectDebitStatus);
				while (getDirectDebitStatus.next()) {
					String paymentMethod = getDirectDebitStatus.getString("PAY_METHOD");
					status = getDirectDebitStatus.getString("STATUS");

					if (paymentMethod.equalsIgnoreCase("DIRECT_DEBIT") && rejectionReason.equalsIgnoreCase("NON-FATAL")
							&& status.equalsIgnoreCase("FAILED")) {
						System.out.println(String.format("The DDs status is wildbill is %s", status));
						return true;
					}
					if (paymentMethod.equalsIgnoreCase("DIRECT_DEBIT") && rejectionReason.equalsIgnoreCase("FATAL")
							&& status.equalsIgnoreCase("FATAL_FAILURE")) {
						System.out.println(String.format("The DDs status is wildbill is %s", status));
						return true;
					} else {
						System.err.println("The Status of DD in Wild bill is not as expected, the status is " + status);
						return false;
					}
				}
				Thread.sleep(Constant.DEFAULT_SLEEP_TIME);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("The Status of DD in Wild bill is not as expected, the status is " + status);
		return false;
	}

	public static void runWorkerPaymentRejection_Jackal() {

		try {
			MVNOProjectSpecific.openNewBrowserwithinTestsForWorkers();
			driver.get(Constant.JACKALWORKERSURL);

			// Trigger worker 'PaymentRejection'
			WebElement workerPaymentRejection = driver
					.findElement(By.xpath("//*[@id='yw995nxzN/GhTVXRl8JDl3O97w0=']/div[2]/a[1]"));
			scrollDownToView(workerPaymentRejection);
			workerPaymentRejection.click();

			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);

			System.out.println("The Payment rejection worker is triggered");
			driver.quit();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void runWorkerDuuningMediation_Jackal() {

		try {
			MVNOProjectSpecific.openNewBrowserwithinTestsForWorkers();
			driver.get(Constant.JACKALWORKERSURL);
			// Trigger worker 'PaymentRejection'
			WebElement workerDunning = driver
					.findElement(By.xpath("//*[@id='Ke+k6VUyfU3OkT3hlegW0UzV5vc=']/div[2]/a[1]"));
			scrollDownToView(workerDunning);
			workerDunning.click();

			sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);

			System.err.println("The Dunning mediation Jackal worker is triggered");
			driver.quit();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void runWorkerCCRetryOK() throws InterruptedException {

		MVNOProjectSpecific.openNewBrowserwithinTestsForWorkers();
		driver.navigate().to(Constant.JACKALWORKERSURL);

		// Trigger worker 'creditCardRetriedOkReqJob'
		WebElement workerCCRetryOK = driver
				.findElement(By.xpath("//*[@id='tyI6BwU7RkqDZtvpBjidBMlqYq0=']/div[2]/a[1]"));
		scrollDownToView(workerCCRetryOK);
		workerCCRetryOK.click();
		sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
		System.err.println("The CC retry OK Jackal Worker is triggered");
		driver.quit();
	}

	public static String queryToVerifyDDisActiveInWildBill(String custNumber) {
		return "SELECT * FROM BS_PAYMENTS where ID = (SELECT MAX(ID) FROM BS_PAYMENTS where PAY_METHOD = 'DIRECT_DEBIT' AND CUST_NBR='"
				+ custNumber + "')";
	}

	public static String queryToVerifyDDisActiveInCues(String custId) {
		return "SELECT * FROM CUST_DIRECT_DEBIT_DETAILS WHERE ID = (SELECT MAX(ID) FROM CUST_DIRECT_DEBIT_DETAILS where CUST_ID = '"
				+ custId + "')";
	}

	public static String queryToVerifyCCisInactiveInCues(String custId) {
		return "SELECT * FROM CUST_CARD_DETAILS WHERE ID = (SELECT MAX(ID) FROM CUST_CARD_DETAILS WHERE CUST_ID = '"
				+ custId + "')";
	}

	// get external account number(ACCOUNT_NBR) and BILL_SYS_REF
	public static String queryBSaccountsWithCustomerNumber(String customerNumber) {
		return "select * from BS_ACCOUNTS where CUST_NBR = '" + customerNumber + "'";

	}

}