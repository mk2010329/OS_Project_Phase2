package database;

public class CreateDatabases {

	public static void main(String[] args) throws ClassNotFoundException {
		
		DatabaseUtil.createPlayerTable();
		DatabaseUtil.seedPlayerTable();
		DatabaseUtil.displayPlayersTable();
		String tokenArr = "jane";
		System.out.println(DatabaseUtil.searchTicket(tokenArr));

	}

}
