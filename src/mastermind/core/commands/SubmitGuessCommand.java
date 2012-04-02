package mastermind.core.commands;
import java.io.IOException;

import mastermind.core.*;
import mastermind.logging.*;

public class SubmitGuessCommand extends PlayListCommand implements ICommand {
	
	private GameLog gameLogger;
	
	/**
	 * 
	 * @param listOfGuesses - The model that contains a list of all the guesses
	 * @param colors - The code to be submitted
	 */
	public SubmitGuessCommand(PlayList listOfGuesses, ColorPeg[] colors) throws IOException
	{
		super(listOfGuesses, colors);
		gameLogger = GameLog.getInstance();
	}
	
	public void execute() 
	{
		Code newGuess = new Code(this.colors);
		this.guesses.addNewCode(newGuess);
		this.gameLogger.write("Added new Guess");
		this.gameLogger.write(newGuess.toString());
	}
	
	public void undo()
	{
		this.guesses.deleteNewCode();
		this.gameLogger.write("Removing last Guess");
	}

}
