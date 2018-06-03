package com.acneuro.test.automation.blockUnblockInternet;

import static com.acneuro.test.automation.db_connection_libraries.OracleDBUtils.*;
import java.sql.ResultSet;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.acneuro.test.automation.db_connection_libraries.OracleJdbcConnection;
import com.acneuro.test.automation.libraries.Constant;
import com.acneuro.test.automation.libraries.RestApiAutomation;
import com.acneuro.test.automation.mvno_project_library.FranceProjectSpecific;
import com.acneuro.test.automation.mvno_project_library.MVNOProjectSpecific;
import com.acneuro.test.automation.mvno_project_library.UKProjectSpecific;

/*
 * @Author: Sijimon James
 * Test: Block and unblock Internet using MyJOi with 
 */

public class TC_02_FR_BlockOrUnblockInternet {

	private static String myJoiNumber = "find during run time";
	private static String country = "FR";
	private static String processId = "unknown";
	private static String cinProductId = "";

	@Test(priority = 1)
	public static void test_1_Block_Or_Unblock_Internet() {
		processInternetBlockOrUnblock();
		Assert.assertTrue(processsDataTariffAction());
	}

	@Test(priority = 2)
	public static void test_2_Block_Or_Unblock_Internet() {

		processInternetBlockOrUnblock();
		Assert.assertTrue(processsDataTariffAction());
	}

