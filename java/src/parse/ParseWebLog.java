package parse;

import java.io.*;

public class ParseWebLog {
  
	public static void parseWebLog(String fileName) {
	    File file = new File(fileName);
	    try {
	      BufferedReader reader = new BufferedReader(new FileReader(file));
	      String line = "";
	
	      while ((line = reader.readLine()) != null) {
	        if (line.charAt(0) == '#') {
	          continue;
	        } else {
	          String[] parts = line.split(" ");
			  String time = parts[0];
			  String timeElapsed = parts[1];
			  String clientIp = parts[2];
		 	  String requestResolution = parts[3].split("/")[0];
			  String responseCode = parts[3].split("/")[1];
			  String responseSize = parts[4];
			  String requestType = parts[5];
			  String url = parts[6];
			  System.out.println("Time=" + time + ", IP Address=" + clientIp + ", Request Res=" + requestResolution + ", Response Code=" + responseCode + ", Request Type=" + requestType + ", URL=" + url);
	        }
	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
}
