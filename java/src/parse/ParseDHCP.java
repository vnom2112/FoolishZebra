package parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseDHCP {
	

	public static void parseFile(String filePath) {
		File file = new File(filePath);
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			String time = "";
			String ip = "";
			String host ="";
			String macAddress = "";
			Pattern pattern = Pattern.compile("Host=([^\\s]*) IP=([^\\s]*) MAC=([^\\s]*)");
			
			while ((text = reader.readLine()) != null) {
				if(text.contains("DHCP_RenewLease") || text.contains("DHCP_GrantLease")) {
					//System.out.println("parsing line: " + text);
					time = text.split("\\s")[0];
					Matcher m = pattern.matcher(text);
					if(m.find()) {
						//System.out.println("Found it!");
						host = m.group(1);
						ip = m.group(2);
						macAddress = m.group(3);
					}
					
					System.out.println("Time: " + time + " Host: " + host + " IP Address: " + ip + " Mac Address: " + macAddress);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
			}
		}
	}
}
