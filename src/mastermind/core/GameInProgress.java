package mastermind.core;

import mastermind.core.codebreaker.ComputerCodebreaker;
import mastermind.core.codemaker.ICodemaker;
import mastermind.core.modes.IGameMode;

public class GameInProgress implements IGameState {

	@Override
	public void setSecretCode(Code c) {
		throw new IllegalStateException();
	}

	@Override
	public void setSettings(int gameGuesses, ICodemaker codeMaker,
			IGameMode mode, ComputerCodebreaker guessStrategy, int guessInterval) {
		throw new IllegalStateException();
	}

	@Override
	public void submitGuess(ColorPeg[] code) {
		// TODO Auto-generated method stub
	}
}
