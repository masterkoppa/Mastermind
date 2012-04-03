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
import mastermind.core.controller.GameController;
import mastermind.core.controller.IGameController;
import mastermind.interfaces.Observer;

public class MastermindMain implements Observer{
	
	//MODELS AND CONTROLLERS
	private PlayList dataBackend;
	private IGameController controller;
	private Code secretCode;
	
	//GUI VARIABLES
	private JPanel mainWindow;
	private MastermindBoard board;
	
	public MastermindMain(IGameController controller, PlayList model, Code secretCode){
		//setLookAndFeel();
		
		this.dataBackend = model;
		this.controller = controller;
		this.secretCode = secretCode;
		
		mainWindow = new JPanel();//Set the JFrame with the window title
		
		mainWindow.setLayout(new BorderLayout());
		
		board = new MastermindBoard(model);
		
		mainWindow.add(board, BorderLayout.CENTER);
		mainWindow.add(this.generateOptions(), BorderLayout.EAST);
		
		//Register after initializing everything
		this.register();
	}

	public void register() {
		dataBackend.register(this);
	}

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

			public void actionPerformed(ActionEvent e) {
				//Input Validation
				ColorPeg[] code = board.getLastGuess();
				controller.submitGuess(code);
			}
			
		});
		
		JButton undo = new JButton("Undo");
		JCheckBox computer = new JCheckBox("Computer Code Breaker");
		JCheckBox logging = new JCheckBox("Logging Enabled");
		
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

	
	public JPanel getView(){
		return mainWindow;
	}

}
