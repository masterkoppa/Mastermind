package mastermind.core.commands;

public interface IMacroCommand {

	void add(ICommand command);
	
	void remove();
	
	void execute();
	
	void undo();
}
