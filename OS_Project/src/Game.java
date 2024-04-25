import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

public class Game implements Runnable {
	
	public  ArrayList<Integer> listOfCurrentGuesses = new ArrayList<>();
	private ArrayList<Player> listOfCurrentPlayers = new ArrayList<>();
	private String gameName;
	
	Socket nextClient;
	PrintWriter to_client;
	BufferedReader from_client;
	int countTrials=0;
	long currentTime = System.currentTimeMillis();
	long newTime, diff;
	
	public Game(Socket nextClient) {
	//	super();
		this.nextClient = nextClient;
	}
	// all game rules will be coded here
	@Override
	public void run() {
		try {
			to_client = new PrintWriter(nextClient.getOutputStream(), true);
			from_client = new BufferedReader(new InputStreamReader(nextClient.getInputStream()));
			
			while(true) {
				if(diff > 60000) {
					to_client.println("You are done, n.o of correct trials = "+countTrials);
					to_client.println("Done");
					break;
				}
				Random rand = new Random();
				int numb1 = rand.nextInt(10)+1;
				int numb2 = rand.nextInt(10)+1;
				int numb3 = rand.nextInt(10)+1;
				
				int serverSum = numb1 + numb2 + numb3;
				
				to_client.println("Enter the Sum of 3 numbers: "+numb1+" "+numb2+" "+numb3);
				
				String userSum = from_client.readLine();
				
				int userSumInt = Integer.parseInt(userSum);
				
				newTime = System.currentTimeMillis();
				
				diff = newTime - currentTime;
				
				if(serverSum == userSumInt && diff<60000) {
					countTrials++;
				}
			}
		}catch(IOException e){
			
			e.printStackTrace();			
		}
		finally{
			try {
				if(nextClient!=null) {
					nextClient.close();
				}
				if(to_client!=null) {
					to_client.close();
				}
				if(from_client!=null) {
					from_client.close();
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public double getAverage() {
		double sum = listOfCurrentGuesses.stream().reduce(0, (acc , curr)-> acc +=curr);
		return sum/listOfCurrentGuesses.size();
	}
	
	public void decideWinner() {
		
		double target = (2.0 / 3.0) * getAverage();
        double minDifference = Double.MAX_VALUE;
        ArrayList<Player> winners = new ArrayList<>();
        for (Player player : listOfCurrentPlayers) {
            double difference = Math.abs(target - player.getGuess());
            if (difference < minDifference) {
                minDifference = difference;
                winners.clear();
                winners.add(player);
                listOfCurrentPlayers.remove(player);
            } else if (difference == minDifference) {
                winners.add(player);
                listOfCurrentPlayers.remove(player);
            }
        }
        decrementPoint(listOfCurrentPlayers);
        for (Player p : winners) {
        	p.setNumberOfWins(p.getNumberOfWins()+1);
        	listOfCurrentPlayers.add(p);
        }
	}
	
	public void decrementPoint(ArrayList<Player> player) {
		for(Player p : player) {
			p.setGamePoints(p.getGamePoints()-1);
		}
	}

}
