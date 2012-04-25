package mastermind.core;

import mastermind.core.codebreaker.ComputerCodebreaker;
import mastermind.core.codemaker.ICodemaker;
import mastermind.core.modes.IGameMode;

public class SettingsSelected implements IGameState {
	
	private GameModel theModel;
	
	public SettingsSelected(GameModel model)
	{
		if(null == model)
			throw new IllegalArgumentException();
		
		this.theModel = model;
	}

	@Override
	public void setSecretCode(Code c) {
		
		if(null == c)
			throw new IllegalArgumentException();
		
		theModel.setSecretCode(c);
	}

	@Override
	public void setSettings(int gameGuesses, ICodemaker codeMaker,
			IGameMode mode, ComputerCodebreaker guessStrategy, int guessInterval) {
		throw new IllegalStateException();
	}

	@Override
	public void submitGuess(ColorPeg[] code) {
		throw new IllegalStateException();
	}

}
