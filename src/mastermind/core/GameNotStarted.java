package mastermind.core;

import mastermind.core.codebreaker.ComputerGuessBehavior;
import mastermind.core.codemaker.ICodemaker;
import mastermind.core.modes.IGameMode;

/**
 * The GameNotStarted concrete state class represents the state in which the game has not been started.
 * In this state, settings may be set
 * 
 * @author Andrew Church
 *
 */
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
		this.theModel.setCodeMaker(codeMaker);
		this.theModel.setGameMode(mode);
	}

	@Override
	public void submitGuess(ColorPeg[] code) {
		throw new IllegalStateException();
	}

}
