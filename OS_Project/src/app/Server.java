package app;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
	
	private static ArrayList<Game> listOfGames; //array list to store games
	
	public static void main(String args[]) {
		try {
			// Bind to service port, so clients access to daytime service
			@SuppressWarnings("resource")
			ServerSocket server = new ServerSocket(13337);
			System.out.println("Server waiting for client on port " +
					server.getLocalPort());
			System.out.println("Day Time Service Started");
			
			setListOfGames(new ArrayList<>()); //Initializing list of games array list
			
			for(;;) {
				// Get the next TCP Client
				Socket nextClient = server.accept();
				// Display connection details
				System.out.println("Receiving Request From " +
						nextClient.getInetAddress() + ":" +
						nextClient.getPort());
				// Write the current time to the client socket
//				PrintWriter output =
//						new PrintWriter(nextClient.getOutputStream(), true);
//				output.println(new Date());
//				// Close connection
//				nextClient.close();
			//	Game service = new Game(nextClient);
			//	service.run();
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
	
	 
}


