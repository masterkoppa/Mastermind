package mastermind.cui;

import java.io.*;
import java.util.ArrayList;

import mastermind.core.*;
import mastermind.core.codebreaker.ComputerGuessBehavior;
import mastermind.core.codebreaker.RandomGuess;
import mastermind.core.codemaker.*;
import mastermind.core.controller.GameController;
import mastermind.core.controller.IGameController;
import mastermind.core.modes.*;
import mastermind.interfaces.Observer;
import mastermind.core.Code;

/**
 * Implements a console interface to the Pizza Delivery System.
 * 
 */
public class ConsoleUi implements Observer {

	private final static int MAX_NUMBER_OF_GUESSES = 10;
	private final static int COMPUTER_INTERVAL = 3;

	ArrayList<String> availableColors;
	private GameModel theGame;
	private IGameController gameController;
	private PlayList data;

	private boolean codeBreakerHuman;

	public ConsoleUi() {
		this.availableColors = new ArrayList<String>();

		// Get all the available colors for code in the system
		for (ColorPeg i : ColorPeg.values()) {
			availableColors.add(i.getShortName());
		}
		// Initialize the main game model, this model is persistent
		// through all the games
		theGame = new GameModel();
		gameController = new GameController(theGame, data);
		this.register();

		run();
	}

	private void run() {
		printBoxedTitle("Welcome to Mastermind!");
		setSettings();
		data = new PlayList(10); // CHANGE TO GET INPUT
		data.register(this);
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

	private void playGame() {
		// Get secret code
		ICodemaker codeMaker = this.theGame.getCodeMaker();
		String[] inputcode = null;
		if (codeMaker != null) {
			codeMaker.setSecretCode();
		} else {
			inputcode = getCode("Set Secret Code!", Code.NUM_OF_PEGS);
		}
		ColorPeg[] pegs;
		pegs = new ColorPeg[Code.NUM_OF_PEGS];
		for (int j = 0; j < Code.NUM_OF_PEGS; j++) {
			System.out.println(inputcode[j]);
			pegs[j] = ColorPeg.valueFromConsole(inputcode[j]);
			System.out.println(pegs[j]);
		}
		Code code = new Code(pegs);
		System.out.println(code);
		this.gameController.setSecretCode(code);

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
				System.out
						.println("\r\nInvalid option. Please enter a valid number.");
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
	 * Prints a title surrounded by a box.
	 * 
	 * @param title
	 */
	private void printBoxedTitle(String title) {
		StringBuffer buffer = new StringBuffer(title.length() * 3 + 12);
		buffer.append("â”Œ");
		for (int i = 0; i < title.length(); i++)
			buffer.append("â”€");
		buffer.append("â”�\r\nâ”‚");
		buffer.append(title);
		buffer.append("â”‚\r\nâ””");
		for (int i = 0; i < title.length(); i++)
			buffer.append("â”€");
		buffer.append("â”˜\r\n");

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
		boolean codeBreakerIsComputer;
		IGameMode mode;
		ComputerGuessBehavior computer;

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
			codeBreakerIsComputer = true;
			algorithm = showMenu(
					"The codebreaker is a computer, select an algorithm",
					new Object[] { "random" });
			if (algorithm == 0) {
				computer = new RandomGuess(gameController);
			}

		} else {
			codeBreakerIsComputer = false;
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
				codeMakerIsComputer, mode, null, COMPUTER_INTERVAL);
	}

	@Override
	public void register() {
		// TODO Auto-generated method stub
		this.theGame.register(this);
	}

	@Override
	public void notifyChange() {
		// TODO Auto-generated method stub

	}

}