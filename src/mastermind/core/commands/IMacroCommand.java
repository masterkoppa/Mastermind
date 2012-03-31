package mastermind.core.commands;

public interface IMacroCommand {

	void Add(ICommand command);
	
	void Remove();
	
	void execute();
}
