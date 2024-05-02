package Dummy2;

import java.util.*;

public class Game {
	
	 private static int idCounter = 1;
	    private int id;
	    private List<Player> players;
	    private int roundNumber;

	    public Game() {
	        id = idCounter++;
	        players = new ArrayList<>();
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
	        players.add(player);
	    }

	    public synchronized void removePlayer(Player player) {
	        players.remove(player);
	    }
	    
	    public synchronized void start() {
	        // Implement game start logic
	        // For example, start round, send initial messages, etc.
	    }

	    // Other game methods...

}
