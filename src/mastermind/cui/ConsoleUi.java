package mastermind.cui;

import java.io.*;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import mastermind.core.*;
import mastermind.core.codebreaker.ComputerGuessBehavior;
import mastermind.core.codebreaker.RandomGuess;
import mastermind.core.codemaker.*;
import mastermind.core.commands.ProvideFeedbackCommand;
import mastermind.core.commands.SubmitGuessCommand;
import mastermind.core.controller.GameController;
import mastermind.core.controller.IGameController;
import mastermind.core.modes.*;
import mastermind.interfaces.Observer;
import mastermind.core.Code;

/**
 * Implements a console interface to Mastermind
 * 
 */
public class ConsoleUi implements Observer {

	private final static int MAX_NUMBER_OF_GUESSES = 10;
	private final static int COMPUTER_INTERVAL = 3;

	ArrayList<String> availableColors;
	private GameModel theGame;
	private IGameController gameController;
	private PlayList data;

	private boolean waitingState;

	public ConsoleUi() {
		this.availableColors = new ArrayList<String>();

		waitingState = false;

		// Get all the available colors for code in the system
		for (ColorPeg i : ColorPeg.values()) {
			availableColors.add(i.getShortName());
		}
		// Initialize the main game model, this model is persistent
		// through all the games
		theGame = new GameModel();
		data = new PlayList(MAX_NUMBER_OF_GUESSES);
		data.register(this);
		gameController = new GameController(theGame, data);
		this.register();
		run();

	}

	private void run() {
		printBoxedTitle("Welcome to Mastermind!");
		setSettings();
		playGame();
	}

	private void restartGame() {
		gameController = null;
		data = null;
		theGame.newGame();
		gameController = new GameController(theGame, data);
		data = new PlayList(10); // CHANGE TO GET INPUT
		data.register(this);
		printBoxedTitle("Restarted Game!");
		playGame();
	}

	@SuppressWarnings("deprecation")
	private void playGame() {
		boolean secretCodeSubmitted = false;

		// Get secret code
		do {
			try {
				setSecretCode();
				secretCodeSubmitted = true; // SUCESS!
			} catch (IllegalArgumentException ex) {
				System.out.println("ERROR: Please try again!");
			}
		} while (!secretCodeSubmitted);

		// Start asking the user for input
		for (int i = 0; i < MAX_NUMBER_OF_GUESSES; i++) {
			// Prepare the timeout timer
			Thread timer = new Thread(new Runnable() {

				@Override
				public void run() {
					synchronized (this) {
						try {
							this.wait(30000);
						} catch (InterruptedException e) {
						}
						theGame.triggerNewGame();
					}
				}
			});

			timer.start();

			String[] code = getCode("Guess #" + (i + 1), Code.NUM_OF_PEGS);

			// This means that the timer ran out
			if (code == null) {
				break;
			}

			timer.stop();

			Code guess = this.stringArrayToCode(code);

			// Since undo isn't required were sending everything through to
			// the controller.
			waitingState = true;
			try {
				SubmitGuessCommand command = new SubmitGuessCommand(data,
						guess.getPegs());
				command.execute();
			} catch (IOException e) {
				System.err.println("There was a problem please try some"
						+ " other time, the program will now exit.");
				System.exit(2);
			}

			try {
				ProvideFeedbackCommand command = new ProvideFeedbackCommand(
						theGame, data, theGame.getSecretCode().getPegs());
				command.execute();
			} catch (IOException e) {
				System.err.println("There was a problem please try some"
						+ " other time, the program will now exit.");
				System.exit(2);
			}

			// Since the Observables dispatch events on a separate thread, to
			// avoid multiple streams messing with each other, we wait for 1
			// second
			synchronized (this) {
				/*
				 * We'll try to make sure this is executed in a synchronized
				 * manner with the rest of the program.
				 * 
				 * IDEALLY, while this waits the model should be updated and
				 * when this wakes up things are back to normal
				 */
				try {
					this.wait(1000);
				} catch (InterruptedException e) {
					// GOOD we stop here
				}
			}

			if (theGame.isGameOver()) {
				break;
			}

		}

		System.out.println();
		System.out.println();
		System.out.println("The game is over, what would you like to do know?");
		System.out.print("(New, Restart, Exit)");

		try {
			String input = (new BufferedReader(new InputStreamReader(System.in)))
					.readLine();
		} catch (IOException e) {
		}

		// Temp, while we get everything working fine
		System.out.println("Goodbye!");

		// If computer player -- Start timer and listen.
		// If human -- prompt for guess
		// Notified of guess feedback
		// Prompt for another guess.
	}

