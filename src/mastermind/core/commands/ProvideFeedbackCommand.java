package mastermind.core.commands;
import mastermind.core.*;

public class ProvideFeedbackCommand implements ICommand {
	
	private Guess latestGuess;
	private Code theSecretCode;
	
	/**
	 * 
	 * @param listOfGuesses The list of guesses for the current game
	 * @param secretCode The secret code for the current game
	 */
	public ProvideFeedbackCommand(PlayList listOfGuesses, Code secretCode)
	{
		if(null == listOfGuesses)
			throw new IllegalArgumentException("Must supply a valid list of guesses");
		
		if(null == secretCode)
			throw new IllegalArgumentException("Must supply a valid secret code");
		
		this.latestGuess = listOfGuesses.getLatestMove();
		this.theSecretCode = secretCode;
	}

	/**
	 * Executes the command to provide feedback
	 */
	public void execute() 
	{		
		this.latestGuess.setFeedback(Feedback.analyze(this.theSecretCode, this.latestGuess.getCode()));
	}

	/**
	 * Removes the most recently provided feedback
	 */
	public void undo() 
	{
		this.latestGuess.setFeedback(null);
	}

}
