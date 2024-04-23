import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class Game implements Runnable {
	
	public  ArrayList<Integer> listOfCurrentGuesses = new ArrayList<>();
	private ArrayList<Player> listOfCurrentPlayers = new ArrayList<>();
	private String gameName;
	
	public Game() {
	//	super();
		
	}
	
	// all game rules will be coded here
	
	@Override
	public void run() {
		// Write the current time to the client socket
		PrintWriter output;
		try {
			//output = new PrintWriter(nextClient.getOutputStream(), true);
			//output.println(new Date());
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	finally {
		try {
	//		if(nextClient!=null) {
				// Close connection
		
		//	}
		} catch (Exception e2) {
			// TODO: handle exception
		}
	}
		
	}
	
	public double getAverage() {
		
		double sum = listOfCurrentGuesses.stream().reduce(0, (acc , curr)-> acc +=curr);
		return sum/listOfCurrentGuesses.size();
	}
	
	public boolean pickPlayer() {
		
		return false;
	}

}
