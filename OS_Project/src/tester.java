
public class tester {

	public static void main(String[] args) {
		
		Player p1 = new Player("say", 5, "s", 0);
		Player p2 = new Player("jay", 2, "j", 0);
		Player p3 = new Player("fay", 3, "f", 0);
		
		Leaderboard lb = new Leaderboard();
		
		lb.putPlayer(p1);
		lb.putPlayer(p2);
		lb.putPlayer(p3);
		
		

	}

}
