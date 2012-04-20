package mastermind.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import mastermind.interfaces.INotifiable;

public class SettingsView extends JPanel {

	/**
	 * Generate Serial Version ID
	 */
	private static final long serialVersionUID = 27122833485072815L;

	public SettingsView(final INotifiable mainFrame) {
		super();
		JButton next = new JButton("NEXT");
		this.add(next);

		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.Notify();
			}

		});
	}
}
