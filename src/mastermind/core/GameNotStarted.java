package mastermind.core;

import mastermind.core.codemaker.ICodemaker;
import mastermind.core.modes.IGameMode;

public class GameNotStarted implements IGameState {

	private GameModel theModel;
	
	public GameNotStarted(GameModel model)
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
	public void setSettings(int gameGuesses, 
							ICodemaker codeMaker,
							IGameMode mode,
							boolean codeBreakerIsComputer, 
							int guessInterval) {
		
		this.theModel.setCodeBreakerAsAI(codeBreakerIsComputer);
		this.theModel.setGuessInterval(guessInterval);
		this.theModel.setCodeMaker(codeMaker);
	}

	@Override
	public void submitGuess(ColorPeg[] code) {
		// TODO Auto-generated method stub
		
	}

}