	public static void processInternetBlockOrUnblock() {

		String customerNumber = "";
		String statusOfInternet = "";

		try {
			// FINDING CIN PRODUCT TABLE DETAILS
			ResultSet getCinProductTable = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryFRCinProductTableWithMaxCinProductnumber());
			while (getCinProductTable.next()) {
				cinProductId = getCinProductTable.getString("ID");
				customerNumber = getCinProductTable.getString("CUSTOMER_NUMBER");
				myJoiNumber = getCinProductTable.getString("ATTRIBUTE1");
				System.out.println("cinProductId: " + cinProductId);
				System.out.println("customer Number: " + customerNumber);
				System.out.println("MSISDN: " + myJoiNumber);

			}

			// SEARCHING STATUS OF INTERNET IN CIN PRODUCT MBSUB
			ResultSet getStatusOfInternat = OracleJdbcConnection
					.crmDatabaseSelectQuery(queryCinProductMBSUBtableDetails(cinProductId));
			while (getStatusOfInternat.next()) {
				statusOfInternet = getStatusOfInternat.getString("BLOCKING_INTERNET");
			}

			if (statusOfInternet.equalsIgnoreCase("AED")) {

				System.out.println("The status of Internet is blocked, unblocking now using MyJOi....");
				loginToManageSettings();
				MVNOProjectSpecific.selectUnBlockInternetFromMyJoiSettings();
				// UNBLOCK INTERNET USING MYJOI
			} else if ((statusOfInternet.equalsIgnoreCase("DED"))) {

				System.out.println("The status of Internet is not blocked, blocking now using MyJoi....");
				loginToManageSettings();
				MVNOProjectSpecific.selectBlockInternetFromMyJoiSettings();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loginToManageSettings() {

		// LOGIN TO MYJOI
		if (country.equalsIgnoreCase("UK")) {

			UKProjectSpecific.LoginToUKMyJoiWithMsisdn(myJoiNumber);
			UKProjectSpecific.ukManageSettingsFromSubscriptions(myJoiNumber);

		} else if (country.equalsIgnoreCase("FR")) {
			FranceProjectSpecific.LoginToFrMyJoiWithMsisdn(myJoiNumber);
			FranceProjectSpecific.franceManageSettingsFromSubscriptions(myJoiNumber);
		}
	}

	public static boolean processsDataTariffAction() {

		String actionName = "";
		String status = "";
		String result = "";

		try {
			System.out.println(cinProductId);
			// GET PROCESS ID
			Assert.assertTrue(cueRequestedCommandsTableForOpenCommands());

			// REMOVE THIS ONCE EVERYTHING WORKS
			System.out.println(processId);

			// check for ERROR_PROVISIONING_RES
			boolean mbModifyDataTrafficAction = MVNOProjectSpecific.checkIfActionExistsWithProcessId(processId,
					"mbModifyDataTrafficAction");
			Assert.assertTrue(mbModifyDataTrafficAction);
			ResultSet getActionInstances = OracleJdbcConnection.crmDatabaseSelectQuery(
					queryActionInstanceWithProcessIdAndActionName(processId, "mbModifyDataTrafficAction"));
			while (getActionInstances.next())

			{
				actionName = getActionInstances.getString("NAME");
				status = getActionInstances.getString("STATUS");
				result = getActionInstances.getString("RESULT");
				if ("mbModifyDataTrafficAction".equals(actionName) && "FIN".equals(status) && "ERROR".equals(result)) {
					Assert.assertTrue(MVNOProjectSpecific.checkIfActionExistsWithProcessId(processId,
							"MBDataFailureUserTaskAction"));
				}
			}
			for (int i = 0; i < Constant.DEFAULT_RETRY_COUNT; i++) {
				ResultSet getActionInstancesforUserTask = OracleJdbcConnection.crmDatabaseSelectQuery(
						queryActionInstanceWithProcessIdAndActionName(processId, "MBDataFailureUserTaskAction"));

				while (getActionInstancesforUserTask.next()) {

					actionName = getActionInstancesforUserTask.getString("NAME");
					status = getActionInstancesforUserTask.getString("STATUS");
					result = getActionInstancesforUserTask.getString("RESULT");

					if ("MBDataFailureUserTaskAction".equals(actionName) && "WAI".equals(status)) {
						System.out.println("MBDataFailureUserTaskAction is in WAI status");

						// Create and send api request
						String createErrorResAPI = RestApiAutomation.requestForERROR_PROVISIONING_RES(myJoiNumber,
								processId, cinProductId);
						System.out.println(createErrorResAPI);
						RestApiAutomation.httpPostTest(Constant.CIN_EXECUTE, createErrorResAPI);
						Thread.sleep(5000);
					}
					if ("MBDataFailureUserTaskAction".equals(actionName) && "FIN".equals(status)
							&& "SKIP_PROVISIONING".equals(result)) {
						System.out.println("MBDataFailureUserTaskAction action is finished");
						return true;
					}
					if ("MBDataFailureUserTaskAction".equals(actionName) && "FIN".equals(status)
							&& "ERROR".equals(result)) {
						System.err.println("MBDataFailureUserTaskAction action finished in Error");
						return false;
					}
					if ("MBDataFailureUserTaskAction".equals(actionName) && "ERR".equals(status)) {
						System.err.println("MBDataFailureUserTaskAction action finished in Error");
						return false;
					}
					Thread.sleep(Constant.DEFAULT_SHORT_SLEEP_TIME);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	public static boolean cueRequestedCommandsTableForOpenCommands() {

		// GET PROCESS ID
		try {
			for (int i = 0; i < 5; i++) {
				System.err.println("CHECKING FOR THE PROCESS ID");

				String queryCues_Commands = querySelectProcessIdFromCUE_COMMANDAndCIN_PRODUCT("ERROR_PROVISIONING_RES",
						myJoiNumber, "OPEN");
				ResultSet getCueCommanddetails = OracleJdbcConnection.crmDatabaseSelectQuery(queryCues_Commands);
				while (getCueCommanddetails.next()) {

					processId = getCueCommanddetails.getString("PROCESS_ID");

					if (!getCueCommanddetails.wasNull()) {
						System.out.println("PROCESS ID: " + processId);

						return true;
					}
				}
				Thread.sleep(3000);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@AfterMethod
	private static void quit() {
		MVNOProjectSpecific.quitBrowser();
	}

}
