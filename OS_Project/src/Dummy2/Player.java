package Dummy2;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import app.ServerUtil;
import chat.Client;
import database.DatabaseUtil;

public class Player implements Runnable{
	
	 private Socket socket;
	    private BufferedReader in;
	    private PrintWriter out;
	    private int ticket;
	    static Player player;
	    
	    private String nickname;
		private int numberOfWins;
		private int gamePoints;
		private int guess;
		private boolean ready;
		private String roundStatus;

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
	     
	    public int getTicket() {
			return ticket;
		}
	    
	    public String getTicketString() {
			return getTicket()+getNickname();
		}
		public void setTicket(int ticket) {
			this.ticket = ticket;
		}


		public String getNickname() {
			return nickname;
		}


		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public int getNumberOfWins() {
			return numberOfWins;
		}

		public void setNumberOfWins(int numberOfWins) {
			this.numberOfWins = numberOfWins;
		}

		public int getGamePoints() {
			return gamePoints;
		}

		public void setGamePoints(int gamePoints) {
			this.gamePoints = gamePoints;
		}

		public int getGuess() {
			return guess;
		}

		public void setGuess(int guess) {
			this.guess = guess;
		}

		public boolean isReady() {
			return ready;
		}

		public void setReady(boolean ready) {
			this.ready = ready;
		}

		public String getRoundStatus() {
			return roundStatus;
		}

		public void setRoundStatus(String roundStatus) {
			this.roundStatus = roundStatus;
		}
		
		@Override
		public String toString() {
			return "Player [ticket=" + ticket + ", nickname=" + nickname + "]";
		}

		@Override
	    public void run() {
	        try {
	            String inputLine;
	            while ((inputLine = in.readLine()) != null) {
	                // Handle client messages
	            	String [] clientMsgArr = inputLine.split(" ");
	        		
	        		switch(clientMsgArr[0].toLowerCase()) {
	        			case "pseudo": parsePseudo(clientMsgArr);break;
	        			case "join"  : parseJoin(clientMsgArr[1]);break;
//	        			case "ready" : return parseReady();
//	        			case "guess" : return parseGuess(clientMsgArr[1]);
//	        			case "chat"  : parseChat();break;
	        			default: System.out.println("Command not recognized"); 
	                }
	            }
	        } catch (IOException | ClassNotFoundException e) {
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
	
//		//processes pseudo command
		private  void parsePseudo(String [] clientMsgArr) throws ClassNotFoundException {
			player = DatabaseUtil.searchTicket(clientMsgArr[1]);
			out.println("Welcome "+player.getNickname()+"\nYour ticket is "+player.getTicketString()); 
			out.println("Leaderboard:");
			out.println(ServerUtil.getLeaderBoard());
			out.println("Games available:");
			out.println(Server.getGames());
			out.println("All Players:");
			out.println(Server.players.toString());
			out.println("Join any game: ");
		}
		
//		//processes join command
		private void parseJoin(String gameId) {
			int gameid =Integer.parseInt(gameId);
			 List<Dummy2.Game> games = Dummy2.Server.getGames();
			for(Dummy2.Game game: games) { //iterating games
				
				if(game.getId()==(gameid)) { //matching the gameId
					game.addPlayer(this);
					return ; 
					}
				
				}
			out.println("Game not found!"); // failed to find game in list of games returns null

		}
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
