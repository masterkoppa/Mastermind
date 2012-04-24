package mastermind.core;

/**
 * 
 * @author Andrew Church
 * 
 * This is the State interface
 *
 */
public interface IGameState {

	void setSecretCode(Code c);
	void setSettings(int gameGuesses, boolean codeMakerIsHuman);
	void submitGuess(ColorPeg[] code);
}
