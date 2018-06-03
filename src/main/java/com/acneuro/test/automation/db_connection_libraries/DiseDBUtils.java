package com.acneuro.test.automation.db_connection_libraries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.acneuro.test.automation.libraries.Constant;

/**
 * @author sijimon james
 * @category Test Library
 *
 */

public class DiseDBUtils {

	// CUE DB CONNECTION DETAILS
	private static final String DB2_USERNAME = "sijames";
	private static final String DB2_PASSWORD = Constant.My_Password;
	private static final String DB2_CONN_STRING_FR = "jdbc:as400://EUDBL01;libraries=DISEFRMPD1;";
	private static final String DB2_CONN_STRING_UK = "jdbc:as400://EUDBL01;libraries=DISEGBMPD1;";

	public static Connection getConnectionToDiseMP(DiseDBConnections diseDatabase) {
		// Load the driver
		try {

			Class.forName("com.ibm.as400.access.AS400JDBCDriver");
		} catch (ClassNotFoundException e) {
			System.err.println("No jdbc driver found...");
			e.printStackTrace();
		}

		switch (diseDatabase) {
		case DB2Fr:
			try {
				return DriverManager.getConnection(DB2_CONN_STRING_FR, DB2_USERNAME, DB2_PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		case DB2UK:
			try {
				return DriverManager.getConnection(DB2_CONN_STRING_UK, DB2_USERNAME, DB2_PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		default:
			return null;
		}

	}

	public static String queryOrderStatus() {
		return "SELECT * FROM DISEFRMPD1.COGDREP WHERE GDAN8X = '99403619'";
	}

	public static String queryDiseAccountNumberFromCRMCustomerNumber(String customerNumber) {
		return "select a.JBPVTY as DISEMP_ACCOUNT_NUMBER from C2JBREP a, C2JCREP b where a.JBELNR = b.JCELNR and b.JCEKNR = '001' and b.JCPXTY = '"
				+ customerNumber + "'";
	}

}