package app;

import java.net.*;
import java.io.*;

public class Client {
	public static void main(String args[]) {
		PrintWriter to_server;
		BufferedReader from_server;
		BufferedReader from_user;
		
		try {

			Socket client = new Socket("localhost", 13337);
			from_server = new BufferedReader(new InputStreamReader(client.getInputStream()));
			from_user = new BufferedReader(new InputStreamReader(System.in));
			to_server = new PrintWriter(client.getOutputStream(), true);
			
			System.out.println("Connected with server " +
					client.getInetAddress() + ":" +
					client.getPort());
			
			while(true) {
				//identify yourself
				String msg = from_server.readLine();
				System.out.printf(msg);
				if(msg.equals("Done") || client == null) {
					break;
				}
				//entering your name
				String userInput= from_user.readLine();
				to_server.println(userInput);

				while ((msg = from_server.readLine()) != null) {
					System.out.println();
					System.out.printf(msg);
				
					}
			}
			// Display result on the screen
			//System.out.println("Result = " + reader.readLine());
			// Close the connection
			//daytime.close();
		} catch(IOException ioe) {
			System.out.println("Error" + ioe);
		}
	}
}