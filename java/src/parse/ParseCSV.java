package parse;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DBAccess;

public class ParseCSV {
  
	public static void parseCSV(String fileName) {
	    File file = new File(fileName);
	    BufferedReader reader = null;
	    Connection conn = null;
	    long startTime = System.currentTimeMillis();
	    
	    try {
	      reader = new BufferedReader(new FileReader(file));
	      conn = DBAccess.getConnection();
	      PreparedStatement ps = conn.prepareStatement(
    				"INSERT INTO studentid VALUES(?, ?, ?)"
	    		  );
	      String line = "";
	      int batchSize = 200;
		  int i = 0;
	
	      while ((line = reader.readLine()) != null) {
	        if (i ==0) {
	          i++;
	          continue;
	        } else {
	          i++;
	          String[] parts = line.split(",");
			  String studentId = parts[0];
			  String macAddress = parts[1].replace(":", "").replace("\"", "").toLowerCase().trim();
			  String wirelessMacAddress = parts[2].replace(":", "").replace("\"", "").toLowerCase().trim();
		 	  
			  //System.out.println("Time=" + time + ", IP Address=" + clientIp + ", Request Res=" + requestResolution + ", Response Code=" + responseCode + ", Request Type=" + requestType + ", URL=" + url);
			  ps.setString(1, studentId);
			  ps.setString(2, macAddress);
			  ps.setString(3, wirelessMacAddress);
			  ps.addBatch();
			  
			  if(i % batchSize == 0) { 
				ps.executeBatch(); 
				System.out.print("\r"); 
				System.out.print(i + " student ids inserted.");
			  }
	        }
	      }
	      ps.executeBatch();
	      
	      long totalTime = System.currentTimeMillis() - startTime;
		  float seconds = totalTime / ((float)1000);
		  System.out.println("\nStudent IDs: Finished inserting " + i + " rows in " + seconds + " seconds.\n");
	    } catch (IOException e) {
	      e.printStackTrace();
	    } catch (SQLException e) {
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
