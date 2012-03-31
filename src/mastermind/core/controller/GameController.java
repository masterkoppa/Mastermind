package mastermind.core.controller;

import mastermind.core.commands.ICommand;
import java.util.ArrayList;

public class GameController implements IGameController {
	private ArrayList<ICommand> history;
	private int nextUndo;
	
	public GameController() {
		history = new ArrayList<ICommand>();
		nextUndo = 0;
	}

	public void executeCommand(ICommand command) {
		command.execute();
		history.add(command);
		nextUndo++;
	}
	
	public void undoCommand() {
		
	}
	
}
