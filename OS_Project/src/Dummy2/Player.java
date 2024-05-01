package Dummy2;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import chat.Client;
import database.DatabaseUtil;

public class Player implements Runnable{
	
	 private Socket socket;
	    private BufferedReader in;
	    private PrintWriter out;
	    private int ticket;

	    public Player(Socket socket) {
	        this.socket = socket;
	        try {
	            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            out = new PrintWriter(socket.getOutputStream(), true);
//	            ticket = Server.generateTicket();
	            out.println("Identify Yourself: ");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    @Override
	    public void run() {
	        try {
	            String inputLine;
	            while ((inputLine = in.readLine()) != null) {
	                // Handle client messages
	            	String [] clientMsgArr = inputLine.split(" ");
	        		
	        		switch(clientMsgArr[0].toLowerCase()) {
	        			case "pseudo": return parsePseudo(clientMsgArr);
//	        			case "join"  : return parseJoin(clientMsgArr[1]);
//	        			case "ready" : return parseReady();
//	        			case "guess" : return parseGuess(clientMsgArr[1]);
//	        			case "chat"  : parseChat();break;
	        			default: System.out.println("Command not recognized"); 
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            Server.removePlayer(this);
	            try {
	                socket.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    private void joinGame(int gameId) {
	        List<Game> games = Server.getGames();
	        for (Game game : games) {
	            if (game.getId() == gameId) {
	                game.addPlayer(this);
	                return;
	            }
	        }
	        out.println("Game not found!");
	    }

//	    public static Object parseClient(String clientMsg) throws ClassNotFoundException, UnknownHostException, IOException {
//			String [] clientMsgArr = clientMsg.split(" ");
//			
//			switch(clientMsgArr[0].toLowerCase()) {
//				case "pseudo": return parsePseudo(clientMsgArr);
//			//	case "join"  : return parseJoin(clientMsgArr[1]);
//				case "ready" : return parseReady();
//				case "guess" : return parseGuess(clientMsgArr[1]);
//				case "chat"  : parseChat();break;
//				default: return "Command not recognized"; 
//			}
//			return "";
//		}
//		
//		//processes pseudo command
		private static Player parsePseudo(String [] clientMsgArr) throws ClassNotFoundException {
			Dummy2.Player player = DatabaseUtil.searchTicket(clientMsgArr[1]);
			return player;//.getTicket()+"\nWelcome"+player.getNickname()
		}
//		
//		//processes join command
//		private void parseJoin(int gameId) {
//			 List<Dummy2.Game> games = Dummy2.Server.getGames();
//			for(Dummy2.Game game: games) { //iterating games
//				
//				if(game.getId()==(gameId)) { //matching the gameId
//					game.addPlayer(this);
//					return ; 
//					}
//				
//				}
//			out.println("Game not found!"); // failed to find game in list of games returns null
//
//		}
//		
//		//processes ready command
//		private static String parseReady() {
//			this.setReady(true);
//			return player.getTicket()+" is ready";
//		}
//		
//		
//		private static int parseGuess(String guess) {
//			player.setGuess(Integer.parseInt(guess));
//
//			return Integer.parseInt(guess);
//
//		}
//
//		private static void parseChat() throws UnknownHostException, IOException {
//
//		}
//		
//		public static String getLeaderBoard() throws ClassNotFoundException {
//			ArrayList<Dummy2.Player> leaderboardArr = DatabaseUtil.getTopFivePlayers();
//			String leaderboardString = leaderboardArr.stream()
//					.map(p -> p.printPlayer() + "\n")
//						.reduce("", (acc, curr) -> acc + curr);
//			return leaderboardString;
//		}

}
