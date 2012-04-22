package mastermind.core.modes;

import mastermind.core.validation.DuplicatePegsAllowedValidator;
import mastermind.core.validation.DuplicatePegsNotAllowedValidator;
import mastermind.core.validation.ICodeValidator;

/**
 * An expert game mode.
 * 
 * @author Matt Addy <mxa5942@rit.edu>
 *
 */
public class ExpertMode implements IGameMode {

	/**
	 * Get the secret code validator associated with this mode.
	 * 
	 * @return ICodeValidator a validator that does allow duplicate pegs.
	 */
	public ICodeValidator secretCodeValidator() {
		return new DuplicatePegsAllowedValidator();
	}
	
}
