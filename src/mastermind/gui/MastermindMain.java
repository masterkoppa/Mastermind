package mastermind.gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import mastermind.core.Code;
import mastermind.core.ColorPeg;
import mastermind.core.PlayList;
import mastermind.core.commands.PlayCommand;
import mastermind.core.commands.ProvideFeedbackCommand;
import mastermind.core.commands.SubmitGuessCommand;
import mastermind.core.controller.GameController;
import mastermind.core.controller.IGameController;
import mastermind.interfaces.Observer;

public class MastermindMain implements Observer{
	
	//MODELS AND CONTROLLERS
	private PlayList dataBackend;
	private IGameController controller;
	private Code secretCode;
	
	//GUI VARIABLES
	private JFrame mainWindow;
	private MastermindBoard board;
	
	public MastermindMain(IGameController controller, PlayList model, Code secretCode){
		setLookAndFeel();
		
		this.dataBackend = model;
		this.controller = controller;
		this.secretCode = secretCode;
		
		mainWindow = new JFrame("Mastermind");//Set the JFrame with the window title
		
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setLayout(new BorderLayout());
		
		
		
		board = new MastermindBoard(model);
		
		mainWindow.add(board, BorderLayout.CENTER);
		mainWindow.add(this.generateOptions(), BorderLayout.EAST);
		
		//Register after initializing everything
		this.register();
	}

	@Override
	public void register() {
		dataBackend.register(this);
	}

	@Override
	public void notifyChange() {
		//Revalidate the internal board
		board.invalidate();
		board.revalidate();
	}
	
	private JPanel generateOptions(){
		JPanel options = new JPanel(new GridBagLayout());
		JPanel checkPanel = new JPanel(new FlowLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		
		JButton submit = new JButton("Submit");
		
		//Action Listener to submit the code
		submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//Input Validation
				ColorPeg[] code = board.getLastGuess();
				PlayCommand play = new PlayCommand();
				play.add(new SubmitGuessCommand(dataBackend, code));
				play.add(new ProvideFeedbackCommand(dataBackend, secretCode));
				controller.executeCommand(play);
			}
			
		});
		
		JButton undo = new JButton("Undo");
		JCheckBox computer = new JCheckBox("Computer Code Breaker");
		JCheckBox logging = new JCheckBox("logging");
		
		checkPanel.add(computer);
		checkPanel.add(logging);
		buttonPanel.add(submit);
		buttonPanel.add(undo);
		
		
		//Setup the gridbag layout options
		
		GridBagConstraints c = new GridBagConstraints();
		
		//Check Panel Settings
		c.gridx = 0;
		c.gridy = 0;
		
		options.add(checkPanel, c);

		//Button Panel Settings
		c.gridx = 0;
		c.gridy = 1;
		
		options.add(buttonPanel, c);
		
		return options;
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
	
	public void startGUI(){
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	//TODO Make this a method calls that creates all the GUI objects here
            	//     inside the GUI thread instead of the main thread.
            	mainWindow.setSize(800, 600);
        		mainWindow.setVisible(true);
            }
        });
	}
	
	/**
	 * Test main
	 * @param args
	 */
	public static void main(String[] args){
		new MastermindMain(new GameController(), new PlayList(), new Code(new ColorPeg[]{ColorPeg.RED, ColorPeg.RED, ColorPeg.BLUE, ColorPeg.BLUE})).startGUI();
	}

}
