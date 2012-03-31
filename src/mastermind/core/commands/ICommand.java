package mastermind.core.commands;

public interface ICommand {
	
	public void execute();
	
	public void undo();

}
