package mastermind.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import mastermind.core.Code;
import mastermind.core.ColorPeg;

public class CodeMakerPanel extends JPanel {

	private Code secret;
	private ColorPeg[] code;
	private ColorPeg[] availableColors;
	private boolean submitted;

	public CodeMakerPanel() {
		secret = new Code();
		code = new ColorPeg[Code.NUM_OF_PEGS];
		availableColors = new ColorPeg[] { ColorPeg.BLACK, ColorPeg.BLUE,
				ColorPeg.GREEN, ColorPeg.RED, ColorPeg.WHITE, ColorPeg.YELLOW };
		submitted = false;
		build();
	}

	private void build() {
		this.setLayout(new GridBagLayout());

		JLabel instructions = new JLabel(
				"Select your secret code by clicking through the colors bellow: ");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;

		c.ipady = 100;

		this.add(instructions, c);

		JPanel buttonArray = new JPanel(new GridLayout(0, Code.NUM_OF_PEGS));
		for (int i = 0; i < Code.NUM_OF_PEGS; i++) {
			buttonArray.add(this.generateButton(i));
		}
		Dimension n = this.getSize();
		n.setSize(400, 50);
		buttonArray.setPreferredSize(n);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;

		this.add(buttonArray, c);

		JButton Submit = new JButton("Submit Secret Code!");

		Submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Validate Code Here
				submitted = true;
			}

		});

		Submit.setSize(Submit.getMinimumSize());

		c.gridx = 1;
		c.gridy = 1;

		c.insets = new Insets(0, 100, 0, 0);

		this.add(Submit, c);

	}

	private JButton generateButton(int ID) {
		JButton gridButton = new JButton();

		gridButton.setName(Integer.toString(ID));

		gridButton.addActionListener(new ActionListener() {
			private int index = -1;

			public void actionPerformed(ActionEvent e) {
				JButton self = (JButton) e.getSource();
				if (index == -1) {
					index = 0;
				} else {
					index = (index + 1) % availableColors.length;
				}

				self.setBackground(availableColors[index].getColor());

				// Some hackish fix :D

				String number = self.getName();
				int index = Integer.parseInt(number);
				code[index] = availableColors[this.index];
			}

		});

		return gridButton;
	}

	public Code getSecret() {
		if (!submitted)
			return new Code();
		secret = new Code(code);
		return secret;
	}

	public static void main(String[] args) {
		JFrame main = new JFrame();
		main.add(new CodeMakerPanel());

		main.setSize(800, 600);
		main.setVisible(true);
	}

}
