package Dummy2;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	
	private static final int PORT = 13337;
    public static List<Player> players = new ArrayList<>();
    private static List<Game> games = new ArrayList<>();
    private static int ticketCounter = 1;
    public static  Player player;
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Listening on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
               player = new Player(clientSocket);
               
               Game game1 = new Game();
               Game game2 = new Game();
               Game game3 = new Game();
               
               //adding games to the list
               addGame(game1);
               addGame(game2);
               addGame(game3);
               //System.out.println(game1.hashCode());
               players.add(player);
                
               new Thread(player).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
//    public static synchronized int generateTicket() {
//        return ticketCounter++;
//    }

    public static synchronized void removePlayer(Player player) {
        players.remove(player);
    }

    public static synchronized List<Game> getGames() {
        return games;
    }

    public static synchronized void addGame(Game game) {
        games.add(game);
    }

    public static synchronized void removeGame(Game game) {
        games.remove(game);
    }

}
