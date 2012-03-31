package mastermind.core.commands;
import mastermind.core.*;

public class SubmitGuessCommand implements ICommand {

	private PlayList guesses;
	private ColorPeg[] colors;
	
	public SubmitGuessCommand(PlayList listOfGuesses, ColorPeg[] colors)
	{
		this.guesses = listOfGuesses;
		this.colors = colors;
	}
	
	public void execute() 
	{
		Code newGuess = new Code(this.colors);
		this.guesses.addNewCode(newGuess);
	}
	
	public void undo()
	{
		
	}

}
