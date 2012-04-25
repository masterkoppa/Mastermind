package mastermind.core;

import mastermind.core.codemaker.ICodemaker;
import mastermind.core.modes.IGameMode;
import mastermind.core.codebreaker.ComputerGuessBehavior;

/**
 * The SecretCodeSet concrete state class represents the state in which the game settings have been set,
 * and the secret code has been set.
 * 
 * @author Andrew Church
 *
 */
public class SecretCodeSet implements IGameState {

	@Override
	public void setSecretCode(Code c) {
		throw new IllegalStateException();
	}

	@Override
	public void setSettings(int gameGuesses, ICodemaker codeMaker,
			IGameMode mode, ComputerGuessBehavior guessStrategy,
			int guessInterval) {
		throw new IllegalStateException();
	}

	@Override
	public void submitGuess(ColorPeg[] code) {
		throw new IllegalStateException();
	}

}
