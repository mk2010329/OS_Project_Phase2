package Dummy2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

import app.Player1;
import database.DatabaseUtil;


public class Game {
	
	 private static int idCounter = 1;
	    private int id;
	    private List<Player> listofCurrentPlayers;
	    private List<Player> winners;
	    private List<Player> listOfDeadPlayers;
	    private int roundNumber;
	    public  List<Integer> listOfCurrentGuesses ;

	    public Game() {
	        id = idCounter++;
			listofCurrentPlayers = new ArrayList<>();
			listOfCurrentGuesses = new ArrayList<>();
			winners = new ArrayList<>();
		    listOfDeadPlayers = new ArrayList<>();
	        roundNumber = 0;
	    }

	    public int getId() {
	        return id;
	    }
	    
	    
	    @Override
		public String toString() {
			return "Game [id=" + id + "]";
		}

		public synchronized void addPlayer(Player player) {
	        listofCurrentPlayers.add(player);
	    }

	    public synchronized void removePlayer(Player player) {
	        listofCurrentPlayers.remove(player);
	    }
	    
	    public synchronized void addGuess(int guess) {
	        listOfCurrentGuesses.add(guess);
	    }

	    public synchronized void start() throws IOException {
	    	
	    	 for (Player player : listofCurrentPlayers) {
	             PrintWriter output = new PrintWriter(player.getSocket().getOutputStream(), true);
	             output.println("GAME HAS STARTED. Guess a number between 0 and 100: ");
	         }
	    	
	    	
	    }
	    public synchronized void getAverage(int guess, Player player) throws IOException, ClassNotFoundException {
	    	
	    	 addGuess(guess);

	         if (listOfCurrentGuesses.size() == listofCurrentPlayers.size()) {
	        	 roundNumber++;
	             calculateWinners();
	             sendRoundResults();  
	         }
		}
	    
	    private void sendRoundResults() throws IOException {
	        // Construct and send round results to all players
	        String roundResults = "game round " + roundNumber + " ";
	        // Add player nicknames
	        roundResults += listofCurrentPlayers.stream()
	                                            .map(Player::getNickname)
	                                            .collect(Collectors.joining(",")) + " ";
	        // Add guesses
	        roundResults += listOfCurrentGuesses.stream()
	                                            .map(Object::toString)
	                                            .collect(Collectors.joining(",")) + " ";
	        //Add points
	        roundResults += listofCurrentPlayers.stream()
                    .map(p -> Integer.toString(p.getGamePoints()))
                    .collect(Collectors.joining(",")) + " ";
	        
	        // Add win/lose status
	        roundResults += listofCurrentPlayers.stream()
	                                            .map(p -> p.getRoundStatus().toLowerCase())
	                                            .collect(Collectors.joining(","));
	        
	        //sending the output to all players in the game
	        for (Player player : listofCurrentPlayers) {
	            PrintWriter output = new PrintWriter(player.getSocket().getOutputStream(), true);
	            output.println(roundResults);
	        }
	    }
		
	    public void calculateWinners() throws IOException, ClassNotFoundException {
	    	
	    	double sum = listOfCurrentGuesses.stream().reduce(0, (acc, curr) -> acc + curr);
			double average=  sum/listOfCurrentGuesses.size();
			double target = (2.0 / 3.0) * average;
	        double minDifference = Double.MAX_VALUE;
	       
	        //last player left logic
	        if(listofCurrentPlayers.size()==1) {
	        	DatabaseUtil.incrementPlayerNumberOfWins(listofCurrentPlayers.get(0).getTicket());
	        	listofCurrentPlayers.clear();
	        	
	        	for(int i =0; i<12;i++) {
	        		Player.initialMessage();
	        	}
	        	
	        }
	      
	      //last round Logic
	        if(listofCurrentPlayers.size()==2) {
	        	  for (Iterator<Player> iterator = listofCurrentPlayers.iterator(); iterator.hasNext();) {
	                  Player player = iterator.next();
	                  if (player.getGuess() == 0) {
	                	  player.setRoundStatus("lose");
	                      iterator.remove();
	                      listOfDeadPlayers.add(player);
	                      winners.add(listofCurrentPlayers.get(0));
	                      return;
	        		}
	        	}
	        }
	        
	        //game logic
	        for (Player player : new ArrayList<>(listofCurrentPlayers)) {
	            if(player.getGuess()>=0 && player.getGuess()<=100) {
	            	double difference = Math.abs(target - player.getGuess());
	                if (difference < minDifference) {
	                    minDifference = difference;
	                    winners.clear();
	                    winners.add(player);
	                    listofCurrentPlayers.remove(player);
	                } else if (difference == minDifference) {
	                    winners.add(player);
	                    listofCurrentPlayers.remove(player);
	                }
	            }
	        }
	        
	        decrementPoint(listofCurrentPlayers);
	        for (Player player : listofCurrentPlayers) {
	            if(player.getGamePoints()==0) {
	            	listofCurrentPlayers.remove(player);
	            	listOfDeadPlayers.add(player);
	            }
	        }
	        
	        for (Player p : winners) {
	        	p.setRoundStatus("win");
	        	listofCurrentPlayers.add(p);
	        }
	        
	    }
	    
      //decrements player points
    	public void decrementPoint(List<Player> player) {
    		for(Player p : player) {
    			p.setGamePoints(p.getGamePoints()-1);
    			p.setRoundStatus("lose");
    		}
    	}
    	
		public List<Player> getListofCurrentPlayers() {
			return listofCurrentPlayers;
		}

		public void setListofCurrentPlayers(List<Player> listofCurrentPlayers) {
			this.listofCurrentPlayers = listofCurrentPlayers;
		}

		public int getRoundNumber() {
			return roundNumber;
		}

		public void setRoundNumber(int roundNumber) {
			this.roundNumber = roundNumber;
		}

		public void setId(int id) {
			this.id = id;
		}

			

	    // Other game methods...

}
