package Dummy2;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

import database.DatabaseUtil;


public class Game {
	
	 private static int idCounter = 1;
	    private int id;
	    private List<Player> listofCurrentPlayers;
	    private List<Player> roundLosers;
	    private List<Player> players;
	    private List<Player> winners;
	    private List<Player> listOfDeadPlayers;
	    private int roundNumber;
	    public  List<Integer> listOfCurrentGuesses ;

	    public Game() {
	        id = idCounter++;
			listofCurrentPlayers = new ArrayList<>();
			listOfCurrentGuesses = new ArrayList<>();
			roundLosers = new ArrayList<>();
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
	    
	    public synchronized void start() throws IOException, InterruptedException {
	    	 roundNumber++;
	    	 players = new ArrayList<>(this.listofCurrentPlayers);
	    	 for (Player player :players) {
	             PrintWriter output = new PrintWriter(player.getSocket().getOutputStream(), true);
	             output.println("Round "+roundNumber+". Guess a number between 0 and 100: ");
	             
	         }
	    	
	    	
	    }
	    public synchronized void getAverage(int guess, Player player) throws IOException, ClassNotFoundException {
	    	
	    	 addGuess(guess);
	         if (listOfCurrentGuesses.size() == players.size()) {
	             calculateWinners();
	             sendRoundResults();
	             listOfCurrentGuesses.clear();
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
	        roundResults += listofCurrentPlayers.stream()
	                                            .map(p -> Integer.toString(p.getGuess()))
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
	    	//calculation variable
	    	double sum = listOfCurrentGuesses.stream().reduce(0, (acc, curr) -> acc + curr);
			double average=  sum/listOfCurrentGuesses.size();
			double target = (2.0 / 3.0) * average;
//	        double minDifference = Double.MAX_VALUE;
			double minDifference = 100000000; //unreachable value
	        // round starter code
	        for (Player player :players) {
	        	player.setRoundStatus("lose");
	        	player.setReady(false);
	        }
	        //new logic 
	        if(players.size()>2) {// players are more than 2
	        	//game logic
		        for (Player player : players) { //iterate players
		            if(player.getGuess()>=0 && player.getGuess()<=100) { //if player's guess is between 0 and 100
		            	double difference = Math.abs(target - player.getGuess()); //check the difference between target and player guess 
		                if (difference < minDifference) { //compares the difference
		                    minDifference = difference; //sets the minDifference to players difference if its lesser
		                    winners.clear(); // clears the winners list
		                    winners.add(player); // adds the player to winners list
		                    players.remove(player); // removes player from list
		                } else if (difference == minDifference) { //if player diff is same as the prev player diff
		                    winners.add(player); //append new player
		                    players.remove(player); // remove new player from list
		                }
		            }
		            
		        }
	        }
	        //last round Logic start/////////////////////////////
	        if(players.size()==2) {
	        	  for (Iterator<Player> iterator = listofCurrentPlayers.iterator(); iterator.hasNext();) {
	                  Player player = iterator.next();
	                  for (Iterator<Player> iterator2 = players.iterator(); iterator.hasNext();) {
		                  Player player2 = iterator2.next();
		                  if(player==player2) {
		                	  if (player.getGuess() == 0) {
			                	  player.setRoundStatus("lose");
			                      iterator2.remove();
			                      listOfDeadPlayers.add(player);
			                      winners.add(players.get(0));
			                      return;
			        		}
		                  }
		                  }
	                 
	        	}
	        }
	      //last round Logic end//////////////////////////////
	        
	        //game logic
	        for (Player player : players) {
	            if(player.getGuess()>=0 && player.getGuess()<=100) {
	            	double difference = Math.abs(target - player.getGuess());
	                if (difference < minDifference) {
	                    minDifference = difference;
	                    winners.clear();
	                    winners.add(player);
	                    players.remove(player);
	                } else if (difference == minDifference) {
	                    winners.add(player);
	                    players.remove(player);
	                }
	            }
	            
	        }
	        
	        decrementPoint(players);
	        for(Player winner:winners) { // adding winners back
	        	for(Player plr:listofCurrentPlayers) {
	        		if(winner.getTicket().equals(plr.getTicket())) {
	        			plr.setRoundStatus("win");
	        			break;
	        		}
	        	}
	        	players.add(winner);
	        }
	        
	        if(players.size()!=1) { //removing dead players
	        	for (Player player : players) {
		            if(player.getGamePoints()==0) {
		            	listOfDeadPlayers.add(player);
		            	players.remove(player);
		            }
		        }
	        }
	        else {
	        	if(players.get(0).getGamePoints()==0) { //check last player points
	        		listOfDeadPlayers.add(players.get(0));
	        		for (Player p : winners) {
	        			for(Player plr:listofCurrentPlayers) {
	    	        		if(p.getTicket().equals(plr.getTicket())) {
	    	        			if(players.size()==1) {
	    	    	        		players.clear();
	    	    	        	}
	    	    	        	p.setRoundStatus("win");
	    	    	        	players.add(p);
	    	    	        	break;
	    	        		}
	    	        	}
	    	        }
	    	        
	    	        //last player left logic
	    	        if(players.size()==1) {
	    	        	DatabaseUtil.incrementPlayerNumberOfWins(players.get(0).getTicket());
//	    	        	listofCurrentPlayers.clear();
	    	        	players.clear();
	    	        	return;
	    	        }
	        	}
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

		public List<Player> getPlayers() {
			return players;
		}

		public void setPlayers(List<Player> players) {
			this.players = players;
		}

		public List<Player> getRoundLosers() {
			return roundLosers;
		}

		public void setRoundLosers(List<Player> roundLosers) {
			this.roundLosers = roundLosers;
		}

			

	    // Other game methods...

}
