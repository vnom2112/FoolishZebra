package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBAccess {
	
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
