package mastermind.core;

import mastermind.core.codebreaker.ComputerGuessBehavior;
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
							ComputerGuessBehavior guessStrategy, 
							int guessInterval) {
		
		if(null != guessStrategy)
		{
			this.theModel.setGuessStrategy(guessStrategy);
			this.theModel.setCodeBreakerAsAI(true);
		}
		this.theModel.setGuessInterval(guessInterval);
		this.theModel.setGuessesAllowed(gameGuesses);
		
		if(null != codeMaker)
		{
			this.theModel.setCodeMaker(codeMaker);
		}
		
		this.theModel.setGameMode(mode);
	}

	@Override
	public void submitGuess(ColorPeg[] code) {
		// TODO Auto-generated method stub
		
	}

}
