package mastermind.core;

import mastermind.core.codemaker.ICodemaker;
import mastermind.core.modes.IGameMode;

public class SecretCodeSet implements IGameState {

	private GameModel theModel;
	
	public SecretCodeSet(GameModel model)
	{
		if(null == model)
			throw new IllegalArgumentException();
		
		this.theModel = model;
	}
	
	@Override
	public void setSecretCode(Code c) {
		throw new IllegalStateException();
	}

	@Override
	public void setSettings(int gameGuesses, ICodemaker codeMaker,
			IGameMode mode, boolean codeBreakerIsComputer, int guessInterval) {
		throw new IllegalStateException();
	}

	@Override
	public void submitGuess(ColorPeg[] code) {
		throw new IllegalStateException();
	}

}
