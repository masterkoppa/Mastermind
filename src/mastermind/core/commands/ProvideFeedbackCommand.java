package mastermind.core.commands;
import java.io.IOException;

import mastermind.core.*;
import mastermind.logging.GameLog;

public class ProvideFeedbackCommand extends PlayListCommand implements ICommand {
		
	private GameLog theLogger;
	
	/**
	 * 
	 * @param listOfGuesses The list of guesses for the current game
	 * @param secretCode The secret code for the current game
	 * @throws IOException 
	 */
	public ProvideFeedbackCommand(PlayList listOfGuesses, ColorPeg[] secretCode) throws IOException
	{
		super(listOfGuesses, secretCode);
		theLogger = GameLog.getInstance();
	}

	/**
	 * Executes the command to provide feedback
	 */
	public void execute() 
	{	
		Code secret = new Code(this.colors);
		Guess latestGuess = this.guesses.getLatestMove();
		Feedback f = Feedback.analyze(secret, latestGuess.getCode());
		latestGuess.setFeedback(f);
		theLogger.write("Feedback provided");
	}

	/**
	 * Removes the most recently provided feedback
	 */
	public void undo() 
	{
		Guess latestGuess = this.guesses.getLatestMove();
		latestGuess.setFeedback(null);
		theLogger.write("Feedback undone");
	}

}
