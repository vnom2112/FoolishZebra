import parse.ParseDHCP;
import parse.ParseWebLog;

public class FoolishZebra {

	public static void main(String[] args) {
		
		ParseDHCP.parseFile("../testDHCP.txt");
		ParseWebLog.parseWebLog("../testWebLog.txt");
	}

}
