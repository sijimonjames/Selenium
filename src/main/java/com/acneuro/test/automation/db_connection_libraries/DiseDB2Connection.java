package com.acneuro.test.automation.db_connection_libraries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author sijimon james
 *
 */

public class DiseDB2Connection {

	public static Connection con = null;
	public static ResultSet rs = null;
	public static Statement stmt = null;

	public static ResultSet diseDatabaseSelectQueryForFrance(String Select_query) {

		try {
			con =  DiseDBUtils.getConnectionToDiseMP(DiseDBConnections.DB2Fr);
			stmt = con.createStatement();
			rs = stmt.executeQuery(Select_query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static ResultSet diseDatabaseSelectQueryForUK(String Select_query) {

		try {
			con =  DiseDBUtils.getConnectionToDiseMP(DiseDBConnections.DB2UK);
			stmt = con.createStatement();
			rs = stmt.executeQuery(Select_query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

}