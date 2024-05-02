package Dummy2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class Game {
	
	 private static int idCounter = 1;
	    private int id;
	    private List<Player> listofCurrentPlayers;
	    private int roundNumber;
	    public static List<Integer> listOfCurrentGuesses ;

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
	    	for(Player player: listofCurrentPlayers) {
	    		output = new PrintWriter(player.getSocket().getOutputStream(), true);
	    		sum = listOfCurrentGuesses.stream()
		    			.reduce(0, (acc,curr) -> acc+=curr);
		    	output.println("SUM = "+player.getGuess());
	    	}
	    	
	    	
	    	/*sum = listOfCurrentGuesses.stream()
			    			.reduce(0, (acc,curr) -> acc+=curr);
			    	output.println("SUM = "+player.getGuess());*/
	    	
	    	
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
