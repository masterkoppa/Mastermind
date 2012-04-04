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
	
	/**
	 * 
	 * Creates the gamecontroller which will maintain a history
	 * of all moves made to allow for undo operations.
	 */
	//TODO Need to remove this and change test cases around.
	public GameController() {
		history = new ArrayList<ICommand>();
		nextUndo = 0;
	}
	
	/**
	 * Creates the gamecontroller which maintains the playlist
	 * for undo operations and the secret code for feedback and
	 * other operations.
	 * 
	 * @param data 
	 * @param secret
	 */
	public GameController(PlayList data, Code secret){
		this(); //Do this to not break compatibility, not yet
		this.dataBackend = data;
		this.secretCode = secret;
		
		System.out.println("Controller Init()");
	}

	/**
	 * Execute a command created by this controller. This allows
	 * us to add commands to a history array for easy undo's
	 * 
	 * @param ICommand - A command object which takes care of 
	 * game operations like guess submits and logs.
	 */
	public void executeCommand(ICommand command) {
		trimHistory();
		command.execute();
		history.add(command);
		nextUndo++;
	}
	
	/**
	 * Pull a command off the history and run its undo operation.
	 * This will just reverse all operations that the command
	 * completed bringing the state of the game back one move.
	 */
	public void undoCommand() {
		// NOTE: We're using nextUndo - 1 as as index into the history, since
		// our nextUndo counter is at 1 when one element gets added
		if (nextUndo >= 0) {
			ICommand command = history.get(nextUndo - 1);
			command.undo();
			nextUndo--;
		}
	}
	
	/**
	 * @return ArrayList<Icommand> - Returns all commands that
	 * have been completed at the time of the call.
	 */
	public ArrayList<ICommand> getHistory() {
		return history;
	}
	
	/**
	 * After undo commands have been completed, the commands still
	 * exist to allow for redo operations. When a new command is
	 * run, this method will delete all possible redo's to allow
	 * for the new command to be added.
	 */
	private void trimHistory() {
		for(int i = nextUndo; i < history.size(); i++) {
			history.remove(i);
		}
	}

	/**
	 * Accepts the colors from the view and crafts a play command
	 * (macro command) which in turn creates the submit guess and provide
	 * feedback commands. After creation, this will execute the commands.
	 */
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

	/**
	 * Create the thread for the AI guesses.
	 */
	@Override
	public void startAI(int delaySeconds) {
		computerCodebreaker = new ComputerCodebreaker(delaySeconds*1000, new RandomGuess(this));
		computerCodebreaker.start();
	}

	/**
	 * Kills the computer codebreaker and its thread.
	 */
	@Override
	public void stopAI() {
		computerCodebreaker.stop();
	}

	@Override
	public void configureLog() {
		// TODO Auto-generated method stub
		
	}
	
}
