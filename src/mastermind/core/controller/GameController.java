package mastermind.core.controller;

import mastermind.core.*;
import mastermind.core.codebreaker.*;
import mastermind.core.commands.*;

import java.io.IOException;
import java.util.ArrayList;

public class GameController implements IGameController {
	private ArrayList<ICommand> history;
	private int nextUndo;
	
	private ComputerCodebreaker computerCodebreaker;
	
	private PlayList dataBackend;
	private Code secretCode;
	
	public GameController() {
		history = new ArrayList<ICommand>();
		nextUndo = 0;
	}
	
	public GameController(PlayList data, Code secret){
		this(); //Do this to not break compatibility, not yet
		this.dataBackend = data;
		this.secretCode = secret;
		
		System.out.println("Controller Init()");
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
		for(int i = nextUndo; i < history.size(); i++) {
			history.remove(i);
		}
	}

	@Override
	public void submitGuess(ColorPeg[] code) {
		IMacroCommand play = new PlayCommand();
		try {
			play.add(new SubmitGuessCommand(dataBackend, code));
		} catch(Exception ie) {
			ie.printStackTrace();
			return;//Cancel this action if there is an error
		}
		try {
			play.add(new ProvideFeedbackCommand(dataBackend, secretCode.getPegs()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.executeCommand(play);
	}

	@Override
	public void startAI(int delaySeconds) {
		computerCodebreaker = new ComputerCodebreaker(delaySeconds*1000, new RandomGuess(this));
		computerCodebreaker.start();
	}

	@Override
	public void stopAI() {
		computerCodebreaker.stop();
	}

	@Override
	public void configureLog() {
		// TODO Auto-generated method stub
		
	}
	
}
