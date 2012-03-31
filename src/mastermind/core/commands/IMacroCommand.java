package mastermind.core.commands;

public interface IMacroCommand {

	//Adds a new command to the bottom of the list of commands
	void add(ICommand command);
	
	//Removes the last command in the stack
	void remove();
	
	//Iterates through all commands in order and executes each
	void execute();
	
	//Iterates through the stack in reverse order and calls undo on each
	void undo();
}
