package mastermind.core;

import mastermind.interfaces.Observable;
import mastermind.interfaces.Observer;
import mastermind.core.validation.*;
import mastermind.core.codemaker.ICodemaker;
import mastermind.core.modes.*;

/**
 * 
 * @author Andrew Church
 * 
 *         Model class to represent the state of the game
 * 
 */
public class GameModel extends Observable {
	private GameWinner winner;
	private Boolean loggingEnabled;
	private Boolean codebreakerIsAI;
	private IGameMode mode;
	private ICodemaker codemaker;
	private Code secretCode;
	
	private boolean codeMakerDone;

	public GameModel() {
		this.winner = null;
		this.loggingEnabled = false;
		this.codebreakerIsAI = false;
		this.codemaker = null;
		
		this.codeMakerDone = false;
		
		mode = new NoviceMode();
	}

	/**
	 * Get the winner of the current game
	 * 
	 * @return
	 */
	public GameWinner getWinner() {
		return this.winner;
	}

	/**
	 * Return a boolean to indicate whether the game is over
	 * 
	 * @return
	 */
	public Boolean isGameOver() {
		if (null == winner)
			return false;
		else
			return true;
	}

	public Boolean isLoggingEnabled() {
		return this.loggingEnabled;
	}

	public Boolean isCodeBreakerAI() {
		return this.codebreakerIsAI;
	}

	/**
	 * Enables logging and notifies observers
	 */
	public void enableLogging() {
		this.loggingEnabled = true;
		super.dataChanged();
	}

	/**
	 * Sets the winner of the game and notifies observers
	 * 
	 * @param theWinner
	 */
	public void declareWinner(GameWinner theWinner) {
		if (null == theWinner)
			throw new IllegalArgumentException(
					"You must supply a valid winner to declare");

		this.winner = theWinner;
		super.dataChanged();
	}

	/**
	 * Set the object up for a new game
	 */
	public void newGame() {
		this.winner = null;
		this.codeMakerDone = false;
	}

	/**
	 * Configures whether AI has been enabled
	 */
	public void setCodeBreakerAsAI(Boolean ai) {
		this.codebreakerIsAI = ai;
		super.dataChanged();
	}
	
	/**
	 * Set the code maker
	 * @param c
	 */
	public void setCodeMaker(ICodemaker c){
		if(null == c)
			throw new IllegalArgumentException();
		
		this.codemaker = c;
	}
	
	/**
	 * Get the Code maker
	 * @return
	 */
	public ICodemaker getCodeMaker(){
		return this.codemaker;
	}
	
	/**
	 * Set the game mode
	 * @param mode
	 */
	public void setGameMode(IGameMode mode)
	{
		if(null == mode)
			throw new IllegalArgumentException();
		
		this.mode = mode;
	}
	
	/**
	 * Return the currently set game mode
	 * @return
	 */
	public IGameMode getGameMode()
	{
		return this.mode;
	}
	
	/**
	 * Sets the secret code
	 * @param c
	 */
	public void setSecretCode(Code c){
		
		if(null == c)
			throw new IllegalArgumentException();
		
		this.secretCode = c;
	}
	
	/**
	 * Sets the number of guesses allowed in the game
	 * @param numGuesses
	 */
	public void setGuessInterval(int numGuesses){
		//if(numGuesses)
	}

	@Override
	public void register(Observer object) {

		if (null == object)
			throw new IllegalArgumentException("Must supply a valid observer");

		this.observers.add(object);
		super.dataChanged();
	}

	public String getWinningMessage() {
		if (winner == null) {
			return "No Winner";
		} else if (winner == GameWinner.CODEBREAKER) {
			return "The Code has been cracked! Codebreaker Wins!";
		} else if (winner == GameWinner.CODEMAKER) {
			return "The Code was NOT Cracked, Codemaker Wins!";
		} else {
			return "I Don't know who won...";//Some comic relief from long hours
		}
	}
	
	public Boolean validateSecretCode(Code c) {
		
		if(null == c)
			throw new IllegalArgumentException();
		
		ICodeValidator v = this.mode.secretCodeValidator();
		return v.validate(c);
	}
	
	public void setCodeMakerDone(){
		codeMakerDone = true;
		this.dataChanged();
	}
	
	public boolean isCodeMakerDone(){
		return codeMakerDone;
	}
}
