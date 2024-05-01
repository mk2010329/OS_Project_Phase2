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

	    public synchronized void addPlayer(Player player) {
	        players.add(player);
	    }

	    public synchronized void removePlayer(Player player) {
	        players.remove(player);
	    }

	    // Other game methods...

}
