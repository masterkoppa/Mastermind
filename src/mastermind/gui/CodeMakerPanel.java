package mastermind.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.*;

import mastermind.core.Code;

public class CodeMakerPanel extends JPanel{
	
	private Code secret;
	
	public CodeMakerPanel(){
		secret = new Code();
		build();
	}
	
	private void build(){
		this.setLayout(new GridBagLayout());
		
		JLabel instructions = new JLabel("Select your secret code by clicking through the colors bellow: ");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		
		c.ipady = 100;
		
		this.add(instructions, c);
		
		JPanel buttonArray = new JPanel(new GridLayout(0,Code.NUM_OF_PEGS));
		for(int i = 0; i < Code.NUM_OF_PEGS; i++){
			buttonArray.add(this.generateButton());
		}
		Dimension n = this.getSize();
		n.setSize(400, 50);
		buttonArray.setPreferredSize(n);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		
		
		this.add(buttonArray, c);
		
		JButton Submit = new JButton("Submit Secret Code!");
		
		Submit.setSize(Submit.getMinimumSize());
		
		c.gridx = 1;
		c.gridy = 1;	
		
		c.insets = new Insets(0,100,0,0);

		
		this.add(Submit, c);
		
		
	}
	
	private JButton generateButton(){
		return new JButton();
	}
	
	public static void main(String[] args){
		JFrame main = new JFrame();
		main.add(new CodeMakerPanel());
		
		main.setSize(800, 600);
		main.setVisible(true);
	}
	
}
