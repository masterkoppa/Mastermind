package mastermind.core.controller;

import mastermind.core.Code;
import mastermind.core.ColorPeg;
import mastermind.core.commands.ICommand;

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
	 * 
	 * @param code
	 */
	public void setSecretCode(Code code);

	/**
	 * Create the thread for the AI guesses.
	 */
	public void startAI(int delaySeconds);

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

}
