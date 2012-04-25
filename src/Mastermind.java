import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import mastermind.core.Code;
import mastermind.core.GameModel;
import mastermind.core.IGameState;
import mastermind.core.PlayList;
import mastermind.core.controller.*;
import mastermind.gui.*;
import mastermind.interfaces.INotifiable;
import mastermind.interfaces.Observer;

/**
 * Mastermind
 * 
 * Main class that takes care of initializing and setting up the program
 * 
 * @author Andres J Ruiz(ajr2546@rit.edu)
 *
 */
public class Mastermind implements INotifiable, Observer {

	// Constants
	// for easy access
	final private String windowTitle;

	// GUI Components
	private JFrame mainWindow;
	private JPanel currentView;
	private SettingsView settings;
	private CodeMakerPanel codemakerView;
	private MastermindMain mainView;

	// Data Models
	private PlayList playListModel;
	private GameModel theGame;
	private IGameState currentState;
	private ViewFactory factory;

	// Controllers
	private IGameController mainController;

	/**
	 * The state of the game
	 * 
	 * 0 for the settings page 1 for the codemakers page 2 for the codebreakers
	 * page
	 */
	private int state;

	/**
	 * Sets up the gui and controller. First view opened will populate the
	 * secret code for the game. Second view will contain the board and computer
	 * player and logger options.
	 */
	public Mastermind() {
		//state = 0; // Set the state as 0 to start

		// Set up any constants through this run
		windowTitle = "Mastermind";
		this.buildNewGame();
	}
	
	/**
	 * Initializes all game data
	 */
	private void buildNewGame() {
		boolean logging = false;
		
		if(null != this.theGame)
			logging = this.theGame.isLoggingEnabled();
			
		// Initialize the main game model, this model is persistent
		// through all the games
		this.theGame = new GameModel();
		
		if(logging)
			this.theGame.enableLogging();
		
		this.mainController = new GameController(this.theGame, null);
		this.theGame.register(this);
		this.currentState = mainController.getGameState();
		this.factory = new ViewFactory(this.mainController, this);
	}

	/**
	 * Create the gui window and show it.
	 */
	private void createAndShowWindow() {
		// Set the look and feel
		setLookAndFeel();

		// Create the window
		mainWindow = new JFrame();

		// Spawn this window in a new thread, good java practice
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				mainWindow.setTitle(windowTitle);
				mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainWindow.setSize(800, 600);

				// Show the window
				mainWindow.setVisible(true);
			}
		});

		// Start by showing the settings window
		showViewForState();
	}

	/**
	 * Show the settings page.
	 * 
	 * This method will remove the window in the previous step, if there was 
	 * a previous step and show the settings window. To grab the information from
	 * it use settings
	 */
	private void showSettings() {
		// If a previous game was played nuke it
		if (mainView != null)
			mainWindow.remove(mainView.getView());
		
		//Setup the settings view
		playListModel = new PlayList(10);
		mainController = new GameController(theGame, playListModel);
		settings = new SettingsView(this, mainController);
		
		//Change the panel
		mainWindow.add(settings);
		mainWindow.validate();
	}

	/**
	 * Get secret and set up gui with look and feel specific to operating system
	 * being used.
	 */
	private void showCodeMaker() {

		// Build the view
		codemakerView = new CodeMakerPanel(mainController);

		// Change the panel
		mainWindow.remove(settings);
		mainWindow.add(codemakerView);

		// Validate the new window contents
		mainWindow.validate();

	}

	/**
	 * Shows the CodeBreaker window and board
	 */
	private void showCodeBreaker() {
		// Get the secret code
		//secret = codemakerView.getSecret();

		// Build the controller and the view
		mainView = new MastermindMain(mainController, playListModel, theGame,
				this);

		// Change the panel
		mainWindow.remove(codemakerView);
		mainWindow.add(mainView.getView());

		// Validate the new window contents
		mainWindow.validate();
	}
	
	private void showViewForState() {
		// Build the view
		JPanel nextView = factory.getViewForState();

		// Change the panel
		if(this.currentView != null)
			mainWindow.remove(this.currentView);
		
		mainWindow.add(nextView);

		// Validate the new window contents
		mainWindow.validate();
		
		//Set current state to the newest game state
		this.currentState = this.mainController.getGameState();
	}

	/**
	 * Code to automatically detect the look and feel from the system. If the
	 * system doesn't have a native look and feel it tries the Nimbus Look and
	 * Feel which looks awesome. If none of them work... then default to do
	 * nothing.
	 */
	private void setLookAndFeel() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				// AWESOMENESS GOING ON HERE!!
				
				// FOR TESTING Use this as an example to change the color scheme
				// see:
				// http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/_nimbusDefaults.html#primary
				// float[] HSB = Color.RGBtoHSB(145, 0, 145, null);
				// System.out.println(HSB[0] + " " + HSB[1] + " " + HSB[2]);
				// UIManager.put("control", Color.getHSBColor(HSB[0], HSB[1],
				// HSB[2]));

				// Set the color for progress bar
				Color windowsGreen = Color.getHSBColor(0.3647343f, 0.96279067f,
						0.84313726f);

				UIManager.put("nimbusOrange", windowsGreen); // Change the color
																// used for the
																// progress bar

				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());

					break;
				}
			}
		} catch (Exception ex) {
			// Ignore and continue
			try {
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
				// System.out.println(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				// e.printStackTrace();
				// Ignore again... wow what's installed in here o_0?
			}
		}
	}

	@Override
	public void Notify() {
		
	}
	
	/**
	 * Kicks the program off by creating the Main container for the gui and
	 * system.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Mastermind().createAndShowWindow();
	}

	@Override
	public void register() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyChange() {
		/*if(theGame.isCodeMakerDone() && !trigger){
			System.out.println("Got the change, code maker is done");
			trigger = true;
			showCodeBreaker();
		}*/
		
		if(null != this.theGame && this.theGame.isGameOver())
		{
			this.buildNewGame();
			this.showViewForState();
		}
		else if(!this.currentState.equals(this.mainController.getGameState()))
			this.showViewForState();
	}

}
