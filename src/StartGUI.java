import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mastermind.gui.RoundButton;


public class StartGUI extends JFrame {
	private ArrayList<RoundButton> secretCode = new ArrayList<RoundButton>();
	private JButton start = new JButton("Start Game");
	private JLabel directions = new JLabel("Please enter the secret code");
	
	public StartGUI(){
		super();
		JPanel codePanel = new JPanel();
		codePanel.setLayout(new FlowLayout());
		
		for(int i = 0; i < 4; i++){
			RoundButton sTemp = new RoundButton("s");
			codePanel.add(sTemp);
			secretCode.add(sTemp);
		}
		//TODO: set the label to the center of the GUI
		setLayout(new GridLayout(3,1));
		add(directions);
		add(codePanel);
		add(start);
		
	}
	
	public static void main(String[] args){
		StartGUI gui = new StartGUI();
		gui.setVisible(true);
	}
	
}
