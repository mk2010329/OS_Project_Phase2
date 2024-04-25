import database.DatabaseUtil;

public class ServerUtil {
	
	public String parseClient(String clientMsg) {
		String [] clientMsgArr = clientMsg.split(" ");
		String command = clientMsgArr[0];
		
		switch(command) {
			case "pseudo": return parsePseudo(clientMsgArr);
			case "join"  : return parseJoin();break;
			case "ready" : ;break;
			case "guess" : ;break;
			case "chat"  : parseChat();break;
			default: ; 
		}
		return "Khusra";
	}
	
	//parseClient methods
	private String parsePseudo(String [] clientMsgArr) {
		Player plr = DatabaseUtil.searchTicket(clientMsgArr[1]);
		
		return plr.getTicket()+"\nWelcome"+plr.getNickname();
	}
	private String parseJoin() {
		
	}
	private void parseReady() {
	
	}
	private void parseGuess() {
	
	}
	
	private void parseChat() {
		
	}
	
	
	
	
	public String getLeaderBoard() {
		return null;
		
	}
	
}