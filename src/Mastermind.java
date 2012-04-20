import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import mastermind.core.Code;
import mastermind.core.GameModel;
import mastermind.core.PlayList;
import mastermind.core.controller.*;
import mastermind.gui.*;
import mastermind.interfaces.Observer;

public class Mastermind implements Observer{

	private JFrame mainWindow;
	private Rectangle previousBounds;
	
	private PlayList playListModel;
	private GameModel theGame;
	private IGameController mainController;
	private SettingsView settings;
	private MastermindMain mainView;
	private CodeMakerPanel codemakerView;
	private Code secret;
	
	/**
	 * The state of the game
	 * 
	 * 0 for the settings page
	 * 1 for the codemakers page
	 * 2 for the codebreakers page 
	 */
	private int state;

	/**
	 * Sets up the gui and controller. First view opened will populate the
	 * secret code for the game. Second view will contain the board and computer
	 * player and logger options.
	 */
	public Mastermind() {
		
		state = 0;

		theGame = new GameModel();
	}
	
	/**
	 * Create the gui window and show it.
	 */
	private void createAndShowWindow(){
		setLookAndFeel();
		mainWindow = new JFrame();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainWindow.setTitle("Mastermind");
				mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainWindow.setSize(800, 600);

				if (previousBounds != null) {
					mainWindow.setBounds(previousBounds);
				}

				mainWindow.setVisible(true);
			}
		});
		showSettings();
	}
	
	private void showSettings(){
		//If a previous game was played nuke it
		if(mainView != null)
			mainWindow.remove(mainView.getView());
		
		settings = new SettingsView(this);
		mainWindow.add(settings);
		mainWindow.validate();
	}

	/**
	 * Get secret and set up gui with look and feel specific to operating system
	 * being used.
	 */
	private void initGUI() {
		
		mainWindow.remove(settings);
		playListModel = new PlayList();
		codemakerView = new CodeMakerPanel(this);
		mainWindow.add(codemakerView);
		mainWindow.validate();
		
	}

	private void startGame() {
		mainController = new GameController(theGame, playListModel, secret);
		mainView = new MastermindMain(mainController, playListModel, theGame, this);

		mainWindow.remove(codemakerView);
		mainWindow.add(mainView.getView());
		mainWindow.validate();
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
				// float[] HSB = Color.RGBtoHSB(192, 194, 196, null);
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

	/**
	 * Kicks the program off by creating the Main container for the gui and
	 * system.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Mastermind().createAndShowWindow();;
	}

	@Override
	public void register() {
		
	}

	@Override
	public void notifyChange() {
		
		state = (state + 1)%3;
		
		
		switch(state){
		case 0:
			System.out.println("Settings Page");
			showSettings();
			break;
		case 1:
			System.out.println("Codemakers Page");
			
			initGUI();
			break;
		case 2:
			System.out.println("Codebreakers Page");
			secret = codemakerView.getSecret();
			startGame();
			break;
		}
	}

}
