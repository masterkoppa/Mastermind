package mastermind.core.controller;

import mastermind.core.*;
import mastermind.core.codebreaker.*;
import mastermind.core.codemaker.ComputerCodemaker;
import mastermind.core.codemaker.ICodemaker;
import mastermind.core.commands.*;
import mastermind.core.modes.IGameMode;
import mastermind.gui.CodeMakerPanel;
import mastermind.interfaces.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

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
	private IGameState state;
	private PlayList dataBackend;
	private Code secretCode;

	/**
	 * 
	 * Creates the gamecontroller which will maintain a history of all moves
	 * made to allow for undo operations.
	 */
	@Deprecated
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
	@Deprecated
	public GameController(GameModel theGame, PlayList data, Code secret) {
		this(); // Do this to not break compatibility, not yet
		this.game = theGame;
		this.game.register(this);
		this.dataBackend = data;
		this.secretCode = secret;

	}
	
	/**
	 * USE THIS ONE
	 * @param theGame
	 * @param data
	 */
	public GameController(GameModel theGame, PlayList data){
		this.game = theGame;
		this.dataBackend = data;
		this.game.register(this);
		this.state = new GameNotStarted(this.game);
		history = new ArrayList<ICommand>();
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
		if (nextUndo >= 1) {
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
		
		if(this.dataBackend.getNumGuesses() == 1)
			this.state = new GameInProgress();
	}
	
	public void setSecretCode(Code c){
		if(null == c)
			throw new IllegalArgumentException();
		
		if(!this.game.validateSecretCode(c))
			throw new IllegalArgumentException();
		
		this.state.setSecretCode(c);
		this.state = new SecretCodeSet();
	}
	
	public void startAI() {
		computerCodebreaker = new ComputerCodebreaker(this.game.getGuessInterval() * 1000,
				this.game.getGuessStrategy());
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
	
	public void stageDone(Observer e){
		if(e instanceof CodeMakerPanel){
			try{
				this.game.setCodeMakerDone();
			}catch(ConcurrentModificationException ex){
				//This is to be expected... sadly
			}
			System.out.println("Stage Done!");
		}
	}
	
	public IGameState getGameState()
	{
		return this.state;
	}
	
	public PlayList getPlaylist()
	{
		return this.dataBackend;
	}
	
	public void setSettings(int gameGuesses, 
			boolean codeMakerIsComputer,
			IGameMode mode,
			ComputerGuessBehavior guessStrategy, 
			int guessInterval){
		
		ICodemaker codeMaker = codeMakerIsComputer ? new ComputerCodemaker(this) : null;
		
		this.state.setSettings(gameGuesses, codeMaker, mode, guessStrategy, guessInterval);
		this.state = new SettingsSelected(this.game);
	}
}
