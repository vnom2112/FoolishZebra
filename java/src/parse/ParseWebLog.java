package parse;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DBAccess;

public class ParseWebLog {
  
	public static void parseWebLog(String fileName) {
	    File file = new File(fileName);
	    BufferedReader reader = null;
	    Connection conn = null;
	    long startTime = System.currentTimeMillis();
	    
	    try {
	      reader = new BufferedReader(new FileReader(file));
	      conn = DBAccess.getConnection();
	      conn.setAutoCommit(false);
	      PreparedStatement ps = conn.prepareStatement(
    				"INSERT INTO weblog (requesttime, host, clientip, mac, wmac, studentid, timeelapsed, requestresolution, responsecode, \n" +
					"responsesize, requesttype, requesturl) \n" +
					"SELECT to_timestamp(?::DOUBLE PRECISION), COALESCE(de.host, ''), COALESCE(de.ip, ''), COALESCE(studentid.mac, ''), COALESCE(studentid.wmac, ''), " +
					"COALESCE(studentid.id, ''), ?::INTEGER, ?, ?, ?, ?, ? \n" +
					"FROM \n" +
						"(SELECT host, ip, mac FROM dhcp \n" +
						"WHERE leasetime < to_timestamp(?::DOUBLE PRECISION) \n" +
						"AND ip = ? \n" +
						"ORDER BY leasetime DESC \n" +
						"LIMIT 1) de, studentid \n" +
					"WHERE studentid.mac = de.mac \n" +
					"OR studentid.wmac = de.mac \n"
	    		  );
	      String line = "";
	      int batchSize = 50;
		  int i = 0;
	
	      while ((line = reader.readLine()) != null) {
	        if (line.charAt(0) == '#') {
	          continue;
	        } else {
	          i++;
	          String[] parts = line.split(" ");
			  String time = parts[0];
			  String clientIp = parts[2];
			  String timeElapsed = parts[1];
		 	  String requestResolution = parts[3].split("/")[0];
			  String responseCode = parts[3].split("/")[1];
			  String responseSize = parts[4];
			  String requestType = parts[5];
			  String url = parts[6];
			  //System.out.println("Time=" + time + ", IP Address=" + clientIp + ", Request Res=" + requestResolution + ", Response Code=" + responseCode + ", Request Type=" + requestType + ", URL=" + url);
			  ps.setString(1, time);
			  ps.setString(2, timeElapsed);
			  ps.setString(3, requestResolution);
			  ps.setString(4, responseCode);
			  ps.setString(5, responseSize);
			  ps.setString(6, requestType);
			  ps.setString(7, url);
			  ps.setString(8, time);
			  ps.setString(9, clientIp);
			  ps.addBatch();
			  
			  if(i % batchSize == 0) { 
				ps.executeBatch(); 
				System.out.print("\r"); 
				System.out.print(i + " weblogs inserted.");
			  }
	        }
	      }
	      ps.executeBatch();
	      conn.commit();
	      long totalTime = System.currentTimeMillis() - startTime;
		  float seconds = totalTime / ((float)1000);
		  System.out.println("\nWeb Log: Finished inserting " + i + " rows in " + seconds + " seconds.\n");
	    } catch (IOException e) {
	      e.printStackTrace();
	    } catch (SQLException e) {
			e.printStackTrace();
			e.getNextException();
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
