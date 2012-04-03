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
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import mastermind.core.ColorPeg;
import mastermind.core.PlayList;
import mastermind.core.controller.IGameController;
import mastermind.interfaces.Observer;

public class MastermindMain implements Observer{
	
	//MODELS AND CONTROLLERS
	private PlayList dataBackend;
	private IGameController controller;
	private int selectedDelay;
	
	//GUI VARIABLES
	private JPanel mainWindow;
	private MastermindBoard board;
	private JButton submit;
	private JButton undo;
	private JSlider delaySelector;
	private JLabel delayLabel;
	
	public MastermindMain(IGameController controller, PlayList model){
		//setLookAndFeel();
		
		this.dataBackend = model;
		this.controller = controller;
		this.selectedDelay = 30;
		
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
		JPanel checkPanel = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		
		submit = new JButton("Submit");
		
		//Action Listener to submit the code
		submit.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				//Input Validation
				ColorPeg[] code = board.getLastGuess();
				controller.submitGuess(code);
			}
			
		});
		
		undo = new JButton("Undo");
		
		
		JCheckBox computer = new JCheckBox("Computer Code Breaker");
		
		computer.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				int newState = e.getStateChange();
				
				if(newState == ItemEvent.DESELECTED){
					submit.setEnabled(true);
					undo.setEnabled(true);
					delaySelector.setEnabled(true);
					
					controller.stopAI();
				} else{
					submit.setEnabled(false);
					undo.setEnabled(false);
					delaySelector.setEnabled(false);

					controller.startAI(delaySelector.getValue());
				}
			}
			
		});
		
		delayLabel = new JLabel("30 s");
		
		delaySelector = new JSlider(0, 30, 30);
		
		delaySelector.addChangeListener(new ChangeListener(){

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
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		
		checkPanel.add(computer, c);
		
		c.gridx = 1;
		c.insets = new Insets(0,20,0,10);
		
		
		
		checkPanel.add(delayLabel, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.insets = new Insets(20,0,40,0);
		
		checkPanel.add(delaySelector, c);
		
		c.gridy = 2;
		c.insets = new Insets(0,0,50,0);
		
		checkPanel.add(logging, c);
		
		
		buttonPanel.add(submit);
		buttonPanel.add(undo);
		
		
		//Setup the gridbag layout options
		
		c = new GridBagConstraints();
		
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
