package mastermind.core.controller;

import mastermind.core.commands.ICommand;

public interface IGameController {
	
	public void executeCommand(ICommand command);
	
	public void undoCommand();
	
}
