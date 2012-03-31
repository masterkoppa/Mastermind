package mastermind.core.commands;

/**
 * IMacroCommand ensures further functionality for a command. It allows
 * for multiple commands to be linked together via the idea of the Macro
 * Command Pattern.
 */
public interface IMacroCommand extends ICommand {

	/** Adds a new command to the bottom of the list of commands to be
	 *  executed */
	void add(ICommand command);
	
	/** Removes the last command in the stack of commands */
	void remove();

}