	/**
	 * Shows a user-selectable menu with the given title and menu items.
	 * 
	 * @param titleText
	 *            The text to display at the top of the menu.
	 * @param items
	 *            The items to display in the menu.
	 * @return The index of the selected item.
	 */
	private int showMenu(String titleText, Object[] items) {
		int result = -1;
		while (result < 0) {
			System.out.println();
			System.out.println(titleText);
			System.out.println();

			for (int i = 0; i < items.length; i++) {
				System.out.printf("  %1$d. %2$s\r\n", i + 1, items[i]);
			}
			System.out.println();
			System.out.print("Select an option: ");
			try {
				String input = (new BufferedReader(new InputStreamReader(
						System.in))).readLine();
				if (input == null || input.isEmpty())
					result = 0;
				else
					result = Integer.parseInt(input) - 1;
				if (result > items.length)
					result = -1;
			} catch (Exception ex) {
				result = -1;
			}

			if (result < 0 || result > items.length) {
				System.out.println("\r\nInvalid option."
						+ " Please enter a valid number.");
				result = -1;
			}
		}
		return result;
	}

	/**
	 * Gets a code prompt from the user
	 * 
	 * @param titleText
	 *            The title to show before asking for input
	 * @param numOfPegs
	 *            The number of inputs you are expecting
	 * @return The valid string representing the code
	 */
	private String[] getCode(String titleText, int numOfPegs) {
		String[] code;
		for (;;) {
			if (theGame.isGameOver()) {
				return null;
			}
			System.out.println();
			System.out.println(titleText);
			System.out.println();

			System.out.println();
			System.out.print("Enter a " + numOfPegs
					+ " peg code(Ex:rd rd gr gr): ");

			try {
				String input = (new BufferedReader(new InputStreamReader(
						System.in))).readLine();
				if (input == null || input.isEmpty())
					continue;
				code = input.split(" ");

			} catch (Exception ex) {
				continue;
			}
			boolean valid = true;
			if (code.length > numOfPegs)
				valid = false;
			for (String a : code) {
				if (!this.availableColors.contains(a))
					valid = false;
			}
			if (!valid) {
				System.out
						.println("\r\nInvalid option. Please enter a valid number.");
				continue;
			}
			break;
		}
		return code;
	}

	/**
	 * Get the secret code in a way that no one can see it, eclipse doesn't like
	 * this so make sure to disable that when in development mode.
	 * 
	 * @param titleText
	 *            The text to show before asking the uses for the code
	 * @param numOfPegs
	 *            The number of pegs to accept
	 * @return A string array containing the user input, the receiving method
	 *         must take care of validating this and what not.
	 */
	private String[] getHiddenCode(String titleText, int numOfPegs) {
		String[] code;
		for (;;) {
			System.out.println();
			System.out.println(titleText);
			System.out.println();

			System.out.println();
			System.out.print("Enter a " + numOfPegs
					+ " peg code(Ex:rd rd gr gr): ");

			try {
				Console c;
				char[] secret;
				String input = "";

				if ((c = System.console()) != null
						&& (secret = c.readPassword()) != null) {
					input = String.valueOf(secret);
				}

				if (input == null || input.isEmpty()) {
					continue;
				}

				code = input.split(" ");

			} catch (Exception ex) {
				continue;
			}

			boolean valid = true;
			if (code.length > numOfPegs) {
				valid = false;
			}

			for (String a : code) {
				if (!this.availableColors.contains(a))
					valid = false;
			}

			if (!valid) {
				System.out
						.println("\r\nInvalid option. Please enter a valid number.");
				continue;
			}

			break;
		}

		return code;
	}

