package Dummy2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

import app.Player1;


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
	    	int sum = 0;
	        // Implement game start logic
	        // For example, start round, send initial messages, etc.
	    	BufferedReader fromClient;
	    	PrintWriter output ;
	    	for (Player player: listofCurrentPlayers) {
	    		output = new PrintWriter(player.getSocket().getOutputStream(), true);
		    	output.println("GAME HAS STARTED,Guess a Number between 0 and 100: ");
		    	fromClient =
		    			new BufferedReader(new InputStreamReader
		    					(player.getSocket().getInputStream()));
	    	}
	    	
	    	
	    	/*sum = listOfCurrentGuesses.stream()
			    			.reduce(0, (acc,curr) -> acc+=curr);
			    	output.println("SUM = "+player.getGuess());*/
	    	
	    	
	    }
	    public synchronized void getAverage(List<Integer> guesses, Player player) throws IOException {
	    	
			double sum = guesses.stream().reduce(0, (acc , curr)-> acc +=curr);
			double average=  sum/guesses.size();
			roundWinnerLogic(average,player);
			
		}
		
	    public void roundWinnerLogic(double average, Player plyr) throws IOException {
			
			//Round End Report Work
			String roundEndResults="game round: "+(roundNumber++)+" ";
			for(int i=0;i<listofCurrentPlayers.size();i++) {
				if(i!=listofCurrentPlayers.size()-1) {
					roundEndResults.concat(listofCurrentPlayers.get(i).getNickname()+", ");
				}
				else {
					roundEndResults.concat(listofCurrentPlayers.get(i).getNickname()+" ");
				}
			}
			
			double target = (2.0 / 3.0) * average;
	        double minDifference = Double.MAX_VALUE;
	        ArrayList<Player> winners = new ArrayList<>();
	        
	        //last round Logic
	        if(listofCurrentPlayers.size()==2) {
	        	for(Player player:listofCurrentPlayers) {
	        		if(player.getGuess()==0) {
	        			listofCurrentPlayers.remove(player);
	        			listOfDeadPlayers.add(player);
	        			winners.add(listofCurrentPlayers.get(0));
	        			return;
	        		}
	        	}
	        }
	        //game logic
	        for (Player player : listofCurrentPlayers) {
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
	                }else{
	                	listOfDeadPlayers.add(player);
	                }
	            }
	        }
	        
	        decrementPoint(listofCurrentPlayers);
	        for (Player player : listofCurrentPlayers) {
	            if(player.getGamePoints()==0) {
	            	player.setRoundStatus("lose");
	            	listofCurrentPlayers.remove(player);
	            	
	            }
	        }
	        
	        for (Player p : winners) {
	        	p.setNumberOfWins(p.getNumberOfWins()+1);
	        	p.setRoundStatus("win");
	        	listofCurrentPlayers.add(p);
	        }
	        
	      //Round End Report Work 2
	      	for(int i=0;i<listofCurrentPlayers.size();i++) {
	      		if(i!=listofCurrentPlayers.size()-1) {
	      			roundEndResults.concat(listofCurrentPlayers.get(i).getGuess()+", ");
	      		}
	      		else {
	      			roundEndResults.concat(listofCurrentPlayers.get(i).getGuess()+" ");
	      		}
	      	}
	        
	        //Round End Report Work 3
	      	for(int i=0;i<listofCurrentPlayers.size();i++) {
	      		if(i!=listofCurrentPlayers.size()-1) {
	      			roundEndResults.concat(listofCurrentPlayers.get(i).getGamePoints()+", ");
	      		}
	      		else {
	      			roundEndResults.concat(listofCurrentPlayers.get(i).getGamePoints()+" ");
	      		}
	      	}
	      	
	        //Round End Report Work 3
	      	for(int i=0;i<listofCurrentPlayers.size();i++) {
	      		if(i!=listofCurrentPlayers.size()-1) {
	      			roundEndResults.concat(listofCurrentPlayers.get(i).getRoundStatus()+", ");
	      		}
	      		else {
	      			roundEndResults.concat(listofCurrentPlayers.get(i).getRoundStatus());
	      		}
	      	}
	      	if(!listOfDeadPlayers.isEmpty()) {
	      		roundEndResults.concat(" Eliminated Player(s): ");
	      		for(int i=0;i<listOfDeadPlayers.size();i++) {
	          		if(i!=listOfDeadPlayers.size()-1) {
	          			roundEndResults.concat(listOfDeadPlayers.get(i).getNickname()+", ");
	          		}
	          		else {
	          			roundEndResults.concat(listOfDeadPlayers.get(i).getNickname());
	          		}
	          	}
	      	}

	      //	System.out.println(roundEndResults);
	      	PrintWriter output = new PrintWriter(plyr.getSocket().getOutputStream(), true);
			output.println(roundEndResults);
	    }
      //decrements player points
    	public void decrementPoint(List<Player> player) {
    		for(Player p : player) {
    			p.setGamePoints(p.getGamePoints()-1);
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
