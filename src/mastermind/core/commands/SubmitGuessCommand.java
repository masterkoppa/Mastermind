package mastermind.core.commands;
import mastermind.core.*;
import mastermind.logging.*;

public class SubmitGuessCommand extends PlayListCommand implements ICommand {
	
	public SubmitGuessCommand(PlayList listOfGuesses, ColorPeg[] colors)
	{
		super(listOfGuesses, colors);
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
