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
		trimHistory();
		command.execute();
		history.add(command);
		nextUndo++;
	}
	
	public void undoCommand() {
		// NOTE: We're using nextUndo - 1 as as index into the history, since
		// our nextUndo counter is at 1 when one element gets added
		if (nextUndo >= 0) {
			ICommand command = history.get(nextUndo - 1);
			command.undo();
			nextUndo--;
		}
	}
	
	public ArrayList<ICommand> getHistory() {
		return history;
	}
	
	private void trimHistory() {
		for( int i = nextUndo; i < history.size(); i++ ) {
			history.remove(i);
		}
	}
	
}
