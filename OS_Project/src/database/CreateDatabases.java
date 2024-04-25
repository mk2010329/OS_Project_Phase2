package database;

public class CreateDatabases {

	public static void main(String[] args) throws ClassNotFoundException {
		
		DatabaseUtil.createPlayerTable();
		DatabaseUtil.seedPlayerTable();
		DatabaseUtil.displayPlayersTable();
		String[] tokenArr = {"pseudo", "3jay"};
		System.out.println(DatabaseUtil.searchTicket(tokenArr));

	}

}
