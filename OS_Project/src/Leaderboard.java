import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;

public class Leaderboard {
	
	private static TreeSet<Player> leaderboard;
	
	public Leaderboard() {
		leaderboard = new TreeSet<>((p1, p2) -> p2.getNumberOfWins() - p1.getNumberOfWins());
	}
	
	public ArrayList<Player> getTopFive() {
		
		ArrayList<Player> topFiveList = new ArrayList<>();
		
		Iterator<Player> iterate; 
		iterate = leaderboard.iterator();
		int counter = 0;
		while (iterate.hasNext()) {
			counter++;
			if (counter == 5) break;
			
			topFiveList.add( iterate.next() );
		}
		return topFiveList;
	}
	
	public void putPlayer(Player player) {
		
		// if the player is not already there then add it, otherwise do not add it
		if (leaderboard.stream().noneMatch(p -> p.getTicket() == player.getTicket()))
			leaderboard.add(player);
	}
	

}
