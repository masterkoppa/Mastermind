package mastermind.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import mastermind.core.Code;
import mastermind.core.ColorPeg;
import mastermind.core.FeedbackPeg;
import mastermind.core.Guess;
import mastermind.core.PlayList;
import mastermind.interfaces.Observer;

public class MastermindBoard extends JPanel implements Observer {

	/**
	 * Generated Serial Version ID
	 */
	private static final long serialVersionUID = 2995807532949820200L;
	private PlayList data;
	private ArrayList<Row> rows;
	private final ColorPeg[] availableColors;
	private boolean isBeingEdited;

	public MastermindBoard(PlayList model) {
		data = model;
		rows = new ArrayList<Row>();
		availableColors = ColorPeg.values();
		this.register();
	}

	@Override
	public void register() {
		data.register(this);
	}

	@Override
	public void notifyChange() {
		this.removeAll(); // Clear the JPanel
		isBeingEdited = false;

		rows = new ArrayList<Row>();

		for (int i = 0; i < PlayList.getNUM_OF_ROWS(); i++) {
			Guess g = data.getMove(i);
			if (g == null) {
				g = new Guess();
			}
			FeedbackPeg[] feedback = g.getFeedbackPegs();
			ColorPeg[] code = g.getCodePegs();
			rows.add(new Row(feedback, code));
		}

		int index = data.getLastPlayIndex();
		if (data.getMove(index) != null)
			rows.get(index).setBackground(Color.GREEN);

		this.setLayout(new GridLayout(0, 1));

		for (Row r : rows) {
			this.add(r);
		}

	}

	public ColorPeg[] getLastGuess() {
		if (data.getLastPlayIndex() == 0 && data.getMove(0) == null) {
			return rows.get(data.getLastPlayIndex()).getCode();
		} else {
			return rows.get(data.getLastPlayIndex() + 1).getCode();
		}
	}

	public void gameIsOver() {
		for (Row i : rows) {
			i.disable();
		}
	}

	private class Row extends JPanel {

		private static final long serialVersionUID = 1L;
		private FeedbackPeg[] feedback;
		private boolean hasFeedback;
		private ColorPeg[] code;
		private JPanel codePanel;

		public Row(FeedbackPeg[] feedback, ColorPeg[] code) {
			// Check if this data even exists, most of the time
			// it wont exist so we make it empty
			if (feedback != null) {
				this.feedback = feedback;
				this.hasFeedback = true;
			} else {
				this.feedback = new FeedbackPeg[Code.NUM_OF_PEGS];
				this.hasFeedback = false;
			}

			if (code != null)
				this.code = code;
			else
				this.code = new ColorPeg[Code.NUM_OF_PEGS];

			build();

			this.setPreferredSize(new Dimension(0, 80));
			this.setBorder(BorderFactory.createEtchedBorder());
		}

		private void build() {
			this.setLayout(new BorderLayout());

			// Add the feedback panel to this row
			JPanel feedbackPanel = new JPanel(new GridLayout(0, 2));

			for (int i = 0; i < Code.NUM_OF_PEGS; i++) {
				JButton peg = new JButton();
				peg.setFocusable(false); // Can't do just setEnabeled for some
											// looks and feels

				if (feedback[i] == null) {
					// Don't do anything
					// peg.setBackground(Color.RED); //To show something
					// there...
					peg.setToolTipText("Empty");
				} else {
					peg.setBackground(feedback[i].getColor());
					peg.setToolTipText(feedback[i].getName());// Add tooltip to
																// the button,
																// just because?
				}

				feedbackPanel.add(peg);
			}

			this.add(feedbackPanel, BorderLayout.WEST);
			feedbackPanel.setPreferredSize(new Dimension(80, 0));

			// Set up the code for this row

			codePanel = new JPanel(new GridLayout(0, Code.NUM_OF_PEGS));

			for (int i = 0; i < Code.NUM_OF_PEGS; i++) {
				JButton peg = new JButton();
				peg.setName(Integer.toString(i)); // This is how we know where
													// we are

				if (code[i] == null) {
					// Don't do anything
					// peg.setBackground(Color.RED); //To show something
					// there...
					peg.setToolTipText("Empty");
				} else {
					peg.setBackground(code[i].getColor());
				}

				// Action Listener to go through all the colors
				peg.addActionListener(new ActionListener() {
					private int index = -1;

					@Override
					public void actionPerformed(ActionEvent e) {
						if (hasFeedback == true) {
							return;
						}
						isBeingEdited = true;
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

				codePanel.add(peg);
			}

			this.add(codePanel, BorderLayout.CENTER);
		}

		@Override
		public void disable() {
			Component[] componentsInPanel = codePanel.getComponents();

			for (Component component : componentsInPanel) {
				try {
					JButton j = (JButton) component;
					for (ActionListener al : j.getActionListeners()) {
						j.removeActionListener(al);
					}
				} catch (ClassCastException e) {

				}
			}
		}

		public ColorPeg[] getCode() {
			return this.code;
		}

	}

	public boolean isBeingEdited() {
		return isBeingEdited;
	}

	public void clearLastRow() {
		this.notifyChange();
	}

}
