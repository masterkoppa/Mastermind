package mastermind.core;

public enum GameWinner {
	CODEMAKER("The Code Maker has won the game!"), CODEBREAKER(
			"The Code Breaker has won the game!");

	private String declareWinnerMessage;

	GameWinner(String theMessage) {
		this.declareWinnerMessage = theMessage;
	}

	/**
	 * Get the winner declaration message
	 * 
	 * @return
	 */
	public String getMessage() {
		return this.declareWinnerMessage;
	}
}
