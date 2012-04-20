package mastermind.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mastermind.core.ColorPeg;
import mastermind.core.GameModel;
import mastermind.core.PlayList;
import mastermind.core.controller.IGameController;
import mastermind.interfaces.INotifiable;
import mastermind.interfaces.Observer;

public class MastermindMain implements Observer {

	// MODELS AND CONTROLLERS
	private PlayList dataBackend;
	private GameModel currentGame;
	private IGameController controller;
	private INotifiable mainGame;
	private boolean newGameSelected;
	private boolean gameIsOver;

	// GUI VARIABLES
	private JPanel mainWindow;
	private MastermindBoard board;
	private JButton submit;
	private JButton undo;
	private JButton newGame;

	public MastermindMain(IGameController controller, PlayList model,
			GameModel theGame, INotifiable mainGame) {
		this.dataBackend = model;
		this.currentGame = theGame;
		this.controller = controller;
		this.newGameSelected = false;
		this.mainGame = mainGame;

		mainWindow = new JPanel();// Set the JFrame with the window title

		mainWindow.setLayout(new BorderLayout());

		board = new MastermindBoard(model);

		mainWindow.add(board, BorderLayout.CENTER);
		mainWindow.add(this.generateOptions(), BorderLayout.EAST);

		// Register after initializing everything
		this.register();
	}

	@Override
	public void register() {
		this.dataBackend.register(this);
		this.currentGame.register(this);
	}

	@Override
	public void notifyChange() {
		// Revalidate the internal board
		board.invalidate();
		board.revalidate();

		// Check if the game is over
		if (currentGame.isGameOver()) {
			board.gameIsOver();
			submit.setEnabled(false);
			undo.setEnabled(false);
			showWinningMessage(currentGame.getWinningMessage());
		}
	}

	/**
	 * Show a pop-up window saying that the game is over
	 * 
	 * @param winningMessage
	 */
	private void showWinningMessage(String winningMessage) {
		if (gameIsOver != true)
			JOptionPane.showMessageDialog(mainWindow, winningMessage);
		gameIsOver = true;
	}

	/**
	 * Return a panel with the options in it, simply to separate the logic into
	 * consise parts
	 * 
	 * @return Built JPanel
	 */
	private JPanel generateOptions() {
		JPanel options = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout());

		submit = new JButton("Submit");

		// Action Listener to submit the code
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Input Validation
				ColorPeg[] code = board.getLastGuess();
				try {
					controller.submitGuess(code);
				} catch (IllegalArgumentException ex) {
					JOptionPane
							.showMessageDialog(mainWindow,
									"The Code you tried to submit is not valid, please try again");
				}
			}

		});

		undo = new JButton("Undo");
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (board.isBeingEdited()) {
					board.clearLastRow();
					notifyChange();
				} else {
					controller.undoCommand();
				}
			}
		});

		newGame = new JButton("New Game");
		newGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainGame.Notify();
				newGameSelected = true;
			}

		});

		JCheckBox logging = new JCheckBox("Logging Enabled");

		logging.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JCheckBox l = (JCheckBox) e.getSource();
				boolean returnValue = LogfilePickerGenerator
						.generateAndShow(mainWindow);

				l.setSelected(returnValue);

			}
		});

		

		// Setup the gridbag layout options
		GridBagConstraints c = new GridBagConstraints();

		// Check Panel Settings
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 40, 0);

		options.add(logging, c);

		// Button Panel Settings
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0,0,0,0);
		
		buttonPanel.add(submit);
		buttonPanel.add(undo);
		options.add(buttonPanel, c);

		c.gridy = 2;

		options.add(newGame, c);

		return options;
	}

	/**
	 * Returns the mainWindow view
	 * 
	 * @return Main View
	 */
	public JPanel getView() {
		return mainWindow;
	}

	/**
	 * Is this view done, and no longer needed
	 * 
	 * @return Do we need to dispose of this view
	 */
	public boolean isDone() {
		return newGameSelected;
	}

	/**
	 * Dispose of the window, to avoid any problems with leaking memory
	 */
	public void dispose() {
		mainWindow.invalidate();
		gameIsOver = true;
	}

}
