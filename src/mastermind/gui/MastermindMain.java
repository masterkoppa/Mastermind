package mastermind.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FileExistsException;

import mastermind.core.ColorPeg;
import mastermind.core.GameModel;
import mastermind.core.PlayList;
import mastermind.core.commands.ConfigureLogForFileCommand;
import mastermind.core.controller.IGameController;
import mastermind.interfaces.Observer;

public class MastermindMain implements Observer {

	// MODELS AND CONTROLLERS
	private PlayList dataBackend;
	private GameModel currentGame;
	private IGameController controller;
	private int selectedDelay;
	private boolean newGameSelected;
	private boolean gameIsOver;

	// GUI VARIABLES
	private JPanel mainWindow;
	private MastermindBoard board;
	private JButton submit;
	private JButton undo;
	private JButton newGame;
	private JSlider delaySelector;
	private JLabel delayLabel;
	private JCheckBox computer;

	public MastermindMain(IGameController controller, PlayList model,
			GameModel theGame) {
		// setLookAndFeel();

		this.dataBackend = model;
		this.currentGame = theGame;
		this.controller = controller;
		this.selectedDelay = 30;
		this.newGameSelected = false;

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
			computer.setEnabled(false);
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
		JPanel checkPanel = new JPanel(new GridBagLayout());
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
				newGameSelected = true;
			}

		});

		computer = new JCheckBox("Computer Code Breaker");

		computer.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				int newState = e.getStateChange();

				if (newState == ItemEvent.DESELECTED) {
					submit.setEnabled(true);
					undo.setEnabled(true);
					delaySelector.setEnabled(true);

					controller.stopAI();
				} else {
					submit.setEnabled(false);
					undo.setEnabled(false);
					delaySelector.setEnabled(false);

					controller.startAI(selectedDelay);
				}
			}
		});

		delayLabel = new JLabel("30 s");

		delaySelector = new JSlider(1, 30, 30);

		delaySelector.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider j = (JSlider) e.getSource();

				NumberFormat number = NumberFormat.getInstance();
				number.setMinimumIntegerDigits(2);

				selectedDelay = j.getValue();
				delayLabel.setText(number.format(j.getValue()) + " s");
			}

		});

		JCheckBox logging = new JCheckBox("Logging Enabled");

		logging.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JCheckBox l = (JCheckBox) e.getSource();

				if (!l.isSelected()) {
					// Disable the logging
				} else {

					// Enable Logging

					// The file name where the user want's the file stored
					String fileName = "";

					// Open a File Chooser

					int result = JFileChooser.ERROR_OPTION;

					JFileChooser file = new JFileChooser();

					file.setFileFilter(new FileFilter() {

						@Override
						public boolean accept(File f) {
							if (f.getName().endsWith(".txt") || f.isDirectory()) {
								return true;
							} else {
								return false;
							}
						}

						@Override
						public String getDescription() {
							return "Text File (.txt)";
						}

					});

					result = file.showSaveDialog(mainWindow);

					// If the user cancels the file picker
					if (result == JFileChooser.CANCEL_OPTION) {
						l = (JCheckBox) e.getSource();
						return;
					} else {
						File f = file.getSelectedFile();

						l = (JCheckBox) e.getSource();
						l.setSelected(true);

						fileName = f.getPath();
					}

					// Start configuring the logging
					ConfigureLogForFileCommand logger = new ConfigureLogForFileCommand(
							fileName, false);

					try {
						// Try to save the log in place
						logger.execute();
					} catch (FileExistsException e1) {
						// The file already exists aske the user what it wants
						// to do
						int response = JOptionPane
								.showConfirmDialog(mainWindow,
										"File Already Exists, Please Pick Another file");

						// Yes Overriwrite?
						if (response == JOptionPane.YES_OPTION) {
							logger = new ConfigureLogForFileCommand(fileName,
									true);
							try {
								logger.execute();
							} catch (FileExistsException e2) {
								System.err
										.println("I say I want to override, why wont you let me IO");
								l.setSelected(false);
								e2.printStackTrace();
							} catch (IOException e2) {
								JOptionPane
										.showMessageDialog(mainWindow,
												"Unknown IO Exception, please try again");
								l.setSelected(false);
								e2.printStackTrace();
							}
						} else {
							// Cancel or No
							l.setSelected(false);
						}
					} catch (IOException e1) {
						// IO Exception, let the user try again
						JOptionPane.showMessageDialog(mainWindow,
								"Unknown IO Exception, please try again");
						e1.printStackTrace(); // Print error message for
												// debugging purposes from the
												// user
						l.setSelected(false);
					}
				}

			}

		});

		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;

		checkPanel.add(computer, c);

		c.gridx = 1;
		c.insets = new Insets(0, 20, 0, 10);

		checkPanel.add(delayLabel, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.insets = new Insets(20, 0, 40, 0);

		checkPanel.add(delaySelector, c);

		c.gridy = 2;
		c.insets = new Insets(0, 0, 50, 0);

		checkPanel.add(logging, c);

		buttonPanel.add(submit);
		buttonPanel.add(undo);

		// Setup the gridbag layout options

		c = new GridBagConstraints();

		// Check Panel Settings
		c.gridx = 0;
		c.gridy = 0;

		options.add(checkPanel, c);

		// Button Panel Settings
		c.gridx = 0;
		c.gridy = 1;

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
