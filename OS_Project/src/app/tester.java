package app;

public class tester {

	public static void main(String[] args) {
		
		Game game = new Game();
		game.listOfCurrentGuesses.add(1);
		game.listOfCurrentGuesses.add(1);
		game.listOfCurrentGuesses.add(3);
		game.listOfCurrentGuesses.add(2);
		
		
		System.out.println(game.getAverage());
	}

}
