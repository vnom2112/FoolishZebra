package parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import db.DBAccess;

public class ParseDHCP {
	

	public static void parseFile(String filePath) {
		File file = new File(filePath);
		BufferedReader reader = null;
		Connection conn = null;
		
		long startTime = System.currentTimeMillis();
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			String time = "";
			String ip = "";
			String host ="";
			String macAddress = "";
			Pattern pattern = Pattern.compile("Host=([^\\s]*) IP=([^\\s]*) MAC=([^\\s]*)");
			
			conn = DBAccess.getConnection();
			int batchSize = 200;
			int i = 0;
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO dhcp \n"
					+ "VALUES (?::TIMESTAMP WITH TIME ZONE,?,?,?)");
			
			System.out.println("Parsing DHCP Log:");
			
			while ((text = reader.readLine()) != null) {
				if(text.contains("DHCP_RenewLease") || text.contains("DHCP_GrantLease")) {
					i++;
					time = text.split("\\s")[0];
					Matcher m = pattern.matcher(text);
					if(m.find()) {
						host = m.group(1);
						ip = m.group(2);
						macAddress = m.group(3);
					}
					
					ps.setString(1, time);
					ps.setString(2, host);
					ps.setString(3, ip);
					ps.setString(4, macAddress);
					ps.addBatch();
					if(i % batchSize == 0) { 
						ps.executeBatch(); 
						System.out.print("\r"); 
						System.out.print(i + " rows inserted.");
					}
				}
			}
			ps.executeBatch();
			
			long totalTime = System.currentTimeMillis() - startTime;
			float seconds = totalTime / ((float)1000);
			System.out.println("\nFinished inserting " + i + " rows in " + seconds + " seconds.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
