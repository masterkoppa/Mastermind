package mastermind.core;

import mastermind.core.codebreaker.ComputerGuessBehavior;
import mastermind.core.codemaker.ICodemaker;
import mastermind.core.modes.IGameMode;

/**
 * 
 * @author Andrew Church
 * 
 * This is the State interface
 *
 */
public interface IGameState {

	/**
	 * Method to set the secret code
	 * @param c code to be set
	 */
	void setSecretCode(Code c);
	
	/**
	 * 
	 * @param code guess to be submitted
	 */
	void submitGuess(ColorPeg[] code);
	
	/**
	 * Configure all the settings of the model
	 * @param gameGuesses The number of guesses allowed in the game
	 * @param codeMaker The code maker to use for setting the secret code
	 * @param mode The mode of the game
	 * @param guessStrategy The strategy to use when making automated guesses
	 * @param guessInterval The amount of time the game should wait between automated guesses
	 */
	void setSettings(int gameGuesses, ICodemaker codeMaker,
			IGameMode mode, ComputerGuessBehavior guessStrategy, int guessInterval);
}
