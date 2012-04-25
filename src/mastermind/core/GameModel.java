package mastermind.core;

import mastermind.interfaces.Observable;
import mastermind.interfaces.Observer;
import mastermind.core.validation.*;
import mastermind.core.codebreaker.ComputerGuessBehavior;
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

	private IGameMode mode;
	private ICodemaker codemaker;
	private ComputerGuessBehavior guessStrategy;
	private Code secretCode;
	private int guessesAllowed;
	private int guessInterval;
	private boolean loggingEnabled;
	private boolean codebreakerIsAI;
	private boolean forceGameOver;
	
	private boolean codeMakerDone;

	public GameModel() {
		this.winner = null;
		this.loggingEnabled = false;
		this.codebreakerIsAI = false;
		this.forceGameOver = false;
		this.codemaker = null;
		
		this.codeMakerDone = false;
		
		mode = new NoviceMode();
		this.guessesAllowed = 10;
		this.guessInterval = 5;
		this.guessStrategy = null;
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
		if (null == winner && !this.forceGameOver)
			return false;
		else
			return true ;
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
		super.dataChanged();
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
		super.dataChanged();
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
		super.dataChanged();
	}
	
	public Code getSecretCode() {
		return this.secretCode;
	}
	
	public void setGuessInterval(int guessInterval) {
		if(guessInterval < 1)
			throw new IllegalArgumentException();
		
		this.guessInterval = guessInterval;
		super.dataChanged();
	}
	
	public int getGuessInterval() {
		return this.guessInterval;
	}
	
	/**
	 * Sets the number of guesses allowed in the game
	 * @param numGuesses
	 */
	public void setGuessesAllowed(int numGuesses){
		if(numGuesses < 10)
			throw new IllegalArgumentException();
		
		this.guessesAllowed = numGuesses;
		super.dataChanged();
	}
	
	public int getGuessesAllowed(){
		return this.guessesAllowed;
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
	
	/**
	 * Sets the desired automated guess strategy for the game
	 * @param strategy The type of strategy to use
	 */
	public void setGuessStrategy(ComputerGuessBehavior strategy) {
		if(null == strategy)
			throw new IllegalArgumentException();
		
		this.guessStrategy = strategy;
		super.dataChanged();
	}
	
	/**
	 * Returns the selected automated guess strategy
	 */
	public ComputerGuessBehavior getGuessStrategy(){
		return this.guessStrategy;
	}
	
	public void triggerNewGame(){
		this.forceGameOver = true;
		super.dataChanged();
	}
}
