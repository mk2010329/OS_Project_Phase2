package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
	
	 public static void createPlayerTable() throws ClassNotFoundException {
	        // SQLite connection string
		 	Class.forName("org.sqlite.JDBC");
	        String url = "jdbc:sqlite:"+System.getProperty("user.dir")+"\\database\\osproject.db";
	        
	        // SQL statement for creating a new table
	        String sql = "CREATE TABLE IF NOT EXISTS players (\n"
	                + "	ticket integer PRIMARY KEY,\n"
	                + "	nickname text NOT NULL,\n"
	                + "	numberOfWins integer NOT NULL\n"
	                + ");";
	        
	        try (Connection conn = DriverManager.getConnection(url);
	        		Statement stmt = conn.createStatement()) {
	        	// create a new table
	        	stmt.execute(sql);
	        } catch (SQLException e) {
	        	System.out.println(e.getMessage());
	        }
	    }

}
