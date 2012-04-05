package mastermind.core.controller;

import mastermind.core.*;
import mastermind.core.codebreaker.*;
import mastermind.core.commands.*;
import mastermind.interfaces.Observer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Handles requests made from the gui. Creates command objects based on these
 * requests and then executes them while maintaining a history of all commands
 * executed.
 */
public class GameController implements IGameController, Observer {
	private ArrayList<ICommand> history;
	private int nextUndo;

	private ComputerCodebreaker computerCodebreaker;

	private GameModel game;
	private PlayList dataBackend;
	private Code secretCode;

	/**
	 * 
	 * Creates the gamecontroller which will maintain a history of all moves
	 * made to allow for undo operations.
	 */
	// TODO Need to remove this and change test cases around.
	public GameController() {
		history = new ArrayList<ICommand>();
		nextUndo = 0;
	}

	/**
	 * Creates the gamecontroller which maintains the playlist for undo
	 * operations and the secret code for feedback and other operations.
	 * 
	 * @param data
	 * @param secret
	 */
	public GameController(GameModel theGame, PlayList data, Code secret) {
		this(); // Do this to not break compatibility, not yet
		this.game = theGame;
		this.game.register(this);
		this.dataBackend = data;
		this.secretCode = secret;

	}

	@Override
	public void executeCommand(ICommand command) {
		trimHistory();
		command.execute();
		history.add(command);
		nextUndo++;
	}

	@Override
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
	 * @return ArrayList<Icommand> - Returns all commands that have been
	 *         completed at the time of the call.
	 */
	public ArrayList<ICommand> getHistory() {
		return history;
	}

	/**
	 * After undo commands have been completed, the commands still exist to
	 * allow for redo operations. When a new command is run, this method will
	 * delete all possible redo's to allow for the new command to be added.
	 */
	private void trimHistory() {
		for (int i = nextUndo; i < history.size(); i++) {
			history.remove(i);
		}
	}

	@Override
	public void submitGuess(ColorPeg[] code) {

		Code test = new Code(code);

		if (test.isEmpty()) {
			throw new IllegalArgumentException(
					"The code submitted is not complete, please validate.");
		}

		IMacroCommand play = new PlayCommand();
		try {
			play.add(new SubmitGuessCommand(dataBackend, code));
		} catch (Exception ie) {
			ie.printStackTrace();
			return;// Cancel this action if there is an error
		}
		try {
			play.add(new ProvideFeedbackCommand(this.game, dataBackend,
					secretCode.getPegs()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.executeCommand(play);
	}

	@Override
	public void startAI(int delaySeconds) {
		computerCodebreaker = new ComputerCodebreaker(delaySeconds * 1000,
				new RandomGuess(this));
		computerCodebreaker.start();
		this.game.setCodeBreakerAsAI(true);
	}

	@Override
	public void stopAI() {
		if(computerCodebreaker != null)
			computerCodebreaker.stop();
		
		this.game.setCodeBreakerAsAI(false);
	}

	@Override
	public void configureLog(String fileName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyChange() {
		if (this.game.isGameOver() && this.game.isCodeBreakerAI()) {
			this.stopAI();
		}
	}

	@Override
	public void register() {
		// TODO Auto-generated method stub

	}

}
