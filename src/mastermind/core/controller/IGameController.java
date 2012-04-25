package mastermind.core.controller;

import mastermind.core.Code;
import mastermind.core.ColorPeg;
import mastermind.core.PlayList;
import mastermind.core.codebreaker.ComputerGuessBehavior;
import mastermind.core.commands.ICommand;
import mastermind.core.modes.IGameMode;
import mastermind.interfaces.Observer;
import mastermind.core.IGameState;

public interface IGameController {

	/**
	 * Execute a command created by this controller. This allows us to add
	 * commands to a history array for easy undo's
	 * 
	 * @param ICommand
	 *            - A command object which takes care of game operations like
	 *            guess submits and logs.
	 */
	public void executeCommand(ICommand command);

	/**
	 * Accepts the colors from the view and crafts a play command (macro
	 * command) which in turn creates the submit guess and provide feedback
	 * commands. After creation, this will execute the commands.
	 */
	public void submitGuess(ColorPeg[] code);
	
	/**
	 * Sets the secret code to that of the arguments
	 * @param code The secret code to set, if the code is invalid this will throw a illegal argument exception, catch it and try again.
	 */
	public void setSecretCode(Code code);

	/**
	 * Create the thread for the AI guesses.
	 */
	public void startAI();

	/**
	 * Kills the computer codebreaker and its thread.
	 */
	public void stopAI();

	public void configureLog(String fileName);// TBD

	/**
	 * Pull a command off the history and run its undo operation. This will just
	 * reverse all operations that the command completed bringing the state of
	 * the game back one move.
	 */
	public void undoCommand();
	
	/**
	 * Notifies the controller that this view is done, and that the game should proceed.
	 * @param e
	 */
	public void stageDone(Observer e);
	
	/**
	 * Gets the current state of the game
	 * @return
	 */
	public IGameState getGameState();
	
	/**
	 * Method to get the playlist model
	 * @return
	 */
	public PlayList getPlaylist();
	
	/**
	 * Method to accept data from views for setting the initial game settings
	 * @param gameGuesses
	 * @param codeMakerIsComputer
	 * @param mode
	 * @param codeBreakerIsComputer
	 * @param guessInterval
	 */
	public void setSettings(int gameGuesses, 
							boolean codeMakerIsComputer,
							IGameMode mode,
							ComputerGuessBehavior guessStrategy, 
							int guessInterval);
	
	/**
	 * Sends a signal to the Game Model to let it know that the game is over.
	 */
	public void triggerNewGame();
}
