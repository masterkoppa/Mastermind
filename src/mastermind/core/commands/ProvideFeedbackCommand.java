package mastermind.core.commands;

import java.io.IOException;

import mastermind.core.*;
import mastermind.logging.GameLog;

/**
 * 
 * @author Andrew Church
 * 
 *         Command to provide feedback
 */
public class ProvideFeedbackCommand implements ICommand {

	private GameLog theLogger;
	private GameModel theGame;
	private PlayList guesses;
	private ColorPeg[] colors;

	/**
	 * 
	 * @param listOfGuesses
	 *            The list of guesses for the current game
	 * @param secretCode
	 *            The secret code for the current game
	 * @throws IOException
	 */
	public ProvideFeedbackCommand(GameModel currentGame,
			PlayList listOfGuesses, ColorPeg[] secretCode) throws IOException {
		if (null == currentGame)
			throw new IllegalArgumentException("GameModel cannot be null");

		if (null == listOfGuesses)
			throw new IllegalArgumentException("PlayList cannot be null");

		if (null == secretCode)
			throw new IllegalArgumentException("Colors cannot be null");

		this.guesses = listOfGuesses;
		this.colors = secretCode;
		this.theGame = currentGame;

		theLogger = GameLog.getInstance();
	}

	/**
	 * Executes the command to provide feedback
	 */
	public void execute() {
		Code secret = new Code(this.colors);
		Guess latestGuess = this.guesses.getLatestMove();
		Feedback f = Feedback.analyze(secret, latestGuess.getCode());
		guesses.addFeedbackToLastGuess(f);
		theLogger.write("Feedback provided");

		if (f.isGameOver())
			this.theGame.declareWinner(GameWinner.CODEBREAKER);
		else if (this.guesses.getLastPlayIndex() == 9)
			this.theGame.declareWinner(GameWinner.CODEMAKER);
	}

	/**
	 * Removes the most recently provided feedback
	 */
	public void undo() {
		Guess latestGuess = this.guesses.getLatestMove();
		latestGuess.setFeedback(null);
		theLogger.write("Feedback undone");
	}

}
