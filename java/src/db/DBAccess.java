package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBAccess {
/**
	CREATE TABLE dhcp
	(
	  leasetime timestamp with time zone,
	  host text,
	  ip character(20),
	  mac character(12)
	)
	
	CREATE TABLE weblog
	(
	  requesttime timestamp with time zone,
	  host text,
	  clientip character(20),
	  mac character(12),
	  wmac character(12),
	  studentid text,
	  timeelapsed integer,
	  requestresolution text,
	  responsecode text,
	  responsesize text,
	  requesttype text,
	  requesturl text
	)
	
	CREATE TABLE studentid (
		id TEXT,
		mac CHARACTER(12),
		wmac CHARACTER(12)
	)
	
 **/
	
	
	private static final String DB_USERNAME = "username";
	private static final String DB_PASSWORD = "password";
	
	public static Connection getConnection() {
		
		Connection conn = null;
		
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
		} catch (SQLException e) {
			System.out.println("Could not find JDBC Driver.");
			e.printStackTrace();
			return null;
		}
		
		System.out.println("Postgres Driver Registered!");
		
		
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/foolishzebra", DB_USERNAME, DB_PASSWORD);
		} catch (SQLException e) {
			System.out.println("Connection to postgres database failed!");
			e.printStackTrace();
			return null;
		}
		
		return conn;
	}
	
	public static void testConnection() {
		getConnection();
	}
}
