package app;


public class Player {

	private String nickname;
	private int numberOfWins;
	private String ticket;
	private int gamePoints;
	private int guess;
	
	public Player(String nickname, int numberOfWins, String ticket) {
		this.gamePoints = 0;
		this.nickname = nickname;
		this.numberOfWins = numberOfWins;
		this.ticket = ticket;
		this.gamePoints = gamePoints;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getNumberOfWins() {
		return numberOfWins;
	}

	public void setNumberOfWins(int numberOfWins) {
		this.numberOfWins = numberOfWins;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public int getGamePoints() {
		return gamePoints;
	}

	public void setGamePoints(int gamePoints) {
		this.gamePoints = gamePoints;
	}
	

	public int getGuess() {
		return guess;
	}

	public void setGuess(int guess) {
		this.guess = guess;
	}

	@Override
	public String toString() {
		return "Player [nickname=" + nickname + ", numberOfWins=" + numberOfWins + ", ticket=" + ticket
				+ ", gamePoints=" + gamePoints + "]";
	}


}
