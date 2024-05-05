package Dummy2;

import java.awt.dnd.DropTargetListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import app.ServerUtil;
import chat.Client;
import database.DatabaseUtil;

public class Player implements Runnable{
		private long startTimer;
		Game tempGameHolder;
		private BufferedReader in;
	    private PrintWriter out;
	 	private Socket socket;
	    private String ticket;
	    private String nickname;
	    private String roundStatus;
	     static Player player;
		private int numberOfWins;
		private int gamePoints;
		private int guess;
		private boolean ready;
		private boolean haveGuessed;

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
	    public String getTicket() {
			return ticket;
		}
	    
	    public String getTicketString() {
			return getTicket()+getNickname();
		}
		public void setTicket(String ticket) {
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
		public long getStartTimer() {
			return startTimer;
		}
		public void setStartTimer(long startTimer) {
			this.startTimer = startTimer;
		}
		@Override
		public String toString() {
			return "Player [ticket=" + ticket + ", nickname=" + nickname + "]";
		}

		@Override
	    public void run() {
	        try {
	        	this.haveGuessed=false;
	            String inputLine;
	            while ((inputLine = in.readLine()) != null) {
	                // Handle client messages
	            	/////////////////////////////////timer///////////////////////////////////////
//	            	if(out.toString().split(" ")[2].equals("Guess")) {
//	            		out.println("Pick in 5 seconds");
//	            		out.flush();
//	            		wait(5000);
//	            		if(inputLine.split(" ")[0].equals("guess")&&inputLine.split(" ").length==2){
//	            			
//	            		}else {
//	            			inputLine = "guess -1";
//	            		}
//						Timer timer = new Timer();
//			            TimerTask task = new TimerTask() {
//			           	 public void run() {
//			           		setHaveGuessed(true);
//			           	 }
//			            };
//			            timer.schedule(task, 5000);
//	            	}	            	
	            	/////////////////////////////////timer///////////////////////////////////////
	            	String [] clientMsgArr = inputLine.split(" ");
	        		switch(clientMsgArr[0].toLowerCase()) {
	        			case "pseudo": parsePseudo(clientMsgArr);
	        			break;
	        			case "join"  : parseJoin(clientMsgArr[1]);
	        			break;
	        			case "ready" : parseReady();
	        			break;
	        			case "guess" : parseGuess(clientMsgArr[1]);
	        			break;
//	        			case "chat"  : parseChat();break;
	        			default: out.println("Command not recognized"); 
	                }
	            }
	        } catch (IOException | ClassNotFoundException | InterruptedException e) {
	            e.printStackTrace();
	        } finally {
//	            Server.removePlayer(this);
	            try {
	                socket.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	
//		//processes pseudo command
		private void parsePseudo(String [] clientMsgArr) throws ClassNotFoundException {
			 player = DatabaseUtil.searchTicket(clientMsgArr[1]);
			out.println("Welcome "+this.getNickname()+"\nYour ticket is "+player.getTicket());
			initialMessage();
		}
		
//		//processes join command
		private void parseJoin(String gameId) throws IOException {
			int gameid =Integer.parseInt(gameId);
			 List<Dummy2.Game> games = Dummy2.Server.getGames();
			for(Dummy2.Game game: games) { //iterating games
				
				if(game.getId()==(gameid)) { //matching the gameId
					//System.out.println(game.hashCode());
					tempGameHolder = game;
					this.haveGuessed = false;
					game.addPlayer(this);
					this.setGamePoints(5);
					out.println("This message is sent by game: " + 
			    			game.getListofCurrentPlayers().stream().map(p-> p.getNickname()+" ")
			    			.reduce("", (acc, curr)-> acc + curr));
					///////////////////timer////////////////////////////////
//					if(game.getListofCurrentPlayers().size()==2) {
//						Timer timer = new Timer();
//			            TimerTask task = new TimerTask() {
//			           	 public void run() {
//			           		 try {
//								game.start();
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//			           	 }
//			            };
//			            timer.schedule(task, 60000);
//					}
					///////////////////timer////////////////////////////////
					/*this.getNickname() + " has joined the game "+ gameid*/
					return; 
					}
				}
			out.println("Game not found!"); // failed to find game in list of games returns null

		}
		
//		//processes ready command
		private void parseReady() throws IOException, InterruptedException {
			this.ready=true;
			int readyCounter=0;
			for(Player player:tempGameHolder.getListofCurrentPlayers()) {
				if(player.ready) {
					readyCounter++;
				}
			}
			//
			if(readyCounter==tempGameHolder.getListofCurrentPlayers().size()
					&&tempGameHolder.getListofCurrentPlayers().size()>=2) {
				tempGameHolder.start();
			}
			
		}
		
		private void parseGuess(String guess) throws IOException, ClassNotFoundException, InterruptedException {
			for(Player player : tempGameHolder.getListofCurrentPlayers()) {
				if(player==this) {
					int playerGuess = Integer.parseInt(guess);
					player.setGuess(playerGuess);
					player.haveGuessed=true;
//					game.addGuess(playerGuess);
				tempGameHolder.getAverage(playerGuess, player);
//			System.out.println(tempGameHolder.listOfCurrentGuesses.get(0));
				}
			}	
		}
		
//
		private static void parseChat(String msg) throws UnknownHostException, IOException {
			String message = " says: "+msg;
			Dummy2.Server.broadcast(message);
		}
		
		 public void sendMessage(String message) {
		        out.println(message);
		    }
		 public  void initialMessage() throws ClassNotFoundException {
				out.println("Leaderboard:");
				out.println(ServerUtil.getLeaderBoard());
				out.println("Games available:");
				out.println(Server.getGames().toString());
				out.println("All Players:");
				out.println(Server.players.toString());
				out.println("Join any game: ");
				out.println("END_OF_TRANS");
		 }
		public boolean isHaveGuessed() {
			return haveGuessed;
		}
		public void setHaveGuessed(boolean haveGuessed) {
			this.haveGuessed = haveGuessed;
		}
}
