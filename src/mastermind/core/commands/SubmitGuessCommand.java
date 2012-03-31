package mastermind.core.commands;
import mastermind.core.*;
import mastermind.logging.*;

public class SubmitGuessCommand implements ICommand {

	private PlayList guesses;
	private ColorPeg[] colors;
	
	public SubmitGuessCommand(PlayList listOfGuesses, ColorPeg[] colors)
	{
		if(null == listOfGuesses)
			throw new IllegalArgumentException("PlayList cannot be null");
		
		if(null == colors)
			throw new IllegalArgumentException("Colors cannot be null");
		
		this.guesses = listOfGuesses;
		this.colors = colors;
	}
	
	public void execute() 
	{
		Code newGuess = new Code(this.colors);
		this.guesses.addNewCode(newGuess);
		GameLog.getInstance().info("Added new Guess");
	}
	
	public void undo()
	{
		this.guesses.deleteNewCode();
		GameLog.getInstance().info("Removing last Guess");
	}

}
