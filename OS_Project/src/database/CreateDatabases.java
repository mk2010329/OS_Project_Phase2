package database;

public class CreateDatabases {

	public static void main(String[] args) throws ClassNotFoundException {
		
		DatabaseUtil.createPlayerTable();
		DatabaseUtil.seedPlayerTable();
		DatabaseUtil.displayPlayersTable();
		String tokenArr = "15jay";
		System.out.println(DatabaseUtil.searchTicket(tokenArr));
		System.out.println( DatabaseUtil.getTopFivePlayers() );
	}

}