	/**
	 * Set the secret code and get it from the used through getHiddenCode()
	 */
	private void setSecretCode() {
		ICodemaker codeMaker = this.theGame.getCodeMaker();
		String[] inputcode = null;
		if (codeMaker != null) {
			codeMaker.setSecretCode();
		} else {
			// Use this line for the testing version
			inputcode = getCode("Set Secret Code!", Code.NUM_OF_PEGS);
			// Use this line for the final version
			// inputcode = getHiddenCode("Set the secret code!",
			// Code.NUM_OF_PEGS);
		}

		Code code = this.stringArrayToCode(inputcode);

		this.gameController.setSecretCode(code);
	}

	/**
	 * Prints a title surrounded by a box.
	 * 
	 * @param title
	 */
	private void printBoxedTitle(String title) {
		StringBuffer buffer = new StringBuffer(title.length() * 3 + 12);
		buffer.append('*');
		for (int i = 0; i < title.length(); i++)
			buffer.append('*');
		buffer.append("*\r\n*");
		buffer.append(title);
		buffer.append("*\r\n*");
		for (int i = 0; i < title.length(); i++)
			buffer.append('*');
		buffer.append("*\r\n");

		System.out.print(buffer.toString());
	}

	/**
	 * Displays a menu on the console, allowing the user to select an item by
	 * index.
	 * 
	 * @param titleText
	 *            The title of the menu.
	 * @param items
	 *            An array of items to display in the menu.
	 * @return An item selected from the array.
	 */
	private void setSettings() {

		boolean codeMakerIsComputer;
		IGameMode mode;
		// If null it means its a computer codebreaker
		ComputerGuessBehavior computer = null;

		// Get the mode of play from the user
		int modeSelection = showMenu("Select mode of play", new Object[] {
				"Novice", "Expert" });

		// Get the code maker
		int codeMaker = showMenu("Identify the codemaker (computer/human)",
				new Object[] { "Computer", "Human" });

		// Get the code breaker
		int codeBreaker = showMenu("Identify the codebreaker (computer/human)",
				new Object[] { "Computer", "Human" });

		// The algorithm, this is unused since we have only one implemented
		// strategy
		int algorithm = -1;

		// Set the codemaker state
		if (codeMaker == 0) {
			codeMakerIsComputer = true;
		} else {
			codeMakerIsComputer = false;
		}

		// If the code breaker is a computer, let the user pick the only
		// possible algorithm
		if (codeBreaker == 0) {
			algorithm = showMenu(
					"The codebreaker is a computer, select an algorithm",
					new Object[] { "random" });
			if (algorithm == 0) {
				computer = new RandomGuess(gameController);
			}

		}

		if (codeBreaker == 0) {
			this.theGame.setCodeMaker(new ComputerCodemaker(gameController));
		}

		// Generate the mode based on the pick
		if (modeSelection == 0) {
			mode = new NoviceMode();
		} else {
			mode = new ExpertMode();
		}

		// Set the settings
		this.gameController.setSettings(MAX_NUMBER_OF_GUESSES,
				codeMakerIsComputer, mode, computer, COMPUTER_INTERVAL);
	}

	private Code stringArrayToCode(String[] code) {
		ColorPeg[] pegs;
		pegs = new ColorPeg[Code.NUM_OF_PEGS];
		for (int j = 0; j < Code.NUM_OF_PEGS; j++) {
			pegs[j] = ColorPeg.valueFromConsole(code[j]);
		}
		return new Code(pegs);
	}

	@Override
	public void register() {
		this.theGame.register(this);
	}

	@Override
	public void notifyChange() {
		if (waitingState) {
			Guess lastGuess = data.getLatestMove();
			if (lastGuess.getFeedback() != null) {
				System.out.println("Feedback: " + lastGuess.getShortFeedback());
				waitingState = false;
			}
		}

		if (theGame.isGameOver()) {
			// If the game was triggered as new(timeout) then the game will exit
			if (theGame.getWinner() == null) {
				// Make some space to account for weird cmd
				// formatting
				System.out.println();
				System.out.println();
				System.out.println("The user has timed out...");
				System.out.println("The system will now exit, goodbye!");
				System.exit(1);
			} else {
				System.out.println(theGame.getWinningMessage());
			}
		}
	}

}