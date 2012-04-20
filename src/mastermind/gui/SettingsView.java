package mastermind.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import mastermind.interfaces.Observer;

public class SettingsView extends JPanel {
	
	public SettingsView(final Observer mainFrame){
		super();
		JButton next = new JButton("NEXT");
		this.add(next);
		
		next.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.notifyChange();
			}
			
		});
	}
}
