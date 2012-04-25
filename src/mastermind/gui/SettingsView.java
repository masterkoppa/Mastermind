package mastermind.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import mastermind.core.controller.*;
import mastermind.core.modes.*;
import mastermind.core.codebreaker.*;

public class SettingsView extends JPanel {

	/**
	 * Generate Serial Version ID
	 */
	private static final long serialVersionUID = 27122833485072815L;

	// GUI Variables
	private JPanel settingsWindow;
	private JButton next;
	private JLabel codeBreakerLabel;
	private JLabel compDifficultyLabel;
	private JLabel guessIntervalLabel;
	private JLabel numGuessesLabel;
	private JCheckBox humanCodemaker;
	private JCheckBox compCodemaker;
	private JCheckBox humanCodebreaker;
	private JCheckBox compCodebreaker;
	private JComboBox modeSelect;
	private JComboBox compCodebreakerSelect;
	private JSlider guessIntervalSlider;
	private JTextField numGuessesField;

	private GridBagConstraints c;

	private IGameController controller;

	public SettingsView(final IGameController controller) {
		super();
		this.controller = controller;

		settingsWindow = new JPanel();

		this.setLayout(new BorderLayout());
		settingsWindow.setLayout(new GridBagLayout());

		// set the constraints for the settings window
		c = new GridBagConstraints();

		// Set everyone to fill their spots
		c.fill = GridBagConstraints.HORIZONTAL;

		// Set some borders, no touching each other....
		c.insets = new Insets(0, 10, 10, 10);

		c.weightx = 1;

		this.buildGameModeRow();
		this.buildCodeMakerRow();
		this.buildCodeBreakerRow();
		this.buildComputerOptions();
		this.buildNumberOfGuessesRow();

		// add the next button
		next = new JButton("NEXT");
		this.add(next, BorderLayout.SOUTH);
		this.add(settingsWindow, BorderLayout.CENTER);

		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String gameModeString = modeSelect.getSelectedItem()
							.toString();
					IGameMode gameMode;

					if (gameModeString == "Novice") {
						gameMode = new NoviceMode();
					} else {
						gameMode = new ExpertMode();
					}

					Boolean codemakerIsComputer = compCodemaker.isSelected();
					Boolean codebreakerIsComputer = compCodebreaker
							.isSelected();

					String difficultyString = compCodebreakerSelect
							.getSelectedItem().toString();
					ComputerGuessBehavior behavior = null;

					if (codebreakerIsComputer) {
						if (difficultyString == "Random") {
							behavior = new RandomGuess(controller);
						}
					}

					int interval = guessIntervalSlider.getValue();
					int numGuesses = Integer.parseInt(numGuessesField.getText());

					System.out.println("Game mode: " + gameModeString);
					System.out.println("Codemaker computer? "
							+ codemakerIsComputer);
					System.out.println("Codebreaker computer? "
							+ codebreakerIsComputer);
					System.out.println("Difficulty: " + difficultyString);
					System.out.println("Interval: " + interval);
					System.out.println("Num guesses: " + numGuesses);

					controller.setSettings(numGuesses, codemakerIsComputer,
							gameMode, behavior, interval);
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane
							.showMessageDialog(SettingsView.this,
									"Please make sure all settings have been set before continuing.");
				}
			}
		});
	}

	private void buildGameModeRow() {
		// add the label for the mode selection
		JLabel modeLabel = new JLabel("Game Mode");

		c.gridx = 0;
		c.gridy = 0;
		settingsWindow.add(modeLabel, c);

		// add the combo box for mode selection
		String[] modes = { "Novice", "Expert" };
		modeSelect = new JComboBox(modes);

		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;

		settingsWindow.add(modeSelect, c);

		c.gridwidth = 1;
	}

	private void buildCodeMakerRow() {

		// add the label for the codemaker selection
		JLabel codeMakerLabel = new JLabel("Code Maker");
		c.gridx = 0;
		c.gridy = 1;
		settingsWindow.add(codeMakerLabel, c);

		ButtonGroup codemakerGroup = new ButtonGroup();

		// add the check box for human codemaker
		humanCodemaker = new JCheckBox("Human");
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		settingsWindow.add(humanCodemaker, c);

		// add the check box for computer codemaker
		compCodemaker = new JCheckBox("Computer");
		c.gridx = 2;
		c.gridy = 1;
		settingsWindow.add(compCodemaker, c);

		codemakerGroup.add(humanCodemaker);
		codemakerGroup.add(compCodemaker);
	}

	private void buildCodeBreakerRow() {
		// set the constraints for the settings window

		// add the label for the codebreaker selection
		codeBreakerLabel = new JLabel("Code Breaker");
		c.gridx = 0;
		c.gridy = 2;
		settingsWindow.add(codeBreakerLabel, c);

		ButtonGroup codebreakerGroup = new ButtonGroup();

		// add the check box for human codebreaker
		humanCodebreaker = new JCheckBox("Human");
		c.gridx = 1;
		c.gridy = 2;
		settingsWindow.add(humanCodebreaker, c);

		// add the check box for computer codemaker
		compCodebreaker = new JCheckBox("Computer");
		c.gridx = 2;
		c.gridy = 2;
		settingsWindow.add(compCodebreaker, c);

		codebreakerGroup.add(compCodebreaker);
		codebreakerGroup.add(humanCodebreaker);

		// Setup the action listeners

		humanCodebreaker.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (humanCodebreaker.isSelected()) {
					System.out.println("System is hiding");
				}

			}

		});

		compCodebreaker.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (compCodebreaker.isSelected()) {

					System.out.println("System is showing");
				}
			}

		});

	}

	private void buildComputerOptions() {
		// add the label for the computer difficulty selection
		compDifficultyLabel = new JLabel("Computer Difficulty");
		c.gridx = 1;
		c.gridy = 3;
		settingsWindow.add(compDifficultyLabel, c);

		// add the combo box for computer difficulty
		String[] compDifficulty = { "Random" };
		compCodebreakerSelect = new JComboBox(compDifficulty);
		c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 1;
		settingsWindow.add(compCodebreakerSelect, c);

		// add the label for the guess interval slider
		guessIntervalLabel = new JLabel("Guess Interval");
		c.gridx = 1;
		c.gridy = 4;
		settingsWindow.add(guessIntervalLabel, c);

		// add the slider for guess interval
		guessIntervalSlider = new JSlider();
		c.gridx = 2;
		c.gridy = 4;
		guessIntervalSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				guessIntervalSlider.setToolTipText(Integer
						.toString(guessIntervalSlider.getValue()));
			}

		});
		settingsWindow.add(guessIntervalSlider, c);

		settingsWindow.validate();
	}

	private void buildNumberOfGuessesRow() {
		// add the label for number of guesses in the game
		numGuessesLabel = new JLabel("Number of Guesses per Game (10-50)");
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		settingsWindow.add(numGuessesLabel, c);

		// add the text field for the number of guesses
		numGuessesField = new JTextField();
		c.gridx = 2;
		c.gridy = 5;
		settingsWindow.add(numGuessesField, c);
	}
}
