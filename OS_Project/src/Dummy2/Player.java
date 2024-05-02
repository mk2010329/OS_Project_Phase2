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
		Game tempGameHolder;
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
	    public Socket getSocket() {
	 			return socket;
	 		}

 		public void setSocket(Socket socket) {
 			this.socket = socket;
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
	        			case "ready" : parseReady();break;
	        			case "guess" : parseGuess(clientMsgArr[1]);break;
//	        			case "chat"  : parseChat();break;
	        			default: out.println("Command not recognized"); 
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
		private void parsePseudo(String [] clientMsgArr) throws ClassNotFoundException {
			player = DatabaseUtil.searchTicket(clientMsgArr[1]);
			out.println("Welcome "+player.getNickname()+"\nYour ticket is "+player.getTicketString()); 
			out.println("Leaderboard:");
			out.println(ServerUtil.getLeaderBoard());
			out.println("Games available:");
			out.println(Server.getGames().toString());
			out.println("All Players:");
			out.println(Server.players.toString());
			out.println("Join any game: ");
		}
		
//		//processes join command
		private void parseJoin(String gameId) throws IOException {
			int gameid =Integer.parseInt(gameId);
			 List<Dummy2.Game> games = Dummy2.Server.getGames();
			for(Dummy2.Game game: games) { //iterating games
				
				if(game.getId()==(gameid)) { //matching the gameId
					//System.out.println(game.hashCode());
					tempGameHolder = game;
					game.addPlayer(this);
					out.println("This message is sent by game: " + 
			    			game.getListofCurrentPlayers().stream().map(p-> p.getNickname()+" ")
			    			.reduce("", (acc, curr)-> acc + curr));
					/*this.getNickname() + " has joined the game "+ gameid*/
					return; 

					}
				
				}
			out.println("Game not found!"); // failed to find game in list of games returns null

		}
		
//		//processes ready command
		private void parseReady() throws IOException {
			this.ready=true;
			int readyCounter=0;
			for(Player player:tempGameHolder.getListofCurrentPlayers()) {
				if(player.ready) {
					readyCounter++;
				}
			}
			//
			if(readyCounter==tempGameHolder.getListofCurrentPlayers().size()&&tempGameHolder.getListofCurrentPlayers().size()>=1) {
				tempGameHolder.start();
			}

//			if(game.getId()==(gameid)) { //matching the gameId
//				game.addPlayer(this);
//				out.println("This message is sent by game: " + 
//		    			game.getListofCurrentPlayers().stream().map(p-> p.getNickname()+" ")
//		    			.reduce("", (acc, curr)-> acc + curr));
//				/*this.getNickname() + " has joined the game "+ gameid*/
//				game.start();
//				return ; 
//				}
//			
//			}
			
			
		}
//		
//	
		private void parseGuess(String guess) throws IOException {
			for(Player player : tempGameHolder.getListofCurrentPlayers()) {
				if(player==this) {
					player.setGuess(Integer.parseInt(guess));
					tempGameHolder.listOfCurrentGuesses.add(player.getGuess());
					tempGameHolder.getAverage(tempGameHolder.listOfCurrentGuesses, player);
//			System.out.println(tempGameHolder.listOfCurrentGuesses.get(0));
		}
	}

			

		}
//
		private static void parseChat(String msg) throws UnknownHostException, IOException {
			String message = " says: "+msg;
			Dummy2.Server.broadcast(message);
		}
//		
//		public static String getLeaderBoard() throws ClassNotFoundException {
//			ArrayList<Dummy2.Player> leaderboardArr = DatabaseUtil.getTopFivePlayers();
//			String leaderboardString = leaderboardArr.stream()
//					.map(p -> p.printPlayer() + "\n")
//						.reduce("", (acc, curr) -> acc + curr);
//			return leaderboardString;
//		}
		
		 public void sendMessage(String message) {
		        out.println(message);
		    }

}
