package mastermind.core.validation;

import mastermind.core.Code;

/**
 * 
 * @author Andrew Church
 * 
 *         Public interface for code validation strategy
 * 
 */
public interface ICodeValidator {

	/**
	 * Definition of validation method
	 * 
	 * @param c
	 * @return whether the code is valid
	 */
	Boolean validate(Code c);

}
