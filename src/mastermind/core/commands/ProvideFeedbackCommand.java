package mastermind.core.commands;
import mastermind.core.*;

public class ProvideFeedbackCommand extends PlayListCommand implements ICommand {
		
	/**
	 * 
	 * @param listOfGuesses The list of guesses for the current game
	 * @param secretCode The secret code for the current game
	 */
	public ProvideFeedbackCommand(PlayList listOfGuesses, ColorPeg[] secretCode)
	{
		super(listOfGuesses, secretCode);
	}

	/**
	 * Executes the command to provide feedback
	 */
	public void execute() 
	{	
		Code secret = new Code(this.colors);
		Guess latestGuess = this.guesses.getLatestMove();
		Feedback f = Feedback.analyze(secret, latestGuess.getCode());
		guesses.addFeedbackToLastGuess(f);
	}

	/**
	 * Removes the most recently provided feedback
	 */
	public void undo() 
	{
		this.guesses.deleteLastFeedback();
	}

}
