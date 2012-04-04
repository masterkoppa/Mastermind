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

import mastermind.core.ColorPeg;
import mastermind.core.GameModel;
import mastermind.core.PlayList;
import mastermind.core.controller.IGameController;
import mastermind.interfaces.Observer;

public class MastermindMain implements Observer{
	
	//MODELS AND CONTROLLERS
	private PlayList dataBackend;
	private GameModel currentGame;
	private IGameController controller;
	private int selectedDelay;
	private boolean newGameSelected;
	
	//GUI VARIABLES
	private JPanel mainWindow;
	private MastermindBoard board;
	private JButton submit;
	private JButton undo;
	private JButton newGame;
	private JSlider delaySelector;
	private JLabel delayLabel;
	private JCheckBox computer;
	
	public MastermindMain(IGameController controller, PlayList model, GameModel theGame){
		//setLookAndFeel();
		
		this.dataBackend = model;
		this.currentGame = theGame;
		this.controller = controller;
		this.selectedDelay = 30;
		this.newGameSelected = false;
		
		mainWindow = new JPanel();//Set the JFrame with the window title
		
		mainWindow.setLayout(new BorderLayout());
		
		board = new MastermindBoard(model);
		
		mainWindow.add(board, BorderLayout.CENTER);
		mainWindow.add(this.generateOptions(), BorderLayout.EAST);
		
		//Register after initializing everything
		this.register();
	}

	public void register() {
		this.dataBackend.register(this);
		this.currentGame.register(this);
	}

	public void notifyChange() {
		//Revalidate the internal board
		board.invalidate();
		board.revalidate();
		
		//Check if the game is over
		if(currentGame.isGameOver()){
			board.gameIsOver();
			submit.setEnabled(false);
			computer.setEnabled(false);
			undo.setEnabled(false);
			showWinningMessage(currentGame.getWinningMessage());
		}
	}
	
	private void showWinningMessage(String winningMessage) {
		JOptionPane.showMessageDialog(mainWindow, winningMessage);
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
		undo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				controller.undoCommand();
			}
		});
		
		newGame = new JButton("New Game");
		newGame.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("You called?");
				newGameSelected = true;
			}
			
		});
		
		
		computer = new JCheckBox("Computer Code Breaker");
		
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
					
					controller.startAI(selectedDelay);
				}
			}
		});
		
		delayLabel = new JLabel("30 s");
		
		delaySelector = new JSlider(1, 30, 30);
		
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
		
		logging.addItemListener( new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				int newState = e.getStateChange();
				
				if(newState == ItemEvent.DESELECTED){
					//Disable the logging
					System.out.println("Item Deselected");
				} else{
					//Enable Logging
					System.out.println("Item Selected");
					
					//The file name where the user want's the file stored
					String fileName = "";
					
					//Open a File Chooser
					
					
					
					int result = JFileChooser.ERROR_OPTION;
					
					JFileChooser file = new JFileChooser();
					
					file.setFileFilter(new FileFilter(){

						@Override
						public boolean accept(File f) {
							if(f.getName().endsWith(".txt") || f.isDirectory()){
								return true;
							}else{
								return false;
							}
						}

						@Override
						public String getDescription() {
							return "Text File (.txt)";
						}
						
					});
					
							
					while(result == JFileChooser.ERROR_OPTION){
						result = file.showSaveDialog(mainWindow);
					}
					
					if(result == JFileChooser.CANCEL_OPTION){
						JCheckBox l = (JCheckBox) e.getSource();
						l.setSelected(false);
						return;
					}else{
						File f = file.getSelectedFile();
						
						fileName = f.getPath();
						System.out.println(fileName);
					}
					
					
				}
			}
			
			
		});
		
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
		
		c.gridy = 2;
		
		options.add(newGame, c);
		
		return options;
	}

	
	public JPanel getView(){
		return mainWindow;
	}

	public boolean isDone() {
		return newGameSelected;
	}

}
