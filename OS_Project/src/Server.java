import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
	public static void main(String args[]) {
		try {

			ServerSocket server = new ServerSocket(13337);
			System.out.println("Server waiting for client on port " +
					server.getLocalPort());
			System.out.println("Server Started");
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

	private static void initialService(Socket nextClient) {
		new Thread(new Runnable() {
			PrintWriter output;
			@Override
			public void run() {

				try {
					output = new PrintWriter(nextClient.getOutputStream(), true);
					output.println("Identify Yourself: ");
					// reading ticket/name from client
					BufferedReader fromClient = 
							new BufferedReader(new InputStreamReader(nextClient.getInputStream()));
					//System.out.println(fromClient.readLine());
					String ticket ="sane1";
					output.println("Welcome "+fromClient.readLine()+", Your Ticket is "+ticket);
					// Close connection
					//	nextClient.close();
					Game game = new Game(nextClient);
					game.run();
					
				} catch (IOException e) {
					e.printStackTrace();
				} 

			}

		}).start();
	}


}


