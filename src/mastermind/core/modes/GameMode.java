package mastermind.core.modes;

import mastermind.core.validation.*;

/**
 * An interface that represents the mode a game can run in.
 *
 * @author Matt Addy <mxa5942@rit.edu>
 * 
 */
public interface GameMode {

	/**
	 * Get the secret code validator.
	 * 
	 * @return ICodeValidator the secret code validator.
	 */
	public ICodeValidator secretCodeValidator();
	
}
