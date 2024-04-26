package app;
import chat.Client;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import database.DatabaseUtil;

public class ServerUtil {
	private static Player player;
	public static Object parseClient(String clientMsg) throws ClassNotFoundException, UnknownHostException, IOException {
		String [] clientMsgArr = clientMsg.split(" ");
		
		switch(clientMsgArr[0].toLowerCase()) {
			case "pseudo": return parsePseudo(clientMsgArr);
			case "join"  : return parseJoin(clientMsgArr[1]);
			case "ready" : return parseReady();
			case "guess" : return parseGuess(clientMsgArr[1]);
			case "chat"  : parseChat();break;
			default: return "Command not recognized"; 
		}
		return "";
	}
	
	//processes pseudo command
	private static Player parsePseudo(String [] clientMsgArr) throws ClassNotFoundException {
		player = DatabaseUtil.searchTicket(clientMsgArr[1]);
		
		return player;//.getTicket()+"\nWelcome"+player.getNickname()
	}
	
	//processes join command
	private static Game parseJoin(String gameId) {
		
		for(Game game:Server.getListOfGames()) { //iterating games
			
			if(game.getGameId().equals(gameId)) { //matching the gameId
				
				if(game.listOfCurrentPlayers.size()<7) { //checking for max player constraint
					game.listOfCurrentPlayers.add(player); //adding player in game if players in game less than 7

				}

				return game; // returning the game object
			}

		}

		return null; // failed to find game in list of games returns null

	}
	
	//processes ready command
	private static String parseReady() {
		player.setReady(true);
		return player.getTicket()+" is ready";
	}
	
	
	private static int parseGuess(String guess) {
		player.setGuess(Integer.parseInt(guess));

		return Integer.parseInt(guess);

	}

	private static void parseChat() throws UnknownHostException, IOException {
		Scanner scanner = new Scanner(System.in);
		String username = player.getNickname();
		Socket socket = new Socket("localhost", 1234);
		Client client = new Client(socket, username);
		client.listenForMe();
		client.sendMessage();
	}
	
	public static String getLeaderBoard() throws ClassNotFoundException {
		ArrayList<Player> leaderboardArr = DatabaseUtil.getTopFivePlayers();
		String leaderboardString = leaderboardArr.stream()
				.map(p -> p.toString() + "\n")
					.reduce("", (acc, curr) -> acc + curr);
		return leaderboardString;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		player = player;
	}

}