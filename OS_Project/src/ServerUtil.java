public class ServerUtil {
	static int uniqueId = 0;

//	private static getUniqueId() {
//		
//	}
	
	public String parse(String clientMsg) {
		String [] clientMsgArr = clientMsg.split(" ");
		String command = clientMsgArr[0];
		
		switch(command) {
			case "pseudo": ;break;
			case "join"  : ;break;
			case "ready" : ;break;
			case "guess" : ;break;
			default: ;
		}
		return "Khusra";
	}
	private void parsePseudo() {
		
	}
	private void parseJoin() {
		
	}
	private void parseReady() {
	
	}
	private void parseGuess() {
	
	}
	public String getLeaderBoard() {
		return null;
		
	}
	
}