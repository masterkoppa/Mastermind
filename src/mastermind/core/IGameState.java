package mastermind.core;

import mastermind.core.codebreaker.ComputerCodebreaker;
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

	void setSecretCode(Code c);
	void submitGuess(ColorPeg[] code);
	void setSettings(int gameGuesses, ICodemaker codeMaker,
			IGameMode mode, boolean codeBreakerIsComputer, int guessInterval);
}
