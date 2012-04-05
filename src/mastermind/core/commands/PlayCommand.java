package mastermind.core.commands;

import java.util.*;

/**
 * PlayCommand holds a list of various commands that need to be executed. These
 * commands take care of functions such as Submit guess and request feedback.
 */
public class PlayCommand implements IMacroCommand {

	/** List of commands to be executed */
	private ArrayList<ICommand> commands;

	/** Initializes the container for commands */
	public PlayCommand() {
		commands = new ArrayList<ICommand>();
	}

	/**
	 * Adds a new command to the stack
	 */
	@Override
	public void add(ICommand command) {
		if (null == command)
			throw new IllegalArgumentException("Must supply a valid command");

		this.commands.add(command);
	}

	/**
	 * Removes the most recently added command from the stack
	 */
	@Override
	public void remove() {
		if (this.commands.size() > 0)
			this.commands.remove(this.commands.size() - 1);
	}

	/**
	 * Executes all the commands in the stack, in the order that they were added
	 * (First In, First Executed)
	 */
	@Override
	public void execute() {
		for (ICommand c : this.commands) {
			c.execute();
		}
	}

	/**
	 * Undoes all the commands in the stack, in the reverse order that they were
	 * added (Last In, First Undone)
	 */
	@Override
	public void undo() {
		for (int count = this.commands.size() - 1; count >= 0; count--) {
			this.commands.get(count).undo();
		}
	}

	/**
	 * Returns the number of commands in the stack
	 */
	@Override
	public int stackSize() {
		return this.commands.size();
	}

}
