package mastermind.core.commands;

import mastermind.core.ColorPeg;
import mastermind.core.PlayList;

/**
 * 
 * @author Andrew Church
 * 
 *         Super class for any commands that need to accept a PlayList and a
 *         ColorPeg array as arguments for the constructor.
 * 
 */
public class PlayListCommand {

	protected PlayList guesses;
	protected ColorPeg[] colors;

	public PlayListCommand(PlayList listOfGuesses, ColorPeg[] colors) {
		if (null == listOfGuesses)
			throw new IllegalArgumentException("PlayList cannot be null");

		if (null == colors)
			throw new IllegalArgumentException("Colors cannot be null");

		this.guesses = listOfGuesses;
		this.colors = colors;
	}
}
