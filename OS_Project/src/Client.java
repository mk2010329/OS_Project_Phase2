import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Client {
	public static void main(String args[]) {
		try {
			// Get a Socket to the daytime service,
			System.out.print("Enter IP address: ");
			Scanner input = new Scanner(System.in);
			String IP = input.next(); 
			
			System.out.print("Enter Port No: ");
			Scanner input2 = new Scanner(System.in);
			int port = input2.nextInt();
			
			Socket daytime = new Socket(IP, port);
			System.out.println("Connected with server " +
					daytime.getInetAddress() + ":" +
					daytime.getPort());
			// Set the socket option just in case server stalls
			daytime.setSoTimeout(2000);
			// Read from the server
			BufferedReader reader =
					new BufferedReader(
							new InputStreamReader(daytime.getInputStream()));
			// Display result on the screen
			System.out.println("Result = " + reader.readLine());
			// Close the connection
			daytime.close();
		} catch(IOException ioe) {
			System.out.println("Error" + ioe);
		}
	}
}