package app;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
	
	private static ArrayList<Game> listOfGames; //array list to store games
	
	public static void main(String args[]) {
		try {

			@SuppressWarnings("resource")

			ServerSocket server = new ServerSocket(13337);
			System.out.println("Server waiting for client on port " +
					server.getLocalPort());
			System.out.println("Server Started");
			
			
			setListOfGames(new ArrayList<>()); //Initializing list of games array list

			for(;;) {
				// Get the next TCP Client
				Socket nextClient = server.accept();
				// Display connection details
				System.out.println("Receiving Request From " +
						nextClient.getInetAddress() + ":" +
						nextClient.getPort());

				Server.initialService(nextClient);

				//				output.println();

			}
		} catch(IOException ioe){
			System.out.println("Error" + ioe);
		}
	}

	public static ArrayList<Game> getListOfGames() {
		return listOfGames;
	}

	public static void setListOfGames(ArrayList<Game> listOfGames) {
		Server.listOfGames = listOfGames;
	}
	
	 
	private static void initialService(Socket nextClient) {
		new Thread(new Runnable() {
			PrintWriter output;
			BufferedReader fromClient;
			@Override
			public void run() {

				try {
					output = new PrintWriter(nextClient.getOutputStream(), true);
					output.println("Identify Yourself: ");
					// reading ticket/name from client
				  
				fromClient=new BufferedReader(new InputStreamReader(nextClient.getInputStream()));
				
					String ticket ="sane1";
					//Player player = new Player(fromClient.readLine(), 0, ticket, 0);
					//Game game = new Game();
					//game.listOfCurrentPlayers.add(player);
					output.println("Welcome "+fromClient.readLine()+", Your Ticket is "+ticket);
				
				    Game game1 = new Game(nextClient);
					game1.run();
					
				} catch (IOException e) {
					e.printStackTrace();
				} 

			}

		}).start();
	}


}


