package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import app.Player;

public class DatabaseUtil {

	private static Connection makeConnection() throws ClassNotFoundException, SQLException {
		// SQLite connection string
		Class.forName("org.sqlite.JDBC");
		String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\database\\osproject.db";
		Connection conn = DriverManager.getConnection(url);
		return conn;
	}
	
	public static void createPlayerTable() throws ClassNotFoundException {

		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS players (\n" + 
					 " ticket_temp INTEGER PRIMARY KEY AUTOINCREMENT,\n" + 
					 " nickname TEXT NOT NULL,\n" +
					 " ticket TEXT GENERATED ALWAYS AS (printf('%01d%s', ticket_temp, nickname)) STORED,\n" + 
					 " numberOfWins INTEGER NOT NULL\n" + ");";

		try (Statement statement = DatabaseUtil.makeConnection().createStatement()) {
			// create a new table
			statement.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void seedPlayerTable() throws ClassNotFoundException {
		String sql = "INSERT INTO players (nickname, numberOfWins)\n" +
					 "VALUES('jay', 0);";
		
		try (Statement statement = DatabaseUtil.makeConnection().createStatement()) {
			// populate the table
			statement.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void displayPlayersTable() throws ClassNotFoundException {
		
		String sql = "SELECT * FROM players;";
		
		try (Statement statement = DatabaseUtil.makeConnection().createStatement()) {	
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int ticket_temp = resultSet.getInt(1);
				String nickname = resultSet.getString(2);
				String ticket = resultSet.getString(3);
				int numberWins = resultSet.getInt(4);

				System.out.println(ticket_temp + " " + nickname + " " + ticket + " " + numberWins);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static Player searchTicket(String[] tokenArray) throws ClassNotFoundException {
		
		String sql = "SELECT * FROM players WHERE ticket LIKE " + "'"+tokenArray[1]+"'";
		
		try (Statement statement = DatabaseUtil.makeConnection().createStatement()) {
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int ticket_temp = resultSet.getInt(1);
				String nickname = resultSet.getString(2);
				String ticket = resultSet.getString(3);
				int numberWins = resultSet.getInt(4);

				Player player = new Player(nickname, numberWins, ticket);
				
				return player;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static String insertPlayer(String[] tokenArray) throws ClassNotFoundException {
		String sql = "INSERT INTO players (nickname, numberOfWins)\n" +
				 "VALUES('"+ tokenArray[1] +"', 0);";
	
		try (Statement statement = DatabaseUtil.makeConnection().createStatement()) {
			statement.execute(sql);
			
			// I am changing the specification of the command to: "pseudo ticket blabla"
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	

}
