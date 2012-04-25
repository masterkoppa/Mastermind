package mastermind.core;

import mastermind.core.codebreaker.ComputerGuessBehavior;
import mastermind.core.codemaker.ICodemaker;
import mastermind.core.modes.IGameMode;

/**
 * The GameInProgress concrete state class represents the state in which the game is being played.
 * Guesses and feedback are occuring in this state.
 * 
 * @author Andrew Church
 *
 */
public class GameInProgress implements IGameState {

	@Override
	public void setSecretCode(Code c) {
		throw new IllegalStateException();
	}

	@Override
	public void setSettings(int gameGuesses, ICodemaker codeMaker,
			IGameMode mode, ComputerGuessBehavior guessStrategy, int guessInterval) {
		throw new IllegalStateException();
	}

	@Override
	public void submitGuess(ColorPeg[] code) {
		// TODO Auto-generated method stub
	}
}
