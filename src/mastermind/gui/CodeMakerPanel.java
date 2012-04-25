package mastermind.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mastermind.core.Code;
import mastermind.core.ColorPeg;
import mastermind.core.controller.IGameController;
import mastermind.interfaces.Observer;

public class CodeMakerPanel extends JPanel implements Observer {

	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 2222995828266338433L;
	private Code secret;
	private ColorPeg[] code;
	private ColorPeg[] availableColors;
	private IGameController gameController;

	public CodeMakerPanel(IGameController gameController) {
		this.gameController = gameController;
		secret = new Code();
		code = new ColorPeg[Code.NUM_OF_PEGS];
		availableColors = new ColorPeg[] { ColorPeg.BLUE, ColorPeg.GREEN,
				ColorPeg.RED, ColorPeg.YELLOW, ColorPeg.PURPLE };
		build();
	}

	private void build() {
		this.setLayout(new GridBagLayout());

		JLabel instructions = new JLabel("Create your secret code bellow,\n"
				+ " to select the desired colors click each button"
				+ " multiple times until the desired color is there");
		
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
			public void actionPerformed(ActionEvent e) {
				try {
					getSecret();
					gameController.setSecretCode(secret);
					System.out.println("Code is valid");
					gameController.stageDone(CodeMakerPanel.this);
				} catch (IllegalArgumentException ex) {
					JOptionPane
							.showMessageDialog(CodeMakerPanel.this,
									"The Code you tried to submit is not valid, please try again");
				}
			}

		});

		Submit.setSize(Submit.getMinimumSize());

		c.gridx = 1;
		c.gridy = 1;

		c.insets = new Insets(0, 100, 0, 0);

		this.add(Submit, c);
	}

	private JButton generateButton(int id) {
		JButton gridButton = new JButton();

		gridButton.setName(Integer.toString(id));

		gridButton.addActionListener(new ActionListener() {
			private int index = -1;

			@Override
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

	private Code getSecret() {
		secret = new Code(code);
		return secret;
	}

	@Override
	public void register() {
		// UNUSED
	}

	@Override
	public void notifyChange() {
		// UNUSED
	}

}
