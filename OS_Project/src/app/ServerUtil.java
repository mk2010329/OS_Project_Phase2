	package app;

import database.DatabaseUtil;

public class ServerUtil {
	private Player player;
	public Object parseClient(String clientMsg) {
		String [] clientMsgArr = clientMsg.split(" ");
		
		switch(clientMsgArr[0].toLowerCase()) {
			case "pseudo": return parsePseudo(clientMsgArr);
			case "join"  : return parseJoin(clientMsgArr[-1]);
			case "ready" : return parseReady();
			case "guess" : return parseGuess(clientMsgArr[-1]);
			case "chat"  : parseChat();break;
			default: return "Command not recognized"; 
		}
		return "";
	}
	
	//processes pseudo command
	private Player parsePseudo(String [] clientMsgArr) throws ClassNotFoundException {
		this.player = DatabaseUtil.searchTicket(clientMsgArr[1]);
		
		return this.player;//.getTicket()+"\nWelcome"+this.player.getNickname()
	}
	
	//processes join command
	private Game parseJoin(String gameId) {
		
		for(Game game:Server.getListOfGames()) { //iterating games
			
			if(game.getGameId().equals(gameId)) { //matching the gameId
				
				if(game.listOfCurrentPlayers.size()<7) { //checking for max player constraint
					game.listOfCurrentPlayers.add(this.player); //adding player in game if players in game less than 7
				}
				
				return game; //returning the game object
			}
			
		}
		
		return null; //failed to find game in list of games returns null
		
	}
	
	//processes ready command
	private String parseReady() {
		this.player.setReady(true);
		return this.player.getTicket()+" is ready";
	}
	
	
	private int parseGuess(String guess) {
		this.player.setGuess(Integer.parseInt(guess));
		return Integer.parseInt(guess);
		
	}
	
	private void parseChat() {
		
	}
	
	
	
	
	public String getLeaderBoard() throws ClassNotFoundException {
		
		String leaderboard = DatabaseUtil.getTopFivePlayers().toString();
		return leaderboard;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}