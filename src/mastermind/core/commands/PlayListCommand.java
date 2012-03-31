package mastermind.core.commands;
import mastermind.core.ColorPeg;
import mastermind.core.PlayList;

public class PlayListCommand {

	protected PlayList guesses;
	protected ColorPeg[] colors;
	
	public PlayListCommand(PlayList listOfGuesses, ColorPeg[] colors)
	{
		if(null == listOfGuesses)
			throw new IllegalArgumentException("PlayList cannot be null");
		
		if(null == colors)
			throw new IllegalArgumentException("Colors cannot be null");
		
		this.guesses = listOfGuesses;
		this.colors = colors;
	}
}
