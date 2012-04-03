import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import mastermind.core.Code;
import mastermind.core.PlayList;
import mastermind.core.controller.*;
import mastermind.gui.*;

public class Mastermind {
	
	private JFrame mainWindow;
	
	private PlayList playListModel;
	private IGameController mainController;
	private MastermindMain mainView;
	private CodeMakerPanel codemakerView;
	private Code secret;
	
	/**
	 * Sets up the gui and controller.
	 * First view opened will populate the secret code for the game.
	 * Second view will contain the board and computer player and
	 * logger options.
	 */
	public Mastermind(){
		
		playListModel = new PlayList();
		
		initGUI();
		
		mainController = new GameController(playListModel, secret);
		mainView = new MastermindMain(mainController, playListModel);
		
		mainWindow.remove(codemakerView);
		mainWindow.add(mainView.getView());
		mainWindow.getContentPane().invalidate();
		mainWindow.getContentPane().validate();
		
	}
	
	/**
	 * Get secret and set up gui with look and feel specific to
	 * operating system being used.
	 */
	private void initGUI(){
		setLookAndFeel();
		codemakerView = new CodeMakerPanel();
		
		mainWindow = new JFrame();
		
		mainWindow.add(codemakerView);
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	//TODO Make this a method calls that creates all the GUI objects here
            	//     inside the GUI thread instead of the main thread.
            	mainWindow.setTitle("Mastermind");
            	mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            	mainWindow.setSize(800, 600);
        		mainWindow.setVisible(true);
            }
        });
		
		while(codemakerView.getSecret().isEmpty()){
			try {
				Thread.sleep(100);
				if(!mainWindow.isVisible()){
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		secret = codemakerView.getSecret();
		
		System.out.println("Found you!");
		
	}
	
	
	
	
	/**
	 * Code to automatically detect the look and feel from the system. If the system
	 * doesn't have a native look and feel it tries the Nimbus Look and Feel which looks
	 * awesome. If none of them work... then default to do nothing.
	 */
	private void setLookAndFeel(){
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				// AWESOMENESS GOING ON HERE!!
				
				//FOR TESTING Use this as an example to change the color scheme
				//see: http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/_nimbusDefaults.html#primary
//				float[] HSB = Color.RGBtoHSB(192, 194, 196, null);
//				System.out.println(HSB[0] + " " + HSB[1] + " " + HSB[2]);
//				UIManager.put("control", Color.getHSBColor(HSB[0], HSB[1], HSB[2]));
				
				//Set the color for progress bar
				Color windowsGreen = Color.getHSBColor(0.3647343f, 0.96279067f, 0.84313726f);
				
				UIManager.put("nimbusOrange", windowsGreen); //Change the color used for the progress bar
				
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
	 * Kicks the program off by creating the Main container for the gui and system.
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Mastermind();
	}

}
