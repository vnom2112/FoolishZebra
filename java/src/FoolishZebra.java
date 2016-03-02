import parse.ParseCSV;
import parse.ParseDHCP;
import parse.ParseWebLog;

public class FoolishZebra {

	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
		
		ParseCSV.parseCSV("../device-reg.csv");
		ParseDHCP.parseFile("../testDHCP.txt");
		ParseWebLog.parseWebLog("../testWebLog.txt");
		
		long totalTime = System.currentTimeMillis() - startTime;
		float seconds = totalTime / ((float)1000);
		System.out.println("\nTotal: Finished all operations in " + seconds + " seconds.");
	}

}
