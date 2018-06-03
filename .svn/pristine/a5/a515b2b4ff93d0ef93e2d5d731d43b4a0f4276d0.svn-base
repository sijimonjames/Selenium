package com.acneuro.test.automation.portingTests;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.acneuro.test.automation.db_connection_libraries.OracleDBUtils;
import com.acneuro.test.automation.db_connection_libraries.OracleJdbcConnection;
import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;
import com.acneuro.test.automation.mvno_project_library.UKProjectSpecific;

public class TC_03_UKCustomerActivationWithPortIn {

	private static String customerNumber = "Latest Active";
	private static String joiNumber = "Latest Active";
	private static String portingNumber = "unique";
	private static String customerEmailId = "";
	private static String customerId = "";
	private static String portinOrderNumber = "";

	@Test
	public static void ukPortInWithPacCode() {

		try {
			// Login
			LoginToUKMyJoiWithActiveSubscription();
			// Change the Email ID
			MVNOProjectSpecific.updateEmailIdInCust_customersTable(Constant.PERSONAL_EMAIL_ADRRESS, customerNumber);
			System.err.println("Email Changed to " + Constant.PERSONAL_EMAIL_ADRRESS);

			UKProjectSpecific.ukManageSettingsFromSubscriptions(joiNumber);
			portingNumber = UKProjectSpecific.UniquePortInNumberUK();
			UKProjectSpecific.ukPortInFromManageSettings(joiNumber, portingNumber);

			portInOrdernumber();

			MVNOProjectSpecific.checkStatusOfOrderIsCompleted(portinOrderNumber);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String queryLatestActiveCinProductnumber_GB() {

		return "SELECT * FROM CIN_PRODUCTS WHERE CIN_NUMBER = " + "(SELECT MAX(CIN_NUMBER) FROM CIN_PRODUCTS "
				+ "WHERE PRODUCT_TYPE = 'MBSUBSCRIPTION' "
				+ "AND STATUS = 'ACT' AND  COUNTRY = 'GB' AND ATTRIBUTE1 LIKE '070%')";
	}

	private static void LoginToUKMyJoiWithActiveSubscription() {

		try {
			ResultSet queryCinProductTable = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryLatestActiveCinProductnumber_GB());

			while (queryCinProductTable.next()) {
				customerId = queryCinProductTable.getString("CUSTOMER_ID");
				System.out.println("Customer ID: " + customerId);
				joiNumber = queryCinProductTable.getString("ATTRIBUTE1");
				System.out.println("MyJoi Number: " + joiNumber);
				customerNumber = queryCinProductTable.getString("CUSTOMER_NUMBER");
				System.out.println("Customer Number: " + customerNumber);
			}

			customerEmailId = MVNOProjectSpecific.emailIdUsingCustomerNumber(customerNumber);

			System.out.println("User Name: " + customerEmailId);
			// Login with current browser
			UKProjectSpecific.LoginToUKMyJoiWithLatestEmailID(customerEmailId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void portInOrdernumber() throws SQLException {

		ResultSet queryLatestOrderNumber = OracleJdbcConnection
				.crmDatabaseSelectQuery(OracleDBUtils.queryLatestOrderNumberForUKPortIN(customerId));
		while (queryLatestOrderNumber.next()) {
			portinOrderNumber = queryLatestOrderNumber.getString("MAX(ORD_NUMBER)");
			System.out.println("Portin OrderNumber: " + portinOrderNumber);
		}
	}

	@AfterMethod
	private void postMethodToChangeEmail() {
		// change the email id back to normal
		MVNOProjectSpecific.updateEmailIdInCust_customersTable(customerEmailId, customerNumber);
		System.err.println(String.format("Email Adress is changed to original: %s", customerEmailId));
	}
}