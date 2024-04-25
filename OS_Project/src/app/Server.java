package app;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
	public static void main(String args[]) {
		try {
			// Bind to service port, so clients access to daytime service
			ServerSocket server = new ServerSocket(13337);
			System.out.println("Server waiting for client on port " +
					server.getLocalPort());
			System.out.println("Day Time Service Started");
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
	
	 
}


